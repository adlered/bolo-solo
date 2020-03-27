<div id="mo-nav">
    <div class="m-avatar">
        <img src="${adminUser.userAvatar}">
    </div>
    <p style="text-align: center; color: #333; font-weight: 900; font-family: 'Ubuntu', sans-serif; letter-spacing: 1.5px">
        ${blogSubtitle}
    </p>
    <p>
    <div>
        <style>
            .mobile {
                text-align: center;
            }

            .mobile .user__site {
                width: 16px;
                height: 16px;
                display: inline-block;
            }

            .mobile .user__site svg {
                width: 16px;
                height: 16px;
                vertical-align: middle;
            }
        </style>
        <div class="mobile">
            <#include "../../common-template/macro-user_site.ftl"/>
            <@userSite dir=""></@userSite>
        </div>
    </div>
    </p>
    <div class="m-search">
        <form class="m-search-form" method="get" action="${servePath}/search" role="search">
            <input class="m-search-input" type="search" name="keyword" placeholder="搜索..." required>
        </form>
    </div>
    <ul id="menu-new-1" class="menu">
        <li class="current-menu-item"><a href="/" aria-current="page"><span class="faa-parent animated-hover"><i
                            class="fa fa-fort-awesome faa-horizontal" aria-hidden="true"></i> 首页</span></a></li>
        <li><a href="/time-series/"><span class="faa-parent animated-hover"><i class="fa fa-archive faa-shake"
                                                                               aria-hidden="true"></i> 归档</span></a>
            <ul class="sub-menu">
                <li><a href="https://2heng.xin/archives/hacking/"><i class="fa fa-terminal" aria-hidden="true"></i>
                        极客</a></li>
                <li><a href="https://2heng.xin/archives/article/"><i class="fa fa-file-text-o" aria-hidden="true"></i>
                        文章</a></li>
                <li><a href="https://2heng.xin/archives/review/"><i class="fa fa-quote-right" aria-hidden="true"></i> 影评</a>
                </li>
                <li><a href="https://2heng.xin/archives/thingking/"><i class="fa fa-commenting-o"
                                                                       aria-hidden="true"></i> 随想</a></li>
                <li><a target="_blank" rel="noopener noreferrer" href="https://mashiro.top/"><i class="fa fa-book"
                                                                                                aria-hidden="true"></i>
                        笔记</a></li>
            </ul>
        </li>
        <li><a><span class="faa-parent animated-hover"><i class="fa fa-list-ul faa-vertical" aria-hidden="true"></i> 清单</span></a>
            <ul class="sub-menu">
                <li><a href="https://2heng.xin/book-list/"><i class="fa fa-th-list faa-bounce" aria-hidden="true"></i>
                        书单</a></li>
                <li><a href="https://2heng.xin/bangumi/"><i class="fa fa-film faa-vertical" aria-hidden="true"></i>
                        番组</a></li>
                <li><a href="https://2heng.xin/music/"><i class="fa fa-headphones" aria-hidden="true"></i> 歌单</a></li>
                <li><a href="https://2heng.xin/hearthstone-deck-index/"><i class="iconfont icon-Hearthstone"
                                                                           aria-hidden="true"></i> 卡组</a></li>
            </ul>
        </li>
        <li><a href="https://2heng.xin/comment/"><span class="faa-parent animated-hover"><i
                            class="fa fa-pencil-square-o faa-tada" aria-hidden="true"></i> 留言板</span></a></li>
        <li><a href="https://2heng.xin/friends/"><span class="faa-parent animated-hover"><i class="fa fa-link faa-shake"
                                                                                            aria-hidden="true"></i> 友人帐</span></a>
        </li>
        <li><a href="https://2heng.xin/donate/"><span class="faa-parent animated-hover"><i class="fa fa-heart faa-pulse"
                                                                                           aria-hidden="true"></i> 赞赏</span></a>
        </li>
        <li><a href="#"><span class="faa-parent animated-hover"><i class="fa fa-leaf faa-wrench" aria-hidden="true"></i> 关于</span></a>
            <ul class="sub-menu">
                <li><a href="https://2heng.xin/about/"><i class="fa fa-grav" aria-hidden="true"></i> 我？</a></li>
                <li><a href="https://2heng.xin/analytics/"><i class="fa fa-area-chart" aria-hidden="true"></i> 统计</a>
                </li>
                <li><a href="https://2heng.xin/server-status/"><i class="fa fa-heartbeat" aria-hidden="true"></i> 监控</a>
                </li>
                <li><a href="https://2heng.xin/theme-sakura/"><i class="iconfont icon-sakura" aria-hidden="true"></i> 主题</a>
                </li>
                <li><a target="_blank" rel="noopener noreferrer" href="https://2heng.xin/sitemap_index.xml"><i
                                class="fa fa-map-signs" aria-hidden="true"></i> MAP</a></li>
                <li><a target="_blank" rel="noopener noreferrer" href="https://2heng.xin/feed"><i class="fa fa-rss "
                                                                                                  aria-hidden="true"></i>
                        RSS</a></li>
            </ul>
        </li>
        <li><a href="https://2heng.xin/client/"><span class="faa-parent animated-hover"><i
                            class="fa fa-android faa-vertical" aria-hidden="true"></i> 客户端</span></a></li>
        <li><a target="_blank" rel="noopener noreferrer" href="https://hello.2heng.xin/"><span
                        class="faa-parent animated-hover"><i class="fa fa-meetup faa-pulse"
                                                             aria-hidden="true"></i> 后院</span></a></li>
    </ul>
    <p style="text-align: center; font-size: 13px; color: #b9b9b9;">
        © ${year} <a href="${servePath}">${blogTitle}</a><br><br>
        Powered by <a href="https://github.com/adlered/bolo-solo" target="_blank">菠萝博客 Bolo</a><br>
        Theme <a rel="friend" href="https://github.com/mashirozx/Sakura" target="_blank">${skinDirName}</a>
        by <a rel="friend" href="https://hello.2heng.xin/@mashiro" target="_blank">Mashiro</a><br>
        ${footerContent}
    </p>
</div>
<a href="#" class="cd-top faa-float animated "></a>
<button onclick="topFunction()" class="mobile-cd-top" id="moblieGoTop" title="Go to top"><i class="fa fa-chevron-up"
                                                                                            aria-hidden="true"></i>