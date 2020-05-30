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
<nav class="navbar navbar-main  is-paddingless ">
    <div class="container ">
        <div class="navbar-brand transparent">
            <a class="navbar-item navbar-logo" href="${servePath}" rel="start">
                <img src="${faviconURL}" alt="${blogTitle!}" height="28"/>
                &nbsp;${blogTitle!}
            </a>
            <span class="navbar-burger burger" data-target="navMenu">
                    <span></span>
                    <span></span>
                    <span></span>
                </span>
        </div>
        <div id="navMenu" class="navbar-menu transparent">
            <div class="navbar-start transparent">
                <div class="navbar-start">
                    <#list pageNavigations as page>
                        <a class="navbar-item" href="${page.pagePermalink}" target="${page.pageOpenTarget}"
                           rel="section">
                            <#if page.pageIcon !=''><img class="page-icon" src="${page.pageIcon}"></#if>
                            ${page.pageTitle}
                        </a>
                    </#list>
                </div>
            </div>
            <div class="navbar-end">
                <#if interactive == "on">
                    <#if isLoggedIn>
                        <a class="navbar-item" href="${servePath}/admin-index.do#main" title="${adminLabel}">
                            ${adminLabel}
                        </a>
                        <a class="navbar-item" href="${logoutURL}">
                            ${logoutLabel}
                        </a>
                    <#else>
                        <a class="navbar-item" href="${servePath}/start">
                            ${startToUseLabel}
                        </a>
                    </#if>
                </#if>
            </div>
        </div>
</nav>


