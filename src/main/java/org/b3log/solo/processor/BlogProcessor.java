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
package org.b3log.solo.processor;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Pagination;
import org.b3log.latke.model.User;
import org.b3log.latke.servlet.HttpMethod;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.JsonRenderer;
import org.b3log.solo.SoloServletListener;
import org.b3log.solo.bolo.SslUtils;
import org.b3log.solo.bolo.prop.FaviconCache;
import org.b3log.solo.bolo.tool.EditIMG;
import org.b3log.solo.bolo.tool.MediaFileUtil;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Option;
import org.b3log.solo.service.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Blog processor.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 0.4.6
 */
@RequestProcessor
public class BlogProcessor {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(BlogProcessor.class);

    /**
     * Article query service.
     */
    @Inject
    private ArticleQueryService articleQueryService;

    /**
     * Tag query service.
     */
    @Inject
    private TagQueryService tagQueryService;

    /**
     * Statistic query service.
     */
    @Inject
    private StatisticQueryService statisticQueryService;

    /**
     * User query service.
     */
    @Inject
    private UserQueryService userQueryService;

    /**
     * Option query service.
     */
    @Inject
    private OptionQueryService optionQueryService;

    /**
     * PWA manifest JSON template.
     */
    private static String PWA_MANIFESTO_JSON;

    /**
     * The cache of favicon.
     */
    HashMap<String, FaviconCache> faviconCache = new HashMap<>();

    static {
        try (final InputStream tplStream = BlogProcessor.class.getResourceAsStream("/manifest.json.tpl")) {
            PWA_MANIFESTO_JSON = IOUtils.toString(tplStream, "UTF-8");
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Loads PWA manifest.json template failed", e);
        }
    }

    /**
     * Gets PWA manifest.json.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/manifest.json", method = HttpMethod.GET)
    public void getPWAManifestJSON(final RequestContext context) {
        final JsonRenderer renderer = new JsonRenderer();
        renderer.setPretty(true);
        context.setRenderer(renderer);
        final JSONObject preference = optionQueryService.getPreference();
        if (null == preference) {
            return;
        }
        final String name = preference.optString(Option.ID_C_BLOG_TITLE);
        PWA_MANIFESTO_JSON = StringUtils.replace(PWA_MANIFESTO_JSON, "${name}", name);
        final String description = preference.optString(Option.ID_C_BLOG_SUBTITLE);
        PWA_MANIFESTO_JSON = StringUtils.replace(PWA_MANIFESTO_JSON, "${description}", description);
        final JSONObject jsonObject = new JSONObject(PWA_MANIFESTO_JSON);
        PWA_MANIFESTO_JSON = StringUtils.replace(PWA_MANIFESTO_JSON, "${shortName}", name);
        renderer.setJSONObject(jsonObject);
    }

    /**
     * Get an edited image by the website favicon.
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/favicon/{width}/{height}")
    public void getFavicon(final RequestContext context) {
        synchronized (this) {
            String resolution = context.pathVar("width") + "/" + context.pathVar("height");
            try {
                if (faviconCache.containsKey(resolution)) {
                    final HttpServletResponse response = context.getResponse();
                    String contentType = faviconCache.get(resolution).getMediaFileType();
                    response.setContentType(contentType);
                    OutputStream outputStream = response.getOutputStream();
                    outputStream.write(faviconCache.get(resolution).getData());
                    outputStream.close();
                } else {
                    final JSONObject preference = optionQueryService.getPreference();
                    if (null == preference) {
                        return;
                    }
                    String favicon = preference.optString(Option.ID_C_FAVICON_URL);
                    // 正则表达式
                    String regUrl = "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\\\/])+$";
                    Pattern p = Pattern.compile(regUrl);
                    Matcher m = p.matcher(favicon);
                    if (!m.matches()) {
                        // 非http或https开头，默认匹配本地
                        favicon = Latkes.getServePath() + "/" + favicon;
                    }

                    File faviconFile = null;

                    // 下载 favicon
                    try {
                        // 分析文件类型
                        MediaFileUtil.MediaFileType mediaFileType = MediaFileUtil.getFileType(favicon);
                        if (mediaFileType == null) {
                            // 后缀名无法分析文件类型时，默认返回png
                            mediaFileType = new MediaFileUtil.MediaFileType(
                                    33,
                                    "image/png"
                            );
                        }

                        // 分析临时目录
                        final ServletContext servletContext = SoloServletListener.getServletContext();
                        final String assets = "/";
                        String path = servletContext.getResource(assets).getPath();
                        path = URLDecoder.decode(path);
                        String sourceFilePath = path + "tmp_favicon";

                        // 创建临时文件，并定义输出流
                        faviconFile = new File(sourceFilePath);
                        FileOutputStream fileOutputStream = new FileOutputStream(faviconFile);

                        // 下载favicon
                        URL url = new URL(favicon);
                        SslUtils.ignoreSsl();
                        URLConnection connection = url.openConnection();
                        InputStream inputStream = connection.getInputStream();
                        int length = 0;
                        byte[] bytes = new byte[1024];
                        while ((length = inputStream.read(bytes)) != -1) {
                            // 将互联网图片通过输出流写入到临时文件tmp_favicon
                            fileOutputStream.write(bytes, 0, length);
                        }
                        fileOutputStream.close();
                        inputStream.close();

                        // 获取图片宽高参数
                        int width = Integer.parseInt(context.pathVar("width"));
                        int height = Integer.parseInt(context.pathVar("height"));

                        // 裁剪图片
                        EditIMG.createThumbnail(faviconFile, faviconFile, width, height, MediaFileUtil.getSuffixByFileType(mediaFileType));

                        // 将裁剪后的图片输出至浏览器
                        final HttpServletResponse response = context.getResponse();
                        FileInputStream fileInputStream = new FileInputStream(faviconFile);
                        int fileSize = fileInputStream.available();
                        byte[] data = new byte[fileSize];
                        fileInputStream.read(data);
                        fileInputStream.close();
                        String contentType = mediaFileType.mimeType;
                        response.setContentType(contentType);
                        OutputStream outputStream = response.getOutputStream();
                        outputStream.write(data);
                        // 写缓存
                        faviconCache.put(resolution, new FaviconCache(contentType, data));
                        outputStream.close();
                    } catch (Exception e) {
                        LOGGER.log(Level.ERROR, "Unable to resolve favicon");
                        context.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                        return;
                    } finally {
                        if (faviconFile != null) {
                            faviconFile.delete();
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Unable to resolve favicon");
                context.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                return;
            }
        }
    }

    /**
     * Gets blog information.
     * <ul>
     * <li>Time of the recent updated article</li>
     * <li>Article count</li>
     * <li>Comment count</li>
     * <li>Tag count</li>
     * <li>Serve path</li>
     * <li>Static serve path</li>
     * <li>Solo version</li>
     * <li>Runtime mode</li>
     * <li>Runtime database</li>
     * <li>Locale</li>
     * <li>Admin username</li>
     * </ul>
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/blog/info", method = HttpMethod.GET)
    public void getBlogInfo(final RequestContext context) {
        final JsonRenderer renderer = new JsonRenderer();
        context.setRenderer(renderer);
        final JSONObject jsonObject = new JSONObject();
        renderer.setJSONObject(jsonObject);

        jsonObject.put("recentArticleTime", articleQueryService.getRecentArticleTime());
        final JSONObject statistic = statisticQueryService.getStatistic();
        jsonObject.put("articleCount", statistic.getLong(Option.ID_T_STATISTIC_PUBLISHED_ARTICLE_COUNT));
        jsonObject.put("commentCount", statistic.getLong(Option.ID_T_STATISTIC_PUBLISHED_BLOG_COMMENT_COUNT));
        jsonObject.put("tagCount", tagQueryService.getTagCount());
        jsonObject.put("servePath", Latkes.getServePath());
        jsonObject.put("staticServePath", Latkes.getStaticServePath());
        jsonObject.put("version", SoloServletListener.VERSION);
        jsonObject.put("runtimeMode", Latkes.getRuntimeMode());
        jsonObject.put("runtimeDatabase", Latkes.getRuntimeDatabase());
        jsonObject.put("locale", Latkes.getLocale());
        final String userName = userQueryService.getAdmin().optString(User.USER_NAME);
        jsonObject.put("userName", userName);
    }

    /**
     * Gets tags of all articles.
     * <p>
     * <pre>
     * {
     *     "data": [
     *         ["tag1", "tag2", ....], // tags of one article
     *         ["tagX", "tagY", ....], // tags of another article
     *         ....
     *     ]
     * }
     * </pre>
     * </p>
     *
     * @param context the specified context
     */
    @RequestProcessing(value = "/blog/articles-tags", method = HttpMethod.GET)
    public void getArticlesTags(final RequestContext context) {
        final JSONObject requestJSONObject = new JSONObject();
        requestJSONObject.put(Pagination.PAGINATION_CURRENT_PAGE_NUM, 1);
        requestJSONObject.put(Pagination.PAGINATION_PAGE_SIZE, Integer.MAX_VALUE);
        requestJSONObject.put(Pagination.PAGINATION_WINDOW_SIZE, Integer.MAX_VALUE);
        requestJSONObject.put(Article.ARTICLE_STATUS, Article.ARTICLE_STATUS_C_PUBLISHED);

        final JSONArray excludes = new JSONArray();

        excludes.put(Article.ARTICLE_CONTENT);
        excludes.put(Article.ARTICLE_UPDATED);
        excludes.put(Article.ARTICLE_CREATED);
        excludes.put(Article.ARTICLE_AUTHOR_ID);
        excludes.put(Article.ARTICLE_RANDOM_DOUBLE);

        requestJSONObject.put(Keys.EXCLUDES, excludes);

        final JSONObject result = articleQueryService.getArticles(requestJSONObject);
        final JSONArray articles = result.optJSONArray(Article.ARTICLES);

        final JsonRenderer renderer = new JsonRenderer();
        context.setRenderer(renderer);
        final JSONObject ret = new JSONObject();
        renderer.setJSONObject(ret);

        final JSONArray data = new JSONArray();
        ret.put("data", data);

        for (int i = 0; i < articles.length(); i++) {
            final JSONObject article = articles.optJSONObject(i);
            final String tagString = article.optString(Article.ARTICLE_TAGS_REF);

            final JSONArray tagArray = new JSONArray();
            data.put(tagArray);

            final String[] tags = tagString.split(",");

            for (final String tag : tags) {
                final String trim = tag.trim();
                if (StringUtils.isNotBlank(trim)) {
                    tagArray.put(tag);
                }
            }
        }
    }
}
