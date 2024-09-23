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
