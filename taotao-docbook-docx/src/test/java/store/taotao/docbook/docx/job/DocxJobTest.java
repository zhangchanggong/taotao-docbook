package store.taotao.docbook.docx.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.fop.apps.MimeConstants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import store.taotao.docbook.core.TaotaoDocbookConstant;
import store.taotao.docbook.core.TaotaoDocbookException;
import store.taotao.docbook.core.job.ClearJob;
import store.taotao.docbook.core.job.ResourceJob;
import store.taotao.docbook.core.util.VFSUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class DocxJobDriver {

    @BeforeAll
    static void cleanAndCopy() throws FileSystemException {
        FileObject pwdFile = VFSUtils.getResource(new File(".").getAbsolutePath(), null);
        String pwdPath = pwdFile.getPublicURIString();
        ClearJob.ClearContext clearContext = new ClearJob.ClearContext();
        clearContext.setWorkDir(pwdPath + "/target/docbook");

        ResourceJob.ResourceContext resourceContext = new ResourceJob.ResourceContext();
        resourceContext.setWorkDir(pwdPath + "/target/docbook");
        resourceContext.setResourcePaths(new String[]{
                "classpath://resource_root"
        });
        resourceContext.setFontPaths(new String[]{
                "classpath://fonts"
        });
        resourceContext.setDocxPaths(new String[]{"classpath://docx"});

        ClearJob clearJob = new ClearJob();
        ResourceJob resourceJob = new ResourceJob();
        assertDoesNotThrow(() -> {
                    clearJob.process(clearContext);
                    resourceJob.process(resourceContext);
                }
                , "不应抛出任何异常");
    }


    @Test
    void process() throws TaotaoDocbookException, FileSystemException {
        FileObject pwdFile = VFSUtils.getResource(new File(".").getAbsolutePath(), null);
        String pwdPath = pwdFile.getPublicURIString();
        log.debug("pwdPath=[{}]", pwdPath);

        DocxJob.DocxJobContext context=new DocxJob.DocxJobContext();
        context.setDescDir(pwdPath + "/target/docbook/" + TaotaoDocbookConstant.PUBLISH_DIR + "/docx");
        context.setDescFile("demo-docbook-tmpl.docx");
        context.setDocbookDir(pwdPath + "/src/test/resources/demo/base/zh-CN");
        context.setDocbookFile("demo-docbook-tmpl.xml");
        context.setFontPaths(new String[]{
                "classpath://fonts"
        });
        context.setLanguage("zh-CN");
        context.setResourcePaths(new String[]{
                "classpath://resource_root",
                "classpath://docx"
        });
        context.setWorkDir(pwdPath + "/target/docbook");
        context.setXsltDir("classpath://xslt/store/taotao/docx");
        context.setXsltFile("document.xsl");
        context.setSingleFile("demo-docbook-tmpl.xml");
        context.setSingleXslt("single.xsl");
        context.setNumberingXslt("numbering.xsl");
        context.setFootnotesXslt("footnotes.xsl");
        context.setImageRelXslt("image.xsl");

        DocxJob job=new DocxJob();
        assertDoesNotThrow(() ->
                        job.process(context)
                , "不应抛出任何异常");
    }

}