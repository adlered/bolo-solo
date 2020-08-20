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
                <li>
                    <a class="nav-link" rel="tooltip" title="中国智造" data-placement="bottom" href="https://baike.baidu.com/item/%E4%B8%AD%E5%9B%BD/1122445" target="_blank">
                        <img src="https://ftp.stackoverflow.wiki/bolo/start/img/flags/CN.png" />
                        <p class="d-lg-none d-xl-none">中国智造</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" rel="tooltip" title="开源驱动：菠萝博客" data-placement="bottom" href="https://github.com/adlered/bolo-solo" target="_blank">
                        <i class="fa fa-github"></i>
                        <p class="d-lg-none d-xl-none">Bolo on GitHub</p>
                    </a>
                </li>
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
<div class="page-header" filter-color="orange">
    <div class="page-header-image" id="bgHeader"></div>
    <div class="container">
        <div class="col-md-4 content-center">
            <div class="card card-login card-plain">
                <form class="form" action="${servePath}/oauth/bolo/login" method="POST" id="loginForm">
                    <div class="content">
                        <div class="input-group form-group-no-border input-lg">
                                <span class="input-group-addon" style="padding-right: 12px;">
                                    <i class="fa fa-user-circle-o"></i>
                                </span>
                            <input type="text" id="username" name="username" class="form-control" placeholder="管理员用户名" />
                        </div>
                        <div class="input-group form-group-no-border input-lg">
                                <span class="input-group-addon" style="padding-right: 12px;">
                                    <i class="fa fa-key"></i>
                                </span>
                            <input type="password" id="password" name="password" class="form-control" placeholder="管理员密码"  />
                        </div>
                    </div>
                    <div class="footer text-center">
                        <button class="btn btn-primary btn-round btn-lg btn-block" id="loginBtn" type="button">登录</button>
                    </div>
                </form>
                <span id="status"></span>
            </div>
        </div>
    </div>
    <script>
        status = '${data}';
        if (status === '2') {
            document.getElementsByTagName("title")[0].innerText = '快速迁移到 Bolo';
            document.getElementById('status').innerHTML = '检测到您是Solo用户 ❤️<br>请按照以下步骤进行快速迁移：<br><b>1. 备份您的数据库</b><br><b>2. 在上方填写您想设定的管理员用户名和密码</b><br><b>3. 点击开始迁移按钮，稍等片刻即可</b>';
            document.getElementById('loginBtn').innerHTML = '开始迁移';
            document.getElementsByClassName("navbar")[0].remove();
            setTimeout(function () {
                document.getElementsByClassName("footer")[1].remove();
            }, 1000);
            document.getElementById('loginBtn').onclick = function () {
                if (document.getElementById('username').value !== '' && document.getElementById('password').value !== '') {
                    let pwdRegex = new RegExp('(?=.*[0-9])(?=.*[a-zA-Z]).{8,30}');
                    if (!pwdRegex.test(document.getElementById('password').value)) {
                        alert("密码强度过低！请使用8-30位密码，必须包含字母和数字。");
                    } else {
                        document.getElementById('loginBtn').innerHTML = '<i class="fa fa-spinner fa-pulse"></i> 正在快速迁移中，请稍候';
                        document.getElementById('loginForm').submit();
                    }
                } else {
                    alert('请填写用户名和密码！');
                }
            }
        } else {
            if (status === '0') {
                document.getElementById('status').innerHTML = '用户名或密码错误';
            }
            if (status === '-1') {
                document.getElementsByTagName("title")[0].innerText = '欢迎使用 Bolo';
                document.getElementById('status').innerHTML = '部署成功！欢迎使用菠萝博客 ❤️<br>您的博客尚未初始化<br>请在输入框中设定管理后台用户名和密码<br>点击“开始初始化”按钮初始化你的菠萝博客';
                document.getElementById('loginBtn').innerHTML = '开始初始化';
                document.getElementById('password').setAttribute("type", "text");
            }
            document.getElementById('loginBtn').onclick = function () {
                if (document.getElementById('username').value !== '' && document.getElementById('password').value !== '') {
                    if (status === '-1') {
                        let pwdRegex = new RegExp('(?=.*[0-9])(?=.*[a-zA-Z]).{8,30}');
                        if (!pwdRegex.test(document.getElementById('password').value)) {
                            alert("密码强度过低！请使用8-30位密码，必须包含字母和数字。");
                        } else {
                            document.getElementById('loginBtn').innerHTML = '<i class="fa fa-spinner fa-pulse"></i> 正在初始化，请稍候';
                            document.getElementById('loginForm').submit();
                        }
                    } else {
                        document.getElementById('loginBtn').innerHTML = '<i class="fa fa-spinner fa-pulse"></i> 登录中';
                        document.getElementById('loginForm').submit();
                    }
                } else {
                    document.getElementById('status').innerHTML = '请填写用户名和密码！';
                }
            }
        }
    </script>
    <footer class="footer">
        <div class="container">
            <div class="copyright">
                <span id="description"></span>
                Powered by <a href="https://github.com/adlered/bolo-solo" target="_blank">Bolo</a> ${boloVersion}
                <br>
                由 <a href="https://github.com/adlered/bolo-solo" target="_blank">菠萝 Sir</a> 挑选优美的「无版权背景图」
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
<script type="text/javascript">
    var timeStamp = Date.parse(new Date());
    $('#description').load('https://programmingwithlove.stackoverflow.wiki/loginDescription?version=' + timeStamp);
    $('#bgHeader').prop('style', 'background-image:url(https://programmingwithlove.stackoverflow.wiki/loginBg?version=' + timeStamp + ')');
</script>
</html>
