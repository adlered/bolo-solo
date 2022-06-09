<#--

    Bolo - A stable and beautiful blogging system based in Solo.
    Copyright (c) 2020, https://github.com/adlered

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

-->
<div>
    <div id="toc"></div>
</div>
<script src="${staticServePath}/js/lib/jquery/jquery.min.js"></script>
<script>
    // initToc()
    // function initToc() {
    //     var toc = $(".post-article ul").first();
    //     $("#toc").html(toc);
    //     initRelationship()
    //     tocFixed()
    // }
    $(document).ready(function () {
        $('#toc').html('');

        var postDirectoryBuild = function () {
            var postChildren = function children(childNodes, reg) {
                    var result = [],
                        isReg = typeof reg === 'object',
                        isStr = typeof reg === 'string',
                        node, i, len;
                    for (i = 0, len = childNodes.length; i < len; i++) {
                        node = childNodes[i];
                        if ((node.nodeType === 1 || node.nodeType === 9) &&
                            (!reg ||
                                isReg && reg.test(node.tagName.toLowerCase()) ||
                                isStr && node.tagName.toLowerCase() === reg)) {
                            result.push(node);
                        }
                    }
                    return result;
                },
                createPostDirectory = function (article, directory, isDirNum) {

                    var contentArr = [],
                        titleId = [],
                        levelArr, root, level,
                        currentList, list, li, link, i, len;
                    levelArr = (function (article, contentArr, titleId) {
                        var titleElem = postChildren(article.childNodes, /^h\d$/),
                            levelArr = [],
                            lastNum = 1,
                            lastRevNum = 1,
                            count = 0,
                            guid = 1,
                            id = 'directory' + (Math.random() + '').replace(/\D/, ''),
                            lastRevNum, num, elem;
                        while (titleElem.length) {
                            elem = titleElem.shift();
                            contentArr.push(elem.innerHTML);
                            num = +elem.tagName.match(/\d/)[0];
                            if (num > lastNum) {
                                levelArr.push(1);
                                lastRevNum += 1;
                            } else if (num === lastRevNum ||
                                num > lastRevNum && num <= lastNum) {
                                levelArr.push(0);
                                lastRevNum = lastRevNum;
                            } else if (num < lastRevNum) {
                                levelArr.push(num - lastRevNum);
                                lastRevNum = num;
                            }
                            count += levelArr[levelArr.length - 1];
                            lastNum = num;
                            elem.id = elem.id || (id + guid++);
                            titleId.push(elem.id);
                        }
                        if (count !== 0 && levelArr[0] === 1) levelArr[0] = 0;

                        return levelArr;
                    })(article, contentArr, titleId);
                    currentList = root = document.createElement('ul');
                    dirNum = [0];
                    for (i = 0, len = levelArr.length; i < len; i++) {
                        level = levelArr[i];
                        if (level === 1) {
                            list = document.createElement('ul');
                            if (!currentList.lastElementChild) {
                                currentList.appendChild(document.createElement('li'));
                            }
                            currentList.lastElementChild.appendChild(list);
                            currentList = list;
                            dirNum.push(0);
                        } else if (level < 0) {
                            level *= 2;
                            while (level++) {
                                if (level % 2) dirNum.pop();
                                currentList = currentList.parentNode;
                            }
                        }
                        dirNum[dirNum.length - 1]++;
                        li = document.createElement('li');
                        link = document.createElement('a');
                        link.href = '#' + titleId[i];
                        link.innerHTML = !isDirNum ? contentArr[i] :
                            dirNum.join('.') + ' ' + contentArr[i];
                        li.appendChild(link);
                        currentList.appendChild(li);
                    }
                    directory.appendChild(root);
                };
            createPostDirectory(document.getElementById('post-article'), document.getElementById('toc'), false);
        };

//建立关联关系
        function initRelationship() {
            var win = $(window);
            var anchors = $('#toc').find('a');
            var offset = 200; //偏移量看情况调
            win.on('scroll', function () {
                var scrollTop = win.scrollTop();
                anchors.each(function (i, v) {
                    var that = $(v);
                    var id = that.attr('href');
                    var target = $(id);

                    if (scrollTop >= target.offset().top - offset) {
                        anchors.removeClass('active');
                        anchors.parent().removeClass("lit-active");
                        that.addClass('active');
                        that.parent().addClass("lit-active");
                    }
                })
            })

        }

        postDirectoryBuild();
        initRelationship();


        var dir = $("#toc");
        var postdiv = $(".article-content");
        var offsetLength = dir.offset().top;
        $(document).scroll(function () {
            var distance = offsetLength - $(window).scrollTop();

            if (distance <= 0) {
                if (!dir.hasClass('directory-fixed')) {
                    dir.addClass('directory-fixed');
                }
            } else if (distance > 0) {
                if (dir.hasClass('directory-fixed')) {
                    dir.removeClass('directory-fixed');
                }
            }
        });
    });

    function initRelationship() {
        var win = $(window);
        var anchors = $('#toc').find('a');
        var offset = 200; //偏移量看情况调
        win.on('scroll', function () {
            var scrollTop = win.scrollTop();
            anchors.each(function (i, v) {
                var that = $(v);
                var id = that.attr('href');
                var target = $(id);

                if (scrollTop >= target.offset().top - offset) {
                    anchors.removeClass('active');
                    anchors.parent().removeClass("lit-active");
                    that.addClass('active');
                    that.parent().addClass("lit-active");
                }
            })
        })

    }

    function tocFixed() {
        var dir = $("#toc");
        var postdiv = $(".article-content");
        var offsetLength = dir.offset().top;
        $(document).scroll(function () {
            var distance = offsetLength - $(window).scrollTop();

            if (distance <= 0) {
                if (!dir.hasClass('directory-fixed')) {
                    dir.addClass('directory-fixed');
                }
            } else if (distance > 0) {
                if (dir.hasClass('directory-fixed')) {
                    dir.removeClass('directory-fixed');
                }
            }
        });
    }

</script>
<style>
    #toc {
        /*position: fixed;*/
        /*left: 44%;*/
        display: inline-block;
        text-align: left;
        margin-top: 20px;
        padding-bottom: 20px;
        /*width: 260px;*/
        /*margin-left: 330px;*/
    }

    .toc-fixed {
        position: fixed;
        z-index: 0;
        left: 50%;
        display: inline-block;
        text-align: left;
        width: 260px;
        top: 40px;
    }

    #toc > ul:before {
        content: "";
        position: absolute;
        top: 0;
        left: 0px;
        bottom: 0;
        width: 3px;
        opacity: .2;
        z-index: 1;

    }

    #toc li, #toc ul {
        margin: 0;
        padding-left: 0;
        list-style: none;
        padding-top: 0.3rem;
    }

    #toc > ul {
        position: relative;
    }


    #toc > ul li::before {
        position: relative;
        top: 0;
        left: -2px;
        display: inline-block;
        width: 7px;
        height: 7px;
        content: '';
        border-radius: 50%;
        z-index: 100;
        opacity: 0.5;
        vertical-align: middle;
    }

    #toc > ul > li:hover::before {
        opacity: 1;
    }

    .lit-active::before {
        opacity: 1 !important;
    }

    #toc > ul > li > a {
        font-weight: bold;
    }

    #toc ul li a {
        display: inline-table;
        white-space: -moz-pre-wrap;
        vertical-align: middle;
    }

    #toc ul li a {
        margin-left: -5px;
        white-space: nowrap;
        width: 200px;
        padding-left: 15px;
        display: inline-block;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;

    }

    #toc ul li ul li a {
        margin-left: -15px;
        white-space: nowrap;
        width: 200px;
        padding-left: 30px;
        display: inline-block;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    #toc ul li ul li ul li a {
        display: inline-block;
        margin-left: -15px;
        padding-left: 35px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        width: 200px;
    }


    .directory-fixed {
        position: fixed;
        top: 0rem;
    }
</style>
