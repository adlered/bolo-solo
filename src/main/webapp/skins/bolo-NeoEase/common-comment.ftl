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
<div id="${comment.oId}">
    <div class="comment-panel">
        <div class="left">
        <#if "http://" == comment.commentURL>
        ${comment.commentName}
        <#else>
            <a href="${comment.commentURL}" target="_blank">${comment.commentName}</a>
        </#if>
        <#if comment.isReply>&nbsp;@
            <a href="${servePath}${article.permalink}#${comment.commentOriginalCommentId}"
               onmouseover="page.showComment(this, '${comment.commentOriginalCommentId}', 20);"
               onmouseout="page.hideComment('${comment.commentOriginalCommentId}')">${comment.commentOriginalCommentName}</a>
        </#if>
        </div>
    <#if article.commentable>
        <div class="right">
            <a rel="nofollow" href="javascript:page.toggleEditor('${comment.oId}', '${comment.commentName}');">${replyLabel}</a>
            &nbsp;|&nbsp;
        ${comment.commentDate2?string("yyyy-MM-dd HH:mm:ss")}
        </div>
    </#if>
        <span class="clear"></span>
        <div class="vditor-reset">${comment.commentContent}</div>
    </div>
    <span class="clear"></span>
</div>