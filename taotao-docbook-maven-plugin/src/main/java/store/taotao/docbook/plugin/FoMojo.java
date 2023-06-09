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

package store.taotao.docbook.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.fop.apps.MimeConstants;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import store.taotao.docbook.core.TaotaoDocbookException;
import store.taotao.docbook.core.job.XslFoJob;

/**
 * 基于 xsl-fo 的实现
 */
@Mojo(name = "fo",defaultPhase = LifecyclePhase.PACKAGE)
@Slf4j
public class FoMojo extends AbstractDocBookMojo{
    /**
     * fop 的配置文件路径 vfs 格式
     */
    @Parameter(name = "fopConfigPath",defaultValue = "classpath://META-INF/fop-config.xml")
    private String fopConfigPath;
    /**
     * fop 特有的输出格式类型
     */
    @Parameter(name = "mimeType",defaultValue = MimeConstants.MIME_PDF,required = true)
    private String mimeType;
    /**
     * 源分辨率
     */
    @Parameter(defaultValue = "72",required = true)
    private float sourceResolution;
    /**
     * 目标分辨率
     */
    @Parameter(defaultValue = "72",required = true)
    private float targetResolution;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        log.info("-------------------- xsl-fo 转换开始----------------------");

        XslFoJob.XslFoJobContext context = new XslFoJob.XslFoJobContext();
        context.setFopConfigPath(fopConfigPath);
        context.setMimeType(mimeType);
        context.setSourceResolution(sourceResolution);
        context.setTargetResolution(targetResolution);
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
        XslFoJob job = new XslFoJob();
        try {
            job.process(context);
            log.info("-------------------- xsl-fo 转换结束----------------------");
        } catch (TaotaoDocbookException e) {
            log.info("-------------------- xsl-fo 转换失败----------------------",e);
            throw new MojoFailureException(e);
        }

    }
}
