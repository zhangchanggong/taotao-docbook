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
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.vfs2.AllFileSelector;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import store.taotao.docbook.core.ClassNameConfigKeyProcesser;
import store.taotao.docbook.core.PreProcessor;
import store.taotao.docbook.core.ProcessorConfig;
import store.taotao.docbook.core.TaotaoDocbookException;
import store.taotao.docbook.core.util.VFSUtils;

import java.util.Arrays;
import java.util.Map;

@Slf4j
public class ResourceProcessor extends ClassNameConfigKeyProcesser<ResourceProcessor.ResourceConfig> implements PreProcessor<ResourceProcessor.ResourceConfig> {

    public ResourceProcessor() {
        super(ResourceConfig.class);
    }

    @Setter
    @Getter
    public static class ResourceConfig extends ProcessorConfig {
        /**
         * 目的路径与原路径的对应关系
         */
        private Map<String, String[]> copyPair;
        /**
         * 基本路径
         */
        private String baseDir;
    }

    @Override
    protected void doProcess(ResourceConfig config) throws TaotaoDocbookException {
        for (Map.Entry<String, String[]> entry : config.getCopyPair().entrySet()) {
            doCopy(entry.getKey(), entry.getValue(), config.baseDir);
        }
    }

    /**
     * 将 sources 中的内容复制到 desc 中
     *
     * @param desc    复制的目标地址
     * @param sources 复制的源地址
     * @param baseDir 复制的源地址
     * @throws TaotaoDocbookException 发生不和继续进行的异常
     */
    private void doCopy(String desc, String[] sources, String baseDir) throws TaotaoDocbookException {
        if (ObjectUtils.isEmpty(sources)){
            return;
        }
        try {
            FileObject descDir = VFSUtils.getResource(desc, baseDir);
            Arrays.stream(sources).forEach(source -> {
                try {
                    FileObject sourceFile = VFSUtils.getResource(source, null);
                    descDir.copyFrom(sourceFile, new AllFileSelector());
                } catch (FileSystemException e) {
                    log.warn("复制文件错误,source=[{}]", source, e);
                }
            });
        } catch (FileSystemException e) {
            log.warn("获取目标文件夹错误,desc=[{}]", desc, e);
            throw new TaotaoDocbookException("获取目标文件夹错误", e);
        }

    }
}
