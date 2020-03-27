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
                <div class="links">
                    <h3 class="link-title"><span class="fake-title">友情链接</span></h3>
                    <ul class="link-items fontSmooth">
                        <#list links as link>
                            <li class="link-item"><a class="link-item-inner effect-apollo" href="${link.linkAddress}" title="${link.linkTitle}" target="_blank" rel="friend"><img class="lazyload" onerror="imgError(this,1)" data-src="${link.linkIcon}" src="${link.linkIcon}"><span class="sitename">${link.linkTitle}</span>
                                    <div class="linkdes">
                                        ${link.linkDescription}
                                    </div>
                                </a></li>
                        </#list>
                    </ul>
                </div>
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