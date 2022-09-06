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

package store.taotao.docbook.core.postprocessor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.vfs2.AllFileSelector;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import store.taotao.docbook.core.ClassNameConfigKeyProcesser;
import store.taotao.docbook.core.PostProcessor;
import store.taotao.docbook.core.ProcessorConfig;
import store.taotao.docbook.core.TaotaoDocbookException;
import store.taotao.docbook.core.util.VFSUtils;

import java.io.IOException;

/**
 * 将无需处理的资源和xsl 处理后的产出物进一个路径下
 */
@Slf4j
public class MergeProcessor extends ClassNameConfigKeyProcesser<MergeProcessor.MergeConfig> implements PostProcessor<MergeProcessor.MergeConfig> {


    public MergeProcessor() {
        super(MergeConfig.class);
    }

    @Override
    public void doProcess(MergeConfig config) throws TaotaoDocbookException {
        FileObject descDir=null;
        try {
            descDir = VFSUtils.getResource(config.descPath,null);
            if (descDir.exists()){
                if (!descDir.isFolder()){
                    throw new TaotaoDocbookException("目标路径存在但不是文件夹");
                }
            }else{
                FileUtils.forceMkdir
                        (descDir.getPath().toFile());
            }
        } catch (FileSystemException e) {
            log.warn("获取目标文件夹失败,descPath=[{}]",config.descPath,e);
            throw new TaotaoDocbookException("获取目标文件夹失败",e);
        } catch (IOException e) {
            log.warn("创建目标文件夹失败,descPath=[{}]",config.descPath,e);
            throw new TaotaoDocbookException("创建目标文件夹失败",e);
        }

        for (String sourcePath:config.sourcePaths){
            try {
                FileObject sourceFile = VFSUtils.getResource(sourcePath, null);
                descDir.copyFrom(sourceFile,new AllFileSelector());
            } catch (FileSystemException e) {
                log.warn("sourcePath=[{}],处理失败",sourcePath);
            }
        }

    }

    @Getter
    @Setter
    public static class MergeConfig extends ProcessorConfig {
        /**
         * 需要聚合的源
         */
        private String[] sourcePaths;
        /**
         * 目标路径
         */
        private String descPath;
    }
}
