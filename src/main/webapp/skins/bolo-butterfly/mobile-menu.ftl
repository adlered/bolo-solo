<#--

    Solo - A small and beautiful blogging system written in Java.
    Copyright (c) 2010-present, b3log.org

    Solo is licensed under Mulan PSL v2.
    You can use this software according to the terms and conditions of the Mulan PSL v2.
    You may obtain a copy of Mulan PSL v2 at:
            http://license.coscl.org.cn/MulanPSL2
    THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
    See the Mulan PSL v2 for more details.

-->
<div id="mobile-sidebar">
    <div id="menu_mask"></div>
    <div id="mobile-sidebar-menus">
        <div class="mobile_author_icon"><img class="avatar-img"
                                             src="${adminUser.userAvatar}"
                                             onerror="onerror=null;src='${staticServePath}/skins/${skinDirName}/images/friend_404.gif'" alt="avatar">
        </div>
        <div class="mobile_post_data">
            <div class="mobile_data_item is-center">
                <div class="mobile_data_link"><a href="${servePath}/archives/">
                        <div class="headline">文章</div>
                        <div class="length_num">${statistic.statisticPublishedBlogArticleCount}</div>
                    </a></div>
            </div>
            <div class="mobile_data_item is-center">
                <div class="mobile_data_link"><a href="${servePath}/tags/">
                        <div class="headline">标签</div>
                        <div class="length_num">${mostUsedTags?size}</div>
                    </a></div>
            </div>
            <div class="mobile_data_item is-center">
                <div class="mobile_data_link"><a href="${servePath}/categories/">
                        <div class="headline">分类</div>
                        <div class="length_num">${mostUsedCategories?size}</div>
                    </a></div>
            </div>
        </div>
        <hr>
        <div class="menus_items">
            <div class="menus_item"><a class="site-page" href="${servePath}"><i
                            class="fa-fw fas fa-home"></i><span> 主页</span></a></div>
            <div class="menus_item"><a class="site-page" href="${servePath}/archives/"><i
                            class="fa-fw fas fa-archive"></i><span> 时间轴</span></a></div>
            <div class="menus_item"><a class="site-page" href="${servePath}/tags/"><i
                            class="fa-fw fas fa-tags"></i><span> 标签</span></a></div>
            <div class="menus_item"><a class="site-page" href="${servePath}/categories/"><i
                            class="fa-fw fas fa-folder-open"></i><span> 分类</span></a></div>
            <div class="menus_item"><a class="site-page" href="${servePath}/link/"><i
                            class="fa-fw fas fa-link"></i><span> 友情链接</span></a></div>
            <div class="menus_item"><a class="site-page" href="${servePath}/about/"><i
                            class="fa-fw fas fa-heart"></i><span> 关于</span></a></div>
            <div class="menus_item"><a class="site-page"><i class="fa-fw fas fa-list"></i><span> 喜欢</span><i
                            class="fas fa-chevron-down menus-expand"></i></a>
                <ul class="menus_item_child">
                    <li><a class="site-page" href="https://coding.lonuslan.com/music/"><i
                                    class="fa-fw fas fa-music"></i><span> 音乐</span></a></li>
                    <li><a class="site-page" href="https://coding.lonuslan.com/movies/"><i
                                    class="fa-fw fas fa-video"></i><span> 电影</span></a></li>
                </ul>
            </div>
            <div class="menus_item"><a class="site-page"><i class="fa-fw fas fa-tools"></i><span> 工具箱</span><i
                            class="fas fa-chevron-down menus-expand"></i></a>
                <ul class="menus_item_child">
                    <li><a class="site-page" target="_blank" rel="noopener" href="https://valine.lonuslan.com/"><i
                                    class="fa-fw fas fa-comments"></i><span> 评论后台管理</span></a></li>
                    <li><a class="site-page" target="_blank" rel="noopener" href="https://imgcdn.lonuslan.com/"><i
                                    class="fa-fw fas fa-file-image"></i><span> 图片上传</span></a></li>
                    <li><a class="site-page" target="_blank" rel="noopener"
                           href="http://mf.aidso.cn:3312/vhost/index.php"><i class="fa-fw fas fa-cloud"></i><span> 图片后台主机管理</span></a>
                    </li>
                    <li><a class="site-page" target="_blank" rel="noopener" href="https://monitor.lonuslan.com/"><i
                                    class="fa-fw fas fa-hourglass"></i><span> 后台状态监控</span></a></li>
                </ul>
            </div>
        </div>
    </div>
</div>
