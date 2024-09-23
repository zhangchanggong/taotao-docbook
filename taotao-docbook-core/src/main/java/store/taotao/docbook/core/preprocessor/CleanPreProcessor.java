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

package store.taotao.docbook.core.preprocessor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import store.taotao.docbook.core.ClassNameConfigKeyProcesser;
import store.taotao.docbook.core.PreProcessor;
import store.taotao.docbook.core.ProcessorConfig;
import store.taotao.docbook.core.util.VFSUtils;

import java.io.IOException;
import java.util.Arrays;

/**
 * 主要用于对目标路径的清理
 */
@Slf4j
public class CleanPreProcessor extends ClassNameConfigKeyProcesser<CleanPreProcessor.CleanConfig> implements PreProcessor<CleanPreProcessor.CleanConfig> {

    public CleanPreProcessor() {
        super(CleanConfig.class);
    }

    @Override
    protected void doProcess(CleanConfig config) {
        Arrays.stream(config.cleanDir).forEach(path -> {
            try {
                FileUtils.forceDelete(VFSUtils.getResource(path, null).getPath().toFile());
            } catch (IOException e) {
                log.warn("删除指定路径错误，path=[{}]", path, e);
            }
        });
    }

    @Setter
    @Getter
    public static class CleanConfig extends ProcessorConfig {
        /**
         * 需要清理的路径
         */
        public String[] cleanDir;
    }
}
