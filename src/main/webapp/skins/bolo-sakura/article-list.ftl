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
<main id="main" class="site-main" role="main">
<h1 class="main-title" style="font-family: 'Ubuntu', sans-serif;"><i class="fa fa-envira" aria-hidden="true"></i> 文章</h1>
<#list articles as article>
<article class="post post-list-thumb" itemscope="" itemtype="http://schema.org/BlogPosting">
    <div class="post-thumb">
        <a href="${servePath}${article.articlePermalink}">
            <img class="lazyload" referrerpolicy="origin" src="${article.articleImg1URL}" onerror="imgError(this,3)" data-src="${article.articleImg1URL}">
        </a>
    </div>
    <div class="post-content-wrap">
        <div class="post-content">
            <div class="post-date">
                <#if article.articlePutTop>
                    置顶帖！
                </#if>
                <i class="iconfont icon-time"></i>${article.articleUpdateDate?string("yyyy年MM月dd日 HH:mm:ss")}
            </div>
            <a href="${servePath}${article.articlePermalink}" class="post-title">
                <h3>${article.articleTitle}</h3>
            </a>
            <div class="post-meta">
                <span><i class="iconfont icon-attention"></i>${article.articleViewCount} 浏览</span>
                <#if interactive == "on">
                    <span class="comments-number"><i class="iconfont icon-mark"></i><a href="javascript:void(0);">${article.articleCommentCount} 条评论</a></span>
                </#if>
                <#if article.articleCategory != "">
                <span><i class="iconfont icon-file"></i><a href="${servePath}/category/${article.categoryURI}">${article.articleCategory}</a></span>
                </#if>
            </div>
            <div class="float-content">
                <p>
                    ${article.articleAbstractText}
                <div class="post-bottom">
                    <a href="${servePath}${article.articlePermalink}" class="button-normal"><i class="iconfont icon-caidan"></i></a>
                </div>
            </div>
        </div>
    </div>
</article>
</#list>
</main>
<div style="text-align: center;">
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


