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
import store.taotao.docbook.core.*;
import store.taotao.docbook.core.docbook.SingleOutputProcessor;
import store.taotao.docbook.core.postprocessor.FopPostProcessor;

import java.util.HashMap;

import static store.taotao.docbook.core.TaotaoDocbookConstant.*;

/**
 * 基于 xsl-fo 的
 */
@Slf4j
public class XslFoJob implements Job<XslFoJob.XslFoJobContext> {


    private DocbookProcessor<?> docbookProcessor=new SingleOutputProcessor();

    private PostProcessor<?>[] postProcessors=new PostProcessor[]{
            new FopPostProcessor()
    };

    @Override
    public void process(XslFoJobContext jobContext) throws TaotaoDocbookException {
        log.info("-------------------- XslFoJob 处理开始 --------------------");
        initContext(jobContext);

        docbookProcessor.process(jobContext.getDocbookProcessorConfig());
        for (PostProcessor<?> processor:postProcessors){
            processor.process(jobContext.getPostProcessorConfigs().get(processor.getConfigKey()));
        }

        log.info("-------------------- XslFoJob 处理结束 --------------------");
    }

    private void initContext(XslFoJobContext jobContext) {
        jobContext.setPreProcessorConfigs(new HashMap<>());
        jobContext.setPostProcessorConfigs(new HashMap<>());


        initProcessorConfig(jobContext);

        initFopConfig(jobContext);
    }

    private void initFopConfig(XslFoJobContext jobContext) {
        FopPostProcessor.FopPostConfig config=new FopPostProcessor.FopPostConfig();

        config.setTargetResolution(jobContext.targetResolution);
        config.setStagingDir(StringUtils.joinWith("/",jobContext.getWorkDir(),STAGING_DIR));
        config.setOutFile(jobContext.getDescFile());
        config.setOutDir(jobContext.getDescDir());
        config.setMimeType(jobContext.mimeType);
        config.setFoFile(jobContext.getDocbookFile());
        config.setFopConfigPath(jobContext.fopConfigPath);
        config.setFoDir(StringUtils.joinWith("/",jobContext.getWorkDir(),STAGING_DIR,FO_DIR));
        config.setLanguage(jobContext.getLanguage());
        config.setResourceDir(StringUtils.joinWith("/",jobContext.getWorkDir(),STAGING_DIR,RESOURCE_DIR));
        config.setSourceResolution(jobContext.sourceResolution);

        jobContext.getPostProcessorConfigs().put(FopPostProcessor.class.getCanonicalName(),config);
    }

    private void initProcessorConfig(XslFoJobContext jobContext) {
        SingleOutputProcessor.SingleOutputProcessorConfig config=new SingleOutputProcessor.SingleOutputProcessorConfig();

        config.setLanguage(jobContext.getLanguage());
        config.setDocbookDir(jobContext.getDocbookDir());
        config.setDocbookFile(jobContext.getDocbookFile());
        config.setResultDir(StringUtils.joinWith("/",jobContext.getWorkDir(),STAGING_DIR,FO_DIR));
        config.setResultFile(jobContext.getDocbookFile());
        config.setXsltDir(jobContext.getXsltDir());
        config.setXsltFile(jobContext.getXsltFile());

        jobContext.setDocbookProcessorConfig(config);

    }

    /**
     * 扩展的上下文
     */
    @Setter
    @Getter
    public static class XslFoJobContext extends JobContext {
        /**
         * fop 的配置文件路径 vfs 格式
         */
        private String fopConfigPath;
        /**
         * fop 特有的输出格式类型
         */
        private String mimeType;
        /**
         * 源分辨率
         */
        private float sourceResolution=72f;
        /**
         * 目标分辨率
         */
        private float targetResolution=72f;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }
    }
}
