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
        config.setDocbookFile("demo-docbook-tmpl.xml");
        config.setResultFile(new File("./target/demo/fo/demo-docbook-tmpl.fo").getAbsolutePath());
        config.setLanguage("zh-CN");

        assertDoesNotThrow(()->{
            docbookProcessor.process(config);
        });

    }
}
