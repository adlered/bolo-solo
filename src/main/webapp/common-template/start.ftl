<#--

    Solo - A small and beautiful blogging system written in Java.
    Copyright (c) 2010-present, b3log.org

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
<#include "macro-common_page.ftl">

<@commonPage "${welcomeToSoloLabel}!">
    <h2>
        <span>Bolo - </span><span class="error">管理员登录</span>

    </h2>

    <div id="github">
        <br>
        <h5>如果你是第一次部署博客，在下方输入你想设定的用户名和没密码，点击管理登录即可注册！</h5>
        <br>
        <form action="${servePath}/oauth/bolo/login" method="post">
            <input type="text" name="username" id="username" placeholder="用户名" style="width: 60%"/>
            <input type="password" name="password" id="password" placeholder="密码" style="width: 60%"/>
            <br>
            <button class="startAction" style="margin-top: 16px">管理登录</button>
        </form>
        <a class="github__link" href="javascript:$('ul').slideToggle()">查看 Bolo - Solo 修改版使用说明</a>
        <div class="github__text">
            <ul>
                <li>Bolo 取消了普通用户的登录功能</li>
                <li>你可以直接填写信息评论</li>
                <li>管理员请通过此页面登录</li>
            </ul>
        </div>
    </div>
    <script type="text/javascript" src="${staticServePath}/js/lib/jquery/jquery.min.js" charset="utf-8"></script>
    <script type="text/javascript">
        (function () {
            try {
                $('.startAction').click(function () {
                    // var isAgreen = $('#isAgreenCheck').prop('checked') ? '0' : '1';
                    // window.location.href = '${servePath}/oauth/github/redirect?referer=${referer}__' + isAgreen;
                    $('#github').addClass('github--loading')
                })
            } catch (e) {
                document.querySelector('.main').innerHTML = "${staticErrorLabel}"
            }
        })()
    </script>
</@commonPage>