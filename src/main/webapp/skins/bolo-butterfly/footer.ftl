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
<footer class="footer">
    <div class="wrapper">
        <#include "../../common-template/macro-user_site.ftl">
        <@userSite dir=""></@userSite> <br>
        &copy; ${year}
        <a href="${servePath}">${blogTitle}</a>
        ${footerContent}
        <span class="footer__heart">❤️</span>
        Powered by <a href="https://github.com/adlered/bolo-solo" target="_blank">Bolo</a>
        Theme ✨ Bolo-Butterfly
        <sup>[<a href="https://github.com/adlered/bolo-solo" target="_blank">ref</a>]</sup>
        Modified by <a href="https://github.com/lonuslan" target="_blank">Lonus Lan</a> <br>
    </div>
</footer>
<#--<svg class="side__top" id="sideTop" version="1.1" xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32">-->
<#--    <path d="M26.562 13.958c0 0.72 0.583 1.303 1.303 1.303s1.303-0.583 1.303-1.303v0c0-0.72-0.583-1.303-1.303-1.303s-1.303 0.583-1.303 1.303v0zM23.305 10.7c0 0 0 0 0 0 0 0.72 0.583 1.303 1.303 1.303s1.303-0.583 1.303-1.303c0-0 0-0 0-0v0c0-0 0-0 0-0 0-0.72-0.583-1.303-1.303-1.303s-1.303 0.583-1.303 1.303c0 0 0 0 0 0v0zM20.022 7.417c0 0 0 0 0 0 0 0.72 0.583 1.303 1.303 1.303s1.303-0.583 1.303-1.303c0-0 0-0 0-0v0c0-0 0-0 0-0 0-0.72-0.583-1.303-1.303-1.303s-1.303 0.583-1.303 1.303c0 0 0 0 0 0v0zM29.67 0h-27.339c-0.677 0-1.228 0.551-1.228 1.228s0.551 1.228 1.228 1.228h11.151l-10.725 10.725c-0.476 0.476-0.476 1.253 0 1.729 0.226 0.226 0.551 0.351 0.852 0.351s0.626-0.125 0.852-0.351l10.074-10.074v25.936c0 0.677 0.551 1.228 1.228 1.228s1.228-0.551 1.228-1.228v-26.011c0.476 0.426 1.203 0.426 1.679-0.050s0.476-1.253 0-1.729l-0.576-0.576h11.577c0.677 0 1.228-0.551 1.228-1.228 0-0.626-0.551-1.178-1.228-1.178z"></path>-->
<#--</svg>-->
<#include "../../common-template/label.ftl">
<#include "rightside.ftl">
<#include "mobile-menu.ftl">
<script type="text/javascript"
        src="${staticServePath}/skins/${skinDirName}/js/common.min.js?${staticResourceVersion}"
        charset="utf-8"></script>
<script type="text/javascript"
        src="${staticServePath}/skins/${skinDirName}/js/circleMagic.min.js?${staticResourceVersion}"
        charset="utf-8"></script>
<script>
    $(function() {
        let darkmode = 0;
        let theme = Cookies.get("theme");
        if (theme === "light") {
            darkmode = 0;
        } else {
            darkmode = 1;
        }
        setInterval(function() {
            if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
                if (darkmode === 0) {
                    $("#darkmode").click();
                    darkmode = 1;
                    console.log("切换至黑暗主题");
                }
            } else {
                if (darkmode === 1) {
                    $("#darkmode").click();
                    darkmode = 0;
                    console.log("切换至明亮主题");
                }
            }
        }, 1000);
    });
</script>
${plugins}
