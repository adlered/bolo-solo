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
    <article class="article__item">
        <h2 class="article__title">
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
        <div class="ft__gray fn__clear">
            <time>
                ${article.articleCreateDate?string("yyyy-MM-dd")}
            </time>
            &nbsp;
            <span class="mobile__none">
            <#if article.articleCategory != "">
                <span>
                    <a class="ft__green" rel="tag" href="${servePath}/category/${article.categoryURI}">所属分类 > ${article.articleCategory}</a>
                </span>
            </#if>
            <#list article.articleTags?split(",") as articleTag>
                <a rel="tag" class="ft__red" href="${servePath}/tags/${articleTag?url('UTF-8')}">
                    ${articleTag}</a><#if articleTag_has_next>, </#if>
            </#list>
            </span>
            <div class="fn__right">
                <#if interactive == "on">
                <a class="ft__red" href="${servePath}${article.articlePermalink}#comments"><#if article.articleCommentCount gt 0>${article.articleCommentCount} </#if>${commentLabel}</a>
                •
                </#if>
                <a class="ft__red" href="${servePath}${article.articlePermalink}">${article.articleViewCount} ${viewLabel}</a>
            </div>
        </div>
        <div class="vditor-reset article__content">
            ${article.articleAbstract}
        </div>
        <a class="article__more" href="${servePath}${article.articlePermalink}">More...</a>
    </article>
</#list>


<#if 0 != paginationPageCount>
    <nav class="fn__flex pagination">
        <#if 1 != paginationPageNums?first>
            <a href="${servePath}${path}?p=${paginationPreviousPageNum}" class="pagination__item fn__flex-center">&laquo; Prev</a>
        </#if>

        <div class="fn__flex-1 ft__center">
        <#if 1 != paginationPageNums?first>
            <a class="pagination__item" href="${servePath}${path}">1</a>
            <span class="pagination__item">...</span>
        </#if>
        <#list paginationPageNums as paginationPageNum>
            <#if paginationPageNum == paginationCurrentPageNum>
            <span class="pagination__item pagination__item--current">${paginationPageNum}</span>
            <#else>
            <a class="pagination__item"
               href="${servePath}${path}?p=${paginationPageNum}">${paginationPageNum}</a>
            </#if>
        </#list>
        <#if paginationPageNums?last != paginationPageCount>
            <span class="pagination__item">...</span>
            <a href="${servePath}${path}?p=${paginationPageCount}"
               class="pagination__item">${paginationPageCount}</a>
        </#if>
        </div>

        <#if paginationPageNums?last != paginationPageCount>
            <a href="${servePath}${path}?p=${paginationNextPageNum}" class="pagination__item fn__flex-center">Next &raquo;</a>
        </#if>
    </nav>
</#if>