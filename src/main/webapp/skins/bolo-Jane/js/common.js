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
  initToc: function () {
    if ($('.article__toc li').length > 0 && $(window).width() > 768) {
      $('.article__toc').css({
        right: '50px',
        'border-right': '1px solid #fff',
        opacity: 1,
      })
      $('#pjax.wrapper').css({
        'max-width': '968px',
        'padding-right': '270px',
      })
    } else {
      $('#pjax.wrapper').removeAttr('style')
    }
  },
  init: function () {
    Skin.initToc()
    Util.initPjax(function () {
      $('.header a').each(function () {
        if (this.href === location.href) {
          this.className = 'current vditor-tooltipped vditor-tooltipped__w'
        } else {
          this.className = 'vditor-tooltipped vditor-tooltipped__w'
        }
      })

      Skin.initToc()
    })

    $('.header a').each(function () {
      if (this.href === location.href) {
        this.className = 'current vditor-tooltipped vditor-tooltipped__w'
      }
    }).click(function () {
      $('.header a').removeClass('current')
      this.className = 'current vditor-tooltipped vditor-tooltipped__w'
    })
  },
}
Skin.init()