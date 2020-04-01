<nav class="navbar navbar-main  is-paddingless ">
    <div class="container ">
        <div class="navbar-brand transparent">
            <a class="navbar-item navbar-logo" href="${servePath}" rel="start">
                <img src="${faviconURL}" alt="${blogTitle!}" height="28"/>
                &nbsp;${blogTitle!}
            </a>
            <span class="navbar-burger burger" data-target="navMenu">
                    <span></span>
                    <span></span>
                    <span></span>
                </span>
        </div>
        <div id="navMenu" class="navbar-menu transparent">
            <div class="navbar-start transparent">
                <div class="navbar-start">
                    <#list pageNavigations as page>
                        <a class="navbar-item" href="${page.pagePermalink}" target="${page.pageOpenTarget}"
                           rel="section">
                            <#if page.pageIcon !=''><img class="page-icon" src="${page.pageIcon}"></#if>
                            ${page.pageTitle}
                        </a>
                    </#list>
                </div>
            </div>
            <div class="navbar-end">

                <#if isLoggedIn>
                    <a class="navbar-item" href="${servePath}/admin-index.do#main" title="${adminLabel}">
                        ${adminLabel}
                    </a>
                    <a class="navbar-item" href="${logoutURL}">
                        ${logoutLabel}
                    </a>
                <#else>
                    <a class="navbar-item" href="${servePath}/start">
                        ${startToUseLabel}
                    </a>
                </#if>

            </div>
        </div>
</nav>


