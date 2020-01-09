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
<script type="text/javascript" src="${staticServePath}/js/lib/jquery/jquery.min.js" charset="utf-8"></script>
<script src="${staticServePath}/js/bolo/jquery.cookie.min.js"></script>
<#include "../common-template/macro-common_page.ftl">

<@commonPage "404 页面未找到">
<h2>404 Not Found!</h2>
<img class="img-error" src="${staticServePath}/images/404.gif" title="404" alt="404 Not Found!"/>
<div class="a-error">
    <a href="${loginURL}">登录</a> | <a href="${servePath}">返回主页</a>
</div>
<script>
    $.removeCookie('skin');
</script>
</@commonPage>