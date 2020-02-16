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
<#include "macro-comment.ftl">
<#include "../../common-template/macro-comment_script.ftl">

<!DOCTYPE html>
<html>
  <@emiya_head title="${article.articleTitle} - ${blogTitle}"></@emiya_head>
  <body id="emiya_blog">
    <@article_header></@article_header>
    <div class="blog">
      <div class="blog__container">
        <div class="container-fluid">
          <div class="container--left">
            <div class="article__content J_article__content vditor-reset">
              ${article.articleContent}
              <#if "" != article.articleSign.signHTML?trim>
              <div>
                ${article.articleSign.signHTML}
              </div>
              </#if>
            </div>
            <script>
              var loggedIn = ${article.logged};
            </script>
            <div class="share__container">
              <a class="item J_share" data-type="weibo" href="javascript:;"></a>
              <a class="item J_share" data-type="qzone" href="javascript:;"></a>
              <a 
                class="item J_share J_share_wechat"
                href="javascript:;"
                data-type="wechat"
                data-title="${article.articleTitle}"
                data-blogtitle="${blogTitle}"
                data-url="${servePath}${article.articlePermalink}"
                data-avatar="${article.authorThumbnailURL}"></a>
            </div>
            <div class="comment__container">
              <@article_comments commentList=articleComments article=article></@article_comments>
            </div>
            <div class="recommendation__container">
              <div class="item" id="externalRelevantArticles">
              </div>
              <div class="item" id="randomArticles">
              </div>
              <div class="item" id="relevantArticles">
              </div>
            </div>
          </div>
          <div class="aside container--right">
            <#include "side.ftl">
          </div>
        </div>
      </div>
    </div>
    <#include "footer.ftl">
    <@comment_script oId=article.oId commentable=article.commentable>
      Skin.initComment = function (articleOId, articleTags) {
        page.tips.externalRelevantArticlesDisplayCount = "${externalRelevantArticlesDisplayCount}";
        page.loadExternalRelevantArticles(articleTags, "<div class='header'><span>HACPAI POSTS</span></div>");
        page.loadRandomArticles("<div class='header'><span>RECOMMEND POSTS</span></div>");
        page.loadRelevantArticles(articleOId, '<div class="header"><span>RELEVANT POSTS</span></div>');
      }
      Skin.initComment('${article.oId}', "<#list article.articleTags?split(",") as articleTag>${articleTag}<#if articleTag_has_next>,</#if></#list>")
      Skin.initArticle()
    </@comment_script>
  </body>
</html>