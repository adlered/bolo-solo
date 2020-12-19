package org.b3log.solo.bolo.tool;

import org.b3log.latke.Keys;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.util.CollectionUtils;
import org.b3log.solo.repository.ArticleRepository;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PBThread implements Runnable {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(PBThread.class);

    final private String STATUS_SPARE = "<span style='color: green; font-weight: bold'>空闲</span>";
    final private String STATUS_RUNNING = "<span style='color: red; font-weight: bold'>运行中</span>";
    final private String STATUS_ERROR = "<span style='color: yellow; font-weight: bold'>有错误，但仍在运行中，请在日志中查看详情并联系维护者</span>";

    private String status = STATUS_SPARE;
    private boolean lock = false;

    public PBThread() {
    }

    public String getStatus() {
        return status;
    }

    @Override
    public void run() {
        if (!lock) {
            // 锁定线程
            lock = true;
            status = STATUS_RUNNING;

            // 开始处理图片
            LOGGER.log(Level.INFO, "Converting images...");

            try {
                final BeanManager beanManager = BeanManager.getInstance();
                final ArticleRepository articleRepository = beanManager.getReference(ArticleRepository.class);
                final Query query = new Query();
                final List<JSONObject> articlesResult = articleRepository.getList(query);

                // 两个前缀
                String suffix_1 = "(\\]\\(https://img.hacpai.com).*?\\)";
                String prefix_1 = ")";
                String suffix_2 = "(\\]\\(https://b3logfile.com).*?\\)";
                String prefix_2 = ")";
                // 解剖每个文章并重新上传组合
                for (JSONObject article : articlesResult) {
                    String oId = article.optString("oId");
                    String articleTitle = article.optString("articleTitle");
                    String articleContent = article.optString("articleContent");

                    LOGGER.log(Level.INFO, "Processing article [oId=" + oId + ", articleTitle=" + articleTitle + "]");

                    Pattern pattern_1 = Pattern.compile(suffix_1);
                    Pattern pattern_2 = Pattern.compile(suffix_2);
                    Matcher matcher_1 = pattern_1.matcher(articleContent);
                    Matcher matcher_2 = pattern_2.matcher(articleContent);

                    ArrayList<String> urlList = new ArrayList<>();
                    while (matcher_1.find()) {
                        urlList.add(matcher_1.group().replaceAll("\\]\\(", "").replaceAll("\\)", ""));
                    }
                    while (matcher_2.find()) {
                        urlList.add(matcher_2.group().replaceAll("\\]\\(", "").replaceAll("\\)", ""));
                    }

                    // 逐个处理图片
                    ArrayList<String> newUrlList = new ArrayList<>();
                    for (int i = 0; i < urlList.size(); i++) {
                        String url = urlList.get(i);
                        String newUrl = "None";
                        // 处理
                        // 保存
                        newUrlList.add(newUrl);
                        LOGGER.log(Level.INFO, url + " >>> " + newUrl);
                    }

                }

            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Cannot get articles.");
                lock = false;
                status = STATUS_ERROR;
                try {
                    Thread.sleep(1000 * 60);
                } catch (InterruptedException ignored) {
                }
            }

            // 关闭线程
            lock = false;
            status = STATUS_SPARE;
            LOGGER.log(Level.INFO, "Image convert completed.");
        }
    }
}
