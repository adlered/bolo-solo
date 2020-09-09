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
<div id="tabPreference" class="sub-tabs fn__clear">
    <ul>
        <li>
            <div id="tabPreference_config">
                <a class="tab-current" href="#tools/preference/config">${configSettingsLabel}</a>
            </div>
        </li>
        <li>
            <div id="tabPreference_setting">
                <a href="#tools/preference/setting">${paramSettingsLabel}</a>
            </div>
        </li>
        <li>
            <div id="tabPreference_mailAndRemind">
                <a href="#tools/preference/mailAndRemind">${mailAndRemindLabel}</a>
            </div>
        </li>
        <li>
            <div id="tabPreference_imageBed">
                <a href="#tools/preference/imageBed">${imageBedLabel}</a>
            </div>
        </li>
        <li>
            <div id="tabPreference_signs">
                <a href="#tools/preference/signs">${signLabel}</a>
            </div>
        </li>
        <li>
            <div id="tabPreference_markdown">
                <a href="#tools/preference/markdown">${editorLabel}</a>
            </div>
        </li>
        <li>
            <div id="tabPreference_interactive">
                <a href="#tools/preference/interactive">${interactiveLabel}</a>
            </div>
        </li>
        <li>
            <div id="tabPreference_b3logEcology">
                <a href="#tools/preference/b3logEcology">${b3logEcologyLabel}</a>
            </div>
        </li>
        <li>
            <div id="tabPreference_security">
                <a href="#tools/preference/security">${securityLabel}</a>
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
        <textarea rows="6" id="footerContent"></textarea><br><br>
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
        <label for="faviconURL">Favicon (网站图标)：</label>
        <input id="faviconURL" class="normalInput" type="text"/>
        <label for="customVars">${customVars1Label}</label>
        <input id="customVars" class="normalInput" type="text"/>
        <label for="githubPAT">${githubPATLabel}</label>
        <input id="githubPAT" class="normalInput" type="text"/>
        <label for="kanbanniangSelector">固定看板娘：</label>
        <select id="kanbanniangSelector">
            <option value="" selected>不固定，随机</option>
        </select>
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
    <div id="tabPreferencePanel_markdown" class="fn__none form">
        <button class="fn__right" onclick="admin.preference.update()">${updateLabel}</button>
        <div class="fn__clear"></div>

        <h3>${editorModeLabel}</h3>
        <br>
        <label><input name="editorMode" type="radio" value="wysiwyg">&nbsp;${editorModeWYSIWYGLabel} </label>
        <label><input name="editorMode" type="radio" value="ir">&nbsp;${editorModeIRLabel} </label>
        <label><input name="editorMode" type="radio" value="sv" checked>&nbsp;${editorModeSVLabel} </label>
        <br><br><br>
        <h3>${othersLabel}</h3>
        <br>
        <label class="checkbox">
            <input id="showCodeBlockLn" type="checkbox" class="normalInput"/>
            <span>&nbsp;${showCodeBlockLnLabel}</span>
        </label>

        <button class="fn__right" onclick="admin.preference.update()">${updateLabel}</button>
        <div class="fn__clear"></div>
    </div>
    <div id="tabPreferencePanel_imageBed" class="fn__none form">
        <div class="fn__clear"></div>
        <div class="fn__clear"></div>
        <div style="display: none">
            元数据：<span id="sourceTC"></span>
        </div>
        <label for="tcS">图床选择</label>
        <select id="tcS" disabled>
            <option selected></option>
            <option value="hacpai">链滴图床（默认）</option>
            <option value="local">本地图床</option>
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
        <div id="td8" for="tc8" style="display:none"></div>
        <input id="tc8" type="text" style="display:none" />
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
                        $('#td1').text('使用链滴默认图床，请在偏好设置中配置链滴的用户名和 B3log Key。');
                        break;
                    case 'picuang':
                        $('#td1').show();
                        $('#td1').html('Picuang 是 Bolo 博客作者开发的一款在自己服务器上搭建的公开图床，<a target="_blank" href="https://github.com/adlered/Picuang">项目地址</a>');
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
                        $('#td1').html('Bolo 支持阿里云图床。AccessKey 信息可以从 AccessKey 管理中找到，必须将 Bucket ACL 的权限设定为 公读私写 或 公共读写。<a target="_blank" href="https://help.aliyun.com/document_detail/31817.html">阿里云开发文档</a>');
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
                        $('#td8').show(); $('#tc8').show();
                        $('#td8').html('<b>子目录（为空则保存至根目录，不需要在最前和最后输入斜杠，直接输入目录名即可，二级目录可以使用 "level1/level2" 类似格式，以此类推）</b>')
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
                    case 'local':
                        $('#td1').show();
                        $('#td1').html('本地图床适用于带宽较大的服务器（或设有CDN加速），如果你希望将上传的图片保存到服务器的指定目录，请使用本地图床功能。如需更换本地图床位置，请将原位置的所有图片直接移动到新的目录，更改本配置下的本地图床位置即可。');
                        $('#td2').show(); $('#tc2').show();
                        $('#td2').html('<b>图片存储目录（Windows例：D:/imageBed，Linux例：/home/adler/imageBed，如目录不存在将会尝试自动创建）</b>');
                        $('#td3').show();
                        $('#td3').html('<button onclick="checkImageBedConfigAndAlert()">💿 测试本地图床配置是否可用</button>');
                        break;
                }
                var stc = $('#sourceTC').text().split('<<>>');
                for (var i = 1; i < stc.length; i++) {
                    $('#tc' + (i + 1)).val(stc[i]);
                }
            }
            $('#tcS').change(loadRemind);

            function clear() {
                for (i = 1; i <= 8; i++) {
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
                if (checkImageBedConfig()) {
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
                            $('#sourceTC').text('aliyun<<>>' + $('#tc2').val() + '<<>>' + $('#tc3').val() + '<<>>' + $('#tc4').val() + '<<>>' + $('#tc5').val() + '<<>>' + $('#tc6').val() + '<<>>' + $('#tc7').val() + '<<>>' + $('#tc8').val());
                            break;
                        case 'upyun':
                            $('#sourceTC').text('upyun<<>>' + $('#tc2').val() + '<<>>' + $('#tc3').val() + '<<>>' + $('#tc4').val() + '<<>>' + $('#tc5').val() + '<<>>' + $('#tc6').val());
                            break;
                        case 'local':
                            $('#sourceTC').text('local<<>>' + $('#tc2').val());
                            break;
                    }
                    alert('配置已保存，图床设置将在重启服务端后生效。');
                    admin.preference.update();
                } else {
                    alert('配置保存失败，请检查本地图床设定的目录！');
                }
            }

            function checkImageBedConfig() {
                let flag = false;
                $.ajax({
                    type: 'GET',
                    url: 'pic/local/check?path=' + $('#tc2').val(),
                    async: false,
                    success: function (res) {
                        result = res.msg;
                        flag = result.indexOf(":)") !== -1;
                    }
                })
                return flag;
            }

            function checkImageBedConfigAndAlert() {
                $.ajax({
                    type: 'GET',
                    url: 'pic/local/check?path=' + $('#tc2').val(),
                    async: false,
                    success: function (res) {
                        alert(res.msg);
                    }
                })
            }
        </script>
        <div><b>请注意！如使用自定义图床中出现 "413 Request Entity Too Large" 等类似报错，请调整 Nginx / Tomcat 的数据包大小限制。</b>
            <br>
            配置图床期间出现疑问或问题，请在用户交流群中提问。</div>
        <div class="fn__clear"></div>
    </div>
    <div id="tabPreferencePanel_mailAndRemind" class="fn__none form">
        <button class="fn__right" onclick="admin.preference.update()">${updateLabel}</button>
        <div class="fn__clear"></div>
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
        <h3>${CommentAndReplyLabel}</h3>
        <label for="replyRemind">${BloggerMailboxLabel}</label>
        <input id="replyRemind" type="text" />
        <div>
            ${BloggerMailboxDescribeLabel}
        </div>
        <label for="adminActiveSentToMailbox">管理员的评论动态也发送至博主邮箱：</label>
        <br>
        <select id="adminActiveSentToMailbox">
            <option value="on" selected="">${enableLabel}</option>
            <option value="off">${disableLabel}</option>
        </select>
        <br><br>
        <button class="fn__right" onclick="admin.preference.update()">${updateLabel}</button>
        <div class="fn__clear"></div>
    </div>
    <div id="tabPreferencePanel_b3logEcology" class="fn__none form">
        <button class="fn__right" onclick="admin.preference.update()">${updateLabel}</button>
        <div class="fn__clear"></div>
        <label for="hacpaiUser">${hacpaiUser1Label}</label>
        <input id="hacpaiUser" type="text"/>
        <label for="b3logKey">${b3logKey1Label}</label>
        <input id="b3logKey" type="text">
        <div>
            <div>
                <b>请注意！此项设置需要重启服务端后生效。</b>
                <br><br>
                菠萝博客为您提供了一个公共账号，无需自行注册账号，即可直接使用链滴的图床服务；<br>
                使用公共账号的图床需要将<b>自定义图床</b>修改为链滴，并将 "链滴用户名" 及 "B3log Key" 字段留空。
                <br><br>
            </div>
            <style>
                .menu ul li {
                    list-style-type: none;
                }
            </style>
            <details class="menu">
                <summary>设置以后，我可以做什么？</summary>
                <ul>
                    <li>
                        1. 使用 Bolo 的编辑器的链滴图床（<b>其它图床不受影响</b>）<br>
                        2. 与链滴社区同步发送的文章<s>和评论</s>（链滴已废弃评论推送接口，文章推送 / 同步不受影响）<br>
                        3. 同步你在链滴中的GitHub、QQ等链接（<b>也可以在工具-联系方式选项直接设置，无需配置 B3log Key。</b>如果要通过链滴同步：<a href="https://${hacpaiDomain}/settings" target="_blank">在这里</a>设置你的多种个人联系方式，将自动同步至 Bolo（24小时），然后显示在你的主页）<a href="https://github.com/b3log/solo/issues/12719" target="_blank">功能详情</a><br>
                        4. 使用自动备份全部文章至链滴功能（<a href="https://${hacpaiDomain}/settings/b3" target="_blank">在这里查看自动备份后的文件</a>）<br>
                        5. 链滴图床有防盗链功能，仅允许在博客内显示图片，<b>推荐使用自定义图床</b>。<br><br>
                    </li>
                </ul>
            </details>
            <details class="menu">
                <summary>如何取得 B3log Key？</summary>
                <ul>
                    <li>
                        1. <a href="https://${hacpaiDomain}/register?r=adlered" target="_blank">在这里</a>注册一个链滴用户（在<a href="https://${hacpaiDomain}/settings/account" target="_blank">个人资料</a>中查看你的用户名）<br>
                        2. <a href="https://${hacpaiDomain}/settings/b3" target="_blank">在这里</a>获得并设置你的B3log Key<br>
                    </li>
                </ul>
            </details>
        </div>
        <button class="fn__right" onclick="admin.preference.update()">${updateLabel}</button>
        <div class="fn__clear"></div>
    </div>
    <div id="tabPreferencePanel_interactive" class="fn__none form">
        <div class="fn__clear">
            <button class="fn__right" onclick="admin.preference.update()">${updateLabel}</button>
        </div>
        <div class="fn__clear"></div>
        <label>${nonInteractiveLabel}</label>
        <br>
        ${interactiveDescribeLabel}
        <br><br>
        <select id="interactiveSwitch">
            <option value="on" selected="">${enableLabel}</option>
            <option value="off">${disableLabel}</option>
        </select>
        <br><br>
        <button class="fn__right" onclick="admin.preference.update()">${updateLabel}</button>
        <div class="fn__clear"></div>
    </div>
    <div id="tabPreferencePanel_security" class="fn__none form">
        <div class="fn__clear">
            <button class="fn__right" onclick="admin.preference.update()">${updateLabel}</button>
        </div>
        <div class="fn__clear"></div>
        <label>${securityPowerLabel}</label>
        <br>
        ${securityDescribeLabel}
        <br><br>
        <select id="wafPower">
            <option value="on" selected="">${enableLabel}</option>
            <option value="off">${disableLabel}</option>
        </select>
        <br><br>
        <label>${frequencyLimitLabel}</label>
        <br>
        <input id="wafCurrentLimitTimes" type="text" style="width: 100px"> ${securitySetTimesLabel} <input id="wafCurrentLimitSecond" type="text" style="width: 100px"> ${securitySetSecondLabel}
        <br><br>
        <label for="spam">${CommentSpamLabel}</label>
        <input id="spam" type="text" />
        <div>
            ${CommentSpamDescribeLabel}
        </div>
        <button class="fn__right" onclick="admin.preference.update()">${updateLabel}</button>
        <div class="fn__clear"></div>
    </div>
</div>
${plugins}
