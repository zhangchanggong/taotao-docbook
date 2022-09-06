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
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import store.taotao.docbook.core.JobContext;
import store.taotao.docbook.core.Job;
import store.taotao.docbook.core.TaotaoDocbookException;
import store.taotao.docbook.core.preprocessor.CleanPreProcessor;

/**
 * 环境清理 job
 */
@Slf4j
public class ClearJob implements Job<ClearJob.ClearContext> {


    private CleanPreProcessor cleanPreProcessor=new CleanPreProcessor();


    @Override
    public void process(ClearContext jobContext) throws TaotaoDocbookException {
        log.info("-------------------- 清理环境 处理开始 --------------------");
        CleanPreProcessor.CleanConfig config=initContext(jobContext);
        cleanPreProcessor.process(config);
        log.info("-------------------- 清理环境 处理结束 --------------------");
    }

    private CleanPreProcessor.CleanConfig initContext(ClearContext jobContext) {
        CleanPreProcessor.CleanConfig config = new CleanPreProcessor.CleanConfig();
        config.setCleanDir(new String[]{jobContext.getWorkDir()});
        return config;
    }

    @Setter
    @Getter
    public static class ClearContext extends JobContext {
        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }
    }
}
