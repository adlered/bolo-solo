<footer class="footer">
  <div class="footer__container">
    © ${year} <a href="${servePath}">${blogTitle}</a> ${footerContent}<br/>
    Powered by <a href="https://solo.b3log.org" target="_blank">Solo</a> 
    Theme <a rel="friend" href="https://github.com/zjhch123/solo-skin-emiya" target="_blank">${skinDirName}</a>
    <sup>[<a href="https://github.com/spiritree/typecho-theme-amaze" target="_blank">ref</a>]</sup> 
    by <a href="https://github.com/zjhch123" target="_blank">Jiahao.Zhang</a>
  </div>
</footer>
<script type="text/javascript" src="${staticServePath}/js/lib/jquery/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${staticServePath}/js/common${miniPostfix}.js?${staticResourceVersion}" charset="utf-8"></script>
<script type="text/javascript" src="${staticServePath}/skins/${skinDirName}/js/common${miniPostfix}.js?${staticResourceVersion}" charset="utf-8"></script>
<#include "../../common-template/label.ftl">
${plugins}