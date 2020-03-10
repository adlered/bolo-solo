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
<div class="comments__item">
    <div class="comments__meta comments__meta--only">${commentLabel}</div>
</div>

<ul class="comments" id="comments">
    <#list commentList as comment>
        <#include 'common-comment.ftl'/>
    </#list>
</ul>

<#if article.commentable>
<div class="comments__item">
    <div class="comments__meta">
        ${postCommentsLabel}
    </div>
    <div class="comments__content">
        <div class="form">
            <input id="boloUser" placeholder="你的昵称" style="padding: 10px; width: fill-available; width: -webkit-fill-available;">
            <input id="boloSite" placeholder="你的个人主页URL（选填）" style="margin-top: 3px; margin-bottom: 10px; padding: 10px; width: fill-available; width: -webkit-fill-available;">
            <textarea rows="3" placeholder="${postCommentsLabel}" id="comment"></textarea>
            <script type="text/javascript" src="${staticServePath}/js/bolo/sweetalert.min.js"></script>
        </div>
    </div>
</div>
</#if>
</#macro>