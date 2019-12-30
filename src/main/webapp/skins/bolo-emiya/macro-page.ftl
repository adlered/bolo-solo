<#include "macro-head.ftl">
<#include "macro-blog_header.ftl">

<#macro sidePage htmlTitle pageTitle="">
<!DOCTYPE html>
<html>
  <@emiya_head title="${htmlTitle}"></@emiya_head>
  <body id="emiya_blog">
    <@common_header></@common_header>
    <div class="blog">
      <div class="blog__container">
        <div class="container-fluid">
          <div class="container--left">
            <#if pageTitle != "">
              <h1 class="blog__title">${pageTitle}</h1>
            </#if>
            <#nested>
          </div>
          <div class="aside container--right">
            <#include "side.ftl">
          </div>
        </div>
      </div>
    </div>
    <#include "footer.ftl">
  </body>
</html>
</#macro>

<#macro articlesPage htmlTitle pageTitle="">
  <@sidePage htmlTitle="${htmlTitle}" pageTitle="${pageTitle}">
    <#nested>
    <#include "article-list.ftl">
  </@sidePage>
</#macro>
