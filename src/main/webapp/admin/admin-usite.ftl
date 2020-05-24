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
<div id="tabUsitePanel" class="sub-tabs-main">
    <div id="tabUsitePanel_edit" class="form">
        <div class="fn__clear">
            <button onclick="admin.usite.reset()" class="fn__right">${resetUsiteLabel}</button>
        </div>
        <textarea rows="24" id="usiteEditor"></textarea>
        <div class="fn__clear">
            <button onclick="admin.usite.update()" class="fn__right">${updateLabel}</button>
        </div>
        <style>
            blockquote {
                font: 14px/22px normal helvetica, sans-serif;
                margin-top: 10px;
                margin-bottom: 10px;
                margin-left: 0px;
                padding-left: 15px;
                padding-top: 10px;
                padding-right: 10px;
                padding-bottom: 10px;
                border-left: 3px solid #ccc;
                background-color:#f1f1f1
            }
        </style>
        <div class="fn__clear">
            <h3>元数据配置指南</h3>
            <p>联系方式元数据采用 JSON 键值方式存储，<b>请勿修改表结构</b>。</p>
            <p>如果误修改了表结构，也请不用担心。菠萝博客会校验数据合法性，如果元数据配置出现问题，刷新页面后重试即可。</p>
            <p><b>请注意：如果你设置了黑客派用户名及 B3log Key，自定义联系方式可能会被覆盖！</b></p>
            <br>
            <p>示例：<b>配置 GitHub 主页，GitHub ID 为 'adlered'</b></p><br>
            修改
            <blockquote>"usiteGitHub": "值",</blockquote>
            部分，将 “值” 替换为 “adlered”，最终结果为：
            <blockquote>"usiteGitHub": "adlered",</blockquote>
            点击 “更新” 后即可生效。
        </div>
        <div class="fn__clear">
            <h3>元数据解析</h3>
            <p>每个皮肤支持显示的联系方式数量不等，部分较少见的联系方式可能不会显示。</p>
            <br>
            <table style="width:100%;" cellpadding="2" cellspacing="0" border="1" bordercolor="#CCCCCC">
                <tbody>
                <tr>
                    <td>
                        usiteUserId
                        <br />
                    </td>
                    <td>
                        不需要修改
                        <br />
                    </td>
                </tr>
                <tr>
                    <td>
                        usiteWeiBo
                        <br />
                    </td>
                    <td>
                        填写新浪微博用户 ID
                        <br />
                    </td>
                </tr>
                <tr>
                    <td>
                        usiteQQMusic
                        <br />
                    </td>
                    <td>
                        填写 QQ 音乐网页版用户 ID
                        <br />
                    </td>
                </tr>
                <tr>
                    <td>
                        usiteStackOverflow
                        <br />
                    </td>
                    <td>
                        填写 StackOverflow 用户名
                        <br />
                    </td>
                </tr>
                <tr>
                    <td>
                        usiteDribbble
                        <br />
                    </td>
                    <td>
                        填写优设网用户 ID
                        <br />
                    </td>
                </tr>
                <tr>
                    <td>
                        usiteGitHub
                        <br />
                    </td>
                    <td>
                        填写 GitHub 个人主页 ID
                        <br />
                    </td>
                </tr>
                <tr>
                    <td>
                        usiteMedium
                        <br />
                    </td>
                    <td>
                        填写 Medium 写作平台用户 ID
                        <br />
                    </td>
                </tr>
                <tr>
                    <td>
                        usiteTwitter
                        <br />
                    </td>
                    <td>
                        填写 Twitter 用户名
                        <br />
                    </td>
                </tr>
                <tr>
                    <td>
                        usiteQQ
                        <br />
                    </td>
                    <td>
                        填写 QQ 号码
                        <br />
                    </td>
                </tr>
                <tr>
                    <td>
                        usiteLinkedln
                        <br />
                    </td>
                    <td>
                        填写领英用户 ID
                        <br />
                    </td>
                </tr>
                <tr>
                    <td>
                        usiteSteam
                        <br />
                    </td>
                    <td>
                        填写 Steam ID
                        <br />
                    </td>
                </tr>
                <tr>
                    <td>
                        oId
                        <br />
                    </td>
                    <td>
                        不需要修改
                        <br />
                    </td>
                </tr>
                <tr>
                    <td>
                        usiteInstagram
                        <br />
                    </td>
                    <td>
                        填写 Instagram 用户 ID
                        <br />
                    </td>
                </tr>
                <tr>
                    <td>
                        usiteCodePen
                        <br />
                    </td>
                    <td>
                        填写 CodePen 用户 ID
                        <br />
                    </td>
                </tr>
                <tr>
                    <td>
                        usiteWYMusic
                        <br />
                    </td>
                    <td>
                        填写网易云音乐用户 ID
                        <br />
                    </td>
                </tr>
                <tr>
                    <td>
                        usiteWeChat
                        <br />
                    </td>
                    <td>
                        填写微信号（在博客主页微信图标上停留，会显示微信号，但不支持点击跳转）
                        <br />
                    </td>
                </tr>
                <tr>
                    <td>
                        usiteZhiHu
                        <br />
                    </td>
                    <td>
                        填写知乎用户 ID
                        <br />
                    </td>
                </tr>
                <tr>
                    <td>
                        usiteBehance
                        <br />
                    </td>
                    <td>
                        填写 Behance 用户 ID
                        <br />
                    </td>
                </tr>
                <tr>
                    <td>
                        usiteTelegram
                        <br />
                    </td>
                    <td>
                        填写 Telegram 用户名
                        <br />
                    </td>
                </tr>
                <tr>
                    <td>
                        usiteFacebook
                        <br />
                    </td>
                    <td>
                        填写 Facebook 用户名
                        <br />
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
${plugins}
