<#--

    Solo - A small and beautiful blogging system written in Java.
    Copyright (c) 2010-present, b3log.org

    Solo is licensed under Mulan PSL v2.
    You can use this software according to the terms and conditions of the Mulan PSL v2.
    You may obtain a copy of Mulan PSL v2 at:
            http://license.coscl.org.cn/MulanPSL2
    THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
    See the Mulan PSL v2 for more details.

-->
<hr/>
<div class="commentFont" id = "commentIcon" style="margin: 0 auto; text-align: center; padding-bottom: 20px">
    <img src="https://img.lonuslan.com/lonuslan/20200727/1P1VgFJPjTKg.png"/>
    <span style="font-size: 30px; vertical-align: middle;">
        评论
    </span>
</div>
<div class="wrapper view-article">
<div class="Valine">
</div>
</div>
<#--<script src='https://img.lonuslan.com/lonuslan/20200724/kaD7UaLWrx0i.js'></script>-->
<#--<script src='https://unpkg.com/valine/dist/Valine.min.js'></script>-->
<script src="https://img.lonuslan.com/lonuslan/20200729/Valine.min.js"></script>
<script>
    const valine = new Valine({
        el: '.Valine',
        appId: 'G9biID9XRxn5YbHGv99L9HQ5-9Nh9j0Va',
        appKey: 'eqfSV7FBGCr0HBqWj0EkbaYS',
        placeholder: '留下您的足迹吧 (●\'◡\'●)',
        lang: 'zh-CN',
        avatar: 'wavatar',
        enableQQ: 'true',
        metaPlaceholder: {"nick":"昵称/QQ号(必填)","mail":"邮箱（接收提醒-必填）","link":"个人站点(选填)"},
        master: '6163aa7c062eae5ed4c4fd24f8635611',   //博主邮箱md5
        tagMeta: ["博主","小伙伴","访客"],     //标识字段名
        friends:  ['7c896fde1507145d79e85851c8b58b2b', 'b9351cadf17830fd9dd16e7f1179a37d', 'd8a55c57090003fb0f19acc04111a7cb'],  //小伙伴邮箱Md5
    });
    // function showDialog(){
    //     $(".vnick").focus(function () {
    //         var addHtml = "<span class = \"popuptext\" id = \"thepopup\" style = \"margin-left: -115px;width: 230px;\">moumoumou</span>";
    //         $(".vheader").append(addHtml);
    //         cmt_showPopup(this);
    //     });
    // };
    // function cmt_showPopup(ele) {
    //     var popup = $(ele).find("#thePopup");
    //     popup.addClass("show");
    //     $(ele).find("input").blur(function () {
    //         var popup = $(ele).find("#thePopup");
    //         popup.removeClass("show");
    //     });
    // };
    // showDialog();
</script>