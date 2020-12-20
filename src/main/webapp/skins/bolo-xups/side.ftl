<!-- 侧栏部分 -->
<aside class="sidebar">
    <section class="widget">
        <h3 class="widget-hd"><strong>文章搜索</strong></h3>
        <div class="search-form">
            <form
                    id="searchForm"
                    method="GET"
                    action="${servePath}/search"
                    target="_blank"
            >
                <input
                        id="searchKeyword"
                        type="text"
                        class="form-control"
                        placeholder="输入关键字搜索"
                        autocomplete="false"
                        name="keyword"
                />
                <input id="searchButton" class="btn" type="submit" value="搜索"/>
            </form>
        </div>
    </section>

    <section class="widget">
        <h3 class="widget-hd"><strong>文章分类</strong></h3>
        <!-- 文章分类 -->
        <ul class="widget-bd">
            <#list mostUsedCategories as category>
                <li>
                    <a href="${servePath}/category/${category.categoryURI}">
                        ${category.categoryTitle}
                    </a>
                    <span class="badge">(${category.categoryPublishedArticleCount})</span>
                    </a>
                </li>
            </#list>
        </ul>
    </section>

    <section class="widget">
        <h3 class="widget-hd"><strong>热门标签</strong></h3>
        <!-- 文章标签 -->
        <div class="widget-bd tag-wrap">
            <a class="tag-item" href="/tags/HTTPS/" title="HTTPS">HTTPS (1)</a>
            <a class="tag-item" href="/tags/静态博客/" title="静态博客"
            >静态博客 (2)</a
            >
            <a class="tag-item" href="/tags/js/" title="js">js (4)</a>
            <a class="tag-item" href="/tags/eslint/" title="eslint">eslint (1)</a>
            <a class="tag-item" href="/tags/gulp/" title="gulp">gulp (1)</a>

            <a class="tag-item" href="/tags/Electron/" title="Electron"
            >Electron (1)</a
            >

            <a class="tag-item" href="/tags/构建工具/" title="构建工具"
            >构建工具 (1)</a
            >

            <a class="tag-item" href="/tags/文件上传/" title="文件上传"
            >文件上传 (1)</a
            >

            <a class="tag-item" href="/tags/content-type/" title="content-type"
            >content-type (1)</a
            >

            <a class="tag-item" href="/tags/Hexo/" title="Hexo">Hexo (3)</a>

            <a class="tag-item" href="/tags/github-pages/" title="github pages"
            >github pages (1)</a
            >

            <a class="tag-item" href="/tags/css/" title="css">css (1)</a>

            <a class="tag-item" href="/tags/编码规范/" title="编码规范"
            >编码规范 (2)</a
            >

            <a class="tag-item" href="/tags/前端/" title="前端">前端 (3)</a>

            <a class="tag-item" href="/tags/Github/" title="Github">Github (1)</a>

            <a class="tag-item" href="/tags/Git/" title="Git">Git (1)</a>

            <a class="tag-item" href="/tags/新博客/" title="新博客">新博客 (1)</a>

            <a class="tag-item" href="/tags/jQuery/" title="jQuery">jQuery (1)</a>

            <a class="tag-item" href="/tags/浏览器高度/" title="浏览器高度"
            >浏览器高度 (1)</a
            >

            <a class="tag-item" href="/tags/js类型/" title="js类型">js类型 (1)</a>

            <a class="tag-item" href="/tags/隐式转换/" title="隐式转换"
            >隐式转换 (1)</a
            >

            <a class="tag-item" href="/tags/HTTP/" title="HTTP">HTTP (1)</a>

            <a class="tag-item" href="/tags/网络状态码/" title="网络状态码"
            >网络状态码 (1)</a
            >

            <a class="tag-item" href="/tags/LeetCode/" title="LeetCode"
            >LeetCode (31)</a
            >

            <a class="tag-item" href="/tags/算法/" title="算法">算法 (32)</a>

            <a class="tag-item" href="/tags/Mac-OSX/" title="Mac OSX"
            >Mac OSX (2)</a
            >

            <a class="tag-item" href="/tags/命令大全/" title="命令大全"
            >命令大全 (1)</a
            >

            <a class="tag-item" href="/tags/Mongoose/" title="Mongoose"
            >Mongoose (1)</a
            >

            <a class="tag-item" href="/tags/Node-js/" title="Node.js"
            >Node.js (4)</a
            >

            <a class="tag-item" href="/tags/开源许可证/" title="开源许可证"
            >开源许可证 (1)</a
            >

            <a class="tag-item" href="/tags/Mac/" title="Mac">Mac (2)</a>

            <a class="tag-item" href="/tags/开机变慢/" title="开机变慢"
            >开机变慢 (1)</a
            >

            <a class="tag-item" href="/tags/MongoDB/" title="MongoDB"
            >MongoDB (1)</a
            >

            <a class="tag-item" href="/tags/react/" title="react">react (1)</a>

            <a class="tag-item" href="/tags/react-native/" title="react-native"
            >react-native (1)</a
            >

            <a class="tag-item" href="/tags/iOS/" title="iOS">iOS (1)</a>

            <a class="tag-item" href="/tags/npm/" title="npm">npm (3)</a>

            <a class="tag-item" href="/tags/node/" title="node">node (1)</a>

            <a class="tag-item" href="/tags/MySQL/" title="MySQL">MySQL (1)</a>
        </div>
    </section>

    <!-- 友情链接 -->
    <section class="widget">
        <h3 class="widget-hd"><strong>友情链接</strong></h3>
        <!-- 文章分类 -->
        <ul class="widget-bd">
            <li>
                <a
                        href="https://github.com/front-end-pigs/blog"
                        target="_blank"
                        title="Github博客"
                >Github博客</a
                >
            </li>
        </ul>
    </section>
</aside>
<!-- / 侧栏部分 -->