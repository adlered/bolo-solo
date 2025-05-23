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
<#macro comment_script oId commentable>
<#if commentable>
    <div style="position: fixed;bottom: -300px;width: 100%;opacity: 0;background-color: #f1f7fe;padding: 20px 0;transition: all .15s ease-in-out;z-index: 100;left: 0;"
         id="soloEditor">
        <div style="max-width: 768px;margin: 0 auto;padding: 0 10px;">
            <div id="soloEditorComment"></div>
            <div style="display: flex;margin-top: 10px;line-height: 30px">
                <div style="flex: 1;" id="soloEditorReplyTarget"></div>
                <div style="color: #d23f31" id="soloEditorError"></div>
                <span id="soloEditorCancel" style="margin: 0 10px;padding: 0 12px;cursor: pointer">
                    ${cancelLabel}
                </span>
                <button id="soloEditorAdd" style="border-radius: 4px;background-color: #60b044;border:1px solid #569e3d;color: #fff;padding: 0 12px">
                    ${confirmLabel}
                </button>
            </div>
        </div>
    </div>
</#if>
<script type="text/javascript">
    Util.addScript('${staticServePath}/js/page.js?${staticResourceVersion}', 'soloPageScript')
    var page = new Page({
        "commentContentCannotEmptyLabel": "${commentContentCannotEmptyLabel}",
        "oId": "${oId}",
        "blogHost": "${blogHost}",
        "randomArticles1Label": "${randomArticles1Label}",
    });
    $(document).ready(function () {
        page.load();
        <#nested>
    });
</script>
<script src="${staticServePath}/js/bolo/jquery.cookie.min.js"></script>
</#macro>