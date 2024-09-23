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

package store.taotao.docbook.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import store.taotao.docbook.core.TaotaoDocbookException;
import store.taotao.docbook.core.job.HtmlJob;

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
