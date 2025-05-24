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
package org.b3log.solo.rss;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.b3log.latke.Keys;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Option;
import org.json.JSONObject;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

public class RssParser {
    private String rssUrl;

    public RssParser(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    private RssParser() {
    }

    public List<JSONObject> parse2Article() {
        SyndFeedInput input = new SyndFeedInput();
        final List<JSONObject> articles = new ArrayList<>();
        try (XmlReader reader = new XmlReader(new URL(this.rssUrl).openStream())) {
            SyndFeed feed = input.build(reader);
            for (SyndEntry entry : feed.getEntries()) {
                final JSONObject article = new JSONObject();
                article.put(Option.ID_C_BLOG_TITLE, feed.getTitle());
                article.put(Option.ID_C_BLOG_SUBTITLE, feed.getDescription());
                article.put(Article.ARTICLE_AUTHOR_ID, entry.getAuthor());
                article.put(Keys.OBJECT_ID, entry.getAuthor());
                article.put(Common.AUTHOR_NAME, entry.getAuthor());
                article.put(Article.ARTICLE_TITLE, entry.getTitle());
                article.put(Article.ARTICLE_PUT_TOP, false);
                article.put(Option.ID_C_COMMENTABLE, false);
                article.put(Common.ARTICLE_SIGN, new JSONObject().put("signHTML", ""));
                article.put(Article.ARTICLE_ABSTRACT, entry.getDescription().getValue());
                article.put(Article.ARTICLE_ABSTRACT_TEXT, entry.getDescription().getValue());
                article.put(Article.ARTICLE_TAGS_REF, entry.getCategories().stream()
                        .map(category -> category.getName())
                        .reduce((a, b) -> a + "," + b).orElse(""));
                article.put(Common.HAS_UPDATED, false);
                article.put(Common.AUTHOR_THUMBNAIL_URL, feed.getImage().getUrl());
                article.put(Article.ARTICLE_PERMALINK, String.format("/follow/diygod/article/%s", entry.getTitle()));
                article.put(Article.ARTICLE_CONTENT, entry.getContents().get(0).getValue());
                article.put(Article.ARTICLE_CREATED, entry.getPublishedDate().getTime());
                article.put(Article.ARTICLE_UPDATED, article.getLong(Article.ARTICLE_CREATED));
                article.put(Article.ARTICLE_VIEW_PWD, "");
                article.put(Article.ARTICLE_STATUS, Article.ARTICLE_STATUS_C_PUBLISHED);
                article.put(Common.POST_TO_COMMUNITY, false);
                article.put(Article.ARTICLE_COMMENTABLE, false);
                article.put(Article.ARTICLE_VIEW_COUNT, 0);
                article.put(Article.ARTICLE_COMMENT_COUNT, 0);
                article.put(Article.ARTICLE_T_CREATE_DATE, new Date(article.optLong(Article.ARTICLE_CREATED)));
                article.put(Article.ARTICLE_T_UPDATE_DATE, new Date(article.optLong(Article.ARTICLE_UPDATED)));
                article.put(Article.ARTICLE_IMG1_URL, feed.getImage().getUrl());
                articles.add(article);
            }
        } catch (Exception e) {
            System.out.println("Error parsing RSS feed: " + e.getMessage());
        }
        return articles;
    }
}
