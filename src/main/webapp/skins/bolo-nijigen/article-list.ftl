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
<div class="article-list">
    <#list articles as article>
    <article class="item <#if article_index &lt; 3>item--active</#if>">
        <time class="vditor-tooltipped vditor-tooltipped__n item__date"
              aria-label="${article.articleCreateDate?string("yyyy")}${yearLabel}">
            ${article.articleCreateDate?string("MM")}${monthLabel}
            <span class="item__day">${article.articleCreateDate?string("dd")}</span>
        </time>

        <h2 class="item__title">
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
                    ${updatedLabel}
                </sup>
            </#if>
        </h2>

        <div class="item__date--m fn__none">
            <i class="icon__date"></i>
            ${article.articleCreateDate?string("yyyy-MM-dd")}
        </div>


        <div class="ft__center">
            <#if article.articleCategory != "">
            <span class="tag">
                <i class="icon__category"></i>
                <a rel="tag" href="${servePath}/category/${article.categoryURI}">${article.articleCategory}</a>
            </span>
            </#if>
            <span class="tag">
                <i class="icon__tags"></i>
                <#list article.articleTags?split(",") as articleTag>
                <a rel="tag" href="${servePath}/tags/${articleTag?url('UTF-8')}">
                ${articleTag}</a><#if articleTag_has_next>,</#if>
                </#list>
            </span>
            <#if interactive == "on">
            <a class="tag" href="${servePath}${article.articlePermalink}#comments">
                <i class="icon__comments"></i> ${article.articleCommentCount} ${commentLabel}
            </a>
            </#if>
            <span class="tag">
                <i class="icon__views"></i>
                ${article.articleViewCount} ${viewLabel}
            </span>
        </div>

        <div class="vditor-reset">
            ${article.articleAbstract}
        </div>
    </article>
    </#list>


    <#if 0 != paginationPageCount>
        <div class="fn__clear">
            <nav class="pagination fn__right">
                <#if 1 != paginationPageNums?first>
                    <a href="${servePath}${path}?p=${paginationPreviousPageNum}" class="pagination__item">&laquo;</a>
                    <a class="pagination__item" href="${servePath}${path}">1</a>
                    <span class="pagination__item pagination__item--text">...</span>
                </#if>
                <#list paginationPageNums as paginationPageNum>
                    <#if paginationPageNum == paginationCurrentPageNum>
                    <span class="pagination__item pagination__item--current">${paginationPageNum}</span>
                    <#else>
                    <a class="pagination__item" href="${servePath}${path}?p=${paginationPageNum}">${paginationPageNum}</a>
                    </#if>
                </#list>
                <#if paginationPageNums?last != paginationPageCount>
                    <span class="pagination__item pagination__item--text">...</span>
                    <a href="${servePath}${path}?p=${paginationPageCount}" class="pagination__item">${paginationPageCount}</a>
                    <a href="${servePath}${path}?p=${paginationNextPageNum}" class="pagination__item">&raquo;</a>
                </#if>
            </nav>
        </div>
    </#if>
</div>