/*
 * Copyright © 2022 王金涛。
 * This file is part of taotao-docbook.
 *
 * taotao-docbook is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * taotao-docbook is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with taotao-docbook. If
 * not, see <https://www.gnu.org/licenses/>.
 */

package xyz.taotao.docbook.core.job;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import xyz.taotao.docbook.core.*;
import xyz.taotao.docbook.core.docbook.SingleOutputProcessor;
import xyz.taotao.docbook.core.postprocessor.MergeProcessor;
import xyz.taotao.docbook.core.preprocessor.CleanPreProcessor;
import xyz.taotao.docbook.core.preprocessor.ResourceProcessor;
import xyz.taotao.docbook.core.util.VFSUtils;

import java.util.HashMap;
import java.util.Map;

import static xyz.taotao.docbook.core.TaotaoDocbookConstant.*;

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
