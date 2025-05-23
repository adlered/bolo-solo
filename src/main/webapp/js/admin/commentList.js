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
 * comment list for admin
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 */

/* comment-list 相关操作 */
admin.commentList = {
  tablePagination: new TablePaginate('comment'),
  pageInfo: {
    currentPage: 1,
  },

  /*
   * 初始化 table, pagination, comments dialog
   */
  init: function (page) {
    this.tablePagination.buildTable([
      {
        text: Label.commentContentLabel,
        index: 'content',
        minWidth: 174,
        style: 'padding-left: 12px;',
      }, {
        text: Label.authorLabel,
        index: 'title',
        style: 'padding-left: 12px;',
        width: 154,
      }, {
        text: Label.dateLabel,
        index: 'date',
        width: 154,
        style: 'padding-left: 12px;',
      }])
    this.tablePagination.initPagination()
    this.getList(page)
  },

  /*
   * 根据当前页码获取列表
   * @pagNum 当前页码
   */
  getList: function (pageNum) {
    var that = this
    $('#loadMsg').text(Label.loadingLabel)

    $.ajax({
      url: Label.servePath + '/console/comments/' + pageNum + '/' +
      Label.PAGE_SIZE + '/' + Label.WINDOW_SIZE,
      type: 'GET',
      cache: false,
      success: function (result, textStatus) {
        $('#tipMsg').text(result.msg)
        if (!result.sc) {
          $('#loadMsg').text('')
          return
        }

        that.pageInfo.currentPage = pageNum
        var comments = result.comments,
          commentsData = []
        for (var i = 0; i < comments.length; i++) {
          var type = 'Article'
          commentsData[i] = {}
          commentsData[i].content = '<div class="vditor-reset">' +
            comments[i].commentContent +
            '</div><span class=\'table-tag\'> on &nbsp;&nbsp;</span><a href=\'' +
            Label.servePath + comments[i].commentSharpURL +
            '\' target=\'_blank\'>' + comments[i].commentTitle +
            '</a>'

          commentsData[i].expendRow = '<a href=\'javascript:void(0)\' onclick="admin.commentList.del(\'' +
            comments[i].oId + '\', \'' + type + '\')">' + Label.removeLabel +
            '</a>'

          commentsData[i].title = '<img class=\'small-head\' src=\'' +
            comments[i].commentThumbnailURL + '\'/>'
          if ('http://' === comments[i].commentURL) {
            commentsData[i].title += comments[i].commentName
          } else {
            commentsData[i].title += '<a href=\'' + comments[i].commentURL +
              '\' target=\'_blank\' class=\'no-underline\'>' +
              comments[i].commentName +
              '</a>'
          }
          commentsData[i].title += '<br/>'

          commentsData[i].date = $.bowknot.getDate(comments[i].commentTime)
        }

        that.tablePagination.updateTablePagination(commentsData, pageNum,
          result.pagination)

        Util.parseMarkdown()
        Util.parseLanguage()
        $('#loadMsg').text('')
      },
    })
  },

  /*
   * 删除评论
   * @id 评论 id
   * @type 评论类型：文章
   */
  del: function (id, type) {
    if (confirm(Label.confirmRemoveLabel + Label.commentLabel + '?')) {
      $('#loadMsg').text(Label.loadingLabel)

      $.ajax({
        url: Label.servePath + '/console/' + type.toLowerCase() +
        '/comment/' + id,
        type: 'DELETE',
        cache: false,
        success: function (result, textStatus) {
          $('#tipMsg').text(result.msg)
          if (!result.sc) {
            $('#loadMsg').text('')
            return
          }

          admin.commentList.getList(admin.commentList.pageInfo.currentPage)

          $('#loadMsg').text('')
        },
      })
    }
  },
}

/*
 * 注册到 admin 进行管理 
 */
admin.register['comment-list'] = {
  'obj': admin.commentList,
  'init': admin.commentList.init,
  'refresh': admin.commentList.getList,
}