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

package store.taotao.docbook.core.docbook;

import org.junit.jupiter.api.Test;
import store.taotao.docbook.core.DocbookProcessor;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SingleOutputProcessorTest {

    @Test
    void process() {
        DocbookProcessor docbookProcessor=new SingleOutputProcessor();
        SingleOutputProcessor.SingleOutputProcessorConfig config=new SingleOutputProcessor.SingleOutputProcessorConfig();
        config.setXsltDir("classpath://xslt/store/taotao");
        config.setXsltFile("fo.xsl");
        config.setDocbookDir("classpath://demo/base/zh-CN");
        config.setDocbookFile("demo-docbook-tmpl.xml");
        config.setResultFile(new File("./target/demo/fo/demo-docbook-tmpl.fo").getAbsolutePath());
        config.setLanguage("zh-CN");

        assertDoesNotThrow(()->{
            docbookProcessor.process(config);
        });

    }
}
