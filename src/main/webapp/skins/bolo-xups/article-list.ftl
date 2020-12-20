<#-- Bolo - A stable and beautiful blogging system based in Solo. Copyright (c)
2020, https://github.com/adlered This program is free software: you can
redistribute it and/or modify it under the terms of the GNU Affero General
Public License as published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version. This program is distributed
in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU Affero General Public License for more details. You should have received a
copy of the GNU Affero General Public License along with this program. If not,
see <https://www.gnu.org/licenses/>. -->
<!-- 博客正文 -->
<section class="content">
    <div class="content-main widget">
        <!-- 首页 -->

        <h3 class="widget-hd">
            <strong> 最近动态 </strong>
        </h3>

        <!-- 文章列表 item -->
        <#list articles as article>
            <#if article.articlePutTop>
            <#else>
                <article class="post">
                    <header>
                        <!-- 标签这有且只能显示一个 -->
                        <#if article.categoryURI??>
                            <a class="cat-link"
                               href="${servePath}/category/${article.categoryURI}">${article.articleCategory}</a>
                        </#if>
                        <!-- 文章标题 -->
                        <h3 class="post-title">
                            <a href="${servePath}${article.articlePermalink}">
                                ${article.articleTitle!}
                            </a>
                        </h3>
                    </header>
                    <p class="post-meta">
                        Jelon 发表于
                        <time datetime="2020-08-13T00:00:00.000Z">2020-08-13</time>
                        &nbsp;&nbsp;
                        <span class="post-tags">
            标签：

            <a href="/tags/LeetCode/" title="LeetCode">LeetCode</a> /

            <a href="/tags/算法/" title="算法">算法</a>
          </span>
                    </p>
                </article>
            </#if>
        </#list>
        <article class="post">
            <header>
                <!-- 标签这有且只能显示一个 -->
                <a class="cat-link" href="/categories/算法练习/">算法练习</a>
                <!-- 文章标题 -->
                <h3 class="post-title">
                    <a href="https://jelon.info/posts/leetcode-93/">
                        【每日一题】93. 复原IP地址
                    </a>
                </h3>
            </header>
            <p class="post-meta">
                Jelon 发表于
                <time datetime="2020-08-13T00:00:00.000Z">2020-08-13</time>
                &nbsp;&nbsp;
                <span class="post-tags">
            标签：

            <a href="/tags/LeetCode/" title="LeetCode">LeetCode</a> /

            <a href="/tags/算法/" title="算法">算法</a>
          </span>
            </p>

            <div class="post-content">
                <div class="post-excerpt">
                    给定一个只包含数字的字符串，复原它并返回所有可能的 IP
                    地址格式。有效的 IP 地址正好由四个整数（每个整数位于 0 到 255
                    之间组成），整数之间用 '.' 分隔...

                    <p class="more">
                        <a href="https://jelon.info/posts/leetcode-93/">阅读剩下更多</a>
                    </p>
                </div>
                <div class="post-thumbnail" data-img="">
                    <a
                            href="https://jelon.info/posts/leetcode-93/"
                            title="【每日一题】93. 复原IP地址"
                    >
                        <img
                                class="thumbnail"
                                src="/img/default.png"
                                data-echo="/img/thumbnail/3.jpg"
                                alt="默认配图"
                        />
                    </a>
                </div>
            </div>
        </article>

        <nav class="page-navigator">
        <span class="page-number current">1</span
        ><a class="page-number" href="/page/2/">2</a
            ><a class="page-number" href="/page/3/">3</a
            ><span class="space">&hellip;</span
            ><a class="page-number" href="/page/13/">13</a
            ><a class="extend next" rel="next" href="/page/2/">下页</a>
        </nav>
    </div>
</section>
