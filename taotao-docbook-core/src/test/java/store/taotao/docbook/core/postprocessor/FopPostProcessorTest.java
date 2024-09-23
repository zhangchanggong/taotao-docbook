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

package store.taotao.docbook.core.postprocessor;

import org.apache.commons.io.FileUtils;
import org.apache.commons.vfs2.AllFileSelector;
import org.apache.commons.vfs2.FileObject;
import org.apache.fop.apps.MimeConstants;
import org.junit.jupiter.api.Test;
import store.taotao.docbook.core.util.VFSUtils;

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
