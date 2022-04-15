package xyz.taotao.docbook.core.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.fop.apps.MimeConstants;
import org.junit.jupiter.api.Test;
import xyz.taotao.docbook.core.TaotaoDocbookConstant;
import xyz.taotao.docbook.core.util.VFSUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class XslFoJobTest {
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
        context.setXsltDir("classpath://xslt/xyz/taotao");
        context.setXsltFile("fo.xsl");
        context.setMimeType(MimeConstants.MIME_PDF);
        context.setFopConfigPath("classpath://META-INF/fop-config.xml");

        XslFoJob job = new XslFoJob();
        assertDoesNotThrow(() ->
                        job.process(context)
                , "不应抛出任何异常");


    }

}
