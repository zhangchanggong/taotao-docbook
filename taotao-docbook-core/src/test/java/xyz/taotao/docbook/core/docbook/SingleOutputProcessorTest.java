package xyz.taotao.docbook.core.docbook;

import org.junit.jupiter.api.Test;
import xyz.taotao.docbook.core.DocbookProcessor;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class SingleOutputProcessorTest {

    @Test
    void process() {
        DocbookProcessor docbookProcessor=new SingleOutputProcessor();
        SingleOutputProcessor.SingleOutputProcessorConfig config=new SingleOutputProcessor.SingleOutputProcessorConfig();
        config.setXsltDir("classpath://xslt/xyz/taotao");
        config.setXsltFile("fo.xsl");
        config.setDocbookDir("classpath://demo/base/zh-CN");
        config.setDocbookFile("demo-docbook-tmpl-book.xml");
        config.setResultFile(new File("./target/demo/fo/demo-docbook-tmpl.xml").getAbsolutePath());
        config.setLanguage("zh-CN");

        assertDoesNotThrow(()->{
            docbookProcessor.process(config);
        });

    }
}
