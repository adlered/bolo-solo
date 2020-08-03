/*
 * Bolo - A stable and beautiful blogging system based in Solo.
 * Copyright (c) 2020, https://github.com/adlered
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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.time.DateFormatUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.servlet.HttpMethod;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.util.Ids;
import org.b3log.solo.SoloServletListener;
import org.b3log.solo.bolo.tool.DeleteFolder;
import org.b3log.solo.model.Article;
import org.b3log.solo.model.Option;
import org.b3log.solo.repository.UserRepository;
import org.b3log.solo.service.ExportService;
import org.b3log.solo.service.ImportService;
import org.b3log.solo.service.InitService;
import org.b3log.solo.service.OptionQueryService;
import org.b3log.solo.util.Solos;
import org.json.JSONArray;
import org.json.JSONObject;
import org.zeroturnaround.zip.ZipUtil;
import pers.adlered.blog_platform_export_tool.module.TranslateResult;
import pers.adlered.blog_platform_export_tool.util.XML;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

import static pers.adlered.blog_platform_export_tool.Launcher.run;

/**
 * <h3>bolo-solo</h3>
 * <p>备份服务</p>
 *
 * @author : https://github.com/adlered
 * @date : 2020-01-05 13:39
 **/
@RequestProcessor
public class BackupService {
    /**
     * Option query service.
     */
    @Inject
    private OptionQueryService optionQueryService;

    /**
     * Export service.
     */
    @Inject
    private ExportService exportService;

    /**
     * User repository.
     */
    @Inject
    private UserRepository userRepository;

    /**
     * Init service.
     */
    @Inject
    private InitService initService;

    /**
     * Import service.
     */
    @Inject
    private ImportService importService;

    @RequestProcessing(value = "/prop/backup/hacpai/do/upload", method = {HttpMethod.GET})
    public void uploadBackupToHacpai(final RequestContext context) {
        if (!Solos.isAdminLoggedIn(context)) {
            context.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        try {
            exportService.exportHacPai(true);

            context.renderJSON().renderCode(200);
            context.renderJSON().renderMsg("Exported backup to HacPai manual successfully.");

            return ;
        } catch (final Exception e) {
            context.sendError(500);

            return ;
        }
    }

    @RequestProcessing(value = "/import/cnblogs", method = {HttpMethod.POST})
    public void importFromCnblogs(final RequestContext context) {
        if (!Solos.isAdminLoggedIn(context)) {
            context.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        DeleteFolder.delFolder(new File("temp/file/").getAbsolutePath());

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(new File("temp/file/"));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        try {
            List<FileItem> itemList = upload.parseRequest(context.getRequest());
            for (FileItem item : itemList) {
                String name = item.getName();
                File file = new File("temp/file/" + name);
                item.write(file);
                item.delete();
            }
            List<TranslateResult> list = run("CNBlogs");
            for (TranslateResult i : list) {
                try {
                    final Transaction transaction = userRepository.beginTransaction();
                    final JSONObject article = new JSONObject();
                    final String ret = Ids.genTimeMillisId();
                    article.put(Keys.OBJECT_ID, ret);
                    article.put(Article.ARTICLE_TITLE, i.getTitle());
                    String content = i.getArticleContent();
                    article.put(Article.ARTICLE_ABSTRACT_TEXT, "");
                    article.put(Article.ARTICLE_ABSTRACT, "");
                    article.put(Article.ARTICLE_CONTENT, content);
                    article.put(Article.ARTICLE_TAGS_REF, "待分类");
                    final String permalink = "/articles/" + DateFormatUtils.format(i.getDate(), "yyyy/MM/dd") + "/" + article.optString(Keys.OBJECT_ID) + ".html";
                    article.put(Article.ARTICLE_PERMALINK, permalink);
                    article.put(Article.ARTICLE_STATUS, Article.ARTICLE_STATUS_C_PUBLISHED);
                    article.put(Article.ARTICLE_SIGN_ID, "1");
                    article.put(Article.ARTICLE_COMMENT_COUNT, 0);
                    article.put(Article.ARTICLE_VIEW_COUNT, 0);
                    final JSONObject admin = userRepository.getAdmin();
                    final long now = System.currentTimeMillis();
                    article.put(Article.ARTICLE_CREATED, i.getDate().getTime());
                    article.put(Article.ARTICLE_UPDATED, now);
                    article.put(Article.ARTICLE_PUT_TOP, false);
                    article.put(Article.ARTICLE_RANDOM_DOUBLE, Math.random());
                    article.put(Article.ARTICLE_AUTHOR_ID, admin.optString(Keys.OBJECT_ID));
                    article.put(Article.ARTICLE_COMMENTABLE, true);
                    article.put(Article.ARTICLE_VIEW_PWD, "");
                    final String articleImg1URL = Article.getArticleImg1URL(article);
                    article.put(Article.ARTICLE_IMG1_URL, articleImg1URL);
                    long contentLength = content.getBytes("UTF-8").length;
                    System.out.println("========== 正在导入 ==========");
                    System.out.println("文章标题：" + i.getTitle());
                    System.out.println("文章长度：" + contentLength);
                    initService.importArticle(article);
                    System.out.println("========== 导入完成 ==========");

                    transaction.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        } catch (Exception e) {
            DeleteFolder.delFolder(new File("temp/file/").getAbsolutePath());
            context.renderJSON().renderMsg("从博客园恢复备份失败，请重试！");

            return;
        }

        System.out.println("博客园文章已全部导入。");
        DeleteFolder.delFolder(new File("temp/file/").getAbsolutePath());
        context.renderJSON().renderMsg("从博客园恢复备份成功。");

        return;
    }

    @RequestProcessing(value = "/import/markdown", method = {HttpMethod.POST})
    public void importFromMarkdown (final RequestContext context) {
        if (!Solos.isAdminLoggedIn(context)) {
            context.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        final ServletContext servletContext = SoloServletListener.getServletContext();
        final String markdownsPath = servletContext.getRealPath("markdowns");

        DeleteFolder.delAllFile(markdownsPath);

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(new File(markdownsPath));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        String result = "";
        try {
            List<FileItem> itemList = upload.parseRequest(context.getRequest());
            for (FileItem item : itemList) {
                String name = item.getName();
                File file = new File(markdownsPath + name);
                item.write(file);
                item.delete();
                ZipUtil.unpack(new File(markdownsPath + name), new File(markdownsPath));
                file.delete();
            }
            result = importService.importMarkdownsSync();
        } catch (Exception e) {
            context.renderJSON().renderMsg("从 Markdown zip 恢复备份失败，请重试！" + result);

            return;
        }

        DeleteFolder.delAllFile(markdownsPath);
        context.renderJSON().renderMsg("从 Markdown zip 恢复备份成功。" + result);

        return;
    }
}
