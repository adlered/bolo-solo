![image-ef3e7eed](https://user-images.githubusercontent.com/23192332/64356995-61416d80-d036-11e9-9361-65bae4b79a06.png)
## solo-nexmoe
![](https://img.shields.io/github/languages/code-size/Jinjianh/solo-nexmoe) &nbsp; ![](https://img.shields.io/github/languages/count/Jinjianh/solo-nexmoe) &nbsp; ![GitHub top language](https://img.shields.io/github/languages/top/Jinjianh/solo-nexmoe)
## 简介
solo-nexmoe是移植Hexo的主题[Nexmoe](https://github.com/nexmoe/hexo-theme-nexmoe)而得到的，皮肤效果基本与原作者一致

## 效果展示

![image-0f32e4c2](https://user-images.githubusercontent.com/23192332/64356996-61416d80-d036-11e9-9e2b-d7ac13602d6a.png)
![image-4ff20186](https://user-images.githubusercontent.com/23192332/64356998-61da0400-d036-11e9-9f17-0e329d2e1fa9.png)
![image-9386c650](https://user-images.githubusercontent.com/23192332/64356999-61da0400-d036-11e9-970e-abd44de2a70c.png)

## 演示

* [墨殇的技术博客](https://www.jinjianh.com/?skin=solo-nexmoe)
* [鼠鼠在碎觉](https://sszsj.cc:444/?skin=solo-nexmoe)
* [记录精彩的人生](https://witheloov.com/?skin=solo-nexmoe)
* [邯城往事](https://www.cjzshilong.cn/?skin=solo-nexmoe)
* [贼拉正经的技术博客](https://www.stackoverflow.wiki/blog/?skin=solo-nexmoe)
* [AlgerFan | Blog](https://www.algerfan.cn/?skin=solo-nexmoe)
* ...

欢迎将你的博客加入这里

## 说明

* 本皮肤依赖于[b3log/solo](https://github.com/b3log/solo)，安装solo可查看[从零开始安装 Solo 博客](https://www.jinjianh.com/articles/2019/08/06/1565021931775.html)

* ~~暂时不支持公告栏---因为我不知道放哪儿~~

* 导航栏自定义图标为字体图标，可以前往[solo-nexmoe 图标详解](https://www.jinjianh.com/articles/2019/08/23/1566548785550.html)查看图标对应名称，直接填入名称即可，目前包内所有图标都被应用了，填写是请删除前缀`solo-`后填写

* 接上条，由于solo会自动拉取你的github项目，《我的开源》对应的图标会被覆盖，所以请升级到最新版本，关闭自动拉取设置，然后讲图标设置为`github`即可

* 需要自定义新图标直接修改`font-icon.scss`与`font-icon.css`，如果你没有node环境，直接修改`css`也是可以的，最后在导航管理处设置即可，你也可以提issue，我会定期收集一些图标更新

* 现用图标可前往github上查看或者[点击下载](https://img.hacpai.com/file/2019/08/download-9acf6646.zip)

* ~~此皮肤有两个版本，`master`与`temporary`，根据目前设计的方式导致友情链接页面很丑，所以就加了一个版本跟换了友情链接展示方式，当前版本请使用`temporary`下的皮肤，至于`master`所需要的东西已经反馈给D哥，进度请查看 [友情链接添加图片属性](https://github.com/b3log/solo/issues/12861)~~

* 侧边栏中的标签可替换为公告栏，操作方法`后台管理`=>`设置`=>`偏好设置`=>`参数设置`=>`自定义模板变量`，更改`key0=0`为`key0=bulletin`即可，PS:(由于侧边栏很窄，所以公告栏外链网易云的，需要考虑一下页面美观度的问题)

* 友情链接提供两种显示方式，默认为原始版本的，操作方法如上，更改`key1=0`为`key1=list`即可(由于友链不能自定义图片，所以现在还很丑，等待D哥完成[友情链接添加图片属性](https://github.com/b3log/solo/issues/12861)后，这个会很好看)

* ~~由于更新了side中部分链接的地址写法，例如原来的标签是`/tags.html`，现在是`http(s)://xxxx.com/tags.html`，为了保证你公告栏的音乐可以全站播放，参考[鼠鼠在睡觉](https://sszsj.cc:444/?utm_source=hacpai.com)，所以在自定义导航的时候，请将url写全，比如：`https://www.jinjianh.com/about`而不是`/about`~~ 经过V姐的努力，此BUG已经修复，solo更新至最新版即可


## 小功能

* 快捷键`T`可直接返回顶部

* 自定义背景：默认使用canvas作为背景，在后台管理 → 偏好设定 → 参数设置 → 自定义模板变量中新加参数`bg=*`,`*`对应0-9的数字，数字为透明度，`bg=1`对应`opacity: .1`。设置好后背景图片为[默认图片](https://img.hacpai.com/file/2019/09/57873300p0-3496bc81.jpg)。如需更改默认图片可新增参数`bgUrl=图片地址`，示例`bgUrl=https://static-solo.b3log.org/skins/Bubble/images/header-bg.jpg`

* 后续添加中...


## 鸣谢

* 感谢皮肤原作者[折影轻梦](https://docs.nexmoe.com/)


## 论坛

欢迎加入我们的小众开源社区，详情请看[这里](https://hacpai.com)


## 最后

欢迎大家提issue，pr，以及点star

## 更新通知

希望使用本皮肤的各位能够关注本帖，皮肤有BUG修复或者功能更新后会在本帖说明，重要的事说三遍
希望使用本皮肤的各位能够关注本帖，皮肤有BUG修复或者功能更新后会在本帖说明，重要的事说三遍
希望使用本皮肤的各位能够关注本帖，皮肤有BUG修复或者功能更新后会在本帖说明，重要的事说三遍

* 2019-09-05：调换`mater`分支与`temporary`内容，目前连分支唯一差别就是友情链接页面

* 2019-09-09：修复头图误删元素问题，完成[左侧可以添加公告栏](https://github.com/InkDP/solo-nexmoe/issues/6)，将`master`与`tempoary`，详细操作查看说明。

* 2019-09-10：添加置顶标识，更换返回顶部图标

* 2019-09-10：修复[文章第一次评论时，无法直接更新只评论列表](https://github.com/InkDP/solo-nexmoe/issues/8)

* 2019-09-10：修复最新版solo出现代码不高亮问题，没有出现代码不高亮可以不更新

* 2019-09-12：侧边栏分类文章数量正确显示

* 2019-09-19：修复solo3.6.5出现的无法从首页登录后台问题

* 2019-09-25：修复表格无法换行问题

* 2019-09-26：添加用户自定义背景功能

