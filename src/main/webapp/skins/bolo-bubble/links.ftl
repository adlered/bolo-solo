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
    <@head title="${linkLabel} - ${blogTitle}">
        <link rel="stylesheet" href="${staticServePath}/skins/${skinDirName}/css/base.css?${staticResourceVersion}"/>
    </@head>
</head>
<body class="fn__flex-column">
<div id="pjax" class="fn__flex-1">
    <#if pjax><!---- pjax {#pjax} start ----></#if>
    <#include "macro-header.ftl">
    <@header type='index'></@header>
    <div class="wrapper">
        <h2 class="other__title"><a href="${servePath}" class="ft__a">${blogTitle}</a> - ${linkLabel}</h2>
        <div class="ft__center">
            ${links?size} ${linkLabel}
        </div>
        <div class="articles">
            <br>
            <#if 0 != links?size>
                <#list links as link>
                    <div class="other__item">
                        <a rel="friend" href="${link.linkAddress}" target="_blank">
                            ${link.linkTitle}
                        </a>
                        <div>${link.linkDescription}</div>
                    </div>
                </#list>
            </#if>
            <br><br>
        </div>
    </div>
    <#if pjax><!---- pjax {#pjax} end ----></#if>
    <#include "footer.ftl">
</body>
</html>
