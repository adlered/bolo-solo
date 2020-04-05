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
<div id="notice">
    <div class="module-panel">
        <div class="module-header">
            <h2><a href="https://github.com/adlered/bolo-solo" target="_blank">实时公告</a></h2>
            <h6>公告栏仅用于显示 Bolo 的重要动态，不会用于加载恶意脚本、恶意广告等操作。</h6>
        </div>
        <div class="module-body padding12">
            <div id="noticeList" style="background: none;">
                <iframe src="https://ftp.stackoverflow.wiki/bolo/notice.html" height="400px" width="100%" frameborder="no" border="0" scrolling="auto"></iframe>
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
            console.log('show');
        } else {
            console.log('skip');
        }
    });

    function neverShowTips() {
        $.removeCookie(key);
        $.cookie(key, boloVersion, { expires: 222, path: '/' });
        $('.trips').remove();

    }

    function showTips() {
        var config = {
            content: "新增 <b>联系方式自定义</b>、<b>其他-日志浏览</b> 功能<br>" +
                "更方便实用，快来体验吧！<br>" +
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
