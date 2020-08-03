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
                <img width="128" src="https://pic.stackoverflow.wiki/uploadImages/114/246/231/87/2020/06/06/16/41/3e4a3ce8-8882-4258-9860-a337bf859605.png" alt="Bolo 重塑经典，致敬 Solo" title="Bolo 重塑经典，致敬 Solo" />
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
            <iframe src="https://doc.stackoverflow.wiki/web/#/7?page_id=91" frameborder="no" border="0" scrolling="auto" style="display: block; height: 550px; width: 100%;"></iframe>
        </div>
    </span>
    <br><br>
</div>

${plugins}
