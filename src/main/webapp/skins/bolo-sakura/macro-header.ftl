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
<div id="banner_wave_1">
</div>
<div id="banner_wave_2">
</div>
<figure id="centerbg" class="centerbg">
    <div class="focusinfo no-select">
        <h1 class="center-text glitch is-glitching Ubuntu-font" data-text="${blogTitle}">${blogTitle}</h1>
        <div class="header-info">
            <p>
                <#if blogSubtitle??>
                    <i class="fa fa-quote-left"></i> ${blogSubtitle} <i class="fa fa-quote-right"></i>
                <#else>
                    <i class="fa fa-quote-left"></i> Nothing here... <i class="fa fa-quote-right"></i>
                </#if>
            </p>
            <div class="top-social_v2">
                <style>
                    .pc .user__site {
                        width: 32px;
                        height: 32px;
                        float: left;
                        background-color: #f1f2f7;
                        border-radius: 50%;
                        margin-right: 10px;
                        opacity: 0.6;
                    }

                    .pc .user__site svg {
                        vertical-align:middle;
                        width: 20px;
                        height: 20px;
                    }
                </style>
                <div class="pc">
                    <#include "../../common-template/macro-user_site.ftl"/>
                    <@userSite dir=""></@userSite>
                </div>
            </div>
        </div>
    </div>
</figure>
<div id="video-container" style="">
    <video id="bgvideo" class="video" video-name="" src="" width="auto" preload="auto"></video>
    <!--<div id="video-btn" class="loadvideo videolive">
    </div>-->
    <div id="video-add">
    </div>
    <div class="video-stu">
    </div>
</div>
<div class="headertop-down faa-float animated" onclick="headertop_down()">
    <span><i class="fa fa-chevron-down" aria-hidden="true"></i></span>
</div>
</div>
<div id="page" class="site wrapper">
    <header class="site-header no-select is-homepage" role="banner">
        <div class="site-top">
            <div class="site-branding">
                <span class="site-title"><span class="logolink moe-mashiro"><a href="${servePath}/" alt="${blogTitle}" style="width: 330px;"><ruby>
                                <span class="no">#</span>&nbsp;<span class="shironeko">${blogTitle}</span><rp></rp><rt class="chinese-font">${blogSubtitle}</rt><rp></rp></ruby></a></span></span>
            </div>
            <div class="lower-cantiner">
                <div class="lower">
                    <div id="show-nav" class="showNav mobile-fit">
                        <div class="line line1">
                        </div>
                        <div class="line line2">
                        </div>
                        <div class="line line3">
                        </div>
                    </div>
                    <nav class="mobile-fit-control hide">
                        <ul id="menu-new" class="menu">
                            <li><a href="${servePath}/"><span class="faa-parent animated-hover"><i
                                                class="fa fa-home faa-horizontal" aria-hidden="true"></i> 首页</span></a></li>
                            <li><a href="${servePath}/links.html"><span class="faa-parent animated-hover"><i
                                                class="fa fa-link faa-horizontal" aria-hidden="true"></i> 友链</span></a></li>
                            <li><a href="${servePath}/tags.html"><span class="faa-parent animated-hover"><i
                                                class="fa fa-tag faa-horizontal" aria-hidden="true"></i> 标签</span></a></li>
                            <li><a href="${servePath}/archives.html"><span class="faa-parent animated-hover"><i
                                                class="fa fa-archive faa-horizontal" aria-hidden="true"></i> 归档</span></a></li>
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
                    </nav>
                </div>
            </div>
            <div class="header-user-avatar">
                <#if interactive == "on">
                <#if isLoggedIn>
                    <a href="${servePath}/admin-index.do#main"><img class="faa-shake animated-hover"
                                                      src="${gravatar}"
                                                      width="30" height="30"></a>
                    <div class="header-user-menu">
                        <div class="herder-user-name logged-in-as">
                            ${adminLabel}
                        </div>
                    </div>
                <#else>
                    <a href="${servePath}/start"><img class="faa-shake animated-hover"
                                                                  src="https://ftp.stackoverflow.wiki/bolo/moezx/cdn/img/Sakura/images/none.png"
                                                                  width="30" height="30"></a>
                    <div class="header-user-menu">
                        <div class="herder-user-name no-logged">
                            ${startToUseLabel}
                        </div>
                    </div>
                </#if>
                </#if>

            </div>
            <#if interactive == "on">
            <div class="searchbox">
                <i class="iconfont js-toggle-search iconsearch icon-search"></i>
            </div>
            </#if>
        </div>
    </header>
    <div class="blank">
    </div>
