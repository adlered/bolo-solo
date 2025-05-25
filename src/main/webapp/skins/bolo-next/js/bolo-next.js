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
 * @description next 皮肤脚本
 * @static
 */
var NexT = {
  init: function () {
    $('.sidebar-toggle').click(function () {
      var $sidebar = $('.sidebar');
      if ($(this).hasClass('sidebar-active')) {
        $(this).removeClass('sidebar-active');

        $('body').animate({
          'padding-right': 0
        });
        $sidebar.animate({
          right: -320
        });
        $sidebar.find('section').css('opacity', 0);
      } else {
        $(this).addClass('sidebar-active');
        $('body').animate({
          'padding-right': 320
        });
        $sidebar.animate({
          right: 0
        }, function () {
          $sidebar.find('section:first').animate({
            'opacity': 1
          });
        });
      }
    });

    $('.site-nav-toggle').click(function () {
      $('.site-nav').slideToggle();
    });

    $(document).ready(function () {
      setTimeout(function () {
        // logo animate
        $('.logo-wrap').css('opacity', 1);
        $('.logo-line-before i').animate({
          'left': '0'
        }, function () {
          $('.site-title').css('opacity', 1).animate({
            'top': 0
          }, function () {
            $('.menu').css('opacity', 1).animate({
              'margin-top': '15px'
            });
            $('.main').css('opacity', 1).animate({
              'top': '0'
            }, function () {
              // 当有文章页面有目录时，回调不放这里，侧边栏就会一片空白
              if ($('.article__toc li').length > 0 && $(window).width() > 1000) {
                $('.sidebar-toggle').click();
              }
            });
          });


        });

        $('.logo-line-after i').animate({
          'right': '0'
        });
      }, 500);
    });
  },
  initArticle: function () {
    if ($('.article__toc li').length > 0 && $(window).width() > 1000) {
      // add color to sidebar menu
      $('.sidebar-toggle').addClass('has-toc');
      this.initToc();
    }
  },
  initToc: function () {
    var $articleTocs = $('.vditor-reset [id^=b3_solo_h]'),
      $articleToc = $('.article__toc');

    $(window).scroll(function (event) {
      if ($('.article__toc li').length === 0) {
        return false;
      }

      // 界面各种图片加载会导致帖子目录定位
      var toc = [];
      $articleTocs.each(function (i) {
        toc.push({
          id: this.id,
          offsetTop: this.offsetTop
        });
      });

      // 当前目录样式
      var scrollTop = $(window).scrollTop();
      for (var i = 0, iMax = toc.length; i < iMax; i++) {
        if (scrollTop < toc[i].offsetTop + 200) {
          $articleToc.find('li').removeClass('current');
          var index = i > 0 ? i - 1 : 0;
          $articleToc.find('a[href="#' + toc[index].id + '"]').parent().addClass('current');
          break;
        }
      }
      if (scrollTop >= toc[toc.length - 1].offsetTop + 200) {
        $articleToc.find('li').removeClass('current');
        $articleToc.find('li:last').addClass('current');
      }
    });

    $(window).scroll();
  }
};
NexT.init();