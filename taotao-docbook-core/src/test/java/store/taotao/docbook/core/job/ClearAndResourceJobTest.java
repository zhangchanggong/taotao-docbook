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

package store.taotao.docbook.core.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.junit.jupiter.api.BeforeAll;
import store.taotao.docbook.core.util.VFSUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Slf4j
class ClearAndResourceJobTest {
    @BeforeAll
    static void preprocess() throws FileSystemException {
        FileObject pwdFile = VFSUtils.getResource(new File(".").getAbsolutePath(), null);
        String pwdPath = pwdFile.getPublicURIString();
        ClearJob.ClearContext clearContext = new ClearJob.ClearContext();
        clearContext.setWorkDir(pwdPath + "/target/docbook");

        ResourceJob.ResourceContext resourceContext = new ResourceJob.ResourceContext();
        resourceContext.setWorkDir(pwdPath + "/target/docbook");
        resourceContext.setResourcePaths(new String[]{
                "classpath://resource_root",
                "classpath://demo/style"
        });
        resourceContext.setFontPaths(new String[]{
                "classpath://fonts"
        });

        ClearJob clearJob = new ClearJob();
        ResourceJob resourceJob = new ResourceJob();
        assertDoesNotThrow(() -> {
                    clearJob.process(clearContext);
                    resourceJob.process(resourceContext);
                }
                , "不应抛出任何异常");


    }
}
