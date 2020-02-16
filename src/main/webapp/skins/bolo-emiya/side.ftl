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
<div class="sidebar">
  <section class="sidebar__container">
    <div class="header">
      <span>${searchLabel}</span>
    </div>
    <div class="search">
      <form class="search__form" action="${servePath}/search">
        <div class="input">
          <input type="text" name="keyword" placeholder="输入关键字搜索">
          <button type="submit"></button>
        <div>
      </form>
    </div>
  </section>

  <section class="sidebar__container">
    <div class="header">
      <span>${aboutLabel}</span>
    </div>
    <main class="user">
      <img class="user__avatar" src="${adminUser.userAvatar}" alt="${adminUser.userName}"/>
      <div class="user__info">
        <div class="item"><a href="${servePath}/archives.html">${statistic.statisticPublishedBlogArticleCount}<span class="text">${articleLabel}</span></a></div>
        <div class="item"><a href="${servePath}/dynamic.html">${statistic.statisticPublishedBlogCommentCount}<span class="text">${commentLabel}</span></a></div>
        <div class="item">${statistic.statisticBlogViewCount}<span class="text">${viewLabel}</span></div>
        <div class="item">${onlineVisitorCnt}<span class="text">${onlineVisitorLabel}</span></div>
      </div>
    </main>
  </section>

  <#if 0 != mostUsedTags?size>
  <section class="sidebar__container">
    <div class="header">
      <span>${tagsLabel}</span>
    </div>
    <div class="tags rowSmallItemLayout">
      <#list mostUsedTags as tag>
      <a class="item" href="${servePath}/tags/${tag.tagTitle?url('UTF-8')}">${tag.tagTitle}</a>
      </#list>
    </div>
  </section>
  </#if>

  <#if 0 != mostUsedCategories?size>
    <section class="sidebar__container">
      <div class="header">
        <span>${categoryLabel}</span>
      </div>
      <div class="tags rowSmallItemLayout">
        <#list mostUsedCategories as category>
          <a class="item" href="${servePath}/category/${category.categoryURI}">${category.categoryTitle}</a>
        </#list>
      </div>
    </section>
  </#if>

  <#if 0 != mostCommentArticles?size>
  <section class="sidebar__container">
    <div class="header">
      <span>${mostCommentLabel}</span>
    </div>
    <div class="lists">
      <#list mostCommentArticles as article>
      <a href="${servePath}${article.articlePermalink}">${article.articleTitle}</a>
      </#list>
    </div>
  </section>
  </#if>

  <#if 0 != mostViewCountArticles?size>
  <section class="sidebar__container">
    <div class="header">
      <span>${mostViewLabel}</span>
    </div>
    <div class="lists">
      <#list mostViewCountArticles as article>
      <a href="${servePath}${article.articlePermalink}">${article.articleTitle}</a>
      </#list>
    </div>
  </section>
  </#if>

  <#if article??>
  <section class="sidebar__container article__contents J_article__contents">
    <div class="header">
      <span>${articleContent}</span>
    </div>
    <div class="contents J_article__contents--container">
    </div>
  </section>
  </#if>
  <div class="toTop J_backToTop">
    <img src="${staticServePath}/skins/${skinDirName}/images/top.png" />
  </div>
</div>