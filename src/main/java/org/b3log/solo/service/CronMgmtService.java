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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.util.Stopwatchs;
import org.b3log.solo.model.Option;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Cron management service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 2.9.7
 */
@Service
public class CronMgmtService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(CronMgmtService.class);

    /**
     * Cron thread pool.
     */
    private static ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1);

    /**
     * User query service.
     */
    @Inject
    private UserQueryService userQueryService;

    /**
     * Article management service.
     */
    @Inject
    private ArticleMgmtService articleMgmtService;

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
     * User management service.
     */
    @Inject
    private UserMgmtService userMgmtService;

    @Inject
    private CommentMgmtService commentMgmtService;

    /**
     * Start all cron tasks.
     */
    public void start() {
        final JSONObject preference = optionQueryService.getPreference();
        long delay = 2000;

        SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(() -> {
            try {
                StatisticMgmtService.removeExpiredOnlineVisitor();
            } catch (final Exception e) {
                LOGGER.log(Level.ERROR, "Executes cron failed", e);
            } finally {
                Stopwatchs.release();
            }
        }, delay, 1000 * 60 * 10, TimeUnit.MILLISECONDS);
        delay += 2000;

        SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(() -> {
            try {
                boolean enableAutoFlushGitHub;
                boolean enableAutoFlushGitHubProfile;
                String myGitHubID;
                try {
                    enableAutoFlushGitHub = preference.getBoolean(Option.ID_C_ENABLE_AUTO_FLUSH_GITHUB);
                    myGitHubID = preference.getString(Option.ID_C_MY_GITHUB_ID);
                } catch (NullPointerException | JSONException e) {
                    enableAutoFlushGitHub = false;
                    myGitHubID = "";
                }
                if (enableAutoFlushGitHub) {
                    if (!myGitHubID.isEmpty()) {
                        articleMgmtService.refreshGitHub(myGitHubID);
                    }
                }
                try {
                    enableAutoFlushGitHubProfile = preference
                            .getBoolean(Option.ID_C_ENABLE_AUTO_FLUSH_BLOG_TO_GITHUB_PROFILE);
                } catch (NullPointerException | JSONException e) {
                    enableAutoFlushGitHubProfile = false;
                }
                exportService.exportGitHub(enableAutoFlushGitHubProfile);
            } catch (final Exception e) {
                LOGGER.log(Level.ERROR, "Executes cron failed", e);
            } finally {
                Stopwatchs.release();
            }
        }, delay, 1000 * 60 * 60 * 24, TimeUnit.MILLISECONDS);
        delay += 2000;

        SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(() -> {
            try {
                final String fishKey = userQueryService.getFishKey();
                if (fishKey == null || "".equals(fishKey)) {
                    return;
                }
                commentMgmtService.syncAllArticleCommentFromFishPI();
            } catch (final Throwable e) {
                LOGGER.log(Level.ERROR, "Executes cron failed", e);
            } finally {
                Stopwatchs.release();
            }
        }, delay, 1000 * 60 * 60 * 24, TimeUnit.MILLISECONDS);
        delay += 2000;

        SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(() -> {
            try {
                exportService.exportHacPai(false);
            } catch (final Exception e) {
                LOGGER.log(Level.ERROR, "Executes cron failed", e);
            } finally {
                Stopwatchs.release();
            }
        }, delay + 1000 * 60 * 10, 1000 * 60 * 60 * 24, TimeUnit.MILLISECONDS);
    }

    /**
     * Stop all cron tasks.
     */
    public void stop() {
        SCHEDULED_EXECUTOR_SERVICE.shutdown();
    }

    /**
     * Restart cron tasks.
     */
    public void restart() {
        stop();
        SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1);
        start();
    }
}
