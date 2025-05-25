<#--

    Bolo - A stable and beautiful blogging system based in Solo.
    Copyright (c) 2020-present, https://github.com/bolo-blog

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
<#include "macro-page.ftl">

<@sidePage htmlTitle="${allTagsLabel} - ${blogTitle}" pageTitle="${sumLabel} ${tags?size} ${tagLabel}">
   <div class="rowSmallItemLayout">
    <#list tags as tag>
    <a rel="tag" data-count="${tag.tagPublishedRefCount}" class="item"
      href="${servePath}/tags/${tag.tagTitle?url('UTF-8')}">
        <span class="name">${tag.tagTitle}</span>
        (<b>${tag.tagPublishedRefCount}</b>)
    </a>
    </#list>
  </div>
</@sidePage>
