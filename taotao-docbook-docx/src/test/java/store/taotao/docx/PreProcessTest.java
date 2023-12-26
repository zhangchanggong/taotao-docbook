package store.taotao.docx;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import store.taotao.docbook.core.DocbookProcessor;
import store.taotao.docbook.core.docbook.SingleOutputProcessor;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Slf4j
class PreProcessTest {

    @Test
    void pre() {
        DocbookProcessor docbookProcessor = new SingleOutputProcessor();
        SingleOutputProcessor.SingleOutputProcessorConfig config = new SingleOutputProcessor.SingleOutputProcessorConfig();
        config.setXsltDir("classpath://xslt/store/taotao/docx");
        config.setXsltFile("single.xsl");
        config.setDocbookDir("classpath://demo/base/zh-CN");
        config.setDocbookFile("demo-docbook-tmpl.xml");
        config.setResultFile(new File("./target/demo/xml/demo-docbook-tmpl.xml").getAbsolutePath());
        config.setLanguage("zh-CN");

        assertDoesNotThrow(() -> {
            docbookProcessor.process(config);
        });
    }
}
