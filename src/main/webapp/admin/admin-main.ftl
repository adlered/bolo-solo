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
<style>
    #broadcast {
        display: block;
        height: 550px;
        width: 100%;
    }
</style>
<div id="notice">
    <div class="module-panel">
        <div class="module-header">
            <h2><a href="https://github.com/adlered/bolo-solo" target="_blank">统计板</a></h2>
        </div>
        <div class="module-body padding12">
            <div style="height: 200px;  width: 350px;">
                <h5 class="bar-title">当前周用户注册数一览表</h5>
                <canvas id="currentWeekChart"></canvas>
            </div>
        </div>
        <script type="text/javascript">
        $(function() {
            var data = {
                labels: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"],
                datasets: [
                    {
                        label: "用户数量",
                        backgroundColor: "rgba(0, 0, 0, 0.1)",//线条填充色
                        pointBackgroundColor:"rgba(255,48,48,0.2)",//定点填充色
                        data: [65, 59, 80, 81, 56, 55, 40]
                    }
                ]
            };
            var options = {};
            var ctx = document.getElementById("currentWeekChart").getContext("2d");
            var currentWeekChart = new Chart(ctx,{
                type: 'line',
                data: data,
                options:options
            });
        })
        </script>
    </div>
    <div class="module-panel">
        <div class="module-header">
            <h2><a href="https://github.com/adlered/bolo-solo" target="_blank">公告</a></h2>
        </div>
        <div class="module-body padding12">
            <div id="noticeList" style="background: none;">
                <iframe id="broadcast" src="https://ftp.stackoverflow.wiki/bolo/notice.html" frameborder="no" border="0" scrolling="auto"></iframe>
            </div>
        </div>
    </div>
</div>
<style>
    .tripscon {
        padding: 10px 10px 10px 10px;
    }
</style>
<script type="text/javascript">
    var key = 'neverShowVer';

    $(function () {
        var isShowTips = true;
        if ($.cookie(key)) {
            if ($.cookie(key) === boloVersion) {
                isShowTips = false;
            }
        }
        if (isShowTips) {
            showTips();
        }
    });

    function neverShowTips() {
        $.removeCookie(key);
        $.cookie(key, boloVersion, { expires: 222, path: '/' });
        $('.trips').remove();

    }

    function showTips() {
        var config = {
            content: "欢迎使用菠萝博客 V2.0 稳定版！<br>" +
                "<a href='javascript:neverShowTips()'>不再提醒</a>",
            type: "html",
            alignTo: ["right","top"],
            trigger: "show",
            isclose: false,
            color: ["#B2E281","#B2E281"]
        };
        $("#tabToolsTitle").showTips(config);
    }
</script>
<div id="mainPanel1">
</div>
<div id="mainPanel2"></div>
${plugins}
