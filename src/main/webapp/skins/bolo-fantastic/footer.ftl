<footer class="footer">
    <div class="container">
        <div class="level">
            <div class="level-start has-text-centered-mobile">
                <p class="is-size-6">
                    &copy; ${.now?string('yyyy')} ${blogTitle!}&nbsp;
                    Powered by <a href="https://github.com/AdlerED/bolo-solo" target="_blank">菠萝博客 Bolo</a>
                    <br>
                    &nbsp;&nbsp;&nbsp;Theme <a href="https://github.com/csfwff">Fantastic</a> by
                    <a href="https://github.com/csfwff" target="_blank">唐墨夏</a>
                    <br />

                </p>
            </div>
            <div class="level-end">
                <div class="field has-addons is-flex-center-mobile has-mt-5-mobile is-flex-wrap is-flex-middle">
                    <a href="${servePath}/tags.html"> ${allTagsLabel}</a>&nbsp;•&nbsp;
                    <a href="${servePath}/archives.html"> ${archiveLabel}</a>&nbsp;•&nbsp;
                    <a href="${servePath}/links.html">
                        ${linkLabel}
                    </a><br />
                   
                </div>
                 <div class="field has-addons is-flex-center-mobile has-mt-5-mobile is-flex-wrap is-flex-middle">
                 ${footerContent} <br>
                 </div>
            </div>
        </div>
    </div>
</footer>
<script type="text/javascript" src="${staticServePath}/js/lib/jquery/jquery.min.js" charset="utf-8"></script>
<script src="${staticServePath}/js/lib/compress/pjax.min.js"></script>
<script src="${staticServePath}/js/common${miniPostfix}.js?${staticResourceVersion}"></script>
<script src="${staticServePath}/skins/${skinDirName}/js/common${miniPostfix}.js?${staticResourceVersion}"></script>
<#include "../../common-template/label.ftl">

${plugins}