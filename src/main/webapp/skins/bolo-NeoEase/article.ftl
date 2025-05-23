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
        ${topBarReplacement}
        <#include "header.ftl">
        <div class="body">
            <div class="wrapper">
                <div class="main">
                    <div class="page">
                        <h2>
                            <a class="article-title" href="${servePath}${article.articlePermalink}">
                                ${article.articleTitle}
                            </a>
                            <#if article.articlePutTop>
                            <sup class="tip">
                                ${topArticleLabel}
                            </sup>
                            </#if>
                        </h2>
                        <div class="left article-element">
                            <span class="date-ico" title="${dateLabel}">
                                ${article.articleCreateDate?string("yyyy-MM-dd")}/${article.articleUpdateDate?string("yyyy-MM-dd")}
                            </span>
                            <span class="user-ico" title="${authorLabel}">
                                <a rel="nofollow" href="${servePath}/authors/${article.authorId}">${article.authorName}</a>
                            </span>
                        </div>
                        <div class="right article-element">
                            <#if interactive == "on">
                            <a rel="nofollow" href="${servePath}${article.articlePermalink}#comments">
                                ${article.articleCommentCount}&nbsp;&nbsp;${commentLabel}
                            </a>&nbsp;&nbsp;
                            </#if>
                            <a rel="nofollow" href="${servePath}${article.articlePermalink}">
                                ${article.articleViewCount}&nbsp;&nbsp;${viewLabel}
                            </a>
                        </div>
                        <div class="clear"></div>
                        <div class="vditor-reset vditor-reset--article">
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
                        <div class="article-element article-element--article">
                            <span class="tag-ico" title="${tagsLabel}">
                                <#list article.articleTags?split(",") as articleTag>
                                <a rel="tag" href="${servePath}/tags/${articleTag?url('UTF-8')}">
                                    ${articleTag}</a><#if articleTag_has_next>,</#if>
                                </#list>
                            </span>
                        </div>
                        <div class="article-panel1">
                            <#if nextArticlePermalink??>
                            <div class="right">
                                <a href="${servePath}${nextArticlePermalink}">${nextArticle1Label}${nextArticleTitle}</a>
                            </div><div class="clear"></div>
                            </#if>
                            <#if previousArticlePermalink??>
                            <div class="right">
                                <a href="${servePath}${previousArticlePermalink}">${previousArticle1Label}${previousArticleTitle}</a>
                            </div>
                            </#if>
                            <div class="clear"></div>
                        </div>
                        <div class="article-panel2">
                            <div id="relevantArticles" class="left" style="width: 50%;"></div>
                            <div id="randomArticles" class="left"></div>
                            <div class="clear" style="height: 15px;"></div>
                        </div>
                    </div>
                    <#if interactive == "on">
                    <@comments commentList=articleComments article=article></@comments>
                    </#if>
                </div>
                <#include "side.ftl">
                <div class="clear"></div>
            </div>
        </div>
        <#include "footer.ftl">
        <@comment_script oId=article.oId commentable=article.commentable>
        <#if 0 != randomArticlesDisplayCount>
        page.loadRandomArticles();
        </#if>
        <#if 0 != relevantArticlesDisplayCount>
        page.loadRelevantArticles('${article.oId}', '<h4>${relevantArticlesLabel}</h4>');
        </#if>
        </@comment_script>
    </body>
</html>
