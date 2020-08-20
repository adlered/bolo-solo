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
package org.b3log.solo.processor;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Role;
import org.b3log.latke.model.User;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.repository.jdbc.util.Connections;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.servlet.HttpMethod;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.util.Requests;
import org.b3log.latke.util.URLs;
import org.b3log.solo.bolo.tool.MD5Utils;
import org.b3log.solo.model.Option;
import org.b3log.solo.model.UserExt;
import org.b3log.solo.repository.OptionRepository;
import org.b3log.solo.service.*;
import org.b3log.solo.util.GitHubs;
import org.b3log.solo.util.Solos;
import org.json.JSONObject;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * OAuth processor.
 * <ul>
 * <li>Redirects to auth page (/oauth/github/redirect), GET</li>
 * <li>OAuth callback (/oauth/github), GET</li>
 * </ul>
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 2.9.5
 */
@RequestProcessor
public class OAuthProcessor {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(OAuthProcessor.class);

    /**
     * OAuth parameters - state.
     */
    private static final Set<String> STATES = ConcurrentHashMap.newKeySet();

    /**
     * Option query service.
     */
    @Inject
    private OptionQueryService optionQueryService;

    /**
     * Option management service.
     */
    @Inject
    private OptionMgmtService optionMgmtService;

    /**
     * User query service.
     */
    @Inject
    private UserQueryService userQueryService;

    /**
     * User management service.
     */
    @Inject
    private UserMgmtService userMgmtService;

    /**
     * Initialization service.
     */
    @Inject
    private InitService initService;

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

    /**
     * Bolo admin login.
     *
     * @param context
     */
    @RequestProcessing(value = "/oauth/bolo/login", method = HttpMethod.POST)
    public void adminLogin(final RequestContext context) {
        HttpServletResponse response = context.getResponse();
        HttpServletRequest request = context.getRequest();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String md5 = MD5Utils.stringToMD5(password);
        try {
            if (UpgradeService.boloFastMigration) {
                // 快速迁移程序
                final JSONObject preference = optionQueryService.getPreference();
                final String currentVer = preference.getString(Option.ID_C_VERSION);
                fastMigrate(currentVer, username, md5, context);
                context.sendRedirect(Latkes.getServePath() + "/");
            } else if (!initService.isInited()) {
                LOGGER.log(Level.INFO, "Bolo initializing...");
                final JSONObject initReq = new JSONObject();
                initReq.put(User.USER_NAME, username);
                initReq.put(UserExt.USER_B3_KEY, password);
                initReq.put(UserExt.USER_AVATAR, "https://pic.stackoverflow.wiki/uploadImages/117/136/73/84/2020/08/03/19/59/2c12286b-91a0-478e-ba47-edaa21f19476.png");
                initReq.put(UserExt.USER_GITHUB_ID, "000000");
                initService.init(initReq);
                context.sendRedirect(Latkes.getServePath() + "/");
            } else {
                try {
                    JSONObject user = userQueryService.getUserByName(username);
                    String cUser = user.optString(User.USER_NAME);
                    String cPass = user.optString(UserExt.USER_B3_KEY);
                    // 非管理员不允许登录
                    if (!Role.ADMIN_ROLE.equals(user.getString(User.USER_ROLE)) && !Role.DEFAULT_ROLE.equals(user.getString(User.USER_ROLE))) {
                        context.sendRedirect(Latkes.getServePath() + "/start?status=error");
                    }
                    // 同时兼容明文和密文密码
                    if ((username.equals(cUser) && password.equals(cPass)) || (username.equals(cUser) && md5.equals(cPass))) {
                        Solos.login(user, context.getResponse());
                        LOGGER.log(Level.INFO, "Logged in [name={0}, remoteAddr={1}] with Bolo auth", username, Requests.getRemoteAddr(request));
                        context.sendRedirect(Latkes.getServePath() + "/admin-index.do#main");
                    } else {
                        context.sendRedirect(Latkes.getServePath() + "/start?status=error");
                    }
                } catch (NullPointerException NPE) {
                    context.sendRedirect(Latkes.getServePath() + "/start?status=error");
                }
            }
        } catch (final Exception e) {
            LOGGER.log(Level.WARN, "Can not write cookie", e);
        }
    }

    private void fastMigrate(String currentVer, String username, String password, final RequestContext context) throws SQLException, RepositoryException {
        int version = Integer.parseInt(currentVer.replaceAll("\\.", ""));
        if (version <= 420) {
            // 低版本，正常迁移
            migrateLow(version, username, password, context);
        } else {
            // 高版本，先加表
            migrateHigh(version);
            migrateLow(version, username, password, context);
        }
    }

    private void migrateLow(int version, String username, String password, final RequestContext context) throws SQLException {
        Connection connection = Connections.getConnection();
        Statement statement = connection.createStatement();
        String tablePrefix = Latkes.getLocalProperty("jdbc.tablePrefix") + "_";
        // 清空表
        try {
            statement.executeUpdate("DELETE FROM `" + tablePrefix + "category`;");
        } catch (Exception ignored) {
        }
        try {
            statement.executeUpdate("DELETE FROM `" + tablePrefix + "category_tag`;");
        } catch (Exception ignored) {
        }
        try {
            statement.executeUpdate("DELETE FROM `" + tablePrefix + "user` WHERE userRole = 'adminRole';");
        } catch (Exception ignored) {
        }
        try {
            statement.executeUpdate("INSERT INTO `" + tablePrefix + "user` ( `oId`, `userName`, `userURL`, `userRole`, `userAvatar`, `userB3Key`, `userGitHubId` ) VALUES ( 'default', '" + username + "', '" + Latkes.getServePath() + "', 'adminRole', 'https://pic.stackoverflow.wiki/uploadImages/117/136/73/84/2020/08/03/19/59/2c12286b-91a0-478e-ba47-edaa21f19476.png', '" + password + "', 'none' );");
        } catch (Exception ignored) {
        }
        statement.close();
        connection.commit();
        connection.close();
        // 改皮肤
        UpgradeService.boloFastMigration = false;
        BeanManager beanManager = BeanManager.getInstance();
        JSONObject skin = optionQueryService.getSkin();
        final SkinMgmtService skinMgmtService = beanManager.getReference(SkinMgmtService.class);
        try {
            skinMgmtService.loadSkins(skin);
        } catch (Exception ignored) {
        }
        // 升级
        final UpgradeService upgradeService = beanManager.getReference(UpgradeService.class);
        upgradeService.upgrade();
        // 跳转
        context.sendRedirect(Latkes.getServePath() + "/");
    }

    private void migrateHigh(int version) throws SQLException, RepositoryException {
        // 高版本降级到4.2.0
        Connection connection = Connections.getConnection();
        Statement statement = connection.createStatement();
        String tablePrefix = Latkes.getLocalProperty("jdbc.tablePrefix") + "_";
        // 降级表到4.2.0
        // 添加列
        try {
            statement.executeUpdate("ALTER TABLE `" + tablePrefix + "article` ADD COLUMN `articleCommentCount` int(11) NOT NULL COMMENT '文章评论计数' DEFAULT 0;");
        } catch (Exception ignored) {
        }
        try {
            statement.executeUpdate("ALTER TABLE `" + tablePrefix + "article` ADD COLUMN `articleViewCount` int(11) NOT NULL COMMENT '文章浏览计数' DEFAULT 0;");
        } catch (Exception ignored) {
        }
        try {
            statement.executeUpdate("ALTER TABLE `" + tablePrefix + "article` ADD COLUMN `articleCommentable` char(1) NOT NULL COMMENT '文章是否可以评论' DEFAULT '1';");
        } catch (Exception ignored) {
        }
        // comment表
        try {
            statement.executeUpdate("CREATE TABLE `b3_solo_comment` ( `oId` varchar(19) NOT NULL COMMENT '主键', `commentContent` text NOT NULL COMMENT '评论内容', `commentCreated` bigint(20) NOT NULL COMMENT '评论时间戳', `commentName` varchar(50) NOT NULL COMMENT '评论人名称', `commentOnId` varchar(19) NOT NULL COMMENT '评论的文章/页面的 id', `commentSharpURL` varchar(255) NOT NULL COMMENT '评论访问路径，带 # 锚点', `commentThumbnailURL` text NOT NULL COMMENT '评论人头像图片链接地址', `commentURL` varchar(255) NOT NULL COMMENT '评论人链接地址', `commentOriginalCommentId` varchar(19) DEFAULT NULL COMMENT '评论回复时原始的评论 id，即父评论 id', `commentOriginalCommentName` varchar(50) DEFAULT NULL COMMENT '评论回复时原始的评论人名称，即父评论人名称', PRIMARY KEY (`oId`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';");
        } catch (Exception ignored) {
        }
        statement.close();
        connection.commit();
        connection.close();
        // 降级版本，添加设定
        BeanManager beanManager = BeanManager.getInstance();
        OptionRepository optionRepository = (OptionRepository)beanManager.getReference(OptionRepository.class);
        Transaction transaction = optionRepository.beginTransaction();
        JSONObject versionOpt = optionRepository.get("version");
        versionOpt.put("optionValue", "4.2.0");
        try {
            optionRepository.update("version", versionOpt, new String[0]);
        } catch (Exception ignored) {
        }
        List<Object[]> optList = new ArrayList<>();
        Collections.addAll(optList,
                    new Object[] { Option.ID_C_MOST_COMMENT_ARTICLE_DISPLAY_CNT, Option.CATEGORY_C_PREFERENCE, Option.DefaultPreference.DEFAULT_MOST_COMMENT_ARTICLE_DISPLAY_COUNT },
                    new Object[] { Option.ID_C_MOST_VIEW_ARTICLE_DISPLAY_CNT, Option.CATEGORY_C_PREFERENCE, Option.DefaultPreference.DEFAULT_MOST_VIEW_ARTICLES_DISPLAY_COUNT },
                    new Object[] { Option.ID_C_RECENT_COMMENT_DISPLAY_CNT, Option.CATEGORY_C_PREFERENCE, Option.DefaultPreference.DEFAULT_RECENT_COMMENT_DISPLAY_COUNT },
                    new Object[] { Option.ID_C_COMMENTABLE, Option.CATEGORY_C_PREFERENCE, Option.DefaultPreference.DEFAULT_COMMENTABLE }
                );
        for (Object[] i : optList) {
            saveOption(i, optionRepository);
        }
        transaction.commit();
        version = 420;
    }

    private void saveOption(Object[] opt, OptionRepository optionRepository) {
        final JSONObject optOpt = new JSONObject();
        optOpt
                .put(Keys.OBJECT_ID, opt[0])
                .put(Option.OPTION_CATEGORY, opt[1])
                .put(Option.OPTION_VALUE, opt[2]);
        try {
            optionRepository.add(optOpt);
        } catch (Exception ignored) {
        }
    }
}
