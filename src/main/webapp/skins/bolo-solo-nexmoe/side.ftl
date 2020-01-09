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
<div class="nexmoe-drawer mdui-drawer" id="drawer">
    <div class="nexmoe-avatar mdui-ripple">
        <a href="${servePath}/" title="${blogTitle}">
            <img src="${adminUser.userAvatar}" title="${blogTitle}" alt="${userName}"></a>
    </div>
    <div class="nexmoe-count">
        <div>
            <span>${articleLabel}</span>
            ${statistic.statisticPublishedBlogArticleCount}
        </div>
        <div>
            <span>${commentLabel}</span>
            ${statistic.statisticPublishedBlogCommentCount}
        </div>
        <div>
            <span>${viewLabel}</span>
            ${statistic.statisticBlogViewCount}
        </div>
    </div>
    <div class="list-content">
        <ul class="nexmoe-list mdui-list header__nav" mdui-collapse="{accordion: true}">
            <a class="nexmoe-list-item mdui-list-item mdui-ripple" href="${servePath}/" title="${blogTitle}">
                <i class="mdui-list-item-icon iconfont solo-home"></i>
                <div class="mdui-list-item-content">回到首页</div>
            </a>
            <a class="nexmoe-list-item mdui-list-item mdui-ripple" href="${servePath}/links.html"
               title="${linkLabel} - ${blogTitle}">
                <i class="mdui-list-item-icon iconfont solo-list"></i>
                <div class="mdui-list-item-content">${linkLabel}</div>
            </a>
            <a class="nexmoe-list-item mdui-list-item mdui-ripple" href="${servePath}/tags.html"
               title="${tagLabel} - ${blogTitle}">
                <i class="mdui-list-item-icon iconfont solo-tags"></i>
                <div class="mdui-list-item-content">${tagLabel}</div>
            </a>
            <#list pageNavigations as page>
                <a class="nexmoe-list-item mdui-list-item mdui-ripple" href="${page.pagePermalink}"
                   title="${page.pageTitle}" target="${page.pageOpenTarget}">
                    <i class="mdui-list-item-icon iconfont solo-${page.pageIcon}"></i>
                    <div class="mdui-list-item-content">${page.pageTitle}</div>
                </a>
            </#list>
        </ul>
    </div>
    <aside id="nexmoe-sidebar">

        <div class="nexmoe-widget-wrap">
            <h3 class="nexmoe-widget-title">功能按钮</h3>
            <div class="nexmoe-widget nexmoe-social features">
                <a href="${servePath}/search" title="搜索">
                    <i class="mdui-list-item-icon iconfont solo-search"></i>
                    <div class="mdui-list-item-content">搜索</div>
                </a>
                <a href="${servePath}/rss.xml" title="RSS">
                    <i class="mdui-list-item-icon iconfont solo-rss"></i>
                </a>
                <#if isLoggedIn>
                    <a href="${servePath}/admin-index.do#main" target="_blank"
                       title="${adminLabel}">
                        <i class="mdui-list-item-icon iconfont solo-spin"></i>
                    </a>
                    <a href="${logoutURL}" title="${logoutLabel}">
                        <i class="mdui-list-item-icon iconfont solo-logout"></i>
                    </a>
                <#else>
                    <a href="${servePath}/start" title="${startToUseLabel}">
                        <i class="mdui-list-item-icon iconfont solo-login"></i>
                        <div class="mdui-list-item-content"> ${startToUseLabel}</div>
                    </a>
                </#if>
            </div>
        </div>

        <div class="nexmoe-widget-wrap">
            <h3 class="nexmoe-widget-title">社交按钮</h3>
            <div class="nexmoe-widget nexmoe-social">
                <#include "../../common-template/macro-user_site.ftl"/>
                <@userSite dir=""></@userSite>
            </div>
        </div>

        <#if noticeBoard?? && 'bulletin'== customVars.key0>
            <div class="nexmoe-widget-wrap">
                <h3 class="nexmoe-widget-title">公告栏</h3>
                <div class="nexmoe-widget tagcloud">
                    <div class="links-of-author">
                        ${noticeBoard}
                    </div>
                </div>
            </div>
        <#elseif  0 != mostUsedTags?size>
            <div class="nexmoe-widget-wrap">
                <h3 class="nexmoe-widget-title">${tagLabel}</h3>
                <div class="nexmoe-widget tagcloud">
                    <#list mostUsedTags as tag>
                        <a rel="tag"
                           href="${servePath}/tags/${tag.tagTitle?url('UTF-8')}"
                           title="${tagLabel}:${tag.tagTitle} - ${blogTitle}"
                           class="mdui-ripple">
                            ${tag.tagTitle}</a>
                    </#list>
                </div>
            </div>
        </#if>


        <#if 0 != mostUsedCategories?size>
            <div class="nexmoe-widget-wrap">
                <h3 class="nexmoe-widget-title">${categoryLabel}</h3>
                <div class="nexmoe-widget">
                    <ul class="category-list">
                        <#list mostUsedCategories as category>
                            <li class="category-list-item">
                                <a class="category-list-link mdui-ripple"
                                   href="${servePath}/category/${category.categoryURI}"
                                   title="${category.categoryTitle} - ${blogTitle}">
                                    ${category.categoryTitle}</a>
                                <span class="category-list-count">${category.categoryPublishedArticleCount}</span>
                            </li>
                        </#list>
                    </ul>
                </div>
            </div>
        </#if>


        <#if 0 != archiveDates?size>
            <div class="nexmoe-widget-wrap">
                <h3 class="nexmoe-widget-title">${archiveLabel}</h3>
                <div class="nexmoe-widget">
                    <ul class="category-list">
                        <#list archiveDates as archiveDate>
                            <li class="category-list-item">
                                <#if "en" == localeString?substring(0, 2)>
                                    <a class="category-list-link mdui-ripple"
                                       href="${servePath}/archives/${archiveDate.archiveDateYear}/${archiveDate.archiveDateMonth}"
                                       title="${archiveDate.archiveDateYear} ${yearLabel} ${archiveDate.archiveDateMonth} ${monthLabel} ${archiveLabel} - ${blogTitle}">
                                        ${archiveDate.monthName} ${archiveDate.archiveDateYear}</a><span
                                        class="category-list-count">${archiveDate.archiveDatePublishedArticleCount}</span>
                                <#else>
                                    <a href="${servePath}/archives/${archiveDate.archiveDateYear}/${archiveDate.archiveDateMonth}"
                                       title="${archiveDate.archiveDateYear} ${yearLabel} ${archiveDate.archiveDateMonth} ${monthLabel} ${archiveLabel} - ${blogTitle}">
                                        ${archiveDate.archiveDateYear} ${yearLabel} ${archiveDate.archiveDateMonth} ${monthLabel}</a>
                                    <span class="category-list-count">${archiveDate.archiveDatePublishedArticleCount}</span>
                                </#if>
                            </li>
                        </#list>
                    </ul>
                </div>
            </div>
        </#if>

    </aside>
    <div class="nexmoe-copyright">
        © ${year} <a href="${servePath}">${blogTitle}</a> <br/>
        ${footerContent} <br>
        Powered by <a href="https://github.com/AdlerED/bolo-solo" target="_blank">菠萝博客 Bolo</a> <br>
        Theme <a rel="friend" href="https://github.com/InkDP/solo-nexmoe" target="_blank">${skinDirName}</a>
        by <a rel="friend" href="https://www.inkdp.cn" target="_blank">InkDP</a>
    </div>
</div>
