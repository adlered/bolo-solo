/*
 * Solo - A small and beautiful blogging system written in Java.
 * Copyright (c) 2010-present, b3log.org
 *
 * Solo is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *         http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
// /*
//  * Solo - A small and beautiful blogging system written in Java.
//  * Copyright (c) 2010-present, b3log.org
//  *
//  * Solo is licensed under Mulan PSL v2.
//  * You can use this software according to the terms and conditions of the Mulan PSL v2.
//  * You may obtain a copy of Mulan PSL v2 at:
//  *         http://license.coscl.org.cn/MulanPSL2
//  * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
//  * See the Mulan PSL v2 for more details.
//  */
// /**
//  * @fileoverview util and every page should be used.
//  *
//  * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
//  * @version 1.0.0.0, Jan 18, 2019
//  */
//
import '../../../js/common'
//
// /**
//  * @description 皮肤脚本
//  * @static
//  */
// window.Skin = {
//     init: function () {
//         // $('#headerDown').click(function () {
//         //     $('html, body').animate({scrollTop: $(window).height()}, 300)
//         // })
//         // $('#scroll_down').click(function () {
//         //     $('html, body').animate({scrollTop: $(window).height()}, 300)
//         // })
//         // $(window).scroll(function (event) {
//         //     $('.fn__progress').attr('value', parseInt($(window).scrollTop())).attr('max', parseInt($('body').outerHeight() -
//         //         $(window).height()))
//         //
//         //     if ($(window).scrollTop() > $(window).height() / 2 - 20) {
//         //         $('.side__menu').addClass('side__menu--edge')
//         //         $('#sideTop').removeClass('side__top--bottom')
//         //     } else {
//         //         $('.side__menu').removeClass('side__menu--edge')
//         //         $('#sideTop').addClass('side__top--bottom')
//         //     }
//         // })
//         //
//         // $('.side__menu').click(function () {
//         //     $('.side__main').addClass('side__main--show').show()
//         // })
//         // $('.side__bg, .side__close').click(function () {
//         //     $('.side__main').removeClass('side__main--show')
//         //     setTimeout(function () {
//         //         $('.side__main').hide()
//         //     }, 1000)
//         // })
//         // $('#sideTop').click(function () {
//         //     if ($(this).hasClass('side__top--bottom')) {
//         //         Util.goBottom()
//         //     } else {
//         //         Util.goTop()
//         //     }
//         // })
//         var timer = 0
//         var blogTitle = $('.header__h1').text()
//         document.addEventListener('visibilitychange', function () {
//             if (timer) clearTimeout(timer)
//             if (document.hidden) {
//                 timer = setTimeout(function () {
//                     document.title = '+_+! --> ' + blogTitle
//                 }, 500)
//             } else {
//                 document.title = 'lonus lan 欢迎回来！'
//                 timer = setTimeout(function () {
//                     document.title = blogTitle
//                 }, 1000)
//             }
//         }, false)
//
//         new Ribbons({
//             colorSaturation: '60%',
//             colorBrightness: '50%',
//             colorAlpha: 0.5,
//             colorCycleSpeed: 5,
//             verticalPosition: 'random',
//             horizontalSpeed: 200,
//             ribbonCount: 3,
//             strokeSize: 0,
//             parallaxAmount: -0.2,
//             animateSections: true,
//         })
//
//         // if ($('#comments').length === 1) {
//         //     return
//         // } else {
//         //     $(window).scroll()
//         // }
//
//         // $('.header').circleMagic({
//         //     clearOffset: 0.3,
//         //     color: 'rgba(255,255,255, .2)',
//         //     density: 0.2,
//         //     radius: 15,
//         // })
//     },
//     // initArticle: function () {
//     //     page.share()
//     //
//     //     initCanvas('articleTop')
//     //
//     //     if ($(window).width() >= 768) {
//     //         $('.post__toc').css({
//     //             left: document.querySelector('.article__content').getBoundingClientRect().right + 20,
//     //             right: 'auto',
//     //             display: 'block',
//     //         })
//     //     } else {
//     //         $('.side__top--toc').click(function () {
//     //             $('.post__toc').slideToggle()
//     //         })
//     //     }
//     //     var $articleTocs = $('.vditor-reset [id^=toc_h]')
//     //     var $articleToc = $('.article__toc')
//     //     $articleToc.find('a').click(function (event) {
//     //         var id = $(this).attr('href')
//     //         window.location.hash = id
//     //         $(window).scrollTop($(id).offset().top)
//     //         if ($(window).width() < 768) {
//     //             $('.post__toc').slideToggle()
//     //         }
//     //         event.preventDefault()
//     //         event.stopPropagation()
//     //         return false
//     //     })
//     //     $(window).scroll(function (event) {
//     //         if ($('.article__toc li').length === 0) {
//     //             return false
//     //         }
//     //         // 界面各种图片加载会导致帖子目录定位
//     //         var toc = []
//     //         $articleTocs.each(function (i) {
//     //             toc.push({
//     //                 id: this.id,
//     //                 offsetTop: $(this).offset().top,
//     //             })
//     //         })
//     //         // 当前目录样式
//     //         var scrollTop = $(window).scrollTop() + 10
//     //         for (var i = 0, iMax = toc.length; i < iMax; i++) {
//     //             if (scrollTop < toc[i].offsetTop) {
//     //                 $articleToc.find('li').removeClass('current')
//     //                 var index = i > 0 ? i - 1 : 0
//     //                 $articleToc.find('a[href="#' + toc[index].id + '"]').parent().addClass('current')
//     //                 break
//     //             }
//     //         }
//     //         if (scrollTop >= toc[toc.length - 1].offsetTop) {
//     //             $articleToc.find('li').removeClass('current')
//     //             $articleToc.find('li:last').addClass('current')
//     //         }
//     //     })
//     //     $(window).scroll()
//     // },
//     three: function () {
//         // //文章页article图片交替出现
//         $("article").each(function (index, element) {
//             if (index % 2 == 0) {
//                 $(element).find(".post-thumb-show").addClass("post-thumb").removeClass("post-left-thumb");
//             } else {
//                 $(element).find(".post-thumb-show").addClass("post-left-thumb").removeClass("post-thumb");
//             }
//         });
//         $("body").append("<meting-js\n" +
//             "        server=\"netease\"\n" +
//             "        type=\"playlist\"\n" +
//             "        id=\"3002544701\"\n" +
//             "        theme=\"#33ccff\"\n" +
//             "        fixed=\"true\"\n" +
//             "        list-max-height=\"150px\"\n" +
//             "        list-folded=\"true\">\n" +
//             // "        lrc-type=\"1\">\n" +
//             "</meting-js>");
//         $(window).scroll(function () {
//             if ($(this).scrollTop() > 0) {
//                 $("#rightside").addClass("rout").removeClass("rin");
//             } else {
//                 $("#rightside").removeClass("rout").addClass("rin");
//             }
//         });
//         const $nav = $('#nav')
//         //滑动时nav的处理
//         $(window).scroll(throttle(function (event) {
//             var currentTop = $(this).scrollTop();
//             var isDown = scrollDirection(currentTop);
//             if (currentTop > 56) {
//                 if (isDown) {
//                     if ($nav.hasClass('visible')) $nav.removeClass('visible');
//                 } else {
//                     if (!$nav.hasClass('visible')) $nav.addClass('visible');
//                 }
//                 $nav.addClass('fixed');
//             } else {
//                 if (currentTop === 0) {
//                     $nav.removeClass('fixed').removeClass('visible')
//                 }
//             }
//         }, 200));
//         var startDate = "2020/1/1";
//         var BirthDay = new Date(startDate)
//         var today = new Date()
//         var timeold = (today.getTime() - BirthDay.getTime())
//         var daysold = Math.floor(timeold / (24 * 60 * 60 * 1000))
//         $('.webinfo-runtime-count').text(daysold + " 天");
//     },
// };
// function randomNum(minNum, maxNum) {
//     switch (arguments.length) {
//         case 1:
//             return parseInt(Math.random() * minNum + 1);
//             break;
//         case 2:
//             return parseInt(Math.random() * (maxNum - minNum + 1) + minNum);
//             break;
//         default:
//             return 0;
//             break;
//     }
// };
// function randomNumBetween(start, end) {
//     return Math.floor(Math.random() * (end - start) + start)
// }
// function show_animation(ajax) {
//     var land_at_home = false;
//     $("article.post-list-thumb").each(
//         function (i) {
//             if (ajax) {
//                 var window_height = $(window).height();
//             } else {
//                 if ($(".headertop").hasClass("headertop-bar")) {
//                     var window_height = 0;
//                 } else {
//                     if (land_at_home) {
//                         var window_height = $(window).height() - 300;
//                     } else {
//                         var window_height = $(window).height();
//                     }
//                 }
//             }
//             var article_height = $("article.post-list-thumb").eq(i).offset().top;
//             if ($(window).height() + $(window).scrollTop() >= article_height)
//                 $("article.post-list-thumb").eq(i).addClass('post-list-show');
//             $(window).scroll(function () {
//                 var scrolltop = $(window).scrollTop();
//                 if (scrolltop + window_height >= article_height && scrolltop)
//                     $("article.post-list-thumb").eq(i).addClass("post-list-show");
//             });
//         });
// };
// //页面互动相关
// var initTop = 0;
// function scrollDirection(currentTop) {
//     var result = currentTop > initTop // true is down & false is up
//     initTop = currentTop
//     return result
// };
// //nav滑动
// function throttle(func, wait, options) {
//     var timeout, context, args
//     var previous = 0
//     if (!options) options = {}
//     var later = function () {
//         previous = options.leading === false ? 0 : new Date().getTime()
//         timeout = null
//         func.apply(context, args)
//         if (!timeout) context = args = null
//     }
//     var throttled = function () {
//         var now = new Date().getTime()
//         if (!previous && options.leading === false) previous = now
//         var remaining = wait - (now - previous)
//         context = this
//         args = arguments
//         if (remaining <= 0 || remaining > wait) {
//             if (timeout) {
//                 clearTimeout(timeout)
//                 timeout = null
//             }
//             previous = now
//             func.apply(context, args)
//             if (!timeout) context = args = null
//         } else if (!timeout && options.trailing !== false) {
//             timeout = setTimeout(later, remaining)
//         }
//     }
//     return throttled
// };
// function scrollToDest(name, offset = 0) {
//     var scrollOffset = $(name).offset()
//     $('body,html').animate({
//         scrollTop: scrollOffset.top - offset
//     })
// };
//时间轴
function timeSeriesReload(flag) {
    if (flag == true) {
        $('#archives span.al_mon').click(function () {
            $(this).next().slideToggle(400);
            return false;
        });
        // lazyload();
    } else {
        (function () {
            $('#al_expand_collapse,#archives span.al_mon').css({cursor: "pointer"});
            $('#archives span.al_mon').each(function () {
                var num = $(this).next().children('li').length;
                $(this).children('#post-num').text(num);
            });
            var $al_post_list = $('#archives ul.al_post_list'),
                $al_post_list_f = $('#archives ul.al_post_list:first');
            $al_post_list.hide(1, function () {
                $al_post_list_f.show();
            });
            $('#archives span.al_mon').click(function () {
                $(this).next().slideToggle(400);
                return false;
            });
            if (document.body.clientWidth > 860) {
                $('#archives li.al_li').mouseover(function () {
                    $(this).children('.al_post_list').show(400);
                    return false;
                });
                if (false) {
                    $('#archives li.al_li').mouseout(function () {
                        $(this).children('.al_post_list').hide(400);
                        return false;
                    });
                }
            }
            var al_expand_collapse_click = 0;
            $('#al_expand_collapse').click(function () {
                if (al_expand_collapse_click == 0) {
                    $al_post_list.show();
                    al_expand_collapse_click++;
                } else if (al_expand_collapse_click == 1) {
                    $al_post_list.hide();
                    al_expand_collapse_click--;
                }
            });
        })();
    }
};
// $(document).ready(function () {
//     Skin.init();
//     Skin.three();
// });
// //随机背景图片
// function loadBg() {
//     // 设置主页图片
//     window.cnblogsConfig = {
//         homeTopImg: [
//             "https://img.lonuslan.com/lonuslan/20200120/AXsi94kr5YSl.jpg",
//             "https://img.lonuslan.com/lonuslan/20200120/o6RVlf8Tym1S.jpg",
//             "https://img.lonuslan.com/lonuslan/20200120/4Xw4pJJ6wA7X.jpg",
//             "https://img.lonuslan.com/lonuslan/20200120/d4yH2omtFThg.jpg",
//             "https://img.lonuslan.com/lonuslan/20200120/QOBHhXHWcgBu.jpg",
//             "https://img.lonuslan.com/lonuslan/20200120/XznCEJWcH0oG.jpg",
//             "https://img.lonuslan.com/lonuslan/20200120/G0pKoHpLTSqp.jpg",
//             "https://img.lonuslan.com/lonuslan/20200120/9P2HW1O4DSNl.jpg",
//             "https://img.lonuslan.com/lonuslan/20200120/IRiGYHISNvks.jpg"
//         ]
//     }
//     let homeTopImg = window.cnblogsConfig.homeTopImg, bgImg;
//     homeTopImg.length > 0 ?
//         (homeTopImg.length > 1 ? bgImg = homeTopImg[randomNumBetween(0, homeTopImg.length - 1)] : bgImg = homeTopImg[0])
//         : bgImg = "";
//     // console.log("bgImg -->" + bgImg)
//     // console.log("测试----> 随机获取header图");
//     $('.full_page').css({
//         'background': '#222 url(' + bgImg + ')  center center no-repeat',
//         'background-size': 'cover'
//     });
// };
// //右边栏按钮
// function rightSideBtn(){
//     //是否在主页展示评论
//     if ($(".commentFont")[0]) {
//         $("#to_comment").show();
//     } else {
//         $("#to_comment").hide();
//     }
//     ;
//     //模式切换
//     var nowMode = "lightMode";
//     $("#readmode").click(function () {
//         // $("body").toggleClass("read-mode")
//     }), $("#font_plus").click(function () {
//         $("body").css("font-size", parseFloat($("body").css("font-size")) + 1)
//     }), $("#font_minus").click(function () {
//         $("body").css("font-size", parseFloat($("body").css("font-size")) - 1)
//     }), $("#to_comment").click(function () {
//         var scoll_offset = $(".commentFont").offset().top;
//         $("body,html").animate({
//             scrollTop: scoll_offset //让body的scrollTop等于pos的top，就实现了滚动
//         }, 1000);
//     }), $("#rightside_config").on("click", function () {
//         // $(".rightside-in").show();
//         $("#rightside-config-hide").hasClass("rightside-in") ? $("#rightside-config-hide").removeClass("rightside-in").addClass("rightside-out") : $("#rightside-config-hide").removeClass("rightside-out").addClass("rightside-in")
//     }), $("#go-up").on("click", function () {
//         Util.goTop();
//     }), $("#darkmode").click(function () {
//         if (nowMode === "lightMode") {
//             $("body").addClass("body-dark").removeClass("body-light");
//             $("p").addClass("p-dark").removeClass("p-light");
//             $(".articles").addClass("article-dark").removeClass("article-light");
//             $(".cardlight").addClass("card-dark").removeClass("card")
//             nowMode = "darkMode";
//         } else {
//             $("body").addClass("body-light").removeClass("body-dark");
//             $("p").addClass("p-light").removeClass("p-dark");
//             $(".articles").addClass("article-light").removeClass("article-dark");
//             $(".cardlight").addClass("card").removeClass("card-dark")
//             nowMode = "lightMode";
//         }
//     });
// }
// //标题打字机效果
// function typedTitle() {
//     //主页打字机效果
//     const elementsString = $('.elements').text();
//     $('.elements').empty();
//     //console.log(elementsString);
//     const options = {
//         // 闪烁光标必须得有：上面的css和下面字符串内添加 ^1000 ，只要是当输入到^1000就解析闪烁的时间，1000ms。
//         strings: [elementsString + '^1000'],
//         typeSpeed: 200,
//         loop: true,
//         cursorChar: '|'
//     };
//     const typed = new Typed(".elements", options);
// }
//加载动画
// function loadingBox() {
//     //动画加载
//     var endLoading = function () {
//         document.body.style.overflow = 'auto';
//         document.getElementById('loading-box').classList.add("loaded")
//     }
//     window.addEventListener('load', endLoading);
// }
// function leftRiight(){
//     $("article").each(function (index, element) {
//         if (index % 2 == 0) {
//             $(element).find(".post_cover").addClass("left_radius");
//         } else {
//             $(element).find(".post_cover").addClass("right_radius");
//         }
//     });
// }
// //禁止调试
// function forbiddenDebug() {
//     //禁止调试
//     $(document).bind("contextmenu", function () { return false; });//禁止右键
//     document.oncontextmenu = function () { return false; };
//     document.onkeydown = function (event) {
//       if (event.keyCode == 123) {
//         event.preventDefault(); // 阻止默认事件行为
//         window.event.returnValue = false;
//       }
//     };//禁止F12
// }
// //随机背景
// loadBg();
//时间轴
timeSeriesReload(false);
// rightSideBtn();
// //开启pjax
// Util.initPjax;
// typedTitle();
// show_animation();
// // forbiddenDebug();
//
//
//
window.Skin = {
    init: function () {
        var activateDarkMode = function () {
            document.documentElement.setAttribute('data-theme', 'dark')
            if (document.querySelector('meta[name="theme-color"]') !== null) {
                document.querySelector('meta[name="theme-color"]').setAttribute('content', '#000')
            }
        }
        var activateLightMode = function () {
            document.documentElement.setAttribute('data-theme', 'light')
            if (document.querySelector('meta[name="theme-color"]') !== null) {
                document.querySelector('meta[name="theme-color"]').setAttribute('content', '#fff')
            }
        }

        var getCookies = function (name) {
            const value = `; ${document.cookie}`
            const parts = value.split(`; ${name}=`)
            if (parts.length === 2) return parts.pop().split(';').shift()
        }

        var autoChangeMode = '2'
        var t = getCookies('theme')
        if (autoChangeMode === '1') {
            var isDarkMode = window.matchMedia('(prefers-color-scheme: dark)').matches
            var isLightMode = window.matchMedia('(prefers-color-scheme: light)').matches
            var isNotSpecified = window.matchMedia('(prefers-color-scheme: no-preference)').matches
            var hasNoSupport = !isDarkMode && !isLightMode && !isNotSpecified

            if (t === undefined) {
                if (isLightMode) activateLightMode()
                else if (isDarkMode) activateDarkMode()
                else if (isNotSpecified || hasNoSupport) {
                    console.log('You specified no preference for a color scheme or your browser does not support it. I Schedule dark mode during night time.')
                    var now = new Date()
                    var hour = now.getHours()
                    var isNight = hour <= 6 || hour >= 18
                    isNight ? activateDarkMode() : activateLightMode()
                }
                window.matchMedia('(prefers-color-scheme: dark)').addListener(function (e) {
                    if (Cookies.get('theme') === undefined) {
                        e.matches ? activateDarkMode() : activateLightMode()
                    }
                })
            } else if (t === 'light') activateLightMode()
            else activateDarkMode()
        } else if (autoChangeMode === '2') {
            now = new Date()
            hour = now.getHours()
            isNight = hour <= 6 || hour >= 18
            if (t === undefined) isNight ? activateDarkMode() : activateLightMode()
            else if (t === 'light') activateLightMode()
            else activateDarkMode()
        } else {
            if (t === 'dark') activateDarkMode()
            else if (t === 'light') activateLightMode()
        }

        var subtitleType = function () {
            loadScript('https://sdk.jinrishici.com/v2/browser/jinrishici.js', function () {
                    var subtitleEffect = true
                    jinrishici.load(function (result) {
                        if (subtitleEffect) {
                            var sub = "May the god bless you.,It is better to burn out than to fade away.".length == 0 ? new Array() : "May the god bless you.,It is better to burn out than to fade away.".split(',')
                            var content = result.data.content
                            var both = sub.unshift(content)
                            var typed = new Typed('#subtitle', {
                                strings: sub,
                                startDelay: 300,
                                typeSpeed: 150,
                                loop: true,
                                backSpeed: 50,
                            })
                        } else {
                            document.getElementById('subtitle').innerHTML = result.data.content
                        }
                    })
                }
            )
        };
        window.addEventListener('load', subtitleType);
        const isSnackbar = GLOBAL_CONFIG.Snackbar !== undefined;
        const $nav = $('#nav');
        const $rightside = $('#rightside');
        const $body = $('body');
        /**
         * 當menu過多時，自動適配，避免UI錯亂
         * 傳入 1 sidebar打開時
         * 傳入 2 正常狀態下
         */
        var blogNameWidth = $('#blog_name').width()
        var menusWidth = $('.menus').width()
        var sidebarWidth = $('#sidebar').width()

        function isAdjust (n) {
            var t
            if (n === 1) {
                t = blogNameWidth + menusWidth > $nav.width() - sidebarWidth - 20
            } else if (n === 2) {
                t = blogNameWidth + menusWidth > $nav.width() - 20
            }

            if (t) headerAdjust()
            else headerAdjustBack()
        }

        function headerAdjust () {
            $nav.find('.toggle-menu').addClass('is-visible-inline')
            $nav.find('.menus_items').addClass('is-invisible')
            $nav.find('#search_button span').addClass('is-invisible')
        }

        function headerAdjustBack () {
            $nav.find('.toggle-menu').removeClass('is-visible-inline')
            $nav.find('.menus_items').removeClass('is-invisible')
            $nav.find('#search_button span').removeClass('is-invisible')
        }

        // 初始化header
        function initAjust () {
            if (window.innerWidth < 768) headerAdjust()
            else isAdjust(2)
        }

        initAjust()
        $('#nav').css({ opacity: '1', animation: 'headerNoOpacity 1s' })

        $(window).on('resize', function () {
            if ($('#sidebar').hasClass('tocOpenPc') && $nav.hasClass('fixed')) {
                isAdjust(1)
            } else {
                initAjust()
            }
        })

        /**
         * 手機menu和toc按鈕點擊
         * 顯示menu和toc的sidebar
         */

        var $toggleMenu = $('.toggle-menu')
        var $mobileSidevarMenus = $('#mobile-sidebar-menus')
        var $mobileTocButton = $('#mobile-toc-button')
        var $menuMask = $('#menu_mask')

        function openMobileSidebar (name) {
            sidebarPaddingR()
            $('body').css('overflow', 'hidden')
            $menuMask.fadeIn()

            if (name === 'menu') {
                $toggleMenu.removeClass('close').addClass('open')
                $mobileSidevarMenus.css('transform', 'translate3d(-100%,0,0)')
                var $mobileSidevarMenusChild = $mobileSidevarMenus.children()
                for (let i = 0; i <= $mobileSidevarMenusChild.length; i++) {
                    const duration = i / 5 + 0.2
                    $mobileSidevarMenusChild.eq(i).css('animation', 'sidebarItem ' + duration + 's')
                }
            }

            if (name === 'toc') {
                $mobileTocButton.removeClass('close').addClass('open')
                $('#sidebar').addClass('tocOpenMobile')
                $('#sidebar').css({ transform: 'translate3d(-100%,0,0)', left: '' })
            }
        }

        function closeMobileSidebar (name) {
            $('body').css({ overflow: '', 'padding-right': '' })
            $menuMask.fadeOut()

            if (name === 'menu') {
                $toggleMenu.removeClass('open').addClass('close')
                $mobileSidevarMenus.css('transform', '')
                $('#mobile-sidebar-menus > div,#mobile-sidebar-menus > hr').css('animation', '')
            }

            if (name === 'toc') {
                $mobileTocButton.removeClass('open').addClass('close')
                $('#sidebar').removeClass('tocOpenMobile')
                $('#sidebar').css({ transform: '' })
            }
        }

        $toggleMenu.on('click', function () {
            openMobileSidebar('menu')
        })

        $mobileTocButton.on('click', function () {
            openMobileSidebar('toc')
        })

        $menuMask.on('click touchstart', function (e) {
            if ($toggleMenu.hasClass('open')) {
                closeMobileSidebar('menu')
            }
            if ($mobileTocButton.hasClass('open')) {
                closeMobileSidebar('toc')
            }
        })

        $(window).on('resize', function (e) {
            if (!$toggleMenu.is(':visible')) {
                if ($toggleMenu.hasClass('open')) closeMobileSidebar('menu')
            }
        })

        const mql = window.matchMedia('(max-width: 1024px)')
        mql.addListener(function (ev) {
            if (ev.matches) {
                if ($('#sidebar').hasClass('tocOpenPc')) closeSidebar()
            } else {
                if ($('#toggle-sidebar').hasClass('on')) openSidebar()
                if ($mobileTocButton.hasClass('open')) closeMobileSidebar('toc')
            }
        })

        /**
         * 首頁top_img底下的箭頭
         */
        $('#scroll_down').on('click', function () {
            scrollToDest('#content-inner')
        })

        /**
         * BOOKMARK 書簽
         */
        $('#bookmark-it').on('click', function () {
            if (window.sidebar && window.sidebar.addPanel) { // Mozilla Firefox Bookmark
                window.sidebar.addPanel(document.title, window.location.href, '')
            } else if (window.external && ('AddFavorite' in window.external)) { // IE Favorite
                window.external.AddFavorite(location.href, document.title)
            } else if (window.opera && window.print) { // Opera Hotlist
                this.title = document.title
                return true
            } else { // webkit - safari/chrome
                if (isSnackbar) {
                    var bookmarkText = GLOBAL_CONFIG.Snackbar.bookmark.message_prev + ' ' + (navigator.userAgent.toLowerCase().indexOf('mac') !== -1 ? 'Command/Cmd' : 'CTRL') + '+ D ' + GLOBAL_CONFIG.Snackbar.bookmark.message_next + '.'
                    snackbarShow(bookmarkText)
                } else {
                    alert(GLOBAL_CONFIG.bookmark.message_prev + ' ' + (navigator.userAgent.toLowerCase().indexOf('mac') !== -1 ? 'Command/Cmd' : 'CTRL') + '+ D ' + GLOBAL_CONFIG.bookmark.message_next + '.')
                }
            }
        })



        /**
         * 滾動處理
         */
        var initTop = 0
        var isChatShow = true
        var isChatBtnHide = typeof chatBtnHide === 'function'
        var isChatBtnShow = typeof chatBtnShow === 'function'
        $(window).scroll(throttle(function (event) {
            var currentTop = $(this).scrollTop()
            var isDown = scrollDirection(currentTop)
            if (currentTop > 56) {
                if (isDown) {
                    if ($nav.hasClass('visible')) $nav.removeClass('visible')
                    if (isChatBtnShow && isChatShow === true) {
                        chatBtnHide()
                        isChatShow = false
                    }
                } else {
                    if (!$nav.hasClass('visible')) $nav.addClass('visible')
                    if (isChatBtnHide && isChatShow === false) {
                        window.chatBtnShow()
                        isChatShow = true
                    }
                }
                $nav.addClass('fixed')
                if ($rightside.css('opacity') === '0') {
                    $rightside.css({ opacity: '1', transform: 'translateX(-38px)' })
                }
            } else {
                if (currentTop === 0) {
                    $nav.removeClass('fixed').removeClass('visible')
                }
                $rightside.css({ opacity: '', transform: '' })
            }
        }, 200))

        // find the scroll direction
        function scrollDirection (currentTop) {
            var result = currentTop > initTop // true is down & false is up
            initTop = currentTop
            return result
        }

        /**
         * 是否在主页显示评论和阅读模式
         */
        if (!$(".body-index")[0]) {
            $("#readmode").show();
            $("#to_comment").show();
            $("#font_plus").show();
            $("#font_minus").show();
        } else {
            $("#readmode").hide();
            $("#to_comment").hide();
            $("#font_plus").hide();
            $("#font_minus").hide();
        }
        /**
         * 點擊滾回頂部
         */
        $('#go-up').on('click', function () {
            scrollToDest('body')
        })

        /**
         *  toc
         */

        if (GLOBAL_CONFIG_SITE.isPost && GLOBAL_CONFIG_SITE.isSidebar) {
            $('.toc-child').hide()

            // main of scroll
            $(window).scroll(throttle(function (event) {
                var currentTop = $(this).scrollTop()
                scrollPercent(currentTop)
                findHeadPosition(currentTop)
                autoScrollToc(currentTop)
            }, 100))

            // scroll
            $('.toc-link').on('click', function (e) {
                if (window.innerWidth <= 1024) {
                    closeMobileSidebar('toc')
                } else {
                    e.preventDefault()
                    scrollToDest($(this).attr('href'))
                }
            })

            // expand toc-item
            var expandToc = function ($item) {
                if ($item.is(':visible')) {
                    return
                }
                $item.fadeIn(400)
            }

            var scrollPercent = function (currentTop) {
                var docHeight = $('#article-container').height()
                var winHeight = $(window).height()
                var contentMath = (docHeight > winHeight) ? (docHeight - winHeight) : ($(document).height() - winHeight)
                var scrollPercent = (currentTop) / (contentMath)
                var scrollPercentRounded = Math.round(scrollPercent * 100)
                var percentage = (scrollPercentRounded > 100) ? 100
                    : (scrollPercentRounded <= 0) ? 0
                        : scrollPercentRounded
                $('.progress-num').text(percentage)
                $('.sidebar-toc__progress-bar').animate({
                    width: percentage + '%'
                }, 100)
            }

            // anchor
            var isanchor = GLOBAL_CONFIG.isanchor
            var updateAnchor = function (anchor) {
                if (window.history.replaceState && anchor !== window.location.hash) {
                    window.history.replaceState(undefined, undefined, anchor)
                }
            }

            // find head position & add active class
            // DOM Hierarchy:
            // ol.toc > (li.toc-item, ...)
            // li.toc-item > (a.toc-link, ol.toc-child > (li.toc-item, ...))
            var findHeadPosition = function (top) {
                // assume that we are not in the post page if no TOC link be found,
                // thus no need to update the status
                if ($('.toc-link').length === 0) {
                    return false
                }

                var list = $('#article-container').find('h1,h2,h3,h4,h5,h6')
                var currentId = ''
                list.each(function () {
                    var head = $(this)
                    if (top > head.offset().top - 25) {
                        currentId = '#' + $(this).attr('id')
                    }
                })

                if (currentId === '') {
                    $('.toc-link').removeClass('active')
                    $('.toc-child').hide()
                }

                var currentActive = $('.toc-link.active')
                if (currentId && currentActive.attr('href') !== currentId) {
                    if (isanchor) updateAnchor(currentId)

                    $('.toc-link').removeClass('active')

                    var _this = $('.toc-link[href="' + currentId + '"]')
                    _this.addClass('active')

                    var parents = _this.parents('.toc-child')
                    // Returned list is in reverse order of the DOM elements
                    // Thus `parents.last()` is the outermost .toc-child container
                    // i.e. list of subsections
                    var topLink = (parents.length > 0) ? parents.last() : _this
                    expandToc(topLink.closest('.toc-item').find('.toc-child'))
                    topLink
                        // Find all top-level .toc-item containers, i.e. sections
                        // excluding the currently active one
                        .closest('.toc-item').siblings('.toc-item')
                        // Hide their respective list of subsections
                        .find('.toc-child').hide()
                }
            }

            var autoScrollToc = function (currentTop) {
                if ($('.toc-link').hasClass('active')) {
                    var activePosition = $('.active').offset().top
                    var sidebarScrolltop = $('#sidebar .sidebar-toc__content').scrollTop()
                    if (activePosition > (currentTop + $(window).height() - 100)) {
                        $('#sidebar .sidebar-toc__content').scrollTop(sidebarScrolltop + 100)
                    }
                    if (activePosition < currentTop + 100) {
                        $('#sidebar .sidebar-toc__content').scrollTop(sidebarScrolltop - 100)
                    }
                }
            }
        }

        /**
         * 閲讀模式
         */
        $('#readmode').click(function () {
            $('body').toggleClass('read-mode')
        })

        /**
         * 字體調整
         */
        $('#font_plus').click(function () {
            $body.css('font-size', parseFloat($body.css('font-size')) + 1)
        })
        $('#font_minus').click(function () {
            $body.css('font-size', parseFloat($body.css('font-size')) - 1)
        })

        /**
         * menu
         * 側邊欄sub-menu 展開/收縮
         * 解決menus在觸摸屏下，滑動屏幕menus_item_child不消失的問題（手機hover的bug)
         */
        $('#mobile-sidebar-menus .menus-expand').on('click', function () {
            if ($(this).hasClass('menus-closed')) {
                $(this).parents('.menus_item').find('.menus_item_child').slideDown()
                $(this).removeClass('menus-closed')
            } else {
                $(this).parents('.menus_item').find('.menus_item_child').slideUp()
                $(this).addClass('menus-closed')
            }
        })

        $(window).on('touchmove', function (e) {
            var $menusChild = $('#nav .menus_item_child')
            if ($menusChild.is(':visible')) {
                $menusChild.css('display', 'none')
            }
        })

        /**
         * rightside 點擊設置 按鈕 展開
         */
        $('#rightside_config').on('click', function () {
            if ($('#rightside-config-hide').hasClass('rightside-in')) {
                $('#rightside-config-hide').removeClass('rightside-in').addClass('rightside-out')
            } else {
                $('#rightside-config-hide').removeClass('rightside-out').addClass('rightside-in')
            }
        });

        /**
         * Darkmode
         */
        var $darkModeButtom = $('#darkmode')
        function switchReadMode () {
            var nowMode = document.documentElement.getAttribute('data-theme') === 'dark' ? 'dark' : 'light'
            if (nowMode === 'light') {
                activateDarkMode()
                Cookies.set('theme', 'dark', 2)
                if (isSnackbar) snackbarShow(GLOBAL_CONFIG.Snackbar.day_to_night)
            } else {
                activateLightMode()
                Cookies.set('theme', 'light', 2)
                if (isSnackbar) snackbarShow(GLOBAL_CONFIG.Snackbar.night_to_day)
            }
        }

        $darkModeButtom.click(function () {
            switchReadMode()
            if (typeof utterancesTheme === 'function') utterancesTheme()
        });

    },
    initArticle: function () {
        page.share()
        initCanvas('articleTop')

        if ($(window).width() >= 768) {
            $('.post__toc').css({
                left: document.querySelector('.article__content').getBoundingClientRect().right + 20,
                right: 'auto',
                display: 'block',
            })
        } else {
            $('.side__top--toc').click(function () {
                $('.post__toc').slideToggle()
            })
        }
        var $articleTocs = $('.vditor-reset [id^=toc_h]')
        var $articleToc = $('.article__toc')
        $articleToc.find('a').click(function (event) {
            var id = $(this).attr('href')
            window.location.hash = id
            $(window).scrollTop($(id).offset().top)
            if ($(window).width() < 768) {
                $('.post__toc').slideToggle()
            }
            event.preventDefault()
            event.stopPropagation()
            return false
        })

        //滑动到评论
        $("#to_comment").click(function () {
            var scoll_offset = $(".commentFont").offset().top;
            $("body,html").animate({
                scrollTop: scoll_offset //让body的scrollTop等于pos的top，就实现了滚动
            }, 1000);
        })

        $(window).scroll(function (event) {
            if ($('.article__toc li').length === 0) {
                return false
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
            var scrollTop = $(window).scrollTop() + 10
            for (var i = 0, iMax = toc.length; i < iMax; i++) {
                if (scrollTop < toc[i].offsetTop) {
                    $articleToc.find('li').removeClass('current')
                    var index = i > 0 ? i - 1 : 0
                    $articleToc.find('a[href="#' + toc[index].id + '"]').parent().addClass('current')
                    break
                }
            }
            if (scrollTop >= toc[toc.length - 1].offsetTop) {
                $articleToc.find('li').removeClass('current')
                $articleToc.find('li:last').addClass('current')
            }
        })
        $(window).scroll()
    }
}
$(document).ready(function () {
    // loadingBox();
    Skin.init();
})

function loadBg() {
    // 设置主页图片
    window.cnblogsConfig = {
        homeTopImg: [
            "https://img.lonuslan.com/lonuslan/20200120/AXsi94kr5YSl.jpg",
            "https://img.lonuslan.com/lonuslan/20200120/o6RVlf8Tym1S.jpg",
            "https://img.lonuslan.com/lonuslan/20200120/4Xw4pJJ6wA7X.jpg",
            "https://img.lonuslan.com/lonuslan/20200120/d4yH2omtFThg.jpg",
            "https://img.lonuslan.com/lonuslan/20200120/QOBHhXHWcgBu.jpg",
            "https://img.lonuslan.com/lonuslan/20200120/XznCEJWcH0oG.jpg",
            "https://img.lonuslan.com/lonuslan/20200120/G0pKoHpLTSqp.jpg",
            "https://img.lonuslan.com/lonuslan/20200120/9P2HW1O4DSNl.jpg",
            "https://img.lonuslan.com/lonuslan/20200120/IRiGYHISNvks.jpg"
        ]
    }
    let homeTopImg = window.cnblogsConfig.homeTopImg, bgImg;
    homeTopImg.length > 0 ?
        (homeTopImg.length > 1 ? bgImg = homeTopImg[randomNumBetween(0, homeTopImg.length - 1)] : bgImg = homeTopImg[0])
        : bgImg = "";
    // console.log("bgImg -->" + bgImg)
    // console.log("测试----> 随机获取header图");
    $('.full_page').css({
        'background': '#222 url(' + bgImg + ')  center center no-repeat',
        'background-size': 'cover'
    });
};
function randomNumBetween(start, end) {
    return Math.floor(Math.random() * (end - start) + start)
}
loadBg();
// loadingBox();
leftRight();