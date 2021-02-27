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
    <@head title="时间轴 - ${blogTitle}">
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
            <div class="time_axis_main">
                <div>
                    <div class="times_axis_h1_span" style="text-align: center;"><h1>时间轴</h1>
                        <br>
                        <span>${archiveDates?size} ${cntMonthLabel}</span>
                        <br>
                        <span>${statistic.statisticPublishedBlogArticleCount} ${cntArticleLabel}
                </span>
                    </div>
                </div>
                <article class="art">
                    <div class="art-main">
                        <div class="art-content">
                            <div id="archives"><p style="text-align:right;font-size: 1.1rem">[<span
                                            id="al_expand_collapse"
                                            style="cursor: pointer;">全部展开/收缩</span>]
                                </p>
                                <#if 0 != archiveDates?size>
                                    <#assign last=""/>
                                    <#list archiveDates as archiveDate>
                                        <#if archiveDate.archiveDateYear != last>
                                            <h3 class="al_year">${archiveDate.archiveDateYear} ${yearLabel}</h3>
                                        </#if>
                                        <#assign last=archiveDate.archiveDateYear/>
                                        <ul class="al_mon_list">
                                            <li class="al_li">
                                                <span class="al_mon"
                                                      style="cursor: s-resize;">${archiveDate.archiveDateMonth} ${monthLabel}</span>
                                                <ul class="al_post_list" style="">
                                                    <li>
                                                        <a href="${servePath}/archives/${archiveDate.archiveDateYear}/${archiveDate.archiveDateMonth}"><span
                                                                    style="color:#0bf;">撰写了 </span>${archiveDate.archiveDatePublishedArticleCount} ${cntArticleLabel}
                                                        </a>
                                                    </li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </#list>
                                </#if>
                            </div>
                        </div>
                    </div>
                </article>


            </div>
        </article>
    </main>
    <#if pjax><!---- pjax {#pjax} start ----></#if>
</div>
<#include "footer.ftl">
</body>
</html>