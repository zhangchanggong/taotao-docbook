---
title: taotao-docbook
---

# 简介

由于 jdocbook 已经多年不更新，所以制作了其替代品。总之这是个 jdocbook 的替代品而不是复制品，本项目最大限度保持与 jdocbook 的兼容，但也有部分特性进行了变更。整体的构建目标如下：

1. 定义以 docbook 为核心文档转换流程。
1. 定义基于 maven 的文档工程结构。
1. 给出基于 maven 的转换插件。
1. 给出基于 commons-vfs 的资源定位方案。
1. 给出针对中文的 docbook-xslt 扩展。
1. 给出针对 ooxml 的 docbook-xslt 扩展

# 用法

在 1.0.1 的实现中给出了前五项的实现。第六项计划在 1.1.0 中实现。本文以 [demo-docbook](https://gitee.com/qq353586539/demo-docbook) 为例说明 maven-plugin 的用法。

## 目录结构

```
demo-docbook
 ├─src
 │  └─main
 │      ├─docbook                            # 放 docbook 的 xml 文件（文档本身）建议分语言地区存放，便于进行翻译工程
 │      │  └─zh-CN                           # 分语言地区的文件夹
 │      ├─docx                               # 生成 docx 用的资源文件，比如样式表，如果放在这个位置，说明这些资源仅用于支持本文档工程（当前不启用）
 │      ├─fonts                              # 字体文件，仅用于本文档工程的字体，用于 fop 渲染 pdf 时使用，需要另行编写和指定 fop 的配置（与 jdocbook 不同）
 │      ├─plantuml                           # 与 taotao-docbook 无关，在 demo 中用于基于 plantuml 生成图片，并进行测试
 │      ├─style                              # 仅用于本文档工程的样式与样式资源（主要是图片）
 │      └─xslt                               # 本文档工程扩展的 xslt 转换规则，即便是只使用 taotao-docbook 提供的转换规则，也建议像 demo-docbook 一样进行封装 
 └─target
     └─docbook                               # 工作目录, 插件工作过程中生成的文件和最终生成的文件都在这里
        ├─publish                            # 生成的最终制品文件夹
        │  ├─html                            # 生成的 html 的目录
        │  └─pdf                             # 生成的 pdf 文件的目录
        └─staging                            # 中间产物目录
            ├─docx                           # docx 的中间产物，散装的 ooxml
            ├─fo                             # fo 文件目录，用于后续进行 fop 渲染，与 jdocbook 不同，这里没删除
            ├─fonts                          # 汇总后的字体目录
            └─resource_root                  # 汇总后的资源目录（ docbook 中图片的引入路径的相对路径就是以这里为基础）
                ├─css                        # css 主要用于生成 html 后使用
                └─images                     # 图片，包括样式图片和内容图片
```

## 流程与 goals

插件的基本工作流程如下：

```
                    ┌───────┐     ┌─────────┐      ┌─────────┐
                    │ 清 理 ├────>│ 处理资源 ├─────>│ 生成文档 │
                    └───────┘     └─────────┘      └─────────┘
```

1. **清理** 该过程删除 _工作目录_ 中的所有文件。
1. **处理资源** 该过程负责汇总各种资源文件，包括但不限于字体、图片、样式等。
1. **生成文档** 该过程负责将 `docbook` 文件和资源文件生成最终文档制品。

对应上述三个过程，插件提供了以下 goals ：

|序号|goal|默认 phase|对应过程|说明 |
|:---:|:---:|:---------:|:-----:|----|
|1|clear|clean|**清理**||
|2|resourceCopy|process-resources|**处理资源**||
|3|fo|package|**生成文档**|基于 xsl-fo 技术生成文档|
|4|html|package|**生成文档**|生成 html 页面|

对于 **生成文档** 的 goal ，一般又对应三个子过程： **前置处理、核心处理过程、后置处理** 过程。以 `fo` 为例,其前置处理过程什么也没做，核心处理过程将 `docbook` 文件处理成一个 `fo` 文件,后置处理过程将 `fo` 文件处理成指定格式的目标文件（ **比如 pdf、rtf 等** ）。

### goal: clear

该过程删除 _工作目录_ 中的所有文件。以便于在一个相对干净的环境中生成文档制品。

| 序号  |参数|默认值|作用与说明|
|:---:|:---------:|:-----:|----|
|  1  |workDir|${basedir}/target/docbook|工作目录, 插件工作过程中生成的文件和最终生成的文件都在这里|

如上所示，`clear` 目标仅与 `workDir` 参数有关，其作用是删除该参数对应的目录下的文件。

直接执行如下命名，可以指定执行该目标：

```bash
mvn taotao-docbook:clear
```

### goal: resourceCopy

该目标是将各种**资源文件**汇聚到**中间产物目录**下。受文档工程化的影响，资源文件可能来源于不同位置，这就为渲染时资源文件的定位增加了困难。该过程将**资源文件**汇聚，提供单一的定位基址。

1. **资源文件** 是指生成文件前，其本身或内部信息无需人工编辑；生成文件时，其本身或内部信息需进入最终文档的文件，比如图片、字体、样式表、docx 的style 文件等。
1. **中间产物目录** 及 `workDir` 下的 `staging` 文件夹，在本项目中，其相对位置不予改变，其目录结构也不予改变。

该目标各参数如下：

| 序号 |参数|默认值|作用与说明|
|:---:|:---------:|:-----:|----|
|  1 |workDir|${basedir}/target/docbook|工作目录, 插件工作过程中生成的文件和最终生成的文件都在这里|
|  2 |resourcePaths|(classpath://resource_root,${basedir}/src/main/style)|提供通用资源的路径|
|  3 |fontPaths|(classpath://fonts,${basedir}/src/main/fonts)|提供字体的路径|
|  4 |docxPaths|(classpath://docx,${basedir}/src/main/docx)|提供 docx 资源的路径|

经作者努力，使上述配置中的 `resourcePaths`,`fontPaths`,`docxPaths` 都支持 [`commons-vfs2` 的路径](https://commons.apache.org/proper/commons-vfs/filesystems.html)。 其中 `classpath` ,就是 `commons-vfs2` 中的 `RES`.

其目标的本质是将多个文件夹下的文件（包括目录结构）复制到指定文件夹下，复制的对应关系如下：

|序号|源|目标|说明|
|:---:|:---------:|:-----:|----|
|1|${resourcePaths}|${workDir}/staging/resource_root|通用资源|
|2|${fontsPaths}|${workDir}/staging/fonts|fo用字体资源|
|3|${docxPaths}|${workDir}/staging/docx|docx用 xml 资源|

直接执行如下命名，可以指定执行该目标：

```bash
mvn taotao-docbook:resourceCopy
```




# 设计说明