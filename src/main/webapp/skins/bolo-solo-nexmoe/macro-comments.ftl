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
<#macro comments commentList article>
    <#if article.commentable || 0 != commentList?size>
        <div class="nexmoe-comment">
            <#if article.commentable>
                <input id="boloUser" placeholder="你的昵称" style="width: 50%; height: 50px; float: left; display: inline; border: 1px solid #eee; padding: 20px">
                <input id="boloSite" placeholder="你的个人主页URL（选填）" style="width: 50%; height: 50px; float: right; display: inline; border: 1px solid #eee; padding: 20px">
                <textarea rows="3" placeholder="评论内容只能为 2 到 500 个字符！" id="comment" readonly="readonly"></textarea>
                <script type="text/javascript" src="${staticServePath}/js/bolo/sweetalert.min.js"></script>
            </#if>
            <ul class="comments" id="comments">
                <#list commentList as comment>
                    <#include "common-comment.ftl"/>
                </#list>
            </ul>
        </div>
    </#if>
</#macro>

