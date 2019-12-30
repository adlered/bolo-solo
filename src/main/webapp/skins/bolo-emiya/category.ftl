<#include "macro-page.ftl">

<@sidePage htmlTitle="${categoryLabel} - ${blogTitle}" pageTitle="${categoryLabel}">
  <div class="rowItemLayout">
  <#list categories as category>
    <a class="item" rel="friend" href="${servePath}/category/${category.categoryURI}" title="${category.categoryTitle}">
      <p>
        ${category.categoryTitle} - 
        <b>${category.categoryTagCnt}</b> ${tagsLabel}
      </p>
      <p>${category.categoryDescription}</p>
    </a>
  </#list>
  </div>
</@sidePage>
