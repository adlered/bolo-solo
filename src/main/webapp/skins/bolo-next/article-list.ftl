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
<section class="posts-expand">
    <#list articles as article>
        <article class="post-item">
            <header>
                <h2>
                    <a class="post-title-link" rel="bookmark" href="${servePath}${article.articlePermalink}">
                        ${article.articleTitle}
                    </a>
                    <#if article.articlePutTop>
                        <sup>
                            ${topArticleLabel}
                        </sup>
                    </#if>
                    <#if article.hasUpdated>
                        <sup>
                            <a class="post__sup" href="${servePath}${article.articlePermalink}">
                                ${updatedLabel}
                            </a>
                        </sup>
                    </#if>
                </h2>

                <div class="post-meta">
                <span>
                    <#if article.articleCreateDate?datetime != article.articleUpdateDate?datetime>
                        ${updateTimeLabel}
                    <#else>
                        ${postTimeLabel}
                    </#if>
                    <time>
                        ${article.articleCreateDate?string("yyyy-MM-dd")}
                    </time>
                </span>
                    <span>
                        <#if interactive == "on">
                    &nbsp; | &nbsp;
                    <a href="${servePath}${article.articlePermalink}#comments">
                        ${article.articleCommentCount} ${cmtLabel}</a>
                </span>
                </#if>
                    &nbsp; | &nbsp;${viewsLabel} ${article.articleViewCount}°C
                </div>
            </header>
            <div class="vditor-reset">
                ${article.articleAbstract}
            </div>
            <div class="post-more-link">
                <a href="${servePath}${article.articlePermalink}#more" rel="contents">
                    ${readLabel} &raquo;
                </a>
            </div>
        </article>
    </#list>
</section>

<#if 0 != paginationPageCount>
    <nav class="pagination">
        <#if 1 != paginationPageNums?first>
            <a href="${servePath}${path}?p=${paginationPreviousPageNum}" class="extend next"><<</a>
            <a class="page-number" href="${servePath}${path}">1</a> ...
        </#if>
        <#list paginationPageNums as paginationPageNum>
            <#if paginationPageNum == paginationCurrentPageNum>
                <span class="page-number current">${paginationPageNum}</span>
            <#else>
                <a class="page-number" href="${servePath}${path}?p=${paginationPageNum}">${paginationPageNum}</a>
            </#if>
        </#list>
        <#if paginationPageNums?last != paginationPageCount> ...
            <a href="${servePath}${path}?p=${paginationPageCount}" class="page-number">${paginationPageCount}</a>
            <a href="${servePath}${path}?p=${paginationNextPageNum}" class="extend next">>></a>
        </#if>
    </nav>
</#if>
