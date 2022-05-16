/*
 * Copyright © 2022 王金涛。
 * This file is part of taotao-docbook.
 *
 * taotao-docbook is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * taotao-docbook is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with taotao-docbook. If
 * not, see <https://www.gnu.org/licenses/>.
 */

package xyz.taotao.docbook.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import xyz.taotao.docbook.core.TaotaoDocbookException;
import xyz.taotao.docbook.core.job.HtmlJob;

/**
 * html 生成的 实现
 */
@Mojo(name = "html",defaultPhase = LifecyclePhase.PACKAGE)
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
