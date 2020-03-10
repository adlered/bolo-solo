<p align = "center">
<img alt="Bolo" src="bolo-logo-256.png">
<br><br>
基于 Solo 的离线多功能版 Bolo 博客，使用 Java 编写，专为程序员设计
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

* [Bolo 的功能亮点](#Bolo-的功能亮点)  
* [全新！使用 Docker 光速安装 Bolo](#使用-Docker-光速安装-Bolo)
* [Bolo 多种安装方法](#Bolo-多种安装方法)  
* [轻松更新 Bolo](#tomcat-%E8%BD%BB%E6%9D%BE%E6%9B%B4%E6%96%B0-Bolo)  
* [视频教程](#视频教程)  
* [其它帮助信息](#其它帮助信息)  
* [轻松迁移：迁移至 Bolo / 迁回至 Solo](#轻松迁移迁移至-bolo--迁回至-solo)  
* [皮肤](#皮肤)  

# 公告

### :tada: 菠萝版本信息

菠萝当前版本：v1.4 稳定版  
对应 Solo 原版版本号：v4.0.0  

### 当前版本注意事项：

* v1.4 稳定版发布啦！Bolo 稳定版支持 Docker、Tomcat 部署，多种部署方法请看下方的《Bolo 多种安装方法》指引 👇
* v1.4 版本新增 6 款皮肤支持！
* 自 v1.3 版本开始，Bolo 支持 **自定义图床** 功能，支持以下存储服务图床：

1. 七牛云
2. 阿里云
3. 又拍云
4. 基于 [Picuang](https://github.com/AdlerED/Picuang) 的自搭建图床
5. 黑客派公共图床（默认）

# Bolo 预览图

[点我查看 Bolo 预览图](/preview/preview.md)

### 使用 Bolo 驱动的博客

如果你也使用 Bolo 驱动你的博客，欢迎向本 README 提出 Pull Request ~

* [✨贼拉正经的技术博客](https://www.stackoverflow.wiki/blog)
* [🐭鼠鼠在碎觉](https://sszsj.top/)
* [☁刘欣的代码笔记](https://www.liuxincode.cn/)
* [:smile_cat:GeekTom | Blog](https://blog.zhqy.xyz)
* [:tropical_fish:糖醋鱼的小破站](https://expoli.tech/)

# Bolo 的功能亮点

- [x] 本地登录
- [x] 提供持续的 WAR 包支持，支持 Tomcat 部署，支持 JAR 方式运行
- [x] 和黑客派社区同步文章、评论、个人联系方式、使用社区图床
- [x] 通过黑客派备份博客中的全部文章
- [x] 用户的评论，如果有人回复会通过用户设定的邮箱地址回复（管理员需在后台设置发信邮箱）
- [x] 视频教程，小白轻松部署
- [x] 自定义图床

### 为什么选择 Bolo

* 核：基于强大的 [Solo](https://github.com/88250/solo) 进行修改，解析快、占用资源少
* 根：本地化环境，数据存储于本地服务器。使用本地账号登录 / 评论
* 稳：更适合求稳、专心写文的博主：如非必要，不必升级 Bolo
* 易：轻松在 Solo / Bolo 之间切换
* 能：强化分类功能，每个文章只拥有一个分类，分类功能独立
* 跨：支持 Tomcat / Jetty / Docker 部署 Bolo（原版Solo v3.6.7 及以上已更新至 Latke 框架，不再支持 Tomcat / Jetty）
* 选：Bolo 支持 B3log 体系，可选支持[黑客派](https://hacpai.com)的 B3log Key 实现图床及社区文章评论、联系方式同步功能
* 随：跟随 [原版 Solo](https://github.com/88250/solo) 同步更新版本号及功能，同步支持最新版本 Solo / Bolo 之间切换
* 安：完全去除对 GitHub 的支持， Bolo 是完全独立的博客，B3log Key 也仅是调用 API：除你自己，没人能操作你的博客，确保数据的安全
* 兼：版本更新不会去掉已有大功能（例如：本地H2数据库、Servlet框架等），保证对旧版本用户的友好，确保旧版本用户可流畅更新至最新版本
* 听：与官方保持更新的同时，更新人性化、简洁、实用的小功能，感谢你提出各种BUG、建议、意见
* 教：Bolo 将全面更新 Bolo 迁移、新建、管理的教学视频（对小白十分友好）及文档，解决你遇到的绝大部分问题（2020年之前完成）

# 特别鸣谢

|:construction_worker:|:construction_worker:|:construction_worker:|
|:-------------------:|:-------------------:|:-------------------:|
|<img height='48' width='48' src='https://avatars3.githubusercontent.com/u/873584?v=4'>|<img height='48' width='48' src='https://avatars0.githubusercontent.com/u/14257327?v=4'>|<img height='48' width='48' src='https://avatars1.githubusercontent.com/u/23192332?v=4'>|
|[@88250](https://github.com/88250)|[@csfwff](https://github.com/csfwff)|[@InkDP](https://github.com/InkDP)|
|[Solo 原作者](https://hacpai.com)|[Bolo 测试、Logo 绘制、前端技术支持](https://sszsj.top)|[默认皮肤 bolo-nexmoe 原作者](https://inkdp.cn)|

# 使用 Docker 光速安装 Bolo

我们对 Bolo 的 Docker 部署逻辑进行了全新设计，现在 Bolo 可以更灵活地在 Docker 中进行运行、修改、调试了！  

* 我们放弃了使用 Maven 在线部署，它通常需要 20 分钟才可以搞定；新版 Bolo Docker 可以在 15 秒内解决问题。
* 新的 Bolo Docker 将 Bolo 整合到 Tomcat 当中，且支持 Tomcat APR 模式，运行在 Tomcat 最高效的模式中。
* 通过一个带有**中文注释**的配置文件，修改你的数据库、HTTP 连接方式，简单易用。
* 在配置文件中一键连接你的 [lute-http](https://github.com/88250/lute-http) Markdown 渲染服务。
* 更新仅需替换 Bolo 源码！
* 旧版 Docker 安装方式会被保留。

[点击这里，使用新版 Bolo Docker 部署你的菠萝博客！](https://github.com/AdlerED/bolo-docker)

# Bolo 多种安装方法

### 使用 Tomcat 安装 Bolo（推荐）

#### 全新安装

[点我！查看安装教程并下载最新版菠萝博客](https://github.com/AdlerED/bolo-solo/releases)

#### Tomcat 轻松**更新** Bolo

1. 进入 Bolo 所在目录，备份 `WEB-INF/classes/local.properties` 和 `WEB-INF/classes/latke.properties`
2. 清空目录
3. 从上方安装地址下载最新版 Bolo
4. 解压至目录
5. 将备份的两个文件替换回去
6. 完成！

### 从 Docker 使用 Bolo

先克隆 Bolo 的源代码：

```shell script
git clone https://github.com/AdlerED/bolo-solo
```

再进入到 Bolo 项目的根目录，构建 Docker 镜像：

```shell script
docker build -t "bolo" .
```

最后，每次输入下面的命令启动 Bolo：

```shell script
docker run -it -d -p8080:8080 --env RUNTIME_DB="MYSQL" \
--env JDBC_USERNAME="root" \
--env JDBC_PASSWORD="123456" \
--env JDBC_DRIVER="com.mysql.cj.jdbc.Driver" \
--env JDBC_URL="jdbc:mysql://192.168.2.253:3306/solo?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC" \
--rm \
bolo --listen_port=8080 --server_scheme=http --server_host=192.168.2.253
```

将 `JDBC_USERNAME` 修改为你的 MySQL数据库用户名  
`JDBC_PASSWORD` 修改为数据库密码  
修改 `JDBC_URL` 下的 `192.168.2.253` 为你服务器的域名（如没有域名，填写IP地址）  
`server_host` 也需要改为你的域名（或IP地址）  
`-p8080:8080` 和 `--listen_port=8080`为端口参数，把三个8080改为你想设置的端口即可。  
记得要先在 MySQL 数据库中创建空的 `solo` 数据库哦~  

如果启动后无法访问，可以去掉命令中的 `-d` 参数，使 Bolo 在前台运行，这样你就可以看到日志了！

### 使用 Java 命令运行（不推荐）

下载最新的 [Bolo](https://github.com/AdlerED/bolo-solo/releases) 压缩包解压，进入解压目录执行：

Windows: `java -cp "WEB-INF/lib/*;WEB-INF/classes" org.b3log.solo.Starter`  
Unix-like: `java -cp "WEB-INF/lib/*:WEB-INF/classes" org.b3log.solo.Starter`

# 视频教程

视频教程公开在`云存储`中，请[点击这里](https://ftp.stackoverflow.wiki)在线观看。

# 其它帮助信息

* [如何添加社交按钮的内容？](https://hacpai.com/article/1574741650421/comment/1574746366256)

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

当前支持且已移植并预置的皮肤：

1. `V1.0` 及以上版本：solo-nexmoe → bolo-nexmoe
2. `V1.1` 及以上版本：emiya -> bolo-emiya
3. `V1.1` 及以上版本：bubble -> bolo-bubble
4. `V1.1` 及以上版本：casper -> bolo-casper
5. `V1.1` 及以上版本：pinghsu -> bolo-pinghsu
6. `V1.4` 及以上版本：9IPHP -> bolo-9IPHP
7. `V1.4` 及以上版本：Jane -> bolo-Jane
8. `V1.4` 及以上版本：NeoEase -> bolo-NeoEase
9. `V1.4` 及以上版本：next -> bolo-next
10. `V1.4` 及以上版本：nijigen ->  bolo-nijigen
11. `V1.4` 及以上版本：yilia -> bolo-yilia

## 针对 Solo 博客的改进和优化

#### [如果你还没有用过 Bolo 基于的原版 Solo 博客，点我进入 Solo 博客项目主页！](https://github.com/88250/Solo)

- [x] 轻松迁移
- [x] 本地评论功能（无需登录）
- [x] 分类功能优化（解除分类与标签的关联，超级好用）
- [x] 评论频率限制（60秒/2次）
- [x] 自定义主页存档显示数量
- [x] 本地化JS，防止CDN造成的加载失败
- [x] 自定义主页显示存档数量
- [x] 免登录评论
- [x] 自定义图床

# 声明

`Bolo` 是 `Solo` 的修改版，我们始终支持 B3log 生态，支持与黑客派同步文章、评论、联系方式，支持社区图床。  