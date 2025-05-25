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
<body class="fn__flex-column">
<div id="pjax" class="fn__flex-1">
    <#if pjax><!---- pjax {#pjax} start ----></#if>
    <#include "macro-header.ftl">
    <@header type="article"></@header>
    <div class="article__top" style="background-image: url(${article.articleImg1URL})">
        <div style="background-image: url(${article.articleImg1URL})"></div>
        <canvas id="articleTop"></canvas>
    </div>
    <div class="article">
        <div class="ft__center">
            <div class="article__meta">
                <time>
                    ${article.articleCreateDate?string("yyyy-MM-dd")}/${article.articleUpdateDate?string("yyyy-MM-dd")}
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
            <section class="vditor-reset articles article__content">
                ${article.articleContent}
                <#if "" != article.articleSign.signHTML?trim>
                    <div>
                        ${article.articleSign.signHTML}
                    </div>
                </#if>
            </section>
        </div>
        <script>
            var loggedIn = ${article.logged};
        </script>
    </div>
    <#if article?? && article.articleToC?? && article.articleToC?size &gt; 0>
        <div class="post__toc">
            <#include "../../common-template/toc.ftl"/>
        </div>
    </#if>
    <#if interactive == "on">
    <@comments commentList=articleComments article=article></@comments>
    </#if>
    <div class="article__bottom">
        <div class="wrapper">
            <div class="fn__flex">
                <div class="item" id="randomArticles"></div>
                <div class="item" id="relevantArticles"></div>
            </div>
        </div>
    </div>
    <#if pjax><!---- pjax {#pjax} end ----></#if>
</div>
<script type="text/javascript"
        src="${staticServePath}/skins/${skinDirName}/js/TweenMax${miniPostfix}.js?${staticResourceVersion}"
        charset="utf-8"></script>
<#include "footer.ftl">
<#if article?? && article.articleToC?? && article.articleToC?size &gt; 0>
    <svg viewBox="0 0 32 32" width="100%" height="100%" class="side__top side__top--toc">
        <path d="M30 18h-28c-1.1 0-2-0.9-2-2s0.9-2 2-2h28c1.1 0 2 0.9 2 2s-0.9 2-2 2zM30 6.25h-28c-1.1 0-2-0.9-2-2s0.9-2 2-2h28c1.1 0 2 0.9 2 2s-0.9 2-2 2zM2 25.75h28c1.1 0 2 0.9 2 2s-0.9 2-2 2h-28c-1.1 0-2-0.9-2-2s0.9-2 2-2z"></path>
    </svg>
</#if>
<#if pjax><!---- pjax {#pjax} start ----></#if>
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
