package org.b3log.solo.bolo.prop;

import io.github.biezhi.ome.SendMailException;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.solo.processor.CommentProcessor;

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
        String emailAdd = MailService.getEmailAddressByCommentId(originalId);
        try {
            MailProcessor.localSendMailMethod(
                    "Bolo 博客 - 你的新提醒",
                    "菠萝博客管家",
                    emailAdd,
                    whoCommentHim + "刚刚回复了你的评论！请点击进入查看：<a href='" + URL + "'>" + URL + "</a>"
            );
        } catch (SendMailException SME) {
        }
    }
}
