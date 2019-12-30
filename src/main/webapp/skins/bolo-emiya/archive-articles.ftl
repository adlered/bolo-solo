<#include "macro-page.ftl">

<@articlesPage htmlTitle="${archiveDate.archiveDateYear}/${archiveDate.archiveDateMonth} (${archiveDate.archiveDatePublishedArticleCount}) - ${blogTitle}">
  <h1 class="blog__title">
    <#if "en" == localeString?substring(0, 2)>
    ${archiveDate.archiveDateMonth} ${archiveDate.archiveDateYear}
    <#else>
    ${archiveDate.archiveDateYear} ${yearLabel} ${archiveDate.archiveDateMonth} ${monthLabel}
    </#if>
    - ${sumLabel} ${archiveDate.archiveDatePublishedArticleCount} ${articleLabel}
  </h1>
</@articlesPage>