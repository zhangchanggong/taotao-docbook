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

package store.taotao.docbook.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;

import java.net.URI;

/**
 * 基于 commons-vfs 资源定位工具
 */
@Slf4j
public class VFSUtils {
    private VFSUtils() {
    }

    /**
     * 根据 href 和 base 获得资源的 FileObject 形式。
     *
     * @param href xml 中 include、import、document 的 href 属性
     * @param base xml 或 xsl 的资源位置 vfs url 的形式
     * @return href 对应的资源。
     * @throws FileSystemException vfs 主要包括获取 FileSystemManager 错误或者解析地址错误
     */
    public static FileObject getResource(String href, String base) throws FileSystemException {
        log.debug("------------------ getResource 开始 -----------------");
        log.debug("href=[{}]", href);
        log.debug("base=[{}]", base);
        if (StringUtils.isBlank(href)) {
            log.debug("------------------ getResource 结束 -----------------");
            return null;
        }
        FileSystemManager vfsm = VFS.getManager();
        if (StringUtils.isBlank(base) || StringUtils.contains(href, ":/") || StringUtils.startsWith(href, "/")) {
            log.info("uri 处理时 base 为空 或 href 是绝对地址，href=[{}]", href);
            log.debug("------------------ getResource 结束 -----------------");
            return vfsm.resolveFile(href);
        } else {
            FileObject baseFile = vfsm.resolveFile(base);
            if(baseFile.isFile()){
                baseFile=baseFile.getParent();
            }
            log.debug("baseFile=[{}]", baseFile);
            log.debug("------------------ getResource 结束 -----------------");
            return vfsm.resolveFile(baseFile, href);
        }
    }

    /**
     * 根据 URI 获取 FileObject
     * @param uri 统一资源定位符
     * @return 对应的 FileObject
     * @throws FileSystemException 发生内部错误
     */
    public static FileObject getResource(URI uri) throws FileSystemException{
        FileSystemManager fsm = VFS.getManager();
        return fsm.resolveFile(uri);
    }


}
