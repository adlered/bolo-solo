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

* [bolo-solo 多种安装方法](#bolo-solo-多种安装方法)  
* [轻松更新 bolo-solo](#tomcat-%E8%BD%BB%E6%9D%BE%E6%9B%B4%E6%96%B0-bolo-solo)  
* [视频教程](#视频教程)  
* [其它帮助信息](#其它帮助信息)  
* [轻松迁移：迁移至 Bolo / 迁回至 Solo](#轻松迁移迁移至-bolo--迁回至-solo)  
* [皮肤](#皮肤)  
* [当前实现的特性](#当前实现的特性)  

# 公告

### :tada: 菠萝版本信息
菠萝当前版本：v1.0 稳定版  
对应 Solo 原版版本号：v3.6.7（即支持 Solo v3.6.7即以下的用户轻松迁移/迁回）

### 当前版本注意事项：

* v1.0 稳定版发布啦！bolo-solo 稳定版支持 Docker、Tomcat 部署，多种部署方法请看下方的《bolo-solo 多种安装方法》指引 👇
* 稳定版可持续使用，欢迎你的体验、反馈、PR！

# bolo-solo 预览图

[点我查看 bolo-solo 预览图](/preview/preview.md)

### 使用 bolo-solo 驱动的博客

如果你也使用 bolo-solo 驱动你的博客，欢迎向本 README 提出 Pull Request ~

* [✨贼拉正经的技术博客](https://www.stackoverflow.wiki/blog)
* [🐭鼠鼠在碎觉](https://sszsj.top/)

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

# 特别鸣谢

|:construction_worker:|:construction_worker:|:construction_worker:|
|:-------------------:|:-------------------:|:-------------------:|
|<img height='48' width='48' src='https://avatars3.githubusercontent.com/u/873584?v=4'>|<img height='48' width='48' src='https://avatars0.githubusercontent.com/u/14257327?v=4'>|<img height='48' width='48' src='https://avatars1.githubusercontent.com/u/23192332?v=4'>|
|[@88250](https://github.com/88250)|[@csfwff](https://github.com/csfwff)|[@InkDP](https://github.com/InkDP)|
|[Solo 原作者](https://hacpai.com)|[Bolo 测试、Logo 绘制、前端技术支持](https://sszsj.top)|[默认皮肤 solo-nexmoe 原作者](https://inkdp.cn)|

# bolo-solo 多种安装方法

### 使用 Tomcat 安装 bolo-solo（推荐）

#### 全新安装

[点我！查看安装教程并下载最新版菠萝博客](https://github.com/AdlerED/bolo-solo/releases)

#### Tomcat 轻松**更新** bolo-solo

1. 进入 bolo-solo 所在目录，备份 `WEB-INF/classes/local.properties` 和 `WEB-INF/classes/latke.properties`
2. 清空目录
3. 从上方安装地址下载最新版 bolo-solo
4. 解压至目录
5. 将备份的两个文件替换回去
6. 完成！

### 从 Docker 使用 bolo-solo

先克隆 bolo-solo 的源代码：

```shell script
git clone https://github.com/AdlerED/bolo-solo
```

再进入到 bolo-solo 项目的根目录，构建 Docker 镜像：

```shell script
docker build -t "bolo" .
```

最后，每次输入下面的命令启动 bolo-solo：

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

如果启动后无法访问，可以去掉命令中的 `-d` 参数，使 bolo-solo 在前台运行，这样你就可以看到日志了！

### 使用 Java 命令运行（不推荐）

下载最新的 [bolo-solo](https://github.com/AdlerED/bolo-solo/releases) 压缩包解压，进入解压目录执行：

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

目前移植的皮肤：

1. `V1.0` 及以上版本：solo-nexmoe → bolo-nexmoe
2. `V1.1` 及以上版本：emiya -> bolo-emiya
3. `V1.1` 及以上版本：bubble -> bolo-bubble
4. `V1.1` 及以上版本：casper -> bolo-casper
5. `V1.1` 及以上版本：pinghsu -> bolo-pinghsu

正在加班加点努力移植... 推荐你先使用当前版本的 bolo-solo，等待你需要的皮肤更新后，直接提取 skins 文件中的皮肤，放到你的 skins 文件夹下即可~

# 当前实现的特性

- [x] 本地登录
- [x] 提供持续的WAR包支持，支持Tomcat部署
- [x] 和黑客派社区同步文章、评论、个人联系方式、使用社区图床
- [x] 本地评论功能（无需登录）
- [x] 分类功能优化（解除分类与标签的关联，超级好用）
- [x] 评论频率限制（60秒/次）
- [x] 自定义主页存档显示数量
- [x] 本地化JS，防止CDN造成的加载失败
- [x] 自定义主页显示存档数量
- [ ] 皮肤显示文章日期跟随“启用文章更新提示”选项
- [ ] 本地自定义社交按钮
- [ ] 代码显示行号功能
- [ ] 自定义评论频率限制
- [ ] 评论审核功能
- [ ] 关闭搜索功能（1.国内备案管局不允许个人建站带有搜索功能 2.节约服务器资源）

### 关于 Solo V4

[Solo V4](https://hacpai.com/article/1571544590916) 版本将会有很大的变动，其将主要支持静态化页面。  
届时 Bolo 不会完全更新 Solo V4 版本的内容，Bolo 会**始终保持旧版本 Bolo 用户的平滑更新升级**。  
Bolo 在 Solo V4 版本之后仍会跟随 Solo 的版本号同步更新，同时引入 Solo V4 的新功能与特性，但为了维持大家的使用习惯，Bolo 的更新内容将不会激进。  
