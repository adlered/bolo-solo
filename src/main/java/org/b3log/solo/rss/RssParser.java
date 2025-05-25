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
import java.util.Objects;

import org.b3log.latke.Keys;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Option;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

public class RssParser {
    private String rssUrl;
    private String userIcon;
    private String userName;

    public RssParser(String rssUrl, String userIcon, String userName) {
        this.rssUrl = rssUrl;
        this.userIcon = userIcon;
        this.userName = userName;
    }

    private RssParser() {
    }

    public List<JSONObject> parse2Article() {
        SyndFeedInput input = new SyndFeedInput();
        final List<JSONObject> articles = new ArrayList<>();
        try (XmlReader reader = new XmlReader(new URL(this.rssUrl).openStream())) {
            SyndFeed feed = input.build(reader);
            String feedDesc = feed.getDescription();
            if (feedDesc == null || feedDesc.isEmpty()) {
                feedDesc = feed.getTitle();
            }
            for (SyndEntry entry : feed.getEntries()) {
                final JSONObject article = new JSONObject();
                article.put(Option.ID_C_BLOG_TITLE, feed.getTitle());
                article.put(Option.ID_C_BLOG_SUBTITLE, feedDesc);
                article.put(Article.ARTICLE_AUTHOR_ID, entry.getAuthor());
                article.put(Keys.OBJECT_ID, entry.getAuthor());
                article.put(Common.AUTHOR_NAME, entry.getAuthor());
                article.put(Article.ARTICLE_TITLE, entry.getTitle());
                article.put(Article.ARTICLE_PUT_TOP, false);
                article.put(Option.ID_C_COMMENTABLE, false);
                article.put(Common.ARTICLE_SIGN, new JSONObject().put("signHTML", ""));
                article.put(Article.ARTICLE_TAGS_REF, entry.getCategories().stream()
                        .map(category -> category.getName())
                        .reduce((a, b) -> a + "," + b).orElse(""));
                article.put(Common.HAS_UPDATED, false);
                if (Objects.nonNull(feed.getImage())) {
                    article.put(Common.AUTHOR_THUMBNAIL_URL, feed.getImage().getUrl());
                } else {
                    article.put(Common.AUTHOR_THUMBNAIL_URL, this.userIcon);
                }
                article.put(Article.ARTICLE_PERMALINK,
                        String.format("/follow/%s/article/%s", this.userName, entry.getTitle()));
                article.put("isRss", false);
                if (null == entry.getContents() || entry.getContents().isEmpty()) {
                    article.put(Article.ARTICLE_CONTENT, entry.getDescription().getValue());
                    article.put(Article.ARTICLE_ABSTRACT,
                            Article.getAbstractText(entry.getDescription().getValue()));
                    article.put(Article.ARTICLE_ABSTRACT_TEXT, article.getString(Article.ARTICLE_ABSTRACT));
                    if (!isRichContent(entry.getDescription().getValue())) {
                        article.put("isRss", true);
                        article.put(Article.ARTICLE_PERMALINK, entry.getLink());
                    }
                } else {
                    // Use the first content if available
                    article.put(Article.ARTICLE_CONTENT, entry.getContents().get(0).getValue());
                    if (entry.getDescription() == null || entry.getDescription().getValue() == null) {
                        article.put(Article.ARTICLE_ABSTRACT,
                                Article.getAbstractText(article.getString(Article.ARTICLE_CONTENT)));
                        article.put(Article.ARTICLE_ABSTRACT_TEXT, article.getString(Article.ARTICLE_ABSTRACT));
                    } else {
                        article.put(Article.ARTICLE_ABSTRACT, Article.getAbstractText(article));
                        article.put(Article.ARTICLE_ABSTRACT_TEXT, article.getString(Article.ARTICLE_ABSTRACT));
                    }
                }
                Date time;
                if (null == entry.getPublishedDate()) {
                    time = entry.getUpdatedDate();
                } else {
                    time = entry.getPublishedDate();
                }
                // 设置文章封面
                entry.getEnclosures().stream()
                        .filter(enclosure -> enclosure.getType().startsWith("image/"))
                        .findFirst()
                        .ifPresentOrElse(enclosure -> article.put(Article.ARTICLE_IMG1_URL, enclosure.getUrl()), () -> {
                            // 如果没有设置封面，则使用内容中的第一张图片,还没有则使用用户头像
                            final String contentFirstImageUrl = Article.getArticleImg1URLWithoutSetSize(article);
                            final String articleCoverUrl = contentFirstImageUrl == null ? this.userIcon
                                    : contentFirstImageUrl;

                            article.put(Article.ARTICLE_IMG1_URL, articleCoverUrl);
                        });
                article.put(Article.ARTICLE_CREATED, time.getTime());
                article.put(Article.ARTICLE_UPDATED, article.getLong(Article.ARTICLE_CREATED));
                article.put(Article.ARTICLE_VIEW_PWD, "");
                article.put(Article.ARTICLE_STATUS, Article.ARTICLE_STATUS_C_PUBLISHED);
                article.put(Common.POST_TO_COMMUNITY, false);
                article.put(Article.ARTICLE_COMMENTABLE, false);
                article.put(Article.ARTICLE_VIEW_COUNT, 0);
                article.put(Article.ARTICLE_COMMENT_COUNT, 0);
                article.put(Article.ARTICLE_T_CREATE_DATE, new Date(article.optLong(Article.ARTICLE_CREATED)));
                article.put(Article.ARTICLE_T_UPDATE_DATE, new Date(article.optLong(Article.ARTICLE_UPDATED)));

                articles.add(article);
            }
        } catch (Exception e) {
            System.out.println("Error parsing RSS feed: " + e.getMessage());
        }
        return articles;
    }

    /**
     * 判断是否是“富内容”，即内容是否像是正文而不是摘要
     */
    public static boolean isRichContent(String html) {
        if (html == null || html.trim().isEmpty()) {
            return false;
        }

        Document doc = Jsoup.parse(html);
        String text = doc.text();
        int textLength = text.length();

        boolean hasLongText = textLength > 200;
        boolean hasParagraphs = doc.select("p").size() >= 3;
        boolean hasImages = doc.select("img").size() > 1;
        boolean hasHeaders = doc.select("h1,h2,h3").size() > 0;

        return hasLongText && (hasParagraphs || hasImages || hasHeaders);
    }
}
