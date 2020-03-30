<#-- Solo - A small and beautiful blogging system written in Java. Copyright (c) 2010-present, b3log.org This program is
    free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
    This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
    warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
    details. You should have received a copy of the GNU Affero General Public License along with this program. If not,
    see <https://www.gnu.org/licenses/>. -->
    <#include "../../common-template/macro-common_head.ftl">
        <#include "macro-comments.ftl">
            <#include "../../common-template/macro-comment_script.ftl">

                <!DOCTYPE html>
                <html>

                <head>
                    <@head title="${article.articleTitle} - ${blogTitle}" description="${article.articleAbstract?html}">
                        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.7.5/css/bulma.min.css" />
                        <link rel="stylesheet" href="https://ftp.stackoverflow.wiki/bolo/fantastic/css/all.min.css">
                        <link rel="stylesheet"
                            href="${staticServePath}/skins/${skinDirName}/css/base.css?${staticResourceVersion}" />
                        <link rel="stylesheet"
                            href="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.3.5/css/swiper.min.css">
                        <script src='https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.3.5/js/swiper.min.js'></script>
                    </@head>
                    <#-- <#include "style.theme.ftl"> -->
                </head>

                <body class="is-3-column">
                    <#include "header.ftl">
                        <div class="card-normal">
                            <section class="section">
                                <div class="container">
                                    <div class="columns">
                                        <div div
                                            class="column is-12-tablet is-8-desktop is-8-widescreen is-8-fullhd has-order-2 column-main">
                                            <div class="card" style="display: block">
                                                <div class="card-content article " id="card-content"
                                                    style="width: 100%">
                                                    <div
                                                        class="level article-meta is-size-7 is-uppercase is-mobile is-overflow-x-auto">
                                                        <div class="level-left">
                                                            <div class="level-item has-text-grey">
                                                                ${article.articleUpdateDate?string("yyyy年MM月dd日")}
                                                            </div>

                                                        </div>
                                                    </div>
                                                    <h1 class="title is-size-3 is-size-4-mobile has-text-weight-normal">
                                                        ${article.articleTitle}

                                                    </h1>
                                                    <div id="post-article" class="content post-article">
                                                        ${article.articleContent}
                                                    </div>
                                                    <div class="level is-size-7 is-uppercase">
                                                        <div class="level-start">
                                                            <div class="level-item">
                                                                <span class="is-size-6 has-text-grey has-mr-7">#</span>
                                                                <#list article.articleTags?split(",") as articleTag>
                                                                    <a class="has-link-grey -link"
                                                                        href="${servePath}/tags/${articleTag?url('UTF-8')}">${articleTag!}</a>&nbsp;
                                                                </#list>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="card" id="comment-wrapper">
                                                <div class="card-content">
                                                    <h3 class="title is-5 has-text-weight-normal">评论</h3>


                                                </div>
                                            </div>
                                        </div>
                                        <div
                                            class="column is-4-tablet is-3-desktop is-3-widescreen is-3-fullhd  is-hidden-touch  has-order-3 column-right <%= sticky_class(position) %>">
                                            <div class="card widget">
                                                <div class="card-content">
                                                    <h3 class="menu-label">
                                                        相关推荐
                                                    </h3>


                                                    <#-- <#list posts as post>
                                                        <article class="media"> -->
                                                            <#-- <#if post.thumbnail?? && post.thumbnail!=''>
                                                                <a href="${post.fullPath!}" class="media-left">
                                                                    <p class="image is-64x64">
                                                                        <img class="thumbnail" src="${post.thumbnail!}"
                                                                            alt="${post.title!}">
                                                                    </p>
                                                                </a>
                                                                </#if>
                                                                <div class="media-content">
                                                                    <div class="content">
                                                                        <div><time
                                                                                class="has-text-grey is-size-7 is-uppercase"
                                                                                datetime="${post.createTime!}">${post.createTime?string["EEE
                                                                                MMM d"]}</time></div>
                                                                        <a href="${post.fullPath!}"
                                                                            class="title has-link-black-ter is-size-6 has-text-weight-normal">${post.title!}</a>
                                                                    </div>
                                                                </div> -->
                                                                <#-- </article> </#list> -->
                                                </div>
                                            </div>
                                        </div>

                                    </div>

                                </div>
                            </section>
                        </div>
                        <a id="back-to-top" title="返回顶部" href="javascript:;"><i class="fas fa-chevron-up"></i></a>
                        <#include "footer.ftl">
                </body>

                </html>