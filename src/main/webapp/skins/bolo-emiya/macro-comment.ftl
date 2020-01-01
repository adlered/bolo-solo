<#macro dynamic_comment comment>
  <div class="comment" id="${comment.oId}">
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
<#if article.commentable>
  <input id="boloUser" placeholder="你的昵称" style="width: 95%; border: 1px solid #eee; padding: 20px">
  <input id="boloSite" placeholder="你的个人主页URL（选填）" style="width: 95%; border: 1px solid #eee; padding: 20px">
  <textarea rows="3" placeholder="${postCommentsLabel}" class="comment__textarea" id="comment"></textarea>
  <script type="text/javascript" src="${staticServePath}/js/bolo/sweetalert.min.js"></script>
</#if>
<#if commentList?size != 0>
  <h3>评论列表</h3>
  <ul class="article__comments" id="comments">
    <#list commentList as comment>
      <@article_comment comment=comment article=article></@article_comment>
    </#list>
  </ul>
</#if>
</#macro>