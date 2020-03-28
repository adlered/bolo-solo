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
    <#list commentList as comment>
        <#include 'common-comment.ftl'/>
    </#list>

    <#if article.commentable>
        <div id="respond" class="comment-respond">
            <form action="https://2heng.xin/wp-comments-post.php" method="post" id="commentform" class="comment-form" novalidate="">
                <p>
                    <i class="iconfont icon-markdown"></i> Markdown Supported while <i class="fa fa-code" aria-hidden="true"></i> Forbidden
                </p>
                <div class="popup cmt-popup" onclick="cmt_showPopup(this)">
                    <span class="popuptext" id="thePopup">怎么称呼你？</span>
                    <input type="text" placeholder="你的昵称" id="boloUser">
                </div>
                <div class="popup cmt-popup" onclick="cmt_showPopup(this)">
                    <span class="popuptext" id="thePopup">你有自己的网站吗？分享给大家吧！</span>
                    <input type="text" placeholder="你的个人主页URL（选填）" id="boloSite">
                </div>
                <div class="comment-textarea">
                    <textarea placeholder="${postCommentsLabel} ..." class="commentbody" id="comment" rows="5" tabindex="4"></textarea><label class="input-label active">${postCommentsLabel} ...</label>
                </div>
            </form>
        </div>
        <script type="text/javascript" src="${staticServePath}/js/bolo/sweetalert.min.js"></script>
    </#if>
</#macro>