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
    <#if article.commentable || 0 != commentList?size>
        <div class="halo-comment">
            <#if article.commentable>
                <style type="text/css">
                    .halo-comment {
                        position: relative;
                        font-family: PingFang SC, Hiragino Sans GB, Microsoft YaHei, STHeiti, WenQuanYi Micro Hei, Helvetica, Arial, sans-serif;
                        font-size: 14px;
                        font-weight: 500;
                        line-height: 1.8;
                        margin: 0 auto;
                        color: #313131;
                        text-rendering: geometricPrecision;
                        -webkit-font-smoothing: antialiased;
                        -moz-osx-font-smoothing: grayscale
                    }

                    .halo-comment a {
                        text-decoration: none;
                        color: #898c7b
                    }

                    .halo-comment input::-webkit-input-placeholder, .halo-comment textarea::-webkit-input-placeholder {
                        color: #ccc
                    }

                    .halo-comment a, .halo-comment abbr, .halo-comment acronym, .halo-comment address, .halo-comment applet, .halo-comment big, .halo-comment blockquote, .halo-comment body, .halo-comment caption, .halo-comment cite, .halo-comment code, .halo-comment dd, .halo-comment del, .halo-comment dfn, .halo-comment div, .halo-comment dl, .halo-comment dt, .halo-comment em, .halo-comment fieldset, .halo-comment figure, .halo-comment form, .halo-comment h1, .halo-comment h2, .halo-comment h3, .halo-comment h4, .halo-comment h5, .halo-comment h6, .halo-comment html, .halo-comment iframe, .halo-comment ins, .halo-comment kbd, .halo-comment label, .halo-comment legend, .halo-comment li, .halo-comment object, .halo-comment ol, .halo-comment p, .halo-comment pre, .halo-comment q, .halo-comment s, .halo-comment samp, .halo-comment small, .halo-comment span, .halo-comment strike, .halo-comment strong, .halo-comment sub, .halo-comment sup, .halo-comment table, .halo-comment tbody, .halo-comment td, .halo-comment tfoot, .halo-comment th, .halo-comment thead, .halo-comment tr, .halo-comment tt, .halo-comment ul, .halo-comment var {
                        border: 0;
                        font-size: 100%;
                        font-style: inherit;
                        font-weight: inherit;
                        margin: 0;
                        outline: 0;
                        padding: 0;
                        vertical-align: baseline
                    }

                    .halo-comment button, .halo-comment input, .halo-comment textarea {
                        -webkit-appearance: none;
                        outline: 0;
                        -webkit-tap-highlight-color: transparent
                    }

                    .halo-comment button:focus, .halo-comment input:focus, .halo-comment textarea:focus {
                        outline: 0
                    }

                    .halo-comment ol, .halo-comment ul {
                        list-style: none
                    }

                    .halo-comment .middle {
                        display: inline-block;
                        vertical-align: middle
                    }

                    .halo-comment .avatar {
                        z-index: 0 !important;
                        display: block;
                        -o-object-fit: cover;
                        object-fit: cover;
                        border-radius: 100%;
                        width: 48px;
                        height: 48px;
                        cursor: pointer;
                        -webkit-transition: all .8s;
                        transition: all .8s
                    }

                    .halo-comment .avatar:hover {
                        -webkit-transform: rotate(1turn);
                        transform: rotate(1turn)
                    }

                    .halo-comment .comment-editor {
                        position: relative;
                        z-index: 1;
                        -webkit-animation: top20 .5s;
                        animation: top20 .5s
                    }

                    .halo-comment .comment-editor .inner {
                        margin: auto;
                        padding: 20px 0
                    }

                    .halo-comment .comment-form {
                        margin-left: 56px
                    }

                    .halo-comment .comment-form input, .halo-comment .comment-form textarea {
                        -webkit-box-shadow: none;
                        box-shadow: none;
                        border: 1px solid #e1e8ed;
                        border-radius: 5px;
                        -webkit-box-sizing: border-box;
                        box-sizing: border-box;
                        padding: 10px;
                        resize: vertical;
                        color: #657786
                    }

                    .halo-comment .comment-form input:focus, .halo-comment .comment-form textarea:focus {
                        border-color: #ccc
                    }

                    .halo-comment .commentator {
                        position: relative;
                        float: left
                    }

                    .halo-comment .author-info {
                        position: relative;
                        overflow: hidden;
                        margin-bottom: 10px
                    }

                    .halo-comment .author-info input {
                        float: left;
                        width: 49%;
                        margin-right: 1%;
                    }

                    .halo-comment .author-info input#boloSite {
                        margin: 0;
                        margin-left: 1%;
                    }

                    .halo-comment .comment-textarea {
                        position: relative;
                        width: 100%
                    }

                    .halo-comment .comment-textarea textarea {
                        font-size: 13px;
                        line-height: 18px;
                        width: 100%;
                        min-height: 90px;
                        overflow: hidden;
                        -webkit-transition: all .15s ease-in-out;
                        transition: all .15s ease-in-out;
                        color: #000
                    }

                    .halo-comment .comment-preview {
                        position: relative;
                        width: 100%;
                        min-height: 90px;
                        -webkit-box-shadow: none;
                        box-shadow: none;
                        border: 1px solid #e1e8ed;
                        border-radius: 5px;
                        -webkit-box-sizing: border-box;
                        box-sizing: border-box;
                        padding: 10px;
                        margin-bottom: 10px
                    }

                    .halo-comment .comment-buttons {
                        font-size: 14px;
                        text-align: right
                    }

                    .halo-comment .comment-buttons .button-preview-edit, .halo-comment .comment-buttons .button-submit {
                        -webkit-animation: bottom20 .5s;
                        animation: bottom20 .5s;
                        border: none;
                        background: #898c7b;
                        color: #fff;
                        font-weight: 500;
                        padding: 6px 18px;
                        text-transform: uppercase;
                        border-radius: 4px;
                        display: inline-block;
                        -webkit-transition: all .3s ease 0s;
                        transition: all .3s ease 0s
                    }

                    .halo-comment .comment-buttons .button-preview-edit:hover, .halo-comment .comment-buttons .button-submit:hover {
                        color: #404040;
                        font-weight: 700;
                        letter-spacing: 3px;
                        background: 0 0;
                        -webkit-box-shadow: 0 5px 40px -10px rgba(0, 0, 0, .57);
                        box-shadow: 0 5px 40px -10px rgba(0, 0, 0, .57);
                        -webkit-transition: all .3s ease 0s;
                        transition: all .3s ease 0s
                    }

                    .halo-comment .comment-loader-container {
                        -webkit-animation: top20 .5s;
                        animation: top20 .5s;
                        position: relative;
                        text-align: center;
                        display: -webkit-box;
                        display: -ms-flexbox;
                        display: flex;
                        -webkit-box-pack: center;
                        -ms-flex-pack: center;
                        justify-content: center;
                        margin: 30px 0
                    }

                    .halo-comment .comment-loader-container .comment-loader {
                        display: -webkit-box;
                        display: -ms-flexbox;
                        display: flex;
                        -webkit-box-orient: horizontal;
                        -webkit-box-direction: normal;
                        -ms-flex-flow: row nowrap;
                        flex-flow: row nowrap;
                        -webkit-box-align: center;
                        -ms-flex-align: center;
                        align-items: center;
                        -webkit-box-pack: justify;
                        -ms-flex-pack: justify;
                        justify-content: space-between;
                        width: 30px
                    }

                    .halo-comment .comment-loader-container .comment-loader span {
                        width: 4px;
                        height: 15px;
                        background-color: #3b83ee
                    }

                    .halo-comment .comment-loader-container .comment-loader span:first-of-type {
                        -webkit-animation: grow 1s ease-in-out -.45s infinite;
                        animation: grow 1s ease-in-out -.45s infinite
                    }

                    .halo-comment .comment-loader-container .comment-loader span:nth-of-type(2) {
                        -webkit-animation: grow 1s ease-in-out -.3s infinite;
                        animation: grow 1s ease-in-out -.3s infinite
                    }

                    .halo-comment .comment-loader-container .comment-loader span:nth-of-type(3) {
                        -webkit-animation: grow 1s ease-in-out -.15s infinite;
                        animation: grow 1s ease-in-out -.15s infinite
                    }

                    .halo-comment .comment-loader-container .comment-loader span:nth-of-type(4) {
                        -webkit-animation: grow 1s ease-in-out infinite;
                        animation: grow 1s ease-in-out infinite
                    }

                    @-webkit-keyframes grow {
                        0%, to {
                            -webkit-transform: scaleY(1);
                            transform: scaleY(1)
                        }
                        50% {
                            -webkit-transform: scaleY(2);
                            transform: scaleY(2)
                        }
                    }

                    @keyframes grow {
                        0%, to {
                            -webkit-transform: scaleY(1);
                            transform: scaleY(1)
                        }
                        50% {
                            -webkit-transform: scaleY(2);
                            transform: scaleY(2)
                        }
                    }

                    .halo-comment .comment-nodes {
                        -webkit-animation: top20 1s;
                        animation: top20 1s;
                        position: relative
                    }

                    .halo-comment .comment-nodes .comment-editor {
                        -webkit-animation: bottom20 .5s;
                        animation: bottom20 .5s
                    }

                    .halo-comment .comment-nodes .comment-editor .inner {
                        padding: 7px 0 12px
                    }

                    .halo-comment .comment-empty, .halo-comment .comment-load-button {
                        margin: 30px 0;
                        text-align: center
                    }

                    .halo-comment .comment-empty {
                        color: #8899a6
                    }

                    .halo-comment .comment-page {
                        margin-top: 30px;
                        text-align: center;
                        border-top: 3px solid #f5f8fa
                    }

                    .halo-comment .comment-page .page {
                        display: inline-block;
                        padding: 10px 0;
                        margin: 0
                    }

                    .halo-comment .comment-page .page li {
                        display: inline;
                        margin-right: 5px
                    }

                    .halo-comment .comment-page .page button {
                        margin-bottom: 8px;
                        position: relative;
                        font-size: inherit;
                        font-family: inherit;
                        padding: 5px 10px;
                        border: 1px solid #d9d9d9;
                        border-radius: 4px;
                        cursor: pointer;
                        -webkit-transition: all .8s;
                        transition: all .8s;
                        font-weight: 400;
                        color: rgba(0, 0, 0, .65);
                        background-color: #fff
                    }

                    .halo-comment .comment-page .page .active, .halo-comment .comment-page .page button:hover {
                        color: #1890ff;
                        border-color: #1890ff
                    }

                    .halo-comment .comment-nodes .index-1 {
                        overflow: hidden;
                        margin-top: 30px;
                        padding-bottom: 20px;
                        border-bottom: 0px solid #f5f8fa
                    }

                    .halo-comment .comment-nodes li:last-child {
                        border: 0
                    }

                    .halo-comment .comment-nodes .commentator a:after, .halo-comment .comment-nodes .commentator a:before {
                        display: none
                    }

                    .halo-comment .comment-body {
                        position: relative;
                        margin: 0 auto;
                        padding: 0
                    }

                    .halo-comment .comment-body:hover .comment-reply {
                        display: block
                    }

                    .halo-comment .children .comment-body:before {
                        content: "";
                        width: 2px;
                        height: 500%;
                        background: #898c7b;
                        left: 23px;
                        top: -500%;
                        position: absolute
                    }

                    .halo-comment .comment-avatar {
                        position: relative;
                        z-index: 1;
                        float: left;
                        padding: 0
                    }

                    .halo-comment .contain-main {
                        margin-left: 58px
                    }

                    .halo-comment .comment-meta {
                        line-height: 1
                    }

                    .halo-comment .comment-meta .useragent-info {
                        margin-top: 6px;
                        font-size: 10px;
                        color: #657786
                    }

                    .halo-comment .comment-author {
                        font-size: 14px
                    }

                    .halo-comment .comment-author .author-name {
                        font-size: 16px;
                        font-weight: 700;
                        color: #666
                    }

                    .halo-comment .is-admin {
                        margin-left: 4px;
                        font-size: 14px;
                        cursor: pointer
                    }

                    .halo-comment .comment-time {
                        display: inline-block;
                        margin-top: 6px;
                        font-size: 12px;
                        color: #657786
                    }

                    .halo-comment .comment-id {
                        display: block;
                        float: right;
                        margin-top: 6px;
                        font-size: 12px;
                        color: #657786
                    }

                    .halo-comment .comment-content {
                        padding: 20px 20px 19px 0;
                        font-size: 13px;
                        color: #111
                    }

                    .halo-comment .comment-content p {
                        margin: 0
                    }

                    .halo-comment .comment-content p img {
                        width: 50%
                    }

                    .halo-comment .comment-reply {
                        display: none;
                        float: right;
                        font-size: 12px;
                        color: #fff;
                        padding: 1px 5px;
                        border-radius: 3px;
                        line-height: 1.5;
                        background: #898c7b;
                        -webkit-transition: all .2s ease-in-out;
                        transition: all .2s ease-in-out
                    }

                    .halo-comment .comment-reply a, .halo-comment .comment-reply a:hover {
                        color: #fff
                    }

                    .halo-comment .alert {
                        -webkit-animation: top20 .5s;
                        animation: top20 .5s;
                        border-radius: 4px;
                        padding: 8px 16px;
                        background-color: #f44336;
                        color: #fff;
                        opacity: 1;
                        -webkit-transition: opacity .6s;
                        transition: opacity .6s;
                        margin-bottom: 15px
                    }

                    .halo-comment .alert.success {
                        background-color: #4caf50
                    }

                    .halo-comment .alert.info {
                        background-color: #2196f3
                    }

                    .halo-comment .alert.warning {
                        background-color: #ff9800
                    }

                    .halo-comment .alert .closebtn {
                        margin-left: 15px;
                        color: #fff;
                        font-weight: 700;
                        float: right;
                        font-size: 22px;
                        line-height: 16px;
                        cursor: pointer;
                        -webkit-transition: .3s;
                        transition: .3s
                    }

                    .halo-comment .alert .closebtn:hover {
                        color: #000
                    }

                    @media only screen and (max-width: 900px) {
                        .halo-comment .comment-reply {
                            display: block
                        }
                    }

                    @-webkit-keyframes top20 {
                        0% {
                            opacity: 0;
                            -webkit-transform: translateY(-20px);
                            transform: translateY(-20px)
                        }
                        to {
                            opacity: 1;
                            -webkit-transform: translateY(0);
                            transform: translateY(0)
                        }
                    }

                    @keyframes top20 {
                        0% {
                            opacity: 0;
                            -webkit-transform: translateY(-20px);
                            transform: translateY(-20px)
                        }
                        to {
                            opacity: 1;
                            -webkit-transform: translateY(0);
                            transform: translateY(0)
                        }
                    }

                    @-webkit-keyframes bottom20 {
                        0% {
                            opacity: 0;
                            -webkit-transform: translateY(20px);
                            transform: translateY(20px)
                        }
                        to {
                            opacity: 1;
                            -webkit-transform: translateY(0);
                            transform: translateY(0)
                        }
                    }

                    @keyframes bottom20 {
                        0% {
                            opacity: 0;
                            -webkit-transform: translateY(20px);
                            transform: translateY(20px)
                        }
                        to {
                            opacity: 1;
                            -webkit-transform: translateY(0);
                            transform: translateY(0)
                        }
                    }
                </style>
                <div id="halo-comment" class="halo-comment">
                    <section role="form" class="comment-editor">
                        <div class="inner">
                            <div class="commentator">
                                <img src="https://pic.stackoverflow.wiki/uploadImages/114/246/231/87/2020/06/06/02/26/65e10ea4-41e0-4da8-82fa-a00da2770ce2.png"
                                     class="avatar">
                            </div>
                            <form class="comment-form">
                                <div class="author-info">
                                    <input type="text" tabindex="1" required="required" aria-required="true"
                                           id="boloUser" placeholder="你的昵称">
                                    <input type="text" tabindex="2" required="required" aria-required="true"
                                           id="boloSite" placeholder="你的个人主页URL（选填）">
                                </div>
                                <div class="comment-textarea">
                                    <textarea rows="3" placeholder="评论内容只能为 2 到 500 个字符！" id="comment"
                                              readonly="readonly"></textarea>
                                </div>
                            </form>
                        </div>
                    </section>
                    <script type="text/javascript" src="${staticServePath}/js/bolo/sweetalert.min.js"></script>
                    <ol class="comment-nodes" id="comments">
                        <#list commentList as comment>
                            <#include "common-comment.ftl"/>
                        </#list>
                    </ol>
                </div>
            </#if>
        </div>
    </#if>
</#macro>

