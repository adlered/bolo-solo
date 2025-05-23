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
<!-- 博客底部 -->
<footer class="footer">
    ${statistic.statisticPublishedBlogArticleCount}
    ${articleLabel}
    <#if interactive == "on"> &nbsp; &nbsp;
    ${statistic.statisticPublishedBlogCommentCount}
    ${commentLabel} </#if>
    <br>
    ${statistic.statisticBlogViewCount}
    ${viewLabel} &nbsp; &nbsp;
    ${onlineVisitorCnt}
    ${onlineVisitorLabel}
    <br><br>
    &copy; ${.now?string('yyyy')} ${blogTitle!}
    Powered by <a href="https://github.com/AdlerED/bolo-solo" target="_blank">Bolo</a>
    <br>
    Theme 极简 · xups by <a href="https://github.com/adlered" target="_blank">adlered</a>
    <br/>

    ${footerContent}
</footer>
<script type="text/javascript" src="${staticServePath}/js/lib/jquery/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${staticServePath}/js/lib/compress/pjax.min.js" charset="utf-8"></script>
<script src="${staticServePath}/js/common${miniPostfix}.js?${staticResourceVersion}"></script>
<script type="text/javascript" src="${staticServePath}/skins/${skinDirName}/js/common.js?${staticResourceVersion}"
        charset="utf-8"></script>

<div class="back-to-top" id="JELON__backToTop" title="返回顶部">返回顶部</div>
</body>

<#include "../../common-template/label.ftl">

${plugins}
