<#--

    Bolo - A stable and beautiful blogging system based in Solo.
    Copyright (c) 2020-present, https://github.com/bolo-blog

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
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no' name='viewport' />
    <link href="https://ftp.stackoverflow.wiki/bolo/start/css/font.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://ftp.stackoverflow.wiki/bolo/start/css/font-awesome.min.css" />
    <link href="https://ftp.stackoverflow.wiki/bolo/start/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://ftp.stackoverflow.wiki/bolo/start/css/now-ui-kit.css?v=1.1.0" rel="stylesheet" />
    <link href="https://ftp.stackoverflow.wiki/bolo/start/css/demo.css" rel="stylesheet" />
    <link rel="icon" type="image/png" href="${faviconURL}"/>
    <link rel="apple-touch-icon" href="${faviconURL}">
    <link rel="shortcut icon" type="image/x-icon" href="${faviconURL}">
    <title>搜索 ${keyword}</title>
    <style>
        .fixedBg{
            min-height: 1024px;
            background-position: center center;
            background-repeat: no-repeat !important;
            background-attachment: fixed;
            background-size: cover;
            background: rgba(109, 150, 230, 0.8);
            background: -webkit-linear-gradient(90deg, rgba(241, 135, 110, 0.75), rgba(41, 116, 178, 0.75));
            background: -o-linear-gradient(90deg, rgba(241, 135, 110, 0.75), rgba(41, 116, 178, 0.75));
            background: -moz-linear-gradient(90deg, rgba(241, 135, 110, 0.75), rgba(41, 116, 178, 0.75));
            background: linear-gradient(0deg, rgba(241, 135, 110, 0.75), rgba(41, 116, 178, 0.75));
        }
        a {
            color: #ffffff;
        }
    </style>
</head>

<body class="fixedBg sidebar-collapse">
<nav class="navbar navbar-expand-lg bg-primary fixed-top navbar-transparent" color-on-scroll="400">
    <div class="container">
        <div class="navbar-translate">
            <button class="navbar-toggler navbar-toggler" type="button" data-toggle="collapse" data-target="#navigation" aria-controls="navigation-index" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-bar bar1"></span>
                <span class="navbar-toggler-bar bar2"></span>
                <span class="navbar-toggler-bar bar3"></span>
            </button>
        </div>

        <div class="card card-login card-plain" style="padding-top: 20px;">
            <input class="form-control" style="color: #ffffff; background-color: transparent; border: 1px solid #E3E3E3;" value="${keyword}" id="keyword" type="text" onkeypress="if(event.keyCode===13){window.location.href='${servePath}/search?keyword=' + document.getElementById('keyword').value}" placeholder="输入搜索内容，按回车搜索">
        </div>

        <div class="collapse navbar-collapse justify-content-end">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" rel="tooltip" title="返回主页" data-placement="bottom" href="${staticServePath}/">
                        <i class="fa fa-home"></i>
                        <p class="d-lg-none d-xl-none">返回主页</p>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="text-center" style="margin-top: 150px; padding: 0px 24px 0px 24px;">
    <div>
        <#list articles as article>
            <span style="font-weight: bold"><a style="color: white; font-size: 18px" href="${servePath}${article.articlePermalink}">
                ${article.articleTitle}
            </a></span>
            <div style="margin: 5px 50px; color: #eaeaea; font-size: 13px">&nbsp;&nbsp;
                <#if article.articleAbstractText?length gt 230>
                    ${article.articleAbstractText[0..230]} ......
                <#else>
                    ${article.articleAbstractText}
                </#if>
            </div>
            <span style="color: #fafafa; font-size: 13px">
                <#list article.articleTags?split(",") as articleTag>
                    <a href="${servePath}/tags/${articleTag?url('UTF-8')}">${articleTag}</a>&nbsp;&nbsp;
                </#list>
                |&nbsp;
                ${article.articleCreateDate?string("yyyy-MM-dd")}
                &nbsp;
                ${article.articleCommentCount} ${commentLabel}
                &nbsp;
                ${article.articleViewCount} ${viewLabel}
            </span>
            <br><br>
        </#list>
    </div>
    <#if 0 != articles?size>
        <nav class="search__pagination" style="font-size: 16px; letter-spacing: 4px; color: #e0e0e0">
            <#if 1 != pagination.paginationPageNums?first>
                <a href="${servePath}/search?keyword=${keyword}&p=${pagination.paginationCurrentPageNum - 1}">&laquo;</a>
                <a href="${servePath}/search?keyword=${keyword}&p=1">1</a> <span class="page-number">...</span>
            </#if>
            <#list pagination.paginationPageNums as paginationPageNum>
                <#if paginationPageNum == pagination.paginationCurrentPageNum>
                    <span>${paginationPageNum}</span>
                <#else>
                    <a href="${servePath}/search?keyword=${keyword}&p=${paginationPageNum}">${paginationPageNum}</a>
                </#if>
            </#list>
            <#if pagination.paginationPageNums?last != pagination.paginationPageCount>
                <span>...</span>
                <a href="${servePath}/search?keyword=${keyword}&p=${pagination.paginationPageCount}">${pagination.paginationPageCount}</a>
                <a href="${servePath}/search?keyword=${keyword}&p=${pagination.paginationCurrentPageNum + 1}">&raquo;</a>
            </#if>
        </nav>
    <#else>
        <p style="color: #FFFFFF">无搜索结果</p>
    </#if>
    <br><br><br><br>
</div>
<div class="navbar-nav fixed-bottom navbar-transparent" style="color: white;">
    <footer class="footer">
        <div class="container">
            <div class="copyright">
                Powered by <a href="https://github.com/adlered/bolo-solo" target="_blank">Bolo</a> ${boloVersion}
            </div>
        </div>
    </footer>
</div>
</body>
<script src="https://ftp.stackoverflow.wiki/bolo/start/js/core/jquery.3.2.1.min.js" type="text/javascript"></script>
<script src="https://ftp.stackoverflow.wiki/bolo/start/js/core/popper.min.js" type="text/javascript"></script>
<script src="https://ftp.stackoverflow.wiki/bolo/start/js/core/bootstrap.min.js" type="text/javascript"></script>
<script src="https://ftp.stackoverflow.wiki/bolo/start/js/plugins/bootstrap-switch.js"></script>
<script src="https://ftp.stackoverflow.wiki/bolo/start/js/plugins/nouislider.min.js" type="text/javascript"></script>
<script src="https://ftp.stackoverflow.wiki/bolo/start/js/plugins/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="https://ftp.stackoverflow.wiki/bolo/start/js/plugins/jquery.sharrre.js" type="text/javascript"></script>
<script src="https://ftp.stackoverflow.wiki/bolo/start/js/now-ui-kit.js?v=1.1.0" type="text/javascript"></script>
</html>
