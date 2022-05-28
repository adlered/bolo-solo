/*
 * Bolo - A stable and beautiful blogging system based in Solo.
 * Copyright (c) 2020, https://github.com/adlered
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
var GLOBAL_CONFIG = {
    root: '/',
    algolia: undefined,
    localSearch: undefined,
    translate: {
        "defaultEncoding": 2,
        "translateDelay": 0,
        "msgToTraditionalChinese": "繁",
        "msgToSimplifiedChinese": "簡"
    },
    copy: {
        success: '复制成功',
        error: '复制错误',
        noSupport: '浏览器不支持'
    },
    bookmark: {
        message_prev: '按',
        message_next: '键将本页加入书签'
    },
    runtime_unit: '天',
    runtime: true,
    copyright: undefined,
    ClickShowText: undefined,
    medium_zoom: false,
    fancybox: true,
    Snackbar: undefined,
    justifiedGallery: {
        js: 'https://ftp.stackoverflow.wiki/bolo/Justified-Gallery/dist/js/jquery.justifiedGallery.min.js',
        css: 'https://ftp.stackoverflow.wiki/bolo/Justified-Gallery/dist/css/justifiedGallery.min.css'
    },
    baiduPush: true,
    highlightCopy: true,
    highlightLang: true,
    isPhotoFigcaption: false,
    islazyload: true,
    isanchor: false
};
var GLOBAL_CONFIG_SITE = {
    isPost: false,
    isHome: true,
    isHighlightShrink: false,
    isSidebar: false
}

//动画加载
// var endLoading = function () {
//     document.body.style.overflow = 'auto';
//     document.getElementById('loading-box').classList.add("loaded")
// }
// window.addEventListener('load', endLoading);

function leftRight(){
    $("article").each(function (index, element) {
        if (index % 2 == 0) {
            $(element).find(".post_cover").addClass("left_radius");
        } else {
            $(element).find(".post_cover").addClass("right_radius");
        }
    });
}
