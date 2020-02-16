/*
 * Solo - A small and beautiful blogging system written in Java.
 * Copyright (c) 2010-present, b3log.org
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
 * @version 0.1.0.0, Feb 17, 2017
 */

/**
 * @description 皮肤脚本
 * @static
 */
function Emiya() {
  this.listen()
}

function initPageShare() {
  var $this = $('.J_share')
  var $qrCode = $('.J_share_wechat')
  var shareURL = $qrCode.data('url')
  var avatarURL = $qrCode.data('avatar')
  var title = encodeURIComponent($qrCode.data('title') + ' - ' +
    $qrCode.data('blogtitle')),
    url = encodeURIComponent(shareURL)

  var urls = {}
  urls.weibo = 'http://v.t.sina.com.cn/share/share.php?title=' + title + '&url=' + url + '&pic=' + avatarURL
  urls.qzone = 'https://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=' + url + '&sharesource=qzone&title=' + title + '&pics=' + avatarURL
  urls.twitter = 'https://twitter.com/intent/tweet?status=' + title + ' ' + url

  $this.on('click', function () {
    var key = $(this).data('type')

    if (!key) {
      return
    }

    if (key === 'wechat') {
      if ($qrCode.find('canvas').length === 0) {
        $.ajax({
          method: 'GET',
          url: Label.staticServePath +
          '/js/lib/jquery.qrcode.min.js',
          dataType: 'script',
          cache: true,
          success: function () {
            $qrCode.qrcode({
              width: 128,
              height: 128,
              text: shareURL,
            })
          },
        })
      } else {
        $qrCode.find('canvas').slideToggle()
      }
      return false
    }

    window.open(urls[key], '_blank', 'top=100,left=200,width=648,height=618')
  })
}

function initContents() {
  var priorities = {
    h1: 5,
    h2: 4,
    h3: 3,
    h4: 2,
    h5: 1,
  };
  var $titles = getArticleTitles();

  if ($titles.length === 0) {
    $('.J_article__contents').hide();
    return;
  }

  var tpl = '<ul class="article__contents">';
  var prevPriority;
  var firstPriority;

  $titles.each(function(index, dom) {
    var priority = priorities[dom.tagName.toLowerCase()];
    var title = $titles.eq(index).text();
    var item = '<li><a href="javascript:void(0);" data-target="#' + $titles.eq(index).attr('id') + '">' + title + '</a></li>';

    if (!priority) {
      return;
    }

    if (!firstPriority) {
      tpl += item;
      firstPriority = priority;
    } else {
      if (priority === prevPriority) {
        tpl += item;
      } else if (priority < prevPriority) {
        tpl += Array(prevPriority - priority).fill('<ul>').join('') + item;
      } else if (priority > prevPriority) {
        tpl += Array(priority - prevPriority).fill('</ul>').join('') + item;
      }
    }

    prevPriority = priority;
  });

  tpl += Array(firstPriority - prevPriority + 1).fill('</ul>').join('');
  $('.J_article__contents--container').append(tpl);
}

var getArticleTitles = (function() {
  var $titles = null;

  return function() {
    if ($titles !== null) { return $titles; }

    var $t = $('[id^=b3_solo_h]');
    $titles = $t;

    return $titles;
  }
})();

function ScrollManagerCreator(_now) {
  var nowScroll = _now
  var $nav = $('.J_navbar')
  var $backToTop = $('.toTop')
  var $contents = $('.J_article__contents')
  var $article = $('.J_article__content')
  var showBackToTopHeight = 100
  var checkContentHighlightId

  function checkFixed(nextScroll) {
    var offsetTop = $nav.height() + 1
    if (nextScroll > offsetTop) {
      $nav.addClass('is-fixed').css('top', -1 * offsetTop);
    } else if (nextScroll <= 0) {
      $nav.removeClass('is-fixed').css('top', 0);
    }

    if (!$nav.hasClass('is-fixed')) {
      return
    }

    if (nextScroll > nowScroll) {
      $nav.removeClass('show')
    } else {
      $nav.addClass('show')
    }
  }

  function checkBackToTop(nextScroll) {
    if(nextScroll > showBackToTopHeight) {
      $backToTop.fadeIn()
    } else {
      $backToTop.fadeOut()
    }
  }

  function checkContents(nextScroll) {
    if ($contents.length <= 0) {
      return
    }
    
    var $prev = $contents.prev()
    var contentsHeight = $contents.height();
    var contentsStaticTop = $prev.offset().top + $prev.height()
    var offsetTop = $nav.height() + 1
    var articleBottom = $article.offset().top + $article.height() - contentsHeight + offsetTop;

    if (contentsHeight + offsetTop > $(window).height()) {
      $contents.css('position', 'static');
      return;
    }

    if (nextScroll <= contentsStaticTop - offsetTop) {
      $contents.css('position', 'static');
    } else if (nextScroll > contentsStaticTop - offsetTop && nextScroll < articleBottom - offsetTop) {
      $contents.css({
        'position': 'fixed',
        'top': offsetTop,
      });
    } else {
      $contents.css({
        'position': 'absolute',
        'top': articleBottom,
      });
    }
  }

  function checkContentHighlight(nextScroll) {
    clearTimeout(checkContentHighlightId)

    checkContentHighlightId = setTimeout(function() {
      var offsetTop = $nav.height() + 1
      var $contentLink = $('.J_article__contents--container a')
      var nowIndex

      for (var i = 0; i < $contentLink.length; i++) {
        var target = $contentLink.eq(i).attr('data-target')
        if (nextScroll + offsetTop > $(target).offset().top) {
          nowIndex = i
        }
      }

      $contentLink.removeClass('active')
      $contentLink.eq(nowIndex).addClass('active')
    }, 50);
  }

  checkFixed(nowScroll)
  checkBackToTop(nowScroll)
  checkContents(nowScroll)
  checkContentHighlight(nowScroll)

  return function(nextScroll) {
    checkFixed(nextScroll)
    checkBackToTop(nextScroll)
    checkContents(nextScroll)
    checkContentHighlight(nextScroll)

    nowScroll = nextScroll
  }
}

function scrollTo(to) {
  $('html ,body').animate({
    scrollTop: to,
  }, 300);
}

var scrollManager = ScrollManagerCreator($(window).scrollTop())

Emiya.prototype.listen = function() {
  $(".J_navbar_toggle").on("click", function() {
    var targetClass = '.' + $(this).attr('data-for')
    if ($(targetClass).hasClass('show')) {
        $(targetClass).removeClass('show')
    } else {
        $(targetClass).addClass('show')
    }
  });
  $(".J_replyName").on('click', function() {
    var replyName = $(this).attr('data-originalId');
    $('#' + replyName)[0].scrollIntoViewIfNeeded(true);
    $('#' + replyName).addClass('blink');
    setTimeout(function() {
      $('#' + replyName).removeClass('blink');
    }, 3000);
  });
  $('.J_backToTop').on('click', function(e) {
    scrollTo(0);
    e.preventDefault();
  });
  $(window).on('scroll', function() {
    var nowScrollTop = $(window).scrollTop()
    scrollManager(nowScrollTop)
  });
  $('body').on('click', '.J_article__contents--container a', function() {
    var target = $(this).attr('data-target');
    var scrollTarget = $(target).offset().top - $('.J_navbar').height() + 1;
    scrollTo(scrollTarget);
  });
  $('body').on('touchstart', function() { });
}

Emiya.prototype.initArticle = function() {
  initPageShare();
  try {
    initContents();
  } catch (e) { console.error(e); }
}

window.Skin = new Emiya();
