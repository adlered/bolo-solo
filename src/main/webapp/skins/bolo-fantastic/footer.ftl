<#--

    Bolo - A stable and beautiful blogging system based in Solo.
    Copyright (c) 2020, https://github.com/adlered

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
    <div class="container">
        <div class="level">
            <div class="level-start has-text-centered-mobile">
                <p class="is-size-6">
                    &copy; ${.now?string('yyyy')} ${blogTitle!}&nbsp;
                    Powered by <a href="https://github.com/AdlerED/bolo-solo" target="_blank">Bolo</a>
                    <br>
                    &nbsp;&nbsp;&nbsp;Theme <a href="https://github.com/csfwff/bolo-fantastic">Fantastic</a> by
                    <a href="https://github.com/csfwff" target="_blank">唐墨夏</a>
                    <br/>

                </p>
            </div>
            <div class="level-end">
                <div class="field has-addons is-flex-center-mobile has-mt-5-mobile is-flex-wrap is-flex-middle">
                    <#if interactive == "on"><a href="${servePath}/search"> 搜索</a>&nbsp;•&nbsp;</#if>
                    <a href="${servePath}/tags.html"> ${allTagsLabel}</a>&nbsp;•&nbsp;
                    <a href="${servePath}/archives.html"> ${archiveLabel}</a>&nbsp;•&nbsp;
                    <a href="${servePath}/links.html">
                        ${linkLabel}
                    </a>

                </div>
                <div class="field has-addons is-flex-center-mobile has-mt-5-mobile is-flex-wrap is-flex-middle">
                    <div>${footerContent} <br></div>
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