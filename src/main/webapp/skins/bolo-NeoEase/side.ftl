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
<div class="side">
    <div>
        <br>
        <#include "../../common-template/macro-user_site.ftl"/>
        <@userSite dir="nw"/>
        <#if "" != noticeBoard>
        <br><br>
        <div style="text-align: center">${noticeBoard}</div>
        </#if>
    </div>
     <#if article?? && article.articleToC?? && article.articleToC?size &gt; 0>
     <div>
         <h4>${tocLabel}</h4>
           <#include "../../common-template/toc.ftl"/>
     </div>

     <#else>
         <#if interactive == "on">
         <#if 0 != recentComments?size>
    <div>
        <h4>${recentCommentsLabel}</h4>
        <ul class="recent-comments">
            <#list recentComments as comment>
                <li>
                    <div class='recent-comments-main'>
                        <div>
                        <span class="left">
                            <#if "http://" == comment.commentURL>
                                ${comment.commentName}
                            <#else>
                            <a target="_blank" href="${comment.commentURL}">${comment.commentName}</a>
                            </#if>
                        </span>
                            <span class="expand-ico"></span>
                            <span class="clear"></span>
                        </div>
                        <div class="recent-comments-content">
                            <a href="${servePath}${comment.commentSharpURL}">
                                ${comment.commentContent}
                            </a>
                        </div>
                    </div>
                    <div class='clear'></div>
                </li>
            </#list>
        </ul>
    </div>
         </#if>
         <#if 0 != mostCommentArticles?size>
    <div>
        <h4>${mostCommentArticlesLabel}</h4>
        <ul class="side-li">
            <#list mostCommentArticles as article>
                <li>
                    <sup>[${article.articleCommentCount}]</sup>
                    <a title="${article.articleTitle}" href="${servePath}${article.articlePermalink}">
                        ${article.articleTitle}
                    </a>
                </li>
            </#list>
        </ul>
    </div>
         </#if>
         </#if>
         <#if 0 != mostViewCountArticles?size>
    <div>
        <h4>${mostViewCountArticlesLabel}</h4>
        <ul class="side-li">
            <#list mostViewCountArticles as article>
                <li>
                    <sup>[${article.articleViewCount}]</sup>
                    <a title="${article.articleTitle}" href="${servePath}${article.articlePermalink}">
                        ${article.articleTitle}
                    </a>
                </li>
            </#list>
        </ul>
    </div>
         </#if>

         <#if 0 != mostUsedCategories?size>
        <div>
            <h4>${categoryLabel}</h4>
            <ul class="tags">
                <#list mostUsedCategories as category>
                    <li>
                        <sup>[${category.categoryTagCnt}]</sup>
                        <a class="tag" href="${servePath}/category/${category.categoryURI}">
                            ${category.categoryTitle}</a>
                    </li>
                </#list>
            </ul>
            <div class="clear"></div>
        </div>
         </#if>

         <#if 0 != mostUsedTags?size>
    <div>
        <h4>${tagsLabel}</h4>
        <ul id="tagsSide" class="tags">
            <#list mostUsedTags as tag>
                <li>
                    <a data-count="${tag.tagPublishedRefCount}"
                       href="${servePath}/tags/${tag.tagTitle?url('UTF-8')}"
                       title="${tag.tagTitle}(${tag.tagPublishedRefCount})">
                        <span>${tag.tagTitle}</span>
                    </a>
                </li>
            </#list>
        </ul>
        <div class="clear"></div>
    </div>
         </#if>
         <#if 0 != links?size>
    <div>
        <h4>${linkLabel}</h4>
        <ul>
            <#list links as link>
                <li>
                    <a rel="friend" href="${link.linkAddress}" title="${link.linkTitle}" target="_blank">
                        <img alt="${link.linkTitle}"
                             src="${faviconAPI}<#list link.linkAddress?split('/') as x><#if x_index=2>${x}<#break></#if></#list>"
                             width="16" height="16"/></a>
                    <a rel="friend" href="${link.linkAddress}" title="${link.linkTitle}"
                       target="_blank">${link.linkTitle}
                    </a>
                </li>
            </#list>
        </ul>
    </div>
         </#if>
         <#if 0 != archiveDates?size>
    <div>
        <h4>${archiveLabel}</h4>
        <ul id="archiveSide">
            <#list archiveDates as archiveDate>
                <li data-year="${archiveDate.archiveDateYear}">
                <#if "en" == localeString?substring(0, 2)>
                    <a href="${servePath}/archives/${archiveDate.archiveDateYear}/${archiveDate.archiveDateMonth}"
                       title="${archiveDate.monthName} ${archiveDate.archiveDateYear}(${archiveDate.archiveDatePublishedArticleCount})">
                        ${archiveDate.monthName} ${archiveDate.archiveDateYear}</a>(${archiveDate.archiveDatePublishedArticleCount}
                    )
                <#else>
                <a href="${servePath}/archives/${archiveDate.archiveDateYear}/${archiveDate.archiveDateMonth}"
                   title="${archiveDate.archiveDateYear} ${yearLabel} ${archiveDate.archiveDateMonth} ${monthLabel}(${archiveDate.archiveDatePublishedArticleCount})">
                    ${archiveDate.archiveDateYear} ${yearLabel} ${archiveDate.archiveDateMonth} ${monthLabel}</a>(${archiveDate.archiveDatePublishedArticleCount}
                    )
                </#if>
                </li>
            </#list>
        </ul>
    </div>
         </#if>
     </#if>
</div>
