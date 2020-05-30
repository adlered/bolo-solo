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
<div id="mo-nav">
    <div class="m-avatar">
        <img src="${adminUser.userAvatar}">
    </div>
    <p style="text-align: center; color: #333; font-weight: 900; font-family: 'Ubuntu', sans-serif; letter-spacing: 1.5px">
        ${blogSubtitle}
    </p>
    <p>
    <div>
        <style>
            .mobile {
                text-align: center;
            }

            .mobile .user__site {
                width: 16px;
                height: 16px;
                display: inline-block;
            }

            .mobile .user__site svg {
                width: 16px;
                height: 16px;
                vertical-align: middle;
            }
        </style>
        <div class="mobile">
            <#include "../../common-template/macro-user_site.ftl"/>
            <@userSite dir=""></@userSite>
        </div>
    </div>
    </p>
    <#if interactive == "on">
    <div class="m-search">
        <form class="m-search-form" method="get" action="${servePath}/search" role="search">
            <input class="m-search-input" type="search" name="keyword" placeholder="搜索..." required>
        </form>
    </div>
    </#if>
    <ul id="menu-new-1" class="menu">
        <#if interactive == "on">
        <#if isLoggedIn>
            <li><a href="${servePath}/admin-index.do#main"><span class="faa-parent animated-hover"><img src="${gravatar}" width="15px" height="15px" style="border-radius:50%"> ${adminLabel}</span></a>
        <#else>
            <li><a href="${servePath}/start"><span class="faa-parent animated-hover"><i
                            class="fa fa-user faa-horizontal" aria-hidden="true"></i> ${startToUseLabel}</span></a>
        </#if>
        </#if>
        <li><a href="${servePath}/"><span class="faa-parent animated-hover"><i
                            class="fa fa-home faa-horizontal" aria-hidden="true"></i> 首页</span></a>
        <li><a href="${servePath}/links.html"><span class="faa-parent animated-hover"><i
                            class="fa fa-link faa-horizontal" aria-hidden="true"></i> 友链</span></a>
        <li><a href="${servePath}/tags.html"><span class="faa-parent animated-hover"><i
                            class="fa fa-tag faa-horizontal" aria-hidden="true"></i> 标签</span></a>
        <li><a href="${servePath}/archives.html"><span class="faa-parent animated-hover"><i
                            class="fa fa-archive faa-horizontal" aria-hidden="true"></i> 归档</span></a>
        <#list pageNavigations as page>
            <li><a href="${page.pagePermalink}" target="${page.pageOpenTarget}"><span
                            class="faa-parent animated-hover">
                                            <#if page.pageIcon?contains("/")>
                                                <img class="icon faa-horizontal" aria-hidden="true" style="height: 16px; width: 16px;"
                                                     src="${page.pageIcon}">
                                            <#else>
                                                <i class="fa fa-${page.pageIcon} faa-horizontal" aria-hidden="true"></i>
                                            </#if> ${page.pageTitle}</span></a></li>
        </#list>
    </ul>
    <p style="text-align: center; font-size: 13px; color: #b9b9b9;">
        © ${year} <a href="${servePath}">${blogTitle}</a><br><br>
        Powered by <a href="https://github.com/adlered/bolo-solo" target="_blank">Bolo</a><br>
        Theme <a rel="friend" href="https://github.com/mashirozx/Sakura" target="_blank">${skinDirName}</a>
        by <a rel="friend" href="https://hello.2heng.xin/@mashiro" target="_blank">Mashiro</a><br>
        <span>${viewLabel}</span>
        ${statistic.statisticBlogViewCount}
        <span>${articleLabel}</span>
        ${statistic.statisticPublishedBlogArticleCount}
        <#if interactive == "on">
        <span>${commentLabel}</span>
        ${statistic.statisticPublishedBlogCommentCount}<br>
        </#if>
        ${footerContent}
    </p>
</div>
<a href="#" class="cd-top faa-float animated "></a>
<button onclick="topFunction()" class="mobile-cd-top" id="moblieGoTop" title="Go to top"><i class="fa fa-chevron-up"
                                                                                            aria-hidden="true"></i>
</button>
