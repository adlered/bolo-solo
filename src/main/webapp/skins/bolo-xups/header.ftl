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
<!-- 博客头部 -->
<body class="home">
<header class="header">
    <section class="container header-main">
        <div class="logo">
            <a href="${servePath}/">
                <div class="cover">
                    <span class="name">${blogTitle}</span>
                    <span class="description">${blogSubtitle}</span>
                </div>
            </a>
        </div>
        <ul class="menu hidden" id="JELON__menu">
            <li class="item current">
                <a href="${servePath}/" title="首页" class="fa fa-home">&nbsp;首页</a>
            </li>
            <#list pageNavigations as page>
                <li class="item current">
                    <a href="${page.pagePermalink}" target="${page.pageOpenTarget}">
                        <#if page.pageIcon != ''>
                            <img src="${page.pageIcon}" width="15px" height="16px">
                        </#if>
                        ${page.pageTitle}
                    </a>
                </li>
            </#list>
        </ul>
        <div class="profile clearfix">
            <div class="feeds fl">
                <p class="links">
                    <#if interactive == "on">
                        <#if isLoggedIn>
                            <a href="${servePath}/admin-index.do#main">${adminLabel}</a>
                            |
                            <a href="${logoutURL}">${logoutLabel}</a>
                        <#else>
                            <a href="${servePath}/start">${startToUseLabel}</a>
                        </#if>
                    </#if>
                </p>
                <p class="sns">
                    <#include "../../common-template/macro-user_site.ftl"/>
                    <#if usite??>
                        <#if hacpaiUser != ''>
                            <a href="https://${hacpaiDomain}/member/${adminUser.userName}" target="_blank">
                                <b style="color: #3be5d3">■</b>
                                链滴社区
                            </a>
                        </#if>
                        <#if usite.usiteGitHub != ''>
                            <a href="https://github.com/${usite.usiteGitHub}" target="_blank">
                                <b style="color: #3b83ee">■</b>
                                GitHub
                            </a>
                        </#if>
                        <#if usite.usiteWeiBo != ''>
                            <a href="https://weibo.com/${usite.usiteWeiBo}" target="_blank">
                                <b style="color: #ee743b">■</b>
                                新浪微博
                            </a>
                        </#if>
                        <#if usite.usiteZhiHu != ''>
                            <a href="https://www.zhihu.com/people/${usite.usiteZhiHu}" target="_blank">
                                <b style="color: #35cd90">■</b>
                                知乎
                            </a>
                        </#if>
                        <#if usite.usiteQQ != ''>
                            <a href="tencent://message/?uin=${usite.usiteQQ}" target="_blank">
                                <b style="color: #cd3535">■</b>
                                腾讯QQ
                            </a>
                        </#if>
                        <#if usite.usiteWeChat != ''>
                            <a href="javascript:alert('微信号：${usite.usiteWeChat}')">
                                <b style="color: #a7ec6b">■</b>
                                微信
                            </a>
                        </#if>
                        <#if usite.usiteWYMusic != ''>
                            <a target="_blank" href="https://music.163.com/#/user/home?id=${usite.usiteWYMusic}" target="_blank">
                                <b style="color: #d745f8">■</b>
                                网易云音乐
                            </a>
                        </#if>
                    </#if>
                </p>
            </div>
            <div class="avatar fr">
                <img src="${adminUser.userAvatar}" alt="avatar" title="Jelon" >
            </div>
        </div>
    </section>
</header>
