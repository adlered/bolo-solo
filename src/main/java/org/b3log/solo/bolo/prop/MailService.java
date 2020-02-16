/*
 * Solo - A small and beautiful blogging system written in Java.
 * Copyright (c) 2010-present, b3log.org
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
package org.b3log.solo.bolo.prop;

import io.github.biezhi.ome.OhMyEmail;
import org.b3log.latke.Keys;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.servlet.HttpMethod;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.solo.bolo.prop.bind.MailBind;
import org.b3log.solo.model.Option;
import org.b3log.solo.repository.OptionRepository;
import org.b3log.solo.util.Solos;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>bolo-solo</h3>
 * <p>邮箱设定</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-12-21 16:44
 **/
@RequestProcessor
public class MailService {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(MailService.class);

    /**
     * 添加指定用户评论的邮件提醒服务
     *
     * @param commentId
     * @param commentUser
     * @param commentEmail
     */
    public static void addCommentMailContext(String commentId, String commentUser, String commentEmail) {
        final BeanManager beanManager = BeanManager.getInstance();
        final OptionRepository optionRepository = beanManager.getReference(OptionRepository.class);
        String mailUserContext = "";
        try {
            mailUserContext = optionRepository.get(Option.ID_C_MAIL_USER_CONTEXT).optString(Option.OPTION_VALUE);
        } catch (Exception e) {
        }

        // Bind
        StringBuilder bind = new StringBuilder();
        bind.append(mailUserContext);
        if (!mailUserContext.isEmpty()) {
            bind.append(";");
        }
        bind.append(commentId)
                .append(":")
                .append(commentUser)
                .append(":")
                .append(commentEmail);
        // Write
        try {
            final Transaction transaction = optionRepository.beginTransaction();

            final JSONObject mailUserContextOpt = optionRepository.get(Option.ID_C_MAIL_USER_CONTEXT);
            mailUserContextOpt.put(Option.OPTION_VALUE, bind.toString());
            optionRepository.update(Option.ID_C_MAIL_USER_CONTEXT, mailUserContextOpt);

            transaction.commit();
        } catch (RepositoryException RE) {
        }
        LOGGER.log(Level.INFO, "Generate user comment context [commentId: " + commentId + ", commentUser: " + commentUser + ", email: " + commentEmail + "]");
    }

    /**
     * 清空指定用户评论的邮件提醒服务列表
     */
    @RequestProcessing(value = "/prop/mail/clear", method = {HttpMethod.GET})
    public void clearCommentMailContext(final RequestContext context) {
        if (!Solos.isAdminLoggedIn(context)) {
            context.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        final BeanManager beanManager = BeanManager.getInstance();
        final OptionRepository optionRepository = beanManager.getReference(OptionRepository.class);

        // Clear ALL context
        try {
            final Transaction transaction = optionRepository.beginTransaction();

            final JSONObject mailUserContextOpt = optionRepository.get(Option.ID_C_MAIL_USER_CONTEXT);
            mailUserContextOpt.put(Option.OPTION_VALUE, "");
            optionRepository.update(Option.ID_C_MAIL_USER_CONTEXT, mailUserContextOpt);

            transaction.commit();
        } catch (RepositoryException RE) {
        }

        LOGGER.log(Level.INFO, "All comment mail context cleared successfully.");

        context.renderJSON().renderCode(200);
        context.renderJSON().renderMsg("All comment mail context cleared successfully.");
    }

    /**
     * 通过评论 Id 获取指定用户邮箱地址
     *
     * @param commentId
     * @return
     */
    public static String getEmailAddressByCommentId(String commentId) {
        List<MailBind> mailBindList = getUserMailContext();
        for (int i = (mailBindList.size() - 1); i >= 0; i--) {
            MailBind mailBind = mailBindList.get(i);
            if (mailBind.getCommentId().equals(commentId)) {
                return mailBind.getCommentEmail();
            }
        }
        return "";
    }

    public static String getUsernameByCommentId(String commentId) {
        List<MailBind> mailBindList = getUserMailContext();
        for (int i = (mailBindList.size() - 1); i >= 0; i--) {
            MailBind mailBind = mailBindList.get(i);
            if (mailBind.getCommentId().equals(commentId)) {
                return mailBind.getCommentUser();
            }
        }
        return "";
    }

    /**
     * 将 Mail Context 转换为可视列表
     *
     * @return
     */
    public static List<MailBind> getUserMailContext() {
        List<MailBind> mailBindList = new ArrayList<>();

        try {
            final BeanManager beanManager = BeanManager.getInstance();
            final OptionRepository optionRepository = beanManager.getReference(OptionRepository.class);
            String mailUserContext = "";
            try {
                mailUserContext = optionRepository.get(Option.ID_C_MAIL_USER_CONTEXT).optString(Option.OPTION_VALUE);
            } catch (Exception e) {
            }
            String[] perUser = mailUserContext.split(";");
            for (int i = 0; i < perUser.length; i++) {
                try {
                    String[] perSet = perUser[i].split(":");
                    String commentId = perSet[0];
                    String commentUser = perSet[1];
                    String commentEmail = perSet[2];
                    MailBind mailBind = new MailBind();
                    mailBind.setCommentId(commentId);
                    mailBind.setCommentUser(commentUser);
                    mailBind.setCommentEmail(commentEmail);
                    mailBindList.add(mailBind);
                } catch (Exception e) {
                    // 分割出问题，一定要抓取然后继续
                    continue;
                }
            }
        } catch (Exception e) {
        }

        return mailBindList;
    }

    /**
     * 重载邮件设置至内存
     */
    public static void loadMailSettings() {
        try {
            final BeanManager beanManager = BeanManager.getInstance();
            final OptionRepository optionRepository = beanManager.getReference(OptionRepository.class);

            try {
                optionRepository.get(Option.ID_C_MAIL_USER_CONTEXT).optString(Option.OPTION_VALUE);
            } catch (RepositoryException | NullPointerException e) {
                final Transaction transaction = optionRepository.beginTransaction();
                JSONObject mailUserContextOpt = new JSONObject();
                mailUserContextOpt.put(Keys.OBJECT_ID, Option.ID_C_MAIL_USER_CONTEXT);
                mailUserContextOpt.put(Option.OPTION_CATEGORY, Option.CATEGORY_C_PREFERENCE);
                mailUserContextOpt.put(Option.OPTION_VALUE, "");
                optionRepository.add(mailUserContextOpt);
                transaction.commit();
            }

            String mailBox = optionRepository.get(Option.ID_C_MAIL_BOX).optString(Option.OPTION_VALUE);
            String mailUsername = optionRepository.get(Option.ID_C_MAIL_USERNAME).optString(Option.OPTION_VALUE);
            String mailPassword = optionRepository.get(Option.ID_C_MAIL_PASSWORD).optString(Option.OPTION_VALUE);
            if (!mailBox.isEmpty()) {
                if (!mailUsername.isEmpty() && !mailPassword.isEmpty()) {
                    if (mailBox.equals("QQ")) {
                        OhMyEmail.config(OhMyEmail.SMTP_QQ(false), mailUsername, mailPassword);
                        LOGGER.log(Level.INFO, "Mailbox Settings loaded successfully " + getMailSet());

                        return;
                    } else if (mailBox.equals("QQ_ENT")) {
                        OhMyEmail.config(OhMyEmail.SMTP_ENT_QQ(false), mailUsername, mailPassword);
                        LOGGER.log(Level.INFO, "Mailbox Settings loaded successfully " + getMailSet());

                        return;
                    } else if (mailBox.equals("163")) {
                        OhMyEmail.config(OhMyEmail.SMTP_163(false), mailUsername, mailPassword);
                        LOGGER.log(Level.INFO, "Mailbox Settings loaded successfully " + getMailSet());

                        return;
                    }
                }
            }
            LOGGER.log(Level.WARN, "Cannot load Mailbox Settings, please check " + getMailSet());
        } catch (RepositoryException RE) {
            LOGGER.log(Level.WARN, "Cannot load Mailbox Settings, please check " + getMailSet());
        } catch (NullPointerException NPE) {
            LOGGER.log(Level.WARN, "Cannot load Mailbox Settings, please check.");
        }
    }

    /**
     * 获取可视化邮件服务器设定
     *
     * @return
     */
    public static String getMailSet() {
        try {
            final BeanManager beanManager = BeanManager.getInstance();
            final OptionRepository optionRepository = beanManager.getReference(OptionRepository.class);

            String mailBox = optionRepository.get(Option.ID_C_MAIL_BOX).optString(Option.OPTION_VALUE);
            String mailUsername = optionRepository.get(Option.ID_C_MAIL_USERNAME).optString(Option.OPTION_VALUE);
            String mailPassword = optionRepository.get(Option.ID_C_MAIL_PASSWORD).optString(Option.OPTION_VALUE);

            return "[mailBox=" + mailBox + ", mailUsername=" + mailUsername + ", mailPassword=" + mailPassword + "]";
        } catch (RepositoryException RE) {
            LOGGER.log(Level.WARN, "Cannot load Mailbox Settings, please check.");

            return "";
        }
    }
}
