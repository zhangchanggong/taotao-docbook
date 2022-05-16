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
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import xyz.taotao.docbook.core.TaotaoDocbookException;
import xyz.taotao.docbook.core.job.ClearJob;

/**
 *  环境清理 Mojo
 */
@Mojo(name = "clear",defaultPhase = LifecyclePhase.POST_CLEAN)
@Slf4j
public class ClearMojo extends AbstractDocBookMojo{
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        log.info("-------------------- 清理环境开始 ----------------------");
        ClearJob.ClearContext context=new ClearJob.ClearContext();
        context.setWorkDir(workDir.getAbsolutePath());

        log.info("context=[{}]",context);


        ClearJob job=new ClearJob();

        try {
            job.process(context);
            log.info("-------------------- 清理环境完成 ----------------------");
        } catch (TaotaoDocbookException e) {
            log.info("-------------------- 清理环境失败 ----------------------",e);
            throw new MojoFailureException(e);
        }
    }
}
