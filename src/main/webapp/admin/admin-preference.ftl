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
            <div id="tabPreference_security">
                <a href="#tools/preference/security">${securityLabel}</a>
            </div>
        </li>
        <li>
            <div id="tabPreference_myGitHub">
                <a href="#tools/preference/myGitHub">${myGitHubLabel}</a>
            </div>
        </li>
        <#if b3logEnabled>
            <li>
                <div id="tabPreference_b3logEcology">
                    <a href="#tools/preference/b3logEcology">${b3logEcologyLabel}</a>
                </div>
            </li>
        </#if>
        <#if fishpiEnabled>
            <li>
                <div id="tabPreference_fishpiEcology">
                    <a href="#tools/preference/fishpiEcology">${fishpiEcologyLabel}</a>
                </div>
            </li>
        </#if>
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
        <div class="fn__clear">
            <button class="fn__right" onclick="admin.preference.update()">${updateLabel}</button>
        </div>
        <div class="fn__clear"></div>
        <label for="localeString">${localeString1Label}</label>
        <select id="localeString">
            <option value="zh_CN">简体中文</option>
            <option value="en_US">English(US)</option>
        </select>
        <label for="articleListDisplayCount">${pageSize1Label}</label>
        <input id="articleListDisplayCount" class="normalInput" type="text"/>
        <label for="faviconURL">${faviconLabel}</label>
        <input id="faviconURL" class="normalInput" type="text"/>
        <label for="githubPAT">${githubPATLabel}</label>
        <input id="githubPAT" class="normalInput" type="text"/>
        <label for="kanbanniangSelector">${selectedKanBanNiangLabel}</label>
        <select id="kanbanniangSelector">
            <option value="" selected>${randomLabel}</option>
        </select>
        <label>
            <div class="fn__flex-inline">
                ${welfareLabel}
                <input id="welfareLuteService" type="checkbox" class="normalInput"/>
            </div>
        </label>
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
        <label>
            <div class="fn__flex-inline">
                <a href="https://doc.stackoverflow.wiki/web/#/7?page_id=180" target="_blank">用户体验改进计划</a>:
                <input id="helpImprovePlan" type="checkbox" class="normalInput"/>
            </div>
        </label>
        <br><br>
        <details>
            <summary style="cursor: default">${advancedSettingsLabel}</summary>
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
            <label for="articleListPaginationWindowSize">${windowSize1Label}</label>
            <input id="articleListPaginationWindowSize" class="normalInput" type="text"/>
            <label for="randomArticlesDisplayCount">${randomArticlesDisplayCnt1Label}</label>
            <input id="randomArticlesDisplayCount" class="normalInput" type="text"/>
            <label for="relevantArticlesDisplayCount">${relevantArticlesDisplayCnt1Label}</label>
            <input id="relevantArticlesDisplayCount" class="normalInput" type="text"/>
            <label for="feedOutputMode">${feedOutputModel1Label}</label>
            <select id="feedOutputMode">
                <option value="abstract">${abstractLabel}</option>
                <option value="fullContent">${fullContentLabel}</option>
            </select>
            <label for="feedOutputCnt">${feedOutputCnt1Label}</label>
            <input id="feedOutputCnt" class="normalInput" type="text"/>
            <label for="imageUploadCompress">${imageUploadCompressLabel}</label>
            <input id="imageUploadCompress" class="normalInput" type="text"/>
            <label for="thumbCompress">${thumbCompressLabel}</label>
            <input id="thumbCompress" class="normalInput" type="text"/>
            <label for="customVars">${customVars1Label}</label>
            <input id="customVars" class="normalInput" type="text"/>
        </details>
        <button class="fn__right" onclick="admin.preference.update()">${updateLabel}</button>
        <div class="fn__clear"></div>
    </div>
    <div id="tabPreferencePanel_signs" class="fn__none form">
        <div class="fn__clear">
            <button class="fn__right" onclick="admin.preference.update()">${updateLabel}</button>
        </div>
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
        <div class="fn__clear">
            <button class="fn__right" onclick="admin.preference.update()">${updateLabel}</button>
        </div>
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
        <div style="display: none">
            ${srcDataLabel}<span id="sourceTC"></span>
        </div>
        <label for="tcS">${selectPicBedLabel}</label>
        <select id="tcS" disabled>
            <option selected></option>
            <option value="local">${localPicBedLabel}</option>
            <option value="qiniu">${qiNiuLabel}</option>
            <option value="aliyun">${aliLabel}</option>
            <option value="upyun">${upyunLabel}</option>
            <option value="tencent">${tencentLabel}</option>
            <option value="picuang">${picuangLabel}</option>
            <option value="hacpai">${liandiPicBedLabel}</option>
        </select>
        <div class="fn__clear" id="changeCfgBtn">
            <button onclick="unlock()" class="fn__left">${allowConfigPicBedLabel}</button>
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
            <button onclick="save()" class="fn__left">${saveConfigPicBedLabel}</button>
        </div>
        <script>
            function loadRemind() {
                clear();
                sel = $('#tcS').val();
                switch (sel) {
                    case 'hacpai':
                        $('#td1').show();
                        $('#td1').text('${picBed1Label}');
                        break;
                    case 'picuang':
                        $('#td1').show();
                        $('#td1').html('${picBed2Label}');
                        $('#td2').show(); $('#tc2').show();
                        $('#td2').html('${picBed3Label}');
                        $('#td3').show(); $('#tc3').show();
                        $('#td3').html('${picBed4Label}');
                        $('#td4').show();
                        $('#td4').html('${picBed5Label}');
                        break;
                    case 'qiniu':
                        $('#td1').show();
                        $('#td1').html('${picBed6Label}');
                        $('#td2').show(); $('#tc2').show();
                        $('#td2').html('<b>AccessKey</b>');
                        $('#td3').show(); $('#tc3').show();
                        $('#td3').html('<b>SecretKey</b>');
                        $('#td4').show(); $('#tc4').show();
                        $('#td4').html('${picBed7Label}');
                        $('#td5').show(); $('#tc5').show();
                        $('#td5').html('${picBed8Label}');
                        $('#td6').show(); $('#tc6').show();
                        $('#td6').html('${picBed9Label}');
                        break;
                    case 'aliyun':
                        $('#td1').show();
                        $('#td1').html('${picBed10Label}');
                        $('#td2').show(); $('#tc2').show();
                        $('#td2').html('<b>AccessKeyID</b>');
                        $('#td3').show(); $('#tc3').show();
                        $('#td3').html('<b>AccessKeySecret</b>');
                        $('#td4').show(); $('#tc4').show();
                        $('#td4').html('${picBed11Label}');
                        $('#td5').show(); $('#tc5').show();
                        $('#td5').html('${picBed12Label}');
                        $('#td6').show(); $('#tc6').show();
                        $('#td6').html('${picBed13Label}');
                        $('#td7').show(); $('#tc7').show();
                        $('#td7').html('${picBed14Label}')
                        $('#td8').show(); $('#tc8').show();
                        $('#td8').html('${picBed26Label}')
                        break;
                    case 'upyun':
                        $('#td1').show();
                        $('#td1').html('${picBed15Label}');
                        $('#td2').show(); $('#tc2').show();
                        $('#td2').html('${picBed16Label}');
                        $('#td3').show(); $('#tc3').show();
                        $('#td3').html('${picBed17Label}');
                        $('#td4').show(); $('#tc4').show();
                        $('#td4').html('${picBed18Label}');
                        $('#td5').show(); $('#tc5').show();
                        $('#td5').html('${picBed19Label}');
                        $('#td6').show(); $('#tc6').show();
                        $('#td6').html('${picBed20Label}');
                        break;
                    case 'local':
                        $('#td1').show();
                        $('#td1').html('${picBed21Label}');
                        $('#td2').show(); $('#tc2').show();
                        $('#td2').html('${picBed22Label}');
                        $('#td3').show();
                        $('#td3').html('${picBed23Label}');
                        break;
                    case 'tencent':
                        $('#td1').show();
                        $('#td1').html('${picBed27Label}');
                        $('#td2').show(); $('#tc2').show();
                        $('#td2').html('${picBed28Label}');
                        $('#td3').show(); $('#tc3').show();
                        $('#td3').html('${picBed29Label}');
                        $('#td4').show(); $('#tc4').show();
                        $('#td4').html('${picBed30Label}');
                        $('#td5').show(); $('#tc5').show();
                        $('#td5').html('${picBed31Label}');
                        $('#td6').show(); $('#tc6').show();
                        $('#td6').html('${picBed32Label}');
                        $('#td7').show(); $('#tc7').show();
                        $('#td7').html('${picBed33Label}')
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
                sel = $('#tcS').val();
                switch (sel) {
                    case 'hacpai':
                        $('#sourceTC').text('hacpai');
                        break;
                    case 'picuang':
                        $('#sourceTC').text('picuang<<>>' + $('#tc2').val() + '<<>>' + $('#tc3').val());
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
                    case 'tencent':
                        $('#sourceTC').text('tencent<<>>' + $('#tc2').val() + '<<>>' + $('#tc3').val() + '<<>>' + $('#tc4').val() + '<<>>' + $('#tc5').val() + '<<>>' + $('#tc6').val() + '<<>>' + $('#tc7').val());
                        break;
                }
                admin.preference.update();
                alert('${picBed24Label}');
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
        <div>${picBed25Label}</div>
        <div class="fn__clear"></div>
    </div>
    <div id="tabPreferencePanel_mailAndRemind" class="fn__none form">
        <div class="fn__clear"></div>
        <label for="adminActiveSentToMailbox">${adminActiveSentToMailboxLabel}</label>
        <br>
        <select id="adminActiveSentToMailbox">
            <option value="on" selected="">${enableLabel}</option>
            <option value="off">${disableLabel}</option>
        </select>
        <br><br><br>
        <h3>${mailRemindLabel}</h3>
        <br>
        <label for="mailBox">${mailBoxLabel}</label>
        <select id="mailBox">
            <option value="" selected>${unsetLabel}</option>
            <option value="QQ">${qqMailboxLabel}</option>
            <option value="QQ_ENT">${qqEntMailboxLabel}</option>
            <option value="163">${neteaseMailboxLabel}</option>
        </select>
        <label for="mailUsername">${mailUsernameLabel}</label>
        <input id="mailUsername" type="text"/>
        <label for="mailPassword">${mailPasswordLabel}</label>
        <input id="mailPassword" type="text"/>
        <div>
            ${mailboxDescriptionLabel}
        </div>
        <label for="replyRemind">${BloggerMailboxLabel}</label>
        <input id="replyRemind" type="text" />
        <div>
            ${BloggerMailboxDescribeLabel}
        </div>
        <br><br>
        <h3>${serverJiangRemindLabel}</h3>
        <br>
        <label for="sendKey">${sendKeyLabel}</label>
        <input id="sendKey" type="text"/>
        <br><br>
        <a style="font-size: 14px; font-weight: bold; color: #86bf47;" href="https://doc.stackoverflow.wiki/web/#/7?page_id=177" target="_blank">
            ${usageLabel}?
        </a>
        <br><br>
        <button class="fn__right" onclick="admin.preference.update()">${updateLabel}</button>
        <div class="fn__clear"></div>
    </div>
    <div id="tabPreferencePanel_b3logEcology" class="fn__none form">
        <div class="fn__clear"></div>
        <h4 style="color: #00a7e0">${liandi1Label}</h4>
        <br>
        <label for="hacpaiUser">${hacpaiUser1Label}</label>
        <input id="hacpaiUser" type="text"/>
        <label for="b3logKey">${b3logKey1Label}</label>
        <input id="b3logKey" type="text">
        <div>
            <div>
                ${liandi2Label}
            </div>
            <style>
                .menu ul li {
                    list-style-type: none;
                }
            </style>
            <details class="menu">
                ${liandi3Label}
            </details>
            <details class="menu">
                ${liandi4Label}
            </details>
        </div>
        <button class="fn__right" onclick="admin.preference.update()">${updateLabel}</button>
        <div class="fn__clear"></div>
    </div>
    <div id="tabPreferencePanel_fishpiEcology" class="fn__none form">
        <div class="fn__clear"></div>
        <h4 style="color: #00a7e0">${fishpi1Label}</h4>
        <br>
        <label for="fishpiKey">API KEY</label>
        <input id="fishpiKey" type="text">
        <div>
            <div>
                ${fishpi2Label}
            </div>
            <style>
                .menu ul li {
                    list-style-type: none;
                }
            </style>
            <details class="menu">
                ${fishpi3Label}
            </details>
        </div>
        <button class="fn__right" onclick="admin.preference.update()">${updateLabel}</button>
        <div class="fn__clear"></div>
    </div>
    <div id="tabPreferencePanel_interactive" class="fn__none form">
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
    <div id="tabPreferencePanel_myGitHub" class="fn__none form">
        <div class="fn__clear"></div>
        ${myGitHub1Label}
        <br>
        <label class="checkbox">
            <input id="enableAutoFlushGitHub" type="checkbox" class="normalInput"/>
            <span>&nbsp;${autoFlushGitHubLabel}</span>
        </label>
        <br>
        <label for="myGitHubID">${myGitHubIDLabel}</label>
        <input id="myGitHubID" type="text"/>
        <br><br>
        <p>${myGitHub2Label}</p>
        <br><br>
        <button class="fn__right" onclick="admin.preference.update()">${updateLabel}</button>
        <div class="fn__clear"></div>
    </div>
</div>
<script>
    $(function () {
        if ("${helpImprovePlan}" === "") {
            swal({
                icon: "https://ftp.stackoverflow.wiki/bolo/feedback.png",
                title: '用户体验改进计划',
                content: {
                    element: "p",
                    attributes: {
                        style: 'font-size: 14px; text-align: left',
                        innerHTML: '为了改进您的博客使用体验，同时也让我们了解用户的使用情况，我们邀请您参与「用户体验改进计划」。<br>' +
                            '我们<b>仅仅只会</b>收集以下内容：<br><br>' +
                            '<i>1. 当您的 Bolo 出现错误时，会将最近的 5 条日志上传；</i><br>' +
                            '<i>2. 为您的 Bolo 接入「博客访问统计」系统，在上传前在博客端去敏，仅保留大略信息，帮助我们改进 Bolo 的性能。</i><br><br>' +
                            '<b>请您放心</b>，Bolo 的源代码完全开源，如果您不放心用户体验计划的任何一个环节，都可以直接审阅源代码。<br>' +
                            '如果您对用户体验改进计划仍有疑问，可随时在 <i>偏好设置-参数设置</i> 中参加或退出体验计划。<br>' +
                            '<a href="https://doc.stackoverflow.wiki/web/#/7?page_id=180" target="_blank">什么是「用户体验改进计划」</a>'
                    }
                },
                buttons: {
                    cancel: "不参与，不再提醒",
                    confirm: "参与计划"
                },
            }).then((value) => {
                if (value === true) {
                    $('#helpImprovePlan').prop('checked', 'checked');
                } else {
                    $('#helpImprovePlan').removeAttr('checked');
                }
                admin.preference.update();
            });
        }
    })
</script>
${plugins}
