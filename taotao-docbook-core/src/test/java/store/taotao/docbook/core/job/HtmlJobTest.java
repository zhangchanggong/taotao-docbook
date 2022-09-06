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

package store.taotao.docbook.core.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.junit.jupiter.api.Test;
import store.taotao.docbook.core.TaotaoDocbookConstant;
import store.taotao.docbook.core.util.VFSUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
class HtmlJobTest extends ClearAndResourceJobTest {
    @Test
    void process() throws FileSystemException {
        FileObject pwdFile = VFSUtils.getResource(new File(".").getAbsolutePath(), null);
        String pwdPath = pwdFile.getPublicURIString();
        log.debug("pwdPath=[{}]", pwdPath);
        HtmlJob.HtmlContext context=new HtmlJob.HtmlContext();
        context.setDescDir(pwdPath + "/target/docbook/" + TaotaoDocbookConstant.PUBLISH_DIR + "/html");
        context.setDescFile("demo-docbook-tmpl.html");
        context.setDocbookDir(pwdPath + "/src/test/resources/demo/base/zh-CN");
        context.setDocbookFile("demo-docbook-tmpl-book.xml");
        context.setFontPaths(new String[]{
                "classpath://fonts"
        });
        context.setLanguage("zh-CN");
        context.setResourcePaths(new String[]{
                "classpath://resource_root",
                "classpath://demo/style"
        });
        context.setWorkDir(pwdPath + "/target/docbook");
        context.setXsltDir("classpath://xslt/xyz/taotao");
        context.setXsltFile("xhtml.xsl");


        HtmlJob job=new HtmlJob();
        assertDoesNotThrow(() ->
                        job.process(context)
                , "不应抛出任何异常");

    }

}
