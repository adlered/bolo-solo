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
 * @fileoverview util and every page should be used.
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 */

/**
 * @description 皮肤脚本
 * @static
 */
var Skin = {
  init: function () {
    Util.initPjax()
  },
  initArticle: function () {
    page.share()

    var $articleTocs = $('.vditor-reset [id^=b3_solo_h]')
    var $articleToc = $('.article__toc')
    var $articleProgress = $('.article__progress')

    if ($articleToc.length === 1) {
      if ($(window).width() > 876) {
        $('.post__toc').
          css('left', $('.article .item__content').offset().left +
            $('.article .item__content').outerWidth() - 80)
      } else {
        $('.post__toc a').click(function () {
          $('.post__toc').hide()
        })
      }
    }

    $articleToc.find('a').click(function (event) {
      var id = $(this).attr('href')
      window.location.hash = id
      $(window).scrollTop($(id).offset().top - 60)
      event.preventDefault()
      event.stopPropagation()
      return false
    })

    $(window).unbind('scroll').scroll(function (event) {
      if ($articleProgress.length === 0) {
        return false
      }

      $articleProgress.attr('value', parseInt($(window).scrollTop())).
        attr('max', parseInt($('body').outerHeight() -
          $(window).height()))

      if ($(window).scrollTop() > 236) {
        $('.article__top').css('top', 0)
      } else {
        $('.article__top').css('top', -61)
      }

      if ($('.article__toc li').length === 0) {
        return false
      }

      if ($(window).width() > 876) {
        if ($(window).scrollTop() > 975 && $(window).scrollTop() <
          $('.article').outerHeight() + 100) {
          $('.post__toc').show()
        } else {
          $('.post__toc').hide()
        }
      }

      // 界面各种图片加载会导致帖子目录定位
      var toc = []
      $articleTocs.each(function (i) {
        toc.push({
          id: this.id,
          offsetTop: $(this).offset().top,
        })
      })

      // 当前目录样式
      var scrollTop = $(window).scrollTop()
      for (var i = 0, iMax = toc.length; i < iMax; i++) {
        if (scrollTop < toc[i].offsetTop - 61) {
          $articleToc.find('li').removeClass('current')
          var index = i > 0 ? i - 1 : 0
          $articleToc.find('a[href="#' + toc[index].id + '"]').
            parent().
            addClass('current')
          break
        }
      }
      if (scrollTop >= toc[toc.length - 1].offsetTop - 61) {
        $articleToc.find('li').removeClass('current')
        $articleToc.find('li:last').addClass('current')
      }
    })

    $(window).scroll()
  },
}
Skin.init()