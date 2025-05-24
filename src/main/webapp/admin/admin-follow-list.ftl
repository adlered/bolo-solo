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
<div>
    <div id="followTable"></div>
    <div id="followPagination" class="fn__margin12 fn__right"></div>
</div>
<div class="fn__clear"></div>
<div class="form form__no-table">
${addFollowLabel}
    <label>${linkTitle1Label}</label>
    <input id="followTitle" type="text"/>
    <label>${url1Label}</label>
    <input id="followAddress" type="text"/>
    <label>${linkDescription1Label}</label>
    <input id="followDescription" type="text"/>
    <label>${linkIcon1Label}</label>
    <input id="followIcon" type="text"/><br><br>
    <button onclick="admin.followList.add();" class="fn__right">${saveLabel}</button>
    <div class="fn__clear"></div>
</div>
<div id="updateFollow" class="fn__none form form__no-table" data-title="${updateFollowLabel}">
    <label>${linkTitle1Label}</label>
    <input id="followTitleUpdate" type="text"/>
    <label>${url1Label}</label>
    <input id="followAddressUpdate" type="text"/>
    <label>${linkDescription1Label}</label>
    <input id="followDescriptionUpdate" type="text"/>
    <label>${linkIcon1Label}</label>
    <input id="followIconUpdate" type="text"/><br><br>
    <button onclick="admin.followList.update();" class="fn__right">${updateLabel}</button>
    <div class="fn__clear"></div>
</div>
${plugins}