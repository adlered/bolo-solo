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
    <div class="fn-clear">
        <div class="fn-left">
            Theme ${skinDirName}
            by
            <a rel="friend" href="http://vanessa.b3log.org" target="_blank">Vanessa</a>
        </div>
        <span class="fn-right">
            ${viewCount1Label}${statistic.statisticBlogViewCount}
            &nbsp;
            ${articleCount1Label}${statistic.statisticPublishedBlogArticleCount}
            <#if interactive == "on">
            &nbsp;
            ${commentCount1Label}${statistic.statisticPublishedBlogCommentCount}
            </#if>
            &nbsp;
            ${onlineVisitor1Label}${onlineVisitorCnt}
        </span>
    </div>
    <div class="fn-clear">
        &copy; ${year}
        <a href="${servePath}">${blogTitle}</a>
        ${footerContent}
        <span class="fn-right">
            Powered by <a href="https://github.com/adlered/bolo-solo" target="_blank">Bolo</a>
        </span>
    </div>
    <span onclick="Util.goTop()" class="icon-goup"></span>
</footer>


<script type="text/javascript" src="${staticServePath}/js/lib/jquery/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${staticServePath}/js/common${miniPostfix}.js?${staticResourceVersion}" charset="utf-8"></script>
<script type="text/javascript" src="${staticServePath}/skins/${skinDirName}/js/${skinDirName}${miniPostfix}.js?${staticResourceVersion}" charset="utf-8"></script>
<#include "../../common-template/label.ftl">
${plugins}
