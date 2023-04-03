package org.b3log.solo.processor;

import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.servlet.DispatcherServlet;
import org.b3log.solo.processor.console.*;

public class RouteProcessor {
    public static void routeConsoleProcessors() {
        final BeanManager beanManager = BeanManager.getInstance();
        final AdminConsole adminConsole = beanManager.getReference(AdminConsole.class);
        DispatcherServlet.get("/admin-index.do", adminConsole::showAdminIndex);
        DispatcherServlet.get("/admin-preference.do", adminConsole::showAdminPreferenceFunction);
        DispatcherServlet.route().get(new String[]{"/admin-article.do",
                "/admin-article-list.do",
                "/admin-comment-list.do",
                "/admin-link-list.do",
                "/admin-page-list.do",
                "/admin-others.do",
                "/admin-draft-list.do",
                "/admin-user-list.do",
                "/admin-category-list.do",
                "/admin-theme-list.do",
                "/admin-plugin-list.do",
                "/admin-main.do",
                "/admin-about.do",
                "/admin-tool-box.do",
                "/admin-usite.do"}, adminConsole::showAdminFunctions);
        DispatcherServlet.get("/console/export/sql", adminConsole::exportSQL);
        DispatcherServlet.get("/console/export/json", adminConsole::exportJSON);
        DispatcherServlet.get("/console/export/hexo", adminConsole::exportHexo);

        final ArticleConsole articleConsole = beanManager.getReference(ArticleConsole.class);
        DispatcherServlet.get("/console/article/push2rhy", articleConsole::pushArticleToCommunity);
        DispatcherServlet.get("/console/thumbs", articleConsole::getArticleThumbs);
        DispatcherServlet.get("/console/article/{id}", articleConsole::getArticle);
        DispatcherServlet.get("/console/articles/status/{status}/{page}/{pageSize}/{windowSize}", articleConsole::getArticles);
        DispatcherServlet.delete("/console/article/{id}", articleConsole::removeArticle);
        DispatcherServlet.put("/console/article/unpublish/{id}", articleConsole::cancelPublishArticle);
        DispatcherServlet.put("/console/article/canceltop/{id}", articleConsole::cancelTopArticle);
        DispatcherServlet.put("/console/article/puttop/{id}", articleConsole::putTopArticle);
        DispatcherServlet.put("/console/article/", articleConsole::updateArticle);
        DispatcherServlet.post("/console/article/", articleConsole::addArticle);

        final CategoryConsole categoryConsole = beanManager.getReference(CategoryConsole.class);
        DispatcherServlet.put("/console/category/order/", categoryConsole::changeOrder);
        DispatcherServlet.get("/console/category/{id}", categoryConsole::getCategory);
        DispatcherServlet.delete("/console/category/{id}", categoryConsole::removeCategory);
        DispatcherServlet.put("/console/category/", categoryConsole::updateCategory);
        DispatcherServlet.post("/console/category/", categoryConsole::addCategory);
        DispatcherServlet.get("/console/categories/{page}/{pageSize}/{windowSize}", categoryConsole::getCategories);

        final CommentConsole commentConsole = beanManager.getReference(CommentConsole.class);
        DispatcherServlet.delete("/console/article/comment/{id}", commentConsole::removeArticleComment);
        DispatcherServlet.get("/console/comments/{page}/{pageSize}/{windowSize}", commentConsole::getComments);
        DispatcherServlet.get("/console/comments/article/{id}", commentConsole::getArticleComments);

        final LinkConsole linkConsole = beanManager.getReference(LinkConsole.class);
        DispatcherServlet.delete("/console/link/{id}", linkConsole::removeLink);
        DispatcherServlet.put("/console/link/", linkConsole::updateLink);
        DispatcherServlet.put("/console/link/order/", linkConsole::changeOrder);
        DispatcherServlet.post("/console/link/", linkConsole::addLink);
        DispatcherServlet.get("/console/links/{page}/{pageSize}/{windowSize}", linkConsole::getLinks);
        DispatcherServlet.get("/console/link/{id}", linkConsole::getLink);

        final PageConsole pageConsole = beanManager.getReference(PageConsole.class);
        DispatcherServlet.put("/console/page/", pageConsole::updatePage);
        DispatcherServlet.delete("/console/page/{id}", pageConsole::removePage);
        DispatcherServlet.post("/console/page/", pageConsole::addPage);
        DispatcherServlet.put("/console/page/order/", pageConsole::changeOrder);
        DispatcherServlet.get("/console/page/{id}", pageConsole::getPage);
        DispatcherServlet.get("/console/pages/{page}/{pageSize}/{windowSize}", pageConsole::getPages);

        final PluginConsole pluginConsole = beanManager.getReference(PluginConsole.class);
        DispatcherServlet.put("/console/plugin/status/", pluginConsole::setPluginStatus);
        DispatcherServlet.get("/console/plugins/{page}/{pageSize}/{windowSize}", pluginConsole::getPlugins);
        DispatcherServlet.post("/console/plugin/toSetting", pluginConsole::toSetting);
        DispatcherServlet.post("/console/plugin/updateSetting", pluginConsole::updateSetting);

        final PreferenceConsole preferenceConsole = beanManager.getReference(PreferenceConsole.class);
        DispatcherServlet.get("/console/signs/", preferenceConsole::getSigns);
        DispatcherServlet.get("/console/preference/", preferenceConsole::getPreference);
        DispatcherServlet.put("/console/preference/", preferenceConsole::updatePreference);

        final SkinConsole skinConsole = beanManager.getReference(SkinConsole.class);
        DispatcherServlet.get("/console/skin", skinConsole::getSkin);
        DispatcherServlet.put("/console/skin", skinConsole::updateSkin);

        final RepairConsole repairConsole = beanManager.getReference(RepairConsole.class);
        DispatcherServlet.get("/fix/restore-signs", repairConsole::restoreSigns);

        final TagConsole tagConsole = beanManager.getReference(TagConsole.class);
        DispatcherServlet.get("/console/tags", tagConsole::getTags);
        DispatcherServlet.get("/console/tag/unused", tagConsole::getUnusedTags);

        final OtherConsole otherConsole = beanManager.getReference(OtherConsole.class);
        DispatcherServlet.delete("/console/archive/unused", otherConsole::removeUnusedArchives);
        DispatcherServlet.delete("/console/tag/unused", otherConsole::removeUnusedTags);

        final UserConsole userConsole = beanManager.getReference(UserConsole.class);
        DispatcherServlet.put("/console/user/", userConsole::updateUser);
        DispatcherServlet.delete("/console/user/{id}", userConsole::removeUser);
        DispatcherServlet.get("/console/users/{page}/{pageSize}/{windowSize}", userConsole::getUsers);
        DispatcherServlet.get("/console/user/{id}", userConsole::getUser);
        DispatcherServlet.get("/console/changeRole/{id}", userConsole::changeUserRole);

        DispatcherServlet.mapping();
    }
}
