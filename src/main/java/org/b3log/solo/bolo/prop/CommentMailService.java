package org.b3log.solo.bolo.prop;

import io.github.biezhi.ome.SendMailException;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.solo.model.Option;
import org.b3log.solo.processor.CommentProcessor;
import org.b3log.solo.service.InitService;
import org.b3log.solo.service.OptionQueryService;
import org.json.JSONObject;

/**
 * <h3>bolo-solo</h3>
 * <p>为评论服务.</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-12-25 00:56
 **/
@RequestProcessor
public class CommentMailService {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(CommentMailService.class);

    public static void remindCommentedGuy(String originalId, String URL, String whoCommentHim) {
        BeanManager beanManager = BeanManager.getInstance();
        OptionQueryService optionQueryService = beanManager.getReference(OptionQueryService.class);
        JSONObject preference = optionQueryService.getPreference();
        String blogTitle = preference.getString(Option.ID_C_BLOG_TITLE);

        String emailAdd = MailService.getEmailAddressByCommentId(originalId);
        MailProcessor.localSendMailMethod(
                "Bolo 博客 - 你的新提醒",
                blogTitle,
                emailAdd,
                "<b>" + whoCommentHim + "</b> 刚刚回复了你的评论！请点击进入查看：<a href='" + URL + "'>" + URL + "</a>"
        );
    }
}
