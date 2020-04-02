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
//animate
(function () {
    function $() {
        return Array.prototype.slice.call(document.querySelectorAll.apply(document, arguments));
    }

    $('body > .navbar, body > .section, body > .footer').forEach(function (element) {
        element.style.transition = '0s';
        element.style.opacity = '0';
    });
    document.querySelector('body > .navbar').style.transform = 'translateY(-100px)';
    ['.column-main > .card',
     '.column-left > .card, .column-right-shadow > .card',
     '.column-right > .card'].map(function (selector) {
        $(selector).forEach(function (element) {
            element.style.transition = '0s';
            element.style.opacity = '0';
            element.style.transform = 'scale(0.8)';
            element.style.transformOrigin = 'center top';
        });
    });
    setTimeout(function () {
        $('body > .navbar, body > .section, body > .footer').forEach(function (element) {
            element.style.opacity = '1';
            element.style.transition = 'opacity 0.3s ease-out, transform 0.3s ease-out';
        });
        document.querySelector('body > .navbar').style.transform = 'translateY(0)';
        ['.column-main > .card',
         '.column-left > .card, .column-right-shadow > .card',
         '.column-right > .card'].map(function (selector) {
            var i = 1;
            $(selector).forEach(function (element) {
                setTimeout(function () {
                    element.style.opacity = '1';
                    element.style.transform = '';
                    element.style.transition = 'opacity 0.3s ease-out, transform 0.3s ease-out';
                }, i * 100);
                i++;
            });
        });
    });
})();

  //burger
  (function () {
    var burger = document.querySelector('.burger');
    var menu = document.querySelector('#' + burger.dataset.target);
    burger.addEventListener('click', function () {
        burger.classList.toggle('is-active');
        menu.classList.toggle('is-active');
    });

})();

//backtotop
$(document).ready(function () {
    var $button = $('#back-to-top');
    var $footer = $('footer.footer');
    var $mainColumn = $('.column-main');
    var $leftSidebar = $('.column-left');
    var $rightSidebar = $('.column-right');
    var lastScrollTop = 0;
    var rightMargin = 20;
    var bottomMargin = 20;
    var lastState = null;
    var state = {
        base: {
            classname: 'card has-text-centered',
            left: '',
            width: 64,
            bottom: bottomMargin,
            'border-radius': 4
        }
    };
    state['desktop-hidden'] = Object.assign({}, state.base, {
        classname: state.base.classname + ' rise-up',
    });
    state['desktop-visible'] = Object.assign({}, state['desktop-hidden'], {
        classname: state['desktop-hidden'].classname + ' fade-in',
    });
    state['desktop-dock'] = Object.assign({}, state['desktop-visible'], {
        classname: state['desktop-visible'].classname + ' fade-in',
        width: 40,
        'border-radius': '50%'
    });
    state['mobile-hidden'] = Object.assign({}, state.base, {
        classname: state.base.classname + ' fade-in',
        right: rightMargin
    });
    state['mobile-visible'] = Object.assign({}, state['mobile-hidden'], {
        classname: state['mobile-hidden'].classname + ' rise-up',
    });

    function isStateEquals(prev, next) {
        for (var prop in prev) {
            if (!next.hasOwnProperty(prop) || next[prop] !== prev[prop]) {
                return false;
            }
        }
        for (var prop in next) {
            if (!prev.hasOwnProperty(prop) || prev[prop] !== prev[prop]) {
                return false;
            }
        }
        return true;
    }

    function applyState(state) {
        if (lastState !== null && isStateEquals(lastState, state)) {
            return;
        }
        $button.attr('class', state.classname);
        for (let prop in state) {
            if (prop === 'classname') {
                continue;
            }
            $button.css(prop, state[prop]);
        }
        lastState = state;
    }

    function isDesktop() {
        return window.innerWidth >= 1078;
    }

    function isTablet() {
        return window.innerWidth >= 768 && !isDesktop();
    }

    function isScrollUp() {
        return $(window).scrollTop() < lastScrollTop && $(window).scrollTop() > 0;
    }

    function hasLeftSidebar() {
        return $leftSidebar.length > 0;
    }

    function hasRightSidebar() {
        return $rightSidebar.length > 0;
    }

    function getRightSidebarBottom() {
        if (!hasRightSidebar()) {
            return 0;
        }
        return Math.max.apply(null, $rightSidebar.find('.widget').map(function () {
            return $(this).offset().top + $(this).outerHeight(true);
        }));
    }

    function getScrollTop() {
        return $(window).scrollTop();
    }

    function getScrollBottom() {
        return $(window).scrollTop() + $(window).height();
    }

    function getButtonWidth() {
        return $button.outerWidth(true);
    }

    function getButtonHeight() {
        return $button.outerHeight(true);
    }

    function updateScrollTop() {
        lastScrollTop = $(window).scrollTop();
    }

    function update() {
        // desktop mode or tablet mode with only right sidebar enabled
        if (isDesktop() || (isTablet() && !hasLeftSidebar() && hasRightSidebar())) {
            var nextState;
            var padding = ($mainColumn.outerWidth() - $mainColumn.width()) / 2;
            var maxLeft = $(window).width() - getButtonWidth() - rightMargin;
            var maxBottom = $footer.offset().top + getButtonHeight() / 2 + bottomMargin;
            if (getScrollTop() == 0 || getScrollBottom() < getRightSidebarBottom() + padding + getButtonHeight()) {
                nextState = state['desktop-hidden'];
            } else if (getScrollBottom() < maxBottom) {
                nextState = state['desktop-visible'];
            } else {
                nextState = Object.assign({}, state['desktop-dock'], {
                    bottom: getScrollBottom() - maxBottom + bottomMargin
                });
            }

            var left = $mainColumn.offset().left + $mainColumn.outerWidth() + padding;
            nextState = Object.assign({}, nextState, {
                left: Math.min(left, maxLeft)
            });
            applyState(nextState);
        } else {
            // mobile and tablet mode
            if (!isScrollUp()) {
                applyState(state['mobile-hidden']);
            } else {
                applyState(state['mobile-visible']);
            }
            updateScrollTop();
        }
    }

    update();
    $(window).resize(update);
    $(window).scroll(update);

    $('#back-to-top').on('click', function () {
        $('body, html').animate({ scrollTop: 0 }, 400);
    });
});

//gallery
document.addEventListener('DOMContentLoaded', function () {
    if (typeof ($.fn.lightGallery) === 'function') {
        $('.article').lightGallery({ selector: '.gallery-item' });
    }
    if (typeof ($.fn.justifiedGallery) === 'function') {
        if ($('.justified-gallery > p > .gallery-item').length) {
            $('.justified-gallery > p > .gallery-item').unwrap();
        }
        $('.justified-gallery').justifiedGallery();
    }
});

//main
(function ($) {
    //alert();
    $('.article img:not(".not-gallery-item")').each(function () {
        // wrap images with link and add caption if possible
        if ($(this).parent('a').length === 0) {
            $(this).wrap('<a class="gallery-item" href="' + $(this).attr('src') + '"></a>');
            if (this.alt) {
                $(this).after('<div class="has-text-centered is-size-6 has-text-grey caption">' + this.alt + '</div>');
            }
        }
    });

    if (typeof (moment) === 'function') {
        $('.article-meta time').each(function () {
            $(this).text(moment($(this).attr('datetime')).fromNow());
        });
    }

    $('.article > .content > table').each(function () {
        if ($(this).width() > $(this).parent().width()) {
            $(this).wrap('<div class="table-overflow"></div>');
        }
    });

    function adjustNavbar() {
        const navbarWidth = $('.navbar-main .navbar-start').outerWidth() + $('.navbar-main .navbar-end').outerWidth();
        if ($(document).outerWidth() < navbarWidth) {
            $('.navbar-main .navbar-menu').addClass('is-flex-start');
        } else {
            $('.navbar-main .navbar-menu').removeClass('is-flex-start');
        }
    }
    adjustNavbar();
    $(window).resize(adjustNavbar);

    $('figure.highlight table').wrap('<div class="highlight-body">');
    if (typeof (IcarusThemeSettings) !== 'undefined' &&
        typeof (IcarusThemeSettings.article) !== 'undefined' &&
        typeof (IcarusThemeSettings.article.highlight) !== 'undefined') {
        if (typeof (ClipboardJS) !== 'undefined' && IcarusThemeSettings.article.highlight.clipboard) {
            $('figure.highlight').each(function () {
                var id = 'code-' + Date.now() + (Math.random() * 1000 | 0);
                var button = '<a href="javascript:;" class="copy" title="Copy" data-clipboard-target="#' + id + ' .code"><i class="fas fa-copy"></i></a>';
                $(this).attr('id', id);
                if ($(this).find('figcaption').length) {
                    $(this).find('figcaption').prepend(button);
                } else {
                    $(this).prepend('<figcaption>' + button + '</figcaption>');
                }
            });
            new ClipboardJS('.highlight .copy');
        }
        var fold = IcarusThemeSettings.article.highlight.fold;
        if (fold.trim()) {
            var button = '<span class="fold">' + (fold === 'unfolded' ? '<i class="fas fa-angle-down"></i>' : '<i class="fas fa-angle-right"></i>') + '</span>';
            $('figure.highlight').each(function () {
                if ($(this).find('figcaption').length) {
                    $(this).find('figcaption').prepend(button);
                } else {
                    $(this).prepend('<figcaption>' + button + '</figcaption>');
                }
            });

            function toggleFold(codeBlock, isFolded) {
                var $toggle = $(codeBlock).find('.fold i');
                !isFolded ? $(codeBlock).removeClass('folded') : $(codeBlock).addClass('folded');
                !isFolded ? $toggle.removeClass('fa-angle-right') : $toggle.removeClass('fa-angle-down');
                !isFolded ? $toggle.addClass('fa-angle-down') : $toggle.addClass('fa-angle-right');
            }

            $('figure.highlight').each(function () {
                toggleFold(this, fold === 'folded');
            });
            $('figure.highlight figcaption .fold').click(function () {
                var $code = $(this).closest('figure.highlight');
                toggleFold($code.eq(0), !$code.hasClass('folded'));
            });
        }
    }

    var $toc = $('#toc');
    if ($toc.length > 0) {
        var $mask = $('<div>');
        $mask.attr('id', 'toc-mask');

        $('body').append($mask);

        function toggleToc() {
            $toc.toggleClass('is-active');
            $mask.toggleClass('is-active');
        }

        $toc.on('click', toggleToc);
        $mask.on('click', toggleToc);
        $('.navbar-main .catalogue').on('click', toggleToc);
    }
})(jQuery);


//widget_pin
var swiper = new Swiper('.blog-slider', {
    spaceBetween: 30,
    effect: 'fade',
    // autoHeight: true,
    pagination: {
      el: '.blog-slider__pagination',
      clickable: true,
    }
  });



  
  
