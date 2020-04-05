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
<div id="tabUsite" class="sub-tabs">
    <ul>
        <li>
            <div id="tabUsite_edit">
                <a href="#tools/usite/edit">数据编辑</a>
            </div>
        </li>
    </ul>
</div>
<div id="tabUsitePanel" class="sub-tabs-main">
    <div id="tabUsitePanel_edit" class="fn__none form">
        <div class="fn__clear">
            <button onclick="admin.usite.reset()" class="fn__right">${resetUsiteLabel}</button>
        </div>
        <textarea rows="24" id="usiteEditor"></textarea>
        <div class="fn__clear">
            <button onclick="admin.usite.update()" class="fn__right">${updateLabel}</button>
        </div>
        <div class="fn__clear">
            <h3>元数据配置指南</h3>
            <p>联系方式元数据采用 JSON 键值方式存储，<b>请勿修改表结构</b>。</p>
        </div>
    </div>
</div>
${plugins}
