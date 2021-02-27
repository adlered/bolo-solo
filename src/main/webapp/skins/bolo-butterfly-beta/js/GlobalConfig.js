/*
 * Solo - A small and beautiful blogging system written in Java.
 * Copyright (c) 2010-present, b3log.org
 *
 * Solo is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *         http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
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
        js: 'https://cdn.jsdelivr.net/npm/justifiedGallery/dist/js/jquery.justifiedGallery.min.js',
        css: 'https://cdn.jsdelivr.net/npm/justifiedGallery/dist/css/justifiedGallery.min.css'
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
