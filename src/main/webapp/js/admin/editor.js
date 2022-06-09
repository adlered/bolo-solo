/*
 * Bolo - A stable and beautiful blogging system based in Solo.
 * Copyright (c) 2020, https://github.com/adlered
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
/**
 * @fileoverview editor
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 */

admin.editors = {}

/*
 * @description Create SoloEditor can use all editor.
 * @constructor
 * @param conf 编辑器初始化参数
 * @param conf.id 编辑器渲染元素 id
 * @param conf.height 编辑器种类
 */
var SoloEditor = function (conf) {
  this.conf = conf
  this.init()
}

$.extend(SoloEditor.prototype, {
  /*
   * @description 初始化编辑器
   */
  init: function () {

    Label.emoji = {}
    let defaultEmojiData = JSON.parse('[{"doge":"https://file.fishpi.cn/vditor/3.8.13/dist/images/emoji/doge.png"},{"huaji":"https://file.fishpi.cn/vditor/3.8.13/dist/images/emoji/huaji.gif"},{"smile":"😄"},{"laughing":"😆"},{"smirk":"😏"},{"heart_eyes":"😍"},{"kissing_heart":"😘"},{"flushed":"😳"},{"grin":"😁"},{"stuck_out_tongue_closed_eyes":"😝"},{"sleeping":"😴"},{"anguished":"😧"},{"open_mouth":"😮"},{"expressionless":"😑"},{"unamused":"😒"},{"sweat_smile":"😅"},{"weary":"😩"},{"sob":"😭"},{"joy":"😂"},{"astonished":"😲"},{"scream":"😱"},{"tired_face":"😫"},{"rage":"😡"},{"triumph":"😤"},{"yum":"😋"},{"mask":"😷"},{"sunglasses":"😎"},{"dizzy_face":"😵"},{"imp":"👿"},{"smiling_imp":"😈"},{"innocent":"😇"},{"alien":"👽"},{"yellow_heart":"💛"},{"blue_heart":"💙"},{"purple_heart":"💜"},{"green_heart":"💚"},{"broken_heart":"💔"},{"anger":"💢"},{"exclamation":"❗"},{"question":"❓"},{"zzz":"💤"},{"notes":"🎶"},{"poop":"💩"},{"+1":"👍"},{"-1":"👎"},{"ok_hand":"👌"},{"punch":"👊"},{"v":"✌"},{"point_up":"☝"},{"point_down":"👇"},{"pray":"🙏"},{"clap":"👏"},{"muscle":"💪"},{"ok_woman":"🙆"},{"no_good":"🙅"},{"raising_hand":"🙋"},{"massage":"💆"},{"haircut":"💇"},{"nail_care":"💅"},{"see_no_evil":"🙈"},{"feet":"🐾"},{"trollface":"https://file.fishpi.cn/vditor/3.8.13/dist/images/emoji/trollface.png"},{"snowman":"⛄"},{"zap":"⚡"},{"cat":"🐱"},{"dog":"🐶"},{"mouse":"🐭"},{"hamster":"🐹"},{"rabbit":"🐰"},{"frog":"🐸"},{"koala":"🐨"},{"pig":"🐷"},{"monkey":"🐒"},{"racehorse":"🐎"},{"camel":"🐫"},{"sheep":"🐑"},{"elephant":"🐘"},{"panda_face":"🐼"},{"snake":"🐍"},{"hatched_chick":"🐥"},{"hatching_chick":"🐣"},{"turtle":"🐢"},{"bug":"🐛"},{"honeybee":"🐝"},{"beetle":"🐞"},{"snail":"🐌"},{"octopus":"🐙"},{"whale":"🐳"},{"dolphin":"🐬"},{"dragon":"🐉"},{"goat":"🐐"},{"paw_prints":"🐾"},{"tulip":"🌷"},{"four_leaf_clover":"🍀"},{"rose":"🌹"},{"mushroom":"🍄"},{"seedling":"🌱"},{"shell":"🐚"},{"crescent_moon":"🌙"},{"partly_sunny":"⛅"},{"octocat":"https://file.fishpi.cn/vditor/3.8.13/dist/images/emoji/octocat.png"},{"jack_o_lantern":"🎃"},{"ghost":"👻"},{"santa":"🎅"},{"tada":"🎉"},{"camera":"📷"},{"loudspeaker":"📢"},{"hourglass":"⌛"},{"lock":"🔒"},{"bulb":"💡"},{"hammer":"🔨"},{"moneybag":"💰"},{"smoking":"🚬"},{"bomb":"💣"},{"gun":"🔫"},{"hocho":"🔪"},{"pill":"💊"},{"syringe":"💉"},{"scissors":"✂"},{"swimmer":"🏊"},{"black_joker":"🃏"},{"coffee":"☕"},{"tea":"🍵"},{"sake":"🍶"},{"beer":"🍺"},{"wine_glass":"🍷"},{"pizza":"🍕"},{"hamburger":"🍔"},{"poultry_leg":"🍗"},{"meat_on_bone":"🍖"},{"dango":"🍡"},{"doughnut":"🍩"},{"icecream":"🍦"},{"shaved_ice":"🍧"},{"cake":"🍰"},{"cookie":"🍪"},{"lollipop":"🍭"},{"apple":"🍎"},{"green_apple":"🍏"},{"tangerine":"🍊"},{"lemon":"🍋"},{"cherries":"🍒"},{"grapes":"🍇"},{"watermelon":"🍉"},{"strawberry":"🍓"},{"peach":"🍑"},{"banana":"🍌"},{"pear":"🍐"},{"pineapple":"🍍"},{"sweet_potato":"🍠"},{"eggplant":"🍆"},{"tomato":"🍅"}]')
    if (Array.isArray(defaultEmojiData)) {
      defaultEmojiData.forEach(item => {
        const key = Object.keys(item)[0]
        Label.emoji[key] = item[key]
      })
    }

    const options = {
      outline: this.conf.outline || false,
      mode: Label.editorMode,
      typewriterMode: this.conf.typewriterMode,
      cache: {
        enable: true,
      },
      tab: '\t',
      preview: {
        delay: 500,
        mode: this.conf.previewMode,
        url: Label.servePath + '/console/markdown/2html',
        hljs: {
          enable: !Label.luteAvailable,
          style: Label.hljsStyle,
        },
        parse: function (element) {
          if (element.style.display === 'none') {
            return
          }
          Util.parseMarkdown()
        },
      },
      upload: {
        max: 100 * 1024 * 1024 * 1024,
        url: Label.uploadURL,
        token: Label.uploadToken,
        filename: function (name) {
          return name.replace(/[^(a-zA-Z0-9\u4e00-\u9fa5\.)]/g, '').
          replace(/[\?\\/:|<>\*\[\]\(\)\$%\{\}@~]/g, '').
          replace('/\\s/g', '')
        },
      },
      height: this.conf.height,
      counter: {
        enable: true,
        max: 102400,
      },
      resize: {
        enable: this.conf.resize,
      },
      lang: Label.localeString,
      hint: {
        emojiTail: `<a href="https://` + Label.hacpaiDomain + `/emoji/index.html" target="_blank">全部表情</a>`,
        emoji: Label.emoji,
      },
      toolbarConfig: {
        pin: true,
      },
      toolbar:[
        "emoji",
        "headings",
        "bold",
        "link",
        "|",
        "list",
        "ordered-list",
        "check",
        "outdent",
        "indent",
        "|",
        "quote",
        "code",
        "insert-before",
        "insert-after",
        "|",
        "upload",
        "record",
        "table",
        "|",
        "undo",
        "redo",
        "|",
        "fullscreen",
        "edit-mode",
        {
          name: "more",
          toolbar: [
            "italic",
            "strike",
            "line",
            "inline-code",
            "both",
            "code-theme",
            "content-theme",
            "export",
            "outline",
            "preview",
            "devtools",
            "info",
            "help",
          ],
        }],
      after: () => {
        if (typeof this.conf.fun === 'function') {
          this.conf.fun()
        }
      },
    }

    if ($(window).width() < 768) {
      options.toolbar = [
        'emoji',
        'link',
        'upload',
        'edit-mode',
        {
          name: 'more',
          toolbar: [
            'insert-after',
            'fullscreen',
            'preview',
            'info',
            'help',
          ],
        },
      ]
      options.resize.enable = false
      options.toolbarConfig.pin = true
    }

    if (typeof Vditor === 'undefined') {
      Util.loadVditor(() => {
        this.editor = new Vditor(this.conf.id, options)
      })
    } else {
      this.editor = new Vditor(this.conf.id, options)
    }
  },
  /*
   * @description 获取编辑器值
   * @returns {string} 编辑器值
   */
  getContent: function () {
    return this.editor.getValue()
  },
  /*
   * @description 设置编辑器值
   * @param {string} content 编辑器回填内容
   */
  setContent: function (content) {
    this.editor.setValue(content)
  },
  /*
   * @description 移除编辑器值
   */
  remove: function () {
    document.getElementById(this.editor.vditor.id).outerHTML = ''
  },
})

admin.editors.articleEditor = {}
admin.editors.abstractEditor = {}
