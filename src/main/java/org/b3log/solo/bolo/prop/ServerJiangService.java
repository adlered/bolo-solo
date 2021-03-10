package org.b3log.solo.bolo.prop;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.solo.model.Option;
import org.b3log.solo.service.OptionQueryService;
import org.b3log.solo.util.Solos;
import org.json.JSONObject;

/**
 * Server酱发信服务
 */
public class ServerJiangService {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ServerJiangService.class);

    public static boolean send(String title, String desp, String sendKey) {
        final HttpResponse response = HttpRequest.
                post("https://sctapi.ftqq.com/" + sendKey + ".send").
                form("title", title).
                form("desp", desp).
                connectionTimeout(3000).
                timeout(7000).
                trustAllCerts(true).
                header("User-Agent", Solos.USER_AGENT).
                send();
        return response.statusCode() == 200;
    }

    public static void remindAdmin(String blogSite, String user, String comment, String blogTitle) {
        final BeanManager beanManager = BeanManager.getInstance();
        final OptionQueryService optionQueryService = beanManager.getReference(OptionQueryService.class);
        final JSONObject preference = optionQueryService.getPreference();
        final String sendKey = preference.getString(Option.ID_C_SEND_KEY);
        if (!sendKey.isEmpty()) {
            String title = "您的博客有新动态";
            String desp = "您的 " + blogTitle + " 博客收到用户 " + user + " 的评论  \n" +
                    "评论链接：" + blogSite + "  \n" +
                    "评论内容：  \n" + comment;
            if (!send(title, desp, sendKey)) {
                LOGGER.log(Level.ERROR, "Server Jiang Remind send failed, please check your \"sendKey\" option. Free accounts can only send 5 times a day, maybe the limit is reached?");
            } else {
                LOGGER.log(Level.INFO, "Server Jiang Remind sent. [title=" + title + ", desp=" + desp + "]");
            }
        }
    }
}
