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
                <h3 class="link-title"><span class="fake-title">分类</span></h3>
                <#list mostUsedCategories as category>
                    <span style="width: 256px; display: inline-block">
                        <a href="${servePath}/category/${category.categoryURI}">
                            ${category.categoryTitle}
                        </a>
                        <span>${category.categoryTagCnt} 篇</span>
                    </span>
                </#list>

                <h3 class="link-title"><span class="fake-title">标签</span></h3>
                <#list tags as tag>
                    <span style="width: 256px; display: inline-block">
                        <a rel="tag" data-count="${tag.tagPublishedRefCount}" class="tag"
                           href="${servePath}/tags/${tag.tagTitle?url('UTF-8')}">
                            ${tag.tagTitle}
                        </a>
                        <span>${tag.tagPublishedRefCount} ${countLabel}</span>
                    </span>
                </#list>
            </div>
        </div>
    </div>
    <#include 'macro-footer.ftl'>
</section>
<#include 'side-mobile.ftl'>
<#include 'footer.ftl'>
</body>
</html>