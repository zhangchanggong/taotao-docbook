package store.taotao.docbook.core.docbook;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLInputSource;
import store.taotao.docbook.core.util.VFSUtils;

import java.io.IOException;

@Slf4j
public class VFSEntityResolver implements XMLEntityResolver {
    @Override
    public XMLInputSource resolveEntity(XMLResourceIdentifier xmlResourceIdentifier) throws XNIException, IOException {

        log.debug("------------------ resolveEntity开始 -----------------");
        log.debug("xmlResourceIdentifier=[{}]", xmlResourceIdentifier);
        try {
            String href = xmlResourceIdentifier.getLiteralSystemId();
            String base = xmlResourceIdentifier.getBaseSystemId();
            log.debug("href=[{}]", href);
            log.debug("base=[{}]", base);
            XMLInputSource xmlInputSource = new XMLInputSource(xmlResourceIdentifier);
            VFSUtils.getResource(href, base);
            xmlInputSource.setByteStream(VFSUtils.getResource(href, base).getContent().getInputStream());
            return xmlInputSource;
        } catch (FileSystemException e) {
            log.warn("获取 source 异常");
            throw new XNIException("获取 source 异常", e);
        } finally {
            log.debug("------------------ resolveEntity 结束 -----------------");
        }

    }
}
