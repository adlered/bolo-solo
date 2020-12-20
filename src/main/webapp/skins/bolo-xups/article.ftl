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
<#include "../../common-template/macro-common_head.ftl">
<#include "macro-comments.ftl">
<#include "../../common-template/macro-comment_script.ftl">
<!DOCTYPE html>
<html>
<head>
    <@head title="${article.articleTitle} - ${blogTitle}" description="${article.articleAbstract?html}">
        <link rel="stylesheet"
              href="${staticServePath}/skins/${skinDirName}/css/base.css?${staticResourceVersion}"/>
    </@head>
</head>
<body>
<#include "header.ftl">

<div class="container body clearfix">
    <section class="content">
        <div class="content-article widget">
            <!-- 文章页 -->
            <!-- 文章 -->
            <article class="post article">
                <header class="text-center">
                    <h3 class="post-title"><span>${article.articleTitle}</span></h3>
                </header>
                <p class="post-meta text-center">
                    ${article.authorName} 发表于
                    ${article.articleCreateDate?string("yyyy-MM-dd")}
                    <br>
                    更新日期 ${article.articleUpdateDate?string("yyyy-MM-dd")}
                </p>
                <div class="post-content">
                    <div class="vditor-reset">
                        ${article.articleContent}
                        <#if "" != article.articleSign.signHTML?trim>
                            <div>
                                ${article.articleSign.signHTML}
                            </div>
                        </#if>
                    </div>
                    <script>
                        var loggedIn = ${article.logged};
                    </script>
                </div>
            </article>

            <!-- 文章评论 -->
            <#if interactive == "on">
                <@comments commentList=articleComments article=article></@comments>

            <div id="comments" class="comment">

                <section class="list-wrap" id="JELON__commentList">
                    <header class="list-header">
                        总共
                        <span class="comments-num" id="JELON__commentsNum">4</span> 条评论
                    </header>
                    <ul class="list">
                        <li class="item">
                            <div class="user-avatar">
                                <a target="_blank" href="https://github.com/wangzhhuan"
                                ><img
                                            src="https://avatars0.githubusercontent.com/u/72487622?v=4"
                                            alt="user-avatar"
                                    /></a>
                            </div>
                            <div class="user-comment">
                                <div
                                        class="user-comment-header"
                                        id="JELON__comment_704787684_reactions"
                                >
                  <span class="post-name">wangzhhuan</span
                  ><span class="post-time">2020-10-07 16:43</span
                                    ><span class="like" onclick="JELON.Actions.like(704787684)"
                                    >点赞</span
                                    ><span class="like-num">1</span
                                    ><span
                                            class="reply"
                                            onclick="JELON.Actions.reply('wangzhhuan', ' 和肉体和 ')"
                                    >回复</span
                                    >
                                </div>
                                <div class="user-comment-body"><p>和肉体和</p></div>
                            </div>
                        </li>
                        <li class="item">
                            <div class="user-avatar">
                                <a target="_blank" href="https://github.com/jangdelong"
                                ><img
                                            src="https://avatars3.githubusercontent.com/u/5547151?v=4"
                                            alt="user-avatar"
                                    /></a>
                            </div>
                            <div class="user-comment">
                                <div
                                        class="user-comment-header"
                                        id="JELON__comment_705559686_reactions"
                                >
                  <span class="post-name">jangdelong</span
                  ><span class="post-time">2020-10-08 21:14</span
                                    ><span class="like" onclick="JELON.Actions.like(705559686)"
                                    >点赞</span
                                    ><span class="like-num">1</span
                                    ><span
                                            class="reply"
                                            onclick="JELON.Actions.reply('jangdelong', '  @wangzhhuan        和肉体和      ?? ')"
                                    >回复</span
                                    >
                                </div>
                                <div class="user-comment-body">
                                    <p>
                                        <a
                                                class="user-mention"
                                                data-hovercard-type="user"
                                                data-hovercard-url="/users/wangzhhuan/hovercard"
                                                data-octo-click="hovercard-link-click"
                                                data-octo-dimensions="link_type:self"
                                                href="https://github.com/wangzhhuan"
                                        >@wangzhhuan</a
                                        >
                                    </p>
                                    <blockquote>
                                        <p>&nbsp;&nbsp;和肉体和&nbsp;</p>
                                    </blockquote>
                                    <p>??</p>
                                </div>
                            </div>
                        </li>
                        <li class="item">
                            <div class="user-avatar">
                                <a target="_blank" href="https://github.com/lihansen12345"
                                ><img
                                            src="https://avatars0.githubusercontent.com/u/67905541?v=4"
                                            alt="user-avatar"
                                    /></a>
                            </div>
                            <div class="user-comment">
                                <div
                                        class="user-comment-header"
                                        id="JELON__comment_732840469_reactions"
                                >
                  <span class="post-name">lihansen12345</span
                  ><span class="post-time">2020-11-24 19:11</span
                                    ><span class="like" onclick="JELON.Actions.like(732840469)"
                                    >点赞</span
                                    ><span class="like-num">2</span
                                    ><span
                                            class="reply"
                                            onclick="JELON.Actions.reply('lihansen12345', ' 可以直接在这里发布吧 ')"
                                    >回复</span
                                    >
                                </div>
                                <div class="user-comment-body"><p>可以直接在这里发布吧</p></div>
                            </div>
                        </li>
                        <li class="item">
                            <div class="user-avatar">
                                <a target="_blank" href="https://github.com/jangdelong"
                                ><img
                                            src="https://avatars3.githubusercontent.com/u/5547151?v=4"
                                            alt="user-avatar"
                                    /></a>
                            </div>
                            <div class="user-comment">
                                <div
                                        class="user-comment-header"
                                        id="JELON__comment_733319527_reactions"
                                >
                  <span class="post-name">jangdelong</span
                  ><span class="post-time">2020-11-25 07:58</span
                                    ><span class="like" onclick="JELON.Actions.like(733319527)"
                                    >点赞</span
                                    ><span class="like-num">0</span
                                    ><span
                                            class="reply"
                                            onclick="JELON.Actions.reply('jangdelong', '  @lihansen12345        可以直接在这里发布吧      可以的 ')"
                                    >回复</span
                                    >
                                </div>
                                <div class="user-comment-body">
                                    <p>
                                        <a
                                                class="user-mention"
                                                data-hovercard-type="user"
                                                data-hovercard-url="/users/lihansen12345/hovercard"
                                                data-octo-click="hovercard-link-click"
                                                data-octo-dimensions="link_type:self"
                                                href="https://github.com/lihansen12345"
                                        >@lihansen12345</a
                                        >
                                    </p>
                                    <blockquote>
                                        <p>&nbsp;&nbsp;可以直接在这里发布吧&nbsp;</p>
                                    </blockquote>
                                    <p>可以的</p>
                                </div>
                            </div>
                        </li>
                    </ul>
                    <div class="page-nav">
                        <a href="javascript: void(0);" class="item current">1</a>
                    </div>
                </section>
            </div>
            </#if>
        </div>
    </section>
</div>

<#include "footer.ftl">

<@comment_script oId=article.oId commentable=article.commentable>
    page.tips.externalRelevantArticlesDisplayCount = "${externalRelevantArticlesDisplayCount}";
    <#if 0 != randomArticlesDisplayCount>
    page.loadRandomArticles('<h3>RECOMMEND POSTS</h3>');
    </#if>
    <#if 0 != externalRelevantArticlesDisplayCount>
    page.loadExternalRelevantArticles("<#list article.articleTags?split(",") as articleTag>${articleTag}<#if articleTag_has_next>,</#if></#list>",
    '<h3>HACPAI POSTS</h3>');
    </#if>
    <#if 0 != relevantArticlesDisplayCount>
    page.loadRelevantArticles('${article.oId}', '<h3>RELEVANT POSTS</h3>');
    </#if>
Skin.initArticle()
</@comment_script>
</body>
</html>
