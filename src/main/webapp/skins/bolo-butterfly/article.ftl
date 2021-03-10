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
        <#if previousArticlePermalink??>
            <link rel="prev" title="${previousArticleTitle}" href="${servePath}${previousArticlePermalink}">
        </#if>
        <#if nextArticlePermalink??>
            <link rel="next" title="${nextArticleTitle}" href="${servePath}${nextArticlePermalink}">
        </#if>
    </@head>
</head>
<body class="fn__flex-column">
<script>
    var loggedIn = ${article.logged};
</script>
<div id="pjax" class="fn__flex-1">
    <#if pjax><!---- pjax {#pjax} start ----></#if>
    <#include "define-header.ftl">
    <@header type="article"></@header>
    <div class="article__top" style="background-image: url(${article.articleImg1URL})">
        <div style="background-image: url(${article.articleImg1URL})"></div>
        <canvas id="articleTop"></canvas>
    </div>

    <main class="layout_post" id="content-inner">
        <article id="post">
            <div class="article">
                <div class="ft__center">
                    <div class="article__meta">
                        <time>
                            ${article.articleCreateDate?string("yyyy-MM-dd")} / ${article.articleUpdateDate?string("yyyy-MM-dd")}
                        </time>
                        /
                        <#list article.articleTags?split(",") as articleTag>
                            <a rel="tag"
                               href="${servePath}/tags/${articleTag?url('UTF-8')}">${articleTag}</a> &nbsp;
                        </#list>
                    </div>
                    <h2 class="article__title">
                        ${article.articleTitle}
                        <#if article.articlePutTop>
                            <sup>
                                ${topArticleLabel}
                            </sup>
                        </#if>
                    </h2>
                    <#include "../../common-template/share.ftl">
                </div>
                <div class="wrapper">
                    <section id="article-container">
                        ${article.articleContent}
                        <#if "" != article.articleSign.signHTML?trim>
                            <div>
                                ${article.articleSign.signHTML}
                            </div>
                        </#if>
                    </section>
                </div>
            </div>

            <#if article?? && article.articleToC?? && article.articleToC?size &gt; 0>
                <div class="post__toc">
                    <#include "../../common-template/toc.ftl"/>
                </div>
            </#if>

            <div class="article__bottom">
                <div class="wrapper">
                    <div class="fn__flex">
                        <div class="item" id="randomArticles"></div>
                        <div class="item" id="relevantArticles"></div>
                    </div>

                    <#if interactive == "on">
                        <section class="comments">
                            <ul class="commentwrap">
                                <@comments commentList=articleComments article=article count=article.articleCommentCount></@comments>
                            </ul>
                        </section>
                    </#if>
                </div>
            </div>
        </article>
    </main>
    <#if pjax><!---- pjax {#pjax} end ----></#if>
</div>
<script type="text/javascript"
        src="${staticServePath}/skins/${skinDirName}/js/TweenMax.min.js?${staticResourceVersion}"
        charset="utf-8"></script>
<#include "footer.ftl">
<#if pjax><!---- pjax {#pjax} start ----></#if>
<script src="${staticServePath}/js/lib/jquery/jquery.min.js" charset="utf-8"></script>
<@comment_script oId=article.oId commentable=article.commentable>
    <#if 0 != randomArticlesDisplayCount>
        page.loadRandomArticles('<h3>${randomArticlesLabel}</h3>');
    </#if>
    <#if 0 != relevantArticlesDisplayCount>
        page.loadRelevantArticles('${article.oId}', '<h3>${relevantArticlesLabel}</h3>');
    </#if>
    Skin.initArticle()
</@comment_script>
<#if pjax><!---- pjax {#pjax} end ----></#if>
</body>
</html>
