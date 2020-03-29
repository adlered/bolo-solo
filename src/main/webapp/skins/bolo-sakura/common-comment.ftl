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
<li class="comment even thread-even depth-1" id="${comment.oId}">
    <div class="contents">
        <div class="comment-arrow">
            <div class="main shadow">
                <div class="commentinfo">
                    <section class="commeta">
                        <div class="left">
                            <#if "http://" == comment.commentURL>
                                <a href="javascript:void(0);" rel="nofollow">${comment.commentName}</a>
                            <#else>
                                <a href="${comment.commentURL}" rel="nofollow">${comment.commentName}</a>
                            </#if>
                            <#if comment.isReply>
                                @
                                <a href="${servePath}${article.permalink}#${comment.commentOriginalCommentId}"
                                   onmouseover="page.showComment(this, '${comment.commentOriginalCommentId}', 23);"
                                   onmouseout="page.hideComment('${comment.commentOriginalCommentId}')"
                                >${comment.commentOriginalCommentName}</a>
                            </#if>
                        </div>
                        <#if article.commentable>
                            <a class="fn-right" href=""></a>
                            <a rel="nofollow" class="comment-reply-link" href="javascript:page.toggleEditor('${comment.oId}', '${comment.commentName}')">${replyLabel}</a>
                        </#if>
                        <div class="right">
                            <div class="info">
                                <time datetime="${comment.commentDate2?string("yyyy-MM-dd")}">${comment.commentDate2?string("yyyy-MM-dd HH:mm")}</time>
                            </div>
                        </div>
                    </section>
                </div>
                <div class="body">
                    ${comment.commentContent}
                </div>
            </div>
            <div class="arrow-left">
            </div>
        </div>
    </div>
    <hr>
</li>