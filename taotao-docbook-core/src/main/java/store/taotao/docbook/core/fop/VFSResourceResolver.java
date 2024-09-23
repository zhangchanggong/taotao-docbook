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
