<#--

    Solo - A small and beautiful blogging system written in Java.
    Copyright (c) 2010-present, b3log.org

    Solo is licensed under Mulan PSL v2.
    You can use this software according to the terms and conditions of the Mulan PSL v2.
    You may obtain a copy of Mulan PSL v2 at:
            http://license.coscl.org.cn/MulanPSL2
    THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
    See the Mulan PSL v2 for more details.

-->
<#include "../../common-template/macro-common_head.ftl">
<!DOCTYPE html>
<html>
<head>
    <@head title="${linkLabel} - ${blogTitle}">
        <link rel="stylesheet" href="${staticServePath}/skins/${skinDirName}/css/base.css?${staticResourceVersion}"/>
    </@head>
</head>
<body class="fn__flex-column">
<div id="pjax" class="fn__flex-1">
    <#if pjax><!---- pjax {#pjax} start ----></#if>
    <#include "index-header.ftl">
    <@header type='index'></@header>
    <main class="layout_post" id="content-inner">
        <article id="post">
            <div class="wrapper web-topage">
                <h2 class="other__title"><a href="${servePath}" class="ft__a">${blogTitle}</a> - ${linkLabel}</h2>
                <div class="ft__center">
                    ${links?size} ${linkLabel}
                </div>
                <div class="articles vditor-reset link_main">
                    <div class="flink-list">
                        <#if 0 != links?size>
                            <#list links as link>
                                <div class="flink-list-item"
                                     style="--primary-color:linear-gradient( 135deg, #FFF886 10%, #F072B6 100%);
                     border-width:1px;border-style:solid;
                     animation:link_custom1 1s infinite alternate;--primary-rotate:0deg;">
                                    <a href="${link.linkAddress}" title="${link.linkTitle}" target="_blank">
                                        <img class="rauto"
                                             style="animation:auto_rotate_left .5s linear infinite;"
                                             data-lazy-src="https://blog.lete114.top/img/Lete.png"
                                             onerror="null"
                                             alt="${link.linkIcon}"
                                             src="${link.linkIcon}">
                                        <span class="flink-item-name">${link.linkTitle}</span>
                                        <span class="flink-item-desc">${link.linkDescription}</span>
                                    </a>
                                </div>
                            </#list>
                        </#if>
                    </div>
            </div>
        </article>
    </main>
    <#if pjax><!---- pjax {#pjax} end ----></#if>
</div>
    <#include "footer.ftl">
</body>
</html>
