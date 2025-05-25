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
<#list articles as article>
    <article>
        <header class="fn__flex">
            <h2 class="fn__flex-1">
                <a rel="bookmark" href="${servePath}${article.articlePermalink}">
                    ${article.articleTitle}
                </a>
                <#if article.articlePutTop>
                    <sup>
                        ${topArticleLabel}
                    </sup>
                </#if>
                <#if article.hasUpdated>
                    <sup>
                        <a href="${servePath}${article.articlePermalink}">
                            ${updatedLabel}
                        </a>
                    </sup>
                </#if>
            </h2>
            <time><span class="icon-date"></span> ${article.articleCreateDate?string("yyyy-MM-dd")}</time>
        </header>
        <#if article.articleAbstractText == ''>
            <a class="abstract" href="${servePath}${article.articlePermalink}">
               <img src="${article.articleImg1URL}"/>
            </a>
        <#else>
            <a class="abstract vditor-reset" href="${servePath}${article.articlePermalink}">
                ${article.articleAbstractText}
            </a>
        </#if>
        <footer class="article__footer fn__flex">
            <span class="icon-tag fn__flex-center"></span>
            <div class="tags fn__flex-1 fn__flex-center">
            <span>&nbsp;&nbsp;&nbsp;</span>
            <#if article.articleCategory != "">
                <a class="tag" rel="tag" href="${servePath}/category/${article.categoryURI}">分类：${article.articleCategory}</a>
            </#if>
            <#list article.articleTags?split(",") as articleTag>
                <a class="tag" rel="tag" href="${servePath}/tags/${articleTag?url('UTF-8')}">
                    ${articleTag}</a>
            </#list>
            </div>
            <span>&nbsp;&nbsp;&nbsp;</span>
            <#if interactive == "on">
            <#if article.articleCommentCount != 0>
                <a href="${servePath}${article.articlePermalink}#comments"
                   class="vditor-tooltipped__n vditor-tooltipped link fn__flex-center"
                   aria-label="${commentLabel}">
                    ${article.articleCommentCount}
                    <span class="icon-chat"></span>
                </a>
            </#if>
            </#if>
            <#if article.articleViewCount != 0>
                <a class="vditor-tooltipped__n vditor-tooltipped link fn__flex-center"
                   href="${servePath}${article.articlePermalink}"
                   aria-label="${viewLabel}">
                    ${article.articleViewCount}
                    <span class="icon-views"></span>
                </a>
            </#if>
            <a rel="nofollow" href="${servePath}/authors/${article.authorId}" class="fn__flex-center">
                <img class="avatar" title="${article.authorName}" alt="${article.authorName}"
                     src="${article.authorThumbnailURL}"/>
            </a>
        </footer>
    </article>
</#list>

<#if 0 != paginationPageCount>
    <nav class="pagination">
        <#if 1 != paginationPageNums?first>
            <a href="${servePath}${path}?p=${paginationPreviousPageNum}" class="extend">${previousPageLabel}</a>
            <a class="page-num" href="${servePath}${path}">1</a> ...
        </#if>
        <#list paginationPageNums as paginationPageNum>
            <#if paginationPageNum == paginationCurrentPageNum>
                <span class="current page-num">${paginationPageNum}</span>
            <#else>
                <a class="page-num" href="${servePath}${path}?p=${paginationPageNum}">${paginationPageNum}</a>
            </#if>
        </#list>
        <#if paginationPageNums?last != paginationPageCount> ...
            <a href="${servePath}${path}?p=${paginationPageCount}" class="page-num">${paginationPageCount}</a>
            <a href="${servePath}${path}?p=${paginationNextPageNum}" class="extend">${nextPagePabel}</a>
        </#if>
    </nav>
</#if>
