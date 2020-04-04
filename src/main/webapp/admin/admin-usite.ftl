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
<div>
    <div id="linkTable"></div>
    <div id="linkPagination" class="fn__margin12 fn__right"></div>
</div>
<div class="fn__clear"></div>
<div class="form form__no-table">
    联系方式
    <label>社交媒体</label>
    <input id="linkTitle" type="text"/>
    <label>个人主页链接</label>
    <input id="linkAddress" type="text"/><br><br>
    <button onclick="admin.linkList.add();" class="fn__right">${saveLabel}</button>
    <div class="fn__clear"></div>
</div>
<div id="updateLink" class="fn__none form form__no-table" data-title="${updateLinkLabel}">
    <label>社交媒体</label>
    <input id="linkTitleUpdate" type="text"/>
    <label>个人主页链接</label>
    <input id="linkAddressUpdate" type="text"/><br><br>
    <button onclick="admin.linkList.update();" class="fn__right">${updateLabel}</button>
    <div class="fn__clear"></div>
</div>
${plugins}
