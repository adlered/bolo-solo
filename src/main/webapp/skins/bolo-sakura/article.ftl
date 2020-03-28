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

        <div class="pattern-center single-center">
            <div class="pattern-attachment-img"><img src="${article.articleImg1URL}"
                                                     data-src="${article.articleImg1URL}"
                                                     class="lazyload" onerror="imgError(this,3)"
                                                     style="width: 100%; height: 100%; object-fit: cover; pointer-events: none;"
                                                     referrerpolicy="origin">
            </div>
            <header class="pattern-header single-header"><h1 class="entry-title">${article.articleTitle}</h1>
                <p class="entry-census"><span><a href="https://2heng.xin/author/Mashiro/"><img
                                    src="${article.authorThumbnailURL}"></a></span><span><a
                                href="https://2heng.xin/author/Mashiro/">${article.authorName}</a></span><span
                            class="bull">·</span>${article.articleUpdateDate?string("yyyy-MM-dd")}<span
                            class="bull">·</span>${article.articleViewCount} 次阅读<span
                            class="bull">·</span>${article.articleCommentCount} ${commentLabel}</p>
                <#if article.articleCategory != '' && article.categoryURI??>
                    <a href="${servePath}/category/${article.categoryURI}">${article.articleCategory}</a> »
                </#if>
                <#list article.articleTags?split(",") as articleTag>
                <a rel="tag" href="${servePath}/tags/${articleTag?url('UTF-8')}">
                    ${articleTag}</a><#if articleTag_has_next> </#if>
                </#list></header>
        </div>

        <div id="content" class="site-content">
            <div id="primary" class="content-area">
                <#if pjax><!---- pjax {#pjax} start ----></#if>
                <main id="main" class="site-main" role="main">
                    <article class="post-4491 post type-post status-publish format-standard has-post-thumbnail hentry category-hacking tag-graphql tag-javascript tag-wordpress">
                        <div class="entry-content">

                            ${article.articleContent}
                            <#if "" != article.articleSign.signHTML?trim>
                                <div>

                                    ${article.articleSign.signHTML}
                                </div>
                            </#if>

                            <script>
                                var loggedIn = ${article.logged};
                            </script>
                        </div>
                    </article>
                </main>
                <#if pjax><!---- pjax {#pjax} end ----></#if>
            </div>
        </div>

        <section id="comments" class="comments">
            <h3 id="comments-list-title">Comments | <span class="noticom">${article.articleCommentCount} ${commentLabel} </span></h3>
            <div id="loading-comments">
                <span></span>
            </div>
            <ul class="commentwrap">
                <@comments commentList=articleComments article=article></@comments>
            </ul>
        </section>
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