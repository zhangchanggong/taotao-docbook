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
生成 word 的 numbering xml
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0"
                exclude-result-prefixes="xsl">
    <xsl:output method="xml" version="1.0"
                encoding="UTF-8" indent="yes" standalone="yes"/>
    <xsl:template match="/">
        <w:numbering xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
                     xmlns:o="urn:schemas-microsoft-com:office:office"
                     xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
                     xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math"
                     xmlns:v="urn:schemas-microsoft-com:vml"
                     xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing"
                     xmlns:w10="urn:schemas-microsoft-com:office:word"
                     xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
                     xmlns:wne="http://schemas.microsoft.com/office/word/2006/wordml"
                     >
        </w:numbering>
    </xsl:template>




</xsl:stylesheet>
