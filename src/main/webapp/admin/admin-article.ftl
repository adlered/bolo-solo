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
<div class="form">
    <div>
        <label>${title1Label}</label>
        <input id="title" type="text"/>
    </div>
    <div>
        <label>${content1Label}</label>
        <div class="fn__right">
            <label for="articleThumbnail" style="margin-bottom: 0">
                ${useTumbnailLabel}
                <input type="checkbox" style="vertical-align: middle;"
                       id="articleThumbnail" onclick="$('.article__thumbnail').slideToggle()" />
            </label>
        </div>
        <div class="fn__clear"></div>
        <div class="article__thumbnail">
            <div class="thumbnail__img"></div>
            <button id="articleThumbnailBtn">${chageLabel}</button>
        </div>
        <div>
            <div id="articleContent" name="articleContent"
                  style="height: 500px;width:100%;"></div>
        </div>
    </div>
    <div>
        <label>${tags1WithTips1Label}</label>
        <div class="tag__select">
            <input id="tag" type="text"/>
        </div>
    </div>
    <div>
        <label>${category1Label}</label>
        <select id="categorySelector">
            <option value="">无分类</option>
            <#list categories as category>
                <option value="${category.oId}">${category.categoryTitle} (${category.categoryPublishedArticleCount})</option>
            </#list>
        </select>
    </div>
    <div>
        <label>${createDateLabel}</label>
        <input id="createDate" type="datetime-local"/>
    </div>
    <div class="comment-content">
        <label>${abstract1Label}</label>
        <div>
            <div id="abstract" style="height: 200px;width: 100%;" name="abstract"></div>
        </div>
    </div>
    <div class="fn__flex">
        <div class="fn__flex fn__flex-1" style="align-items: center">
            <label for="permalink" class="permalink__label" style="margin-bottom: 0">${permalink1Label}</label>
            <input id="permalink" class="fn__flex-1" type="text" style="margin: 0 12px 0 6px;" />
        </div>
        <div class="fn__right viewpwd__panel">
            <label for="viewPwd">${articleViewPwd1Label}</label>
            <input id="viewPwd" type="text" style="width: 156px" />
        </div>
    </div>
    <div>
        <span class="signs">
            <label>${sign1Label}</label>
            <button style="margin-left: 0px;" id="articleSign1" class="selected">${signLabel}1</button>
            <button id="articleSign2">${signLabel}2</button>
            <button id="articleSign3">${signLabel}3</button>
            <button id="articleSign0">${noSignLabel}</button>
        </span>
        <div class="fn__right article-commentable__panel">
            <label class="checkbox">
                <input type="checkbox" id="articleCommentable" checked />
                ${allowCommentLabel}
            </label>
            &nbsp;
            <#if b3logEnabled || fishpiEnabled>
            <span id="postToCommunityPanel">
            <#else>
            <span id="postToCommunityPanel" style="display: none">
            </#if>
                <label class="checkbox">
                    <input id="postToCommunity" type="checkbox" />
                    <a href="https://doc.stackoverflow.wiki/web/#/7?page_id=46" target="_blank">${syncToCommunityLabel}</a>
                </label>
            </span>
        </div>
        <div class="fn__clear"></div>
    </div>
    <div class="fn__right">
        <button class="marginRight12" class="marginRight12" id="cancelDo">${cancelLabel}</button>
        <button id="unSubmitArticle" class="fn__none marginRight12" onclick="admin.article.unPublish();">${unPublishLabel}</button>
        <button class="marginRight12" id="saveArticle">${saveLabel}${draftListLabel}</button>
        <button id="submitArticle">${publishLabel}</button>
    </div>
    <div class="fn__clear"></div>
</div>
${plugins}