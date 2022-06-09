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
        <link rel="stylesheet" href="${staticServePath}/js/lib/mdui.min.css">
        <link rel="stylesheet" href="${staticServePath}/js/lib/atom-one-dark.css">
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
            <style>
                .nexmoe-archives li {
                    position: relative;
                    padding: 10px 0;
                }

                .nexmoe-archives li::before {
                    content: "";
                    width: 14px;
                    height: 14px;
                    background: #ff4e6a;
                    display: inline-block;
                    vertical-align: middle;
                    margin-top: -2px;
                    margin-right: 11px;
                    border-radius: 100%;
                    border: 3px solid #fff;
                    z-index: 100;
                    position: relative;
                }

                .nexmoe-archives ul {
                    list-style: none;
                    padding-left: 0 !important;
                }

                .nexmoe-archives li::after {
                    content: "";
                    height: 100%;
                    width: 2px;
                    background: rgba(255, 78, 106, .2);
                    position: absolute;
                    left: 6px;
                    top: 20px;
                }

                .nexmoe-archives span {
                    margin-right: 15px;
                }
            </style>
            <div class="nexmoe-archives">
                <div class="nexmoe-post">
                    <article>
                        <div id="archives">
                        <#if 0 != archiveDates?size>
                            <#assign last=""/>
                            <#list archiveDates as archiveDate>
                                <#if archiveDate.archiveDateYear != last>
                                    <h2>${archiveDate.archiveDateYear} ${yearLabel}</h2>
                                </#if>
                                <#assign last=archiveDate.archiveDateYear/>
                                <ul class="article1">
                                    <li>
                                        <span>${archiveDate.archiveDateMonth} ${monthLabel}</span>
                                        <a href="${servePath}/archives/${archiveDate.archiveDateYear}/${archiveDate.archiveDateMonth}" style="cursor: pointer;">
                                            撰写了 ${archiveDate.archiveDatePublishedArticleCount} ${cntArticleLabel}
                                        </a>
                                    </li>
                                </ul>
                            </#list>
                        </#if>
                        </div>
                    </article>
                </div>
            </div> <#if pjax><!---- pjax {#pjax} end ----></#if>
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
