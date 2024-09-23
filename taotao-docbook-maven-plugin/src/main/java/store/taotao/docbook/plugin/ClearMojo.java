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
import store.taotao.docbook.core.job.ClearJob;

/**
 *  环境清理 Mojo
 */
@Mojo(name = "clear",defaultPhase = LifecyclePhase.CLEAN)
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
