/*
 * Copyright © 2022 王金涛。
 * This file is part of taotao-docbook.
 *
 * taotao-docbook is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * taotao-docbook is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE. See the GNU Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser Public License along with taotao-docbook. If
 * not, see <https://www.gnu.org/licenses/>.
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
