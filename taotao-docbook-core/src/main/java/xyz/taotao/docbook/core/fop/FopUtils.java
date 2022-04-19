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

package xyz.taotao.docbook.core.fop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.configuration.ConfigurationException;
import org.apache.fop.configuration.DefaultConfiguration;
import org.apache.fop.configuration.DefaultConfigurationBuilder;
import xyz.taotao.docbook.core.TaotaoDocbookException;
import xyz.taotao.docbook.core.util.VFSUtils;

import java.net.URI;

@Slf4j
public class FopUtils {
    private FopUtils() {
    }

    public static DefaultConfiguration getConfig(String path) throws TaotaoDocbookException {
        if (StringUtils.isBlank(path)) {
            path = "classpath:META-INF/fop-config.xml";
        }
        DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder();
        try {
            FileObject configFile = VFSUtils.getResource(path, null);
            return builder.build(configFile.getContent().getInputStream());
        } catch (FileSystemException e) {
            log.warn("生成 fop 默认配置错误", e);
            throw new TaotaoDocbookException("生成 fop 默认配置错误", e);
        } catch (ConfigurationException e) {
            log.warn("fop 默认配置解析错误", e);
            throw new TaotaoDocbookException("fop 默认配置解析错误", e);
        }
    }

    /**
     * 获取 FopFactoryBuilder
     *
     * @param stagingDir  临时地址
     * @param resourceDir 通用资源目录
     * @return FopFactoryBuilder
     * @throws TaotaoDocbookException 发生 vfs 异常
     */
    public static FopFactoryBuilder getFactoryBuilder(String stagingDir, String resourceDir) throws TaotaoDocbookException {
        try {
            FileObject baseDir = VFSUtils.getResource(resourceDir, stagingDir);
            FopFactoryBuilder builder = new FopFactoryBuilder(URI.create(baseDir.getPublicURIString()+"/"), new VFSResourceResolver());
            return builder;
        } catch (FileSystemException e) {
            log.warn("基于 vfs 获取资源错误,stagingDir=[{}],resourceDir=[{}]", stagingDir, resourceDir, e);
            throw new TaotaoDocbookException("基于 vfs 获取资源错误", e);
        }

    }
}
