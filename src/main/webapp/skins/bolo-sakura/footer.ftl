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
<form class="js-search search-form search-form--modal" method="get" action="${servePath}/search" role="search">
    <div class="search-form__inner">
        <div>
            <p class="micro mb-">
                想要找点什么呢？
            </p>
            <i class="iconfont icon-search"></i><input class="text-input" type="search" name="keyword"
                                                       placeholder="Search" required>
        </div>
    </div>
    <div class="search_close">
    </div>
</form>
<script type='text/javascript' src='https://ftp.stackoverflow.wiki/bolo/moezx/cdn/js/lib.min.js?ver=3.1.5'></script>
<script type='text/javascript'>var Poi = {
        "movies": {
            "url": "https:\/\/ftp.stackoverflow.wiki\/bolo\/moezx\/cdn\/",
            "name": "The Pet Girl of Sakurasou",
            "live": "close"
        },
        "windowheight": "fixed",
        "codelamp": "close",
        "ajaxurl": "https:\/\/2heng.xin\/wp-admin\/admin-ajax.php",
        "order": "desc",
        "formpostion": "bottom"
    };</script>
<script type='text/javascript' src='https://ftp.stackoverflow.wiki/bolo/github-cards/widget.js?ver=3.1.5'></script>
<div class="changeSkin-gear no-select">
    <div class="keys">
        <span id="open-skinMenu"> 主题 | Theme &nbsp;<i
                    class="iconfont icon-gear inline-block rotating"></i></span>
    </div>
</div>
<div class="skin-menu no-select">
    <div class="theme-controls row-container">
        <ul class="menu-list">
            <li id="white-bg"><i class="fa fa-television" aria-hidden="true"></i></li>
            <li id="sakura-bg"><i class="iconfont icon-sakura"></i></li>
            <li id="gribs-bg"><i class="fa fa-slack" aria-hidden="true"></i></li>
            <li id="KAdots-bg"><i class="iconfont icon-dots"></i></li>
            <li id="totem-bg"><i class="fa fa-optin-monster" aria-hidden="true"></i></li>
            <li id="pixiv-bg"><i class="iconfont icon-pixiv"></i></li>
            <li id="bing-bg"><i class="iconfont icon-bing"></i></li>
            <li id="dark-bg"><i class="fa fa-moon-o" aria-hidden="true"></i></li>
        </ul>
    </div>
    <div class="font-family-controls row-container">
        <button type="button" class="control-btn-serif selected" data-mode="serif"
                onclick="mashiro_global.font_control.change_font()">Serif
        </button>
        <button type="button" class="control-btn-sans-serif" data-mode="sans-serif"
                onclick="mashiro_global.font_control.change_font()">Sans Serif
        </button>
    </div>
</div>
<canvas id="night-mode-cover"></canvas>
<script type="text/javascript" src="${staticServePath}/js/common.js?${staticResourceVersion}"
        charset="utf-8"></script>
<script defer src="${staticServePath}/skins/${skinDirName}/js/main.js?${staticResourceVersion}"></script>
<script type="text/javascript" src="${staticServePath}/skins/${skinDirName}/js/common.js?${staticResourceVersion}"
        charset="utf-8"></script>
<#include "../../common-template/label.ftl">

${plugins}
