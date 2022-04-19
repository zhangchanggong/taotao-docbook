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

package xyz.taotao.docbook.core;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 过程配置参量和运行上下文
 */
@Setter
@Getter
public class JobContext {

    /**
     * docbook 首文件所在的路径（不包括文件本身）
     * vfs 格式
     */
    private String docbookDir;
    /**
     * docbook 首文件文件名
     */
    private String docbookFile;
    /**
     * 工作目录
     */
    private String workDir;
    /**
     * 目标文件所在的路径（不包括文件本身）
     * vfs 格式
     */
    private String descDir;
    /**
     * 目标文件文件名
     */
    private String descFile;
    /**
     * 输出的参考语言
     */
    private String language;

    /**
     * 提供通用资源的路径
     * vfs 格式
     */
    private String[] resourcePaths;
    /**
     * 提供字体的路径
     * vfs 格式
     */
    private String[] fontPaths;
    /**
     * 提供docx 西苑的路径
     */
    private String[] docxPaths;

    /**
     * xslt 首文件所在的路径（不包括文件本身）
     * vfs 格式
     */
    private String xsltDir;
    /**
     * xslt 首文件所在的路径，如果是绝对路径，则 xsltDirPath 不生效
     * vfs 格式
     */
    private String xsltFile;

    /**
     * 预处理器配置
     */
    private Map<String, ProcessorConfig> preProcessorConfigs;
    /**
     * 后处理器配置
     */
    private Map<String, ProcessorConfig> postProcessorConfigs;
    /**
     * docbook 处理过程的配置
     */
    private ProcessorConfig docbookProcessorConfig;
}
