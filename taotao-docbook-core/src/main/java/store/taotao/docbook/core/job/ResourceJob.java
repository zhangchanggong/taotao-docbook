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
import store.taotao.docbook.core.Job;
import store.taotao.docbook.core.JobContext;
import store.taotao.docbook.core.TaotaoDocbookException;
import store.taotao.docbook.core.preprocessor.ResourceProcessor;

import java.util.HashMap;
import java.util.Map;

import static store.taotao.docbook.core.TaotaoDocbookConstant.*;
import static store.taotao.docbook.core.TaotaoDocbookConstant.DOCX_DIR;

/**
 * 资源准备的 job
 */
@Slf4j
public class ResourceJob implements Job<ResourceJob.ResourceContext> {


    private ResourceProcessor processor=new ResourceProcessor();

    @Override
    public void process(ResourceContext jobContext) throws TaotaoDocbookException {
        log.info("-------------------- 资源文件 处理开始 --------------------");
        ResourceProcessor.ResourceConfig config=initContext(jobContext);
        processor.process(config);
        log.info("-------------------- 资源文件 处理结束 --------------------");
    }

    private ResourceProcessor.ResourceConfig initContext(ResourceContext jobContext) {
        ResourceProcessor.ResourceConfig config = new ResourceProcessor.ResourceConfig();
        config.setBaseDir(StringUtils.joinWith("/", jobContext.getWorkDir(), STAGING_DIR));
        Map<String, String[]> copyPair = new HashMap<>(3);
        copyPair.put(RESOURCE_DIR, jobContext.getResourcePaths());
        copyPair.put(FONTS_DIR, jobContext.getFontPaths());
        copyPair.put(DOCX_DIR, jobContext.getDocxPaths());
        config.setCopyPair(copyPair);
        return config;
    }

    @Setter
    @Getter
    public static class ResourceContext extends JobContext {
        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }
    }
}
