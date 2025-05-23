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
        <link rel="stylesheet" href="${staticServePath}/skins/${skinDirName}/css/style.css?${staticResourceVersion}"/>
    </@head>
</head>
<body>
<#include "header.ftl">
<div id="pjax">
    <#if pjax><!---- pjax {#pjax} start ----></#if>
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
            </#if>
        </div>
    </section>
</div>
    <#if pjax><!---- pjax {#pjax} end ----></#if>
</div>

<#include "footer.ftl">

<div id="pjax">
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
</div>
<#if pjax><!---- pjax {#pjax} end ----></#if>
</body>
</html>
