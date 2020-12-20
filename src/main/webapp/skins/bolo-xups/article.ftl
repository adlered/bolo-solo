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
        <link rel="stylesheet" href="${staticServePath}/skins/${skinDirName}/css/style.css?${staticResourceVersion}" />
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
            <section class="box" id="JELON__commentBox">
                <div class="com-text">
                    <div class="main">
                        <#if interactive == "on">
                            <@comments commentList=articleComments article=article></@comments>
                        </#if>
                    </div>
                </div>
            </section>

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
                    </ul>
                </section>
            </div>
        </div>
    </section>
</div>

<#include "footer.ftl">

<@comment_script oId=article.oId commentable=article.commentable>
    page.tips.externalRelevantArticlesDisplayCount = "${externalRelevantArticlesDisplayCount}";
    <#if 0 != randomArticlesDisplayCount>
        page.loadRandomArticles('<h3>${randomArticlesLabel}</h3>');
    </#if>
    <#if 0 != externalRelevantArticlesDisplayCount>
        page.loadExternalRelevantArticles("<#list article.articleTags?split(",") as articleTag>${articleTag}<#if articleTag_has_next>,</#if></#list>",
        '<h3>${externalRelevantArticlesLabel}</h3>');
    </#if>
    <#if 0 != relevantArticlesDisplayCount>
        page.loadRelevantArticles('${article.oId}', '<h3>${relevantArticlesLabel}</h3>');
    </#if>
    Skin.initArticle()
</@comment_script>
</body>
</html>
