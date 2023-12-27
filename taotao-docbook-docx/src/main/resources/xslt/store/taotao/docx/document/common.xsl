<?xml version='1.0' encoding='utf-8'?>
<!--
  ~ Copyright © 2022 王金涛。
  ~ This file is part of taotao-docbook.
  ~
  ~ taotao-docbook is free software: you can redistribute it and/or modify it under the terms of the
  ~ GNU Lesser General Public License as published by the Free Software Foundation, either
  ~ version 3 of the License, or (at your option) any later version.
  ~
  ~ taotao-docbook is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
  ~ without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  ~ PURPOSE. See the GNU Lesser General Public License for more details.
  ~
  ~ You should have received a copy of the GNU Lesser General Public License along with taotao-docbook. If
  ~ not, see <https://www.gnu.org/licenses/>.
  -->

<!--
生成 word 的同用处理逻辑部分
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0"

                xmlns:docbook="http://docbook.org/ns/docbook"
                exclude-result-prefixes="xsl">
    <!-- xml 的输出格式 -->
    <xsl:output method="xml" version="1.0"
                encoding="UTF-8" indent="yes" standalone="yes"/>
    <!-- 文档类型 -->
    <xsl:variable name="docbook.type" select="local-name(/*)"/>


</xsl:stylesheet>