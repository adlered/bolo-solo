package org.b3log.solo.bolo.prop;

import io.github.biezhi.ome.OhMyEmail;
import org.b3log.latke.Keys;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.solo.bolo.prop.bind.MailBind;
import org.b3log.solo.model.Option;
import org.b3log.solo.repository.OptionRepository;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     * List 化用户邮件关系表
     *
     * @return
     */
    @RequestProcessing("/maph")
    public void getUserMailContext(final RequestContext context) {
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
                String[] perSet = perUser[i].split(":");
                String commentId = perSet[0];
                String commentUser = perSet[1];
                String commentEmail = perSet[2];
                MailBind mailBind = new MailBind();
                mailBind.setCommentId(commentId);
                mailBind.setCommentUser(commentUser);
                mailBind.setCommentEmail(commentEmail);
                mailBindList.add(mailBind);
            }
        } catch (Exception e) {
        }
    }
}
