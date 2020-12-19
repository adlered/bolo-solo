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
<div id="tabOthers" class="sub-tabs">
    <ul>
        <li>
            <div id="tabOthers_data">
                <a href="#tools/others/data">${exportDataLabel}</a>
            </div>
        </li>
        <li>
            <div id="tabOthers_import">
                <a href="#tools/others/import">${importDataLabel}</a>
            </div>
        </li>
        <li>
            <div id="tabOthers_move">
                <a href="#tools/others/move">图床迁移</a>
            </div>
        </li>
        <li>
            <div id="tabOthers_log">
                <a href="#tools/others/log">${viewLogLabel}</a>
            </div>
        </li>
        <li>
            <div id="tabOthers_tag">
                <a href="#tools/others/tag">${clearDataLabel}</a>
            </div>
        </li>
        <li>
            <div id="tabOthers_commentSync">
                <a href="#tools/others/commentSync">${syncLabel}</a>
            </div>
        </li>
    </ul>
</div>
<div id="tabOthersPanel" class="sub-tabs-main">
    <div id="tabOthersPanel_tag" class="fn__none">
        <button class="fn__margin12" onclick="admin.others.removeUnusedTags();">${removeUnusedTagsLabel}</button>
        <button class="fn__margin12" onclick="admin.others.removeUnusedArchives();">${removeUnusedArchivesLabel}</button>
    </div>
    <div id="tabOthersPanel_data" class="fn__none">
        <#if supportExport>
        <button class="fn__margin12" onclick="admin.others.exportSQL();">${exportSQLLabel}</button>
        </#if>
        <button class="fn__margin12" onclick="admin.others.exportJSON();">${exportJSONLabel}</button>
        <button class="fn__margin12" onclick="admin.others.exportHexo();">${exportHexoLabel}</button>
    </div>
    <div id="tabOthersPanel_move" class="fn__none">
        <h4>一键将您博客上使用黑客派（链滴）图床上的图片转换至当前设定的图床</h4>
        <br>
        <p>Bolo 默认将您在编辑器中上传的图床上传至链滴图床（URL 为 ld246.com 或 hacpai.com），但链滴图床的限制较多，可能会偶现无法打开的情况。</p>
        <p>如果您希望获得更好的图床体验，请：</p><br>
        <p><b>1.</b> 在偏好设定中将您的自定义图床修改为其它图床（例又拍云、阿里云或本地图床）</p>
        <p><b>2.</b> 通过本功能一键将您旧文章中使用链滴图床的图片一键上传至您指定新图床的图片，并替换链接</p>
        <p><b>3.</b> 享受数据掌握在自己手中的安全感</p><br>
        <h4>使用前请注意</h4>
        <p>由于链滴图床有访问频率限制，您的图片转换将在后台静默执行，您<b>可以关闭当前页面</b>，但请<b>不要停止 Bolo</b>并且<b>不要重复执行该功能</b></p>
        <p>当您的转换任务在下方从 “工作中” 变更为 “空闲” 时，即转换成功</p><br>
        <span>当前状态：
            <span style="color: green; font-weight: bold">空闲</span>
        </span><br><br>
        <button>开始转换</button>
    </div>
    <div id="tabOthersPanel_log" class="fn__none form">
        <div class="fn__clear">
            <button onclick="admin.others.getLog()" class="fn__right">${RefreshLabel}</button>
        </div>
        <span style="float: left">JVM 空闲内存：<span id="memFree" style="font-weight: bold"></span></span><span style="float: right"><span id="now"></span> 更新</span>
        <div class="fn__clear"></div>
        <table id="logList">
        </table>
        <div class="fn__clear">
            <button onclick="admin.others.getLog()" class="fn__right">${RefreshLabel}</button>
        </div>
    </div>
    <div id="tabOthersPanel_import" class="fn__none">
        <h3>从其它平台导入文章</h3>
        <br>
        <p><b>第一步，选择备份文件</b></p>
        <br>
        <form id="fileUploadForm" enctype="multipart/form-data">
            <input name="file" type="file" name="fileUpload" id="backupUpload" accept=".xml,.zip,.dat" multiple="multiple">
        </form>
        <br>
        <script type="text/javascript">
            function uploadFile(name) {
                if ($("#backupUpload").val() !== "") {
                    $("#" + name).html("正在导入中，请不要进行其它操作！");
                    let formData = new FormData($("#fileUploadForm")[0]);
                    let options = {
                        url: "${staticServePath}/import/" + name,
                        async: "false",
                        type: 'POST',
                        data: formData,
                        dataType: 'json',
                        cache: false,
                        processData: false,
                        contentType: false,
                        success: function (result) {
                            //返回数据后你的后续处理
                            alert(result.msg);
                            location.reload();
                        }
                    };
                    $.ajax(options);
                } else {
                    alert("请先选择文件！");
                }
            }

            $("#cnblogs").click(function () {
                uploadFile("cnblogs");
            });


            $("#markdown").click(function () {
                uploadFile("markdown");
            });
        </script>

        <br>
        <p><b>第二步，选择备份文件的类型</b></p>
        <br>
        <button id="cnblogs">从博客园备份文件导入文章</button>
        <p style="margin-top: 5px">可将从博客园备份的 xml 文件导入至菠萝博客，部分文章可能会由于长度原因跳过导入。</p>
        <button id="markdown" style="margin-top: 10px">Markdown zip 导入文章</button>
        <p style="margin-top: 5px">可将多篇 .md 文章打包成 zip 导入至菠萝博客，支持 Hexo/jekyll 格式文件；支持从<a href="#tools/others/data">数据导出</a>选项卡中导出的 Hexo 文件导入。</p>
        <br>
        <p><b>文章导入功能可能存在数据风险，请谨慎使用。</b></p>
    </div>
    <div id="tabOthersPanel_commentSync" class="fn__none form">
        <br>
        你可以手动从链滴拉取文章中的评论到本地博客的某篇文章中。<br>
        每条评论只能同步一次，如要将评论导入两次，请先删除第一次导入的评论。<br>
        请勿滥用该功能、自欺欺人哦~<br>
        如果评论过多，可能需要较长时间，请不要多次点击同步按钮，稍安勿躁。<br>
        <br><br>
        <b>1. 输入链滴文章号：</b><br>
        https://${hacpaiDomain}/article/ <input id="remoteArticleID" type="text" style="width: 200px">
        <br>
        <b>2.选择本地文章：</b>
        <br>
        <select id="localArticleList">
        </select>
        <br><br>
        <b>3. 填入您在链滴社区的 Cookie 中 "symphony" 选项的值</b>
        <br>
        请您打开<a href="https://ld246.com" target="_blank">链滴社区</a>并登录, 使用谷歌浏览器右键选择"检查元素", 在"Application"标签页选择"Cookies"-"https://ld246.com", 将右侧"symphony"的值填入.
        <br>
        <input id="symphony" type="text">
        <br><br>
        <script type="text/javascript">
            $.ajax({
                url: Label.servePath + '/article/commentSync/getList',
                type: 'GET',
                async: false,
                success: function(res) {
                    let data = res.data;
                    for(i = 0; i < data.length; i++) {
                        let oId = data[i].oId;
                        let title = data[i].title;
                        $("#localArticleList").append("<option value=\"" +  oId + "\">" + title + "</option>");
                    }
                }
            });

            function commentSync() {
                let locale = $("#localArticleList").val();
                let remote = $("#remoteArticleID").val();
                let symphony = $("#symphony").val();
                $.ajax({
                    url: Label.servePath + '/article/commentSync/' + locale + '/' + remote + "/" + symphony,
                    type: 'GET',
                    async: false,
                    success: function(res) {
                        alert(res.msg);
                    }
                });
            }
        </script>
        <button onclick="commentSync()">开始同步</button>
    </div>
</div>
${plugins}
