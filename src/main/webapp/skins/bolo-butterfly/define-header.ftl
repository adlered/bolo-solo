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
<#include "macro-header.ftl">
<div id="web_bg" data-type="color"></div>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Titillium+Web&amp;display=swap">
<link media="all" href="${staticServePath}/skins/${skinDirName}/css/main.css?${staticResourceVersion}"
      rel="stylesheet"/>
<script src="${staticServePath}/js/lib/instantpage.js" type="module" defer></script>
<script src="${staticServePath}/js/lib/lazyload.iife.min.js" async></script>
<script src="${staticServePath}/js/lib/typed.js"></script>
<script src="${staticServePath}/skins/${skinDirName}/js/GlobalConfig.js"></script>
<script src="${staticServePath}/skins/${skinDirName}/js/utils.js"></script>
<script src="${staticServePath}/skins/${skinDirName}/js/activate-power-mode.js"></script>
<#macro header type>
    <header class="header--${type}">
        <nav id="nav" style="opacity: 1; animation: 1s ease 0s 1 normal none running headerNoOpacity;" class="">
            <span class="pull-left" id="blog_name">
                <a class="blog_title" id="site-name" href="${servePath}">${blogTitle} 👋🏼</a>
            </span>
            <span class="pull-right menus">
                <div class="menus_items">
                    <div class="menus_item">
                        <a class="site-page" href="${servePath}">
                            <i class="fa-fw fas fa-home"></i>
                            <span>
                                <#if type == 'article'>
                                    ${blogTitle}
                                <#else>
                                    ${indexLabel}
                                </#if>
                            </span>
                        </a>
                    </div>
                    <div class="menus_item">
                        <a class="site-page" href="${servePath}/timeaxis">
                            <i class="fa-fw fas fa-archive"></i>
                            <span> 时间轴</span>
                        </a>
                    </div>
                    <div class="menus_item">
                        <a class="site-page" href="${servePath}/tags.html">
                            <i class="fa-fw fas fa-tags"></i>
                            <span> 标签</span>
                        </a>
                    </div>
                    <div class="menus_item">
                        <a class="site-page" href="${servePath}/categories.html">
                            <i class="fa-fw fas fa-folder-open"></i>
                            <span> 分类</span></a>
                    </div>
                    <div class="menus_item">
                        <a class="site-page" href="${servePath}/links.html">
                            <i class="fa-fw fas fa-link"></i>
                            <span> 友情链接</span>
                        </a>
                    </div>
                    <div class="menus_item">
                        <a class="site-page">
                            <span class="footer__heart">❤️更多</span>
                            <i class="fas fa-chevron-down menus-expand"></i>
                        </a>
                        <ul class="menus_item_child">
                            <#list pageNavigations as page>
                                <li>
                                <a class="site-page" href="${page.pagePermalink}" target="${page.pageOpenTarget}">
                                <#if page.pageIcon != ''>
                                    <i class="fa-fw fas">
                                        <img class="page-icon" src="${page.pageIcon}">
                                    </i>
                                </#if>
                                    <span> ${page.pageTitle}</span>
                                </a>
                            </li>
                            </#list>
                        </ul>
                    </div>
                    <#if interactive == "on">
                    <div class="menus_item">
                        <a class="site-page">
                            <i class="fa-fw fas fa-list"></i>
                            <span> 管理后台</span>
                            <i class="fas fa-chevron-down menus-expand"></i>
                        </a>
                        <ul class="menus_item_child">
                                <#if isLoggedIn>
                                    <li>
                                        <a class="site-page" href="${servePath}/admin-index.do#main">
                                            <i class="fa-fw fas"><svg t="1595817408268" class="icon"
                                                                      viewBox="0 0 1024 1024" version="1.1"
                                                                      xmlns="http://www.w3.org/2000/svg" p-id="13737"
                                                                      width="200" height="200"><path
                                                            d="M728.7 511.9c0 9-3.3 16.8-9.9 23.5L435.4 818.8c-6.5 6.5-14.5 9.9-23.5 9.9s-16.8-3.3-23.5-9.9c-6.5-6.5-9.9-14.4-9.9-23.5v-150H145.4c-9 0-16.8-3.3-23.5-9.9-6.5-6.5-9.9-14.4-9.9-23.5v-200c0-9 3.3-16.8 9.9-23.5 6.5-6.5 14.4-9.9 23.5-9.9h233.3v-150c0-9 3.3-16.8 9.9-23.5 6.5-6.5 14.4-9.9 23.5-9.9 9 0 16.8 3.3 23.5 9.9L719 488.4c6.3 6.8 9.7 14.5 9.7 23.5zM912 328.6v366.6c0 41.3-14.7 76.7-44.1 106.1s-64.7 44.1-106.1 44.1H595.2c-4.5 0-8.5-1.6-11.7-4.9-3.3-3.3-4.9-7.2-4.9-11.7 0-1.4-0.2-4.8-0.5-10.4-0.3-5.6-0.4-10.2-0.2-13.8 0.2-3.6 0.6-7.8 1.5-12.2 0.9-4.5 2.7-7.9 5.3-10.2s6.1-3.4 10.6-3.4h166.8c22.9 0 42.5-8.1 58.9-24.4 16.3-16.3 24.4-35.9 24.4-58.9V328.6c0-22.9-8.3-42.5-24.4-58.9-16.3-16.3-35.9-24.4-58.9-24.4H599.6c-1.1 0-3.1-0.2-6-0.5-3-0.3-4.9-0.9-6-1.5-1.1-0.6-2.4-1.6-4.2-2.9-1.7-1.2-3-2.8-3.6-4.7-0.6-1.8-1.1-4.3-1.1-7 0-1.4-0.2-4.8-0.5-10.4s-0.4-10.2-0.2-13.8c0.2-3.6 0.6-7.8 1.5-12.2s2.7-7.9 5.3-10.2 6.1-3.4 10.6-3.4h166.8c41.3 0 76.7 14.7 106.1 44.1 29 29.1 43.7 64.5 43.7 105.8z"
                                                            fill="#1afa29" p-id="13738"></path></svg>
                                            </i><span> ${adminLabel}</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a class="site-page" href="${logoutURL}">
                                        <i class="fa-fw fas"><svg t="1595817487544" class="icon" viewBox="0 0 1257 1024"
                                                                  version="1.1" xmlns="http://www.w3.org/2000/svg"
                                                                  p-id="14876" width="200" height="200"><path
                                                        d="M716.374975 705.661397H345.484763a29.004292 29.004292 0 0 1-32.217343-22.75187 47.240523 47.240523 0 0 1-0.521035-8.68392V354.831039a27.962222 27.962222 0 0 1 31.43579-30.914754h372.1928v-9.72599V29.878763a27.614865 27.614865 0 0 1 16.759965-27.441187 27.354347 27.354347 0 0 1 32.043663 7.207654c5.210352 5.557709 10.941739 10.594382 16.32577 15.978412l203.551078 203.116883 261.385984 260.517592a27.788543 27.788543 0 0 1 0 44.027473l-374.103262 373.408549c-35.430393 35.343553-71.121303 70.513428-106.551695 105.94382a27.875382 27.875382 0 0 1-31.783146 8.68392 27.528026 27.528026 0 0 1-17.802036-29.091131V705.921914zM492.416684 5.30327V112.897035H208.799866a97.607258 97.607258 0 0 0-100.386112 100.559791v601.187763a100.472951 100.472951 0 0 0 77.460564 98.215133 140.505821 140.505821 0 0 0 25.183367 2.257819h281.358999v106.985891a21.709799 21.709799 0 0 1-4.25512 0.868392c-96.130992 0-192.261983 0.955231-288.392975 0A205.722059 205.722059 0 0 1 4.727752 857.977349 210.498214 210.498214 0 0 1 0.385792 818.031318c0-202.595847-0.781553-405.191695 0-607.874382A200.859064 200.859064 0 0 1 116.229281 25.276285a176.630927 176.630927 0 0 1 83.973504-20.320372H491.80881z m0 0"
                                                        p-id="14877" fill="#d4237a"></path></svg>
                                            </i><span> ${logoutLabel}</span>
                                        </a>
                                    </li>
                                <#else>
                                    <li>
                                        <a class="site-page" rel="alternate" href="${servePath}/start">
                                        <i class="fa-fw fas"><svg t="1595817538981" class="icon" viewBox="0 0 1024 1024"
                                                                  version="1.1" xmlns="http://www.w3.org/2000/svg"
                                                                  p-id="16331" width="200" height="200"><path
                                                        d="M512 0c282.770286 0 512 229.229714 512 512s-229.229714 512-512 512S0 794.770286 0 512 229.229714 0 512 0zM362.642286 237.714286a73.252571 73.252571 0 0 0-35.84 63.122285v422.326858c0 25.965714 13.604571 50.029714 35.84 63.158857a71.68 71.68 0 0 0 72.996571 0l357.814857-211.163429c22.308571-13.129143 35.876571-37.193143 35.876572-63.158857 0-25.965714-13.604571-50.029714-35.84-63.158857L435.565714 237.677714a71.606857 71.606857 0 0 0-72.96 0z m37.302857 63.817143l356.352 210.285714c-0.109714 0.365714-0.146286 0.438857 0 0.365714l-356.388572 210.285714V301.531429z"
                                                        fill="#60BE00" p-id="16332"></path></svg>
                                        </i><span> ${startToUseLabel}</span>
                                        </a>
                                    </li>
                                </#if>
                        </ul>
                    </div>
                    </#if>
                </div>
                <span class="toggle-menu close">
                    <a class="site-page">
                        <i class="fas fa-bars fa-fw"></i>
                    </a>
                </span>
            </span>
        </nav>
    </header>
</#macro>

