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
                <a class="tab-current" href="#tools/usite/edit">数据编辑</a>
            </div>
        </li>
    </ul>
</div>
<style type="text/css">
    table.tftable {font-size:12px;color: #545454;width:100%;border-width: 1px;border-color: #729ea5;border-collapse: collapse;}
    table.tftable th {font-size:12px;background-color: #f8f8f8;border-width: 1px;padding: 8px;border-style: solid;border-color: rgba(118, 118, 118, 0.2);text-align:left;}
    table.tftable tr {background-color:#ffffff;}
    table.tftable td {font-size:12px;border-width: 1px;padding: 8px;border-style: solid;border-color: rgba(118, 118, 118, 0.2);}
</style>
<div id="tabUsitePanel" class="sub-tabs-main usite">
    <div id="tabUsitePanel_edit" class="form">
        <div class="fn__clear">
            <button onclick="admin.usite.update()" class="fn__right">${updateLabel}</button>
        </div>
        <div class="fn__clear">
            <label>联系方式将以图标的方式显示在你的博客主页中，立即生效。<br>每个皮肤支持显示的联系方式数量不等，部分较少见的联系方式可能不会显示。<br>如要通过设定的 链滴用户名 及 B3log Key 从链滴更新联系方式，请通过联机工具 - 立即从链滴获取联系方式 进行获取。</label>
        </div>
        <table id="tfhover" class="tftable" border="1">
            <tr>
                <th>平台</th>
                <th>联系方式</th>
            </tr>
            <tr>
                <td><b>新浪微博</b></td>
                <td>https://www.weibo.com/ <input id="usiteWeiBo" type="text" style="width:200px"></td>
            </tr>
            <tr>
                <td><b>QQ音乐</b></td>
                <td>https://y.qq.com/portal/profile.html?uin= <input id="usiteQQMusic" type="text" style="width:200px"></td>
            </tr>
            <tr>
                <td><b>StackOverflow</b></td>
                <td>https://stackoverflow.com/users/ <input id="usiteStackOverflow" type="text" style="width:200px"></td>
            </tr>
            <tr>
                <td><b>优设网</b></td>
                <td>https://dribbble.com/ <input id="usiteDribbble" type="text" style="width:200px"></td>
            </tr>
            <tr>
                <td><b>GitHub</b></td>
                <td>https://github.com/ <input id="usiteGitHub" type="text" style="width:200px"></td>
            </tr>
            <tr>
                <td><b>Medium</b></td>
                <td>https://medium.com/ <input id="usiteMedium" type="text" style="width:200px"></td>
            </tr>
            <tr>
                <td><b>Twitter</b></td>
                <td>https://twitter.com/ <input id="usiteTwitter" type="text" style="width:200px"></td>
            </tr>
            <tr>
                <td><b>QQ</b></td>
                <td>QQ-Account <input id="usiteQQ" type="text" style="width:200px"></td>
            </tr>
            <tr>
                <td><b>领英</b></td>
                <td>https://www.linkedin.com/in/ <input id="usiteLinkedln" type="text" style="width:200px"></td>
            </tr>
            <tr>
                <td><b>Steam</b></td>
                <td>https://steamcommunity.com/id/ <input id="usiteSteam" type="text" style="width:200px"></td>
            </tr>
            <tr>
                <td><b>Instagram</b></td>
                <td>Instagram-Account <input id="usiteInstagram" type="text" style="width:200px"></td>
            </tr>
            <tr>
                <td><b>CodePen</b></td>
                <td>https://codepen.io/ <input id="usiteCodePen" type="text" style="width:200px"></td>
            </tr>
            <tr>
                <td><b>网易云音乐</b></td>
                <td>https://music.163.com/#/user/home?id= <input id="usiteWYMusic" type="text" style="width:200px"></td>
            </tr>
            <tr>
                <td><b>微信</b></td>
                <td>WeChat-Account <input id="usiteWeChat" type="text" style="width:200px"></td>
            </tr>
            <tr>
                <td><b>知乎</b></td>
                <td>https://www.zhihu.com/people/ <input id="usiteZhiHu" type="text" style="width:200px"></td>
            </tr>
            <tr>
                <td><b>Behance</b></td>
                <td>https://www.behance.net/ <input id="usiteBehance" type="text" style="width:200px"></td>
            </tr>
            <tr>
                <td><b>Telegram</b></td>
                <td>https://telegram.me/ <input id="usiteTelegram" type="text" style="width:200px"></td>
            </tr>
            <tr>
                <td><b>Facebook</b></td>
                <td>https://www.facebook.com/ <input id="usiteFacebook" type="text" style="width:200px"></td>
            </tr>
        </table>

        <div class="fn__clear">
            <button onclick="admin.usite.update()" class="fn__right">${updateLabel}</button>
        </div>
    </div>
</div>
${plugins}
