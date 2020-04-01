<#--

    Bolo - A stable and beautiful blogging system based in Solo.
    Copyright (c) 2020, https://github.com/adlered

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
<li id="li-comment-${comment.oId}" itemtype="http://schema.org/Comment" itemprop="comment"
    class="comment index-1">
    <div id="comment-${comment.oId}" class="comment-body">
        <div class="comment-avatar">
            <a href="" rel="nofollow" target="_blank">
                <img alt="${comment.commentName}" src="${comment.commentThumbnailURL}" class="avatar">
            </a>
        </div>
        <div class="contain-main">
            <div class="comment-meta">
                <div itemprop="author" class="comment-author">
                    <#if "http://" == comment.commentURL>
                        <a href="javascript:void(0);" rel="nofollow" target="_blank"
                           class="author-name">${comment.commentName}</a>
                    <#else>
                        <a href="${comment.commentURL}" rel="nofollow" target="_blank"
                           class="author-name">${comment.commentName}</a>
                    </#if>

                    <#if comment.isReply>
                        <span class="comment-content" style="margin:0;padding:0;">@</span>
                        <a href="${servePath}${article.permalink}#li-comment-${comment.commentOriginalCommentId}"
                           onmouseover="page.showComment(this, '${comment.commentOriginalCommentId}', 20);"
                           onmouseout="page.hideComment('${comment.commentOriginalCommentId}')"
                        >${comment.commentOriginalCommentName}</a>
                    </#if>

                    <#if article.commentable>
                        <span class="comment-reply" style="cursor: pointer;">
                            <a href="javascript:page.toggleEditor('${comment.oId}', '${comment.commentName}')">${replyLabel}</a>
                        </span>
                    </#if>

                    <div class="useragent-info"><a href="${comment.commentURL}"
                                                   target="_blank">${comment.commentURL!}</a></div>
                </div>
                <time itemprop="datePublished" datetime="${comment.commentDate2?string("yyyy-MM-dd")}"
                      class="comment-time">
                    ${comment.commentDate2?string("yyyy-MM-dd HH:mm")}
                </time>
                <a href="#li-comment-${comment.oId}" class="comment-id">#${comment.oId}</a></div>
            <style>
                .comment-content .emoji {
                    width: 25px !important;
                    height: 25px !important;
                }
            </style>
            <div itemprop="description" class="comment-content">
                ${comment.commentContent}
            </div>
        </div>
    </div>
</li>