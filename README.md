<p align = "center">
<img alt="Bolo" src="bolo-logo.png">
<br><br>
</p>

:construction: Bolo 菠萝 博客系统仍未完成，点击右上方的 Star 和 Watch 来追踪本项目的进展，第一个 Release 版本很快就会发布 :)

# 声明

`Bolo` 是 `Solo` 的修改版，我们始终支持 B3log 生态。开发的初衷是做一个更符合我的偏好的博客。

Bolo 的后台编辑器仍然使用HacPai的图床，但步骤不同。由于我们加入了本地登录功能，去掉了游客登录的流程，所以如要使用博客编辑器的图床，与黑客派社区同步文章的话，需要在设置中，设置你的HacPai用户名和B3 Key，但不需要使用GitHub再次登录。（发布后教程文档会同步更新）。

### 特性

* 持续支持WAR包部署，支持Tomcat/Jetty，使用Servlet作为底层（Solo V3.6.7 及以上已更新至 Latke 框架，不再支持 Tomcat/Jetty）
* 与官方保持更新，兼容原版 Solo 数据库（需要与 Bolo 同版本号或更低的版本，Solo 版本高于 Bolo 需手动修改数据库中版本号，稍后会出教程）
* 倾听：与官方保持更新的同时，更新人性化、简洁、实用的小功能，欢迎提出建议
* 持续保留H2本地数据库
* 无需GitHub，直接本地登录
* 支持本地登录的同时，也支持通过[黑客派](https://hacpai.com)取得默认编辑器的图床权限
* 支持与[黑客派](https://hacpai.com)社区进行文章/评论同步（支持手动开启/关闭） 

### 关于 Solo V4

[Solo V4](https://hacpai.com/article/1571544590916) 版本将会有很大的变动，其将主要支持静态化页面。

届时 Bolo 不会同 Solo 一起更新到 V4 版本，Bolo 会**始终保持旧版本 Bolo 用户的平滑更新升级**。

Bolo 在 Solo V4 版本之后仍会跟随 Solo 的版本号同步更新，同时引入 Solo V4 的新功能与特性，但为了维持大家的使用习惯，Bolo 的更新内容将不会激进。

至于 Bolo 如何进行版本更新，我们稍后会单独写出一份说明供大家参阅。

# 特别鸣谢

|:construction_worker:|:construction_worker:|:construction_worker:|
|:-------------------:|:-------------------:|:-------------------:|
|<img height='48' width='48' src='https://avatars3.githubusercontent.com/u/873584?v=4'>|<img height='48' width='48' src='https://avatars0.githubusercontent.com/u/14257327?v=4'>|<img height='48' width='48' src='https://avatars1.githubusercontent.com/u/23192332?v=4'>|
|[@88250](https://github.com/88250)|[@csfwff](https://github.com/csfwff)|[@InkDP](https://github.com/InkDP)|
|[hacpai.com](https://hacpai.com)|[sszsj.top](https://sszsj.top)|[inkdp.cn](https://inkdp.cn)|

# 迁移

如果你之前使用了官方版本的Solo：

1. 请先**备份**原来的数据库
2. 清空数据库
3. 初始化Bolo
4. 将b3_solo_user表中的一行数据备份
5. 再次清空数据库
6. 将原来的数据库恢复回去
7. 将备份b3_solo_user表中的一行数据 **插入**（保留原有数据） 回去即可

> 如果不会操作，可以联系我（请看我的名片）帮助操作。

# 当前进度

- [x] 本地登录
- [x] 提供持续的WAR包支持，支持Tomcat部署
- [ ] 访客评论
- [ ] 分类功能优化（解除分类与标签的关联）
- [ ] 敬请期待...

#### 目前本地登录功能已经做好了，我录了一段 Demo 视频以代表我真的在写，而不是骗Star。在项目的根目录就有`demoX.mp4`文件，你可以下载后查看。