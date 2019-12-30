<#include "../../common-template/macro-common_head.ftl">
<#macro emiya_head title description="">
  <head>
    <@head title="${title}" description="${description}">
      <link type="text/css" rel="stylesheet" href="${staticServePath}/skins/${skinDirName}/css/base.css?${staticResourceVersion}" charset="utf-8" />
    </@head>
    <#nested>
  </head>
</#macro>