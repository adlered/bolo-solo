/*
 * Bolo - A stable and beautiful blogging system based in Solo.
 * Copyright (c) 2020-present, https://github.com/bolo-blog
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
package org.b3log.solo.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class defines all comment model relevant keys.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 0.3.1
 */
public final class Comment {

    /**
     * Comment.
     */
    public static final String COMMENT = "comment";

    /**
     * Comments.
     */
    public static final String COMMENTS = "comments";

    /**
     * Key of comment.
     */
    public static final String COMMENT_CONTENT = "commentContent";

    /**
     * Key of comment name.
     */
    public static final String COMMENT_NAME = "commentName";

    /**
     * Key of comment URL.
     */
    public static final String COMMENT_URL = "commentURL";

    /**
     * Key of comment sharp URL.
     */
    public static final String COMMENT_SHARP_URL = "commentSharpURL";

    /**
     * Key of comment created at.
     */
    public static final String COMMENT_CREATED = "commentCreated";

    /**
     * Key of comment date.
     */
    public static final String COMMENT_T_DATE = "commentDate";

    /**
     * Key of comment time.
     */
    public static final String COMMENT_TIME = "commentTime";

    /**
     * Key of comment thumbnail URL.
     */
    public static final String COMMENT_THUMBNAIL_URL = "commentThumbnailURL";

    /**
     * Key of original comment id.
     */
    public static final String COMMENT_ORIGINAL_COMMENT_ID = "commentOriginalCommentId";

    /**
     * Key of original comment user name.
     */
    public static final String COMMENT_ORIGINAL_COMMENT_NAME = "commentOriginalCommentName";

    /**
     * Key of comment on id.
     */
    public static final String COMMENT_ON_ID = "commentOnId";

    /**
     * Private constructor.
     */
    private Comment() {
    }

    /**
     * Gets comment sharp URL with the specified page and comment id.
     *
     * @param page      the specified page
     * @param commentId the specified comment id
     * @return comment sharp URL
     * @throws JSONException json exception
     */
    public static String getCommentSharpURLForPage(final JSONObject page, final String commentId) throws JSONException {
        return page.getString(Page.PAGE_PERMALINK) + "#" + commentId;
    }

    /**
     * Gets comment sharp URL with the specified article and comment id.
     *
     * @param article   the specified article
     * @param commentId the specified comment id
     * @return comment sharp URL
     * @throws JSONException json exception
     */
    public static String getCommentSharpURLForArticle(final JSONObject article, final String commentId) throws JSONException {
        return article.getString(Article.ARTICLE_PERMALINK) + "#" + commentId;
    }
}
