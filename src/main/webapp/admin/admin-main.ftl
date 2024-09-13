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
            <h2>数据</h2>
        </div>
        <div class="module-body padding12">
            <div style="width: 32%; display: inline-table;">
                <canvas id="articleCountChart"></canvas>
                <script type="text/javascript">
                    Chart.defaults.global.animation.duration = 2000;

                    let options = {
                        responsive: true,
                        title: {
                            display: true,
                            text: '每月文章数量统计'
                        },
                        tooltips: {
                            mode: 'index',
                            intersect: false,
                        },
                        hover: {
                            mode: 'nearest',
                            intersect: true
                        }
                    };
                    let ctx = document.getElementById("articleCountChart").getContext("2d");
                    var categoryCountChart = new Chart(ctx, {
                        type: 'line',
                        data: data1,
                        options: options
                    });
                </script>
            </div>

            <div style="width: 32%; display: inline-table;">
                <canvas id="categoryCountChart"></canvas>
                <script type="text/javascript">
                    function getRandomColor() { var letters = '0123456789ABCDEF'.split(''); var color = '#'; for (var i = 0; i < 6; i++ ) { color += letters[Math.floor(Math.random() * 16)]; } return color; }
                    let data3 = {
                        datasets: [
                            {
                                label: [
                                    "文章数量"
                                ],
                                data: [
                                    <#list categories as category>
                                    <#if category.categoryPublishedArticleCount != 0>
                                    ${category.categoryPublishedArticleCount},
                                    </#if>
                                    </#list>
                                ],
                                backgroundColor: [
                                    <#list categories as category>
                                    <#if category.categoryPublishedArticleCount != 0>
                                    Chart.helpers.color(getRandomColor()).alpha(0.5).rgbString(),
                                    </#if>
                                    </#list>
                                ],
                            }
                        ],

                        labels: [
                            <#list categories as category>
                                <#if category.categoryPublishedArticleCount != 0>
                                    "${category.categoryTitle}",
                                </#if>
                            </#list>
                        ]
                    };
                    let options = {
                        responsive: true,
                        title: {
                            display: true,
                            text: '分类使用率'
                        },
                        legend: {
                            display: false
                        }
                    };
                    let ctx = document.getElementById("categoryCountChart").getContext("2d");
                    var tagsTop5Chart = new Chart(ctx, {
                        type: 'bar',
                        data: data3,
                        options: options
                    });
                </script>
            </div>

            <div style="width: 32%; display: inline-table;">
                <canvas id="tagsTop5Chart"></canvas>
                <script type="text/javascript">
                    let options = {
                        responsive: true,
                        title: {
                            display: true,
                            text: '标签 Top5'
                        }
                    };
                    let ctx = document.getElementById("tagsTop5Chart").getContext("2d");
                    var tagsTop5Chart = new Chart(ctx, {
                        type: 'doughnut',
                        data: data2,
                        options: options
                    });
                </script>
            </div>
        </div>
    </div>
    <div class="module-panel">
        <div class="module-header">
            <h2>公告</h2>
        </div>
        <div class="module-body padding12">
            <div id="noticeList" style="background: none;">
                <script>
                    $("#noticeList").load("https://ftp.stackoverflow.wiki/bolo/boardcast.html");
                </script>
            </div>
        </div>
    </div>
    <div class="module-panel">
        <div class="module-header">
            <h2>近期日志</h2>
        </div>
        <div class="module-body padding12">
            <div id="recentLogs"></div>
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
            content: "欢迎使用菠萝博客 " + boloVersion + "！<br>" +
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
