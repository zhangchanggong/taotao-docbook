/*
 * Copyright 2024 王金涛
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package store.taotao.docbook.core.job;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import store.taotao.docbook.core.*;
import store.taotao.docbook.core.docbook.SingleOutputProcessor;
import store.taotao.docbook.core.postprocessor.MergeProcessor;
import store.taotao.docbook.core.util.VFSUtils;

import java.util.HashMap;

import static store.taotao.docbook.core.TaotaoDocbookConstant.RESOURCE_DIR;
import static store.taotao.docbook.core.TaotaoDocbookConstant.STAGING_DIR;

/**
 * html 处理类的处理过程
 */
@Slf4j
public class HtmlJob implements Job<HtmlJob.HtmlContext> {

    private DocbookProcessor<?> docbookProcessor = new SingleOutputProcessor();

    private PostProcessor<?>[] postProcessors = new PostProcessor[]{
            new MergeProcessor()
    };

    @Override
    public void process(HtmlContext jobContext) throws TaotaoDocbookException {
        log.info("-------------------- HtmlJob 处理开始 --------------------");
        initContext(jobContext);
        docbookProcessor.process(jobContext.getDocbookProcessorConfig());
        for (PostProcessor<?> processor : postProcessors) {
            processor.process(jobContext.getPostProcessorConfigs().get(processor.getConfigKey()));
        }

        log.info("-------------------- HtmlJob 处理结束 --------------------");
    }

    private void initContext(HtmlContext jobContext) {
        jobContext.setPostProcessorConfigs(new HashMap<>());
        initProcessorConfig(jobContext);
        initMergeConfig(jobContext);
    }

    private void initMergeConfig(HtmlContext jobContext) {
        MergeProcessor.MergeConfig config = new MergeProcessor.MergeConfig();
        try {
            FileObject descFile = VFSUtils.getResource(jobContext.getDescFile(), jobContext.getDescDir());
            config.setDescPath(descFile.getParent().getPublicURIString());
        } catch (FileSystemException e) {
            log.warn("获取目标地址出错",e);
            throw new RuntimeException(e);
        }
        config.setSourcePaths(new String[]{StringUtils.joinWith("/", jobContext.getWorkDir(), STAGING_DIR,RESOURCE_DIR)});
        jobContext.getPostProcessorConfigs().put(MergeProcessor.class.getCanonicalName(), config);
    }


    private void initProcessorConfig(HtmlContext jobContext) {
        SingleOutputProcessor.SingleOutputProcessorConfig config = new SingleOutputProcessor.SingleOutputProcessorConfig();

        config.setLanguage(jobContext.getLanguage());
        config.setDocbookDir(jobContext.getDocbookDir());
        config.setDocbookFile(jobContext.getDocbookFile());
        config.setResultDir(jobContext.getDescDir());
        config.setResultFile(jobContext.getDescFile());
        config.setXsltDir(jobContext.getXsltDir());
        config.setXsltFile(jobContext.getXsltFile());

        jobContext.setDocbookProcessorConfig(config);

    }


    @Setter
    @Getter
    public static class HtmlContext extends JobContext {
        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }
    }

}
