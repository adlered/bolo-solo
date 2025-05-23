<#--

    Bolo - A stable and beautiful blogging system based in Solo.
    Copyright (c) 2020-present, https://github.com/bolo-blog

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
        <#if previousArticlePermalink??>
            <link rel="prev" title="${previousArticleTitle}" href="${servePath}${previousArticlePermalink}">
        </#if>
        <#if nextArticlePermalink??>
            <link rel="next" title="${nextArticleTitle}" href="${servePath}${nextArticlePermalink}">
        </#if>
    </@head>
</head>
<body>
<#include "side.ftl">
<main>
    <article class="post">
        <header class="fn__flex">
            <h2 class="fn__flex-1">
                <a rel="bookmark" href="${servePath}${article.articlePermalink}">
                    ${article.articleTitle}
                </a>
                <#if article.articlePutTop>
                    <sup>
                        ${topArticleLabel}
                    </sup>
                </#if>
            </h2>
            <time><span class="icon-date"></span> ${article.articleCreateDate?string("yyyy-MM-dd")}/${article.articleUpdateDate?string("yyyy-MM-dd")}</time>
        </header>
        <div class="article__footer fn__flex">
            <span class="icon-tag fn__flex-center"></span>
            <span>&nbsp;&nbsp;&nbsp;</span>
            <div class="tags fn__flex-1 fn__flex-center">
                <#if article.articleCategory != "">
                <a class="tag" rel="tag">分类：${article.articleCategory}</a>
                </#if>
                <#list article.articleTags?split(",") as articleTag>
                    <a class="tag" rel="tag" href="${servePath}/tags/${articleTag?url('UTF-8')}">
                        ${articleTag}</a>
                </#list>
            </div>
            <span>&nbsp;&nbsp;&nbsp;</span>
            <#if interactive == "on">
            <#if article.articleCommentCount != 0>
                <a href="${servePath}${article.articlePermalink}#comments"
                   class="vditor-tooltipped__n vditor-tooltipped link fn__flex-center"
                   aria-label="${commentLabel}">
                    ${article.articleCommentCount}
                    <span class="icon-chat"></span>
                </a>
            </#if>
            </#if>
            <#if article.articleViewCount != 0>
                <a class="vditor-tooltipped__n vditor-tooltipped link fn__flex-center"
                   href="${servePath}${article.articlePermalink}"
                   aria-label="${viewLabel}">
                    ${article.articleViewCount}
                    <span class="icon-views"></span>
                </a>
            </#if>
            <a rel="nofollow" href="${servePath}/authors/${article.authorId}" class="fn__flex-center">
                <img class="avatar" title="${article.authorName}" alt="${article.authorName}"
                     src="${article.authorThumbnailURL}"/>
            </a>
        </div>

        <section class="abstract vditor-reset">
            ${article.articleContent}
            <#if "" != article.articleSign.signHTML?trim>
                <div>
                    ${article.articleSign.signHTML}
                </div>
            </#if>

            <#if nextArticlePermalink?? || previousArticlePermalink??>
                <aside class="fn__flex">
                    <#if previousArticlePermalink??>
                        <a class="fn__flex-1 fn__flex-inline" rel="prev" href="${servePath}${previousArticlePermalink}">
                            <strong>&lt;</strong>
                            <span>&nbsp; ${previousArticleTitle}&nbsp;&nbsp;&nbsp;</span>
                        </a>
                    </#if>
                    <#if nextArticlePermalink??>
                        <a class="fn__flex-inline" rel="next" href="${servePath}${nextArticlePermalink}">
                            <span>${nextArticleTitle}&nbsp; </span>
                            <strong>&gt;</strong>
                        </a>
                    </#if>
                </aside>
            </#if>
        </section>
        <script>
            var loggedIn = ${article.logged};
        </script>

        <footer class="fn-clear share">
            <div class="fn-right">
                        <span class="icon icon-wechat"
                              data-type="wechat"
                              data-title="${article.articleTitle}"
                              data-blogtitle="${blogTitle}"
                              data-url="${servePath}${article.articlePermalink}"
                              data-avatar="${article.authorThumbnailURL}"></span>
                <span class="icon icon-weibo" data-type="weibo"></span>
                <span class="icon icon-twitter" data-type="twitter"></span>
                <span class="icon icon-qqz" data-type="qqz"></span>
            </div>
        </footer>
        <#if 0 != relevantArticlesDisplayCount>
            <div id="relevantArticles" class="abstract"></div>
        </#if>
        <#if 0 != randomArticlesDisplayCount>
            <div id="randomArticles" class="abstract"></div>
        </#if>
        <br>
    </article>
    <#if interactive == "on">
    <@comments commentList=articleComments article=article></@comments>
    </#if>

    <#include "footer.ftl">

    <@comment_script oId=article.oId commentable=article.commentable>
        <#if 0 != randomArticlesDisplayCount>
            page.loadRandomArticles();
        </#if>
        <#if 0 != relevantArticlesDisplayCount>
            page.loadRelevantArticles('${article.oId}', '<h4>${relevantArticles1Label}</h4>');
        </#if>
        Yilia.share()
    </@comment_script>
</main>
</body>
</html>
