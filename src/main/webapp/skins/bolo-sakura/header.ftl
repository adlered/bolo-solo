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
<!DOCTYPE html>
<html lang="en-US">
<head>
    <@head title="${blogTitle}">
        <link media="all" href="${staticServePath}/skins/${skinDirName}/css/base.css?${staticResourceVersion}"
              rel="stylesheet"/>
        <link media="all" href="${staticServePath}/skins/${skinDirName}/css/style.css?${staticResourceVersion}"
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
            tr th, tr td {
                border: 1px solid #1d1f21;
            }

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
                background-image: url("https://programmingwithlove.stackoverflow.wiki/randombg");
                background-position: center center;
                background-attachment: inherit;
            }
        </style>
        <script>/*Initial Variables*/
            var mashiro_global = {};
            var mashiro_option = new function () {
                this.NProgressON = false;
                this.site_name = "${blogTitle}";
            };;
            mashiro_option.jsdelivr_css_src = "https://ftp.stackoverflow.wiki/bolo/moezx/cdn/css/lib.min.css";
            mashiro_option.head_notice = "on";
            /*End of Initial Variables*/
            console.info("%c Bolo %c", "background:#24272A; color:#ffffff", "", "https://github.com/adlered/bolo-solo");
            mashiro_option.land_at_home = true;</script>
        <script type="text/javascript">if (!!window.ActiveXObject || "ActiveXObject" in window) { //is IE?
                alert('朋友，IE浏览器未适配哦~（QQ、360浏览器请关闭 IE 模式访问~）');
            }</script>
        <script src="${staticServePath}/js/lib/jquery/jquery.min.js"></script>
        <link rel="stylesheet" href="https://ftp.stackoverflow.wiki/bolo/fancybox/dist/jquery.fancybox.min.css"/>
        <script src="https://ftp.stackoverflow.wiki/bolo/fancybox/dist/jquery.fancybox.min.js"></script>
    </@head>
</head>
