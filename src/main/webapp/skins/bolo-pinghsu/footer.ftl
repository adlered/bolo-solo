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
    <#include "../../common-template/macro-user_site.ftl"/>
    <div class="ft__center">
        <@userSite dir="n"/>
    </div>
    <nav class="footer__nav mobile__none">
        <#list pageNavigations as page>
            <a class="ft__link" href="${page.pagePermalink}" target="${page.pageOpenTarget}" rel="section">
                ${page.pageTitle}
            </a>
        </#list>
        <a class="ft__link" rel="alternate" href="${servePath}/rss.xml" rel="section">RSS</a>
        <#if interactive == "on">
        <#if isLoggedIn>
        <a class="ft__link" href="${servePath}/admin-index.do#main" title="${adminLabel}">${adminLabel}</a>
        <a class="ft__link" href="${logoutURL}">${logoutLabel}</a>
        <#else>
        <a class="ft__link" href="${servePath}/start">${startToUseLabel}</a>
        </#if>
        </#if>
    </nav>
    <div class="footer__border mobile__none"></div>
    <div class="wrapper fn__flex">
        <div class="fn__flex-1 mobile__none">
            <div class="ft__fade">${adminUser.userName} - ${blogSubtitle}</div><br>
            <#if noticeBoard??>
                ${noticeBoard}
            </#if>
        </div>

        <#if 0 != mostUsedCategories?size>
            <div class="footer__mid fn__flex-1 mobile__none">
                <div class="ft__fade">${categoryLabel}</div> <br>
                <#list mostUsedCategories as category>
                    <a href="${servePath}/category/${category.categoryURI}"
                       aria-label="${category.categoryTagCnt} ${cntLabel}${tagsLabel}"
                       class="ft__link ft__nowrap vditor-tooltipped vditor-tooltipped__n">
                        ${category.categoryTitle}</a> &nbsp; &nbsp;
                </#list>
            </div>
        </#if>

        <div class="fn__flex-1 footer__copyright">
            <a class="ft__link" href="${servePath}/archives.html">
            ${statistic.statisticPublishedBlogArticleCount}
            ${articleLabel}
            </a><#if interactive == "on"> &nbsp; &nbsp;
            ${statistic.statisticPublishedBlogCommentCount}
            ${commentLabel} </#if><br>
            ${statistic.statisticBlogViewCount} <span class="ft-gray">${viewLabel}</span> &nbsp; &nbsp;
            ${onlineVisitorCnt} <span class="ft-gray">${onlineVisitorLabel}</span> <br>
            &copy; ${year}
            <a class="ft__link" href="${servePath}">${blogTitle}</a>
            ${footerContent}
            <br>
            Powered by <a class="ft__link" href="https://github.com/adlered/bolo-solo" target="_blank">Bolo</a>
            <br>
            Theme ${skinDirName}
            <sup>[<a class="ft__link" target="_blank" href="https://github.com/chakhsu/pinghsu">ref</a>]</sup>
            by <a class="ft__link" href="http://vanessa.b3log.org" target="_blank">Vanessa</a>
        </div>
    </div>
</footer>
<script type="text/javascript" src="${staticServePath}/js/lib/compress/pjax.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${staticServePath}/js/common${miniPostfix}.js?${staticResourceVersion}"
        charset="utf-8"></script>
<script type="text/javascript" src="${staticServePath}/skins/${skinDirName}/js/headroom${miniPostfix}.js"
        charset="utf-8"></script>
<script type="text/javascript"
        src="${staticServePath}/skins/${skinDirName}/js/common${miniPostfix}.js?${staticResourceVersion}"
        charset="utf-8"></script>
<#include "../../common-template/label.ftl">
${plugins}
