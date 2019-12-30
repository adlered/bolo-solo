<#include "macro-page.ftl">

<@sidePage htmlTitle="${linkLabel} - ${blogTitle}" pageTitle="${linkLabel}">
  <div class="rowRowLayout">
  <#list links as link>
    <a class="item" rel="friend" href="${link.linkAddress}" title="${link.linkDescription}" target="_blank">
      <span class="item__title">${link.linkTitle}</span>
      <span class="item__desc">${link.linkDescription}</span>
    </a>
  </#list>
  </div>
</@sidePage>
