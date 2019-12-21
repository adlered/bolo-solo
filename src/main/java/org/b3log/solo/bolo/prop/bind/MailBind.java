package org.b3log.solo.bolo.prop.bind;

/**
 * <h3>bolo-solo</h3>
 * <p>Mail bind.</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-12-21 18:31
 **/
public class MailBind {
    private String commentId;
    private String commentUser;
    private String commentEmail;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser;
    }

    public String getCommentEmail() {
        return commentEmail;
    }

    public void setCommentEmail(String commentEmail) {
        this.commentEmail = commentEmail;
    }
}
