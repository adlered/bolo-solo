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
<#include 'header.ftl'>
<body nonce-data="4fb3a4be0d" class="home blog hfeed chinese-font">
<div class="scrollbar" id="bar">
</div>
<section id="main-container">
    <div class="headertop filter-dot">
        <#include 'macro-header.ftl'>
        <div id="content" class="site-content">
            <div id="primary" class="content-area">
                <header class="page-header">
                    <h1 class="cat-title">归档</h1>
                    <span class="cat-des"><p>Archives</p> </span>
                    <br>
                    ${archiveDates?size} ${cntMonthLabel}
                    <br>
                    ${statistic.statisticPublishedBlogArticleCount} ${cntArticleLabel}
                </header>
                <div id="main-part">
                    <article class="art">
                        <div class="art-main">
                            <div class="art-content">
                                <div id="archives"><p style="text-align:right;">[<span id="al_expand_collapse"
                                                                                       style="cursor: s-resize;">全部展开/收缩</span>]
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
                                                    <span class="al_mon" style="cursor: s-resize;">${archiveDate.archiveDateMonth} ${monthLabel}</span>
                                                    <ul class="al_post_list" style="">
                                                        <li><a href="${servePath}/archives/${archiveDate.archiveDateYear}/${archiveDate.archiveDateMonth}"><span
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
            </div>
        </div>
    </div>
    <#include 'macro-footer.ftl'>
</section>
<#include 'side-mobile.ftl'>
<#include 'footer.ftl'>
</body>
</html>