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
<div class="module-panel">
    <div class="module-header">
        <h2>${aboutLabel}</h2>
    </div>
    <div class="module-body padding12 fn__flex">
        <div class="about-logo">
            <a href="https://github.com/adlered/bolo-solo" target="_blank">
                <img width="128" src="https://pic.stackoverflow.wiki/uploadImages/114/244/224/38/2019/11/30/01/04/1cb648c8-f9f8-430f-8616-43f7b0e2333a.png" alt="Bolo 重塑经典，致敬 Solo" title="Bolo 重塑经典，致敬 Solo" />
            </a>
        </div>
        <div class="about__panel">
            <script type="text/javascript" src="${staticServePath}/js/bolo/sweetalert.min.js"></script>
            <script type="text/javascript">
                function moveHelp() {
                    swal({
                        title: "",
                        text: "菠萝博客支持从 Solo 的指定版本快速迁移。详情请参阅帮助文档。",
                        icon: "success",
                        buttons: ["好", "前往帮助文档"],
                    })
                    .then((value) => {
                        if (null !== value) {
                            window.open('https://doc.stackoverflow.wiki/web/#/7?page_id=83');
                        }
                    });
                }
            </script>

            <div class="about-margin fn__left">
                您正在使用 菠萝博客 Bolo<br>
                当前 Bolo 版本：${boloVersion}<br>
                支持 Solo 快速迁移版本：2.9.9 ~ ${version} <a href="javascript:moveHelp()"><sup>？</sup></a>
            </div>

            <iframe src="https://ghbtns.com/github-btn.html?user=adlered&repo=bolo-solo&type=star&count=true&size=large"
                    frameborder="0" scrolling="0" width="160px" height="30px" class="about__iframe"
                    style="margin: 21px 0 0 20px;border: 0"
                    class="fn__left"></iframe>
            <div class="fn__clear"></div>
            <script type="text/javascript">
                var version = '${boloVersion}';
            </script>
            <b><p id="updateCheck">正在检查版本更新...</p></b>
        </div>
    </div>
    <span class="fn__clear">
        <div style="margin: 25px 25px 0px 25px">
            <span id="words"></span><span id="cursor">|</span>
        </div>
        <br><br>
    </span>
    <script>
        var strings = new Array(
            "　　菠萝博客是一个离线化的博客平台，不同于 Solo，Bolo 为用户提供完整的个人博客体验。菠萝博客开发组保持高频率的测试版本更新，并为参与内测的用户推送内测包。菠萝博客从用户的角度出发，解决用户提出的每个反馈、建议，致力于打造最佳体验的博客系统，感谢你的使用！参与内测请加入菠萝博客体验官 QQ 群：1105260625，提出问题、建议请访问：https://github.com/adlered/bolo-solo/issues/new",
        );
    </script>
    <link href="https://ftp.stackoverflow.wiki/bolo/typing.js/css/typing.css" rel="stylesheet">
    <script src="https://ftp.stackoverflow.wiki/bolo/typing.js/js/typing.js"></script>
</div>

${plugins}
