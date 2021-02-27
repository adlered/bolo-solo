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
    <div class="recent-posts" id="recent-posts">
        <#list articles as article>
            <article class="recent-post-item" itemscope="" itemtype="http://schema.org/BlogPosting">
                    <div class="post_cover">
                        <a href="${servePath}${article.articlePermalink}" title="${article.articleTitle}">
                            <img class="post_bg" data-src="${article.articleImg1URL}" onerror="this.onerror=null;this.src='${staticServePath}/skins/${skinDirName}/images/404.jpg'" alt="Hello World">
                        </a>
                    </div>
                    <div class="recent-post-info">
                        <a class="article-title" href="${servePath}${article.articlePermalink}" title="${article.articleTitle}">${article.articleTitle}</a>
                        <div class="article-meta-wrap">
                            <time class="post-meta__date" title="">
                                <i class="far fa-calendar-alt"></i>
                                <#if article.articlePutTop>
                                    ${topArticleLabel}
                                </#if>
                                <#if article.hasUpdated>
                                    ${updatedLabel}
                                </#if>
                                ${article.articleUpdateDate?string("yyyy年MM月dd日 HH:mm:ss")}
                            </time>
                            <span class="article-meta"><span class="article-meta__separator">|</span>
                            <i class="fas fa-boxes article-meta__icon"></i>
                            <a class="article-meta__categories" href="">${article.articleViewCount} ${viewLabel}</a>
                            </span>
                            <span class="article-meta tags">
                                <span class="article-meta__separator">|</span>
                                <#list article.articleTags?split(",") as articleTag>
                                <i class="fas fa-tag article-meta__icon"></i>
                                <a class="article-meta__tags" href="${servePath}/tags/${articleTag?url('UTF-8')}">${articleTag}</a>
                                <span class="article-meta__link"> </span>
                                </#list>
                            </span>
                        </div>
                        <div class="content">${article.articleAbstractText}
                        </div>
                    </div>
            </article>
        </#list>
        <#if 0 != paginationPageCount>
            <nav class="pagination">
                <#if 1 != paginationPageNums?first>
                    <a href="${servePath}${path}${pagingSep}${paginationPreviousPageNum}"
                       aria-label="${previousPageLabel}"
                       class="pagination__item vditor-tooltipped__n vditor-tooltipped">&laquo;</a>
                    <a class="pagination__item" href="${servePath}${path}">1</a>
                    <span class="pagination__item pagination__item--omit">...</span>
                </#if>
                <#list paginationPageNums as paginationPageNum>
                    <#if paginationPageNum == paginationCurrentPageNum>
                        <span class="pagination__item pagination__item--active">${paginationPageNum}</span>
                    <#else>
                        <a class="pagination__item"
                           href="${servePath}${path}?p=${paginationPageNum}">${paginationPageNum}</a>
                    </#if>
                </#list>
                <#if paginationPageNums?last != paginationPageCount>
                    <span class="pagination__item pagination__item--omit">...</span>
                    <a href="${servePath}${path}${pagingSep}${paginationPageCount}"
                       class="pagination__item">${paginationPageCount}</a>
                    <a href="${servePath}${path}${pagingSep}${paginationNextPageNum}" aria-label="${nextPagePabel}"
                       class="pagination__item vditor-tooltipped__n vditor-tooltipped">&raquo;</a>
                </#if>
            </nav>
        </#if>
    </div>
    <div class = "aside_content" id = "aside_content">
        <div class="card-widget card-info">
            <div class="card-content">
                <div class="card-info-avatar is-center"><img class="avatar-img"
                                                             onerror="this.onerror=null;this.src=null;"
                                                             alt="avatar"
                                                             src="${adminUser.userAvatar}">
                    <div class="author-info__name">${blogTitle}</div>
                    <div class="author-info__description">${blogSubtitle}</div>
                </div>
                <div class="card-info-data">
                    <div class="card-info-data-item is-center"><a href="${servePath}">
                            <div class="headline">${articleLabel}</div>
                            <div class="length_num">${statistic.statisticPublishedBlogArticleCount}</div>
                        </a></div>
                    <div class="card-info-data-item is-center"><a href="${servePath}/categories.html">
                            <div class="headline">${categoryLabel}</div>
                            <div class="length_num">${mostUsedCategories?size}</div>
<#--                            ${categories?size}-->
                        </a></div>
                    <div class="card-info-data-item is-center"><a href="${servePath}/tags.html">
                            <div class="headline">${tagLabel}</div>
                            <div class="length_num">${mostUsedTags?size}</div>
<#--                            ${tag.tagPublishedRefCount}-->
<#--                            ${tags?size}-->
                        </a></div>
                </div>
                <div class="card-info-bookmark is-center">
                    <button class="button--animated" id="bookmark-it" title="加入书签"><i
                                class="fas fa-bookmark"></i><span>加入书签</span></button>
                </div>
                <#if usite??>
                <div class="card-info-social-icons is-center">
                    <#if usite.usiteQQ !=''>
                        <a class="social-icon" target="_blank" title="QQ"
                           href="tencent://message/?uin=${usite.usiteQQ}">
                            <i class="fab fa-qq"></i>
                        </a>
                    </#if>
                    <#if usite.usiteWeChat !=''>
                        <a class="social-icon" target="_blank" title="WeChat"
                           href="javascript:alert('我的微信号：${usite.usiteWeChat}')">
                            <i class="fab fa-weixin"></i>
                        </a>
                    </#if>
                    <#if usite.usiteWeiBo !=''>
                        <a class="social-icon" target="_blank" title="Weibo"
                           href="https://weibo.com/${usite.usiteWeiBo}">
                            <i class="fab fa-weibo"></i>
                        </a>
                    </#if>
                    <#if usite.usiteGitHub !=''>
                        <a class="social-icon" target="_blank" title="Github"
                           href="https://github.com/${usite.usiteGitHub}">
                            <i class="fab fa-github"></i>
                        </a>
                    </#if>
                    <#if usite.usiteTwitter !=''>
                        <a class="social-icon" target="_blank" title="Twitter"
                           href="https://twitter.com/${usite.usiteTwitter}">
                            <i class="fab fa-twitter"></i>
                        </a>
                    </#if>
                    <#if usite.usiteFacebook !=''>
                        <a class="social-icon" target="_blank" title="Facebook"
                           href="https://www.facebook.com/${usite.usiteFacebook}">
                            <i class="fab fa-facebook"></i>
                        </a>
                    </#if>
                    <#if usite.usiteTelegram !=''>
                        <a class="social-icon" target="_blank" title="Telegram"
                           href="https://telegram.me/${usite.usiteTelegram}">
                            <i class="fab fa-telegram"></i>
                        </a>
                    </#if>
                </div>
                </#if>
            </div>
        </div>
        <div class="card-widget card-announcement">
            <div class="card-content">
                <div class="item-headline"><i
                            class="fas fa-bullhorn card-announcement-animation"></i><span>公告</span></div>
                <div class="announcement_content">
                    ${noticeBoard}
                </div>
            </div>
        </div>
        <div class="card-widget card-tags">
            <div class="card-content">
                <div class="item-headline"><i class="fas fa-tags"></i><span>标签</span></div>
                <div class="card-tag-cloud tagcloud">
                    <#if 0 != mostUsedTags?size>
                    <#list mostUsedTags as tag>
                        <a rel="tag"
                           href="${servePath}/tags/${tag.tagTitle?url('UTF-8')}"
                           class=""
                           aria-label="${tag.tagPublishedRefCount} ${countLabel}${articleLabel}">
                            ${tag.tagTitle}</a>
                    </#list>
                    </#if>
                </div>
            </div>
        </div>
        <div class="card-widget card-categories">
            <div class="card-content">
                <div class="item-headline"><i class="fas fa-folder-open"></i><span>分类</span></div>
                <ul class="card-category-list">
                    <#if 0 != mostUsedCategories?size>
                        <#list mostUsedCategories as category>
                            <li class="card-category-list-item ">
                            <a href="${servePath}/category/${category.categoryURI}"
                               aria-label="${category.categoryPublishedArticleCount} ${articleLabel}"
                               class="">
                                <span class="card-category-list-name category-title">${category.categoryTitle}</span></a>
                            </li>
                        </#list>
                    </#if>
                </ul>
            </div>
        </div>
        <div class="card-widget card-archives">
            <div class="card-content">
                <div class="item-headline"><i class="fas fa-archive"></i><span>归档</span></div>
                <ul class="card-archive-list">
                    <#if 0 != archiveDates?size>
                        <#list archiveDates as archiveDate>
                            <#if archiveDate_index < 10>
                                <#if archiveDate.archiveDatePublishedArticleCount != 0>
                                <li class="card-archive-list-item">
                                    <#if "en" == localeString?substring(0, 2)>
                                        <a href="${servePath}/archives/${archiveDate.archiveDateYear}/${archiveDate.archiveDateMonth}"
                                           class="card-archive-list-link"
                                           title="${archiveDate.monthName}${archiveDate.archiveDateYear}(${archiveDate.archiveDatePublishedArticleCount})">
                                            <span class="card-archive-list-date">${archiveDate.monthName}${archiveDate.archiveDateYear}</span>
                                            <span class="card-archive-list-count">${archiveDate.archiveDatePublishedArticleCount}</span>
                                        </a>
                                    <#else>
                                        <a href="${servePath}/archives/${archiveDate.archiveDateYear}/${archiveDate.archiveDateMonth}"
                                           class="card-archive-list-link"
                                           title="${archiveDate.archiveDateYear}${yearLabel}${archiveDate.archiveDateMonth}${monthLabel}(${archiveDate.archiveDatePublishedArticleCount})">
                                            <span class="card-archive-list-date">${archiveDate.archiveDateMonth}${monthLabel}${archiveDate.archiveDateYear}</span>
                                            <span class="card-archive-list-count">${archiveDate.archiveDatePublishedArticleCount}</span>
                                        </a>
                                    </#if>
                                </li>
                                </#if>
                            </#if>
                        </#list>
                    </#if>
                </ul>
            </div>
        </div>
        <div class="card-widget card-webinfo">
            <div class="card-content">
                <div class="item-headline"><i class="fas fa-chart-line"></i><span>网站资讯</span></div>
                <div class="webinfo">
                    <div class="webinfo-item">
                        <div class="webinfo-article-name">${articleViewCountLabel}</div>
                        <div class="webinfo-article-count">${statistic.statisticPublishedBlogArticleCount}</div>
                    </div>
                    <div class="webinfo-item">
                        <div class="webinfo-runtime-name">${viewCount1Label}</div>
                        <div class="webinfo-runtime-count">${statistic.statisticBlogViewCount}</div>
                    </div>
                    <div class="webinfo-item">
                        <div class="webinfo-runtime-name">${articleCount1Label}</div>
                        <div class="webinfo-runtime-count">${statistic.statisticPublishedBlogArticleCount}</div>
                    </div>
                    <#if interactive == "on">
                    <div class="webinfo-item">
                        <div class="webinfo-runtime-name">${commentCount1Label}</div>
                        <div class="webinfo-runtime-count">${statistic.statisticPublishedBlogCommentCount}</div>
                    </div>
                    </#if>
                    <div class="webinfo-item">
                        <div class="webinfo-runtime-name">${onlineVisitor1Label}</div>
                        <div class="webinfo-runtime-count">${onlineVisitorCnt}</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

