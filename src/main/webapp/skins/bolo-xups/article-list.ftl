<#-- Bolo - A stable and beautiful blogging system based in Solo. Copyright (c)
2020, https://github.com/adlered This program is free software: you can
redistribute it and/or modify it under the terms of the GNU Affero General
Public License as published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version. This program is distributed
in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU Affero General Public License for more details. You should have received a
copy of the GNU Affero General Public License along with this program. If not,
see <https://www.gnu.org/licenses/>. -->
<!-- 博客正文 -->
<section class="content">
    <div class="content-main widget">
        <!-- 首页 -->

        <h3 class="widget-hd">
            <strong> 最近动态 </strong>
        </h3>

        <!-- 文章列表 item -->
        <#list articles as article>
            <#if article.articlePutTop>
            <#else>
                <article class="post">
                    <header>
                        <!-- 标签这有且只能显示一个 -->
                        <#if article.articleCategory != "">
                            <a class="cat-link"
                               href="${servePath}/category/${article.categoryURI}">${article.articleCategory}</a>
                        <#else>
                            <a class="cat-link">无</a>
                        </#if>
                        <!-- 文章标题 -->
                        <h3 class="post-title">
                            <a href="${servePath}${article.articlePermalink}">
                                ${article.articleTitle!}
                            </a>
                        </h3>
                    </header>
                    <p class="post-meta">
                        ${article.authorName} 发表于
                        ${article.articleCreateDate?string("yyyy年MM月dd日 HH:mm:ss")}
                        &nbsp;&nbsp;
                        <span class="post-tags">
                        标签：
                        <#list article.articleTags?split(",") as articleTag>
                            <a href="${servePath}/tags/${articleTag?url('UTF-8')}">${articleTag}</a>&nbsp;
                        </#list>
                        </span>
                    </p>
                    <div class="post-content">
                        <div class="post-excerpt">
                            <#if article.articleAbstractText?length gt 230>
                                ${article.articleAbstractText[0..230]} ......
                            <#else>
                                ${article.articleAbstractText}
                            </#if>
                        </div>
                        <div class="post-thumbnail" data-img="">
                            <a
                                    href="${servePath}${article.articlePermalink}"
                                    title="${article.articleTitle!}"
                            >
                                <img class="thumbnail" src="${article.articleImg1URL}"/>
                            </a>
                        </div>
                    </div>
                </article>
            </#if>
        </#list>

        <#if 0 != paginationPageCount>
        <nav class="page-navigator">
            <#if 1 != paginationPageNums?first>
                <a href="${servePath}${path}?p=${paginationPreviousPageNum}"
                   aria-label="${previousPageLabel}"
                   class="pagination__item vditor-tooltipped__n vditor-tooltipped">&laquo;</a>
                <a class="pagination__item" href="${servePath}${path}">1</a>
                <span class="space">&hellip;</span>
            </#if>
            <#list paginationPageNums as paginationPageNum>
                <#if paginationPageNum == paginationCurrentPageNum>
                    <span class="page-number current">${paginationPageNum}</span>
                <#else>
                    <a class="page-number" href="${servePath}${path}?p=${paginationPageNum}">${paginationPageNum}</a>
                </#if>
            </#list>
            <#if paginationPageNums?last != paginationPageCount>
                <span class="space">&hellip;</span>
                <a href="${servePath}${path}?p=${paginationPageCount}" class="pagination__item">${paginationPageCount}</a>
                <a href="${servePath}${path}?p=${paginationNextPageNum}" aria-label="${nextPagePabel}"
                   class="pagination__item vditor-tooltipped__n vditor-tooltipped">&raquo;</a>
            </#if>
        </nav>
        </#if>
    </div>
</section>
