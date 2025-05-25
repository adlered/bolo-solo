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
 * @fileoverview Page util, load highlight and process comment.
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 */
var Page = function (tips) {
    this.currentCommentId = '';
    this.tips = tips
};

$.extend(Page.prototype, {
    share: function () {
        var $this = $('.article__share');
        if ($this.length === 0) {
            return
        }
        var $qrCode = $this.find('.item__qr');
        var shareURL = $this.data('url');
        var avatarURL = $this.data('avatar');
        var title = encodeURIComponent($this.data('title') + ' - ' +
            $this.data('blogtitle'));
        var url = encodeURIComponent(shareURL);

        var urls = {};
        urls.tencent = 'http://share.v.t.qq.com/index.php?c=share&a=index&title=' +
            title +
            '&url=' + url + '&pic=' + avatarURL;
        urls.weibo = 'http://v.t.sina.com.cn/share/share.php?title=' +
            title + '&url=' + url + '&pic=' + avatarURL;
        urls.qqz = 'https://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url='
            + url + '&sharesource=qzone&title=' + title + '&pics=' + avatarURL;
        urls.twitter = 'https://twitter.com/intent/tweet?status=' + title + ' ' +
            url;

        $this.find('span').click(function () {
            var key = $(this).data('type');

            if (!key) {
                return
            }

            if (key === 'wechat') {
                if ($qrCode.find('canvas').length === 0) {
                    Util.addScript(Label.staticServePath +
                        '/js/lib/jquery.qrcode.min.js', 'qrcodeScript');
                    $qrCode.qrcode({
                        width: 128,
                        height: 128,
                        text: shareURL,
                    })
                } else {
                    $qrCode.slideToggle()
                }
                return false
            }

            window.open(urls[key], '_blank', 'top=100,left=200,width=648,height=618')
        })
    },
    /*
     * @description 文章加载
     */
    load: function () {
        var that = this;
        // comment
        $('#comment').click(function () {
            that.toggleEditor()
        }).attr('readonly', 'readonly');

        $('#soloEditorCancel').click(function () {
            that.toggleEditor()
        });
        $('#soloEditorAdd').click(function () {
            if ($("#boloUser").val() == "" && !loggedIn) {
                swal({
                    title: "",
                    text: "请先填写昵称!",
                    icon: "warning",
                    button: "好",
                });
            } else {
                swal({
                    title: "谢谢！还有一件事。",
                    text: "如果你的评论被回复了，你希望我们发送邮件通知你吗？如果愿意，请在下方填写你的邮箱地址，反之请留空（我们不会向你的邮箱发送除必要通知外的任何信息）：",
                    content: {
                        element: "input",
                        attributes: {
                            placeholder: "写下你的邮箱（可留空）",
                            type: "text",
                        },
                    },
                    buttons: ["取消", "好"],
                })
                    .then((value) => {
                        email = value;
                        if (null !== email) {
                            reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
                            if (!reg.test(email) && email !== "") {
                                swal({
                                    title: "",
                                    text: "评论推送失败: 非法邮箱格式",
                                    icon: "error",
                                    button: "好",
                                });
                            } else {
                                $.cookie('my_email', email, {expires: 7, path: '/'});
                                that.submitComment();
                            }
                        }
                    });
            }
        })
    },
    toggleEditor: function (commentId, name) {
        var $editor = $('#soloEditor');
        if ($editor.length === 0) {
            location.href = Label.servePath + '/start';
            return
        }

        if (!$('#soloEditorComment').hasClass('vditor')) {
            var that = this;
            Util.addScript(
                'https://file.fishpi.cn/vditor/3.8.13/dist/index.min.js',
                'vditorScript');
            var toolbar = [
                'emoji',
                'headings',
                'bold',
                'italic',
                'strike',
                'link',
                '|',
                'list',
                'ordered-list',
                'check',
                'outdent',
                'indent',
                'quote',
                'line',
                'code',
                'inline-code',
                'table',
                'insert-before',
                'insert-after',
                '|',
                'undo',
                'redo',
                'edit-mode',
                'both',
                'preview',
                '|',
                'fullscreen',
                'devtools',
                'help',
            ], resizeEnable = true;
            if ($(window).width() < 768) {
                toolbar = [
                    'emoji',
                    'link',
                    'upload',
                    'insert-after',
                    'edit-mode',
                    'preview',
                    'fullscreen',
                ];
                resizeEnable = false
            }

            window.vditor = new Vditor('soloEditorComment', {
                placeholder: that.tips.commentContentCannotEmptyLabel,
                height: 180,
                tab: '\t',
                esc: function () {
                    $('#soloEditorCancel').click()
                },
                ctrlEnter: function () {
                    $('#soloEditorAdd').click()
                },
                preview: {
                    delay: 500,
                    mode: 'editor',
                    url: Label.servePath + '/console/markdown/2html',
                    hljs: {
                        enable: !Label.luteAvailable,
                        style: Label.hljsStyle,
                    },
                    parse: function (element) {
                        if (element.style.display === 'none') {
                            return
                        }
                        Util.parseLanguage()
                    },
                },
                counter: 500,
                resize: {
                    enable: resizeEnable,
                    position: 'top',
                },
                lang: Label.langLabel,
                toolbar: toolbar,
                after: () => {
                    vditor.focus()
                }
            });
        }

        if ($editor.css('bottom') === '-300px' || commentId) {
            $('#soloEditorError').text('');
            if ($(window).width() < 768) {
                $editor.css({'top': '0', 'bottom': 'auto', 'opacity': 1})
            } else {
                $editor.css({'bottom': '0', top: 'auto', 'opacity': 1})
            }

            this.currentCommentId = commentId;
            $('#soloEditorReplyTarget').text(name ? '@' + name : '');
            if (typeof vditor !== 'undefined' && vditor.vditor.wysiwyg) {
                vditor.focus()
            }
        } else {
            $editor.css({'bottom': '-300px', top: 'auto', 'opacity': 0})
        }
    },
    /*
     * @description 加载随机文章
     * @param {String} headTitle 随机文章标题
     */
    loadRandomArticles: function (headTitle) {
        var randomArticles1Label = this.tips.randomArticles1Label;
        // getRandomArticles
        $.ajax({
            url: Label.servePath + '/articles/random',
            type: 'POST',
            success: function (result, textStatus) {
                var randomArticles = result.randomArticles;
                if (!randomArticles || 0 === randomArticles.length) {
                    $('#randomArticles').remove();
                    return
                }

                var listHtml = '';
                for (var i = 0; i < randomArticles.length; i++) {
                    var article = randomArticles[i];
                    var title = article.articleTitle;
                    var randomArticleLiHtml = '<li>' + '<a rel=\'nofollow\' title=\'' +
                        title + '\' href=\'' + Label.servePath +
                        article.articlePermalink + '\'>' + title + '</a></li>';
                    listHtml += randomArticleLiHtml
                }

                var titleHTML = headTitle ? headTitle : '<h4>' + randomArticles1Label +
                    '</h4>';
                var randomArticleListHtml = titleHTML + '<ul>' +
                    listHtml + '</ul>';
                $('#randomArticles').append(randomArticleListHtml)
            },
        })
    },
    /*
     * @description 加载相关文章
     * @param {String} id 文章 id
     * @param {String} headTitle 相关文章标题
     */
    loadRelevantArticles: function (id, headTitle) {
        $.ajax({
            url: Label.servePath + '/article/id/' + id + '/relevant/articles',
            type: 'GET',
            success: function (data, textStatus) {
                var articles = data.relevantArticles;
                if (!articles || 0 === articles.length) {
                    $('#relevantArticles').remove();
                    return
                }
                var listHtml = '';
                for (var i = 0; i < articles.length; i++) {
                    var article = articles[i];
                    var title = article.articleTitle;
                    var articleLiHtml = '<li>'
                        + '<a rel=\'nofollow\' title=\'' + title + '\' href=\'' +
                        Label.servePath + article.articlePermalink + '\'>'
                        + title + '</a></li>';
                    listHtml += articleLiHtml
                }

                var relevantArticleListHtml = headTitle
                    + '<ul>'
                    + listHtml + '</ul>';
                $('#relevantArticles').append(relevantArticleListHtml)
            },
            error: function () {
                $('#relevantArticles').remove()
            },
        })
    },
    /*
     * @description 提交评论
     * @param {String} commentId 回复评论时的评论 id
     */
    submitComment: function () {
        var that = this,
            tips = this.tips;

        if (vditor.getValue().length > 1 && vditor.getValue().length < 500) {
            $('#soloEditorAdd').attr('disabled', 'disabled');
            var requestJSONObject = {
                'oId': tips.oId,
                'commentContent': vditor.getValue(),
                'boloUser': $("#boloUser").val(),
                'boloSite': $("#boloSite").val(),
                'email': $.cookie("my_email"),
                'URI': window.location.href
            };

            if (this.currentCommentId) {
                requestJSONObject.commentOriginalCommentId = this.currentCommentId
            }

            $.ajax({
                type: 'POST',
                url: Label.servePath + '/article/comments',
                cache: false,
                contentType: 'application/json',
                data: JSON.stringify(requestJSONObject),
                success: function (result) {
                    $('#soloEditorAdd').removeAttr('disabled');
                    if (!result.sc) {
                        $('#soloEditorError').html(result.msg);
                        swal({
                            title: "",
                            text: "评论推送失败: " + result.msg,
                            icon: "error",
                            button: "好",
                        });

                        return
                    }
                    that.toggleEditor();
                    vditor.setValue('');
                    swal({
                        title: "",
                        text: "评论已推送!",
                        icon: "success",
                        button: "好",
                    });
                    that.addCommentAjax(result.cmtTpl)
                },
            })
        } else {
            $('#soloEditorError').text(that.tips.commentContentCannotEmptyLabel)
            swal({
                title: "",
                text: "评论推送失败: " + that.tips.commentContentCannotEmptyLabel,
                icon: "error",
                button: "好",
            });
        }
    },
    /*
     * @description 添加回复评论表单
     * @param {String} id 被回复的评论 id
     */
    addReplyForm: function (id, name) {
        var that = this;
        that.currentCommentId = id;
        this.toggleEditor(id, name)
    },
    /*
     * @description 隐藏回复评论的浮出层
     * @parma {String} id 被回复的评论 id
     */
    hideComment: function (id) {
        $('#commentRef' + id).hide()
    },
    /*
     * @description 显示回复评论的浮出层
     * @parma {Dom} it 触发事件的 dom
     * @param {String} id 被回复的评论 id
     * @param {Int} top 位置相对浮出层的高度
     * @param {String} [parentTag] it 如果嵌入在 position 为 relative 的元素 A 中时，需取到 A 元素
     */
    showComment: function (it, id, top, parentTag) {
        var positionTop = parseInt($(it).position().top);
        if (parentTag) {
            positionTop = parseInt($(it).parents(parentTag).position().top)
        }
        if ($('#commentRef' + id).length > 0) {
            // 此处重复设置 top 是由于评论为异步，原有回复评论的显示位置应往下移动
            $('#commentRef' + id).show().css('top', (positionTop + top) + 'px')
        } else {
            var $refComment = $('#' + id).clone();
            $refComment.addClass('comment-body-ref').attr('id', 'commentRef' + id);
            $refComment.find('#replyForm').remove();
            $('#comments').append($refComment);
            $('#commentRef' + id).css('top', (positionTop + top) + 'px')
        }
    },
    /*
     * @description 回复不刷新，将回复内容异步添加到评论列表中
     * @parma {String} commentHTML 回复内容 HTML
     */
    addCommentAjax: function (commentHTML) {
        if ($('#comments').children().length > 0) {
            $($('#comments').children()[0]).before(commentHTML)
        } else {
            $('#comments').html(commentHTML)
        }
        Util.parseMarkdown();
        Util.parseLanguage();
        window.location.hash = '#comments'
    },
});
