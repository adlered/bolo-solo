<div class="top-feature-row">
<h1 class="fes-title" style="font-family: 'Ubuntu', sans-serif;"><i class="fa fa-anchor" aria-hidden="true"></i> START:DASH!!</h1>
<#list articles as article>
<#if article.articlePutTop>
    <div class="top-feature-v2">
        <div class="the-feature square from_left_and_right">
            <a href="${servePath}${article.articlePermalink}" target="_blank">
                <div class="img">
                    <img src="${article.articleImg1URL}">
                </div>
                <div class="info">
                    <p>${article.articleTitle}</p>
                </div>
            </a>
        </div>
    </div>
</#if>
</#list>
</div>
