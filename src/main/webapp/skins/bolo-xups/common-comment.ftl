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
<#setting locale="en_US">
<li id="${comment.oId}" class="item">
    <div class="user-comment">
        <div class="user-comment-header">
            <a class="post-name" href="${comment.commentURL}" target="_blank">${comment.commentName}</a>
            <#if comment.isReply>
                @<a href="${servePath}${article.permalink}#${comment.commentOriginalCommentId}"><span class="post-name">${comment.commentOriginalCommentName}</span></a>
            </#if>
            <span class="post-time">${comment.commentDate2?string["MMM d, yyyy"]}</span>
            <#if article?? && article.commentable>
                <span class="reply" onclick="page.toggleEditor('${comment.oId}', '${comment.commentName}')">回复</span>
            </#if>
        </div>
        <div class="user-comment-body">${comment.commentContent}</div>
    </div>
</li>
