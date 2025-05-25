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
package org.b3log.solo.service;

import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Pagination;
import org.b3log.latke.model.Role;
import org.b3log.latke.model.User;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.SortDirection;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.util.Paginator;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Comment;
import org.b3log.solo.model.Common;
import org.b3log.solo.repository.ArticleRepository;
import org.b3log.solo.repository.CommentRepository;
import org.b3log.solo.repository.PageRepository;
import org.b3log.solo.util.Emotions;
import org.b3log.solo.util.Markdowns;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Comment query service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 0.3.5
 */
@Service
public class CommentQueryService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(CommentQueryService.class);

    /**
     * User service.
     */
    @Inject
    private UserQueryService userQueryService;

    /**
     * Comment repository.
     */
    @Inject
    private CommentRepository commentRepository;

    /**
     * Article repository.
     */
    @Inject
    private ArticleRepository articleRepository;

    /**
     * Page repository.
     */
    @Inject
    private PageRepository pageRepository;

    /**
     * Can the specified user access a comment specified by the given comment id?
     *
     * @param commentId the given comment id
     * @param user      the specified user
     * @return {@code true} if the current user can access the comment, {@code false} otherwise
     * @throws Exception exception
     */
    public boolean canAccessComment(final String commentId, final JSONObject user) throws Exception {
        if (StringUtils.isBlank(commentId)) {
            return false;
        }

        if (null == user) {
            return false;
        }

        if (Role.ADMIN_ROLE.equals(user.optString(User.USER_ROLE))) {
            return true;
        }

        final JSONObject comment = commentRepository.get(commentId);
        if (null == comment) {
            return false;
        }

        final String onId = comment.optString(Comment.COMMENT_ON_ID);
        final JSONObject article = articleRepository.get(onId);
        if (null == article) {
            return false;
        }

        final String currentUserId = user.getString(Keys.OBJECT_ID);

        return article.getString(Article.ARTICLE_AUTHOR_ID).equals(currentUserId);
    }

    /**
     * Gets comments with the specified request json object, request and response.
     *
     * @param requestJSONObject the specified request json object, for example,
     *                          "paginationCurrentPageNum": 1,
     *                          "paginationPageSize": 20,
     *                          "paginationWindowSize": 10
     * @return for example,
     * <pre>
     * {
     *     "comments": [{
     *         "oId": "",
     *         "commentTitle": "",
     *         "commentName": "",
     *         "thumbnailUrl": "",
     *         "commentURL": "",
     *         "commentContent": "",
     *         "commentTime": long,
     *         "commentSharpURL": ""
     *      }, ....]
     *     "sc": "GET_COMMENTS_SUCC"
     * }
     * </pre>
     * @throws ServiceException service exception
     * @see Pagination
     */
    public JSONObject getComments(final JSONObject requestJSONObject) throws ServiceException {
        try {
            final JSONObject ret = new JSONObject();

            final int currentPageNum = requestJSONObject.getInt(Pagination.PAGINATION_CURRENT_PAGE_NUM);
            final int pageSize = requestJSONObject.getInt(Pagination.PAGINATION_PAGE_SIZE);
            final int windowSize = requestJSONObject.getInt(Pagination.PAGINATION_WINDOW_SIZE);

            final Query query = new Query().setPage(currentPageNum, pageSize).
                    addSort(Comment.COMMENT_CREATED, SortDirection.DESCENDING);
            final JSONObject result = commentRepository.get(query);
            final JSONArray comments = result.getJSONArray(Keys.RESULTS);

            // Sets comment title and content escaping
            for (int i = 0; i < comments.length(); i++) {
                final JSONObject comment = comments.getJSONObject(i);
                String title;

                final String onId = comment.getString(Comment.COMMENT_ON_ID);
                final JSONObject article = articleRepository.get(onId);
                if (null == article) {
                    // 某种情况下导致的数据不一致：文章已经被删除了，但是评论还在
                    // 为了保持数据一致性，需要删除该条评论 https://ld246.com/article/1556060195022
                    final Transaction transaction = commentRepository.beginTransaction();
                    final String commentId = comment.optString(Keys.OBJECT_ID);
                    commentRepository.remove(commentId);
                    transaction.commit();

                    continue;
                }

                title = article.getString(Article.ARTICLE_TITLE);
                comment.put(Common.TYPE, Common.ARTICLE_COMMENT_TYPE);
                comment.put(Common.COMMENT_TITLE, title);

                String commentContent = comment.optString(Comment.COMMENT_CONTENT);
                commentContent = Markdowns.toHTML(commentContent);
                commentContent = Markdowns.clean(commentContent);
                comment.put(Comment.COMMENT_CONTENT, commentContent);

                String commentName = comment.optString(Comment.COMMENT_NAME);
                commentName = Jsoup.clean(commentName, Whitelist.none());
                comment.put(Comment.COMMENT_NAME, commentName);

                comment.put(Comment.COMMENT_TIME, comment.optLong(Comment.COMMENT_CREATED));
                comment.remove(Comment.COMMENT_CREATED);
            }

            final int pageCount = result.getJSONObject(Pagination.PAGINATION).getInt(Pagination.PAGINATION_PAGE_COUNT);
            final JSONObject pagination = new JSONObject();
            final List<Integer> pageNums = Paginator.paginate(currentPageNum, pageSize, pageCount, windowSize);

            pagination.put(Pagination.PAGINATION_PAGE_COUNT, pageCount);
            pagination.put(Pagination.PAGINATION_PAGE_NUMS, pageNums);

            ret.put(Comment.COMMENTS, comments);
            ret.put(Pagination.PAGINATION, pagination);

            return ret;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Gets comments failed", e);

            throw new ServiceException(e);
        }
    }

    /**
     * Gets comments of an article or page specified by the on id.
     *
     * @param onId the specified on id
     * @return a list of comments, returns an empty list if not found
     * @throws ServiceException service exception
     */
    public List<JSONObject> getComments(final String onId) throws ServiceException {
        try {
            final List<JSONObject> ret = new ArrayList<>();
            final List<JSONObject> comments = commentRepository.getComments(onId, 1, Integer.MAX_VALUE);
            for (final JSONObject comment : comments) {
                comment.put(Comment.COMMENT_TIME, comment.optLong(Comment.COMMENT_CREATED));
                comment.put(Comment.COMMENT_T_DATE, new Date(comment.optLong(Comment.COMMENT_CREATED)));
                comment.put("commentDate2", new Date(comment.optLong(Comment.COMMENT_CREATED))); // 1.9.0 向后兼容
                comment.put(Comment.COMMENT_NAME, comment.getString(Comment.COMMENT_NAME));
                String url = comment.getString(Comment.COMMENT_URL);
                if (StringUtils.contains(url, "<")) { // legacy issue https://github.com/b3log/solo/issues/12091
                    url = "";
                }
                comment.put(Comment.COMMENT_URL, url);
                comment.put(Common.IS_REPLY, false); // Assumes this comment is not a reply

                if (StringUtils.isNotBlank(comment.optString(Comment.COMMENT_ORIGINAL_COMMENT_ID))) {
                    // This comment is a reply
                    comment.put(Common.IS_REPLY, true);
                }

                String commentContent = comment.optString(Comment.COMMENT_CONTENT);
                commentContent = Markdowns.toHTML(commentContent);
                commentContent = Markdowns.clean(commentContent);
                comment.put(Comment.COMMENT_CONTENT, commentContent);

                String commentName = comment.optString(Comment.COMMENT_NAME);
                commentName = Jsoup.clean(commentName, Whitelist.none());
                comment.put(Comment.COMMENT_NAME, commentName);

                ret.add(comment);
            }

            return ret;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Gets comments failed", e);
            throw new ServiceException(e);
        }
    }
}
