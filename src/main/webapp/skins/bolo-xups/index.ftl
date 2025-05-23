<#--

    Bolo - A stable and beautiful blogging system based in Solo.
    Copyright (c) 2020-present, https://github.com/bolo-blog

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

-->
<#include "../../common-template/macro-common_head.ftl">
<!DOCTYPE html>
<html>
<head>
    <@head title="${blogTitle}">
        <link rel="stylesheet" href="${staticServePath}/skins/${skinDirName}/css/base.css?${staticResourceVersion}"/>
        <link rel="stylesheet" href="${staticServePath}/skins/${skinDirName}/css/style.css?${staticResourceVersion}" />
        <link href="https://ftp.stackoverflow.wiki/bolo/start/css/font-awesome.min.css" rel="stylesheet">
    </@head>
</head>
<body class="body--gray">
<#include "header.ftl">
<main id="pjax" class="fn__flex-1">
    <div class="wrapper wrapper--min">
        <div id="pjax">
            <#if pjax><!---- pjax {#pjax} start ----></#if>
        <div class="container body clearfix">
            <#include "article-list.ftl">
            <#include "side.ftl">
        </div>
            <#if pjax><!---- pjax {#pjax} end ----></#if>
        </div>
    </div>
</main>
<#include "footer.ftl">
</body>
</html>