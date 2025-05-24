/*
 * Bolo - A stable and beautiful blogging system based in Solo.
 * Copyright (c) 2020-present, https://github.com/bolo-blog
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
/**
 * follow list for admin
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 */

/* follow-list 相关操作 */
admin.followList = {
    tablePagination: new TablePaginate("follow"),
    pageInfo: {
        currentCount: 1,
        pageCount: 1,
        currentPage: 1
    },
    id: "",
    /*
     * 初始化 table, pagination
     */
    init: function (page) {
        this.tablePagination.buildTable([{
            text: "",
            index: "followOrder",
            width: 60
        }, {
            style: "padding-left: 12px;",
            text: Label.linkTitle1Label,
            index: "followTitle",
            width: 230
        }, {
            style: "padding-left: 12px;",
            text: Label.urlLabel,
            index: "followAddress",
            minWidth: 180
        }, {
            style: "padding-left: 12px;",
            text: Label.linkDescription1Label,
            index: "followDescription",
            width: 360
        }]);

        this.tablePagination.initPagination();
        this.getList(page);

        $("#updateFollow").dialog({
            title: $("#updateFollow").data('title'),
            width: 700,
            height: 350,
            "modal": true,
            "hideFooter": true
        });
    },

    /* 
     * 根据当前页码获取链接列表
     * 
     * @pagNum 当前页码
     */
    getList: function (pageNum) {
        $("#loadMsg").text(Label.loadingLabel);
        if (pageNum === 0) {
            pageNum = 1;
        }
        this.pageInfo.currentPage = pageNum;
        var that = this;

        $.ajax({
            url: Label.servePath + "/console/follows/" + pageNum + "/" + Label.PAGE_SIZE + "/" + Label.WINDOW_SIZE,
            type: "GET",
            cache: false,
            success: function (result, textStatus) {
                $("#tipMsg").text(result.msg);
                if (!result.sc) {
                    $("#loadMsg").text("");
                    return;
                }

                var follows = result.follows;
                var followData = [];
                admin.followList.pageInfo.currentCount = follows.length;
                admin.followList.pageInfo.pageCount = result.pagination.paginationPageCount === 0 ? 1 : result.pagination.paginationPageCount;

                for (var i = 0; i < follows.length; i++) {
                    followData[i] = {};
                    if (i === 0) {
                        if (follows.length === 1) {
                            followData[i].followOrder = "";
                        } else {
                            followData[i].followOrder = '<div class="table-center" style="width:14px">\
                                <span onclick="admin.followList.changeOrder(' + follows[i].oId + ', ' + i + ', \'down\');" class="icon-move-down"></span>\
                            </div>';
                        }
                    } else if (i === follows.length - 1) {
                        followData[i].followOrder = '<div class="table-center" style="width:14px">\
                                <span onclick="admin.followList.changeOrder(' + follows[i].oId + ', ' + i + ', \'up\');" class="icon-move-up"></span>\
                            </div>';
                    } else {
                        followData[i].followOrder = '<div class="table-center" style="width:38px">\
                                <span onclick="admin.followList.changeOrder(' + follows[i].oId + ', ' + i + ', \'up\');" class="icon-move-up"></span>\
                                <span onclick="admin.followList.changeOrder(' + follows[i].oId + ', ' + i + ', \'down\');" class="icon-move-down"></span>\
                            </div>';
                    }

                    followData[i].followTitle = follows[i].followTitle;
                    followData[i].followAddress = "<a target='_blank' class='no-underline' href='" + follows[i].followAddress + "'>"
                        + follows[i].followAddress + "</a>";
                    followData[i].followDescription = follows[i].followDescription;
                    followData[i].followIcon = follows[i].followIcon;
                    followData[i].expendRow = "<span><a href='" + follows[i].followAddress + "' target='_blank'>" + Label.viewLabel + "</a>  \
                                <a href='javascript:void(0)' onclick=\"admin.followList.get('" + follows[i].oId + "')\">" + Label.updateLabel + "</a>\
                                <a href='javascript:void(0)' onclick=\"admin.followList.del('" + follows[i].oId + "', '" + encodeURIComponent(follows[i].followTitle) + "')\">" + Label.removeLabel + "</a></span>";
                }

                that.tablePagination.updateTablePagination(followData, pageNum, result.pagination);

                $("#loadMsg").text("");
            }
        });
    },

    /*
     * 添加链接
     */
    add: function () {
        if (this.validate()) {
            $("#loadMsg").text(Label.loadingLabel);
            $("#tipMsg").text("");
            var requestJSONObject = {
                "follow": {
                    "followTitle": $("#followTitle").val(),
                    "followAddress": $("#followAddress").val(),
                    "followDescription": $("#followDescription").val(),
                    "followIcon": $("#followIcon").val()
                }
            };

            $.ajax({
                url: Label.servePath + "/console/follow/",
                type: "POST",
                cache: false,
                data: JSON.stringify(requestJSONObject),
                success: function (result, textStatus) {
                    $("#tipMsg").text(result.msg);
                    if (!result.sc) {
                        $("#loadMsg").text("");
                        return;
                    }

                    $("#followTitle").val("");
                    $("#followAddress").val("");
                    $("#followDescription").val("");
                    $("#followIcon").val("");
                    if (admin.followList.pageInfo.currentCount === Label.PAGE_SIZE &&
                        admin.followList.pageInfo.currentPage === admin.followList.pageInfo.pageCount) {
                        admin.followList.pageInfo.pageCount++;
                    }
                    var hashList = window.location.hash.split("/");
                    if (admin.followList.pageInfo.pageCount !== parseInt(hashList[hashList.length - 1])) {
                        admin.setHashByPage(admin.followList.pageInfo.pageCount);
                    }

                    admin.followList.getList(admin.followList.pageInfo.pageCount);

                    $("#loadMsg").text("");
                }
            });
        }
    },

    /*
     * 获取链接
     * @id 链接 id
     */
    get: function (id) {
        $("#loadMsg").text(Label.loadingLabel);
        $("#updateFollow").dialog("open");

        $.ajax({
            url: Label.servePath + "/console/follow/" + id,
            type: "GET",
            cache: false,
            success: function (result, textStatus) {
                $("#tipMsg").text(result.msg);
                if (!result.sc) {
                    $("#loadMsg").text("");
                    return;
                }

                admin.followList.id = id;

                $("#followTitleUpdate").val(result.follow.followTitle);
                $("#followAddressUpdate").val(result.follow.followAddress);
                $("#followDescriptionUpdate").val(result.follow.followDescription);
                $("#followIconUpdate").val(result.follow.followIcon);

                $("#loadMsg").text("");
            }
        });
    },

    /*
     * 更新链接
     */
    update: function () {
        if (this.validate("Update")) {
            $("#loadMsg").text(Label.loadingLabel);
            $("#tipMsg").text("");
            var requestJSONObject = {
                "follow": {
                    "followTitle": $("#followTitleUpdate").val(),
                    "oId": this.id,
                    "followAddress": $("#followAddressUpdate").val(),
                    "followDescription": $("#followDescriptionUpdate").val(),
                    "followIcon": $("#followIconUpdate").val()
                }
            };

            $.ajax({
                url: Label.servePath + "/console/follow/",
                type: "PUT",
                cache: false,
                data: JSON.stringify(requestJSONObject),
                success: function (result, textStatus) {
                    $("#updateFollow").dialog("close");
                    $("#tipMsg").text(result.msg);
                    if (!result.sc) {
                        $("#loadMsg").text("");
                        return;
                    }

                    admin.followList.getList(admin.followList.pageInfo.currentPage);

                    $("#loadMsg").text("");
                }
            });
        }
    },

    /*
     * 删除链接
     * @id 链接 id
     * @title 链接标题
     */
    del: function (id, title) {
        var isDelete = confirm(Label.confirmRemoveLabel + Label.permafollowLabel + '"' + Util.htmlDecode(title) + '"?');
        if (isDelete) {
            $("#loadMsg").text(Label.loadingLabel);
            $("#tipMsg").text("");

            $.ajax({
                url: Label.servePath + "/console/follow/" + id,
                type: "DELETE",
                cache: false,
                success: function (result, textStatus) {
                    $("#tipMsg").text(result.msg);
                    if (!result.sc) {
                        $("#loadMsg").text("");
                        return;
                    }

                    var pageNum = admin.followList.pageInfo.currentPage;
                    if (admin.followList.pageInfo.currentCount === 1 && admin.followList.pageInfo.pageCount !== 1 &&
                        admin.followList.pageInfo.currentPage === admin.followList.pageInfo.pageCount) {
                        admin.followList.pageInfo.pageCount--;
                        pageNum = admin.followList.pageInfo.pageCount;
                    }

                    var hashList = window.location.hash.split("/");
                    if (pageNum !== parseInt(hashList[hashList.length - 1])) {
                        admin.setHashByPage(pageNum);
                    }

                    admin.followList.getList(pageNum);

                    $("#loadMsg").text("");
                }
            });
        }
    },

    /*
     * 验证字段
     * @status 更新或者添加时进行验证
     */
    validate: function (status) {
        if (!status) {
            status = "";
        }
        if ($("#followTitle" + status).val().replace(/\s/g, "") === "") {
            $("#tipMsg").text(Label.titleEmptyLabel);
            $("#followTitle" + status).focus().val("");
        } else if ($("#followAddress" + status).val().replace(/\s/g, "") === "") {
            $("#tipMsg").text(Label.addressEmptyLabel);
            $("#followAddress" + status).focus().val("");
        } else if (!/^\w+:\/\//.test($("#followAddress" + status).val())) {
            $("#tipMsg").text(Label.addressInvalidLabel);
            $("#followAddress" + status).focus().val("");
        } else {
            return true;
        }
        return false;
    },

    /*
     * 调换顺序
     */
    changeOrder: function (id, order, status) {
        $("#loadMsg").text(Label.loadingLabel);
        $("#tipMsg").text("");

        var requestJSONObject = {
            "oId": id.toString(),
            "direction": status
        };

        $.ajax({
            url: Label.servePath + "/console/follow/order/",
            type: "PUT",
            cache: false,
            data: JSON.stringify(requestJSONObject),
            success: function (result, textStatus) {
                $("#tipMsg").text(result.msg);

                // Refershes the follow list
                admin.followList.getList(admin.followList.pageInfo.currentPage);

                $("#loadMsg").text("");
            }
        });
    }
};

/*
 * 注册到 admin 进行管理 
 */
admin.register["follow-list"] = {
    "obj": admin.followList,
    "init": admin.followList.init,
    "refresh": admin.followList.getList
}