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
<style>
    :root {
        --color: #333333;
        --background: #f7f7f7;
        --cardbgcolor: #fff;
        --cardcolor: #333;
        --cardtitlecolor: #333333;
        --navbarcolor: #333333;
        --navbarbgcolor: rgba(255, 255, 255, 0.7);
        --img-default-filter: none;
        --img-hover-filter: none;
        --btn-bg-color: whitesmoke;
        --btn-color: #333333;
        --btn-hover-color: #eeeeee;
        --link-color: #1485FE;
        --link-hover-color: #1485FE;

        /*code highlight*/
        --code-bg-color: #F1F3F3;
        --code-default-color: #A2A1A1;
        --code-line-numbers-bg-color: #EBEDED;
        --code-line-numbers-color: #888;

        --code-keyworks-color: #7012cc;
        --code-class-color: #f2777a;
        --code-cdata-color: hsl(313, 67%, 36%);
        --code-symbol-color: hsl(33, 83%, 45%);
        --code-variable-color: hsl(281, 89%, 64%);
        --code-regex-color: #e90;
        --code-deleted-color: red;
        --code-toolbar-bg-color: #EBEDED;
        --code-toolbar-color: #1485FE;
        --code-line-numbers-rows-color: #888;
        /*toc*/
        --toc-dot-color: #1485FE;
        --toc-dot-active-color: #1485FE;
        --toc-dot-active-bg-color: #ebedef;
        --toc-vertical-line-color: #1485FE;
        --toc-item-bg-hover: #1485FE;
    }

    <#if settings.auto_theme!true>
    /*light theme*/
    @media (prefers-color-scheme: light) {
        :root {
            --color: #333333;
            --background: #f7f7f7;
            --cardbgcolor: #fff;
            --cardcolor: #333;
            --cardtitlecolor: #333333;
            --navbarcolor: #333333;
            --navbarbgcolor: rgba(255, 255, 255, 0.7);
            --img-default-filter: none;
            --img-hover-filter: none;
            --btn-bg-color: whitesmoke;
            --btn-color: #333333;
            --btn-hover-color: #eeeeee;
            --link-color: #1485FE;
            --link-hover-color: #1485FE;

            /*code highlight*/
            --code-bg-color: #F1F3F3;
            --code-default-color: #A2A1A1;
            --code-line-numbers-bg-color: #EBEDED;
            --code-line-numbers-color: #888;

            --code-keyworks-color: #7012cc;
            --code-class-color: #f2777a;
            --code-cdata-color: hsl(313, 67%, 36%);
            --code-symbol-color: hsl(33, 83%, 45%);
            --code-variable-color: hsl(281, 89%, 64%);
            --code-regex-color: #e90;
            --code-deleted-color: red;
            --code-toolbar-bg-color: #EBEDED;
            --code-toolbar-color: #1485FE;
            --code-line-numbers-rows-color: #888;
            /*toc*/
            --toc-dot-color: #1485FE;
            --toc-dot-active-color: #1485FE;
            --toc-dot-active-bg-color: #ebedef;
            --toc-vertical-line-color: #1485FE;
            --toc-item-bg-hover: #1485FE;
        }
    }

    /* Dark mode */
    @media (prefers-color-scheme: dark) {
        :root {
            --color: #CCC;
            --background: #2c2a2a !important;
            --cardbgcolor: #343232 !important;
            --cardcolor: #bcbcbc;
            --cardtitlecolor: #bcbcbc;
            --navbarcolor: #bcbcbc;
            --navbarbgcolor: rgba(52, 50, 50, 0.7);
            /*--img-default-filter: grayscale(100%);*/
            img-default-filter: none;
            --img-hover-filter: none;
            --btn-bg-color: #2c2a2a;
            --btn-color: #bcbcbc;
            --btn-hover-color: #3B3C3E;
            --link-color: #1485FE;
            --link-hover-color: #1485FE;

            /*code highlight*/
            --code-bg-color: #2c2a2a;
            --code-default-color: #A2A1A1;
            --code-line-numbers-bg-color: #403E3E;
            --code-line-numbers-color: #888;

            --code-keyworks-color: #c9c;
            --code-class-color: #f2777a;
            --code-cdata-color: hsl(313, 67%, 36%);
            --code-symbol-color: hsl(33, 83%, 45%);
            --code-variable-color: hsl(281, 89%, 64%);
            --code-regex-color: #e90;
            --code-deleted-color: red;
            --code-toolbar-bg-color: #454545;
            --code-toolbar-color: #fff;
            --code-line-numbers-rows-color: #888;

            /*toc*/
            --toc-dot-color: #1485FE;
            --toc-dot-active-bg-color: rgba(255, 255, 255, 0.2);
            --toc-dot-active-color: #1485FE;
            --toc-vertical-line-color: #888;
            --toc-bg-bg-hover: #321;
        }
    }

    <#else>
    @media (prefers-color-scheme: light) {
        :root {
            --color: #333333;
            --background: #f7f7f7;
            --cardbgcolor: #fff;
            --cardcolor: #333;
            --cardtitlecolor: #333333;
            --navbarcolor: #333333;
            --navbarbgcolor: rgba(255, 255, 255, 0.7);
            --img-default-filter: none;
            --img-hover-filter: none;
            --btn-bg-color: whitesmoke;
            --btn-color: #333333;
            --btn-hover-color: #eeeeee;
            --link-color: #1485FE;
            --link-hover-color: #1485FE;

            /*code highlight*/
            --code-bg-color: #F1F3F3;
            --code-default-color: #A2A1A1;
            --code-line-numbers-bg-color: #EBEDED;
            --code-line-numbers-color: #888;

            --code-keyworks-color: #7012cc;
            --code-class-color: #f2777a;
            --code-cdata-color: hsl(313, 67%, 36%);
            --code-symbol-color: hsl(33, 83%, 45%);
            --code-variable-color: hsl(281, 89%, 64%);
            --code-regex-color: #e90;
            --code-deleted-color: red;
            --code-toolbar-bg-color: #EBEDED;
            --code-toolbar-color: #1485FE;
            --code-line-numbers-rows-color: #888;
            /*toc*/
            --toc-dot-color: #1485FE;
            --toc-dot-active-color: #1485FE;
            --toc-dot-active-bg-color: #ebedef;
            --toc-vertical-line-color: #1485FE;
            --toc-item-bg-hover: #1485FE;
        }
    }

    </#if>
    /* Light mode */


    html {
        background: var(--background);
        color: var(--color);
    }

    body > .footer, body > .navbar {
        background-color: var(--cardbgcolor);
        color: var(--cardcolor);
    }

    a:hover {
        color: var(--link-color);
    }

    .content a {
        color: var(--link-color);
    }

    .content a:hover {
        color: var(--link-hover-color);
        font-weight: 400;
    }

    .card {
        background-color: var(--cardbgcolor);
        color: var(--cardcolor);
    }

    .card .card-image img,
    img.thumbnail {
        filter: var(--img-default-filter);
    }

    .card .card-image img:hover,
    img.thumbnail:hover {
        filter: var(--img-hover-filter);
    }

    .card .title {
        color: var(--color) !important;
    }

    .navbar-dropdown {
        background-color: var(--background);
    }

    pre,
    blockquote {
        background-color: var(--code-bg-color) !important;
        color: var(--color) !important;
    }

    .card .content,
        /*.card .title a{*/
        /*    color: var(--cardtitlecolor) !important;*/
        /*}*/
    .pagination .pagination-link:not(.is-current),
    .pagination .pagination-previous,
    .pagination .pagination-next {
        background-color: var(--cardbgcolor);
        color: var(--cardcolor);
    }

    .navbar-item, .navbar-link,
    .navbar-main .navbar-item .sub-menu {
        color: var(--navbarcolor);

    }

    .has-link-black-ter {
        color: var(--cardtitlecolor) !important;
    }


    .button {
        background-color: var(--btn-bg-color) !important;
        color: var(--btn-color) !important;
    }

    .button:hover {
        background-color: var(--btn-hover-color) !important;
    }

    body > footer > div > div > div.level-end > div > a > i {
        background-color: transparent;
        color: var(--color);
    }

    .level .level-item.button {
        background-color: var(--cardbgcolor) !important;
        border-color: var(--cardbgcolor) !important;
        color: var(--color) !important;
    }

    .menu-list .level {
        color: var(--color);
    }


    .menu-list a:hover {
        background-color: var(--background);
        opacity: 0.6;
        color: var(--link-hover-color);
    }

    .tag:not(body) {
        background-color: var(--background) !important;
        color: var(--color) !important;
    }

    .content .tag {
        background-color: transparent !important;
        color: var(--code-class-color) !important;
    }

    h1,
    h2,
    h3,
    h4,
    h5,
    h6,
    h7,
    strong {
        color: var(--color) !important;
    }

    .is-active a {
        color: var(--color) !important;
    }

    a.navbar-item:hover {
        background-color: var(--cardbgcolor);
        color: #3273dc;
    }

    code {
        color: var(--color);
        background-color: var(--code-bg-color)
    }

    code[class*="language-"],
    pre[class*="language-"] {
        color: var(--color);
        background-color: var(--code-bg-color) !important;
    }

    pre[class*="language-"],
    :not(pre) > code[class*="language-"] {
        background: var(--background);
    }

    :not(pre) > code[class*="language-"] {
        border: red;
    }

    .token.comment,
    .token.prolog,
    .token.doctype,
    .token.cdata {
        color: var(--code-cdata-color);
    }

    .token.property,
    .token.tag,
    .token.boolean,
    .token.number,
    .token.constant,
    .token.symbol {
        color: var(--code-symbol-color);
    }

    .token.selector,
    .token.attr-name,
    .token.string,
    .token.char,
    .token.builtin,
    .token.inserted {
        color: var(--code-class-color);
    }

    .token.operator,
    .token.entity,
    .token.url,
    .language-css .token.string,
    .style .token.string,
    .token.variable {
        color: var(--code-variable-color);
    }

    .token.atrule,
    .token.attr-value,
    .token.keyword {
        color: var(--code-keyworks-color);
    }

    .token.regex,
    .token.important {
        color: var(--code-regex-color);
    }

    .token.deleted {
        color: var(--code-deleted-color);
    }


    .line-numbers .line-numbers-rows {
        background-color: var(--code-line-numbers-bg-color) !important;
    }

    .line-numbers-rows > span:before {
        color: var(--code-line-numbers-color) !important;
    }

    .line-highlight {
        background: rgba(21, 21, 21, 0.2) !important;
        background: -webkit-linear-gradient(left, rgba(21, 21, 21, 0.2) 70%, rgba(21, 21, 21, 0)) !important;
        background: linear-gradient(to right, rgba(21, 21, 21, 0.2) 70%, rgba(21, 21, 21, 0)) !important;
    }

    div.code-toolbar > .toolbar a,
    div.code-toolbar > .toolbar button,
    div.code-toolbar > .toolbar span {
        background: var(--code-toolbar-bg-color) !important;
        /*box-shadow: 0 2px 0 0 rgba(0,0,0,0.2);*/
        box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
    }

    div.code-toolbar > .toolbar a:hover,
    div.code-toolbar > .toolbar a:focus,
    div.code-toolbar > .toolbar button:hover,
    div.code-toolbar > .toolbar button:focus,
    div.code-toolbar > .toolbar span:hover,
    div.code-toolbar > .toolbar span:focus {
        color: var(--code-toolbar-color) !important;
    }

    .line-numbers-rows > span:before {
        color: var(--code-line-numbers-rows-color);
    }

    .content table thead td,
    .content table thead th {
        color: var(--color);
    }

    #toc ul li a,
    #toc ul li ul li a,
    #toc ul li ul li ul li a {
        color: var(--color);
    }

    #toc .active {
        color: var(--toc-dot-active-color) !important;
    }

    #toc > ul li::before {
        background-color: var(--toc-dot-color)
    }

    #toc a:hover {
        color: var(--toc-dot-active-color) !important;
        background-color: var(--toc-dot-active-bg-color);
    }

    #toc > ul:before {
        background-color: var(--toc-vertical-line-color);
    }


    .navbar-burger {
        color: var(--color);
    }

    .navbar-menu {
        background-color: var(--cardbgcolor);
    }

    body > .navbar {
        background-color: var(--navbarbgcolor);
        -webkit-backdrop-filter: blur(20px);
        backdrop-filter: blur(20px);
    }

    .content table thead td,
    .content table thead th {
        border: 1px solid var(--background);

    }

    .content table td,
    .content table th {
        border: 1px solid var(--background);
    }

    table {
        border: 2px solid var(--btn-hover-color);
    }

    thead, tbody, tr, th {
        border: 1px solid var(--background);
    }

    footer .title {
        color: var(--color);
    }

    footer .button {
        background-color: var(--cardbgcolor) !important;
    }

    .has-text-black-ter {
        color: var(--color) !important;
    }
</style>