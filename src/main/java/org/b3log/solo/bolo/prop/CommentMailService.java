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

import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.solo.model.Option;
import org.b3log.solo.service.OptionQueryService;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public static void remindCommentedGuy(String originalId, String URL, String whoCommentHim, String blogTitle) {
        String emailAdd = MailService.getEmailAddressByCommentId(originalId);
        String username = MailService.getUsernameByCommentId(originalId);

        String html = "<div style=\"background-color:#ECECEC; padding: 35px;\"><table cellpadding=\"0\" align=\"center\"style=\"width: 600px; margin: 0px auto; text-align: left; position: relative; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 5px; border-bottom-left-radius: 5px; font-size: 14px; font-family:微软雅黑, 黑体; line-height: 1.5; box-shadow: rgb(153, 153, 153) 0px 0px 5px; border-collapse: collapse; background-position: initial initial; background-repeat: initial initial;background:#fff;\"><tbody><tr><th valign=\"middle\"style=\"height: 25px; line-height: 25px; padding: 15px 35px; border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #42a3d3; background-color: #49bcff; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;\"><font face=\"微软雅黑\" size=\"5\" style=\"color: rgb(255, 255, 255); \">" + blogTitle + " - 新评论提醒</font></th></tr><tr><td><div style=\"padding:25px 35px 40px; background-color:#fff;\"><h2 style=\"margin: 5px 0px; \"><font color=\"#333333\" style=\"line-height: 20px; \"><font style=\"line-height: 22px; \" size=\"4\">你好，" + username + "</font></font></h2><p>你在 " + blogTitle + " 博客中的评论被 " + whoCommentHim + " 回复了！<br>请 <b><a href=\"" + URL + "\">轻点这里</a></b> 跳转至评论。<br><p align=\"right\">" + blogTitle + "</p><p align=\"right\">" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "</p><div style=\"width:700px;margin:0 auto;\"><div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\"><p>此为系统邮件，请勿回复<br></p><p>©" + new SimpleDateFormat("yyyy").format(new Date()) + " <a href='https://github.com/AdlerED/bolo-solo'>由菠萝博客强力驱动</a></p></div></div></div></td></tr></tbody></table></div>";

        MailProcessor.localSendMailMethod(
                blogTitle + "→有人回复了你",
                blogTitle,
                emailAdd,
                html
        );
    }

    public static void remindAdmin(String replyRemindMailBoxAddress, String blogSite, String user, String comment, String blogTitle) {
        String html = "<div style=\"background-color:#ECECEC; padding: 35px;\"><table cellpadding=\"0\" align=\"center\"style=\"width: 600px; margin: 0px auto; text-align: left; position: relative; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 5px; border-bottom-left-radius: 5px; font-size: 14px; font-family:微软雅黑, 黑体; line-height: 1.5; box-shadow: rgb(153, 153, 153) 0px 0px 5px; border-collapse: collapse; background-position: initial initial; background-repeat: initial initial;background:#fff;\"><tbody><tr><th valign=\"middle\"style=\"height: 25px; line-height: 25px; padding: 15px 35px; border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #42a3d3; background-color: #49bcff; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;\"><font face=\"微软雅黑\" size=\"5\" style=\"color: rgb(255, 255, 255); \">您的博客新动态 - " + blogTitle + "</font></th></tr><tr><td><div style=\"padding:25px 35px 40px; background-color:#fff;\"><h2 style=\"margin: 5px 0px; \"><font color=\"#333333\" style=\"line-height: 20px; \"><font style=\"line-height: 22px; \" size=\"4\">您好，我是你的博客助理，菠萝 Sir。</font></font></h2><br><p>您的 " + blogTitle + " 博客被用户 " + user + " 评论了！<br>内容：" + comment + "<br>请 <b><a href=\"" + blogSite + "\">轻点这里</a></b> 跳转至评论。<br><p align=\"right\">" + blogTitle + "</p><p align=\"right\">" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "</p><div style=\"width:700px;margin:0 auto;\"><div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\"><p>此为系统邮件，请勿回复<br></p><p>©" + new SimpleDateFormat("yyyy").format(new Date()) + " <a href='https://github.com/AdlerED/bolo-solo'>由菠萝博客强力驱动</a></p></div></div></div></td></tr></tbody></table></div>";

        MailProcessor.localSendMailMethod(
                "你的菠萝博客有新动态",
                blogTitle,
                replyRemindMailBoxAddress,
                html
        );
    }
}
