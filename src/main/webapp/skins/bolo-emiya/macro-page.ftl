<#--

    Solo - A small and beautiful blogging system written in Java.
    Copyright (c) 2010-present, b3log.org

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

-->
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
