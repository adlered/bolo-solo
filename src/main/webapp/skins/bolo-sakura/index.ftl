<#include "../../common-template/macro-common_head.ftl">
<!DOCTYPE html>
<html lang="en-US">
<head>
    <@head title="${blogTitle}">
        <link media="all" href="${staticServePath}/skins/${skinDirName}/css/base.css?${staticResourceVersion}"
              rel="stylesheet"/>
        <script type='application/ld+json' class='yoast-schema-graph yoast-schema-graph--main'>{
                "@context": "https://schema.org",
                "@graph": [
                    {
                        "@type": "WebSite",
                        "@id": "https://2heng.xin/#website",
                        "url": "https://2heng.xin/",
                        "name": "\u6a31\u82b1\u5e84\u7684\u767d\u732b",
                        "publisher": {
                            "@id": "https://2heng.xin/#/schema/person/"
                        },
                        "potentialAction": {
                            "@type": "SearchAction",
                            "target": "https://2heng.xin/?s={search_term_string}",
                            "query-input": "required name=search_term_string"
                        }
                    },
                    {
                        "@type": "CollectionPage",
                        "@id": "https://2heng.xin/#webpage",
                        "url": "https://2heng.xin/",
                        "inLanguage": "en-US",
                        "name": "\u6a31\u82b1\u5e84\u7684\u767d\u732b | \u306d\u3053\u30fb\u3057\u308d\u30fb\u307e\u3057\u308d",
                        "isPartOf": {
                            "@id": "https://2heng.xin/#website"
                        },
                        "about": {
                            "@id": "https://2heng.xin/#/schema/person/"
                        },
                        "description": "\u55b5\u55b5\u55b5\uff01\u56de\u5f52\u73b0\u5b9e\u7684\u5c0f\u8d85\u4eba~"
                    }
                ]
            }</script>
        <style type="text/css">
            .site-top .lower nav {
                display: block !important;
            }

            .author-profile i, .post-like a, .post-share .show-share, .sub-text, .we-info a, span.sitename, .post-more i:hover, #pagination a:hover, .post-content a:hover, .float-content i:hover {
                color: #FE9600
            }

            .feature i, /*.feature-title span ,*/
            .download, .navigator i:hover, .links ul li:before, .ar-time i, span.ar-circle, .object, .comment .comment-reply-link, .siren-checkbox-radio:checked + .siren-checkbox-radioInput:after {
                background: #FE9600
            }

            ::-webkit-scrollbar-thumb {
                background: #FE9600
            }

            .download, .navigator i:hover, .link-title, .links ul li:hover, #pagination a:hover, .comment-respond input[type='submit']:hover {
                border-color: #FE9600
            }

            .entry-content a:hover, .site-info a:hover, .comment h4 a, #comments-navi a.prev, #comments-navi a.next, .comment h4 a:hover, .site-top ul li a:hover, .entry-title a:hover, #archives-temp h3, span.page-numbers.current, .sorry li a:hover, .site-title a:hover, i.iconfont.js-toggle-search.iconsearch:hover, .comment-respond input[type='submit']:hover {
                color: #FE9600
            }

            .comments .comments-main {
                display: block !important;
            }

            .comments .comments-hidden {
                display: none !important;
            }

            .centerbg {
                background-image: url("https://api.2heng.xin/cover/");
                background-position: center center;
                background-attachment: inherit;
            }
        </style>
        <script>/*Initial Variables*/
            var mashiro_global = {};
            var mashiro_option = new function () {
                this.NProgressON = true;
                this.email_domain = "moezx.cc";
                this.email_name = "me";
                this.cookie_version_control = "---2018/9/17";
                this.qzone_autocomplete = false;
                this.site_name = "${blogTitle}";
                this.author_name = "Mashiro";
                this.template_url = "https://2heng.xin/wp-content/themes/Sakura";
                this.site_url = "https://2heng.xin";
                this.qq_api_url = "https://api.2heng.xin/qqinfo/";
                this.qq_avatar_api_url = "https://api.2heng.xin/qqinfo/";
                this.is_app = function (app_version) {
                    window.is_app = true;
                    window.checked = false;
                    window.app_version = app_version;
                    window.latest_version = 7;
                }
            };;
            mashiro_option.jsdelivr_css_src = "https://cdn.jsdelivr.net/gh/moezx/cdn@3.7.5/css/lib.min.css";
            mashiro_option.head_notice = "off";
            /*End of Initial Variables*/
            console.log = function () {
            };
            console.info("%c Bolo %c", "background:#24272A; color:#ffffff", "", "https://github.com/adlered/bolo-solo");
            mashiro_option.land_at_home = true;</script>
        <script type="text/javascript">if (!!window.ActiveXObject || "ActiveXObject" in window) { //is IE?
                alert('朋友，IE浏览器未适配哦~（QQ、360浏览器请关闭 IE 模式访问~）');
            }</script>
        <script src="https://cdn.jsdelivr.net/npm/jquery@3.3.1/dist/jquery.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/fancyapps/fancybox@3.5.6/dist/jquery.fancybox.min.css"/>
        <script src="https://cdn.jsdelivr.net/gh/fancyapps/fancybox@3.5.6/dist/jquery.fancybox.min.js"></script>
    </@head>
</head>
<body nonce-data="4fb3a4be0d" class="home blog hfeed chinese-font">
<div class="scrollbar" id="bar">
</div>
<section id="main-container">
    <div class="headertop filter-dot">
        <#include 'macro-header.ftl'>
        <div id="content" class="site-content">
            <div id="primary" class="content-area">
                <#include "article-list.ftl">
            </div>
        </div>
    </div>
    <footer id="colophon" class="site-footer" role="contentinfo">
        <div class="site-info">
            <div class="footertext">
                <div class="img-preload">
                    <img src="https://cdn.jsdelivr.net/gh/moezx/cdn@3.1.9/img/Sakura/images/wordpress-rotating-ball-o.svg"><img
                            src="https://cdn.jsdelivr.net/gh/moezx/cdn@3.1.9/img/Sakura/images/disqus-preloader.svg">
                </div>
                <p class="foo-logo"
                   style="background-image: url('https://cdn.jsdelivr.net/gh/moezx/cdn@3.1.9/img/Sakura/images/sakura.svg');">
                </p>
                <p style="font-family: 'Ubuntu', sans-serif;">
                    <span style="color: #666666;">Crafted with <i class='fa fa-heart animated'
                                                                  style="color: #e74c3c;"></i> by <a rel="me"
                                                                                                     href="https://hello.2heng.xin/@mashiro"
                                                                                                     target="_blank"
                                                                                                     style="color: #000000;text-decoration:none;">Mashiro</a></span>
                </p>
                <!-- 此处放页脚 -->

            </div>
            <div class="footer-device">
            </div>
        </div>
    </footer>
    <div class="openNav no-select">
        <div class="iconflat no-select">
            <div class="icon">
            </div>
        </div>
        <div class="site-branding">
            <h1 class="site-title"><a href="${staticServePath}/">${blogTitle}</a></h1>
        </div>
    </div>
</section>
<div id="mo-nav">
    <div class="m-avatar">
        <img src="${adminUser.userAvatar}">
    </div>
    <p style="text-align: center; color: #333; font-weight: 900; font-family: 'Ubuntu', sans-serif; letter-spacing: 1.5px">
        ${userName}
    </p>
    <p style="text-align: center; word-spacing: 20px;">
        <a href="https://twitter.com/2hengxin" class="fa fa-twitter" target="_blank" style="color: #00aced"></a><a
                href="https://weibo.com/2960337711" class="fa fa-weibo" target="_blank" style="color: #dd4b39"></a><a
                href="https://github.com/mashirozx" class="fa fa-github" target="_blank" style="color: #333"></a><a
                onclick="mail_me()" class="fa fa-envelope" style="color: #ffbf00"></a>
    </p>
    <div class="m-search">
        <form class="m-search-form" method="get" action="https://2heng.xin" role="search">
            <input class="m-search-input" type="search" name="s" placeholder="搜索..." required>
        </form>
    </div>
    <ul id="menu-new-1" class="menu">
        <li class="current-menu-item"><a href="/" aria-current="page"><span class="faa-parent animated-hover"><i
                            class="fa fa-fort-awesome faa-horizontal" aria-hidden="true"></i> 首页</span></a></li>
        <li><a href="/time-series/"><span class="faa-parent animated-hover"><i class="fa fa-archive faa-shake"
                                                                               aria-hidden="true"></i> 归档</span></a>
            <ul class="sub-menu">
                <li><a href="https://2heng.xin/archives/hacking/"><i class="fa fa-terminal" aria-hidden="true"></i>
                        极客</a></li>
                <li><a href="https://2heng.xin/archives/article/"><i class="fa fa-file-text-o" aria-hidden="true"></i>
                        文章</a></li>
                <li><a href="https://2heng.xin/archives/review/"><i class="fa fa-quote-right" aria-hidden="true"></i> 影评</a>
                </li>
                <li><a href="https://2heng.xin/archives/thingking/"><i class="fa fa-commenting-o"
                                                                       aria-hidden="true"></i> 随想</a></li>
                <li><a target="_blank" rel="noopener noreferrer" href="https://mashiro.top/"><i class="fa fa-book"
                                                                                                aria-hidden="true"></i>
                        笔记</a></li>
            </ul>
        </li>
        <li><a><span class="faa-parent animated-hover"><i class="fa fa-list-ul faa-vertical" aria-hidden="true"></i> 清单</span></a>
            <ul class="sub-menu">
                <li><a href="https://2heng.xin/book-list/"><i class="fa fa-th-list faa-bounce" aria-hidden="true"></i>
                        书单</a></li>
                <li><a href="https://2heng.xin/bangumi/"><i class="fa fa-film faa-vertical" aria-hidden="true"></i>
                        番组</a></li>
                <li><a href="https://2heng.xin/music/"><i class="fa fa-headphones" aria-hidden="true"></i> 歌单</a></li>
                <li><a href="https://2heng.xin/hearthstone-deck-index/"><i class="iconfont icon-Hearthstone"
                                                                           aria-hidden="true"></i> 卡组</a></li>
            </ul>
        </li>
        <li><a href="https://2heng.xin/comment/"><span class="faa-parent animated-hover"><i
                            class="fa fa-pencil-square-o faa-tada" aria-hidden="true"></i> 留言板</span></a></li>
        <li><a href="https://2heng.xin/friends/"><span class="faa-parent animated-hover"><i class="fa fa-link faa-shake"
                                                                                            aria-hidden="true"></i> 友人帐</span></a>
        </li>
        <li><a href="https://2heng.xin/donate/"><span class="faa-parent animated-hover"><i class="fa fa-heart faa-pulse"
                                                                                           aria-hidden="true"></i> 赞赏</span></a>
        </li>
        <li><a href="#"><span class="faa-parent animated-hover"><i class="fa fa-leaf faa-wrench" aria-hidden="true"></i> 关于</span></a>
            <ul class="sub-menu">
                <li><a href="https://2heng.xin/about/"><i class="fa fa-grav" aria-hidden="true"></i> 我？</a></li>
                <li><a href="https://2heng.xin/analytics/"><i class="fa fa-area-chart" aria-hidden="true"></i> 统计</a>
                </li>
                <li><a href="https://2heng.xin/server-status/"><i class="fa fa-heartbeat" aria-hidden="true"></i> 监控</a>
                </li>
                <li><a href="https://2heng.xin/theme-sakura/"><i class="iconfont icon-sakura" aria-hidden="true"></i> 主题</a>
                </li>
                <li><a target="_blank" rel="noopener noreferrer" href="https://2heng.xin/sitemap_index.xml"><i
                                class="fa fa-map-signs" aria-hidden="true"></i> MAP</a></li>
                <li><a target="_blank" rel="noopener noreferrer" href="https://2heng.xin/feed"><i class="fa fa-rss "
                                                                                                  aria-hidden="true"></i>
                        RSS</a></li>
            </ul>
        </li>
        <li><a href="https://2heng.xin/client/"><span class="faa-parent animated-hover"><i
                            class="fa fa-android faa-vertical" aria-hidden="true"></i> 客户端</span></a></li>
        <li><a target="_blank" rel="noopener noreferrer" href="https://hello.2heng.xin/"><span
                        class="faa-parent animated-hover"><i class="fa fa-meetup faa-pulse"
                                                             aria-hidden="true"></i> 后院</span></a></li>
    </ul>
    <p style="text-align: center; font-size: 13px; color: #b9b9b9;">
        &copy; 2018 樱花庄的白猫
    </p>
</div>
<a href="#" class="cd-top faa-float animated "></a>
<button onclick="topFunction()" class="mobile-cd-top" id="moblieGoTop" title="Go to top"><i class="fa fa-chevron-up"
                                                                                            aria-hidden="true"></i>
</button>
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
<script type='text/javascript'>var HermitX = {
        "ajaxurl": "https:\/\/2heng.xin\/wp-admin\/admin-ajax.php",
        "version": "2.9.6"
    };</script>
<script type='text/javascript'
        src='https://cdn.jsdelivr.net/gh/moeplayer/hermit-x@2.9.6/assets/js/hermit-load.min.js?ver=2.9.6'></script>
<script type='text/javascript' src='https://cdn.jsdelivr.net/gh/moezx/cdn@3.7.5/js/lib.min.js?ver=3.1.5'></script>
<script type='text/javascript'>var Poi = {
        "pjax": "1",
        "movies": {
            "url": "https:\/\/cdn.jsdelivr.net\/gh\/moezx\/cdn@3.1.3\/",
            "name": "The Pet Girl of Sakurasou",
            "live": "close"
        },
        "windowheight": "fixed",
        "codelamp": "close",
        "ajaxurl": "https:\/\/2heng.xin\/wp-admin\/admin-ajax.php",
        "order": "desc",
        "formpostion": "bottom"
    };</script>
<script type='text/javascript' src='https://cdn.jsdelivr.net/github-cards/latest/widget.js?ver=3.1.5'></script>
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
<script defer src="${staticServePath}/skins/${skinDirName}/js/common.js?${staticResourceVersion}"></script>
</body>
</html>