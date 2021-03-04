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
<#macro comments commentList article count>
    <#if article.commentable>
        <style type="text/css">.v[data-class=v] {
                font-size: 16px;
                text-align: left
            }

            .v[data-class=v] * {
                -webkit-box-sizing: border-box;
                box-sizing: border-box;
                line-height: 1.75
            }

            .v[data-class=v] .status-bar, .v[data-class=v] .veditor, .v[data-class=v] .vinput, .v[data-class=v] p, .v[data-class=v] pre code {
                color: #555
            }

            .v[data-class=v] .vsys, .v[data-class=v] .vtime {
                color: #b3b3b3
            }

            .v[data-class=v] .text-right {
                text-align: right
            }

            .v[data-class=v] .text-center {
                text-align: center
            }

            .v[data-class=v] img {
                max-width: 100%;
                border: none
            }

            .v[data-class=v] hr {
                margin: .825em 0;
                border-color: #f6f6f6;
                border-style: dashed
            }

            .v[data-class=v].hide-avatar .vimg {
                display: none
            }

            .v[data-class=v] a {
                position: relative;
                cursor: pointer;
                color: #1abc9c;
                text-decoration: none;
                display: inline-block
            }

            .v[data-class=v] a:hover {
                color: #d7191a
            }

            .v[data-class=v] code, .v[data-class=v] pre {
                background-color: #f8f8f8;
                padding: .2em .4em;
                border-radius: 3px;
                font-size: 85%;
                margin: 0
            }

            .v[data-class=v] pre {
                padding: 10px;
                overflow: auto;
                line-height: 1.45
            }

            .v[data-class=v] pre code {
                padding: 0;
                background: transparent;
                white-space: pre-wrap;
                word-break: keep-all
            }

            .v[data-class=v] blockquote {
                color: #666;
                margin: .5em 0;
                padding: 0 0 0 1em;
                border-left: 8px solid hsla(0, 0%, 93%, .5)
            }

            .v[data-class=v] .vinput {
                border: none;
                resize: none;
                outline: none;
                padding: 10px 5px;
                max-width: 100%;
                font-size: .775em
            }

            .v[data-class=v] input[type=checkbox], .v[data-class=v] input[type=radio] {
                display: inline-block;
                vertical-align: middle;
                margin-top: -2px
            }

            .v[data-class=v] .vicon {
                cursor: pointer;
                display: inline-block;
                overflow: hidden;
                fill: #555;
                vertical-align: middle
            }

            .v[data-class=v] .vicon + .vicon {
                margin-left: 10px
            }

            .v[data-class=v] .vicon.actived {
                fill: #66b1ff
            }

            .v[data-class=v] .vrow {
                font-size: 0;
                padding: 10px 0
            }

            .v[data-class=v] .vrow .vcol {
                display: inline-block;
                vertical-align: middle;
                font-size: 14px
            }

            .v[data-class=v] .vrow .vcol.vcol-20 {
                width: 20%
            }

            .v[data-class=v] .vrow .vcol.vcol-30 {
                width: 30%
            }

            .v[data-class=v] .vrow .vcol.vcol-40 {
                width: 40%
            }

            .v[data-class=v] .vrow .vcol.vcol-50 {
                width: 50%
            }

            .v[data-class=v] .vrow .vcol.vcol-60 {
                width: 60%
            }

            .v[data-class=v] .vrow .vcol.vcol-70 {
                width: 70%
            }

            .v[data-class=v] .vrow .vcol.vcol-80 {
                width: 80%
            }

            .v[data-class=v] .vrow .vcol.vctrl {
                font-size: 12px
            }

            .v[data-class=v] .emoji, .v[data-class=v] .vemoji {
                max-width: 25px;
                vertical-align: middle;
                margin: 0 1px;
                display: inline-block
            }

            .v[data-class=v] .vwrap {
                border: 1px solid #f0f0f0;
                border-radius: 4px;
                margin-bottom: 10px;
                overflow: hidden;
                position: relative;
                padding: 10px
            }

            .v[data-class=v] .vwrap input {
                background: transparent
            }

            .v[data-class=v] .vwrap .vedit {
                position: relative;
                padding-top: 10px
            }

            .v[data-class=v] .vwrap .cancel-reply-btn {
                position: absolute;
                right: 5px;
                top: 5px;
                cursor: pointer
            }

            .v[data-class=v] .vwrap .vemojis {
                display: none;
                font-size: 18px;
                max-height: 145px;
                overflow: auto;
                padding-bottom: 10px;
                -webkit-box-shadow: 0 0 1px #f0f0f0;
                box-shadow: 0 0 1px #f0f0f0
            }

            .v[data-class=v] .vwrap .vemojis i {
                font-style: normal;
                padding-top: 7px;
                width: 36px;
                cursor: pointer;
                text-align: center;
                display: inline-block;
                vertical-align: middle
            }

            .v[data-class=v] .vwrap .vpreview {
                padding: 7px;
                -webkit-box-shadow: 0 0 1px #f0f0f0;
                box-shadow: 0 0 1px #f0f0f0
            }

            .v[data-class=v] .vwrap .vheader .vinput {
                width: 33.33%;
                border-bottom: 1px dashed #dedede
            }

            .v[data-class=v] .vwrap .vheader.item2 .vinput {
                width: 50%
            }

            .v[data-class=v] .vwrap .vheader.item1 .vinput {
                width: 100%
            }

            .v[data-class=v] .vwrap .vheader .vinput:focus {
                border-bottom-color: #eb5055
            }

            @media screen and (max-width: 520px) {
                .v[data-class=v] .vwrap .vheader.item2 .vinput, .v[data-class=v] .vwrap .vheader .vinput {
                    width: 100%
                }
            }

            .v[data-class=v] .vpower {
                color: #999;
                font-size: .75em;
                padding: .5em 0
            }

            .v[data-class=v] .vpower a {
                font-size: .75em
            }

            .v[data-class=v] .vcount {
                padding: 5px;
                font-weight: 600;
                font-size: 1.25em
            }

            .v[data-class=v] ol, .v[data-class=v] ul {
                padding: 0;
                margin-left: 1.25em
            }

            .v[data-class=v] .txt-center {
                text-align: center
            }

            .v[data-class=v] .txt-right {
                text-align: right
            }

            .v[data-class=v] .pd5 {
                padding: 5px
            }

            .v[data-class=v] .pd10 {
                padding: 10px
            }

            .v[data-class=v] .veditor {
                width: 100%;
                min-height: 8.75em;
                font-size: .875em;
                background: transparent;
                resize: vertical;
                -webkit-transition: all .25s ease;
                transition: all .25s ease
            }

            .v[data-class=v] .vbtn {
                -webkit-transition-duration: .4s;
                transition-duration: .4s;
                text-align: center;
                color: #555;
                border: 1px solid #ededed;
                border-radius: .3em;
                display: inline-block;
                background: transparent;
                margin-bottom: 0;
                font-weight: 400;
                vertical-align: middle;
                -ms-touch-action: manipulation;
                touch-action: manipulation;
                cursor: pointer;
                white-space: nowrap;
                padding: .5em 1.25em;
                font-size: .875em;
                line-height: 1.42857143;
                -webkit-user-select: none;
                -moz-user-select: none;
                -ms-user-select: none;
                user-select: none;
                outline: none
            }

            .v[data-class=v] .vbtn + .vbtn {
                margin-left: 1.25em
            }

            .v[data-class=v] .vbtn:active, .v[data-class=v] .vbtn:hover {
                color: #3090e4;
                border-color: #3090e4
            }

            .v[data-class=v] .vbtn:disabled {
                border-color: #e1e1e1;
                color: #e1e1e1;
                background-color: #fdfafa;
                cursor: not-allowed
            }

            .v[data-class=v] .vempty {
                padding: 1.25em;
                text-align: center;
                color: #555;
                overflow: auto
            }

            .v[data-class=v] .vsys {
                display: inline-block;
                padding: .2em .5em;
                font-size: .75em;
                border-radius: .2em;
                margin-right: .3em
            }

            @media screen and (max-width: 520px) {
                .v[data-class=v] .vsys {
                    display: none
                }
            }

            .v[data-class=v] .vcards {
                width: 100%
            }

            .v[data-class=v] .vcards .vcard {
                padding-top: 1.25em;
                position: relative;
                display: block
            }

            .v[data-class=v] .vcards .vcard:after {
                content: "";
                clear: both;
                display: block
            }

            .v[data-class=v] .vcards .vcard .vimg {
                width: 3.125em;
                height: 3.125em;
                float: left;
                border-radius: 50%;
                margin-right: .7525em;
                border: 1px solid #f5f5f5;
                padding: .125em
            }

            @media screen and (max-width: 720px) {
                .v[data-class=v] .vcards .vcard .vimg {
                    width: 2.5em;
                    height: 2.5em
                }
            }

            .v[data-class=v] .vcards .vcard .vhead {
                line-height: 1.5;
                margin-top: 0
            }

            .v[data-class=v] .vcards .vcard .vhead .vnick {
                position: relative;
                font-size: .875em;
                font-weight: 500;
                margin-right: .4em;
                cursor: pointer;
                text-decoration: none;
                display: inline-block
            }

            .vtag {
                font-size: 12px;
                display: inline-block;
                line-height: 20px;
                border-radius: 2px;
                color: #fff;
                padding: 0 5px;
                position: inherit;
            }

            .vtag.vmaster {
                background: #ffa51e
            }

            .vtag.vfriend {
                background: #6cf
            }

            .vtag.vvisitor {
                background: #828282
            }

            .v[data-class=v] .vcards .vcard .vhead .vnick:hover {
                color: #d7191a
            }

            .v[data-class=v] .vcards .vcard .vh {
                overflow: hidden;
                padding-bottom: .5em;
                border-bottom: 1px dashed #f5f5f5
            }

            .v[data-class=v] .vcards .vcard .vh .vtime {
                font-size: .75em;
                margin-right: .875em
            }

            .v[data-class=v] .vcards .vcard .vh .vmeta {
                line-height: 1;
                position: relative
            }

            .v[data-class=v] .vcards .vcard .vh .vmeta .vat {
                font-size: .8125em;
                color: #ef2f11;
                cursor: pointer;
                float: right
            }

            .v[data-class=v] .vcards .vcard:last-child .vh {
                border-bottom: none
            }

            .v[data-class=v] .vcards .vcard .vcontent {
                word-wrap: break-word;
                word-break: break-all;
                font-size: .875em;
                line-height: 2;
                position: relative;
                margin-bottom: .75em;
                padding-top: .625em
            }

            .v[data-class=v] .vcards .vcard .vcontent.expand {
                cursor: pointer;
                max-height: 8em;
                overflow: hidden
            }

            .v[data-class=v] .vcards .vcard .vcontent.expand:before {
                display: block;
                content: "";
                position: absolute;
                width: 100%;
                left: 0;
                top: 0;
                bottom: 3.15em;
                background: -webkit-gradient(linear, left top, left bottom, from(hsla(0, 0%, 100%, 0)), to(hsla(0, 0%, 100%, .9)));
                background: linear-gradient(180deg, hsla(0, 0%, 100%, 0), hsla(0, 0%, 100%, .9));
                z-index: 999
            }

            .v[data-class=v] .vcards .vcard .vcontent.expand:after {
                display: block;
                content: attr(data-expand);
                text-align: center;
                color: #828586;
                position: absolute;
                width: 100%;
                height: 3.15em;
                line-height: 3.15em;
                left: 0;
                bottom: 0;
                z-index: 999;
                background: hsla(0, 0%, 100%, .9)
            }

            .v[data-class=v] .vcards .vcard .vquote {
                padding-left: 1em;
                border-left: 1px dashed hsla(0, 0%, 93%, .5)
            }

            .v[data-class=v] .vcards .vcard .vquote .vimg {
                width: 2.225em;
                height: 2.225em
            }

            .v[data-class=v] .vpage .vmore {
                margin: 1em 0
            }

            .v[data-class=v] .clear {
                content: "";
                display: block;
                clear: both
            }

            @-webkit-keyframes spin {
                0% {
                    -webkit-transform: rotate(0deg);
                    transform: rotate(0deg)
                }
                to {
                    -webkit-transform: rotate(1turn);
                    transform: rotate(1turn)
                }
            }

            @keyframes spin {
                0% {
                    -webkit-transform: rotate(0deg);
                    transform: rotate(0deg)
                }
                to {
                    -webkit-transform: rotate(1turn);
                    transform: rotate(1turn)
                }
            }

            @-webkit-keyframes pulse {
                50% {
                    background: #dcdcdc
                }
            }

            @keyframes pulse {
                50% {
                    background: #dcdcdc
                }
            }

            .v[data-class=v] .vspinner {
                width: 22px;
                height: 22px;
                display: inline-block;
                border: 6px double #a0a0a0;
                border-top-color: transparent;
                border-bottom-color: transparent;
                border-radius: 50%;
                -webkit-animation: spin 1s infinite linear;
                animation: spin 1s infinite linear;
                position: relative;
                vertical-align: middle;
                margin: 0 5px
            }

            .dark .v[data-class=v] .status-bar, .dark .v[data-class=v] .veditor, .dark .v[data-class=v] .vinput, .dark .v[data-class=v] p, .dark .v[data-class=v] pre code, .night .v[data-class=v] .status-bar, .night .v[data-class=v] .veditor, .night .v[data-class=v] .vinput, .night .v[data-class=v] p, .night .v[data-class=v] pre code, .theme__dark .v[data-class=v] .status-bar, .theme__dark .v[data-class=v] .veditor, .theme__dark .v[data-class=v] .vinput, .theme__dark .v[data-class=v] p, .theme__dark .v[data-class=v] pre code, [data-theme=dark] .v[data-class=v] .status-bar, [data-theme=dark] .v[data-class=v] .veditor, [data-theme=dark] .v[data-class=v] .vinput, [data-theme=dark] .v[data-class=v] p, [data-theme=dark] .v[data-class=v] pre code {
                color: #b2b2b5
            }

            .dark .v[data-class=v] .vsys, .dark .v[data-class=v] .vtime, .night .v[data-class=v] .vsys, .night .v[data-class=v] .vtime, .theme__dark .v[data-class=v] .vsys, .theme__dark .v[data-class=v] .vtime, [data-theme=dark] .v[data-class=v] .vsys, [data-theme=dark] .v[data-class=v] .vtime {
                color: #929298
            }

            .dark .v[data-class=v] code, .dark .v[data-class=v] pre, .dark .v[data-class=v] pre code, .night .v[data-class=v] code, .night .v[data-class=v] pre, .night .v[data-class=v] pre code, .theme__dark .v[data-class=v] code, .theme__dark .v[data-class=v] pre, .theme__dark .v[data-class=v] pre code, [data-theme=dark] .v[data-class=v] code, [data-theme=dark] .v[data-class=v] pre, [data-theme=dark] .v[data-class=v] pre code {
                color: #929298;
                background-color: #151414
            }

            .dark .v[data-class=v] .vwrap, .night .v[data-class=v] .vwrap, .theme__dark .v[data-class=v] .vwrap, [data-theme=dark] .v[data-class=v] .vwrap {
                border-color: #b2b2b5
            }

            .dark .v[data-class=v] .vicon, .night .v[data-class=v] .vicon, .theme__dark .v[data-class=v] .vicon, [data-theme=dark] .v[data-class=v] .vicon {
                fill: #b2b2b5
            }

            .dark .v[data-class=v] .vicon.actived, .night .v[data-class=v] .vicon.actived, .theme__dark .v[data-class=v] .vicon.actived, [data-theme=dark] .v[data-class=v] .vicon.actived {
                fill: #66b1ff
            }

            .dark .v[data-class=v] .vbtn, .night .v[data-class=v] .vbtn, .theme__dark .v[data-class=v] .vbtn, [data-theme=dark] .v[data-class=v] .vbtn {
                color: #b2b2b5;
                border-color: #b2b2b5
            }

            .dark .v[data-class=v] .vbtn:hover, .night .v[data-class=v] .vbtn:hover, .theme__dark .v[data-class=v] .vbtn:hover, [data-theme=dark] .v[data-class=v] .vbtn:hover {
                color: #66b1ff;
                border-color: #66b1ff
            }

            .dark .v[data-class=v] a:hover, .night .v[data-class=v] a:hover, .theme__dark .v[data-class=v] a:hover, [data-theme=dark] .v[data-class=v] a:hover {
                color: #d7191a
            }

            .dark .v[data-class=v] .vcards .vcard .vcontent.expand:before, .night .v[data-class=v] .vcards .vcard .vcontent.expand:before, .theme__dark .v[data-class=v] .vcards .vcard .vcontent.expand:before, [data-theme=dark] .v[data-class=v] .vcards .vcard .vcontent.expand:before {
                background: -webkit-gradient(linear, left top, left bottom, from(rgba(0, 0, 0, .3)), to(rgba(0, 0, 0, .7)));
                background: linear-gradient(180deg, rgba(0, 0, 0, .3), rgba(0, 0, 0, .7))
            }

            .dark .v[data-class=v] .vcards .vcard .vcontent.expand:after, .night .v[data-class=v] .vcards .vcard .vcontent.expand:after, .theme__dark .v[data-class=v] .vcards .vcard .vcontent.expand:after, [data-theme=dark] .v[data-class=v] .vcards .vcard .vcontent.expand:after {
                background: rgba(0, 0, 0, .7)
            }

            @media (prefers-color-scheme: dark) {
                .v[data-class=v] .status-bar, .v[data-class=v] .veditor, .v[data-class=v] .vinput, .v[data-class=v] p, .v[data-class=v] pre code {
                    color: #b2b2b5
                }

                .v[data-class=v] .vsys, .v[data-class=v] .vtime {
                    color: #929298
                }

                .v[data-class=v] code, .v[data-class=v] pre, .v[data-class=v] pre code {
                    color: #929298;
                    background-color: #151414
                }

                .v[data-class=v] .vwrap {
                    border-color: #b2b2b5
                }

                .v[data-class=v] .vicon {
                    fill: #b2b2b5
                }

                .v[data-class=v] .vicon.actived {
                    fill: #66b1ff
                }

                .v[data-class=v] .vbtn {
                    color: #b2b2b5;
                    border-color: #b2b2b5
                }

                .v[data-class=v] .vbtn:hover {
                    color: #66b1ff;
                    border-color: #66b1ff
                }

                .v[data-class=v] a:hover {
                    color: #d7191a
                }

                .v[data-class=v] .vcards .vcard .vcontent.expand:before {
                    background: -webkit-gradient(linear, left top, left bottom, from(rgba(0, 0, 0, .3)), to(rgba(0, 0, 0, .7)));
                    background: linear-gradient(180deg, rgba(0, 0, 0, .3), rgba(0, 0, 0, .7))
                }

                .v[data-class=v] .vcards .vcard .vcontent.expand:after {
                    background: rgba(0, 0, 0, .7)
                }
            }</style>
        <div class="commentFont" id="commentIcon" style="margin: 0 auto; text-align: center; padding-bottom: 20px">
            <img src="https://ftp.stackoverflow.wiki/bolo/background/butterfly/comment.png">
            <span style="font-size: 30px; vertical-align: middle;">评论</span>
        </div>

        <div class="wrapper view-article">
            <div class="Valine v" data-class="v">
                <div class="vpanel">
                    <div class="vwrap">
                        <div class="vheader item3">
                            <input placeholder="你的昵称" id="boloUser" class="vnick vinput" type="text">
                            <input placeholder="你的个人主页URL（选填）" id="boloSite" class="vmail vinput" type="text">
                        </div>
                        <div class="vedit">
                            <textarea placeholder="${postCommentsLabel} ..." class="veditor vinput" id="comment" rows="5" tabindex="4"></textarea>
                        </div>
                        <script type="text/javascript" src="${staticServePath}/js/bolo/sweetalert.min.js"></script>
                        <div class="vrow">
                            <div class="vcol vcol-30"><a alt="Markdown is supported"
                                                         href="https://guides.github.com/features/mastering-markdown/"
                                                         class="vicon" target="_blank">
                                    <svg class="markdown" viewBox="0 0 16 16" version="1.1" width="16" height="16"
                                         aria-hidden="true">
                                        <path fill-rule="evenodd"
                                              d="M14.85 3H1.15C.52 3 0 3.52 0 4.15v7.69C0 12.48.52 13 1.15 13h13.69c.64 0 1.15-.52 1.15-1.15v-7.7C16 3.52 15.48 3 14.85 3zM9 11H7V8L5.5 9.92 4 8v3H2V5h2l1.5 2L7 5h2v6zm2.99.5L9.5 8H11V5h2v3h1.5l-2.51 3.5z"></path>
                                    </svg>
                                </a></div>
                        </div>
                    </div>
                </div>
    </#if>

    <div class="vcount" style="display: block; padding: 25px 0px 20px 0px;"><span class="vnum">${count}</span> 评论</div>

    <div class="vcards" id="comments">
        <#list commentList as comment>
            <#include 'common-comment.ftl'/>
        </#list>
    </div>

    </div>
    </div>
</#macro>