package org.b3log.solo.bolo.prop;

import io.github.biezhi.ome.OhMyEmail;
import io.github.biezhi.ome.SendMailException;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.servlet.HttpMethod;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.solo.util.Solos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <h3>bolo-solo</h3>
 * <p>属性 API.</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-12-20 20:02
 **/
@RequestProcessor
public class MailProcessor {
    private static final Logger LOGGER = Logger.getLogger(MailProcessor.class);

    @RequestProcessing(value = "/prop/mail/send", method = {HttpMethod.GET})
    public void sendMail(final RequestContext context) {
        if (!Solos.isAdminLoggedIn(context)) {
            context.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        HttpServletRequest request = context.getRequest();
        String subject = request.getParameter("subject");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String html = request.getParameter("html");

        try {
            OhMyEmail.subject(subject)
                    .from(from)
                    .to(to)
                    .html(html)
                    .send();

            context.renderJSON().renderCode(200);
            context.renderJSON().renderMsg("Mail has sent.");

            return;
        } catch (SendMailException SME) {
            LOGGER.log(Level.ERROR, "Send mail failed! Please check your MailBox Settings.");

            context.renderJSON().renderCode(500);
            context.renderJSON().renderMsg("Send mail failed! Please check your MailBox Settings.");

            return;
        }
    }

    /*
        === 静态方法区 ===
     */

    public static void localSendMailMethod(String subject, String from, String to, String html) throws SendMailException {
        OhMyEmail.subject(subject)
                .from(from)
                .to(to)
                .html(html)
                .send();
    }
}
