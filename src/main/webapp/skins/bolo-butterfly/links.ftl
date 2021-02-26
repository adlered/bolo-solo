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
                    <br>
                    <#--            <#if 0 != links?size>-->
                    <#--                <#list links as link>-->
                    <#--                    <div class="other__item">-->
                    <#--                        <img id="friend_link_icon" src="${link.linkIcon}">-->
                    <#--                        <a rel="friend" href="${link.linkAddress}" target="_blank">-->
                    <#--&lt;#&ndash;                            <img id="friend_link_icon" src="${link.linkIcon}">&ndash;&gt;-->
                    <#--                            ${link.linkTitle}-->
                    <#--                        </a>-->
                    <#--                        <div>${link.linkDescription}</div>-->
                    <#--                    </div>-->
                    <#--                </#list>-->
                    <#--            </#if>-->
                    <#--            <br><br>-->
                    <h2 id="">Lonus Lan 的贵宾席</h2>
                    <br>
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

                    <#--        描述性文字  -->
                    <div class="link_des">
                        <h2 id="">申请友链</h2>
                        <ol>
                            <li>如果您觉得本站还不错，欢迎您添加本站为您的友链。</li>
                            <li>进入下方评论区按照下面的格式留言吧。</li>
                            <li>请耐心等待博主审核。</li>
                        </ol>
                        <blockquote>
                            <ol>
                                <li>
                                    <p>关于修改信息</p>
                                    <p>如果你已经审核通过，但是需要更改当时的信息，那么你可以再次进入评论区重新发表评论，并备注修改信息，等待博主审核通过就可以修改你的信息了！</p>
                                </li>
                                <li>
                                    <p>关于清理友链</p>
                                    <p>我会不定期访问您的友链，如果出现网站无法访问、404、友链入口难以发现、删除本站友链等情况我会直接将你的网站在此站上移除，如需再次添加友链，请重新申请。</p>
                                </li>
                            </ol>
                        </blockquote>
                        <h3>我的信息</h3>
                        <pre>
                    <code>name: lonuslan<br>link: https://www.lonuslan.com/<br>avatar: https://img.lonuslan.com/lonuslan/20200414/SPGX7UvyK7aT.png<br>descr: May the god bless you!
                    </code>
                    </pre>
                    </div>
                    <#include "define-comment.ftl">
                </div>
            </div>
        </article>
    </main>
    <#if pjax><!---- pjax {#pjax} end ----></#if>
</div>
    <#include "footer.ftl">
</body>
</html>
