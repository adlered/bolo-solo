<#--

    Solo - A small and beautiful blogging system written in Java.
    Copyright (c) 2010-present, b3log.org

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
<div id="tabPreference" class="sub-tabs fn__clear">
    <ul>
        <li>
            <div id="tabPreference_config">
                <a class="tab-current" href="#tools/preference/config">${configSettingsLabel}</a>
            </div>
        </li>
        <li>
            <div id="tabPreference_signs">
                <a href="#tools/preference/signs">${signLabel}</a>
            </div>
        </li>
        <li>
            <div id="tabPreference_setting">
                <a href="#tools/preference/setting">${paramSettingsLabel}</a>
            </div>
        </li>
    </ul>
</div>
<div id="tabPreferencePanel" class="sub-tabs-main">
    <div id="tabPreferencePanel_config" class="form">
        <div class="fn__clear">
            <button onclick="admin.preference.update()" class="fn__right">${updateLabel}</button>
        </div>
        <div class="fn__clear"></div>
        <div style="display: none">
            元数据：<span id="sourceTC"></span>
        </div>
        <h3>自定义图床</h3>
        <label for="tcS">图床选择</label>
        <select id="tcS" disabled>
            <option selected></option>
            <option value="hacpai">黑客派图床（默认）</option>
            <option value="qiniu">七牛云</option>
            <option value="aliyun">阿里云</option>
            <option value="upyun">又拍云</option>
            <option value="picuang">基于 Picuang 的自搭建图床</option>
        </select>
        <div class="fn__clear" id="changeCfgBtn">
            <button onclick="unlock()" class="fn__left">允许修改图床配置</button>
        </div>
        <div id="td1" for="tc1" style="display:none"></div>
        <input id="tc1" type="text" style="display:none" />
        <div id="td2" for="tc2" style="display:none"></div>
        <input id="tc2" type="text" style="display:none" />
        <div id="td3" for="tc3" style="display:none"></div>
        <input id="tc3" type="text" style="display:none" />
        <div id="td4" for="tc4" style="display:none"></div>
        <input id="tc4" type="text" style="display:none" />
        <div id="td5" for="tc5" style="display:none"></div>
        <input id="tc5" type="text" style="display:none" />
        <div id="td6" for="tc6" style="display:none"></div>
        <input id="tc6" type="text" style="display:none" />
        <div id="td7" for="tc7" style="display:none"></div>
        <input id="tc7" type="text" style="display:none" />
        <div class="fn__clear" id="tuChuangCfg" style="display: none">
            <button onclick="save()" class="fn__left">保存图床配置</button>
        </div>
        <script>
            function loadRemind() {
                clear();
                sel = $('#tcS').val();
                switch (sel) {
                    case 'hacpai':
                        $('#td1').show();
                        $('#td1').text('使用黑客派默认图床，请在偏好设置中配置黑客派的用户名和 B3log Key。');
                        break;
                    case 'picuang':
                        $('#td1').show();
                        $('#td1').html('Picuang 是 Bolo 博客作者开发的一款在自己服务器上搭建的公开图床，<a target="_blank" href="https://github.com/AdlerED/Picuang">项目地址</a>');
                        $('#td2').show(); $('#tc2').show();
                        $('#td2').html('<b>图床地址</b>');
                        $('#td3').show();
                        $('#td3').text('图床地址需带上 HTTP/HTTPS 协议地址，例：https://pic.stackoverflow.wiki')
                        $('#td4').show();
                        $('#td4').text('图床必须允许匿名用户上传才可以使用。');
                        break;
                    case 'qiniu':
                        $('#td1').show();
                        $('#td1').html('Bolo 支持七牛云图床。AK/SK 可以从密钥管理找到，请确保你的 Bucket 已经配置好域名。<a target="_blank" href="https://developer.qiniu.com/kodo/manual/1272/form-upload">七牛云开发文档</a>');
                        $('#td2').show(); $('#tc2').show();
                        $('#td2').html('<b>AccessKey</b>');
                        $('#td3').show(); $('#tc3').show();
                        $('#td3').html('<b>SecretKey</b>');
                        $('#td4').show(); $('#tc4').show();
                        $('#td4').html('<b>Bucket 名称</b>');
                        $('#td5').show(); $('#tc5').show();
                        $('#td5').html('<b>Bucket 绑定的域名（不需要填写协议，正确示例：qiniu.stackoverflow.wiki）</b>');
                        $('#td6').show(); $('#tc6').show();
                        $('#td6').html('<b>协议（填写英文小写 http 或 https，以你的设定为准）</b>');
                        break;
                    case 'aliyun':
                        $('#td1').show();
                        $('#td1').html('Bolo 支持阿里云图床。AccessKey 信息可以从 AccessKey 管理中找到，必须将 Bucket ACL 的权限设定为 公共读写。<a target="_blank" href="https://help.aliyun.com/document_detail/31817.html">阿里云开发文档</a>');
                        $('#td2').show(); $('#tc2').show();
                        $('#td2').html('<b>AccessKeyID</b>');
                        $('#td3').show(); $('#tc3').show();
                        $('#td3').html('<b>AccessKeySecret</b>');
                        $('#td4').show(); $('#tc4').show();
                        $('#td4').html('<b>EndPoint（地域节点）</b>');
                        $('#td5').show(); $('#tc5').show();
                        $('#td5').html('<b>Bucket 名称</b>');
                        $('#td6').show(); $('#tc6').show();
                        $('#td6').html('<b>Bucket 域名</b>');
                        $('#td7').show(); $('#tc7').show();
                        $('#td7').html('<b>协议（填写英文小写 http 或 https，以你的设定为准）</b>')
                        break;
                    case 'upyun':
                        $('#td1').show();
                        $('#td1').html('Bolo 支持又拍云图床。<a target="_blank" href="https://help.upyun.com/knowledge-base/rest_api/">又拍云开发文档</a>');
                        $('#td2').show(); $('#tc2').show();
                        $('#td2').html('<b>空间名称（服务名称）</b>');
                        $('#td3').show(); $('#tc3').show();
                        $('#td3').html('<b>操作员名称</b>');
                        $('#td4').show(); $('#tc4').show();
                        $('#td4').html('<b>操作员密码</b>');
                        $('#td5').show(); $('#tc5').show();
                        $('#td5').html('<b>绑定域名</b>');
                        $('#td6').show(); $('#tc6').show();
                        $('#td6').html('<b>协议（填写英文小写 http 或 https，以你的设定为准）</b>');
                        break;
                }
            }
            $('#tcS').change(loadRemind);

            function clear() {
                for (i = 1; i <= 7; i++) {
                    $('#tc' + i).hide();
                    $('#td' + i).hide();
                }
            }

            function unlock() {
                $('#changeCfgBtn').hide();
                $('#tcS').removeAttr("disabled");
                $('#tuChuangCfg').show();
                loadRemind();
            }

            function save() {
                sel = $('#tcS').val();
                switch (sel) {
                    case 'hacpai':
                        $('#sourceTC').text('hacpai');
                        break;
                    case 'picuang':
                        $('#sourceTC').text('picuang<<>>' + $('#tc2').val());
                        break;
                    case 'qiniu':
                        $('#sourceTC').text('qiniu<<>>' + $('#tc2').val() + '<<>>' + $('#tc3').val() + '<<>>' + $('#tc4').val() + '<<>>' + $('#tc5').val() + '<<>>' + $('#tc6').val());
                        break;
                    case 'aliyun':
                        $('#sourceTC').text('aliyun<<>>' + $('#tc2').val() + '<<>>' + $('#tc3').val() + '<<>>' + $('#tc4').val() + '<<>>' + $('#tc5').val() + '<<>>' + $('#tc6').val() + '<<>>' + $('#tc7').val());
                        break;
                    case 'upyun':
                        $('#sourceTC').text('upyun<<>>' + $('#tc2').val() + '<<>>' + $('#tc3').val() + '<<>>' + $('#tc4').val() + '<<>>' + $('#tc5').val() + '<<>>' + $('#tc6').val());
                        break;
                }
                alert('配置已保存。如果自定义图床未生效，请清除浏览器缓存并重启 Bolo 服务端。');
                admin.preference.update();
            }
        </script>
        <div><b>请注意！</b>如使用自定义图床中出现 "413 Request Entity Too Large" 等类似报错，请调整 Nginx / Tomcat 的数据包大小限制。<br>配置图床期间出现疑问或问题，请联系作者微信：1101635162</div>
        <br>
        <h3>B3log 生态设定</h3>
        <label for="hacpaiUser">${hacpaiUser1Label}</label>
        <input id="hacpaiUser" type="text"/>
        <label for="b3logKey">${b3logKey1Label}</label>
        <input id="b3logKey" type="text">
        <div>
            <b>设置黑客派用户名和 B3log Key 后，你可以</b><br>
            1. 使用 Bolo 的编辑器图床<br>
            2. 与黑客派社区同步发送的文章和评论<br>
            3. 同步你在黑客派中的GitHub、QQ等链接（<a href="https://hacpai.com/settings" target="_blank">在这里</a>设置你的多种个人联系方式，将自动同步至 Bolo（24小时），然后显示在你的主页）<a href="https://github.com/b3log/solo/issues/12719" target="_blank">功能详情</a><br>
            4. 使用自动备份全部文章至黑客派功能（<a href="https://hacpai.com/settings/b3" target="_blank">在这里查看自动备份后的文件</a>）<br><br>
            <b>如何取得 B3log Key</b><br>
            1. <a href="https://hacpai.com/register?r=AdlerED" target="_blank">在这里</a>注册一个黑客派用户（在<a href="https://hacpai.com/settings/account" target="_blank">个人资料</a>中查看你的用户名）<br>
            2. <a href="https://hacpai.com/settings/b3" target="_blank">在这里</a>获得并设置你的B3log Key<br><br>
            <b>Bolo 坚实的防数据丢失策略</b><br>
            1. 与黑客派仅保持<b>单向</b>联系。即：只允许上传下载（备份至黑客派、上传至黑客派图床、同步联系方式、同步文章与评论等），不允许修改或删除你的任何本地博客数据<br>
            2. 完全解除与 GitHub 联系的 API 接口<br>
        </div>
        <br>
        <h3>邮件服务器设定</h3>
        <label for="mailBox">${mailBoxLabel}</label>
        <select id="mailBox">
            <option value="" selected>未设定</option>
            <option value="QQ">QQ 邮箱</option>
            <option value="QQ_ENT">QQ 企业版邮箱</option>
            <option value="163">163 网易免费邮箱</option>
        </select>
        <label for="mailUsername">${mailUsernameLabel}</label>
        <input id="mailUsername" type="text"/>
        <label for="mailPassword">${mailPasswordLabel}</label>
        <input id="mailPassword" type="text"/>
        <div>
            设定你的邮件服务器，以正常向用户发送评论 / 通知提醒。<br>
            目前支持：QQ 邮箱、QQ 企业版邮箱、163 网易免费邮箱<br>
            请注意：<b>有些邮箱服务器可能设有独立密码，具体设定方法请查询后填写！</b>
        </div>
        <br>
        <h3>回帖提醒</h3>
        <label for="replyRemind">博主邮箱：</label>
        <input id="replyRemind" type="text" />
        <div>
            填写你用于接收提醒的邮箱，当有人在你的博客文章中评论时，菠萝会发送邮件提醒你。<br>
            <b>此项设定必须先设定邮件服务器。</b>
        </div>
        <br>
        <h3>本地博客设定</h3>
        <label for="blogTitle">${blogTitle1Label}</label>
        <input id="blogTitle" type="text"/>
        <label for="blogSubtitle">${blogSubtitle1Label}</label>
        <input id="blogSubtitle" type="text"/>
        <label for="blogHost">${blogHost1Label}</label>
        <input id="blogHost" type="text" value="${serverHost}" readonly="true"/>
        <label for="metaKeywords">${metaKeywords1Label}</label>
        <input id="metaKeywords" type="text"/>
        <label for="metaDescription">${metaDescription1Label}</label>
        <input id="metaDescription" type="text"/>
        <label for="htmlHead">${htmlhead1Label}</label>
        <textarea rows="6" id="htmlHead"></textarea>
        <label for="noticeBoard">${noticeBoard1Label}</label>
        <textarea rows="6" id="noticeBoard"></textarea>
        <label for="footerContent">${footerContent1Label}</label>
        <textarea rows="2" id="footerContent"></textarea><br><br>
        <div class="fn__clear">
            <button onclick="admin.preference.update()" class="fn__right">${updateLabel}</button>
        </div>
    </div>
    <div id="tabPreferencePanel_setting" class="fn__none form">
        <button class="fn__right" onclick="admin.preference.update()">${updateLabel}</button>
        <div class="fn__clear"></div>
        <label for="localeString">${localeString1Label}</label>
        <select id="localeString">
            <option value="zh_CN">简体中文</option>
            <option value="en_US">English(US)</option>
        </select>
        <label for="timeZoneId">${timeZoneId1Label}</label>
        <select id="timeZoneId">
        ${timeZoneIdOptions}
        </select>
        <label for="articleListDisplay">${articleListDisplay1Label}</label>
        <select id="articleListDisplay">
            <option value="titleOnly">${titleOnlyLabel}</option>
            <option value="titleAndAbstract">${titleAndAbstractLabel}</option>
            <option value="titleAndContent">${titleAndContentLabel}</option>
        </select>
        <label for="hljsTheme">
            <a href="https://xyproto.github.io/splash/docs/longer/all.html" target="_blank">${previewLabel}</a>${hljsThemeLabel}
        </label>
        <select id="hljsTheme">
            <option value="abap">abap</option>
            <option value="algol">algol</option>
            <option value="algol_nu">algol_nu</option>
            <option value="arduino">arduino</option>
            <option value="autumn">autumn</option>
            <option value="borland">borland</option>
            <option value="bw">bw</option>
            <option value="colorful">colorful</option>
            <option value="dracula">dracula</option>
            <option value="emacs">emacs</option>
            <option value="friendly">friendly</option>
            <option value="fruity">fruity</option>
            <option value="github">github</option>
            <option value="igor">igor</option>
            <option value="lovelace">lovelace</option>
            <option value="manni">manni</option>
            <option value="monokai">monokai</option>
            <option value="monokailight">monokailight</option>
            <option value="murphy">murphy</option>
            <option value="native">native</option>
            <option value="paraiso-dark">paraiso-dark</option>
            <option value="paraiso-light">paraiso-light</option>
            <option value="pastie">pastie</option>
            <option value="perldoc">perldoc</option>
            <option value="pygments">pygments</option>
            <option value="rainbow_dash">rainbow_dash</option>
            <option value="rrt">rrt</option>
            <option value="solarized-dark">solarized-dark</option>
            <option value="solarized-dark256">solarized-dark256</option>
            <option value="solarized-light">solarized-light</option>
            <option value="swapoff">swapoff</option>
            <option value="tango">tango</option>
            <option value="trac">trac</option>
            <option value="vim">vim</option>
            <option value="vs">vs</option>
            <option value="xcode">xcode</option>
        </select>
        <label for="maxArchive">${maxArchiveLabel}</label>
        <input id="maxArchive" class="normalInput" type="text"/>
        <label for="mostUsedTagDisplayCount">${indexTagDisplayCnt1Label}</label>
        <input id="mostUsedTagDisplayCount" class="normalInput" type="text"/>
        <label for="recentCommentDisplayCount">${indexRecentCommentDisplayCnt1Label}</label>
        <input id="recentCommentDisplayCount" class="normalInput" type="text"/>
        <label for="mostCommentArticleDisplayCount">${indexMostCommentArticleDisplayCnt1Label}</label>
        <input id="mostCommentArticleDisplayCount" class="normalInput" type="text"/>
        <label for="mostViewArticleDisplayCount">${indexMostViewArticleDisplayCnt1Label}</label>
        <input id="mostViewArticleDisplayCount" class="normalInput" type="text"/>
        <label for="articleListDisplayCount">${pageSize1Label}</label>
        <input id="articleListDisplayCount" class="normalInput" type="text"/>
        <label for="articleListPaginationWindowSize">${windowSize1Label}</label>
        <input id="articleListPaginationWindowSize" class="normalInput" type="text"/>
        <label for="randomArticlesDisplayCount">${randomArticlesDisplayCnt1Label}</label>
        <input id="randomArticlesDisplayCount" class="normalInput" type="text"/>
        <label for="relevantArticlesDisplayCount">${relevantArticlesDisplayCnt1Label}</label>
        <input id="relevantArticlesDisplayCount" class="normalInput" type="text"/>
        <label for="externalRelevantArticlesDisplayCount">${externalRelevantArticlesDisplayCnt1Label}</label>
        <input id="externalRelevantArticlesDisplayCount" class="normalInput" type="text"/>
        <label for="feedOutputMode">${feedOutputModel1Label}</label>
        <select id="feedOutputMode">
            <option value="abstract">${abstractLabel}</option>
            <option value="fullContent">${fullContentLabel}</option>
        </select>
        <label for="feedOutputCnt">${feedOutputCnt1Label}</label>
        <input id="feedOutputCnt" class="normalInput" type="text"/>
        <label for="faviconURL">Favicon</label>
        <input id="faviconURL" class="normalInput" type="text"/>
        <label for="customVars">${customVars1Label}</label>
        <input id="customVars" class="normalInput" type="text"/>
        <label>
            <div class="fn__flex-inline">
                ${enableArticleUpdateHint1Label}
                <input id="enableArticleUpdateHint" type="checkbox" class="normalInput"/>
            </div>
        </label>
        <label>
            <div class="fn__flex-inline">
                ${allowVisitDraftViaPermalink1Label}
                <input id="allowVisitDraftViaPermalink" type="checkbox" class="normalInput"/>
            </div>
        </label>
        <label>
            <div class="fn__flex-inline">
                ${allowComment1Label}
                <input id="commentable" type="checkbox" class="normalInput"/>
            </div>
        </label>
        <label>
            <div class="fn__flex-inline">
                ${syncGitHubLabel}
                <input id="syncGitHub" type="checkbox" class="normalInput"/>
            </div>
        </label>
        <button class="fn__right" onclick="admin.preference.update()">${updateLabel}</button>
        <div class="fn__clear"></div>
    </div>
    <div id="tabPreferencePanel_signs" class="fn__none form">
        <button onclick="admin.preference.update()" class="fn__right">${updateLabel}</button>
        <div class="fn__clear"></div>
        <button id="preferenceSignButton1">${signLabel}1</button>
        <textarea rows="8" id="preferenceSign1"></textarea>
        <button id="preferenceSignButton2">${signLabel}2</button>
        <textarea rows="8" id="preferenceSign2"></textarea>
        <button id="preferenceSignButton3">${signLabel}3</button>
        <textarea rows="8" id="preferenceSign3"></textarea><br><br>
        <button onclick="admin.preference.update()" class="fn__right">${updateLabel}</button>
        <div class="fn__clear"></div>
    </div>
</div>
${plugins}
