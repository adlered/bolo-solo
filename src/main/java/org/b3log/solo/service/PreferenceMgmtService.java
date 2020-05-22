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
package org.b3log.solo.service;

import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.service.annotation.Service;
import org.b3log.latke.util.Locales;
import org.b3log.solo.bolo.prop.MailService;
import org.b3log.solo.model.Option;
import org.b3log.solo.repository.OptionRepository;
import org.json.JSONObject;

import java.util.*;

/**
 * Preference management service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.4.0.6, Apr 6, 2020
 * @since 0.4.0
 */
@Service
public class PreferenceMgmtService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(PreferenceMgmtService.class);

    /**
     * Option query service.
     */
    @Inject
    private OptionQueryService optionQueryService;

    /**
     * Option repository.
     */
    @Inject
    private OptionRepository optionRepository;

    /**
     * Language service.
     */
    @Inject
    private LangPropsService langPropsService;

    /**
     * Updates the preference with the specified preference.
     *
     * @param preference the specified preference
     * @throws ServiceException service exception
     */
    public void updatePreference(final JSONObject preference) throws ServiceException {
        final Iterator<String> keys = preference.keys();
        while (keys.hasNext()) {
            final String key = keys.next();
            if (preference.isNull(key)) {
                throw new ServiceException("A value is null of preference [key=" + key + "]");
            }
        }

        final Transaction transaction = optionRepository.beginTransaction();

        try {
            preference.put(Option.ID_C_SIGNS, preference.get(Option.ID_C_SIGNS).toString());

            final JSONObject oldPreference = optionQueryService.getPreference();

            final String version = oldPreference.optString(Option.ID_C_VERSION);
            preference.put(Option.ID_C_VERSION, version);

            final String localeString = preference.getString(Option.ID_C_LOCALE_STRING);
            Latkes.setLocale(new Locale(Locales.getLanguage(localeString), Locales.getCountry(localeString)));

            List<String> optionList = new ArrayList<>();
            Collections.addAll(optionList,
                    // Solo 字段
                    Option.ID_C_CUSTOM_VARS,
                    Option.ID_C_HLJS_THEME,
                    Option.ID_C_PULL_GITHUB,
                    Option.ID_C_SYNC_GITHUB,
                    Option.ID_C_FAVICON_URL,
                    Option.ID_C_VERSION,
                    Option.ID_C_TIME_ZONE_ID,
                    Option.ID_C_SIGNS,
                    Option.ID_C_RELEVANT_ARTICLES_DISPLAY_CNT,
                    Option.ID_C_RECENT_COMMENT_DISPLAY_CNT,
                    Option.ID_C_RECENT_ARTICLE_DISPLAY_CNT,
                    Option.ID_C_RANDOM_ARTICLES_DISPLAY_CNT,
                    Option.ID_C_NOTICE_BOARD,
                    Option.ID_C_MOST_VIEW_ARTICLE_DISPLAY_CNT,
                    Option.ID_C_MOST_USED_TAG_DISPLAY_CNT,
                    Option.ID_C_MOST_COMMENT_ARTICLE_DISPLAY_CNT,
                    Option.ID_C_META_KEYWORDS,
                    Option.ID_C_META_DESCRIPTION,
                    Option.ID_C_LOCALE_STRING,
                    Option.ID_C_HTML_HEAD,
                    Option.ID_C_FOOTER_CONTENT,
                    Option.ID_C_FEED_OUTPUT_MODE,
                    Option.ID_C_FEED_OUTPUT_CNT,
                    Option.ID_C_EXTERNAL_RELEVANT_ARTICLES_DISPLAY_CNT,
                    Option.ID_C_ENABLE_ARTICLE_UPDATE_HINT,
                    Option.ID_C_COMMENTABLE,
                    Option.ID_C_BLOG_TITLE,
                    Option.ID_C_BLOG_SUBTITLE,
                    Option.ID_C_ARTICLE_LIST_STYLE,
                    Option.ID_C_ARTICLE_LIST_PAGINATION_WINDOW_SIZE,
                    Option.ID_C_ARTICLE_LIST_DISPLAY_COUNT,
                    Option.ID_C_ALLOW_VISIT_DRAFT_VIA_PERMALINK,
                    // Bolo 字段
                    Option.ID_C_MAX_ARCHIVE,
                    Option.ID_C_MAIL_BOX,
                    Option.ID_C_MAIL_USERNAME,
                    Option.ID_C_MAIL_PASSWORD,
                    Option.ID_C_TUCHUANG_CONFIG,
                    Option.ID_C_REPLY_REMIND,
                    Option.ID_C_KANBANNIANG_SELECTOR,
                    Option.ID_C_EDITOR_MODE,
                    Option.ID_C_B3LOG_KEY,
                    Option.ID_C_HACPAI_USER,
                    Option.ID_C_SPAM
                    );

            for (String i : optionList) {
                emptyPreferenceOptSave(i, preference.optString(i));
            }

            transaction.commit();
            MailService.loadMailSettings();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Updates preference failed", e);
            throw new ServiceException(langPropsService.get("updateFailLabel"));
        }

        LOGGER.log(Level.DEBUG, "Updates preference successfully");
    }

    private void emptyPreferenceOptSave(final String optID, final String val) throws Exception {
        // 该方法用于向后兼容，如果数据库中不存在该配置项则创建再保存
        JSONObject opt = optionRepository.get(optID);
        if (null == opt) {
            opt = new JSONObject();
            opt.put(Keys.OBJECT_ID, optID);
            opt.put(Option.OPTION_CATEGORY, Option.CATEGORY_C_PREFERENCE);
            opt.put(Option.OPTION_VALUE, val);
            optionRepository.add(opt);
        } else {
            opt.put(Option.OPTION_VALUE, val);
            optionRepository.update(optID, opt);
        }
    }
}
