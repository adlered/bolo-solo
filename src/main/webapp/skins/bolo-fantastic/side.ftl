<#--

    Bolo - A stable and beautiful blogging system based in Solo.
    Copyright (c) 2020, https://github.com/adlered

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
<div
        class="column is-4-tablet is-4-desktop  is-3-widescreen has-order-1  column-left <%= sticky_class(position) %>">
    <div class="card widget profile">
        <div class="card-content">
            <nav class="level">
                <div class="level-item has-text-centered" style="flex-shrink: 1">
                    <div class="level">
                        <figure class="image is-64x64 has-mb-6 level-left" style="margin: 0 auto">
                            <img class="is-rounded" src="${adminUser.userAvatar!}" alt="${userName!}" style="padding: 2px;
box-shadow: 0 1px 2px 0 rgba(0,0,0,0.1);">
                        </figure>
                        <div class="level-right">
                            <div>
                                <p class="title is-4" style="text-align: center">
                                    ${blogTitle!}
                                </p>
                                <p class="is-size-6 is-block" style="text-align: center">
                                    ${blogSubtitle!}
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </nav>
            <nav class="level is-mobile">
                <div class="level-item has-text-centered is-marginless">
                    <div>
                        <p class="heading">
                            文章
                        </p>
                        <p class="title has-text-weight-normal">
                            ${statistic.statisticPublishedBlogArticleCount}
                        </p>
                    </div>
                </div>
                <div class="level-item has-text-centered is-marginless">
                    <div>
                        <p class="heading">
                            评论
                        </p>
                        <p class="title has-text-weight-normal">
                            ${statistic.statisticPublishedBlogCommentCount}
                        </p>
                    </div>
                </div>
                <div class="level-item has-text-centered is-marginless">
                    <div>
                        <p class="heading">
                            浏览
                        </p>
                        <p class="title has-text-weight-normal">
                            ${statistic.statisticBlogViewCount}
                        </p>
                    </div>
                </div>
            </nav>
            <div class="level is-mobile">
                <#if usite??>
                    <#if usite.usiteQQ !=''>
                        <a class="level-item button is-marginless" target="_blank" title="QQ"
                           href="tencent://message/?uin=${usite.usiteQQ}">
                            <i class="fab fa-qq"></i>
                        </a>
                    </#if>
                    <#if usite.usiteWeChat !=''>
                        <a class="level-item button is-marginless" target="_blank" title="WeChat"
                           href="javascript:alert('我的微信号：${usite.usiteWeChat}')">
                            <i class="fab fa-weixin"></i>
                        </a>
                    </#if>
                    <#if usite.usiteWeiBo !=''>
                        <a class="level-item button is-marginless" target="_blank" title="Weibo"
                           href="https://weibo.com/${usite.usiteWeiBo}">
                            <i class="fab fa-weibo"></i>
                        </a>
                    </#if>
                    <#if usite.usiteGitHub !=''>
                        <a class="level-item button is-marginless" target="_blank" title="Github"
                           href="https://github.com/${usite.usiteGitHub}">
                            <i class="fab fa-github"></i>
                        </a>
                    </#if>
                    <#if usite.usiteTwitter !=''>
                        <a class="level-item button is-marginless" target="_blank" title="Twitter"
                           href="https://twitter.com/${usite.usiteTwitter}">
                            <i class="fab fa-twitter"></i>
                        </a>
                    </#if>
                    <#if usite.usiteFacebook !=''>
                        <a class="level-item button is-marginless" target="_blank" title="Facebook"
                           href="https://www.facebook.com/${usite.usiteFacebook}">
                            <i class="fab fa-facebook"></i>
                        </a>
                    </#if>
                    <#if usite.usiteTelegram !=''>
                        <a class="level-item button is-marginless" target="_blank" title="Telegram"
                           href="https://telegram.me/${usite.usiteTelegram}">
                            <i class="fab fa-telegram"></i>
                        </a>
                    </#if>
                </#if>
            </div>
        </div>
    </div>
    <div class="card widget">
        <div class="card-content" style="width:100%">
            <div class="menu">
                <h3 class="menu-label">
                    公告
                </h3>
                <ul class="menu-list">
                    ${noticeBoard}
                </ul>
            </div>
        </div>
    </div>
    <div class="card widget">
        <div class="card-content">
            <div class="menu">
                <h3 class="menu-label">
                    ${categoryLabel}
                </h3>
                <ul class="menu-list">
                    <#list mostUsedCategories as category>
                        <li>
                            <a class="level is-marginless" href="${servePath}/category/${category.categoryURI}">
                                      <span class="level-start">
                                          <span class="level-item">${category.categoryTitle}</span>
                                      </span>
                                <span class="level-end">
                                          <span class="level-item tag">${category.categoryPublishedArticleCount}</span>
                                      </span>
                            </a>
                        </li>
                    </#list>
                </ul>
            </div>
        </div>
    </div>
    <div class="card widget">
        <div class="card-content">
            <div class="menu">
                <h3 class="menu-label">
                    ${tagLabel}
                </h3>
                <div class="field is-grouped is-grouped-multiline">
                    <#list mostUsedTags as tag>
                        <div class="control">
                            <a class="tags has-addons" href="${servePath}/tags/${tag.tagTitle?url('UTF-8')}">
                                <span class="tag">${tag.tagTitle}</span>
                                <span class="tag is-grey">${tag.tagPublishedRefCount!}</span>
                            </a>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
    </div>
    <div class="column-right-shadow is-hidden-widescreen is-6-fullhd">
        <div class="card widget">
            <div class="card-content">
                <h3 class="menu-label">
                    ${mostViewCountArticlesLabel}
                </h3>

                <#list mostViewCountArticles as article>
                    <article class="media">
                        <#if article.articleImg1URL?? && article.articleImg1URL!=''>
                            <a href="${servePath}${article.articlePermalink}" class="media-left">
                                <p class="image is-64x64">
                                    <img class="thumbnail" src="${article.articleImg1URL!}"
                                         alt="${article.articleTitle!}">
                                </p>
                            </a>
                        </#if>
                        <div class="media-content">
                            <div class="content">
                                <div style="padding-top: 10px;">

                                    <div class="has-text-grey is-size-7 is-uppercase">${article.articleViewCount}浏览
                                    </div>
                                </div>
                                <a href="${servePath}${article.articlePermalink}"
                                   class="title has-link-black-ter is-size-6 has-text-weight-normal">${article.articleTitle!}</a>
                            </div>
                        </div>
                    </article>
                </#list>

            </div>
        </div>
        <div class="card widget">
            <div class="card-content">
                <div class="menu">
                    <h3 class="menu-label">
                        ${archiveLabel}
                    </h3>
                    <ul class="menu-list">
                        <#list archiveDates as archiveDate>
                            <li>
                                <a class="level is-marginless"
                                   href="${servePath}/archives/${archiveDate.archiveDateYear}/${archiveDate.archiveDateMonth}">
                                      <span class="level-start">
                                          <span class="level-item">${archiveDate.archiveDateYear} ${yearLabel}
                                              ${archiveDate.archiveDateMonth} ${monthLabel}</span>
                                      </span>
                                    <span class="level-end">
                                          <span
                                                  class="level-item tag">${archiveDate.archiveDatePublishedArticleCount}</span>
                                      </span>
                                </a>
                            </li>
                        </#list>
                    </ul>
                </div>
            </div>
        </div>
    </div>


</div>