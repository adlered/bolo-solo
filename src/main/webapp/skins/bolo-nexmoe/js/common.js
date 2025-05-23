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
        Skin._initToc()
        Util.initPjax(function () {
            if ($('.post__fix').length === 0) {
                $('body').addClass('body--gray')
            } else {
                $('body').removeClass('body--gray')
            }
            $('.header__nav a').each(function () {
                console.log(this.href)
                console.log(location.href)
                if (this.href === location.href) {
                    this.className = 'active nexmoe-list-item mdui-list-item mdui-ripple'
                }else{
                    this.className ='nexmoe-list-item mdui-list-item mdui-ripple'
                }
            })

            Skin._initToc()
        })

        $('.header__nav a').each(function () {
            if (this.href === location.href) {
                this.className = 'active nexmoe-list-item mdui-list-item mdui-ripple'
            }
        }).click(function () {
            $('.header__nav a').removeClass('active')
            if (this.href === location.href) {
                this.className = 'active nexmoe-list-item mdui-list-item mdui-ripple'
            }
        })

        $('.post__toc .article__toc').find("li").find("a").on('click', function (e) {
            e.preventDefault();
            let _id = $(this).attr('href');
            $('html,body').animate({scrollTop: $(_id).offset().top}, 800)
        });


    },
    _initToc: function () {
        /* $(window).keydown(function (event) {
            if (event.which == 84) {
                Util.goTop()
            }
        }); */
        // 删除重复头图问题
        let headerImg = $(".nexmoe-post-cover img:eq(0)").attr("data-src")
        let firstP = $("article p:eq(0) img:eq(0)").attr("data-src")

        if (firstP == undefined) {
            firstP = $("article p:eq(0) img:eq(0)").attr("src")
        }
        if (headerImg == firstP && headerImg != undefined) {
            $("article img:eq(0)").remove()
        }

        if ($('.article__toc').length === 0) {
            return
        }

        var $articleTocs = $('.vditor-reset [id^=b3_solo_h]'),
            $articleToc = $('.article__toc');
        $(window).unbind('scroll').scroll(function (event) {
            if ($('.article__toc li').length === 0) {
                return false;
            }

            if ($(window).scrollTop() > 72) {
                $('.post__toc').show()
            } else {
                $('.post__toc').hide()
                return
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
                if (scrollTop < toc[i].offsetTop - 20) {
                    $articleToc.find('li').removeClass('current');
                    var index = i > 0 ? i - 1 : 0;
                    $articleToc.find('a[href="#' + toc[index].id + '"]').parent().addClass('current');
                    break;
                }
            }
            if (scrollTop >= toc[toc.length - 1].offsetTop - 20) {
                $articleToc.find('li').removeClass('current');
                $articleToc.find('li:last').addClass('current');
            }
        });
        $(window).scroll()
    },
    _initShare: function () {
        var $this = $('.post__share')
        var $qrCode = $this.find('.post__code')
        var shareURL = $qrCode.data('url')
        var avatarURL = $qrCode.data('avatar')
        var title = encodeURIComponent($qrCode.data('title') + ' - ' +
            $qrCode.data('blogtitle')),
            url = encodeURIComponent(shareURL)

        var urls = {}
        urls.weibo = 'http://v.t.sina.com.cn/share/share.php?title=' +
            title + '&url=' + url + '&pic=' + avatarURL
        urls.qqz = 'https://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url='
            + url + '&sharesource=qzone&title=' + title + '&pics=' + avatarURL
        urls.twitter = 'https://twitter.com/intent/tweet?status=' + title + ' ' +
            url

        $this.find('span').click(function () {
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
    },
    initArticle: function () {
        Skin._initShare()
        Skin._initToc()
    },
}
Skin.init()