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

package store.taotao.docbook.core.fop;

import org.apache.commons.vfs2.FileObject;
import org.apache.xmlgraphics.io.Resource;
import org.apache.xmlgraphics.io.ResourceResolver;
import store.taotao.docbook.core.util.VFSUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * 基于 vfs 定义的资源处理器
 */
public class VFSResourceResolver implements ResourceResolver {
    @Override
    public Resource getResource(URI uri) throws IOException {
        FileObject resource = VFSUtils.getResource(uri);
        return new Resource(resource.getType().getName(),resource.getContent().getInputStream());
    }

    @Override
    public OutputStream getOutputStream(URI uri) throws IOException {
        return VFSUtils.getResource(uri).getContent().getOutputStream();
    }
}
