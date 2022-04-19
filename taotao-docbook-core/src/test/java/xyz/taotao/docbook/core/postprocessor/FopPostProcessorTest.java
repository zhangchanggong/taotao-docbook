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

package xyz.taotao.docbook.core.postprocessor;

import org.apache.commons.io.FileUtils;
import org.apache.commons.vfs2.AllFileSelector;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.fop.apps.MimeConstants;
import org.junit.jupiter.api.Test;
import xyz.taotao.docbook.core.util.VFSUtils;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FopPostProcessorTest {

    @Test
    void process() throws IOException {

        FopPostProcessor.FopPostConfig config=new FopPostProcessor.FopPostConfig();
        config.setLanguage("zh-CN");
        config.setFoDir("classpath://demo/fop");
        config.setFoFile("demo-docbook-tmpl.fo");
        config.setMimeType(MimeConstants.MIME_PDF);
        config.setOutDir(new File("./target/docbook/publish").getAbsolutePath());
//        config.setOutFile("ps/demo-docbook-tmpl.eps");
        config.setOutFile("pdf/demo-docbook-tmpl-book.pdf");
        config.setStagingDir(new File("./target/docbook/staging").getAbsolutePath());
        config.setTargetResolution(144f);


        FileObject src = VFSUtils.getResource("classpath://resource_root", null);
        File baseDir=new File("./target/docbook/staging/resource_root");
        FileUtils.forceMkdir(baseDir);
        FileObject dist = VFSUtils.getResource(baseDir.getAbsolutePath(), null);
        dist.copyFrom(src, new AllFileSelector());

        FileObject fonts = VFSUtils.getResource("classpath://fonts", null);
        FileObject fontsFolder = dist.getParent().resolveFile("fonts");
//        fontsFolder.createFolder();
        fontsFolder.copyFrom(fonts,new AllFileSelector());

        FopPostProcessor postProcessor=new FopPostProcessor();

        assertDoesNotThrow(()->{
            postProcessor.process(config);
        });


    }
}
