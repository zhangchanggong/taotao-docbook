package xyz.taotao.docbook.core.fop;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.VFS;
import org.apache.xmlgraphics.io.Resource;
import org.apache.xmlgraphics.io.ResourceResolver;
import xyz.taotao.docbook.core.util.VFSUtils;

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
