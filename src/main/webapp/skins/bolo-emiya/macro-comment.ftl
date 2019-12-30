<#macro dynamic_comment comment>
  <div class="comment" id="${comment.oId}">
    <img class="comment__avatar" src="${comment.commentThumbnailURL}" />
    <div class="comment__info">
      <#if "http://" == comment.commentURL>
      <span class="username">${comment.commentName}</span>
      <#else>
      <a class="username" href="${comment.commentURL}" target="_blank">${comment.commentName}</a>
      </#if>
      <a class="btn" href="${servePath}${comment.commentSharpURL}">${viewLabel}»</a>
    </div>
    <main class="comment__detail">
      <div class="vditor-reset">
        ${comment.commentContent}
      </div>
      <time class="time">${comment.commentDate?string("yyyy-MM-dd HH:mm")}</time>
    </main>
  </div>
</#macro>

<#macro article_comment comment article>
  <div class="comment" id="${comment.oId}">
    <img class="comment__avatar" src="${comment.commentThumbnailURL}" />
    <div class="comment__info">
      <#if "http://" == comment.commentURL || "https://" == comment.commentURL>
        <span class="username">${comment.commentName}</span>
        <#else>
        <a class="username" href="${comment.commentURL}" target="_blank">${comment.commentName}</a>
      </#if>
      <#if comment.isReply>
      @<a 
        class="replyName J_replyName"
        href="javascript:;"
        data-originalId="${comment.commentOriginalCommentId}" 
      >${comment.commentOriginalCommentName}</a>
      </#if>
      <#if article.commentable>
        <a class="btn" href="javascript:page.toggleEditor('${comment.oId}', '${comment.commentName}')">${replyLabel}</a>
      </#if>
    </div>
    <main class="comment__detail">
      <div class="vditor-reset">
        ${comment.commentContent}
      </div>
      <time class="time">${comment.commentDate2?string("yyyy-MM-dd HH:mm")}</time>
    </main>
  </div>
</#macro>

<#macro article_comments commentList article>
<#if commentList?size != 0>
  <h3>评论列表</h3>
  <ul class="article__comments" id="comments">
    <#list commentList as comment>
      <@article_comment comment=comment article=article></@article_comment>
    </#list>
  </ul>
</#if>
<#if article.commentable>
  <h3>添加新评论</h3>
  <textarea 
      rows="3" placeholder="${postCommentsLabel}"
      class="comment__textarea" id="comment"></textarea>
</#if>
</#macro>