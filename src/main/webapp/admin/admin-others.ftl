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
        <span style="float: left">JVM 空闲内存：<span id="memFree" style="font-weight: bold"></span></span><span style="float: right"><span id="now"></span> 更新</span>
        <br><br>
        <table>
            <tr><th style="width:30px">级别</th><th style="width: 90%">日志</th></tr>
        </table>
        <table id="logList">
        </table>
    </div>
    <div id="tabOthersPanel_import" class="fn__none">
        <h3>从其它平台导入文章</h3>
        <br>
        <p><b>请先选择备份文件</b></p>
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

        <button id="cnblogs">从博客园备份文件导入文章</button>
        <p style="margin-top: 5px">可将从博客园备份的 xml 文件导入至菠萝博客。</p>
        <button id="markdown" style="margin-top: 10px">Markdown zip 导入文章</button>
        <p style="margin-top: 5px">可将多篇 .md 文章打包成 zip 导入至菠萝博客。</p>
    </div>
</div>
${plugins}
