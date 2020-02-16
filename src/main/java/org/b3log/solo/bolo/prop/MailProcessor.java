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
            LOGGER.log(Level.INFO, "Mail has sent [subject=" + subject + ", from=" + from + ", to=" + to + ", html=" + html + "]");

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

    /**
     * 发送普通邮件
     *
     * @param subject
     * @param from
     * @param to
     * @param html
     * @throws SendMailException
     */
    public static void localSendMailMethod(String subject, String from, String to, String html) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OhMyEmail.subject(subject)
                            .from(from)
                            .to(to)
                            .html(html)
                            .send();
                    LOGGER.log(Level.INFO, "Mail has sent [subject=" + subject + ", from=" + from + ", to=" + to + ", html=" + html + "]");
                } catch (SendMailException SME) {
                    LOGGER.log(Level.INFO, "Mail sent failed [cause=" + SME.getCause() + ", subject=" + subject + ", from=" + from + ", to=" + to + ", html=" + html + "]");
                }
            }
        }).start();
    }
}
