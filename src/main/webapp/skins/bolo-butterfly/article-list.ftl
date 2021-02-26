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
<#--        <h1 class="main-title" style="font-family: 'Ubuntu', sans-serif;">-->
<#--            <svg t="1588439558273" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"-->
<#--                 p-id="6535" width="260" height="260">-->
<#--                <path d="M834.35479 512A53.248 53.248 0 0 0 788.47959 566.0672v329.3184A26.2144 26.2144 0 0 1 762.26519 921.6H261.73399A26.2144 26.2144 0 0 1 235.51959 895.3856V128.6144A26.2144 26.2144 0 0 1 261.73399 102.4h319.2832a12.0832 12.0832 0 0 1 10.0352 5.3248L742.39959 327.68H604.15959v-68.8128A53.248 53.248 0 0 0 558.28439 204.8 51.2 51.2 0 0 0 501.75959 256v115.712A58.5728 58.5728 0 0 0 560.12759 430.08h277.7088a52.224 52.224 0 0 0 50.9952-37.0688 49.9712 49.9712 0 0 0-9.8304-46.4896L656.99799 22.7328v-1.2288l-3.4816-2.8672a37.0688 37.0688 0 0 0-3.6864-4.3008A28.4672 28.4672 0 0 0 645.11959 11.264a28.4672 28.4672 0 0 0-3.072-3.072l-4.5056-2.4576-4.3008-2.048-4.9152-1.4336A17.6128 17.6128 0 0 0 624.63959 1.024a26.0096 26.0096 0 0 0-5.7344 0L614.39959 0H242.27799A109.1584 109.1584 0 0 0 133.11959 109.1584v805.6832A109.1584 109.1584 0 0 0 242.27799 1024h539.4432A109.1584 109.1584 0 0 0 890.87959 914.8416V563.2a51.2 51.2 0 0 0-56.5248-51.2z"-->
<#--                      fill="#515151" p-id="6536"></path>-->
<#--                <path d="M389.11959 532.48a51.2 51.2 0 0 0 0 102.4h245.76a51.2 51.2 0 0 0 0-102.4z" fill="#515151"-->
<#--                      p-id="6537"></path>-->
<#--                <path d="M337.91959 716.8m51.2 0l143.36 0q51.2 0 51.2 51.2l0 0q0 51.2-51.2 51.2l-143.36 0q-51.2 0-51.2-51.2l0 0q0-51.2 51.2-51.2Z"-->
<#--                      fill="#515151" p-id="6538"></path>-->
<#--            </svg>-->
<#--            Articles-->
<#--        </h1>-->
        <#--    <i class="fa fa-envira" aria-hidden="true"></i>-->
        <#--        <article class="item">-->
        <#--            <h2 class="item__title">-->
        <#--                <a rel="bookmark" href="${servePath}${article.articlePermalink}">-->
        <#--                    ${article.articleTitle}-->
        <#--                </a>-->
        <#--                <#if article.articlePutTop>-->
        <#--                    <sup>-->
        <#--                        ${topArticleLabel}-->
        <#--                    </sup>-->
        <#--                </#if>-->
        <#--                <#if article.hasUpdated>-->
        <#--                    <sup>-->
        <#--                        <a href="${servePath}${article.articlePermalink}">-->
        <#--                        ${updatedLabel}-->
        <#--                        </a>-->
        <#--                    </sup>-->
        <#--                </#if>-->
        <#--            </h2>-->
        <#--            <a class="item__abstract" pjax-title="${article.articleTitle}"-->
        <#--               href="${servePath}${article.articlePermalink}">-->
        <#--                <#if article.articleAbstractText?length gt 80>-->
        <#--                    ${article.articleAbstractText[0..80]}-->
        <#--                <#else>-->
        <#--                    ${article.articleAbstractText}-->
        <#--                </#if>-->
        <#--            </a>-->
        <#--            <div class="fn__clear">-->
        <#--                ${article.articleUpdateDate?string("yyyy-MM-dd")}   &nbsp;·&nbsp;-->
        <#--                <a href="${servePath}/authors/${article.authorId}">${article.authorName}</a>-->
        <#--                &nbsp;·&nbsp;-->
        <#--                <#list article.articleTags?split(",") as articleTag>-->
        <#--                    <a rel="tag" class="item__tag" href="${servePath}/tags/${articleTag?url('UTF-8')}">-->
        <#--                        ${articleTag}-->
        <#--                    </a> &nbsp;-->
        <#--                </#list>-->
        <#--                    &nbsp;·&nbsp;-->
        <#--                    <a class="item__tag" href="${servePath}${article.articlePermalink}#b3logsolocomments">-->
        <#--                        <span data-uvstatcmt="${article.oId}">0</span> ${commentLabel}-->
        <#--                    </a>-->
        <#--                &nbsp;·&nbsp;-->
        <#--                <a class="item__tag" href="${servePath}${article.articlePermalink}">-->
        <#--                    <span data-uvstaturl="${servePath}${article.articlePermalink}">0</span> ${viewLabel}-->
        <#--                </a>-->
        <#--            </div>-->
        <#--        </article>-->
    <div class="recent-posts" id="recent-posts">
        <#list articles as article>
<#--            <article class="post post-list-thumb" itemscope="" itemtype="http://schema.org/BlogPosting">-->
<#--                <div class="post-thumb-show">-->
<#--                    <a href="${servePath}${article.articlePermalink}">-->
<#--                        <img class="lazyload" referrerpolicy="origin" src="${article.articleImg1URL}"-->
<#--                             onerror="imgError(this,3)" data-src="${article.articleImg1URL}">-->
<#--                    </a>-->
<#--                </div>-->
<#--                <div class="post-content-wrap">-->
<#--                    <div class="post-content">-->
<#--                        <div class="post-date">-->
<#--                            <#if article.articlePutTop>-->
<#--                                ${topArticleLabel}-->
<#--                            </#if>-->
<#--                            <#if article.hasUpdated>-->
<#--                                ${updatedLabel}-->
<#--                            </#if>-->
<#--                            <i class="iconfont icon-time"></i>-->
<#--                            <svg t="1595732236570" class="icon" viewBox="0 0 1024 1024" version="1.1"-->
<#--                                 xmlns="http://www.w3.org/2000/svg" p-id="1544" width="200" height="200">-->
<#--                                <path d="M939.904 721.05984a218.84416 218.84416 0 1 0-218.84416 218.84416 218.84416 218.84416 0 0 0 218.84416-218.84416z"-->
<#--                                      fill="#F4CA1C" p-id="1545"></path>-->
<#--                                <path d="M517.12 962.56a445.44 445.44 0 1 1 445.44-445.44 445.952 445.952 0 0 1-445.44 445.44z m0-819.03104A373.59616 373.59616 0 1 0 890.71616 517.12 374.016 374.016 0 0 0 517.12 143.52896z m139.50976 554.40384a35.80416 35.80416 0 0 1-26.04544-11.17696l-126.49472-133.12a76.32896 76.32896 0 0 1-21.0944-52.80768v-172.9024a35.92192 35.92192 0 1 1 71.84384 0v172.92288a4.81792 4.81792 0 0 0 1.32096 3.31776l126.49984 133.12a35.92192 35.92192 0 0 1-26.03008 60.64128z"-->
<#--                                      fill="#595BB3" p-id="1546"></path>-->
<#--                            </svg>-->
<#--                            ${article.articleUpdateDate?string("yyyy年MM月dd日 HH:mm:ss")}-->
<#--                        </div>-->
<#--                        <a href="${servePath}${article.articlePermalink}" class="post-title">-->
<#--                            <h3>${article.articleTitle}</h3>-->
<#--                        </a>-->
<#--                        <div class="post-meta">-->
<#--                            &lt;#&ndash;                        <span><i class="iconfont icon-attention"></i>${article.articleViewCount} 热度</span>&ndash;&gt;-->
<#--                            <a class="item__tag" href="${servePath}${article.articlePermalink}">-->
<#--                                <svg t="1595733444577" class="icon" viewBox="0 0 1024 1024" version="1.1"-->
<#--                                     xmlns="http://www.w3.org/2000/svg" p-id="4148" width="200" height="200">-->
<#--                                    <path d="M336 972.8c-60.8-128-28.8-201.6 19.2-268.8 51.2-76.8 64-150.4 64-150.4s41.6 51.2 25.6 134.4c70.4-80 83.2-208 73.6-256 160 112 230.4 358.4 137.6 537.6 492.8-281.6 121.6-700.8 57.6-745.6 22.4 48 25.6 128-19.2 166.4-73.6-281.6-256-336-256-336 22.4 144-76.8 300.8-172.8 419.2-3.2-57.6-6.4-96-38.4-153.6-6.4 105.6-86.4 188.8-108.8 294.4C89.6 758.4 140.8 860.8 336 972.8L336 972.8z"-->
<#--                                          p-id="4149" fill="#d81e06"></path>-->
<#--                                </svg>-->
<#--                                <span data-uvstaturl="${servePath}${article.articlePermalink}">-->
<#--                                ${viewLabel}-->
<#--                            </span>-->
<#--                                ${viewLabel}-->
<#--                            </a>-->
<#--                            &nbsp&nbsp-->
<#--                            <a class="item__tag" href="${servePath}${article.articlePermalink}#b3logsolocomments">-->
<#--                                <svg t="1595733609873" class="icon" viewBox="0 0 1024 1024" version="1.1"-->
<#--                                     xmlns="http://www.w3.org/2000/svg" p-id="8528" width="200" height="200">-->
<#--                                    <path d="M572.27 118H97.15C78.92 118 64 132.91 64 151.13v472.04c0 18.22 14.92 33.13 33.15 33.13h95.28c9.3 0 18.16 3.9 24.44 10.75l66.94 100.14c6.57 7.17 17.87 7.17 24.44 0l116.67-100.14a33.162 33.162 0 0 1 24.44-10.75h343.9c18.23 0 33.15-14.91 33.15-33.13V151.13c0-18.22-14.92-33.13-33.15-33.13h-74.58M258.75 440.97c-27.46 0-49.72-22.25-49.72-49.69 0-27.44 22.26-49.69 49.72-49.69s49.72 22.25 49.72 49.69c0 27.45-22.26 49.69-49.72 49.69z m186.46 0c-27.46 0-49.72-22.25-49.72-49.69 0-27.44 22.26-49.69 49.72-49.69 27.46 0 49.72 22.25 49.72 49.69 0 27.45-22.26 49.69-49.72 49.69z m186.46 0c-27.46 0-49.72-22.25-49.72-49.69 0-27.44 22.26-49.69 49.72-49.69 27.46 0 49.72 22.25 49.72 49.69 0 27.45-22.26 49.69-49.72 49.69z"-->
<#--                                          fill="#1296db" p-id="8529"></path>-->
<#--                                    <path d="M926.85 251.45h-49.72V673.8c0 18.22-14.92 33.13-33.15 33.13H483.51c-9.29 0-18.16 3.9-24.44 10.75l-83.96 72.06h199.52c9.29 0 18.16 3.9 24.44 10.75l116.67 100.14c6.57 7.17 17.87 7.17 24.44 0l66.94-100.14a33.162 33.162 0 0 1 24.44-10.75h95.28c18.23 0 33.15-14.91 33.15-33.13V284.58c0.01-18.22-14.91-33.13-33.14-33.13z"-->
<#--                                          fill="#1296db" p-id="8530"></path>-->
<#--                                </svg>-->
<#--                                <span data-uvstatcmt="${article.oId}">0</span> ${commentLabel}-->
<#--                            </a>-->
<#--                            <#list article.articleTags?split(",") as articleTag>-->
<#--                                &nbsp&nbsp-->
<#--                                <a rel="tag" class="item__tag" href="${servePath}/tags/${articleTag?url('UTF-8')}">-->
<#--                                    <svg t="1595733525468" class="icon" viewBox="0 0 1024 1024" version="1.1"-->
<#--                                         xmlns="http://www.w3.org/2000/svg" p-id="4982" width="200" height="200">-->
<#--                                        <path d="M970.8 325.7l-253.1-46c-15-2.7-29.5 7.5-32.3 22.9L613 701.4c-1.3 7.1 0.1 14.4 3.9 20.4l28.5 45.3c2.9-3 6.4-5.6 10.4-7.4 17.5-8.1 38.3-0.5 46.5 17s0.5 38.3-17 46.5c-1.2 0.6-2.5 1.1-3.7 1.5l36.8 58.5c8.6 13.7 26.8 17 39.7 7.2L909.7 775c5.7-4.3 9.5-10.6 10.8-17.7L993 358.5c2.8-15.4-7.1-30.1-22.2-32.8z"-->
<#--                                              fill="#1565C0" p-id="4983"></path>-->
<#--                                        <path d="M636.3 218.8L541 231.4l114.9 528.1c17.5-8 38.2-0.4 46.3 17 8.1 17.5 0.5 38.3-17 46.5-4.8 2.2-9.8 3.3-14.8 3.3l5.1 23.3 45.9 38.4c14.1 11.8 34.9 9 45.4-6l123.9-177.6c4.6-6.6 6.6-14.8 5.5-23l-56.5-424.9-4.2-31.7c-2.3-17.6-18.2-30.1-35.4-27.8l-163.8 21.8z"-->
<#--                                              fill="#F44336" p-id="4984"></path>-->
<#--                                        <path d="M896.2 681.4l-56.5-424.9-203.4-37.7-95.3 12.6 114.9 528.1c17.5-8 38.2-0.4 46.3 17 8.1 17.5 0.5 38.3-17 46.5-4.8 2.2-9.8 3.3-14.8 3.3l5.1 23.3 45.9 38.4c14.1 11.8 34.9 9 45.4-6l123.9-177.6c4.6-6.7 6.6-14.9 5.5-23z"-->
<#--                                              fill="#D32F2F" p-id="4985"></path>-->
<#--                                        <path d="M836.1 613.7L660 260.9l-116.2-21.5L660 260.9l-52.2-104.7c-8.8-17.7-29.9-25-47.2-16.4L270.2 284.7c-17.2 8.6-24.1 29.9-15.2 47.6l228.3 457.5c4.1 8.1 11 14.4 19.4 17.7l138.1 53.1-224.4-644.9 224.4 644.8 86.3 33.1c19.1 7.3 39.9-3.1 45.5-22.7l66-231.1c2.4-8.7 1.5-18-2.5-26.1zM685.2 823.1c-17.5 8.1-38.3 0.5-46.5-17s-0.5-38.3 17-46.5 38.3-0.5 46.5 17 0.5 38.3-17 46.5z"-->
<#--                                              fill="#FFC107" p-id="4986"></path>-->
<#--                                        <path d="M836.1 613.7L660 260.9l-243.7-45.2 224.4 644.8 86.3 33.1c19.1 7.3 39.9-3.1 45.5-22.7l66-231.1c2.5-8.7 1.6-18-2.4-26.1zM685.2 823.1c-17.5 8.1-38.3 0.5-46.5-17s-0.5-38.3 17-46.5 38.3-0.5 46.5 17 0.5 38.3-17 46.5z"-->
<#--                                              fill="#FFA000" p-id="4987"></path>-->
<#--                                        <path d="M771.3 587c-0.1-10.1-4.1-19.9-11.1-27.4l-394.6-420c-15.2-16.2-40.4-17.3-56.2-2.5L42.7 387.6c-15.8 14.9-16.3 40.1-1.1 56.3L436.2 864c7 7.5 16.5 12 26.6 12.8L733 897.2c22.9 1.7 42.1-16.3 41.8-39.3L771.3 587z m-86.1 236.1c-17.5 8.1-38.3 0.5-46.5-17s-0.5-38.3 17-46.5 38.3-0.5 46.5 17 0.5 38.3-17 46.5z"-->
<#--                                              fill="#4CAF50" p-id="4988"></path>-->
<#--                                    </svg>-->
<#--                                    ${articleTag}-->
<#--                                </a> &nbsp;-->
<#--                            </#list>-->
<#--                        </div>-->
<#--                        <div class="float-content">-->
<#--                            <p>-->
<#--                                ${article.articleAbstractText}-->
<#--&lt;#&ndash;                            <div class="post-bottom">&ndash;&gt;-->
<#--&lt;#&ndash;                                <a href="${servePath}${article.articlePermalink}" class="button-normal"><i&ndash;&gt;-->
<#--&lt;#&ndash;                                            class="iconfont icon-caidan"> 阅读全文 </i></a>&ndash;&gt;-->
<#--&lt;#&ndash;                            </div>&ndash;&gt;-->
<#--                        </div>-->
<#--                    </div>-->
<#--                </div>-->
<#--            </article>-->
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
                            <a class="article-meta__categories" href="">${viewLabel}</a>
                            </span>
                            <span class="article-meta tags">
                                <span class="article-meta__separator">|</span>
                                <#list article.articleTags?split(",") as articleTag>
                                <i class="fas fa-tag article-meta__icon"></i>
                                <a class="article-meta__tags" href="${servePath}/tags/${articleTag?url('UTF-8')}">${articleTag}</a>
                                <span class="article-meta__link">•</span>
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
                    <div class="author-info__name">Lonus Lan</div>
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
                <div class="card-info-social-icons is-center"><a class="social-icon"
                                                                 href="https://github.com/lonuslan" target="_blank"
                                                                 title="Github"><i class="fab fa-github"></i></a><a
                            class="social-icon" href="mailto:L158943041@gmail.com" target="_blank" title="Email"><i
                                class="fas fa-envelope"></i></a></div>
            </div>
        </div>
        <div class="card-widget card-announcement">
            <div class="card-content">
                <div class="item-headline"><i
                            class="fas fa-bullhorn card-announcement-animation"></i><span>公告</span></div>
                <div class="announcement_content">
<#--                    <center> 暂无更新通知!</center>-->
<#--                    <div class="twopeople">-->
<#--                        <div class="container" style="height:200px">-->
<#--                            <canvas class="illo" width="480" height="480"-->
<#--                                    style="max-width:200px;max-height:200px;touch-action:none;width:640px;height:640px"></canvas>-->
<#--                        </div>-->
<#--                        <script src="https://cdn.jsdelivr.net/gh/Justlovesmile/CDN/js/twopeople1.js"></script>-->
<#--                        <script src="https://cdn.jsdelivr.net/gh/Justlovesmile/CDN/js/zdog.dist.js"></script>-->
<#--                        <script id="rendered-js" src="https://cdn.jsdelivr.net/gh/Justlovesmile/CDN/js/twopeople.js"></script>-->
<#--                        <style>.twopeople {-->
<#--                                margin: 0;-->
<#--                                align-items: center;-->
<#--                                justify-content: center;-->
<#--                                text-align: center-->
<#--                            }-->

<#--                            canvas {-->
<#--                                display: block;-->
<#--                                margin: 0 auto;-->
<#--                                cursor: move-->
<#--                            }</style>-->
<#--                    </div>-->
<#--                    <script type="text/javascript" id="clstr_globe" src="//clustrmaps.com/globe.js?d=UtH5Dqjggv76eff5Tq_SyjSL6i3POXl-DfmMCRC0ohs"></script>-->
                </div>
            </div>
        </div>
        <div class="card-widget card-recent-post">
            <div class="card-content">
                <div class="item-headline"><i class="fas fa-history"></i><span>最新文章</span></div>

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
                        <div class="webinfo-article-name">文章数目 :</div>
                        <div class="webinfo-article-count">${statistic.statisticPublishedBlogArticleCount}</div>
                    </div>
                    <div class="webinfo-item">
                        <div class="webinfo-runtime-name">已运行时间 :</div>
                        <div class="webinfo-runtime-count" id="webinfo-runtime-count" publish_date="2020-1-1">0
                            天
                        </div>
                    </div>
                    <div class="webinfo-item">
<#--                        <script async src="//busuanzi.ibruce.info/busuanzi/2.3/busuanzi.pure.mini.js"></script>-->
                        <div class="webinfo-site-uv-name">本站在线访客数 :</div>
                        <div class="webinfo-site-uv-count" id="busuanzi_value_site_uv">
                            ${viewCount1Label}${statistic.statisticBlogViewCount}
                            &nbsp;
                            ${articleCount1Label}${statistic.statisticPublishedBlogArticleCount}
                            <#if interactive == "on">
                                &nbsp;
                                ${commentCount1Label}${statistic.statisticPublishedBlogCommentCount}
                            </#if>
                            &nbsp;
                            ${onlineVisitor1Label}${onlineVisitorCnt}
                        </div>
<#--                        busuanzi_value_site_uv-->
                    </div>
                    <div class="webinfo-item">
                        <div class="webinfo-site-name">本站总访问量 :</div>
                        <div class="webinfo-site-pv-count" id="busuanzi_value_site_pv">
                            <span data-uvstaturl="${servePath}">${statistic.statisticBlogViewCount}</span>
                        </div>
<#--                        busuanzi_value_site_pv-->
                    </div>
                </div>
            </div>
        </div>
        <div class="card-widget card-webinfo">
            <div class="card-content">
                <a target="_blank" href="https://www.vultr.com/?ref=7843474">
                    <img class="adimg" src="https://img.lonuslan.com/img/banner_300x250.webp"/>
                </a>
            </div>
        </div>
    </div>

