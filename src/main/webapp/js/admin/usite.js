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
 * preference for admin.
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 */

var usiteJSON = {};

/* usite 相关操作 */
admin.usite = {
  locale: '',
  /*
   * 初始化
   */
  init: function () {
    $("#tabUsite").tabs();
    $('#loadMsg').text('');

    $.ajax({
      url: Label.servePath + '/admin/usite/get',
      type: 'GET',
      async: false,
      success: function (data) {
        if (data.msg === '') {
          usiteJSON = $.parseJSON('' +
            '\n' +
            '{\n' +
            '    "usiteUserId": "",\n' +
            '    "usiteResume": "",\n' +
            '    "usiteWeiBo": "",\n' +
            '    "usiteQQMusic": "",\n' +
            '    "usiteStackOverflow": "",\n' +
            '    "usiteDribbble": "",\n' +
            '    "usiteGitHub": "",\n' +
            '    "usiteMedium": "",\n' +
            '    "usiteTwitter": "",\n' +
            '    "usiteQQ": "",\n' +
            '    "usiteLinkedIn": "",\n' +
            '    "usiteSteam": "",\n' +
            '    "oId": "",\n' +
            '    "usiteInstagram": "",\n' +
            '    "usiteCodePen": "",\n' +
            '    "usiteWYMusic": "",\n' +
            '    "usiteWeChat": "",\n' +
            '    "usiteZhiHu": "",\n' +
            '    "usiteBehance": "",\n' +
            '    "usiteTelegram": "",\n' +
            '    "usiteFacebook": ""\n' +
            '}\n' +
            '\n');
        } else {
          usiteJSON = $.parseJSON(data.msg);
        }

        for (var i in usiteJSON) {
          $('#' + i).val(usiteJSON[i]);
        }
      }
    })
  },
  /*
   * @description 参数校验
   */
  validate: function () {
    return true
  },
  /*
   * @description 更新
   */
  update: function () {
    noBtnSwal("请稍候", 0);
    for (var i in usiteJSON) {
      let value = $('#' + i).val();
      if (value === undefined) {
        value = '';
      }
      usiteJSON[i] = value;
    }
    let newResult = JSON.stringify(usiteJSON);

    $.ajax({
      url: Label.servePath + '/admin/usite/set',
      type: 'POST',
      async: false,
      dataType: 'json',
      data: newResult,
      success: function (data) {
        if (data.code === 200) {
          noBtnSwal('联系方式元数据更新成功！', 1000);
        } else {
          noBtnSwal('联系方式元数据更新失败！请检查元数据内容后重试。', 1000);
        }
      }
    });
  }
}

/*
 * 注册到 admin 进行管理
 */
admin.register['usite'] = {
  'obj': admin.usite,
  'init': admin.usite.init,
  'refresh': function () {
    admin.clearTip()
  },
}
