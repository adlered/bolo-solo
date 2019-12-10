<p align = "center">
<img alt="Bolo" src="bolo-logo-256.png">
<br><br>
基于 Solo 的离线多功能版 Bolo 博客，专为程序员设计
<br><br>
<img src="http://img.shields.io/badge/license-AGPLv3-orange.svg?style=flat-square">
<img src="https://img.shields.io/github/last-commit/AdlerED/bolo-solo.svg?style=flat-square">
<img src="https://img.shields.io/github/issues-pr-closed/AdlerED/bolo-solo.svg?style=flat-square">
<img src="https://img.shields.io/github/downloads/AdlerED/bolo-solo/total?style=flat-square">
<img src="https://img.shields.io/github/v/release/AdlerED/bolo-solo?style=flat-square">
<img src="https://img.shields.io/github/commit-activity/y/AdlerED/bolo-solo?style=flat-square">
<br>
<img src="https://img.shields.io/github/languages/code-size/AdlerED/bolo-solo.svg?style=flat-square">
<img src="https://img.shields.io/github/repo-size/AdlerED/bolo-solo?style=flat-square">
<img src="https://img.shields.io/github/languages/count/AdlerED/bolo-solo?style=flat-square">
<img src="https://img.shields.io/github/languages/top/AdlerED/bolo-solo?style=flat-square">
<img src="https://img.shields.io/github/issues/AdlerED/bolo-solo?style=flat-square">
<img src="https://img.shields.io/github/issues-closed-raw/AdlerED/bolo-solo?style=flat-square">
<br>
<img src="https://img.shields.io/github/followers/AdlerED?style=social">
<img src="https://img.shields.io/github/forks/AdlerED/bolo-solo?style=social">
<img src="https://img.shields.io/github/stars/AdlerED/bolo-solo?style=social">
<img src="https://img.shields.io/github/watchers/AdlerED/bolo-solo?style=social">
</p>

****

# 公告

### :tada: 菠萝版本信息
菠萝当前公测版本：v1.0
对应 Solo 原版版本号：v3.6.7（即支持 Solo v3.6.7即以下的用户轻松迁移/迁回）

### 安装教程 && 下载地址：

[点我！查看安装教程并下载最新版菠萝博客](https://github.com/AdlerED/bolo-solo/releases)

### 当前版本注意事项：

* 公测版仅测试 Tomcat 版本使用正常，其它方式（Docker、Jar等等），请自行测试，也欢迎提出建议~
* 稳定版将在功能测试稳定后发布！建议追求稳定的用户等待稳定版发布后安装使用！

# 声明

`Bolo` 是 `Solo` 的修改版，我们始终支持 B3log 生态，支持与黑客派同步文章、评论、联系方式，使用社区图床。  

### 为什么选择 bolo-solo

* 核：基于强大的 [Solo](https://github.com/88250/solo) 进行修改，解析快、占用资源少
* 根：本地化环境，数据存储于本地服务器。使用本地账号登录 / 评论
* 稳：更适合求稳、专心写文的博主：如非必要，不必升级 bolo-solo
* 易：轻松在 Solo / Bolo 之间切换
* 能：强化分类功能，每个文章只拥有一个分类，分类功能独立
* 跨：支持 Tomcat / Jetty / Docker 部署 Bolo（原版Solo v3.6.7 及以上已更新至 Latke 框架，不再支持 Tomcat / Jetty）
* 选：bolo-solo 支持 B3log 体系，可选支持[黑客派](https://hacpai.com)的 B3log Key 实现图床及社区文章评论、联系方式同步功能
* 随：跟随 [原版 Solo](https://github.com/88250/solo) 同步更新版本号及功能，同步支持最新版本 Solo / Bolo 之间切换
* 安：完全去除对 GitHub 的支持， bolo-solo 是完全独立的博客，B3log Key 也仅是调用 API：除你自己，没人能操作你的博客，确保数据的安全
* 兼：版本更新不会去掉已有大功能（例如：本地H2数据库、Servlet框架等），保证对旧版本用户的友好，确保旧版本用户可流畅更新至最新版本
* 听：与官方保持更新的同时，更新人性化、简洁、实用的小功能，感谢你提出各种BUG、建议、意见
* 教：bolo-solo 将全面更新 bolo-solo 迁移、新建、管理的教学视频（对小白十分友好）及文档，解决你遇到的绝大部分问题（2020年之前完成）

### 关于 Solo V4

[Solo V4](https://hacpai.com/article/1571544590916) 版本将会有很大的变动，其将主要支持静态化页面。  
届时 Bolo 不会完全更新 Solo V4 版本的内容，Bolo 会**始终保持旧版本 Bolo 用户的平滑更新升级**。  
Bolo 在 Solo V4 版本之后仍会跟随 Solo 的版本号同步更新，同时引入 Solo V4 的新功能与特性，但为了维持大家的使用习惯，Bolo 的更新内容将不会激进。  

# 特别鸣谢

|:construction_worker:|:construction_worker:|:construction_worker:|
|:-------------------:|:-------------------:|:-------------------:|
|<img height='48' width='48' src='https://avatars3.githubusercontent.com/u/873584?v=4'>|<img height='48' width='48' src='https://avatars0.githubusercontent.com/u/14257327?v=4'>|<img height='48' width='48' src='https://avatars1.githubusercontent.com/u/23192332?v=4'>|
|[@88250](https://github.com/88250)|[@csfwff](https://github.com/csfwff)|[@InkDP](https://github.com/InkDP)|
|[Solo 原作者](https://hacpai.com)|[Bolo 测试、Logo 绘制、前端技术支持](https://sszsj.top)|[默认皮肤 solo-nexmoe 原作者](https://inkdp.cn)|

# 轻松迁移：迁移至 Bolo / 迁回至 Solo

### 从 Solo 迁移至 Bolo

为防出现意外，请先**备份**原来的数据库
1. 修改 Bolo 的数据库配置文件 `local.properties`，使 Bolo 连接至数据库；
2. **清空（只清空数据，不删除表本身）** `b3_solo_user` 、 `b3_solo_category` 和 `b3_solo_category_tag` 表；
3. 执行SQL语句，在用户表中新建一个管理员用户：
```sql
INSERT INTO `b3_solo_user` (
	`oId`,
	`userName`,
	`userURL`,
	`userRole`,
	`userAvatar`,
	`userB3Key`,
	`userGitHubId` 
)
VALUES
	(
		'default',
		'{管理员用户名}',
		'{管理员的个人主页网址}',
		'adminRole',
		'{管理员的头像网址}',
		'{管理员密码}',
	'none' 
	);
```
如果你修改了`b3_solo_user`的表名，请记得在 SQL 语句中也加以修改（第一行）。  
将 SQL 语句中的`{管理员用户名}`、`{管理员的个人主页网址}`、`{管理员的头像网址}`、`{管理员密码}`修改。  
**启动 Bolo ，迁移完成~**

### 从 Bolo 迁回至 Solo

1. **清空（只清空数据，不删除表本身）** `b3_solo_category` 和 `b3_solo_category_tag` 表；
2. 确定 `b3_solo_user` 表中的 `userName` 列用户名，和你的 GitHub 账户用户名相同，如果不同，请直接修改；
3. 启动 Solo；
4. 登录，在`管理后台 - 工具 - 用户管理`中，更新管理员账号的B3log Key为黑客派中的B3log Key。
5. 大功告成~

> 如果不会操作，欢迎联系我~（请看我名片中的联系方式）

# 皮肤

由于特性原因，Bolo **不支持原版皮肤**。但我们会在官方皮肤的基础上修改皮肤，并**附带到** Bolo 当中。

预计很快就能移植绝大部分皮肤。

* Bolo 使用 [solo-nexmoe](https://github.com/Programming-With-Love/solo-nexmoe) 作为默认皮肤。
* Bolo 皮肤已自带，不需要额外下载。

目前移植的皮肤：

1. [solo-nexmoe](https://github.com/Programming-With-Love/solo-nexmoe) → bolo-solo-nexmoe

# 当前进度

- [x] 本地登录
- [x] 提供持续的WAR包支持，支持Tomcat部署
- [x] 和黑客派社区同步文章、评论、个人联系方式、使用社区图床
- [x] 本地评论功能（无需登录）
- [x] 分类功能优化（解除分类与标签的关联）
- [x] 评论频率限制（60秒/次）
- [x] 自定义主页存档显示数量
- [ ] 自定义评论频率限制
- [ ] 评论审核功能
- [ ] 关闭搜索功能（1.国内备案管局不允许个人建站带有搜索功能 2.节约服务器资源）

# 未公开版目前实现功能截图（请点击对应链接预览）

### 本地用户登录

[https://pic.stackoverflow.wiki/uploadImages/123/113/178/101/2019/12/04/00/19/6c0e2f0e-1baf-4fac-8a7a-1052be272ead.png](https://pic.stackoverflow.wiki/uploadImages/123/113/178/101/2019/12/04/00/19/6c0e2f0e-1baf-4fac-8a7a-1052be272ead.png)

### 默认皮肤 bolo-solo-nexmoe 主页

[https://pic.stackoverflow.wiki/uploadImages/123/113/178/101/2019/12/04/00/19/4acc2d62-e31f-4ab0-9b61-22965dd26804.png](https://pic.stackoverflow.wiki/uploadImages/123/113/178/101/2019/12/04/00/19/4acc2d62-e31f-4ab0-9b61-22965dd26804.png)

### 菠萝博客已部署成功

[https://pic.stackoverflow.wiki/uploadImages/123/113/178/101/2019/12/04/00/19/9f109db0-92f8-48e2-aa83-867b69345091.png](https://pic.stackoverflow.wiki/uploadImages/123/113/178/101/2019/12/04/00/19/9f109db0-92f8-48e2-aa83-867b69345091.png)

### 设置个人密码、头像

[https://pic.stackoverflow.wiki/uploadImages/123/113/178/101/2019/12/04/00/19/34067167-49a9-4a79-a93f-8e965888ba04.png](https://pic.stackoverflow.wiki/uploadImages/123/113/178/101/2019/12/04/00/19/34067167-49a9-4a79-a93f-8e965888ba04.png)

### B3log Key相关设置

[https://pic.stackoverflow.wiki/uploadImages/123/113/178/101/2019/12/04/00/19/d353a30e-4b06-4542-b3be-8b635dc76e69.png](https://pic.stackoverflow.wiki/uploadImages/123/113/178/101/2019/12/04/00/19/d353a30e-4b06-4542-b3be-8b635dc76e69.png)