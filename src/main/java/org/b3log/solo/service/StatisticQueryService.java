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

import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.FilterOperator;
import org.b3log.latke.repository.PropertyFilter;
import org.b3log.latke.repository.Query;
import org.b3log.latke.service.annotation.Service;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Option;
import org.b3log.solo.repository.ArticleRepository;
import org.b3log.solo.repository.CommentRepository;
import org.json.JSONObject;

/**
 * Statistic query service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 0.5.0
 */
@Service
public class StatisticQueryService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(StatisticQueryService.class);

    /**
     * Option query service.
     */
    @Inject
    private OptionQueryService optionQueryService;

    /**
     * Article repository.
     */
    @Inject
    private ArticleRepository articleRepository;

    /**
     * Comment repository.
     */
    @Inject
    private CommentRepository commentRepository;

    /**
     * Gets the online visitor count.
     *
     * @return online visitor count
     */
    public static int getOnlineVisitorCount() {
        return StatisticMgmtService.ONLINE_VISITORS.size();
    }

    /**
     * Gets the statistic.
     *
     * @return statistic, returns {@code null} if not found
     */
    public JSONObject getStatistic() {
        try {
            final JSONObject ret = optionQueryService.getOptions(Option.CATEGORY_C_STATISTIC);
            final long publishedArticleCount = articleRepository.count(new Query().setFilter(new PropertyFilter(Article.ARTICLE_STATUS, FilterOperator.EQUAL, Article.ARTICLE_STATUS_C_PUBLISHED)));
            ret.put(Option.ID_T_STATISTIC_PUBLISHED_ARTICLE_COUNT, publishedArticleCount);
            final long commentCount = commentRepository.count(new Query());
            ret.put(Option.ID_T_STATISTIC_PUBLISHED_BLOG_COMMENT_COUNT, commentCount);

            return ret;
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Gets statistic failed", e);

            return null;
        }
    }
}
