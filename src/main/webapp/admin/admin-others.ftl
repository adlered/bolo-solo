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
        你可以手动从链滴拉取文章中的评论到本地博客的某篇文章中。<br>
        但需要该文章为本人编写，从博客平台编写且将文章推送到链滴中过。
        <br><br>
        <b>1. 输入链滴文章号：</b><br>
        https://${hacpaiDomain}/article/ <input id="remoteArticleID" type="text" style="width: 200px">
        <br>
        <b>2.选择本地文章：</b>
        <br>
        <select id="localArticleList">
        </select>
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
                $.ajax({
                    url: Label.servePath + '/article/commentSync/' + locale + '/' + remote,
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
