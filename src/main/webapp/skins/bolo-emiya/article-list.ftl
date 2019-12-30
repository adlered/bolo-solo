<div>
  <#list articles as article>
  <article class="article">
    <div class="article__info">
      <div class="article__info--left">
        <a class="author" href="${servePath}/authors/${article.authorId}" rel="nofollow">
          <img class="author__avatar" src="${article.authorThumbnailURL}" alt="author avatar" />
          <span class="author__name">${article.authorName}</span>
        </a>
        <span class="split">in</span>
        <span class="tags">
          <#list article.articleTags?split(",") as articleTag>
          <a class="tags__name" rel="tag" href="${servePath}/tags/${articleTag?url('UTF-8')}">${articleTag}</a>
          </#list>
        </span>
      </div>
      <div class="article__info--right">
        <span class="createTime f-web">${article.articleCreateDate?string("yyyy-MM-dd")}</span>
      </div>
    </div>
    <div class="article__intro">
      <div class="title">
        <a href="${servePath}${article.articlePermalink}">${article.articleTitle}</a>
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
      </div>
      <div class="abstract vditor-reset">${article.articleAbstract}</div>
    </div>
    <div class="article__visit">
      <div class="article__visit--left">
        <span class="item--split createTime">${article.articleCreateDate?string("yyyy-MM-dd")}</span>
        <a class="item item--split" href="${servePath}${article.articlePermalink}#comments">${article.articleCommentCount} ${commentLabel}</a>
        <span class="item">${article.articleViewCount} ${viewLabel}</span>
      </div>
      <a class="article__visit--right" href="${servePath}${article.articlePermalink}">阅读全文</a>
    </div>
  </article>
  </#list>


  <#if 0 != paginationPageCount>
  <div class="pagination">
    <#if 1 < paginationCurrentPageNum>
    <a class="pagination__item pagination__item--left" href="${servePath}${path}?p=${paginationCurrentPageNum-1}">&laquo; Prev</a>
    </#if>
    <#if paginationCurrentPageNum < paginationPageCount>
    <a class="pagination__item pagination__item--right" href="${servePath}${path}?p=${paginationCurrentPageNum+1}">Next &raquo;</a>
    </#if>
  </div>
  </#if>
</div>