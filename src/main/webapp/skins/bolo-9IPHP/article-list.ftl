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
<div>
    <#list articles as article>
    <article class="post">
        <header>
            <h2>
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

            <div class="meta">
                <span class="vditor-tooltipped vditor-tooltipped__n"
                      aria-label="<#if article.articleCreateDate?datetime != article.articleUpdateDate?datetime>${updateDateLabel}<#else>${createDateLabel}</#if>">
                    <i class="icon-date"></i>
                    <time>
                        ${article.articleCreateDate?string("yyyy-MM-dd")}
                    </time>
                </span>
                &nbsp; | &nbsp;
                <#if interactive == "on">
                <span class="vditor-tooltipped vditor-tooltipped__n" aria-label="${commentCountLabel}">
                    <i class="icon-comments"></i>
                    <a href="${servePath}${article.articlePermalink}#comments">
                        ${article.articleCommentCount} ${commentLabel}</a>
                </span>
                &nbsp; | &nbsp;
                </#if>
                <span class="vditor-tooltipped vditor-tooltipped__n" aria-label="${viewCountLabel}">
                    <i class="icon-views"></i>
                    ${article.articleViewCount} ${viewLabel}
                </span>
            </div>
        </header>
        <div class="vditor-reset">
            ${article.articleAbstract}
        </div>
        <footer class="fn-clear tags">
            <#if article.articleCategory != "">
                <a class="tag" rel="tag" href="${servePath}/category/${article.categoryURI}">所属分类 > ${article.articleCategory}</a>
            </#if>
            <#list article.articleTags?split(",") as articleTag>
                <a class="tag" rel="tag" href="${servePath}/tags/${articleTag?url('UTF-8')}">
                    ${articleTag}</a>
            </#list>
            <a href="${servePath}${article.articlePermalink}#more" rel="contents" class="fn-right">
                ${readLabel} &raquo;
            </a>
        </footer>
    </article>
    </#list>


    <#if 0 != paginationPageCount>
        <div class="fn-clear">
            <nav class="pagination fn-right">
                <#if 1 != paginationPageNums?first>
                <a href="${servePath}${path}?p=${paginationPreviousPageNum}" class="page-number">&laquo;</a>
                    <a class="page-number" href="${servePath}${path}">1</a> <span class="page-number">...</span>
                </#if>
                <#list paginationPageNums as paginationPageNum>
                <#if paginationPageNum == paginationCurrentPageNum>
                <span class="page-number current">${paginationPageNum}</span>
                <#else>
                <a class="page-number" href="${servePath}${path}?p=${paginationPageNum}">${paginationPageNum}</a>
                </#if>
                </#list>
                <#if paginationPageNums?last != paginationPageCount> <span class="page-number">...</span>
                <a href="${servePath}${path}?p=${paginationPageCount}" class="page-number">${paginationPageCount}</a>
                <a href="${servePath}${path}?p=${paginationNextPageNum}" class="page-number">&raquo;</a>
                </#if>
            </nav>
        </div>
    </#if>
</div>
