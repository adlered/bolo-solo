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