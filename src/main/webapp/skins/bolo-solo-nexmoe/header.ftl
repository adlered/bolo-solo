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
<#if customVars.bg??>
    <div class="nexmoe-bg"
         style="background-image:url(<#if customVars.bgUrl??>${customVars.bgUrl}<#else>${staticServePath}/skins/${skinDirName}/images/bg.jpg</#if>);opacity: .${customVars.bg};"></div>
<#else>
    <canvas id="c_n1" class="nexmoe-bg"></canvas>
</#if>
<div class="mdui-appbar mdui-shadow-0">
    <div class="mdui-toolbar">
        <a href="#" mdui-drawer="{target: '#drawer', swipe: true}" title="menu" class="mdui-btn mdui-btn-icon">
            <i class="mdui-icon  iconfont solo-list"></i>
        </a>
        <div class="mdui-toolbar-spacer"></div>
        <a href="/" title="${blogTitle}" class="mdui-btn mdui-btn-icon">
            <img src="${adminUser.userAvatar}" title="${blogTitle}" alt="${blogTitle}"></a>
        </a>
    </div>
</div>