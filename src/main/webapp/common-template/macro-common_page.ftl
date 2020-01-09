<#--

    Solo - A small and beautiful blogging system written in Java.
    Copyright (c) 2010-present, b3log.org

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
<#include "macro-common_head.ftl"/>
<#macro commonPage title>
<!DOCTYPE html>
<html>
<head>
    <#if !blogTitle??>
    <#assign blogTitle = "Bolo">
    </#if>
    <@head title="${title} - ${blogTitle}">
        <link type="text/css" rel="stylesheet"
        href="${staticServePath}/scss/start.css?${staticResourceVersion}" charset="utf-8"/>
        <meta name="robots" content="none"/>
    </@head>
</head>
<body>
<div class="wrap">
    <div class="content-wrap">
        <div class="content">
            <div class="main">
            <#nested>
            </div>
        </div>
    </div>
    <div class="footerWrapper">
        <div class="footer">
            Powered by <a href="https://github.com/AdlerED/bolo-solo" target="_blank">菠萝博客 Bolo</a> ${version}
        </div>
    </div>
</div>
</body>
</html>
</#macro>
