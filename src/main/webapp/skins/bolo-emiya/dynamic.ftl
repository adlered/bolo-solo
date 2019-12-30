<#include "macro-page.ftl">
<#include "macro-comment.ftl">

<@sidePage htmlTitle="${dynamicLabel} - ${blogTitle}" pageTitle="${dynamicLabel}">
  <div class="commentList">
    <#list recentComments as comment>
      <@dynamic_comment comment=comment></@dynamic_comment>
    </#list>
  </div>
</@sidePage>