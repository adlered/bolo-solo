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
<#include "header.ftl">
<div class="main" id="pjax">
    <#if pjax><!---- pjax {#pjax} start ----></#if>
    <div class="content">
        <main id="articlePage">
            <div class="article-list">
                <div class="item item--active">
                    <time class="vditor-tooltipped vditor-tooltipped__n item__date"
                          aria-label="${article.articleCreateDate?string("yyyy")}${yearLabel}">
                    ${article.articleCreateDate?string("MM")}${monthLabel}
                        <span class="item__day">${article.articleCreateDate?string("dd")}</span>
                    </time>

                    <h2 class="item__title">
                        <a rel="bookmark" href="${servePath}${article.articlePermalink}">
                        ${article.articleTitle}
                        </a>
                    <#if article.articlePutTop>
                    <sup>
                        ${topArticleLabel}
                    </sup>
                    </#if>
                    </h2>

                    <div class="item__date--m fn__none">
                        <i class="icon__date"></i>
                    ${article.articleCreateDate?string("yyyy-MM-dd")}/${article.articleUpdateDate?string("yyyy-MM-dd")}
                    </div>

                    <div class="ft__center">
                        <#if article.articleCategory != "">
                        <span class="tag">
                            <i class="icon__category"></i>
                            <a rel="tag">${article.articleCategory}</a>
                        </span>
                        </#if>
                        <span class="tag">
                            <i class="icon__tags"></i>
                            <#list article.articleTags?split(",") as articleTag>
                            <a rel="tag" href="${servePath}/tags/${articleTag?url('UTF-8')}">
                                ${articleTag}</a><#if articleTag_has_next>,</#if>
                            </#list>
                        </span>
                        <#if interactive == "on">
                        <a class="tag" href="${servePath}${article.articlePermalink}#comments">
                            <i class="icon__comments"></i> ${article.articleCommentCount} ${commentLabel}
                        </a>
                        </#if>
                        <span class="tag">
                            <i class="icon__views"></i>
                        ${article.articleViewCount} ${viewLabel}
                        </span>
                    </div>

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
            </div>

            <#if previousArticlePermalink?? || nextArticlePermalink??>
            <div class="module mobile__hidden">
                <div class="module__content fn__clear">
                    <#if previousArticlePermalink??>
                        <a href="${servePath}${previousArticlePermalink}" rel="prev" class="fn__left breadcrumb">
                            ${previousArticleLabel}: ${previousArticleTitle}
                        </a>
                    </#if>
                    <#if nextArticlePermalink??>
                        <a href="${servePath}${nextArticlePermalink}" rel="next"
                           class="fn__right breadcrumb">
                            ${nextArticleTitle}: ${nextArticleLabel}
                        </a>
                    </#if>
                </div>
            </div>
            </#if>

            <#if previousArticlePermalink??>
            <div class="module mobile__hidden fn__none">
                <div class="module__content">
                    <a href="${servePath}${previousArticlePermalink}" rel="prev" class="breadcrumb">
                        ${previousArticleLabel}: ${previousArticleTitle}
                    </a>
                </div>
            </div>
            </#if>

            <#if nextArticlePermalink??>
            <div class="module mobile__hidden fn__none">
                <div class="module__content">
                    <a href="${servePath}${nextArticlePermalink}" rel="next"
                       class="breadcrumb">
                        ${nextArticleLabel}: ${nextArticleTitle}
                    </a>
                </div>
            </div>
            </#if>

            <#if interactive == "on">
            <@comments commentList=articleComments article=article></@comments>
            </#if>

            <div class="fn__flex article__relevant">
                <div class="mobile__hidden">&nbsp; &nbsp; &nbsp; &nbsp; </div>
                <div class="fn__flex-1" id="randomArticlesWrap">
                    <div class="module">
                        <div id="randomArticles" class="module__list"></div>
                    </div>
                </div>
                <div class="mobile__hidden">&nbsp; &nbsp; &nbsp; &nbsp; </div>
                <div class="fn__flex-1" id="relevantArticlesWrap">
                    <div class="module">
                        <div id="relevantArticles" class="module__list"></div>
                    </div>
                </div>
            </div>
        </main>
    </div>
    <#include "side.ftl">
    <#if pjax><!---- pjax {#pjax} end ----></#if>
    </div>
</div>
<#include "footer.ftl">
<#if pjax><!---- pjax {#pjax} start ----></#if>
<@comment_script oId=article.oId commentable=article.commentable>
    <#if 0 != randomArticlesDisplayCount>
    page.loadRandomArticles('<header class="module__header">${randomArticlesLabel}</header>');
    </#if>
    <#if 0 != relevantArticlesDisplayCount>
    page.loadRelevantArticles('${article.oId}', '<header class="module__header">${relevantArticlesLabel}</header>');
    </#if>
    Skin.initArticle()
</@comment_script>
<#if pjax><!---- pjax {#pjax} end ----></#if>
</body>
</html>
