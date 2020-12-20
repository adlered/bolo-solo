<!-- 侧栏部分 -->
<aside class="sidebar">
    <#if interactive == "on">
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
    </#if>

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
            <#list mostUsedTags as tag>
                <a class="tag-item" href="${servePath}/tags/${tag.tagTitle?url('UTF-8')}" title="${tag.tagTitle}">${tag.tagTitle} (${tag.tagPublishedRefCount!})</a>
            </#list>
        </div>
    </section>

    <!-- 友情链接 -->
    <section class="widget">
        <h3 class="widget-hd"><strong>友情链接</strong></h3>
        <!-- 文章分类 -->
        <ul class="widget-bd">
            <#list links as link>
                <li>
                    <a
                            href="${link.linkAddress}"
                            target="_blank"
                            title="${link.linkTitle}"
                    >
                        ${link.linkTitle}
                    </a>
                </li>
            </#list>
        </ul>
    </section>
</aside>
<!-- / 侧栏部分 -->