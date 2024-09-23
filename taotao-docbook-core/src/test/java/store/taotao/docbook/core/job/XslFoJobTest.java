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

package store.taotao.docbook.core.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.fop.apps.MimeConstants;
import org.junit.jupiter.api.Test;
import store.taotao.docbook.core.TaotaoDocbookConstant;
import store.taotao.docbook.core.util.VFSUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Slf4j
class XslFoJobTest extends ClearAndResourceJobTest {
    @Test
    void process() throws FileSystemException {
        FileObject pwdFile = VFSUtils.getResource(new File(".").getAbsolutePath(), null);
        String pwdPath = pwdFile.getPublicURIString();
        log.debug("pwdPath=[{}]", pwdPath);
        XslFoJob.XslFoJobContext context = new XslFoJob.XslFoJobContext();
        context.setDescDir(pwdPath + "/target/docbook/" + TaotaoDocbookConstant.PUBLISH_DIR + "/pdf");
        context.setDescFile("demo-docbook-tmpl.pdf");
        context.setDocbookDir(pwdPath + "/src/test/resources/demo/base/zh-CN");
        context.setDocbookFile("demo-docbook-tmpl.xml");
        context.setFontPaths(new String[]{
                "classpath://fonts"
        });
        context.setLanguage("zh-CN");
        context.setResourcePaths(new String[]{
                "classpath://resource_root",
                "classpath://demo/style"
        });
        context.setWorkDir(pwdPath + "/target/docbook");
        context.setXsltDir("classpath://xslt/store/taotao");
        context.setXsltFile("fo.xsl");
        context.setMimeType(MimeConstants.MIME_PDF);
        context.setFopConfigPath("classpath://META-INF/fop-config.xml");


        XslFoJob job = new XslFoJob();
        assertDoesNotThrow(() ->
                        job.process(context)
                , "不应抛出任何异常");


    }

}
