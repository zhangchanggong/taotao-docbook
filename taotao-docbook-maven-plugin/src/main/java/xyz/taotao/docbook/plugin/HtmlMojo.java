package xyz.taotao.docbook.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import xyz.taotao.docbook.core.TaotaoDocbookException;
import xyz.taotao.docbook.core.job.HtmlJob;

/**
 * html 生成的 实现
 */
@Mojo(name = "html")
@Slf4j
public class HtmlMojo extends AbstractDocBookMojo{
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        log.info("-------------------- html 转换开始----------------------");
        HtmlJob.HtmlContext context=new HtmlJob.HtmlContext();
        context.setXsltFile(xsltFile);
        context.setXsltDir(xsltDir);
        context.setLanguage(language);
        context.setWorkDir(workDir.getAbsolutePath());
        context.setDocbookFile(docbookFile);
        context.setDocbookDir(docbookDir+"/"+language);
        context.setDescFile(descFile);
        context.setDescDir(descDir.getAbsolutePath());
        context.setResourcePaths(resourcePaths.toArray(new String[0]));
        context.setFontPaths(fontPaths.toArray(new String[0]));
        context.setDocxPaths(docxPaths.toArray(new String[0]));
        log.info("context=[{}]",context);
        HtmlJob job=new HtmlJob();
        try {
            job.process(context);
            log.info("-------------------- html 转换结束----------------------");
        } catch (TaotaoDocbookException e) {
            log.info("-------------------- html 转换失败----------------------",e);
            throw new MojoFailureException(e);
        }


    }
}
