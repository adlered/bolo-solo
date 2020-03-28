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