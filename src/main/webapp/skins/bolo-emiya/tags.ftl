<#include "macro-page.ftl">

<@sidePage htmlTitle="${allTagsLabel} - ${blogTitle}" pageTitle="${sumLabel} ${tags?size} ${tagLabel}">
   <div class="rowSmallItemLayout">
    <#list tags as tag>
    <a rel="tag" data-count="${tag.tagPublishedRefCount}" class="item"
      href="${servePath}/tags/${tag.tagTitle?url('UTF-8')}">
        <span class="name">${tag.tagTitle}</span>
        (<b>${tag.tagPublishedRefCount}</b>)
    </a>
    </#list>
  </div>
</@sidePage>
