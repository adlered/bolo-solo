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
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 */

/* preference 相关操作 */
admin.preference = {
  locale: '',
  editorMode: '',
  /*
   * 初始化
   */
  init: function () {
    $('#tabPreference').tabs()

    $.ajax({
      url: Label.servePath + '/console/preference/',
      type: 'GET',
      cache: false,
      success: function (result, textStatus) {
        $('#tipMsg').text(result.msg)
        if (!result.sc) {
          $('#loadMsg').text('')
          return
        }

        var preference = result.preference

        $.ajax({
          url: Label.servePath + '/plugins/kanbanniang/assets/list',
          type: 'GET',
          async: false,
          success: function(res) {
            var kanbanniangList = res.msg.split(';');
            for (var i = 0; i < kanbanniangList.length; i++) {
              $('#kanbanniangSelector').append(
                  '<option value="' + kanbanniangList[i] + '">' + kanbanniangList[i] + '</option>'
              );
            }
          }
        });

        if (preference.wafCurrentLimitTimes === "" || preference.wafCurrentLimitTimes === undefined) {
          $("#wafCurrentLimitTimes").val("180")
        } else {
          $("#wafCurrentLimitTimes").val(preference.wafCurrentLimitTimes)
        }

        if (preference.wafCurrentLimitSecond === "" || preference.wafCurrentLimitSecond === undefined) {
          $("#wafCurrentLimitSecond").val("180")
        } else {
          $("#wafCurrentLimitSecond").val(preference.wafCurrentLimitSecond)
        }

        if (preference.wafPower === "" || preference.wafPower === undefined) {
          $("#wafPower").val("on")
        } else {
          $("#wafPower").val(preference.wafPower)
        }

        if (preference.interactive === "" || preference.interactive === undefined) {
          $("#interactiveSwitch").val("on")
        } else {
          $("#interactiveSwitch").val(preference.interactive)
        }

        if(preference.adminActiveSentToMailbox === "" || preference.adminActiveSentToMailbox === undefined) {
          $("#adminActiveSentToMailbox").val("on")
        } else {
          $("#adminActiveSentToMailbox").val(preference.adminActiveSentToMailbox)
        }

        $('#sendKey').val(preference.sendKey)
        $('#spam').val(preference.spam)
        $('#kanbanniangSelector').val(preference.kanbanniangSelector)
        $('#replyRemind').val(preference.replyRemind)
        $('#sourceTC').text(preference.tuChuangConfig)
        sltd = $('#sourceTC').text().split('<<>>')[0]
        $('#tcS').val(sltd)
        $('#hacpaiUser').val(preference.hacpaiUser)
        $('#b3logKey').val(preference.b3logKey)
        $('#fishpiKey').val(preference.fishKey)
        $('#mailBox').val(preference.mailBox)
        $('#mailUsername').val(preference.mailUsername)
        $('#mailPassword').val(preference.mailPassword)
        $('#metaKeywords').val(preference.metaKeywords)
        $('#metaDescription').val(preference.metaDescription)
        $('#blogTitle').val(preference.blogTitle)
        $('#blogSubtitle').val(preference.blogSubtitle)
        $('#mostCommentArticleDisplayCount').
        val(preference.mostCommentArticleDisplayCount)
        $('#mostViewArticleDisplayCount').
        val(preference.mostViewArticleDisplayCount)
        $('#recentCommentDisplayCount').
        val(preference.recentCommentDisplayCount)
        $('#mostUsedTagDisplayCount').val(preference.mostUsedTagDisplayCount)
        $('#articleListDisplayCount').val(preference.articleListDisplayCount)
        $('#articleListPaginationWindowSize').
        val(preference.articleListPaginationWindowSize)
        $('#localeString').val(preference.localeString)
        $('#timeZoneId').val(preference.timeZoneId)
        $('#noticeBoard').val(preference.noticeBoard)
        $('#footerContent').val(preference.footerContent)
        $('#htmlHead').val(preference.htmlHead)
        $('#relevantArticlesDisplayCount').
        val(preference.relevantArticlesDisplayCount)
        $('#randomArticlesDisplayCount').
        val(preference.randomArticlesDisplayCount)
        $('#customVars').val(preference.customVars)
        $('#githubPAT').val(preference.githubPAT)
        $('#maxArchive').val(preference.maxArchive)
        $('#myGitHubID').val(preference.myGitHubID)

        'true' === preference.enableAutoFlushGitHub ? $('#enableAutoFlushGitHub').attr('checked', 'checked') : $('#enableAutoFlushGitHub').removeAttr('checked')
        'true' === preference.showCodeBlockLn ? $('#showCodeBlockLn').attr('checked', 'checked') : $('#showCodeBlockLn').removeAttr('checked')
        'true' === preference.enableArticleUpdateHint ? $('#enableArticleUpdateHint').attr('checked', 'checked') : $('#enableArticleUpdateHint').removeAttr('checked')
        'true' === preference.allowVisitDraftViaPermalink ? $('#allowVisitDraftViaPermalink').attr('checked', 'checked') : $('allowVisitDraftViaPermalink').removeAttr('checked')
        'true' === preference.commentable ? $('#commentable').attr('checked', 'checked') : $('commentable').removeAttr('checked')
        'true' === preference.syncGitHub ? $('#syncGitHub').attr('checked', 'checked') : $('syncGitHub').removeAttr('checked')
        'true' === preference.pullGitHub ? $('#pullGitHub').attr('checked', 'checked') : $('pullGitHub').removeAttr('checked')
        'true' === preference.welfareLuteService ? $('#welfareLuteService').attr('checked', 'checked') : $('#welfareLuteService').removeAttr('checked')
        'true' === preference.helpImprovePlan ? $('#helpImprovePlan').attr('checked', 'checked') : $('#helpImprovePlan').removeAttr('checked')

        $("input:radio[value='" + preference.editorMode + "']").attr('checked','true');
        admin.preference.editorMode = preference.editorMode

        admin.preference.locale = preference.localeString

        // sign
        var signs = eval('(' + preference.signs + ')')
        for (var j = 1; j < signs.length; j++) {
          $('#preferenceSign' + j).val(signs[j].signHTML)
        }

        $('#articleListDisplay').val(preference.articleListStyle)
        $('#hljsTheme').val(preference.hljsTheme)
        $('#feedOutputMode').val(preference.feedOutputMode)
        $('#feedOutputCnt').val(preference.feedOutputCnt)
        $('#imageUploadCompress').val(preference.imageUploadCompress)
        $('#thumbCompress').val(preference.thumbCompress)
        $('#faviconURL').val(preference.faviconURL)

        $('#loadMsg').text('')
      },
    })
  },
  /*
   * @description 参数校验
   */
  validate: function () {
    if (!/^\d+$/.test($('#mostUsedTagDisplayCount').val())) {
      $('#tipMsg').
      text('[' + Label.paramSettingsLabel + ' - ' +
          Label.indexTagDisplayCntLabel + '] ' +
          Label.nonNegativeIntegerOnlyLabel)
      $('#mostUsedTagDisplayCount').focus()
      return false
    } else if (!/^\d+$/.test($('#recentCommentDisplayCount').val())) {
      $('#tipMsg').
      text('[' + Label.paramSettingsLabel + ' - ' +
          Label.indexRecentCommentDisplayCntLabel + '] ' +
          Label.nonNegativeIntegerOnlyLabel)
      $('#recentCommentDisplayCount').focus()
      return false
    } else if (!/^\d+$/.test($('#mostCommentArticleDisplayCount').val())) {
      $('#tipMsg').
      text('[' + Label.paramSettingsLabel + ' - ' +
          Label.indexMostCommentArticleDisplayCntLabel + '] ' +
          Label.nonNegativeIntegerOnlyLabel)
      $('#mostCommentArticleDisplayCount').focus()
      return false
    } else if (!/^\d+$/.test($('#mostViewArticleDisplayCount').val())) {
      $('#tipMsg').
      text('[' + Label.paramSettingsLabel + ' - ' +
          Label.indexMostViewArticleDisplayCntLabel + '] ' +
          Label.nonNegativeIntegerOnlyLabel)
      $('#mostViewArticleDisplayCount').focus()
      return false
    } else if (!/^\d+$/.test($('#articleListDisplayCount').val())) {
      $('#tipMsg').
      text('[' + Label.paramSettingsLabel + ' - ' + Label.pageSizeLabel +
          '] ' + Label.nonNegativeIntegerOnlyLabel)
      $('#articleListDisplayCount').focus()
      return false
    } else if (!/^\d+$/.test($('#articleListPaginationWindowSize').val())) {
      $('#tipMsg').
      text('[' + Label.paramSettingsLabel + ' - ' + Label.windowSizeLabel +
          '] ' + Label.nonNegativeIntegerOnlyLabel)
      $('#articleListPaginationWindowSize').focus()
      return false
    } else if (!/^\d+$/.test($('#randomArticlesDisplayCount').val())) {
      $('#tipMsg').
      text('[' + Label.paramSettingsLabel + ' - ' +
          Label.randomArticlesDisplayCntLabel + '] ' +
          Label.nonNegativeIntegerOnlyLabel)
      $('#randomArticlesDisplayCount').focus()
      return false
    } else if (!/^\d+$/.test($('#relevantArticlesDisplayCount').val())) {
      $('#tipMsg').
      text('[' + Label.paramSettingsLabel + ' - ' +
          Label.relevantArticlesDisplayCntLabel + '] ' +
          Label.nonNegativeIntegerOnlyLabel)
      $('#relevantArticlesDisplayCount').focus()
      return false
    } else if (
        (!/^\d+$/.test($('#wafCurrentLimitTimes').val())) || $('#wafCurrentLimitTimes').val() < 2 || $('#wafCurrentLimitTimes').val() > 2147483647 ||
        (!/^\d+$/.test($('#wafCurrentLimitSecond').val())) || $('#wafCurrentLimitSecond').val() < 2 || $('#wafCurrentLimitSecond').val() > 2147483647
    ) {
      $('#tipMsg').text('访问频率次数与时间必须在 2-2147483647 之间!')
      return false
    }
    return true
  },
  /*
   * @description 更新
   */
  update: function () {
    if (!admin.preference.validate()) {
      return
    }

    noBtnSwal("请稍候", 0);
    $('#tipMsg').text('')
    $('#loadMsg').text('')
    var signs = [
      {
        'oId': 0,
        'signHTML': '',
      }, {
        'oId': 1,
        'signHTML': $('#preferenceSign1').val(),
      }, {
        'oId': 2,
        'signHTML': $('#preferenceSign2').val(),
      }, {
        'oId': 3,
        'signHTML': $('#preferenceSign3').val(),
      }]

    if ($("#interactiveSwitch").val() === null) {
      $("#interactiveSwitch").val("on");
    }

    var requestJSONObject = {
      'preference': {
        'helpImprovePlan': $("#helpImprovePlan").prop('checked'),
        'enableAutoFlushGitHub': $("#enableAutoFlushGitHub").prop('checked'),
        'welfareLuteService': $("#welfareLuteService").prop('checked'),
        'sendKey': $("#sendKey").val(),
        'myGitHubID': $("#myGitHubID").val(),
        'adminActiveSentToMailbox': $("#adminActiveSentToMailbox").val(),
        'wafCurrentLimitTimes': $("#wafCurrentLimitTimes").val(),
        'wafCurrentLimitSecond': $("#wafCurrentLimitSecond").val(),
        'wafPower': $("#wafPower").val(),
        'interactive': $("#interactiveSwitch").val(),
        'spam': $('#spam').val(),
        'kanbanniangSelector': $('#kanbanniangSelector').val(),
        'replyRemind': $('#replyRemind').val(),
        'tuChuangConfig': $('#sourceTC').text(),
        'hacpaiUser': $('#hacpaiUser').val(),
        'b3logKey': $('#b3logKey').val(),
        'fishKey': $('#fishpiKey').val(),
        'mailBox': $('#mailBox').val(),
        'mailUsername': $('#mailUsername').val(),
        'mailPassword': $('#mailPassword').val(),
        'metaKeywords': $('#metaKeywords').val(),
        'metaDescription': $('#metaDescription').val(),
        'blogTitle': $('#blogTitle').val(),
        'blogSubtitle': $('#blogSubtitle').val(),
        'mostCommentArticleDisplayCount': $('#mostCommentArticleDisplayCount').val(),
        'mostViewArticleDisplayCount': $('#mostViewArticleDisplayCount').val(),
        'recentCommentDisplayCount': $('#recentCommentDisplayCount').val(),
        'mostUsedTagDisplayCount': $('#mostUsedTagDisplayCount').val(),
        'articleListDisplayCount': $('#articleListDisplayCount').val(),
        'articleListPaginationWindowSize': $('#articleListPaginationWindowSize').val(),
        'localeString': $('#localeString').val(),
        'timeZoneId': $('#timeZoneId').val(),
        'noticeBoard': $('#noticeBoard').val(),
        'footerContent': $('#footerContent').val(),
        'htmlHead': $('#htmlHead').val(),
        'relevantArticlesDisplayCount': $('#relevantArticlesDisplayCount').val(),
        'randomArticlesDisplayCount': $('#randomArticlesDisplayCount').val(),
        'enableArticleUpdateHint': $('#enableArticleUpdateHint').prop('checked'),
        'signs': signs,
        'allowVisitDraftViaPermalink': $('#allowVisitDraftViaPermalink').prop('checked'),
        'articleListStyle': $('#articleListDisplay').val(),
        'hljsTheme': $('#hljsTheme').val(),
        'feedOutputMode': $('#feedOutputMode').val(),
        'feedOutputCnt': $('#feedOutputCnt').val(),
        'imageUploadCompress': $('#imageUploadCompress').val(),
        'thumbCompress': $('#thumbCompress').val(),
        'faviconURL': $('#faviconURL').val(),
        'syncGitHub': $('#syncGitHub').prop('checked'),
        'showCodeBlockLn': $('#showCodeBlockLn').prop('checked'),
        'pullGitHub': $('#pullGitHub').prop('checked'),
        'commentable': $('#commentable').prop('checked'),
        'customVars': $('#customVars').val(),
        'githubPAT': $('#githubPAT').val(),
        'maxArchive': $('#maxArchive').val(),
        'editorMode': $("input[name='editorMode']:checked").val(),
      },
    }

    $.ajax({
      url: Label.servePath + '/console/preference/',
      type: 'PUT',
      cache: false,
      data: JSON.stringify(requestJSONObject),
      success: function (result, textStatus) {
        $('#tipMsg').text(result.msg)
        if (!result.sc) {
          $('#loadMsg').text('')
          return
        }

        if ($('#localeString').val() !== admin.preference.locale || $("input[name='editorMode']:checked").val() !== admin.preference.editorMode) {
          window.location.reload()
        }

        window.location.reload()
      },
    })
  },
}

/*
 * 注册到 admin 进行管理
 */
admin.register['preference'] = {
  'obj': admin.preference,
  'init': admin.preference.init,
  'refresh': function () {
    admin.clearTip()
  },
}
