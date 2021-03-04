<#--

    Bolo - A stable and beautiful blogging system based in Solo.
    Copyright (c) 2020, https://github.com/adlered

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
    <#include "index-header.ftl">
    <@header type='index'></@header>
    <main class="layout_post" id="content-inner">
        <article id="post">
            <div class="wrapper web-topage">
                <h2 class="other__title"><a href="${servePath}" class="ft__a">${blogTitle}</a> - ${linkLabel}</h2>
                <div class="ft__center">
                    ${links?size} ${linkLabel}
                </div>
                <div class="articles vditor-reset link_main">
                    <div class="flink-list">
                        <#if 0 != links?size>
                            <#list links as link>
                                <div class="flink-list-item"
                                     style="--primary-color:linear-gradient( 135deg, #FFF886 10%, #F072B6 100%);
                     border-width:1px;border-style:solid;">
                                    <a href="${link.linkAddress}" title="${link.linkTitle}" target="_blank">
                                        <img class="rauto"
                                             data-lazy-src="https://blog.lete114.top/img/Lete.png"
                                             onerror="null"
                                             alt="${link.linkIcon}"
                                             src="${link.linkIcon}">
                                        <span class="flink-item-name">${link.linkTitle}</span>
                                        <span class="flink-item-desc">${link.linkDescription}</span>
                                    </a>
                                </div>
                            </#list>
                        </#if>
                    </div>
            </div>
        </article>
    </main>
    <#if pjax><!---- pjax {#pjax} end ----></#if>
</div>
    <#include "footer.ftl">
</body>
</html>
