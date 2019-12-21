package org.b3log.bolo.prop;

import io.github.biezhi.ome.OhMyEmail;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.solo.SoloServletListener;

/**
 * <h3>bolo-solo</h3>
 * <p>邮件设置</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-12-21 14:37
 **/
public class MailProp {
    private static final Logger LOGGER = Logger.getLogger(MailProp.class);

    public static String getMailSet() {
        String mailBox = Prop.getProperty("mailBox");
        String mailUsername = Prop.getProperty("mailUsername");
        String mailPassword = Prop.getProperty("mailPassword");

        return "[mailBox=" + mailBox + ", mailUsername=" + mailUsername + ", mailPassword=" + mailPassword + "]";
    }

    public static void setMail(String mailBox, String mailUsername, String mailPassword) {
        Prop.setProperty("mailBox", mailBox);
        Prop.setProperty("mailUsername", mailUsername);
        Prop.setProperty("mailPassword", mailPassword);
    }

    public static void loadMailSettings() {
        String mailBox = Prop.getProperty("mailBox");
        String mailUsername = Prop.getProperty("mailUsername");
        String mailPassword = Prop.getProperty("mailPassword");
        if (!mailBox.isEmpty()) {
            if (!mailUsername.isEmpty() && !mailPassword.isEmpty()) {
                if (mailBox.equals("QQ")) {
                    OhMyEmail.config(OhMyEmail.SMTP_QQ(false), mailUsername, mailPassword);
                    LOGGER.log(Level.INFO, "Mailbox Settings loaded successfully " + getMailSet());

                    return ;
                } else if (mailBox.equals("QQ_ENT")) {
                    OhMyEmail.config(OhMyEmail.SMTP_ENT_QQ(false), mailUsername, mailPassword);
                    LOGGER.log(Level.INFO, "Mailbox Settings loaded successfully " + getMailSet());

                    return ;
                } else if (mailBox.equals("163")) {
                    OhMyEmail.config(OhMyEmail.SMTP_163(false), mailUsername, mailPassword);
                    LOGGER.log(Level.INFO, "Mailbox Settings loaded successfully " + getMailSet());

                    return ;
                }
            }
        }
        LOGGER.log(Level.WARN, "Cannot load Mailbox Settings, please check " + getMailSet());
    }
}
