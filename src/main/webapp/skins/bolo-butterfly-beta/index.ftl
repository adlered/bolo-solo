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
    <@head title="${blogTitle}">
        <link rel="stylesheet" href="${staticServePath}/skins/${skinDirName}/css/base.css?${staticResourceVersion}"/>
    </@head>
</head>
<#--<body class="fn__flex-column">-->
<#--<div id="pjax" class="fn__flex-1">-->
<#--    <#if pjax><!---- pjax {#pjax} start --&ndash;&gt;</#if>-->
<#--&lt;#&ndash;    <#include "macro-header.ftl">&ndash;&gt;-->
<#--    <#include "define-header.ftl">-->
<#--    <@header type='index'></@header>-->
<#--    <div class="wrapper_main web-topage">-->
<#--        <#include "article-list.ftl">-->
<#--    </div>-->
<#--    <#if pjax><!---- pjax {#pjax} end --&ndash;&gt;</#if>-->
<#--</div>-->
<#--<#include "define-header.ftl">-->

<#--<#include "footer.ftl">-->
<#--</body>-->
<body>
<#include "index-header.ftl">
<@header type='index'></@header>
<div id="body-wrap" class="body-index">
    <main class="layout_page" id="content-inner">
        <#include "article-list.ftl">
    </main>
</div>
<#include "footer.ftl">
</body>
</html>