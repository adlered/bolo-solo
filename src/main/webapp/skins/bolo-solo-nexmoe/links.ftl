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
<#include "../../common-template/macro-common_head.ftl">
<!DOCTYPE html>
<html>
<head>
    <@head title="${linkLabel} - ${blogTitle}">
        <link rel="stylesheet" href="${staticServePath}/skins/${skinDirName}/css/base.css?${staticResourceVersion}"/>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/mdui@0.4.3/dist/css/mdui.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/highlight.js@9.15.8/styles/atom-one-dark.css">
        <link rel="stylesheet"
              href="${staticServePath}/skins/${skinDirName}/css/font-icon.css?${staticResourceVersion}">
    </@head>
</head>
<body class="mdui-drawer-body-left mdui-loaded">
<div id="nexmoe-background">
    <#include "header.ftl">
</div>
<div id="nexmoe-header">
    <#include "side.ftl">
</div>
<div id="nexmoe-content">
    <div class="nexmoe-primary">
        <main id="pjax" class="fn__flex-1">
            <#if pjax><!---- pjax {#pjax} start ----></#if>
            <article class="links nexmoe-py">
                <h2>${linkLabel}</h2>
                <#if 0 != links?size>
                    <#if 'list' != customVars.key1>
                        <#list links as link>
                            <div class="other__item">
                                <a href="${link.linkAddress}" target="_blank"
                                   rel="noopener">
                                    ${link.linkTitle}
                                </a>
                                <div>${link.linkDescription}</div>
                            </div>
                        </#list>
                    <#else>
                        <ul>
                            <#list links as link>
                                <li>
                                    <a href="${link.linkAddress}" title="${link.linkTitle}" target="_blank"
                                       rel="noopener">
                                        <img src="${link.linkIcon}"
                                             alt="${link.linkDescription}">
                                    </a>
                                </li>
                            </#list>
                        </ul>
                    </#if>
                </#if>
            </article>
            <#if pjax><!---- pjax {#pjax} end ----></#if>
        </main>

        <#if "" != noticeBoard>
            <div class="nexmoe-hitokoto">
                <p id="hitokoto">${blogSubtitle}</p>
            </div>
        </#if>
        <div class="back-to-top iconfont solo-gotop" onclick="Util.goTop()"></div>
    </div>
</div>
<#include "footer.ftl">
</body>
</html>