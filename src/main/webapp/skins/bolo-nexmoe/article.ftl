<#--

    Solo - A small and beautiful blogging system written in Java.
    Copyright (c) 2010-present, b3log.org

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
        <link rel="stylesheet" href="${staticServePath}/skins/${skinDirName}/css/base.css?${staticResourceVersion}"/>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/mdui@0.4.3/dist/css/mdui.min.css">
        <link rel="stylesheet"
              href="${staticServePath}/skins/${skinDirName}/css/font-icon.css?${staticResourceVersion}">
    </@head>
</head>
<body class="mdui-drawer-body-left mdui-loaded">
<div id="nexmoe-background">
    <#include "header.ftl">
</div>
<div id="nexmoe-header">
    <#include "side.ftl">
</div>
<div id="nexmoe-content">
    <div class="nexmoe-primary">
        <main id="pjax" class="fn__flex-1">
            <#if pjax><!---- pjax {#pjax} start ----></#if>
            <div class="nexmoe-post">
                <div class="nexmoe-post-cover">
                    <img src="${article.articleImg1URL}" alt="${article.articleTitle}"
                         data-src="${article.articleImg1URL}" class=" ls-is-cached lazyloaded">
                    <h1>${article.articleTitle}</h1>
                </div>
                <div class="nexmoe-post-meta">
                <span>
                    <i class="nexmoefont iconfont solo-calendarl"></i>${article.articleUpdateDate?string("yyyy年MM月dd日")}
                </span><span>
                    <i class="nexmoefont iconfont solo-heat"></i>${article.articleViewCount} °C
                </span><#if article.articleCategory != ""><span>
                    <i class="nexomefont iconfont solo-category"></i>${article.articleCategory}</span></#if>
                    <#if article.articleCommentCount != 0><span>
                        <i class="nexmoefont iconfont solo-comment"></i>${article.articleCommentCount}
                        </span></#if>
                    <#list article.articleTags?split(",") as articleTag>
                        <#if articleTag_index == 0>
                            <#if articleCategory??><span>
                                <a class="nexmoefont iconfont solo-category -link"
                                   href="${servePath}/category/${articleCategory.categoryURI}">${articleCategory.categoryTitle}</a>
                                </span>
                            </#if>
                        </#if><span><a class="nexmoefont iconfont solo-tag -link"
                                       href="${servePath}/tags/${articleTag?url('UTF-8')}">${articleTag}</a></span>
                    </#list>

                </div>
                <article>
                    <div class="vditor-reset">
                        ${article.articleContent}
                    </div>
                </article>
                <script>
                    var loggedIn = ${article.logged};
                </script>
                <#if "" != article.articleSign.signHTML?trim>
                    <div class="nexmoe-post-copyright">
                        <i class="mdui-list-item-icon iconfont solo-about2"></i>
                        ${article.articleSign.signHTML}
                    </div>
                </#if>
            </div>
            <div class="post__toc">
                <#if article?? && article.articleToC?? && article.articleToC?size &gt; 0>
                    <#include "../../common-template/toc.ftl"/>
                </#if>
            </div>
            <@comments commentList=articleComments article=article></@comments>
            <#if pjax><!---- pjax {#pjax} end ----></#if>
        </main>

        <#if "" != noticeBoard>
            <div class="nexmoe-hitokoto">
                <p id="hitokoto">${blogSubtitle}</p>
            </div>
        </#if>
        <div class="back-to-top iconfont solo-gotop" onclick="Util.goTop()"></div>
    </div>
</div>
<#include "footer.ftl">
<#if pjax><!---- pjax {#pjax} start ----></#if>
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
<#if pjax><!---- pjax {#pjax} end ----></#if>
</body>
</html>