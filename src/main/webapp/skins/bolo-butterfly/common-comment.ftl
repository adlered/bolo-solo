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
<div class="vcard" id="${comment.oId}">
    <div class="vh">
        <div class="vhead">
            <#if "http://" == comment.commentURL>
                <a href="javascript:void(0);" rel="nofollow">${comment.commentName}</a>
            <#else>
                <a class="vnick" rel="nofollow" href="${comment.commentURL}" target="_blank">${comment.commentName}</a>
            </#if>
            <#if comment.isReply>
                @
                <a href="${servePath}${article.permalink}#${comment.commentOriginalCommentId}"
                   onmouseover="page.showComment(this, '${comment.commentOriginalCommentId}', 23);"
                   onmouseout="page.hideComment('${comment.commentOriginalCommentId}')"
                >${comment.commentOriginalCommentName}</a>
            </#if>
        </div>
        <div class="vmeta">
            <span class="vtime">${comment.commentDate2?string("yyyy-MM-dd HH:mm")}</span>
        <#if article.commentable>
            <a rel="nofollow" class="vat" href="javascript:page.toggleEditor('${comment.oId}', '${comment.commentName}')">${replyLabel}</a>
        </#if>
        </div>
        <div class="vcontent">
            ${comment.commentContent}
        </div>
    </div>
</div>
