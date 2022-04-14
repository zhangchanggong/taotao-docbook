package xyz.taotao.docbook.core.docbook;

import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileSystemManager;
import org.junit.jupiter.api.Test;
import xyz.taotao.docbook.core.DocbookProcessor;
import xyz.taotao.docbook.core.ProcessorConfig;

import java.io.File;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class SingleOutputProcessorTest {

    @Test
    void process() {

        try {
            URL.setURLStreamHandlerFactory(VFS.getManager().getURLStreamHandlerFactory());
        } catch (FileSystemException e) {
            throw new RuntimeException(e);
        }
        DocbookProcessor docbookProcessor=new SingleOutputProcessor();
        SingleOutputProcessor.SingleOutputProcessorConfig config=new SingleOutputProcessor.SingleOutputProcessorConfig();
        config.setXsltDir("classpath://xslt/xyz/taotao");
        config.setXsltFile("fo.xsl");
        config.setDocbookDir("classpath://demo/base/zh-CN");
        config.setDocbookFile("demo-docbook-tmpl-book.xml");
        config.setResultFile(new File("./target/demo/fo/demo-docbook-tmpl.xml").getAbsolutePath());

        assertDoesNotThrow(()->{
            docbookProcessor.process(config);
        });

    }
}
