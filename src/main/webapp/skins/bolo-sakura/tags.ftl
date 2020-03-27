<#include "../../common-template/macro-common_head.ftl">
<#include 'header.ftl'>
<body nonce-data="4fb3a4be0d" class="home blog hfeed chinese-font">
<div class="scrollbar" id="bar">
</div>
<section id="main-container">
    <div class="headertop filter-dot">
        <#include 'macro-header.ftl'>
        <div id="content" class="site-content">
            <div id="primary" class="content-area">
                <#if pjax><!---- pjax {#pjax} start ----></#if>
                <h3 class="link-title"><span class="fake-title">分类</span></h3>
                <#list mostUsedCategories as category>
                    <span style="width: 256px; display: inline-block">
                        <a href="${servePath}/category/${category.categoryURI}">
                            ${category.categoryTitle}
                        </a>
                        <span>${category.categoryTagCnt} 篇</span>
                    </span>
                </#list>

                <h3 class="link-title"><span class="fake-title">标签</span></h3>
                <#list tags as tag>
                    <span style="width: 256px; display: inline-block">
                        <a rel="tag" data-count="${tag.tagPublishedRefCount}" class="tag"
                           href="${servePath}/tags/${tag.tagTitle?url('UTF-8')}">
                            ${tag.tagTitle}
                        </a>
                        <span>${tag.tagPublishedRefCount} ${countLabel}</span>
                    </span>
                </#list>
                <#if pjax><!---- pjax {#pjax} end ----></#if>
            </div>
        </div>
    </div>
    <#include 'macro-footer.ftl'>
</section>
<#include 'side-mobile.ftl'>
<#include 'footer.ftl'>
</body>
</html>