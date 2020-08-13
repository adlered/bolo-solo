/*
 * Bolo - A stable and beautiful blogging system based in Solo.
 * Copyright (c) 2020, https://github.com/adlered
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
 * others for admin.
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 */

/* others 相关操作 */
var last = -1;
admin.others = {
  /*
   * @description 初始化
   */
  init: function () {
    $("#tabOthers").tabs();
    $('#loadMsg').text('');

    admin.others.getLog();
    setInterval(this.getLog, 5000);
  },

  getLog: () => {
    $.ajax({
      url: Label.servePath + '/admin/logs',
      cache: false,
      timeout: 2000,
      success: function (result) {
        var json = result.result;
        let free = (result.freeMemNow / (1024 * 1024)).toFixed(2) + ' MB';
        $('#memFree').html(free);
        $('#now').html(new Date().toLocaleTimeString());
        for (var i = 0; i < json.length; i++) {
          var r = json[i];
          var rId = r.id;
          if (rId > last) {
            var rDate = r.date;
            var rLevel = r.level;
            var rSrc = r.name + ':' + r.lineNumber;
            var msg = r.message;
            let buildStart = '<tbody class="table-oddRow"><tr class="table-hasExpend">';
            rDate = rDate.substring(0, 19);
            let build1 = '';
            if (rLevel === 'WARN') {
              build1 = '<td style="width: 40px; vertical-align: top"><span style="color: #f8ba0b; font-weight: bold">' + rLevel + '</span></td>';
            } else if (rLevel === 'INFO') {
              build1 = '<td style="width: 40px; vertical-align: top"><span style="color: #00bbff; font-weight: bold">' + rLevel + '</span></td>';
            } else if (rLevel === 'ERROR') {
              build1 = '<td style="width: 40px; vertical-align: top"><span style="color: #dd1144" font-weight: bold">' + rLevel + '</span></td>';
            } else {
              build1 = '<td style="width: 40px; vertical-align: top"><span style="color: #1ea0c3" font-weight: bold">' + rLevel + '</span></td>';
            }
            let build2 = '<td style="width: 135px; vertical-align: top"><span style="color: #4caf50; font-weight: bold">' + rDate + '</span></td>';
            let build3 = '<td style="vertical-align: top"><span style="word-wrap: break-word; white-space: normal; word-break: break-all"><span style="color: #4caf50; font-weight: bold">' + rSrc + '</span><br>' + msg + '</span></td>';
            let buildEnd = '</tr></tbody>';
            var res = buildStart + build1 + build2 + build3 + buildEnd;
            if (r.throwable !== undefined) {
              res += r.throwable.class + ': ' + r.throwable.message + '<br>';
              for (var j = 0; j < r.throwable.stackTrace.length; j++) {
                res += r.throwable.stackTrace[j] + '<br>';
              }
            }
            $('#tabOthersPanel_log #logList').prepend(res);
            last = rId;
          }
        }
      }
    });
  },

  /*
   * @description 移除未使用的存档
   */
  removeUnusedArchives: function () {
    $("#tipMsg").text("");

    $.ajax({
      url: Label.servePath + "/console/archive/unused",
      type: "DELETE",
      cache: false,
      success: function (result, textStatus) {
        $("#tipMsg").text(result.msg);
      }
    });
  },
  /*
   * @description 移除未使用的标签
   */
  removeUnusedTags: function () {
    $("#tipMsg").text("");

    $.ajax({
      url: Label.servePath + "/console/tag/unused",
      type: "DELETE",
      cache: false,
      success: function (result, textStatus) {
        $("#tipMsg").text(result.msg);
      }
    });
  },
  /*
   * @description 导出数据为 SQL 文件
   */
  exportSQL: function () {
    $("#tipMsg").text("");

    $.ajax({
      url: Label.servePath + "/console/export/sql",
      type: "GET",
      cache: false,
      success: function (result, textStatus) {
        // AJAX 下载文件的话这里会发两次请求，用 sc 来判断是否是文件，如果没有 sc 说明文件可以下载（实际上就是 result）
        if (!result.sc) {
          // 再发一次请求进行正式下载
          window.location = Label.servePath + "/console/export/sql";
        } else {
          $("#tipMsg").text(result.msg);
        }
      }
    });
  },
  /*
 * @description 导出数据为 JSON 文件
 */
  exportJSON: function () {
    $("#tipMsg").text("");

    $.ajax({
      url: Label.servePath + "/console/export/json",
      type: "GET",
      cache: false,
      success: function (result, textStatus) {
        // AJAX 下载文件的话这里会发两次请求，用 sc 来判断是否是文件，如果没有 sc 说明文件可以下载（实际上就是 result）
        if (!result.sc) {
          // 再发一次请求进行正式下载
          window.location = Label.servePath + "/console/export/json";
        } else {
          $("#tipMsg").text(result.msg);
        }
      }
    });
  },
  /*
  * @description 导出数据为 Hexo Markdown 文件
  */
  exportHexo: function () {
    $("#tipMsg").text("");

    $.ajax({
      url: Label.servePath + "/console/export/hexo",
      type: "GET",
      cache: false,
      success: function (result, textStatus) {
        // AJAX 下载文件的话这里会发两次请求，用 sc 来判断是否是文件，如果没有 sc 说明文件可以下载（实际上就是 result）
        if (!result.sc) {
          // 再发一次请求进行正式下载
          window.location = Label.servePath + "/console/export/hexo";
        } else {
          $("#tipMsg").text(result.msg);
        }
      }
    });
  },
  /*
   * 获取未使用的标签。
   * XXX: Not used this function yet.
   */
  getUnusedTags: function () {
    $.ajax({
      url: Label.servePath + "/console/tag/unused",
      type: "GET",
      cache: false,
      success: function (result, textStatus) {
        $("#tipMsg").text(result.msg);
        if (!result.sc) {
          $("#loadMsg").text("");
          return;
        }

        var unusedTags = result.unusedTags;
        if (0 === unusedTags.length) {
          return;
        }
      }
    });
  }
};

/*
 * 注册到 admin 进行管理 
 */
admin.register.others = {
  "obj": admin.others,
  "init": admin.others.init,
  "refresh": function () {
    admin.clearTip();
  }
};
