<#--

    Solo - A small and beautiful blogging system written in Java.
    Copyright (c) 2010-present, b3log.org

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
    <link href="${staticServePath}/js/bolo/start-style/css/font.css" rel="stylesheet" />
    <link rel="stylesheet" href="${staticServePath}/js/bolo/start-style/css/font-awesome.min.css" />
    <link href="${staticServePath}/js/bolo/start-style/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${staticServePath}/js/bolo/start-style/css/now-ui-kit.css?v=1.1.0" rel="stylesheet" />
    <link href="${staticServePath}/js/bolo/start-style/css/demo.css" rel="stylesheet" />
    <link rel="icon" type="image/png" href="${faviconURL}"/>
    <link rel="apple-touch-icon" href="${faviconURL}">
    <link rel="shortcut icon" type="image/x-icon" href="${faviconURL}">
    <title>管理登录</title>
</head>

<body class="login-page sidebar-collapse">
<nav class="navbar navbar-expand-lg bg-primary fixed-top navbar-transparent" color-on-scroll="400">
    <div class="container">
        <div class="dropdown button-dropdown">
            <a href="javascript:void(0);" class="dropdown-toggle" id="navbarDropdown" data-toggle="dropdown">
                <span class="button-bar"></span>
                <span class="button-bar"></span>
                <span class="button-bar"></span>
            </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-header">菠萝博客</a>
                <a class="dropdown-item" href="${servePath}">返回首页</a>
            </div>
        </div>
        <div class="navbar-translate">
            <a class="navbar-brand" href="javascript:void(0);" rel="tooltip" data-placement="bottom">
                管理登录
            </a>
            <button class="navbar-toggler navbar-toggler" type="button" data-toggle="collapse" data-target="#navigation" aria-controls="navigation-index" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-bar bar1"></span>
                <span class="navbar-toggler-bar bar2"></span>
                <span class="navbar-toggler-bar bar3"></span>
            </button>
        </div>
        <div class="collapse navbar-collapse justify-content-end">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" rel="tooltip" title="开源驱动：菠萝博客" data-placement="bottom" href="https://github.com/AdlerED/bolo-solo" target="_blank">
                        <i class="fa fa-github"></i>
                        <p class="d-lg-none d-xl-none">Bolo on GitHub</p>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="page-header" filter-color="orange">
    <div class="page-header-image" style="background-image:url(https://ftp.stackoverflow.wiki/bolo/bg.png)"></div>
    <div class="container">
        <div class="col-md-4 content-center">
            <div class="card card-login card-plain">
                <form class="form" action="${servePath}/oauth/bolo/login" method="POST">
                    <div class="content">
                        <div class="input-group form-group-no-border input-lg">
                                <span class="input-group-addon" style="padding-right: 12px;">
                                    <i class="now-ui-icons users_circle-08"></i>
                                </span>
                            <input type="text" id="username" name="username" class="form-control" placeholder="管理员用户名" />
                        </div>
                        <div class="input-group form-group-no-border input-lg">
                                <span class="input-group-addon" style="padding-right: 12px;">
                                    <i class="now-ui-icons text_caps-small"></i>
                                </span>
                            <input type="password" id="password" name="password" class="form-control" placeholder="管理员密码"  />
                        </div>
                    </div>
                    <div class="footer text-center">
                        <button class="btn btn-primary btn-round btn-lg btn-block" id="loginBtn">登录</button>
                    </div>
                </form>
                <span id="status"></span>
            </div>
        </div>
    </div>
    <script>
        status = '${data}';
        if (status === '0') {
            document.getElementById('status').innerHTML = '用户名或密码错误';
        }
        if (status === '-1') {
            document.getElementById('status').innerHTML = '您的博客尚未初始化<br>请设定管理后台用户名和密码';
            document.getElementById('loginBtn').innerHTML = '开始初始化';
            document.getElementById('password').setAttribute("type", "text");
        }
        document.getElementById('loginBtn').onclick = function () {
            document.getElementById('loginBtn').innerHTML = '<i class="fa fa-circle-o-notch fa-spin"></i> 登录中';
        }
    </script>
    <footer class="footer">
        <div class="container">
            <div class="copyright">
                <span id="description"></span>
                Powered by <a href="https://github.com/AdlerED/bolo-solo" target="_blank">菠萝博客 Bolo</a> ${version}
                <br>
                由 <a href="https://github.com/AdlerED/bolo-solo" target="_blank">菠萝 Sir</a> 挑选优美的「无版权背景图」
            </div>
        </div>
    </footer>
</div>
</body>
<script src="${staticServePath}/js/bolo/start-style/js/core/jquery.3.2.1.min.js" type="text/javascript"></script>
<script src="${staticServePath}/js/bolo/start-style/js/core/popper.min.js" type="text/javascript"></script>
<script src="${staticServePath}/js/bolo/start-style/js/core/bootstrap.min.js" type="text/javascript"></script>
<script src="${staticServePath}/js/bolo/start-style/js/plugins/bootstrap-switch.js"></script>
<script src="${staticServePath}/js/bolo/start-style/js/plugins/nouislider.min.js" type="text/javascript"></script>
<script src="${staticServePath}/js/bolo/start-style/js/plugins/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${staticServePath}/js/bolo/start-style/js/plugins/jquery.sharrre.js" type="text/javascript"></script>
<script src="${staticServePath}/js/bolo/start-style/js/now-ui-kit.js?v=1.1.0" type="text/javascript"></script>
<script>
    $('#description').load('https://ftp.stackoverflow.wiki/bolo/description.html');
</script>
</html>
