<#include "../../common-template/macro-common_head.ftl">
<#include "macro-comments.ftl">
<#include "../../common-template/macro-comment_script.ftl">
<#include 'header.ftl'>
<body nonce-data="4fb3a4be0d" class="home blog hfeed chinese-font">
<div class="scrollbar" id="bar">
</div>
<section id="main-container">
    <div class="headertop filter-dot">
        <#include 'macro-header.ftl'>
        <div id="content" class="site-content">
            <div id="primary" class="content-">
                <#if pjax><!---- pjax {#pjax} start ----></#if>
                <div class="content">
                    <main id="articlePage">
                        <div class="article-list">
                            <div class="item item--active">
                                <time class="vditor-tooltipped vditor-tooltipped__n item__date"
                                      aria-label="${article.articleUpdateDate?string("yyyy")}${yearLabel}">
                                    ${article.articleUpdateDate?string("MM")}${monthLabel}
                                    <span class="item__day">${article.articleUpdateDate?string("dd")}</span>
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
                                    ${article.articleUpdateDate?string("yyyy-MM-dd")}
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
                                    <a class="tag" href="${servePath}${article.articlePermalink}#comments">
                                        <i class="icon__comments"></i> ${article.articleCommentCount} ${commentLabel}
                                    </a>
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

                        <@comments commentList=articleComments article=article></@comments>

                        <div class="fn__flex article__relevant">
                            <div class="fn__flex-1" id="externalRelevantArticlesWrap">
                                <div class="module">
                                    <div id="externalRelevantArticles" class="module__list"></div>
                                </div>
                            </div>
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
                <#if pjax><!---- pjax {#pjax} end ----></#if>
            </div>
        </div>
    </div>
    <#include 'macro-footer.ftl'>
</section>
<#include 'side-mobile.ftl'>
<#include 'footer.ftl'>
<#if pjax><!---- pjax {#pjax} start ----></#if>
<@comment_script oId=article.oId commentable=article.commentable>
    Skin.initArticle()
</@comment_script>
<#if pjax><!---- pjax {#pjax} end ----></#if>
</body>
</html>