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
<div class="top-feature-row">
<h1 class="fes-title" style="font-family: 'Ubuntu', sans-serif;"><i class="fa fa-anchor" aria-hidden="true"></i> START:DASH!!</h1>
<#list articles as article>
<#if article.articlePutTop>
    <div class="top-feature-v2">
        <div class="the-feature square from_left_and_right">
            <a href="${servePath}${article.articlePermalink}" target="_blank">
                <div class="img">
                    <img src="${article.articleImg1URL}">
                </div>
                <div class="info">
                    <p>${article.articleTitle}</p>
                </div>
            </a>
        </div>
    </div>
</#if>
</#list>
</div>
