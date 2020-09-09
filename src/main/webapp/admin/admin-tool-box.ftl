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
<div id="filler"><script>
        function clearMailCache() {
            $.get("prop/mail/clear", function (result) {
                alert("清除成功！");
            });
        }
        function backup() {
            $.ajax({
                type: 'GET',
                url: 'prop/backup/hacpai/do/upload',
                async: false,
                success: function (res) {
                    alert("备份成功！");
                },
                error: function (e) {
                    if (e.status === 404) {
                        alert("抱歉，当前 Bolo 版本不支持该功能！小提示：重启服务端，等待 5 分钟后，Bolo 会自动备份文章到链滴（功能开启的情况下）。");
                    } else {
                        alert("备份失败！请检查偏好设置，并确定链滴用户名和 B3log Key 设置正确。");
                    }
                }
            });
        }
        function refreshUsite() {
            $.ajax({
                type: 'GET',
                url: 'admin/usite/refresh',
                async: false,
                success: function (res) {
                    alert("刷新成功！");
                },
                error: function (e) {
                    if (e.status === 404) {
                        alert("抱歉，当前 Bolo 版本不支持该功能！");
                    } else {
                        alert("获取失败！请检查 链滴用户名 及 B3log Key 是否正确！");
                    }
                }
            });
        }
    </script>
    <div id="pluginTable">
        <div class="table-main" id="pluginTableTable">
            <div id="pluginTableTableHeader" class="table-header">
                <table cellpadding="0" cellspacing="0" style="width:100%">
                    <tbody>
                    <tr>
                        <th style="width:465px;">

                        </th>
                        <th style="min-width:80px;">
                            描述
                        </th>
                        <th style="width:105px;">
                            操作
                        </th>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div id="pluginTableTableMain" class="table-body" style="height:auto">
                <table id="pluginTableSubTable0" style="width:100%;background-color: white;" cellpadding="0" cellspacing="0">
                    <tbody class="table-oddRow">
                    <tr class="table-hasExpend">
                        <td style="padding-left: 20px; padding-right: 20px;">
                            清除邮箱缓存
                        </td>
                        <td>
                            清空评论时提示用户输入的邮箱列表，评论过多时可以优化性能；<br>
                            清空后旧评论的用户将无法收到邮件提醒，请慎重。
                        </td>
                        <td style="padding-right: 20px;">
                            <button onclick="clearMailCache()" style="float:right">确定执行</button>
                        </td>
                    </tr>
                    </tbody>
                    <tbody class="table-oddRow">
                    <tr class="table-hasExpend">
                        <td style="padding-left: 20px; padding-right: 20px;">
                            手动上传公开文章备份至链滴
                        </td>
                        <td>
                            手动将你的<b>公开</b>文章备份上传至链滴，并参与<a target="_blank" href="https://${hacpaiDomain}/top/solo">博客排行榜</a>排名。
                        </td>
                        <td style="padding-right: 20px;">
                            <button onclick="backup()" style="float:right">确定备份</button>
                        </td>
                    </tr>
                    </tbody>
                    <tbody class="table-oddRow">
                    <tr class="table-hasExpend">
                        <td style="padding-left: 20px; padding-right: 20px;">
                            立即从链滴获取联系方式
                        </td>
                        <td>
                            你将从指定 链滴用户名 和 B3log Key 的链滴账户中获取你的社交媒体信息，本地自定义联系方式将被覆盖。
                        </td>
                        <td style="padding-right: 20px;">
                            <button onclick="refreshUsite()" style="float:right">开始获取</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
${plugins}
