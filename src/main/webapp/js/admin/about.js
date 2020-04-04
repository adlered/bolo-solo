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
 *  about for admin
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.0.0, Jul 17, 2019
 */

/* about 相关操作 */
admin.about = {
  init: function () {
    $.ajax({
      url: 'https://rhythm.b3log.org/version/solo/latest/' + Label.version,
      type: 'GET',
      cache: false,
      dataType: 'jsonp',
      success: function (data, textStatus) {
        var version = data.soloVersion
        if (version === Label.version) {
          $('#aboutLatest').text(Label.upToDateLabel)
        } else {
          $('#aboutLatest').html(Label.outOfDateLabel +
            '<a href=\'' + data.soloDownload + '\'>' + version + '</a>')
        }
      },
      complete: function (XHR, TS) {
        admin.clearTip()
      },
    })

    $.ajax({
      url: 'https://hacpai.com/apis/sponsors',
      type: 'GET',
      dataType: 'jsonp',
      jsonp: 'callback',
      success: function (data, textStatus) {
        var payments = data.data.payments
        var sponsprsHTML = ''
        for (var i = 0; i < payments.length; i++) {
          var userName = '<b>匿名好心人</b>'
          if (payments[i].paymentUserName) {
            userName = '<a href="https://hacpai.com/member/' +
              payments[i].paymentUserName + '"><b>' + payments[i].paymentUserName +
              '</b></a>'
          }
          sponsprsHTML += '<li><div class="fn__flex">' + userName + ' <span class="ft__green fn__flex-1">&nbsp;' +
            payments[i].paymentAmount + 'RMB</span><time class="ft__fade">&nbsp;&nbsp;' +
            payments[i].paymentTimeStr + '</time></div><div>' +
            payments[i].paymentMemo + '</div></li>'
        }
        $('#adminAboutSponsors').html(sponsprsHTML)
      },
      complete: function (XHR, TS) {
        admin.clearTip()
      },
    })

    // 版本检查
    version = version.split(" ")[0];
    version = version.substr(1);
    version = version.replace('.', "_");
    var filename = 'bolo_v' + version + '_stable.zip';
    var url = 'https://ftp.stackoverflow.wiki/bolo/releases/' + filename;
    console.info('当前版本校验中：' + url);
    var finalVersion = version;
    // 当前版本校验
    $.ajax({
      url: url,
      type: 'head',
      error: function () {
        $('#updateCheck').html('<font style="color: #e94c89">版本更新检查失败，网络错误或该版本为内测版本。</font>');
      },
      success: function () {
        console.info('当前版本在数据库中存在！查询新版本...');
        var index = version.indexOf('_');
        var sVersion = String(version.replace('_', ''));
        console.info('版本分隔符位置：' + index + '; 版本序列号：' + sVersion);
        // 记录字符串长度，灵活修改分隔符位置
        var len = sVersion.length;
        var hasNext = true;
        // 遍历生成
        while (hasNext) {
          sVersion = String(Number(sVersion) + 1);
          var newLen = sVersion.length;
          if (len !== newLen) {
            index++;
          }
          len = newLen;
          console.info('裸版本号：' + sVersion);
          var head = sVersion.substr(0, index);
          var foot = sVersion.substr(index);
          var rollback = head + '_' + foot;
          console.info('查询号：' + rollback);
          filename = 'bolo_v' + rollback + '_stable.zip';
          url = 'https://ftp.stackoverflow.wiki/bolo/releases/' + filename;
          console.info('查询版本仓库：' + url);
          $.ajax({
            url: url,
            type: 'head',
            async: false,
            error: function () {
              console.info('版本 v' + rollback.replace('_', '.') + ' 不存在，停止遍历');
              hasNext = false;
              $('#loadMsg').text('')
            },
            success: function () {
              finalVersion = rollback;
              console.info('版本 v' + rollback.replace('_', '.') + ' 存在，继续查询下一个...');
            }
          });
        }
        if (version === finalVersion) {
          $('#updateCheck').html('<font style="color: #3caf36">你正在使用菠萝博客最新版 :)</font>');
        } else {
          $('#updateCheck').html('<font style="color: #991a1a">菠萝博客 v' + finalVersion.replace('_', '.') + ' 已推出，赶快更新吧！</font>');
        }
      }
    })
  },
}

/*
 * 注册到 admin 进行管理 
 */
admin.register['about'] = {
  'obj': admin.about,
  'init': admin.about.init,
  'refresh': function () {
    admin.clearTip()
  },
}