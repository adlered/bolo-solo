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
    <@head title="${allTagsLabel} - ${blogTitle}">
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
                <h2 class="other__title"><a href="${servePath}" class="ft__a">${blogTitle}</a> - ${allTagsLabel}</h2>
                <div class="ft__center">
                    ${tags?size} ${tagLabel}
                </div>
                <div class="articles">
                    <div class="fn__clear tagcloud">
                        <#list tags as tag>
                            <a rel="tag" data-count="${tag.tagPublishedRefCount}" class="other__item fn__left"
                               href="${servePath}/tags/${tag.tagTitle?url('UTF-8')}">
                                <#--                        <svg t="1595733525468" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4982" width="200" height="200"><path d="M970.8 325.7l-253.1-46c-15-2.7-29.5 7.5-32.3 22.9L613 701.4c-1.3 7.1 0.1 14.4 3.9 20.4l28.5 45.3c2.9-3 6.4-5.6 10.4-7.4 17.5-8.1 38.3-0.5 46.5 17s0.5 38.3-17 46.5c-1.2 0.6-2.5 1.1-3.7 1.5l36.8 58.5c8.6 13.7 26.8 17 39.7 7.2L909.7 775c5.7-4.3 9.5-10.6 10.8-17.7L993 358.5c2.8-15.4-7.1-30.1-22.2-32.8z" fill="#1565C0" p-id="4983"></path><path d="M636.3 218.8L541 231.4l114.9 528.1c17.5-8 38.2-0.4 46.3 17 8.1 17.5 0.5 38.3-17 46.5-4.8 2.2-9.8 3.3-14.8 3.3l5.1 23.3 45.9 38.4c14.1 11.8 34.9 9 45.4-6l123.9-177.6c4.6-6.6 6.6-14.8 5.5-23l-56.5-424.9-4.2-31.7c-2.3-17.6-18.2-30.1-35.4-27.8l-163.8 21.8z" fill="#F44336" p-id="4984"></path><path d="M896.2 681.4l-56.5-424.9-203.4-37.7-95.3 12.6 114.9 528.1c17.5-8 38.2-0.4 46.3 17 8.1 17.5 0.5 38.3-17 46.5-4.8 2.2-9.8 3.3-14.8 3.3l5.1 23.3 45.9 38.4c14.1 11.8 34.9 9 45.4-6l123.9-177.6c4.6-6.7 6.6-14.9 5.5-23z" fill="#D32F2F" p-id="4985"></path><path d="M836.1 613.7L660 260.9l-116.2-21.5L660 260.9l-52.2-104.7c-8.8-17.7-29.9-25-47.2-16.4L270.2 284.7c-17.2 8.6-24.1 29.9-15.2 47.6l228.3 457.5c4.1 8.1 11 14.4 19.4 17.7l138.1 53.1-224.4-644.9 224.4 644.8 86.3 33.1c19.1 7.3 39.9-3.1 45.5-22.7l66-231.1c2.4-8.7 1.5-18-2.5-26.1zM685.2 823.1c-17.5 8.1-38.3 0.5-46.5-17s-0.5-38.3 17-46.5 38.3-0.5 46.5 17 0.5 38.3-17 46.5z" fill="#FFC107" p-id="4986"></path><path d="M836.1 613.7L660 260.9l-243.7-45.2 224.4 644.8 86.3 33.1c19.1 7.3 39.9-3.1 45.5-22.7l66-231.1c2.5-8.7 1.6-18-2.4-26.1zM685.2 823.1c-17.5 8.1-38.3 0.5-46.5-17s-0.5-38.3 17-46.5 38.3-0.5 46.5 17 0.5 38.3-17 46.5z" fill="#FFA000" p-id="4987"></path><path d="M771.3 587c-0.1-10.1-4.1-19.9-11.1-27.4l-394.6-420c-15.2-16.2-40.4-17.3-56.2-2.5L42.7 387.6c-15.8 14.9-16.3 40.1-1.1 56.3L436.2 864c7 7.5 16.5 12 26.6 12.8L733 897.2c22.9 1.7 42.1-16.3 41.8-39.3L771.3 587z m-86.1 236.1c-17.5 8.1-38.3 0.5-46.5-17s-0.5-38.3 17-46.5 38.3-0.5 46.5 17 0.5 38.3-17 46.5z" fill="#4CAF50" p-id="4988"></path></svg>-->
                                ${tag.tagTitle} - <b>${tag.tagPublishedRefCount}</b> ${countLabel}
                            </a>
                        </#list>
                    </div>
                    <br><br>
                </div>
            </div>
        </article>
    </main>
    <#if pjax><!---- pjax {#pjax} end ----></#if>
</div>
<#include "footer.ftl">
</body>
</html>
