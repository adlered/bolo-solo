<#include "macro-page.ftl">

<@sidePage htmlTitle="${allTagsLabel} - ${blogTitle}" pageTitle="${archiveLabel} - ${sumLabel} ${statistic.statisticPublishedBlogArticleCount} ${articleLabel}">
  <div class="rowItemLayout">
    <#list archiveDates as archiveDate>
    <#if archiveDate.archiveDatePublishedArticleCount != 0>
      <#if "en" == localeString?substring(0, 2)>
        <a class="item" href="${servePath}/archives/${archiveDate.archiveDateYear}/${archiveDate.archiveDateMonth}">
          ${archiveDate.monthName} ${archiveDate.archiveDateYear}(${archiveDate.archiveDatePublishedArticleCount})
        </a>
      <#else>
        <a class="item" href="${servePath}/archives/${archiveDate.archiveDateYear}/${archiveDate.archiveDateMonth}">
          ${archiveDate.archiveDateYear} ${yearLabel} ${archiveDate.archiveDateMonth} ${monthLabel}(${archiveDate.archiveDatePublishedArticleCount})
        </a> 
      </#if>
    </#if>
    </#list>
  </div>
</@sidePage>