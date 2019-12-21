package org.b3log.solo.bolo.prop;

import io.github.biezhi.ome.OhMyEmail;
import org.b3log.latke.Keys;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.Transaction;
import org.b3log.solo.model.Option;
import org.b3log.solo.repository.OptionRepository;
import org.json.JSONObject;

/**
 * <h3>bolo-solo</h3>
 * <p>邮箱设定</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-12-21 16:44
 **/
public class MailService {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(MailService.class);

    public static void loadMailSettings() {
        try {
            final BeanManager beanManager = BeanManager.getInstance();
            final OptionRepository optionRepository = beanManager.getReference(OptionRepository.class);

            String mailUserContext = "";
            try {
                mailUserContext = optionRepository.get(Option.ID_C_MAIL_USER_CONTEXT).optString(Option.OPTION_VALUE);
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
}
