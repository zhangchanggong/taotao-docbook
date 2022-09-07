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
= 生成pdf用的xsl模版


本模板主要定义了fo变量默认值

各变量含义的参考《DocBook XSL Stylesheets: Reference Documentation》的 "FO Parameter Reference" 节
-->
<xsl:stylesheet  version="1.0"
        xmlns:d="http://docbook.org/ns/docbook"
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:xslthl="http://xslthl.sf.net"
		xmlns:fo="http://www.w3.org/1999/XSL/Format"
		exclude-result-prefixes="xslthl fo xsl">

	<xsl:import href="classpath:docbook/fo/docbook.xsl" />
	<xsl:import href="classpath:docbook/fo/highlight.xsl" />
	<xsl:import href="common.xsl" />
	<!-- 扩展的高亮配置 -->
	<xsl:import href="highlight.xsl" />

	<!--  Pagination and General Styles start -->
	<!--
		页面与一般样式
	-->
	<!-- 段落间隔 -->
	<xsl:attribute-set name="normal.para.spacing">
		<!-- 段落缩进2em -->
		<xsl:attribute name="text-indent">2em</xsl:attribute>
	</xsl:attribute-set>
	<!-- 题注缩进为 0 -->
	<xsl:attribute-set name="formal.title.properties">
		<!-- 段落缩进 0 -->
		<xsl:attribute name="text-indent">0</xsl:attribute>
	</xsl:attribute-set>
	<!-- 行高 -->
	<xsl:param name="line-height" select="1.5" />
	<!-- 纸张类型,默认A4,就是210mm宽 -->
	<xsl:param name="paper.type">A4</xsl:param>
	<!-- 双面打印 -->
	<xsl:param name="double.sided" select="1"/>
	<!-- 空白页有页眉 -->
	<xsl:param name="headers.on.blank.pages" select="1"/>
	<!-- 空白页有页脚 -->
	<xsl:param name="footers.on.blank.pages" select="1"/>
	<!-- 页眉三列的比例是1:2:1 -->
	<xsl:param name="header.column.widths">1 2 1</xsl:param>
	<!-- 页脚三列的比例是1:2:1 -->
	<xsl:param name="footer.column.widths">1 2 1</xsl:param>
	<!-- 页上边距15毫米 -->
	<xsl:param name="page.margin.top">15mm</xsl:param>
	<!-- 页眉高10毫米 -->
	<xsl:param name="region.before.extent">10mm</xsl:param>
	<!-- 正文到打印区域上边缘15毫米 -->
	<xsl:param name="body.margin.top">15mm</xsl:param>
	<!-- 正文到打印区域下边缘15毫米 -->
	<xsl:param name="body.margin.bottom">15mm</xsl:param>
	<!-- 页脚高10毫米 -->
	<xsl:param name="region.after.extent">10mm</xsl:param>
	<!-- 页下边距15毫米 -->
	<xsl:param name="page.margin.bottom">15mm</xsl:param>
	<!-- (左装订)非装订侧页宽30毫米 -->
	<xsl:param name="page.margin.outer">
		<xsl:choose>
			<xsl:when test="$double.sided != 0">25mm</xsl:when>
			<xsl:otherwise>30mm</xsl:otherwise>
		</xsl:choose></xsl:param>
	<!-- (左装订)装订侧页宽30毫米 -->
	<xsl:param name="page.margin.inner">
		<xsl:choose>
			<xsl:when test="$double.sided != 0">35mm</xsl:when>
			<xsl:otherwise>30mm</xsl:otherwise>
		</xsl:choose>
	</xsl:param>
	<!-- 页眉页脚字体大小 -->
	<xsl:param name="footnote.font.size">
		<xsl:value-of select="$body.font.master * 0.8" />
		<xsl:text>pt</xsl:text>
	</xsl:param>
	<!-- 主体不再缩进 -->
	<xsl:param name="body.start.indent">0pt</xsl:param>
	<!--  Pagination and General Styles end -->

	<!-- Processor Extensions begin -->
	<!--
		xslt 处理器扩展
	-->
	<!-- 使用fop 0.20.5 之后的扩展 -->
	<xsl:param name="fop.extensions" select="0" />
	<!-- 使用fop 0.90 之后的扩展 -->
	<xsl:param name="fop1.extensions" select="1" />
	<!--  Processor Extensions end  -->

	<!-- Miscellaneous begin -->
	<xsl:param name="ulink.hyphenate" select="1"/>
	<!--  Miscellaneous end  -->

	<!-- Admonitions begin -->
	<!--
        强调性标注，各变量含义见参考章节的 "Admonitions" 小节
    -->
	<!-- 使用svg版的图像作为强调性标注标签 -->
	<xsl:param name="admon.graphics.extension">.svg</xsl:param>
	<!-- 强调性标注标题样式 -->
	<xsl:attribute-set name="admonition.title.properties">
		<xsl:attribute name="font-size">14pt</xsl:attribute>
		<xsl:attribute name="color">
			<xsl:choose>
				<xsl:when test="self::d:note">#4C5253</xsl:when>
				<xsl:when test="self::d:caution">#533500</xsl:when>
				<xsl:when test="self::d:important">white</xsl:when>
				<xsl:when test="self::d:warning">white</xsl:when>
				<xsl:when test="self::d:tip">white</xsl:when>
				<xsl:otherwise>white</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="hyphenate">false</xsl:attribute>
		<xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
	</xsl:attribute-set>
	<!-- 强调性标注主体样式 -->
	<xsl:attribute-set name="graphical.admonition.properties">
		<xsl:attribute name="color">
			<xsl:choose>
				<xsl:when test="self::d:note">#4C5253</xsl:when>
				<xsl:when test="self::d:caution">#533500</xsl:when>
				<xsl:when test="self::d:important">white</xsl:when>
				<xsl:when test="self::d:warning">white</xsl:when>
				<xsl:when test="self::d:tip">white</xsl:when>
				<xsl:otherwise>white</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
		<xsl:attribute name="background-color">
			<xsl:choose>
				<xsl:when test="self::d:note">#B5BCBD</xsl:when>
				<xsl:when test="self::d:caution">#E3A835</xsl:when>
				<xsl:when test="self::d:important">#4A5D75</xsl:when>
				<xsl:when test="self::d:warning">#7B1E1E</xsl:when>
				<xsl:when test="self::d:tip">#7E917F</xsl:when>
				<xsl:otherwise>#404040</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
		<xsl:attribute name="space-before.optimum">1em</xsl:attribute>
		<xsl:attribute name="space-before.minimum">0.8em</xsl:attribute>
		<xsl:attribute name="space-before.maximum">1.2em</xsl:attribute>
		<xsl:attribute name="space-after.optimum">1em</xsl:attribute>
		<xsl:attribute name="space-after.minimum">0.8em</xsl:attribute>
		<xsl:attribute name="space-after.maximum">1em</xsl:attribute>
		<xsl:attribute name="padding-bottom">12pt</xsl:attribute>
		<xsl:attribute name="padding-top">12pt</xsl:attribute>
		<xsl:attribute name="padding-right">12pt</xsl:attribute>
		<xsl:attribute name="padding-left">12pt</xsl:attribute>
		<xsl:attribute name="margin-left">
			<xsl:value-of select="$title.margin.left" />
		</xsl:attribute>
	</xsl:attribute-set>
	<!-- Admonitions end   -->

	<!-- Callout begin -->
	<!--
     序号标注扩展
     -->
	<!-- 行级序号标注所在的位置 -->
	<xsl:param name="callout.defaultcolumn" select="80"/>
	<!-- 序号标注的扩展 -->
	<xsl:param name="callout.icon.size">10pt</xsl:param>
	<!-- Callout end   -->

	<!-- ToC/LoT/Index Generation begin -->
	<!--
        ToC: 内容表，就是目录
        LoT: 不是很清楚
        Index: 索引，基于关键词的内容索引
        该部分为上述三者生成的公共配置
    -->
	<!-- 默认使用3级深度的目录 -->
	<xsl:param name="toc.section.depth" select="3"/>

	<!-- 规定了目录的位置和形式 -->
<!--	<xsl:param name="generate.toc">-->
<!--		set toc-->
<!--		book toc-->
<!--		article toc-->
<!--	</xsl:param>-->
	<!--  ToC/LoT/Index Generation end  -->

	<!-- Graphics begin -->
	<!--
		图形图像的相关参数
	-->
	<!-- 图像的默认宽度为17.4cm -->
	<xsl:param name="default.image.width">17.4cm</xsl:param>
	<!--  Graphics end  -->

	<!-- Cross References begin -->
	<!--
		交叉引用的相关参数
	-->
	<!-- 交叉引用文本样式 -->
	<xsl:attribute-set name="xref.properties">
		<xsl:attribute name="font-style">italic</xsl:attribute>
		<xsl:attribute name="color">
			<xsl:choose>
				<xsl:when
						test="ancestor::d:note or ancestor::d:caution or ancestor::d:important or ancestor::d:warning or ancestor::d:tip">
					<xsl:text>#aee6ff</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>#0066cc</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
	</xsl:attribute-set>
	<!--  Cross References end  -->

	<!-- Property Sets begin -->
	<!-- 通用属性集 -->
	<!-- 等宽字体输出基本样式 -->
	<xsl:attribute-set name="monospace.properties">
		<xsl:attribute name="font-size">8pt</xsl:attribute>
		<xsl:attribute name="font-family">
			<xsl:value-of select="$monospace.font.family" />
		</xsl:attribute>
	</xsl:attribute-set>
	<!-- 等宽字体逐字输出基本样式 -->
	<xsl:attribute-set name="monospace.verbatim.properties"
					   use-attribute-sets="verbatim.properties monospace.properties">
		<xsl:attribute name="text-align">start</xsl:attribute>
		<xsl:attribute name="wrap-option">wrap</xsl:attribute>
		<xsl:attribute name="hyphenation-character">&#x25BA;</xsl:attribute>
	</xsl:attribute-set>
	<!-- 逐字输出基本样式(主要是启用了允许折行) -->
	<xsl:attribute-set name="verbatim.properties">
		<xsl:attribute name="space-before.minimum">0.8em</xsl:attribute>
		<xsl:attribute name="space-before.optimum">1em</xsl:attribute>
		<xsl:attribute name="space-before.maximum">1.2em</xsl:attribute>
		<xsl:attribute name="space-after.minimum">0.8em</xsl:attribute>
		<xsl:attribute name="space-after.optimum">1em</xsl:attribute>
		<xsl:attribute name="space-after.maximum">1.2em</xsl:attribute>
		<xsl:attribute name="hyphenate">false</xsl:attribute>
		<xsl:attribute name="wrap-option">wrap</xsl:attribute>
		<xsl:attribute name="white-space-collapse">false</xsl:attribute>
		<xsl:attribute name="white-space-treatment">preserve</xsl:attribute>
		<xsl:attribute name="linefeed-treatment">preserve</xsl:attribute>
		<xsl:attribute name="text-align">start</xsl:attribute>
	</xsl:attribute-set>
	<!-- 一级节标题使用1.6倍正文 -->
	<xsl:attribute-set name="section.title.level1.properties">
		<xsl:attribute name="color"><xsl:value-of select="$section.title.color" /></xsl:attribute>
		<xsl:attribute name="font-size">
			<xsl:value-of select="$body.font.master * 1.6" />
			<xsl:text>pt</xsl:text>
		</xsl:attribute>
	</xsl:attribute-set>
	<!-- 二级节标题使用1.4倍正文 -->
	<xsl:attribute-set name="section.title.level2.properties">
		<xsl:attribute name="color"><xsl:value-of select="$section.title.color" /></xsl:attribute>
		<xsl:attribute name="font-size">
			<xsl:value-of select="$body.font.master * 1.4" />
			<xsl:text>pt</xsl:text>
		</xsl:attribute>
	</xsl:attribute-set>
	<!-- 三级节标题使用1.3倍正文 -->
	<xsl:attribute-set name="section.title.level3.properties">
		<xsl:attribute name="color"><xsl:value-of select="$section.title.color" /></xsl:attribute>
		<xsl:attribute name="font-size">
			<xsl:value-of select="$body.font.master * 1.3" />
			<xsl:text>pt</xsl:text>
		</xsl:attribute>
	</xsl:attribute-set>
	<!-- 四级节标题使用1.2倍正文 -->
	<xsl:attribute-set name="section.title.level4.properties">
		<xsl:attribute name="color"><xsl:value-of select="$section.title.color" /></xsl:attribute>
		<xsl:attribute name="font-size">
			<xsl:value-of select="$body.font.master * 1.2" />
			<xsl:text>pt</xsl:text>
		</xsl:attribute>
	</xsl:attribute-set>
	<!-- 五级节标题使用1.1倍正文 -->
	<xsl:attribute-set name="section.title.level5.properties">
		<xsl:attribute name="color"><xsl:value-of select="$section.title.color" /></xsl:attribute>
		<xsl:attribute name="font-size">
			<xsl:value-of select="$body.font.master * 1.1" />
			<xsl:text>pt</xsl:text>
		</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="section.title.level6.properties">
		<xsl:attribute name="color"><xsl:value-of select="$section.title.color" /></xsl:attribute>
		<xsl:attribute name="font-size">
			<xsl:value-of select="$body.font.master" />
			<xsl:text>pt</xsl:text>
		</xsl:attribute>
	</xsl:attribute-set>
	<!-- 章节标题基本样式 -->
	<xsl:attribute-set name="component.title.properties">
		<xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
		<xsl:attribute name="space-before.optimum">
			<xsl:value-of select="concat($body.font.master, 'pt')" />
		</xsl:attribute>
		<xsl:attribute name="space-before.minimum">
			<xsl:value-of select="concat($body.font.master, 'pt')" />
		</xsl:attribute>
		<xsl:attribute name="space-before.maximum">
			<xsl:value-of select="concat($body.font.master, 'pt')" />
		</xsl:attribute>
		<xsl:attribute name="hyphenate">false</xsl:attribute>
		<xsl:attribute name="color">
			<xsl:choose>
				<xsl:when test="not(parent::d:chapter | parent::d:article | parent::d:appendix)">
					<xsl:value-of select="$title.color" />
				</xsl:when>
				<xsl:otherwise>inherit</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
		<xsl:attribute name="text-align">
			<xsl:choose>
				<xsl:when test="((parent::d:article | parent::d:articleinfo) and not(ancestor::d:book) and not(self::d:bibliography)) or (parent::d:slides | parent::d:slidesinfo)">center</xsl:when>
				<xsl:otherwise>start</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
		<xsl:attribute name="start-indent">
			<xsl:value-of select="$title.margin.left" />
		</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="formal.object.properties">
		<xsl:attribute name="keep-together.within-column">auto</xsl:attribute>
	</xsl:attribute-set>
	<!-- 题注的标题位置 -->
	<xsl:param name="formal.title.placement">
		figure after
		example after
		equation after
		table after
		procedure after
	</xsl:param>
	<!-- 文档修订历史表格 start -->
	<xsl:attribute-set name="revhistory.table.properties">
		<xsl:attribute name="border-before-width.conditionality">retain</xsl:attribute>
		<xsl:attribute name="border-collapse">collapse</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="revhistory.table.header.cell.properties">
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="background-color">#4a5d75</xsl:attribute>
		<xsl:attribute name="color">white</xsl:attribute>
	</xsl:attribute-set>
	<xsl:attribute-set name="section.title.properties">
		<xsl:attribute name="font-family">
			<xsl:value-of select="$title.font.family"/>
		</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<!-- font size is calculated dynamically by section.heading template -->
		<xsl:attribute name="keep-with-next.within-column">always</xsl:attribute>
		<xsl:attribute name="space-before.minimum">0.8em</xsl:attribute>
		<xsl:attribute name="space-before.optimum">1.0em</xsl:attribute>
		<xsl:attribute name="space-before.maximum">1.2em</xsl:attribute>
		<xsl:attribute name="text-align">left</xsl:attribute>
		<xsl:attribute name="start-indent"><xsl:value-of select="$title.margin.left"/></xsl:attribute>
	</xsl:attribute-set>
	<!-- 文档修订历史表格 end -->
	<!--  Property Sets end  -->

	<!-- Miscellaneous begin -->
	<!--
	混合模式输出
	-->
	<!-- 是否启用逐字输出样式 -->
	<xsl:param name="shade.verbatim" select="1" />
	<!-- 逐字输出样式 -->
	<xsl:attribute-set name="shade.verbatim.style">
		<xsl:attribute name="wrap-option">wrap</xsl:attribute>
		<xsl:attribute name="background-color">
			<xsl:choose>
				<xsl:when test="ancestor::d:note"> <xsl:text>#B5BCBD</xsl:text> </xsl:when>
				<xsl:when test="ancestor::d:caution"> <xsl:text>#E3A835</xsl:text> </xsl:when>
				<xsl:when test="ancestor::d:important"> <xsl:text>#4A5D75</xsl:text> </xsl:when>
				<xsl:when test="ancestor::d:warning"> <xsl:text>#7B1E1E</xsl:text> </xsl:when>
				<xsl:when test="ancestor::d:tip"> <xsl:text>#7E917F</xsl:text> </xsl:when>
				<xsl:otherwise>
					<xsl:text>#F6F6F6</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
		<xsl:attribute name="color">
			<xsl:choose>
				<xsl:when test="ancestor::d:note"> <xsl:text>#4C5253</xsl:text> </xsl:when>
				<xsl:when test="ancestor::d:caution"> <xsl:text>#533500</xsl:text> </xsl:when>
				<xsl:when test="ancestor::d:important"> <xsl:text>white</xsl:text> </xsl:when>
				<xsl:when test="ancestor::d:warning"> <xsl:text>white</xsl:text> </xsl:when>
				<xsl:when test="ancestor::d:tip"> <xsl:text>white</xsl:text> </xsl:when>
				<xsl:otherwise>
					<xsl:text>#000000</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
		<xsl:attribute name="padding-left">12pt</xsl:attribute>
		<xsl:attribute name="padding-right">12pt</xsl:attribute>
		<xsl:attribute name="padding-top">6pt</xsl:attribute>
		<xsl:attribute name="padding-bottom">6pt</xsl:attribute>
		<xsl:attribute name="margin-left">
			<xsl:value-of select="$title.margin.left" />
		</xsl:attribute>
	</xsl:attribute-set>
	<!-- 将变量列表处理为阻塞块 -->
	<xsl:param name="variablelist.as.blocks" select="1"/>
	<!-- 页码数字格式 -->
	<xsl:param name="footnote.number.format">1</xsl:param>
	<!--  Miscellaneous end  -->

	<!-- Lists begin -->
	<!-- 列表间距 -->
	<xsl:attribute-set name="list.block.spacing">
		<xsl:attribute name="space-before.optimum">2em</xsl:attribute>
		<xsl:attribute name="space-before.minimum">1em</xsl:attribute>
		<xsl:attribute name="space-before.maximum">3em</xsl:attribute>
		<xsl:attribute name="space-after.optimum">0.1em</xsl:attribute>
		<xsl:attribute name="space-after.minimum">0.1em</xsl:attribute>
		<xsl:attribute name="space-after.maximum">0.1em</xsl:attribute>
	</xsl:attribute-set>
	<!--  Lists end  -->

	<!-- Tables begin -->
	<!-- 表格单元格内衬 -->
	<xsl:attribute-set name="table.cell.padding">
		<xsl:attribute name="padding-left">4pt</xsl:attribute>
		<xsl:attribute name="padding-right">4pt</xsl:attribute>
		<xsl:attribute name="padding-top">2pt</xsl:attribute>
		<xsl:attribute name="padding-bottom">2pt</xsl:attribute>
	</xsl:attribute-set>
	<!--
	表格的线宽和颜色
	frame: 外框
	cell: 内框
	-->
	<xsl:param name="table.frame.border.thickness">0.3pt</xsl:param>
	<xsl:param name="table.frame.border.color">#5c5c4f</xsl:param>
	<xsl:param name="table.cell.border.thickness">0.15pt</xsl:param>
	<xsl:param name="table.cell.border.color">#5c5c4f</xsl:param>
	<!--  Tables end  -->

	<!-- Font Families begin -->
	<xsl:param name="title.font.family">
		<xsl:call-template name="pickfont-sans" />
	</xsl:param>

	<xsl:param name="body.font.family">
		<xsl:call-template name="pickfont-serif" />
	</xsl:param>

	<xsl:param name="monospace.font.family">
		<xsl:call-template name="pickfont-mono" />
	</xsl:param>

	<xsl:param name="sans.font.family">
		<xsl:call-template name="pickfont-sans" />
	</xsl:param>
	<!-- Font Families end -->

	<!-- 自定义变量 begin -->
	<!-- 程序列表使用等宽字体显示程序 -->
	<xsl:param name="programlisting.font" select="$monospace.font.family" />
	<!-- 程序列表中字体大小为正文的75% -->
	<xsl:param name="programlisting.font.size">75%</xsl:param>
	<!-- 标题颜色 -->
	<xsl:param name="title.color">#000000</xsl:param>
	<!-- 章标题颜色 -->
	<xsl:param name="chapter.title.color" select="$title.color" />
	<!-- 节标题颜色 -->
	<xsl:param name="section.title.color" select="$title.color" />
	<!-- 标题页主字体颜色 -->
	<xsl:param name="titlepage.color" select="$title.color" />
	<!-- 标题页样式 -->
	<xsl:attribute-set name="book.titlepage.recto.style">
		<xsl:attribute name="font-family">
			<xsl:value-of select="$title.fontset" />
		</xsl:attribute>
		<xsl:attribute name="color"><xsl:value-of select="$titlepage.color" /></xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="font-size">12pt</xsl:attribute>
		<xsl:attribute name="text-align">center</xsl:attribute>
	</xsl:attribute-set>
	<!-- 章标题页的相关属性 -->
	<xsl:attribute-set name="chapter.titlepage.recto.style">
		<xsl:attribute name="color"><xsl:value-of select="$chapter.title.color" /></xsl:attribute>
		<xsl:attribute name="background-color">white</xsl:attribute>
		<xsl:attribute name="font-size">
			<xsl:choose>
				<xsl:when test="$l10n.gentext.language = 'ja-JP'">
					<xsl:value-of select="$body.font.master * 1.7" />
					<xsl:text>pt</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>24pt</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="text-align">left</xsl:attribute>
		<!--xsl:attribute name="wrap-option">no-wrap</xsl:attribute -->
		<xsl:attribute name="padding-left">1em</xsl:attribute>
		<xsl:attribute name="padding-right">1em</xsl:attribute>
	</xsl:attribute-set>
	<!-- 扉页的相关属性 -->
	<xsl:attribute-set name="preface.titlepage.recto.style">
		<xsl:attribute name="font-family">
			<xsl:value-of select="$title.fontset" />
		</xsl:attribute>
		<xsl:attribute name="color">#4a5d75</xsl:attribute>
		<xsl:attribute name="font-size">12pt</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>
	</xsl:attribute-set>
	<!-- 部标题页的相关属性 -->
	<xsl:attribute-set name="part.titlepage.recto.style">
		<xsl:attribute name="color"><xsl:value-of select="$title.color" /></xsl:attribute>
		<xsl:attribute name="text-align">center</xsl:attribute>
	</xsl:attribute-set>
	<!-- 自定义变量 end -->

	<!-- 自定义模板 begin -->
	<!-- 自定义序号标注模板 -->
	<!--	&lt;!&ndash; From: fo/callout.xsl Version: 1.73.2 Reason: This includes the callout.icon.size-->
	<!--		attribute for SVGs, we also add padding to the graphics (We can probably-->
	<!--		get rid of this if we upgrade to DocBook Stylesheets 1.73.2) &ndash;&gt;-->
	<!--	<xsl:template name="callout-bug">-->
	<!--		<xsl:param name="conum" select='1' />-->

	<!--		<xsl:choose>-->
	<!--			&lt;!&ndash; Draw callouts as images &ndash;&gt;-->
	<!--			<xsl:when-->
	<!--					test="$callout.graphics != '0'-->
	<!--                    and $conum &lt;= $callout.graphics.number.limit">-->
	<!--				<xsl:variable name="filename"-->
	<!--							  select="concat($callout.graphics.path, $conum,-->
	<!--                                   $callout.graphics.extension)" />-->

	<!--				<fo:external-graphic content-width="{$callout.icon.size}"-->
	<!--									 width="{$callout.icon.size}" padding="0.0em" margin="0.0em">-->
	<!--					<xsl:attribute name="src">-->
	<!--						<xsl:choose>-->
	<!--							<xsl:when-->
	<!--									test="$passivetex.extensions != 0-->
	<!--                            or $fop.extensions != 0-->
	<!--                            or $arbortext.extensions != 0">-->
	<!--								<xsl:value-of select="$filename" />-->
	<!--							</xsl:when>-->
	<!--							<xsl:otherwise>-->
	<!--								<xsl:text>url(</xsl:text>-->
	<!--								<xsl:value-of select="$filename" />-->
	<!--								<xsl:text>)</xsl:text>-->
	<!--							</xsl:otherwise>-->
	<!--						</xsl:choose>-->
	<!--					</xsl:attribute>-->
	<!--				</fo:external-graphic>-->
	<!--			</xsl:when>-->

	<!--			<xsl:when-->
	<!--					test="$callout.unicode != 0-->
	<!--                    and $conum &lt;= $callout.unicode.number.limit">-->
	<!--				<xsl:variable name="comarkup">-->
	<!--					<xsl:choose>-->
	<!--						<xsl:when test="$callout.unicode.start.character = 10102">-->
	<!--							<xsl:choose>-->
	<!--								<xsl:when test="$conum = 1">-->
	<!--									&#10102;-->
	<!--								</xsl:when>-->
	<!--								<xsl:when test="$conum = 2">-->
	<!--									&#10103;-->
	<!--								</xsl:when>-->
	<!--								<xsl:when test="$conum = 3">-->
	<!--									&#10104;-->
	<!--								</xsl:when>-->
	<!--								<xsl:when test="$conum = 4">-->
	<!--									&#10105;-->
	<!--								</xsl:when>-->
	<!--								<xsl:when test="$conum = 5">-->
	<!--									&#10106;-->
	<!--								</xsl:when>-->
	<!--								<xsl:when test="$conum = 6">-->
	<!--									&#10107;-->
	<!--								</xsl:when>-->
	<!--								<xsl:when test="$conum = 7">-->
	<!--									&#10108;-->
	<!--								</xsl:when>-->
	<!--								<xsl:when test="$conum = 8">-->
	<!--									&#10109;-->
	<!--								</xsl:when>-->
	<!--								<xsl:when test="$conum = 9">-->
	<!--									&#10110;-->
	<!--								</xsl:when>-->
	<!--								<xsl:when test="$conum = 10">-->
	<!--									&#10111;-->
	<!--								</xsl:when>-->
	<!--							</xsl:choose>-->
	<!--						</xsl:when>-->
	<!--						<xsl:otherwise>-->
	<!--							<xsl:message>-->
	<!--								<xsl:text>Don't know how to generate Unicode callouts </xsl:text>-->
	<!--								<xsl:text>when $callout.unicode.start.character is </xsl:text>-->
	<!--								<xsl:value-of select="$callout.unicode.start.character" />-->
	<!--							</xsl:message>-->
	<!--							<fo:inline background-color="#404040" color="white"-->
	<!--									   padding-top="0.1em" padding-bottom="0.1em" padding-start="0.2em"-->
	<!--									   padding-end="0.2em" baseline-shift="0.1em" font-family="{$body.fontset}"-->
	<!--									   font-weight="bold" font-size="75%">-->
	<!--								<xsl:value-of select="$conum" />-->
	<!--							</fo:inline>-->
	<!--						</xsl:otherwise>-->
	<!--					</xsl:choose>-->
	<!--				</xsl:variable>-->

	<!--				<xsl:choose>-->
	<!--					<xsl:when test="$callout.unicode.font != ''">-->
	<!--						<fo:inline font-family="{$callout.unicode.font}">-->
	<!--							<xsl:copy-of select="$comarkup" />-->
	<!--						</fo:inline>-->
	<!--					</xsl:when>-->
	<!--					<xsl:otherwise>-->
	<!--						<xsl:copy-of select="$comarkup" />-->
	<!--					</xsl:otherwise>-->
	<!--				</xsl:choose>-->
	<!--			</xsl:when>-->

	<!--			&lt;!&ndash; Most safe: draw a dark gray square with a white number inside &ndash;&gt;-->
	<!--			<xsl:otherwise>-->
	<!--				<fo:inline background-color="#404040" color="white"-->
	<!--						   padding-top="0.1em" padding-bottom="0.1em" padding-start="0.2em"-->
	<!--						   padding-end="0.2em" baseline-shift="0.1em" font-family="{$body.fontset}"-->
	<!--						   font-weight="bold" font-size="75%">-->
	<!--					<xsl:value-of select="$conum" />-->
	<!--				</fo:inline>-->
	<!--			</xsl:otherwise>-->
	<!--		</xsl:choose>-->
	<!--	</xsl:template>-->

	<!-- 自定义目录模板 -->
<!--		<xsl:template name="toc.line">-->
<!--			<xsl:variable name="id">-->
<!--				<xsl:call-template name="object.id" />-->
<!--			</xsl:variable>-->

<!--			<xsl:variable name="label">-->
<!--				<xsl:apply-templates select="." mode="label.markup" />-->
<!--			</xsl:variable>-->

<!--			<fo:block text-align-last="justify" end-indent="{$toc.indent.width}pt"-->
<!--					  last-line-end-indent="-{$toc.indent.width}pt">-->
<!--				<fo:inline keep-with-next.within-line="always">-->
<!--					<fo:basic-link internal-destination="{$id}">-->

<!--						&lt;!&ndash; Chapter titles should be bold. &ndash;&gt;-->
<!--						<xsl:choose>-->
<!--							<xsl:when test="local-name(.) = 'chapter'">-->
<!--								<xsl:attribute name="font-weight">bold</xsl:attribute>-->
<!--							</xsl:when>-->
<!--						</xsl:choose>-->

<!--						<xsl:if test="$label != ''">-->
<!--							<xsl:copy-of select="$label" />-->
<!--							<xsl:value-of select="$autotoc.label.separator" />-->
<!--						</xsl:if>-->
<!--						<xsl:apply-templates select="."-->
<!--											 mode="titleabbrev.markup" />-->
<!--					</fo:basic-link>-->
<!--				</fo:inline>-->
<!--				<fo:inline keep-together.within-line="always">-->
<!--					<xsl:text> </xsl:text>-->
<!--					<fo:leader leader-pattern="dots" leader-pattern-width="3pt"-->
<!--							   leader-alignment="reference-area" keep-with-next.within-line="always" />-->
<!--					<xsl:text> </xsl:text>-->
<!--					<fo:basic-link internal-destination="{$id}">-->
<!--						<fo:page-number-citation ref-id="{$id}" />-->
<!--					</fo:basic-link>-->
<!--				</fo:inline>-->
<!--			</fo:block>-->
<!--		</xsl:template>-->
	<!-- 单元格模板，高亮了表头和表尾 -->
	<xsl:template name="table.cell.block.properties">
		<xsl:if test="ancestor::d:thead or ancestor::d:tfoot">
			<xsl:attribute name="font-weight">bold</xsl:attribute>
			<xsl:attribute name="background-color">#4a5d75</xsl:attribute>
			<xsl:attribute name="color">white</xsl:attribute>
		</xsl:if>
	</xsl:template>
	<!-- 表格行模板，高亮了表头和表尾  -->
	<xsl:template name="table.row.properties">
		<xsl:variable name="bgcolor">
			<xsl:call-template name="dbfo-attribute">
				<xsl:with-param name="pis" select="processing-instruction('dbfo')"/>
				<xsl:with-param name="attribute" select="'bgcolor'"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:if test="$bgcolor != ''">
			<xsl:attribute name="background-color">
				<xsl:value-of select="$bgcolor"/>
			</xsl:attribute>
		</xsl:if>
		<xsl:if test="ancestor::d:thead or ancestor::d:tfoot">
			<xsl:attribute name="background-color">#4a5d75</xsl:attribute>
		</xsl:if>
	</xsl:template>
	<!-- 附录的title页 -->
	<xsl:template match="d:title" mode="appendix.titlepage.recto.auto.mode">
		<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format"
				  xsl:use-attribute-sets="chapter.titlepage.recto.style">
			<xsl:call-template name="component.title.nomarkup">
				<xsl:with-param name="node" select="ancestor-or-self::d:appendix[1]" />
			</xsl:call-template>
		</fo:block>
	</xsl:template>
	<!-- 扉页 -->
	<xsl:template name="preface.titlepage.recto">
		<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format"
				  xsl:use-attribute-sets="preface.titlepage.recto.style" margin-left="{$title.margin.left}">
			<xsl:call-template name="component.title.nomarkup">
				<xsl:with-param name="node" select="ancestor-or-self::d:preface[1]" />
			</xsl:call-template>
		</fo:block>
		<xsl:choose>
			<xsl:when test="d:prefaceinfo/d:subtitle">
				<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
									 select="d:prefaceinfo/d:subtitle" />
			</xsl:when>
			<xsl:when test="d:docinfo/d:subtitle">
				<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
									 select="d:docinfo/d:subtitle" />
			</xsl:when>
			<xsl:when test="d:info/d:subtitle">
				<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
									 select="d:info/d:subtitle" />
			</xsl:when>
			<xsl:when test="d:subtitle">
				<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
									 select="d:subtitle" />
			</xsl:when>
		</xsl:choose>
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:prefaceinfo/d:corpauthor" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:docinfo/d:corpauthor" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:info/d:corpauthor" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:prefaceinfo/d:authorgroup" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:docinfo/d:authorgroup" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:info/d:authorgroup" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:prefaceinfo/d:author" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:docinfo/d:author" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:info/d:author" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:prefaceinfo/d:othercredit" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:docinfo/d:othercredit" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:info/d:othercredit" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:prefaceinfo/d:releaseinfo" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:docinfo/d:releaseinfo" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:info/d:releaseinfo" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:prefaceinfo/d:copyright" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:docinfo/d:copyright" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:info/d:copyright" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:prefaceinfo/d:legalnotice" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:docinfo/d:legalnotice" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:info/d:legalnotice" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:prefaceinfo/d:pubdate" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:docinfo/d:pubdate" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:info/d:pubdate" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:prefaceinfo/d:revision" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:docinfo/d:revision" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:info/d:revision" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:prefaceinfo/d:revhistory" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:docinfo/d:revhistory" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:info/d:revhistory" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:prefaceinfo/d:abstract" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:docinfo/d:abstract" />
		<xsl:apply-templates mode="preface.titlepage.recto.auto.mode"
							 select="d:info/d:abstract" />
	</xsl:template>
	<!-- 无衬线字体 -->
	<xsl:template name="pickfont-sans">
		<xsl:variable name="font">
			<xsl:choose>
				<xsl:when test="$l10n.gentext.language = 'ja-JP'">
					<xsl:text>KochiMincho</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'ko-KR'">
					<xsl:text>BaekmukBatang</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'zh-CN'">
					<xsl:text>FZHei-B01</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'bn-IN'">
					<xsl:text>LohitBengali</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'ta-IN'">
					<xsl:text>LohitTamil</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'pa-IN'">
					<xsl:text>LohitPunjabi</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'hi-IN'">
					<xsl:text>LohitHindi</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'gu-IN'">
					<xsl:text>LohitGujarati</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'zh-TW'">
					<xsl:text>ARPLMingti2LBig5</xsl:text>
				</xsl:when>
			</xsl:choose>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="$fop1.extensions != 0">
				<xsl:text>DejaVu Sans,FreeSans,</xsl:text>
				<xsl:copy-of select="$font" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>FreeSans,</xsl:text>
				<xsl:copy-of select="$font" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!-- 衬线字体 -->
	<xsl:template name="pickfont-serif">
		<xsl:variable name="font">
			<xsl:choose>
				<xsl:when test="$l10n.gentext.language = 'ja-JP'">
					<xsl:text>KochiMincho</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'ko-KR'">
					<xsl:text>BaekmukBatang</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'zh-CN'">
					<xsl:text>FZFangSong-Z02</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'bn-IN'">
					<xsl:text>LohitBengali</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'ta-IN'">
					<xsl:text>LohitTamil</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'pa-IN'">
					<xsl:text>LohitPunjabi</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'hi-IN'">
					<xsl:text>LohitHindi</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'gu-IN'">
					<xsl:text>LohitGujarati</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'zh-TW'">
					<xsl:text>ARPLMingti2LBig5</xsl:text>
				</xsl:when>
			</xsl:choose>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="$fop1.extensions != 0">
				<xsl:text>DejaVu Serif,FreeSerif,</xsl:text>
				<xsl:copy-of select="$font" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>FreeSerif,</xsl:text>
				<xsl:copy-of select="$font" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!-- 等宽字体 -->
	<xsl:template name="pickfont-mono">
		<xsl:variable name="font">
			<xsl:choose>
				<xsl:when test="$l10n.gentext.language = 'ja-JP'">
					<xsl:text>KochiMincho</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'ko-KR'">
					<xsl:text>BaekmukBatang</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'zh-CN'">
					<xsl:text>FZKai-Z03</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'bn-IN'">
					<xsl:text>LohitBengali</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'ta-IN'">
					<xsl:text>LohitTamil</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'pa-IN'">
					<xsl:text>LohitPunjabi</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'hi-IN'">
					<xsl:text>LohitHindi</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'gu-IN'">
					<xsl:text>LohitGujarati</xsl:text>
				</xsl:when>
				<xsl:when test="$l10n.gentext.language = 'zh-TW'">
					<xsl:text>ARPLMingti2LBig5</xsl:text>
				</xsl:when>
			</xsl:choose>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="$fop1.extensions != 0">
				<xsl:text>DejaVu Sans Mono,Liberation Mono,</xsl:text>
				<xsl:copy-of select="$font" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>Liberation Mono,</xsl:text>
				<xsl:copy-of select="$font" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!-- 地址模板 -->
	<xsl:template match="d:address">
		<xsl:param name="suppress-numbers" select="'0'" />
		<xsl:variable name="content">
			<xsl:choose>
				<xsl:when
						test="$suppress-numbers = '0'
											and @linenumbering = 'numbered'
											and $use.extensions != '0'
											and $linenumbering.extension != '0'">
					<xsl:call-template name="number.rtf.lines">
						<xsl:with-param name="rtf">
							<xsl:apply-templates />
						</xsl:with-param>
					</xsl:call-template>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<fo:block wrap-option='no-wrap' white-space-collapse='false'
				  white-space-treatment='preserve' linefeed-treatment="preserve"
				  text-align="start" xsl:use-attribute-sets="verbatim.properties">
			<xsl:copy-of select="$content" />
		</fo:block>
	</xsl:template>
	<!-- 章节标题 -->
	<xsl:template name="component.title.nomarkup">
		<xsl:param name="node" select="." />
		<xsl:variable name="id">
			<xsl:call-template name="object.id">
				<xsl:with-param name="object" select="$node" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="title">
			<xsl:apply-templates select="$node" mode="object.title.markup">
				<xsl:with-param name="allow-anchors" select="1" />
			</xsl:apply-templates>
		</xsl:variable>
		<xsl:copy-of select="$title" />
	</xsl:template>
	<!-- 页眉内容 -->
	<xsl:template name="header.content">
		<xsl:param name="pageclass" select="''" />
		<xsl:param name="sequence" select="''" />
		<xsl:param name="position" select="''" />
		<xsl:param name="gentext-key" select="''" />
		<xsl:param name="title-limit" select="'30'" />
		<xsl:choose>
			<xsl:when
					test="$confidential = 1 and (($sequence='odd' and $position='left') or ($sequence='even' and $position='right'))">
				<fo:inline keep-together.within-line="always" font-weight="bold">
					<xsl:text>STORE.TAOTAO CONFIDENTIAL</xsl:text>
				</fo:inline>
			</xsl:when>
			<xsl:when test="$sequence = 'blank'">
				<!-- nothing -->
			</xsl:when>
			<!-- Extracting 'Chapter' + Chapter Number from the full Chapter title,
				with a dirty, dirty hack -->
			<xsl:when
					test="($sequence='first' and $position='center' and $gentext-key='chapter')">
				<xsl:variable name="text">
					<xsl:call-template name="component.title.nomarkup" />
				</xsl:variable>

				<!-- 正文左页页眉 -->
				<fo:inline keep-together.within-line="always" font-weight="bold">
					<xsl:value-of select="$text" />
				</fo:inline>
			</xsl:when>
			<!--xsl:when test="($sequence='odd' or $sequence='even') and $position='center'" -->
			<xsl:when test="($sequence='even' and $position='left')">
				<!--xsl:if test="$pageclass != 'titlepage'" -->
				<xsl:variable name="text">
					<xsl:call-template name="component.title.nomarkup" />
				</xsl:variable>
				<!-- 目录左页页眉 -->
				<fo:inline keep-together.within-line="always" font-weight="bold">
					<xsl:choose>
						<xsl:when test="string-length($text) &gt; '33'">
							<xsl:value-of select="concat(substring($text, 0, $title-limit), '...')" />
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="$text" />
						</xsl:otherwise>
					</xsl:choose>
				</fo:inline>
				<!--xsl:if -->
			</xsl:when>
			<xsl:when test="($sequence='odd' and $position='right')">
				<!--xsl:if test="$pageclass != 'titlepage'" -->
				<fo:inline keep-together.within-line="always">
					<fo:retrieve-marker retrieve-class-name="section.head.marker"
										retrieve-position="first-including-carryover"
										retrieve-boundary="page-sequence" />
				</fo:inline>
				<!--/xsl:if -->
			</xsl:when>
			<xsl:when test="$position='left'">
				<!-- Same for odd, even, empty, and blank sequences -->
				<xsl:call-template name="draft.text" />
			</xsl:when>
			<xsl:when test="$position='center'">
				<!-- nothing for empty and blank sequences -->
			</xsl:when>
			<xsl:when test="$position='right'">
				<!-- Same for odd, even, empty, and blank sequences -->
				<xsl:call-template name="draft.text" />
			</xsl:when>
			<xsl:when test="$sequence = 'first'">
				<!-- nothing for first pages -->
			</xsl:when>
			<xsl:when test="$sequence = 'blank'">
				<!-- nothing for blank pages -->
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<!-- 页眉分割线 -->
	<xsl:template name="head.sep.rule">
		<xsl:param name="pageclass" />
		<xsl:param name="sequence" />
		<xsl:param name="gentext-key" />

		<xsl:if test="$header.rule != 0">
			<xsl:attribute name="border-bottom-width">0.5pt</xsl:attribute>
			<xsl:attribute name="border-bottom-style">solid</xsl:attribute>
			<xsl:attribute name="border-bottom-color">#4a5d75</xsl:attribute>
		</xsl:if>
	</xsl:template>
	<!-- 页脚分割线 -->
	<xsl:template name="foot.sep.rule">
		<xsl:param name="pageclass" />
		<xsl:param name="sequence" />
		<xsl:param name="gentext-key" />
		<xsl:if test="$footer.rule != 0">
			<xsl:attribute name="border-top-width">0.5pt</xsl:attribute>
			<xsl:attribute name="border-top-style">solid</xsl:attribute>
			<xsl:attribute name="border-top-color">#4a5d75</xsl:attribute>
		</xsl:if>
	</xsl:template>
	<!-- 表格作者模板 -->
	<xsl:template match="d:author" mode="tablerow.titlepage.mode">
		<fo:table-row>
			<fo:table-cell>
				<fo:block>
					<xsl:call-template name="gentext">
						<xsl:with-param name="key" select="'Author'" />
					</xsl:call-template>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell>
				<fo:block>
					<xsl:call-template name="person.name">
						<xsl:with-param name="node" select="." />
					</xsl:call-template>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell>
				<fo:block>
					<xsl:apply-templates select="d:email" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>
	<!-- 作者模板 -->
	<xsl:template match="d:author" mode="titlepage.mode">
		<fo:block>
			<xsl:call-template name="person.name">
				<xsl:with-param name="node" select="." />
			</xsl:call-template>
		</fo:block>
	</xsl:template>
	<!-- 参与编辑人员 -->
	<xsl:template match="d:editor" mode="tablerow.titlepage.mode">
		<fo:table-row>
			<fo:table-cell>
				<fo:block>
					<xsl:call-template name="gentext">
						<xsl:with-param name="key" select="'Editor'" />
					</xsl:call-template>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell>
				<fo:block>
					<xsl:call-template name="person.name">
						<xsl:with-param name="node" select="." />
					</xsl:call-template>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell>
				<fo:block>
					<xsl:apply-templates select="d:email" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>
	<!-- 其它参与人员 -->
	<xsl:template match="d:othercredit" mode="tablerow.titlepage.mode">
		<fo:table-row>
			<fo:table-cell>
				<fo:block>
					<xsl:call-template name="gentext">
						<xsl:with-param name="key" select="'translator'" />
					</xsl:call-template>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell>
				<fo:block>
					<xsl:call-template name="person.name">
						<xsl:with-param name="node" select="." />
					</xsl:call-template>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell>
				<fo:block>
					<xsl:apply-templates select="d:email" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>
	<!-- 书的标题 -->
	<xsl:template match="d:title" mode="book.titlepage.recto.auto.mode">
		<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format"
				  xsl:use-attribute-sets="book.titlepage.recto.style" text-align="center"
				  font-size="34pt" space-before="18.6624pt" font-weight="bold"
				  font-family="{$title.fontset}">
			<xsl:call-template name="division.title">
				<xsl:with-param name="node" select="ancestor-or-self::d:book[1]" />
			</xsl:call-template>
		</fo:block>
	</xsl:template>
	<!-- 书的子标题 -->
	<xsl:template match="d:subtitle" mode="book.titlepage.recto.auto.mode">
		<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format"
				  xsl:use-attribute-sets="book.titlepage.recto.style" text-align="center"
				  font-size="20pt" space-before="30pt" font-family="{$title.fontset}">
			<xsl:apply-templates select="."
								 mode="book.titlepage.recto.mode" />
		</fo:block>
	</xsl:template>
	<!-- 勘误编号 -->
	<xsl:template match="d:issuenum" mode="book.titlepage.recto.auto.mode">
		<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format"
				  xsl:use-attribute-sets="book.titlepage.recto.style" text-align="center"
				  font-size="16pt" space-before="15.552pt" font-family="{$title.fontset}">
			<xsl:apply-templates select="."
								 mode="book.titlepage.recto.mode" />
		</fo:block>
	</xsl:template>
	<!-- 封面作者 -->
	<xsl:template match="d:author" mode="book.titlepage.recto.auto.mode">
		<fo:block xsl:use-attribute-sets="book.titlepage.recto.style"
				  font-size="14pt" space-before="15.552pt">
			<xsl:call-template name="person.name">
				<xsl:with-param name="node" select="." />
			</xsl:call-template>
		</fo:block>
	</xsl:template>
	<!-- 扉页模板 -->
	<xsl:template name="book.titlepage.recto">
		<xsl:choose>
			<xsl:when test="d:bookinfo/d:title">
				<xsl:apply-templates mode="book.titlepage.recto.auto.mode"
									 select="d:bookinfo/d:title" />
			</xsl:when>
			<xsl:when test="d:info/d:title">
				<xsl:apply-templates mode="book.titlepage.recto.auto.mode"
									 select="d:info/d:title" />
			</xsl:when>
			<xsl:when test="d:title">
				<xsl:apply-templates mode="book.titlepage.recto.auto.mode"
									 select="d:title" />
			</xsl:when>
		</xsl:choose>

		<xsl:apply-templates mode="book.titlepage.recto.auto.mode"
							 select="d:bookinfo/d:issuenum" />
		<xsl:apply-templates mode="book.titlepage.recto.auto.mode"
							 select="d:info/d:issuenum" />
		<xsl:apply-templates mode="book.titlepage.recto.auto.mode"
							 select="d:issuenum" />

		<xsl:choose>
			<xsl:when test="d:bookinfo/d:subtitle">
				<xsl:apply-templates mode="book.titlepage.recto.auto.mode"
									 select="d:bookinfo/d:subtitle" />
			</xsl:when>
			<xsl:when test="d:info/d:subtitle">
				<xsl:apply-templates mode="book.titlepage.recto.auto.mode"
									 select="d:info/d:subtitle" />
			</xsl:when>
			<xsl:when test="d:subtitle">
				<xsl:apply-templates mode="book.titlepage.recto.auto.mode"
									 select="d:subtitle" />
			</xsl:when>
		</xsl:choose>

		<fo:block xsl:use-attribute-sets="book.titlepage.recto.style"
				  font-size="14pt" space-before="15.552pt">
			<xsl:apply-templates mode="book.titlepage.recto.auto.mode"
								 select="d:bookinfo/d:releaseinfo" />
		</fo:block>

		<fo:block text-align="center" space-before="15.552pt">
			<xsl:call-template name="person.name.list">
				<xsl:with-param name="person.list"
								select="d:bookinfo/d:authorgroup/d:author|d:bookinfo/d:authorgroup/d:corpauthor" />
				<xsl:with-param name="person.type" select="'author'" />
			</xsl:call-template>
		</fo:block>

		<fo:block text-align="center" space-before="15.552pt">
			<xsl:call-template name="person.name.list">
				<xsl:with-param name="person.list" select="d:bookinfo/d:authorgroup/d:editor" />
				<xsl:with-param name="person.type" select="'editor'" />
			</xsl:call-template>
		</fo:block>

		<fo:block text-align="center" space-before="15.552pt">
			<xsl:call-template name="person.name.list">
				<xsl:with-param name="person.list"
								select="d:bookinfo/d:authorgroup/d:othercredit" />
				<xsl:with-param name="person.type" select="'othercredit'" />
			</xsl:call-template>
		</fo:block>
	</xsl:template>
	<xsl:template name="book.titlepage.verso"></xsl:template>
	<xsl:template name="book.titlepage3.recto"></xsl:template>
	<xsl:template name="book.titlepage.before.verso"></xsl:template>
	<xsl:template name="book.titlepage.separator"></xsl:template>
	<xsl:template name="book.titlepage.before.recto"></xsl:template>
	<!-- 扉页模板 -->
	<xsl:template name="book.titlepage">
		<fo:block xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<xsl:call-template name="book.titlepage.before.recto" />
			<fo:block>
				<xsl:call-template name="book.titlepage.recto" />
			</fo:block>
			<xsl:call-template name="book.titlepage.separator" />
			<fo:block>
				<xsl:call-template name="book.titlepage.verso" />
			</fo:block>
			<xsl:call-template name="book.titlepage.separator" />
			<fo:block>
				<xsl:call-template name="book.titlepage3.recto" />
			</fo:block>
			<xsl:call-template name="book.titlepage.separator" />
		</fo:block>
	</xsl:template>
	<!-- 问题 -->
	<xsl:template match="d:question">
		<xsl:variable name="id">
			<xsl:call-template name="object.id" />
		</xsl:variable>
		<xsl:variable name="entry.id">
			<xsl:call-template name="object.id">
				<xsl:with-param name="object" select="parent::d:*" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="deflabel">
			<xsl:choose>
				<xsl:when test="ancestor-or-self::d:*[@defaultlabel]">
					<xsl:value-of
							select="(ancestor-or-self::d:*[@defaultlabel])[last()]
                              /@defaultlabel" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$qanda.defaultlabel" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<fo:list-item id="{$entry.id}" xsl:use-attribute-sets="list.item.spacing">
			<fo:list-item-label end-indent="label-end()">
				<xsl:choose>
					<xsl:when test="$deflabel = 'none'">
						<fo:block />
					</xsl:when>
					<xsl:otherwise>
						<fo:block>
							<xsl:apply-templates select="." mode="label.markup" />
							<xsl:if test="$deflabel = 'number' and not(label)">
								<xsl:apply-templates select="."
													 mode="intralabel.punctuation" />
							</xsl:if>
						</fo:block>
					</xsl:otherwise>
				</xsl:choose>
			</fo:list-item-label>
			<fo:list-item-body start-indent="body-start()">
				<xsl:choose>
					<xsl:when test="$deflabel = 'none'">
						<fo:block font-weight="bold">
							<xsl:apply-templates select="d:*[local-name(.)!='label']" />
						</fo:block>
					</xsl:when>
					<xsl:otherwise>
						<xsl:apply-templates select="d:*[local-name(.)!='label']" />
					</xsl:otherwise>
				</xsl:choose>
				<!-- Uncomment this line to get revhistory output in the question -->
				<!-- <xsl:apply-templates select="preceding-sibling::revhistory"/> -->
			</fo:list-item-body>
		</fo:list-item>
	</xsl:template>
	<!-- 答案 -->
	<xsl:template match="d:answer">
		<xsl:variable name="id">
			<xsl:call-template name="object.id" />
		</xsl:variable>
		<xsl:variable name="entry.id">
			<xsl:call-template name="object.id">
				<xsl:with-param name="object" select="parent::d:*" />
			</xsl:call-template>
		</xsl:variable>

		<xsl:variable name="deflabel">
			<xsl:choose>
				<xsl:when test="ancestor-or-self::d:*[@defaultlabel]">
					<xsl:value-of
							select="(ancestor-or-self::d:*[@defaultlabel])[last()]
                              /@defaultlabel" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$qanda.defaultlabel" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<fo:list-item xsl:use-attribute-sets="list.item.spacing">
			<fo:list-item-label end-indent="label-end()">
				<xsl:choose>
					<xsl:when test="$deflabel = 'none'">
						<fo:block />
					</xsl:when>
					<xsl:otherwise>
						<fo:block>
							<xsl:variable name="answer.label">
								<xsl:apply-templates select="." mode="label.markup" />
							</xsl:variable>
							<xsl:copy-of select="$answer.label" />
						</fo:block>
					</xsl:otherwise>
				</xsl:choose>
			</fo:list-item-label>
			<fo:list-item-body start-indent="body-start()">
				<xsl:apply-templates select="d:*[local-name(.)!='label']" />
			</fo:list-item-body>
		</fo:list-item>
	</xsl:template>
	<!-- 高亮方案 -->
	<!-- 关键字 -->
	<xsl:template match='xslthl:keyword' mode="xslthl">
		<fo:inline color="#7F0055" font-weight="bold">
			<xsl:apply-templates mode="xslthl" />
		</fo:inline>
	</xsl:template>
	<!-- 预编译指令 -->
	<xsl:template match='xslthl:directive' mode="xslthl">
		<fo:inline color="#7F0055" font-weight="bold">
			<xsl:apply-templates mode="xslthl" />
		</fo:inline>
	</xsl:template>
	<!-- 单行注释和普通多行注释 -->
	<xsl:template match='xslthl:comment' mode="xslthl">
		<fo:inline color="#3F7F5F">
			<xsl:apply-templates mode="xslthl" />
		</fo:inline>
	</xsl:template>
	<!-- 文档注释 -->
	<xsl:template match='xslthl:doccomment|xslthl:htmlComment|xslthl:xmlComment'
				  mode="xslthl">
		<fo:inline color="#3F3FBF">
			<xsl:apply-templates mode="xslthl" />
		</fo:inline>
	</xsl:template>
	<!-- 字符串 -->
	<xsl:template match='xslthl:string' mode="xslthl">
		<fo:inline color="#2A00FF" font-weight="bold">
			<xsl:apply-templates mode="xslthl" />
		</fo:inline>
	</xsl:template>
	<!-- 标签 -->
	<xsl:template match='xslthl:html' mode="xslthl">
		<fo:inline color="#008080" font-weight="bold">
			<xsl:apply-templates mode="xslthl" />
		</fo:inline>
	</xsl:template>
	<!-- tag标签 -->
	<xsl:template match='xslthl:tag' mode="xslthl">
		<fo:inline color="#008080" font-weight="bold">
			<xsl:apply-templates mode="xslthl" />
		</fo:inline>
	</xsl:template>
	<!-- 属性 -->
	<xsl:template match='xslthl:attribute' mode="xslthl">
		<fo:inline color="#7F007F" font-weight="bold">
			<xsl:apply-templates mode="xslthl" />
		</fo:inline>
	</xsl:template>
	<!-- 属性的值 -->
	<xsl:template match='xslthl:value' mode="xslthl">
		<fo:inline color="#2A00FF" font-weight="bold">
			<xsl:apply-templates mode="xslthl" />
		</fo:inline>
	</xsl:template>
	<!-- 代码块 start -->
<!--	<xsl:template match="programlisting">-->
<!--		<fo:block background-color="#F6F6F6" border-style="solid"-->
<!--				  border-width=".3mm" border-color="#CCCCCC" font-family="{$programlisting.font}"-->
<!--				  font-size="{$programlisting.font.size}" space-before="12pt"-->
<!--				  space-after="12pt" linefeed-treatment="preserve"-->
<!--				  white-space-collapse="false" white-space-treatment="preserve"-->
<!--				  padding-bottom="12pt" padding-top="12pt" padding-right="12pt"-->
<!--				  padding-left="12pt">-->
<!--			<xsl:call-template name="apply-highlighting" />-->
<!--		</fo:block>-->

<!--        <fo:block>-->
<!--            ==========================================================================-->
<!--        </fo:block>-->
<!--        <fo:block>-->
<!--            <xsl:call-template name="apply-highlighting" />-->
<!--        </fo:block>-->
<!--	</xsl:template>-->
	<!-- 代码块 end -->
	<!-- 文档修订历史 start -->
	<xsl:template match="d:revhistory" mode="titlepage.mode">

		<xsl:variable name="explicit.table.width">
			<xsl:call-template name="pi.dbfo_table-width" />
		</xsl:variable>

		<xsl:variable name="table.width">
			<xsl:choose>
				<xsl:when test="$explicit.table.width != ''">
					<xsl:value-of select="$explicit.table.width" />
				</xsl:when>
				<xsl:when test="$default.table.width = ''">
					<xsl:text>100%</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$default.table.width" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<fo:block>
			<xsl:choose>
				<xsl:when test="d:title|d:info/d:title">
					<xsl:apply-templates select="d:title|d:info/d:title"
										 mode="titlepage.mode" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:call-template name="gentext">
						<xsl:with-param name="key" select="'RevHistory'" />
					</xsl:call-template>
				</xsl:otherwise>
			</xsl:choose>
		</fo:block>

		<fo:table table-layout="auto" width="{$table.width}"
				  xsl:use-attribute-sets="revhistory.table.properties">
			<fo:table-column column-number="1" />
			<fo:table-column column-number="2" />
			<fo:table-column column-number="3" />
			<fo:table-column column-number="4" />
			<fo:table-header>
				<fo:table-row>
					<fo:table-cell xsl:use-attribute-sets="revhistory.table.header.cell.properties">
						<fo:block>版本号</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="revhistory.table.header.cell.properties">
						<fo:block>修订日期</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="revhistory.table.header.cell.properties">
						<fo:block>修订人</fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="revhistory.table.header.cell.properties">
						<fo:block>修订说明</fo:block>
					</fo:table-cell>
				</fo:table-row>
			</fo:table-header>
			<fo:table-body>
				<xsl:apply-templates select="d:*[not(self::d:title)]"
									 mode="titlepage.mode" />
			</fo:table-body>
		</fo:table>
	</xsl:template>
	<!-- 文档修订历史 end -->
	<!-- 文档修订历史记录 start -->
	<xsl:template match="d:revhistory/d:revision" mode="titlepage.mode">
		<xsl:variable name="revnumber" select="d:revnumber" />
		<xsl:variable name="revdate" select="d:date" />
		<xsl:variable name="revauthor" select="d:authorinitials|d:author" />
		<xsl:variable name="revremark" select="d:revremark|d:revdescription" />
		<fo:table-row>
			<fo:table-cell xsl:use-attribute-sets="revhistory.table.cell.properties">
				<fo:block>
					<xsl:if test="$revnumber">
						<xsl:call-template name="gentext">
							<xsl:with-param name="key" select="'Revision'" />
						</xsl:call-template>
						<xsl:call-template name="gentext.space" />
						<xsl:apply-templates select="$revnumber[1]"
											 mode="titlepage.mode" />
					</xsl:if>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell xsl:use-attribute-sets="revhistory.table.cell.properties">
				<fo:block>
					<xsl:apply-templates select="$revdate[1]" mode="titlepage.mode" />
				</fo:block>
			</fo:table-cell>
			<fo:table-cell xsl:use-attribute-sets="revhistory.table.cell.properties">
				<fo:block>
					<xsl:for-each select="$revauthor">
						<xsl:apply-templates select="." mode="titlepage.mode" />
						<xsl:if test="position() != last()">
							<xsl:text>, </xsl:text>
						</xsl:if>
					</xsl:for-each>
				</fo:block>
			</fo:table-cell>

			<fo:table-cell xsl:use-attribute-sets="revhistory.table.cell.properties">
				<fo:block>
					<xsl:if test="$revremark">
						<xsl:apply-templates select="$revremark[1]"
											 mode="titlepage.mode" />
					</xsl:if>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>
	<!-- 文档修订历史记录 end -->
	<!-- 题注块 start -->
	<xsl:template name="formal.object.heading">
		<xsl:param name="object" select="."/>
		<xsl:param name="placement" select="'before'"/>

		<fo:block text-align="center" xsl:use-attribute-sets="formal.title.properties">
			<xsl:choose>
				<xsl:when test="$placement = 'before'">
					<xsl:attribute
							name="keep-with-next.within-column">always</xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute
							name="keep-with-previous.within-column">always</xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:apply-templates select="$object" mode="object.title.markup">
				<xsl:with-param name="allow-anchors" select="1"/>
			</xsl:apply-templates>
		</fo:block>
	</xsl:template>
	<!-- 题注块 end -->
	<!--  自定义模板 end  -->
</xsl:stylesheet>
