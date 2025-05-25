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
<#include "../common-template/macro-common_head.ftl"/>
<!DOCTYPE html>
<html>
    <head>
        <@head title="${adminConsoleLabel} - ${blogTitle}">
            <link type="text/css" rel="stylesheet" href="${staticServePath}/scss/admin.css?${staticResourceVersion}" />
            <link type="text/css" rel="stylesheet" href="${staticServePath}/js/lib/Chart.min.css" />
            <meta name="robots" content="fn__none" />
            <link href="https://ftp.stackoverflow.wiki/bolo/start/css/font-awesome.min.css" rel="stylesheet">
        </@head>
    </head>
    <body onhashchange="admin.setCurByHash();">
        <div class="tip"><span id="loadMsg">${loadingLabel}</span></div>
        <div class="tip tip-msg"><span id="tipMsg"></span></div>
        <div id="allPanel">
            <div id="top">
                <a href="${servePath}" target="_blank" class="hover">
                    ${blogTitle}
                </a>
                <span class="icon-unordered-list top__menu fn__none"
                      onclick="admin.toggleMenu()"></span>
                <span class="fn__right">
                    <a href="${servePath}" title='${indexLabel}'>
                        <div class="avatar" style="background-image: url(${gravatar})"></div>
                        ${userName}
                    </a>
                    <a href='javascript:admin.logout();' title='${logoutLabel}'>${logoutLabel}</a>
                </span>
            </div>
            <div id="tabs">
                <ul>
                    <li>
                        <div id="tabs_main">
                            <a href="#main">
                                <i class="fa fa-home"></i>
                                ${adminIndexLabel}
                            </a>
                        </div>
                    </li>
                    <li>
                        <div id="tabArticleTitle" class="tab-current" onclick="admin.collapseNav(this)">
                            <i class="fa fa-align-left"></i>
                            ${articleLabel}
                            <span class="icon-chevron-up fn__right"></span>
                        </div>
                        <ul id="tabArticleMgt">
                            <li>
                                <div id="tabs_article">
                                    <a href="#article/article" onclick="admin.article.prePost()">${postArticleLabel}</a>
                                </div>
                            </li>
                            <li>
                                <div id="tabs_article-list">
                                    <a href="#article/article-list">${articleListLabel}</a>
                                </div>
                            </li>
                            <li>
                                <div id="tabs_draft-list">
                                    <a href="#article/draft-list">${draftListLabel}</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <div id="tabs_comment-list">
                            <a href="#comment-list">
                                <i class="fa fa-commenting"></i>
                                ${commentListLabel}
                            </a>
                        </div>
                    </li>
                    <li>
                        <div id="tabToolsTitle" onclick="admin.collapseNav(this)">
                            <i class="fa fa-gear"></i>
                            ${ToolLabel}
                            <span class="icon-chevron-down fn__right"></span>
                        </div>
                        <ul class="fn__none" id="tabTools">
                            <li>
                                <div id="tabs_preference">
                                    <a href="#tools/preference">${preferenceLabel}</a>
                                </div>
                            </li>
                            <li>
                                <div id="tabs_usite">
                                    <a href="#tools/usite">${usiteLabel}</a>
                                </div>
                            </li>
                            <li>
                                <div id="tabs_theme-list">
                                    <a href="#tools/theme-list">${skinLabel}</a>
                                </div>
                            </li>
                            <li>
                                <div id="tabs_category-list">
                                    <a href="#tools/category-list">${categoryListLabel}</a>
                                </div>
                            </li>
                            <li>
                                <div id="tabs_page-list">
                                    <a href="#tools/page-list">${navMgmtLabel}</a>
                                </div>
                            </li>
                            <li>
                                <div id="tabs_link-list">
                                    <a href="#tools/link-list">${linkManagementLabel}</a>
                                </div>
                            </li>
                            <li>
                                <div id="tabs_follow-list">
                                    <a href="#tools/follow-list">${followManagementLabel}</a>
                                </div>
                            </li>
                            <li>
                                <div id="tabs_user-list">
                                    <a href="#tools/user-list">${userManageLabel}</a>
                                </div>
                            </li>
                            <li>
                                <div id="tabs_plugin-list">
                                    <a href="#tools/plugin-list">${pluginMgmtLabel}</a>
                                </div>
                            </li>
                            <li>
                                <div id="tabs_others">
                                    <a href="#tools/others/data">${functionalLabel}</a>
                                </div>
                            </li>
                            <li>
                                <div id="tabs_tool-box">
                                    <a href="#tools/tool-box">${toolBoxLabel}</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <div id="tabs_about">
                            <a href="#about">
                                <i class="fa fa-info-circle"></i>
                                ${aboutLabel}
                            </a>
                        </div>
                    </li>
                    <li>
                        <div>
                            <a href="https://doc.stackoverflow.wiki/web/#/7?page_id=46" target="_blank">
                                <i class="fa fa-question-circle"></i>
                                ${helpLabel}
                            </a>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="tabs__bg" onclick="admin.toggleMenu()"></div>
            <div id="tabsPanel">
                <div id="tabsPanel_main" class="fn__none"></div>
                <div id="tabsPanel_article" class="fn__none"></div>
                <div id="tabsPanel_article-list" class="fn__none"></div>
                <div id="tabsPanel_draft-list" class="fn__none"></div>
                <div id="tabsPanel_link-list" class="fn__none"></div>
                <div id="tabsPanel_follow-list" class="fn__none"></div>
                <div id="tabsPanel_preference" class="fn__none"></div>
                <div id="tabsPanel_theme-list" class="fn__none"></div>
                <div id="tabsPanel_category-list" class="fn__none"></div>
                <div id="tabsPanel_page-list" class="fn__none"></div>
                <div id="tabsPanel_others" class="fn__none"></div>
                <div id="tabsPanel_user-list" class="fn__none"></div>
                <div id="tabsPanel_comment-list" class="fn__none"></div>
                <div id="tabsPanel_plugin-list" class="fn__none"></div>
                <div id="tabsPanel_about" class="fn__none"></div>
                <div id="tabsPanel_tool-box" class="fn__none"></div>
                <div id="tabsPanel_usite" class="fn__none"></div>
            </div>
            <div class="fn__clear"></div>
            <script type="text/javascript">
                var boloVersion = '${boloVersion}';
            </script>
            <script type="text/javascript">
                var data1 = {
                    labels: [
                        <#if latestArchives?exists && (latestArchives?size > 0)>
                            <#list latestArchives?reverse as archiveDate>
                                "${archiveDate.archiveDateYear}/${archiveDate.archiveDateMonth}",
                            </#list>
                        </#if>
                    ],
                    datasets: [{
                        label: '文章数量',
                        fill: false,
                        backgroundColor: '#FFFFFF',
                        borderColor: '#36A2EB',
                        pointBackgroundColor: "#36A2EB",
                        pointBorderColor: "#fff",
                        data: [
                            <#if latestArchives?exists && (latestArchives?size > 0)>
                                <#list latestArchives?reverse as archiveDate>
                                    ${archiveDate.archiveDatePublishedArticleCount},
                                </#list>
                            </#if>
                        ],
                    }]
                };

                var data2 = {
                    datasets: [
                        {
                            data: [
                                <#if tagsTop5?exists && (tagsTop5?size > 0)>
                                    <#list tagsTop5 as tag>
                                        ${tag.tagPublishedRefCount},
                                    </#list>
                                </#if>
                            ],
                            backgroundColor: [
                                'rgb(254,67,101)',
                                'rgb(252,157,154)',
                                'rgb(249,205,173)',
                                'rgb(200,200,169)',
                                'rgb(131,175,155)',
                            ],
                        }
                    ],

                    labels: [
                        <#if tagsTop5?exists && (tagsTop5?size > 0)>
                            <#list tagsTop5 as tag>
                                "${tag.tagTitle}",
                            </#list>
                        </#if>
                    ]
                };
            </script>
            <div class="footer">
                Powered by <a href="https://github.com/adlered/bolo-solo" target="_blank">Bolo</a> <span id="version">${boloVersion}</span>
            </div>
        </div>
        <script src="${staticServePath}/js/lib/compress/admin-lib.min.js"></script>
        <script src="https://file.fishpi.cn/vditor/3.8.13/dist/index.min.js"></script>
        <script src="${staticServePath}/js/common.js"></script>
        <#if "" == miniPostfix>
        <script src="${staticServePath}/js/admin/admin.js"></script>
        <script src="${staticServePath}/js/admin/editor.js"></script>
        <script src="${staticServePath}/js/admin/tablePaginate.js"></script>
        <script src="${staticServePath}/js/admin/article.js"></script>
        <script src="${staticServePath}/js/admin/comment.js"></script>
        <script src="${staticServePath}/js/admin/articleList.js"></script>
        <script src="${staticServePath}/js/admin/draftList.js"></script>
        <script src="${staticServePath}/js/admin/pageList.js"></script>
        <script src="${staticServePath}/js/admin/others.js"></script>
        <script src="${staticServePath}/js/admin/linkList.js"></script>
        <script src="${staticServePath}/js/admin/followList.js"></script>
        <script src="${staticServePath}/js/admin/preference.js"></script>
        <script src="${staticServePath}/js/admin/pluginList.js"></script>
        <script src="${staticServePath}/js/admin/userList.js"></script>
        <script src="${staticServePath}/js/admin/categoryList.js"></script>
        <script src="${staticServePath}/js/admin/commentList.js"></script>
        <script src="${staticServePath}/js/admin/plugin.js"></script>
        <script src="${staticServePath}/js/admin/main.js"></script>
        <script src="${staticServePath}/js/admin/about.js"></script>
        <script src="${staticServePath}/js/admin/themeList.js"></script>
        <script src="${staticServePath}/js/admin/toolBox.js"></script>
        <script src="${staticServePath}/js/admin/usite.js"></script>
        <#else>
        <script src="${staticServePath}/js/admin/admin.min.js?${staticResourceVersion}"></script>
        </#if>
        <script src="${staticServePath}/js/lib/Chart.min.js"></script>
        <script type="text/javascript" src="${staticServePath}/js/bolo/sweetalert.min.js"></script>
        <script>
            function noBtnSwal(msg, timer) {
                if (timer === 0) {
                    swal({
                        text: msg,
                        buttons: false
                    });
                } else {
                    swal({
                        text: msg,
                        buttons: false,
                        timer: timer
                    });
                }
            }
        </script>
        <#include "admin-label.ftl">
        ${plugins}
        <script>
            admin.inited();
        </script>
    </body>
</html>
