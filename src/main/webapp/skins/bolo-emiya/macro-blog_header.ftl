<#--

    Bolo - A stable and beautiful blogging system based in Solo.
    Copyright (c) 2020-present, https://github.com/bolo-blog

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
<#macro nav_items>
  <#list pageNavigations as page>
  <li><a href="${page.pagePermalink}" target="${page.pageOpenTarget}">${page.pageTitle}</a></li>
  </#list>
  <li><a href="${servePath}/links.html">${linkLabel}</a></li>
  <#if interactive == "on">
  <#if isLoggedIn>
  <li><a href="${servePath}/admin-index.do#main">${adminLabel}</a></li>
  <li><a href="${logoutURL}">${logoutLabel}</a></li>
  <#else>
  <li><a href="${servePath}/start">${startToUseLabel}</a></li>
  </#if>
  </#if>
  <#nested>
</#macro>

<#macro blog_header>
  <nav class="navbar J_navbar">
    <div class="navbar__container navbar__container--mobile">
      <a class="brand" href="/">${blogTitle}</a>
      <button type="button" class="toggle J_navbar_toggle" data-for="toggle-items">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <div class="toggle-items">
        <ul><@nav_items></@nav_items></ul>
      </div>
    </div>

    <div class="navbar__container navbar__container--web">
      <a class="brand" href="/">${blogTitle}</a>
      <div class="items">
        <ul><@nav_items></@nav_items></ul>
      </div>
    </div>
  </nav>

  <#nested>
</#macro>

<#macro common_header>
  <@blog_header>
    <header class="custom_header" style="background-image: url('${staticServePath}/skins/${skinDirName}/images/header.jpg')">
      <div class="custom_header__container">
        <p class="subtitle">${blogSubtitle}</p>
      </div>
    </header>
  </@blog_header>
</#macro>

<#macro article_header>
  <@blog_header>
    <header class="custom_header" style="background-image: url('${staticServePath}/skins/${skinDirName}/images/header.jpg')">
      <div class="custom_header__container">
        <div class="custom_header__articleMeta">
          <h1 class="articleMeta__title">${article.articleTitle}</h1>
          <div class="articleMeta__info">
            <span class="author">@${article.authorName} &nbsp;${article.articleCreateDate?string("yyyy-MM-dd")}/${article.articleUpdateDate?string("yyyy-MM-dd")}</span>
            <#if interactive == "on">
            <span class="comments">
              <a href="${servePath}${article.articlePermalink}#comments">${article.articleCommentCount} ${commentLabel}</a>
            </span>
            </#if>
            <span class="views">
              ${article.articleViewCount} ${viewLabel}
            </span>
          </div>
          <div class="articleMeta__tags">
            <#list article.articleTags?split(",") as articleTag>
            <a class="tagBtn" rel="tag" href="${servePath}/tags/${articleTag?url('UTF-8')}">${articleTag}</a>&nbsp;
            </#list>
          </div>
        </div>
      </div>
    </header>
  </@blog_header>
</#macro>