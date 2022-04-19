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
= 通用处理样式模版

本模板主要定义了html和fo共有变量默认值

各变量含义的参考《DocBook XSL Stylesheets: Reference Documentation》的 "HTML Parameter Reference" 节和 "FO Parameter Reference" 节（下文中合称参考章节）
-->

<xsl:stylesheet
        xmlns:d="http://docbook.org/ns/docbook"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        version="1.0">
    <!-- 令图片保持相对路径 -->
    <xsl:param name="keep.relative.image.uris" select="1"/>
    <!-- Admonitions begin -->
    <!--
        强调性标注，各变量含义见参考章节的 "Admonitions" 小节
    -->
    <!-- 在各种提示框中是否使用图形 -->
    <xsl:param name="admon.graphics" select="1"/>
    <!-- 图形存放的路径 -->
    <xsl:param name="admon.graphics.path">
        <xsl:if test="$img.src.path != ''">
            <xsl:value-of select="$img.src.path"/>
        </xsl:if>
        <xsl:text>images/taotao/docbook/</xsl:text>
    </xsl:param>
    <!-- Admonitions end   -->

    <!-- Callout begin -->
    <!--
     序号标注扩展
     -->
    <!-- callouts.extension 是否允许在programlistco中混合使用扩展的序号标注 -->
    <xsl:param name="callouts.extension" select="1"/>
    <!-- 在programlistco使用基于图形的标注会更漂亮 -->
    <xsl:param name="callout.graphics" select="1"/>
    <!-- 一段代码中标注的个数限制 -->
    <xsl:param name="callout.graphics.number.limit">15</xsl:param>
    <!-- 使用基于向量图形的标注扩展 -->
    <xsl:param name="callout.graphics.extension">.svg</xsl:param>
    <!-- 向量图形的存放路径 -->
    <xsl:param name="callout.graphics.path">
        <xsl:if test="$img.src.path != ''">
            <xsl:value-of select="$img.src.path"/>
        </xsl:if>
        <xsl:text>images/taotao/docbook/callouts/</xsl:text>
    </xsl:param>
    <!-- Callout end   -->

    <!-- ToC/LoT/Index Generation begin -->
    <!--
        ToC: 内容表，就是目录
        LoT: 不是很清楚
        Index: 索引，基于关键词的内容索引
        该部分为上述三者生成的公共配置
    -->


    <!--  ToC/LoT/Index Generation end  -->

    <!-- Stylesheet Extensions begin -->
    <!--
     多种扩展项的开关
     -->
    <!-- use.extensions：是否允许使用扩展项，0 否 1 是 如果否，则callouts等选项也不可用 -->
    <xsl:param name="use.extensions" select="1"/>
    <!-- 禁用表格列扩展 -->
    <xsl:param name="tablecolumns.extension" select="0"/>
    <!--  Stylesheet Extensions end  -->

    <!-- Automatic labelling begin -->
    <!--
        序号自动生成器
    -->
    <!-- 节标题自动生成序号 -->
    <xsl:param name="section.autolabel" select="1"/>
    <!-- 节号上包括章号 -->
    <xsl:param name="section.label.includes.component.label" select="1"/>
    <!--  Automatic labelling end  -->

    <!-- XSLT Processing begin -->
    <!--  XSLT Processing end  -->

    <!-- Miscellaneous begin -->
    <!-- 允许源码高亮 -->
    <xsl:param name="highlight.source" select="1"/>
    <xsl:param name="highlight.xslthl.config">classpath:docbook/highlighting/xslthl-config.xml</xsl:param>
    <!--  Miscellaneous end  -->

    <!-- Localization begin -->
    <!-- 默认语言 -->
    <xsl:param name="l10n.gentext.default.language" select="'zh-CN'"/>
    <!--  Localization end  -->

    <!-- 自定义变量 begin -->
    <!--	<xsl:param name="use.simplified.author.group" select="1" />-->
    <!-- 是否声名版权 -->
    <xsl:param name="confidential" select="0"/>
    <!--  自定义变量 end  -->


    <!-- 自定义模板 begin -->
    <!--	&lt;!&ndash; 人名列表模版 &ndash;&gt;-->
    <!--	<xsl:template name="person.name.list">-->
    <!--		<xsl:param name="person.list" select="author|corpauthor|othercredit|editor" />-->
    <!--		<xsl:param name="person.count" select="count($person.list)" />-->
    <!--		<xsl:param name="person.type" select="'author'" />-->
    <!--		<xsl:param name="count" select="1" />-->

    <!--		<xsl:choose>-->
    <!--			<xsl:when test="$use.simplified.author.group = 1">-->
    <!--				<xsl:choose>-->
    <!--					&lt;!&ndash; 如果人名列表为空，就什么都不显示 &ndash;&gt;-->
    <!--					<xsl:when test="$count &gt; $person.count"></xsl:when>-->
    <!--					<xsl:otherwise>-->

    <!--						&lt;!&ndash; 不同类型的人名显示方式不同 &ndash;&gt;-->
    <!--						<xsl:choose>-->
    <!--							<xsl:when-->
    <!--									test="$count = 1 and $person.type = 'author' or $person.type = 'corpauthor'">-->
    <!--								<xsl:call-template name="gentext.by" />-->
    <!--								<xsl:call-template name="gentext.space" />-->
    <!--							</xsl:when>-->
    <!--							<xsl:when test="$count = 1 and $person.type = 'editor'">-->
    <!--								<xsl:call-template name="gentext.editors" />-->
    <!--								<xsl:call-template name="gentext.space" />-->
    <!--							</xsl:when>-->
    <!--							<xsl:when test="$count = 1 and $person.type = 'othercredit'">-->
    <!--								<xsl:call-template name="gentext.others" />-->
    <!--								<xsl:call-template name="gentext.space" />-->
    <!--							</xsl:when>-->
    <!--						</xsl:choose>-->

    <!--						&lt;!&ndash; Output each person's name &ndash;&gt;-->
    <!--						<xsl:call-template name="person.name">-->
    <!--							<xsl:with-param name="node"-->
    <!--											select="$person.list[position()=$count]" />-->
    <!--						</xsl:call-template>-->

    <!--						<xsl:choose>-->
    <!--							&lt;!&ndash; Put parathenses around short affiliation descriptions &ndash;&gt;-->
    <!--							<xsl:when-->
    <!--									test="$person.list[position()=$count]/affiliation/shortaffil">-->
    <!--								<xsl:call-template name="gentext.space" />-->
    <!--								<xsl:text>(</xsl:text>-->
    <!--								<xsl:value-of-->
    <!--										select="$person.list[position()=$count]/affiliation/shortaffil" />-->
    <!--								<xsl:text>)</xsl:text>-->
    <!--							</xsl:when>-->
    <!--						</xsl:choose>-->

    <!--						<xsl:choose>-->
    <!--							&lt;!&ndash; If only two names are present then insert 'and' between them &ndash;&gt;-->
    <!--							<xsl:when test="$person.count = 2 and $count = 1">-->
    <!--								<xsl:call-template name="gentext.template">-->
    <!--									<xsl:with-param name="context" select="'authorgroup'" />-->
    <!--									<xsl:with-param name="name" select="'sep2'" />-->
    <!--								</xsl:call-template>-->
    <!--							</xsl:when>-->
    <!--							&lt;!&ndash; If we get to the last name insert 'and' before it &ndash;&gt;-->
    <!--							<xsl:when test="$person.count &gt; 2 and $count+1 = $person.count">-->
    <!--								<xsl:call-template name="gentext.template">-->
    <!--									<xsl:with-param name="context" select="'authorgroup'" />-->
    <!--									<xsl:with-param name="name" select="'seplast'" />-->
    <!--								</xsl:call-template>-->
    <!--							</xsl:when>-->
    <!--							&lt;!&ndash; If we are in the middle of a list insert a comma between names &ndash;&gt;-->
    <!--							<xsl:when test="$count &lt; $person.count">-->
    <!--								<xsl:call-template name="gentext.template">-->
    <!--									<xsl:with-param name="context" select="'authorgroup'" />-->
    <!--									<xsl:with-param name="name" select="'sep'" />-->
    <!--								</xsl:call-template>-->
    <!--							</xsl:when>-->
    <!--						</xsl:choose>-->

    <!--						&lt;!&ndash; Recursively call the template to process all the names in the-->
    <!--							list &ndash;&gt;-->
    <!--						<xsl:call-template name="person.name.list">-->
    <!--							<xsl:with-param name="person.list" select="$person.list" />-->
    <!--							<xsl:with-param name="person.count" select="$person.count" />-->
    <!--							<xsl:with-param name="count" select="$count+1" />-->
    <!--						</xsl:call-template>-->
    <!--					</xsl:otherwise>-->
    <!--				</xsl:choose>-->
    <!--			</xsl:when>-->
    <!--			<xsl:otherwise>-->
    <!--				<xsl:apply-imports />-->
    <!--			</xsl:otherwise>-->
    <!--		</xsl:choose>-->
    <!--	</xsl:template>-->

    <!--	<xsl:template name="gentext.editors">-->
    <!--		<xsl:text>编辑：</xsl:text>-->
    <!--	</xsl:template>-->

    <!--	<xsl:template name="gentext.others">-->
    <!--		<xsl:text>感谢：</xsl:text>-->
    <!--	</xsl:template>-->
    <!--  自定义模板 end  -->

    <!-- 本地化扩展 begin -->
    <!-- 设置默认的本地化导航标签 -->
    <xsl:param name="local.l10n.xml" select="document('')"/>
    <l:i18n xmlns:l="http://docbook.sourceforge.net/xmlns/l10n/1.0">
        <l:l10n language="en">
            <l:gentext key="nav-home" text="Front page"/>
        </l:l10n>
        <l:l10n language="zh_cn">
            <l:gentext key="nav-home" text="首页"/>
        </l:l10n>
        <l:l10n language="zh">
            <l:gentext key="nav-home" text="首页"/>
        </l:l10n>
    </l:i18n>
    <l:i18n xmlns:l="http://docbook.sourceforge.net/xmlns/l10n/1.0">
        <l:l10n language="en">
            <l:gentext key="nav-up" text="Top of page"/>
        </l:l10n>
        <l:l10n language="zh_cn">
            <l:gentext key="nav-up" text="页首"/>
        </l:l10n>
        <l:l10n language="zh">
            <l:gentext key="nav-up" text="页首"/>
        </l:l10n>
    </l:i18n>
    <!--  本地化扩展 end  -->




</xsl:stylesheet>
