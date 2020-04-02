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
<div class="card widget">
    <div class="card-content">
        <h3 class="menu-label">
            ${mostViewCountArticlesLabel}
        </h3>

        <#list mostViewCountArticles as article>
            <article class="media">
                <#if article.articleImg1URL?? && article.articleImg1URL!=''>
                    <a href="${servePath}${article.articlePermalink}" class="media-left">
                        <p class="image is-64x64">
                            <img class="thumbnail" src="${article.articleImg1URL!}" alt="${article.articleTitle!}">
                        </p>
                    </a>
                </#if>
                <div class="media-content">
                    <div class="content">
                        <div style="padding-top: 10px;">

                            <div class="has-text-grey is-size-7 is-uppercase">
                                ${article.articleViewCount}浏览
                            </div>
                        </div>
                        <a href="${servePath}${article.articlePermalink}"
                           class="title has-link-black-ter is-size-6 has-text-weight-normal">${article.articleTitle!}</a>
                    </div>
                </div>
            </article>
        </#list>

    </div>
</div>
<div class="card widget">
    <div class="card-content">
        <div class="menu">
            <h3 class="menu-label">
                ${archiveLabel}
            </h3>
            <ul class="menu-list">
                <#list archiveDates as archiveDate>
                    <li>
                        <a class="level is-marginless"
                           href="${servePath}/archives/${archiveDate.archiveDateYear}/${archiveDate.archiveDateMonth}">
                            <span class="level-start">
                                <span class="level-item">${archiveDate.archiveDateYear}
                                    ${yearLabel}
                                    ${archiveDate.archiveDateMonth}
                                    ${monthLabel}</span>
                            </span>
                            <span class="level-end">
                                <span class="level-item tag">${archiveDate.archiveDatePublishedArticleCount}</span>
                            </span>
                        </a>
                    </li>
                </#list>
            </ul>
        </div>
    </div>
</div>