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
<#include "../common-template/macro-common_page.ftl">

<@commonPage "你的请求被防火墙拦截">
    <h2>访问太快啦! 请稍候重试.</h2>
    <img class="img-error" src="${staticServePath}/images/shield.png" title="500" alt="500 Internal Server Error!"/>
    <div class="a-error">
        <a href="${loginURL}">管理登录</a> | <a href="${servePath}">返回主页</a>
    </div>
</@commonPage>