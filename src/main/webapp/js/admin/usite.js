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
 * preference for admin.
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.3.0.3, Aug 18, 2019
 */

// 格式化 JSON
var formatJson = function(json, options) {
  var reg = null,
      formatted = '',
      pad = 0,
      PADDING = '    '; // one can also use '\t' or a different number of spaces
  // optional settings
  options = options || {};
  // remove newline where '{' or '[' follows ':'
  options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
  // use a space after a colon
  options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;

  // begin formatting...

  // make sure we start with the JSON as a string
  if (typeof json !== 'string') {
    json = JSON.stringify(json);
  }
  // parse and stringify in order to remove extra whitespace
  json = JSON.parse(json);
  json = JSON.stringify(json);

  // add newline before and after curly braces
  reg = /([\{\}])/g;
  json = json.replace(reg, '\r\n$1\r\n');

  // add newline before and after square brackets
  reg = /([\[\]])/g;
  json = json.replace(reg, '\r\n$1\r\n');

  // add newline after comma
  reg = /(\,)/g;
  json = json.replace(reg, '$1\r\n');

  // remove multiple newlines
  reg = /(\r\n\r\n)/g;
  json = json.replace(reg, '\r\n');

  // remove newlines before commas
  reg = /\r\n\,/g;
  json = json.replace(reg, ',');

  // optional formatting...
  if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
    reg = /\:\r\n\{/g;
    json = json.replace(reg, ':{');
    reg = /\:\r\n\[/g;
    json = json.replace(reg, ':[');
  }
  if (options.spaceAfterColon) {
    reg = /\:/g;
    json = json.replace(reg, ': ');
  }

  $.each(json.split('\r\n'), function(index, node) {
    var i = 0,
        indent = 0,
        padding = '';

    if (node.match(/\{$/) || node.match(/\[$/)) {
      indent = 1;
    } else if (node.match(/\}/) || node.match(/\]/)) {
      if (pad !== 0) {
        pad -= 1;
      }
    } else {
      indent = 0;
    }

    for (i = 0; i < pad; i++) {
      padding += PADDING;
    }
    formatted += padding + node + '\r\n';
    pad += indent;
  });
  return formatted;
};

/* usite 相关操作 */
admin.usite = {
  locale: '',
  /*
   * 初始化
   */
  init: function () {
    $("#tabUsite").tabs();
    $('#loadMsg').text('');

    location.href = '#tools/usite/edit';

    $.ajax({
      url: Label.servePath + '/admin/usite/get',
      type: 'GET',
      async: false,
      success: function (data) {
        $('#usiteEditor').val(formatJson(data.msg));
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
    $.ajax({
      url: Label.servePath + '/admin/usite/set',
      type: 'POST',
      async: false,
      dataType: 'json',
      data: $('#usiteEditor').val(),
      success: function () {
        $('#loadMsg').text('联系方式元数据更新成功！');
        setTimeout(function () {
          $('#loadMsg').text('');
        }, 2000)
      }
    });
  },

  /*
   * @description 恢复至全空白状态
   */
  reset: function () {
    $('#usiteEditor').val('' +
        '\n' +
        '{\n' +
        '    "usiteUserId": "",\n' +
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
