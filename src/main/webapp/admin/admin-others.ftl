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
        <#if b3logEnabled>
            <li>
                <div id="tabOthers_commentSync">
                    <a href="#tools/others/commentSync">${syncLabel}</a>
                </div>
            </li>
        </#if>
        <#if b3logEnabled>
            <li>
                <div id="tabOthers_move">
                    <a href="#tools/others/move">${picBedMigrationLabel}</a>
                </div>
            </li>
        </#if>
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
        ${others1Label}
    </div>
    <script>
        $(function () {
            setInterval(function () {
                $.ajax({
                    url: Label.servePath + "/PBC/status",
                    type: "GET",
                    cache: false,
                    success: function (result, textStatus) {
                        $("#status").html(result.msg);
                    }
                });
            }, 10000)
        });
    </script>
    <div id="tabOthersPanel_log" class="fn__none form">
        <div class="fn__clear">
            <button onclick="admin.others.getLog()" class="fn__right">${RefreshLabel}</button>
        </div>
        <span style="float: left">${others2Label} <span id="memFree" style="font-weight: bold"></span></span><span style="float: right"><span id="now"></span> ${others3Label}</span>
        <div class="fn__clear"></div>
        <table id="logList">
        </table>
        <div class="fn__clear">
            <button onclick="admin.others.getLog()" class="fn__right">${RefreshLabel}</button>
        </div>
    </div>
    <div id="tabOthersPanel_import" class="fn__none">
        <h3>${others4Label}</h3>
        <br>
        <p><b>${others5Label}</b></p>
        <br>
        <form id="fileUploadForm" enctype="multipart/form-data">
            <input name="file" type="file" name="fileUpload" id="backupUpload" accept=".xml,.zip,.dat" multiple="multiple">
        </form>
        <br>
        <script type="text/javascript">
            function uploadFile(name) {
                if ($("#backupUpload").val() !== "") {
                    $("#" + name).html("${others6Label}");
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
                    alert("${others7Label}");
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
        ${others8Label}
    </div>
    <div id="tabOthersPanel_commentSync" class="fn__none form">
        <br>
        ${others9Label}
        <br><br>
        <b>${others10Label}</b><br>
        https://${hacpaiDomain}/article/ <input id="remoteArticleID" type="text" style="width: 200px">
        <br>
        <b>${others11Label}</b>
        <br>
        <select id="localArticleList">
        </select>
        <br><br>
        <b>${others12Label}</b>
        <br>
        ${others13Label}
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
        <button onclick="commentSync()">${others14Label}</button>
    </div>
</div>
${plugins}
