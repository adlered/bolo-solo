<div id="banner_wave_1">
</div>
<div id="banner_wave_2">
</div>
<figure id="centerbg" class="centerbg">
    <div class="focusinfo no-select">
        <h1 class="center-text glitch is-glitching Ubuntu-font" data-text="${blogTitle}">${blogTitle}</h1>
        <div class="header-info">
            <p>
                <#if blogSubtitle??>
                    <i class="fa fa-quote-left"></i> ${blogSubtitle} <i class="fa fa-quote-right"></i>
                <#else>
                    <i class="fa fa-quote-left"></i> Nothing here... <i class="fa fa-quote-right"></i>
                </#if>
            </p>
            <div class="top-social_v2">
                <li id="bg-pre"><img class="flipx"
                                     src="https://cdn.jsdelivr.net/gh/moezx/cdn@3.1.9/img/Sakura/images/next-b.svg"/>
                </li>
                <li><a href="https://github.com/mashirozx" target="_blank" class="social-github" title="github"><img
                                src="https://cdn.jsdelivr.net/gh/moezx/cdn@3.1.9/img/Sakura/images/sns/github.png"/></a>
                </li>
                <li><a href="https://weibo.com/mashirozx?is_all=1" target="_blank" class="social-sina" title="sina"><img
                                src="https://cdn.jsdelivr.net/gh/moezx/cdn@3.1.9/img/Sakura/images/sns/sina.png"/></a>
                </li>
                <li><a href="https://t.me/SakurasoNoMashiro" target="_blank" class="social-lofter" title="telegram"><img
                                src="https://cdn.jsdelivr.net/gh/moezx/cdn@3.1.9/img/Sakura/images/sns/telegram.svg"/></a>
                </li>
                <li><a rel="me" href="https://hello.2heng.xin/@mashiro" target="_blank" class="social-lofter"
                       title="Mastodon"><img
                                src="https://2heng.xin/wp-content/themes/Sakura/images/Mastodon_Logotype_Simple.svg"/></a>
                </li>
                <li><a href="https://music.163.com/m/user/home?id=2655217" target="_blank" class="social-wangyiyun"
                       title="CloudMusic"><img
                                src="https://cdn.jsdelivr.net/gh/moezx/cdn@3.1.9/img/Sakura/images/sns/wangyiyun.png"/></a>
                </li>
                <li><a href="https://twitter.com/2hengxin" target="_blank" class="social-wangyiyun" title="Twitter"><img
                                src="https://cdn.jsdelivr.net/gh/moezx/cdn@3.1.9/img/Sakura/images/sns/twitter.png"/></a>
                </li>
                <li><a href="https://www.zhihu.com/people/young-dolphin" target="_blank" class="social-wangyiyun"
                       title="Zhihu"><img
                                src="https://cdn.jsdelivr.net/gh/moezx/cdn@3.1.9/img/Sakura/images/sns/zhihu.png"/></a>
                </li>
                <li><a onclick="mail_me()" class="social-wangyiyun" title="E-mail"><img
                                src="https://cdn.jsdelivr.net/gh/moezx/cdn@3.1.9/img/Sakura/images/sns/email.svg"/></a>
                </li>
                <li id="bg-next"><img src="https://cdn.jsdelivr.net/gh/moezx/cdn@3.1.9/img/Sakura/images/next-b.svg"/>
                </li>
            </div>
        </div>
    </div>
</figure>
<div id="video-container" style="">
    <video id="bgvideo" class="video" video-name="" src="" width="auto" preload="auto"></video>
    <div id="video-btn" class="loadvideo videolive">
    </div>
    <div id="video-add">
    </div>
    <div class="video-stu">
    </div>
</div>
<div class="headertop-down faa-float animated" onclick="headertop_down()">
    <span><i class="fa fa-chevron-down" aria-hidden="true"></i></span>
</div>
</div>
<div id="page" class="site wrapper">
    <header class="site-header no-select is-homepage" role="banner">
        <div class="site-top">
            <div class="site-branding">
                <span class="site-title"><span class="logolink moe-mashiro"><a href="${servePath}/" alt="${blogTitle}"><ruby><span
                                        class="sakuraso">${userName}</span><span class="no">の</span><span
                                        class="shironeko">${blogTitle}</span><rp></rp><rt class="chinese-font">${blogSubtitle}</rt><rp></rp></ruby></a></span></span>
            </div>
            <div class="lower-cantiner">
                <div class="lower">
                    <div id="show-nav" class="showNav mobile-fit">
                        <div class="line line1">
                        </div>
                        <div class="line line2">
                        </div>
                        <div class="line line3">
                        </div>
                    </div>
                    <nav class="mobile-fit-control hide">
                        <ul id="menu-new" class="menu">
                            <li><a href="/" aria-current="page"><span class="faa-parent animated-hover"><i
                                                class="fa fa-home"></i> 首页</span></a></li>
                            <li><a href="/" aria-current="page"><span class="faa-parent animated-hover"><i
                                                class="fa fa-link"></i> 友链</span></a></li>
                            <li><a href="/" aria-current="page"><span class="faa-parent animated-hover"><i
                                                class="fa fa-tag"></i> 标签</span></a></li>
                            <#list pageNavigations as page>
                                <li><a href="${page.pagePermalink}" target="${page.pageOpenTarget}"><span
                                                class="faa-parent animated-hover">
                                            <#if page.pageIcon?contains("/")>
                                                <img class="icon" style="height: 16px; width: 16px;"
                                                     src="${page.pageIcon}">
                                            <#else>
                                                <i class="fa fa-${page.pageIcon}"></i>
                                            </#if> ${page.pageTitle}</span></a></li>
                            </#list>
                        </ul>
                    </nav>
                </div>
            </div>
            <div class="header-user-avatar">
                <a href="https://2heng.xin/wp-login.php"><img class="faa-shake animated-hover"
                                                              src="https://cdn.jsdelivr.net/gh/moezx/cdn@3.1.9/img/Sakura/images/none.png"
                                                              width="30" height="30"></a>
                <div class="header-user-menu">
                    <div class="herder-user-name no-logged">
                        Whether to <a href="https://2heng.xin/login/" target="_blank"
                                      style="color:#333;font-weight:bold;text-decoration:none">log in</a> now?
                    </div>
                </div>
            </div>
            <div class="searchbox">
                <i class="iconfont js-toggle-search iconsearch icon-search"></i>
            </div>
        </div>
    </header>
    <div class="blank">
    </div>