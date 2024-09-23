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


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import store.taotao.docbook.core.TaotaoDocbookConstant;

import java.io.File;
import java.util.List;


/**
 * 抽象的 Docbook 定义,主要是定义了各个参数
 */
public abstract class AbstractDocBookMojo extends AbstractMojo {
    /**
     * docbook 首文件所在的路径（不包括文件本身）,默认是工程的 src/main/docbook doc 目录
     */
    @Parameter(name = "docbookDir", defaultValue = "${basedir}/src/main/docbook", required = true)
    protected String docbookDir;
    /**
     * docbook 首文件文件名
     */
    @Parameter(name = "docbookFile", required = true)
    protected String docbookFile;
    /**
     * 工作目录, 插件工作过程中生成的文件和最终生成的文件都在这里
     */
    @Parameter(name = "workDir", defaultValue = "${basedir}/target/docbook", required = true)
    protected File workDir;
    /**
     * 目标文件所在的路径（不包括文件本身）
     */
    @Parameter(name = "descDir", defaultValue = "${basedir}/target/docbook/publish", required = true)
    protected File descDir;
    /**
     * 目标文件文件名
     */
    @Parameter(name = "descFile", required = false)
    protected String descFile;
    /**
     * 输出的参考语言,格式为 ll-CC
     */
    @Parameter(name = "language", defaultValue = "zh-CN", required = true)
    protected String language;
    /**
     * 提供通用资源的路径
     * vfs 格式
     */
    @Parameter(name = "resourcePaths", defaultValue =
            "classpath://" + TaotaoDocbookConstant.RESOURCE_DIR+
            ",${basedir}/src/main/style",required = true)
    protected List<String> resourcePaths;
    /**
     * 提供字体的路径
     * vfs 格式
     */
    @Parameter(name = "fontPaths", defaultValue =
            "classpath://" + TaotaoDocbookConstant.FONTS_DIR +
                    ",${basedir}/src/main/" + TaotaoDocbookConstant.FONTS_DIR, required = true)
    protected List<String> fontPaths;
    /**
     * 提供 docx 资源的路径
     */
    @Parameter(name = "docxPaths", defaultValue = "classpath://" + TaotaoDocbookConstant.DOCX_DIR + ",${basedir}/src/main/" + TaotaoDocbookConstant.DOCX_DIR, required = true)
    protected List<String> docxPaths;

    /**
     * xslt 首文件所在的路径（不包括文件本身）
     * vfs 格式
     */
    @Parameter(name = "xsltDir", required = true, defaultValue = "classpath://xslt/store/taotao")
    protected String xsltDir;
    /**
     * xslt 首文件所在的路径
     * vfs 格式
     */
    @Parameter(name = "xsltFile", required = false)
    protected String xsltFile;

}
