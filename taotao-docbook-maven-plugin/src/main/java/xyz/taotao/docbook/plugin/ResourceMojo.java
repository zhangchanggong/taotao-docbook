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

package xyz.taotao.docbook.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import xyz.taotao.docbook.core.TaotaoDocbookException;
import xyz.taotao.docbook.core.job.ResourceJob;

/**
 *  复制资源的 Mojo
 */
@Mojo(name = "resourceCopy",defaultPhase = LifecyclePhase.PROCESS_RESOURCES)
@Execute(phase = LifecyclePhase.PROCESS_RESOURCES)
@Slf4j
public class ResourceMojo extends AbstractDocBookMojo{
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        log.info("-------------------- 资源复制开始 ----------------------");
        ResourceJob.ResourceContext context = new ResourceJob.ResourceContext();
        context.setWorkDir(workDir.getAbsolutePath());
        context.setResourcePaths(resourcePaths.toArray(new String[0]));
        context.setFontPaths(fontPaths.toArray(new String[0]));
        context.setDocxPaths(docxPaths.toArray(new String[0]));
        log.info("context=[{}]",context);
        ResourceJob job=new ResourceJob();
        try {
            job.process(context);
            log.info("-------------------- 资源复制完成 ----------------------");
        } catch (TaotaoDocbookException e) {
            log.info("-------------------- 资源复制失败----------------------",e);
            throw new MojoFailureException(e);
        }
    }
}
