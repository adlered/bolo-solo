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
<style type="text/css">
    #top {
        background-color: #FFF;
        background-image: linear-gradient(#FFFFFF,#E5E5E5);
        background-image: -ms-linear-gradient(#FFFFFF,#E5E5E5);
        background-image: -o-linear-gradient(#FFFFFF,#E5E5E5);
        background-image: -webkit-linear-gradient(#FFFFFF,#E5E5E5);
        filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0, startColorstr='#FFFFFF', endColorstr='#E5E5E5');
        border-bottom: 1px solid #E5E5E5;
        line-height: 26px;
        display: none;
    }

    #top a, #top span span {
        border-right: 1px solid #D9D9D9;
        color: #4C4C4C;
        float: left;
        line-height: 14px;
        margin: 6px 0;
        padding: 0 6px;
        text-decoration: none;
        text-shadow: 0 -1px 0 #FFFFFF;
        font-weight: normal;
    }

    #top a:hover, #top a.hover {
        background-color: #EEF2FA;
        border-left-color: #707070;
        border-radius: 0 13px 13px 0;
        margin: 0px;
        line-height: 26px;
    }

    #showTop {
        background-image: url("${staticServePath}/images/arrow-right.png");
        cursor: pointer;
        height: 26px;
        right: 0;
        position: absolute;
        top: 0;
        width: 26px;
        border-radius: 0 0 0 15px;
        z-index: 10;
    }

    #showTop:hover {
        background-image: url("${staticServePath}/images/arrow-right.gif");
    }

    #top #hideTop {
        background-image: url("${staticServePath}/images/arrow-left.png");
        height: 26px;
        margin: 0;
        padding: 0;
        width: 26px;
        border: 0;
    }

    #top #hideTop:hover {
        background-image: url("${staticServePath}/images/arrow-left.gif");
        border-radius: 0;    
    }
</style>
<div id="showTop"></div>
<div id="top">
    <a href="https://github.com/adlered/bolo-solo" target="_blank" class="hover">
        Bolo
    </a>
    <span class="left">&nbsp;${onlineVisitor1Label}${onlineVisitorCnt}</span>
    <span class="right" id="admin" data-login="${isLoggedIn?string}">
        <#if isLoggedIn>
            <span id="adminName">${userName}</span>
            <#if !isVisitor>
            <a href="${servePath}/admin-index.do#main" title="${adminLabel}">
                ${adminLabel}
            </a>
            </#if>
            <a href="${logoutURL}" title="${logoutLabel}">${logoutLabel}</a>
        <#else>
            <a href="${servePath}/start" title="${startToUseLabel}">${startToUseLabel}</a>
            </#if>
            <#if isMobileRequest>
            <a href="javascript:void(0)" onclick="Util.switchMobile('mobile');" title="${mobileLabel}">${mobileLabel}</a>
        </#if>
        <a href="javascript:void(0)" id="hideTop"></a>
    </span>
    <div class="clear"></div>
</div>