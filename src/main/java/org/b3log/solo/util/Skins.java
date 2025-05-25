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
package org.b3log.solo.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.ServiceException;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.util.Locales;
import org.b3log.latke.util.Stopwatchs;
import org.b3log.solo.SoloServletListener;
import org.b3log.solo.model.Common;
import org.b3log.solo.model.Option;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Skin utilities.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 0.3.1
 */
public final class Skins {

    /**
     * FreeMarker configuration.
     */
    public static final Configuration TEMPLATE_CFG;
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Skins.class);
    /**
     * Properties map.
     */
    private static final Map<String, Map<String, String>> LANG_MAP = new HashMap<>();

    static {
        TEMPLATE_CFG = new Configuration(Configuration.VERSION_2_3_29);
        TEMPLATE_CFG.setDefaultEncoding("UTF-8");
        final ServletContext servletContext = SoloServletListener.getServletContext();
        TEMPLATE_CFG.setServletContextForTemplateLoading(servletContext, "");
        TEMPLATE_CFG.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        TEMPLATE_CFG.setLogTemplateExceptions(false);
    }

    /**
     * Private constructor.
     */
    private Skins() {
    }

    /**
     * Gets a template with the specified template name.
     *
     * @param templateName the specified template name
     * @return template, returns {@code null} if not found
     */
    public static Template getTemplate(final String templateName) {
        try {
            return Skins.TEMPLATE_CFG.getTemplate(templateName);
        } catch (final IOException e) {
            LOGGER.log(Level.ERROR, "Gets console template [" + templateName + "] failed", e);

            return null;
        }
    }

    /**
     * Gets a skins template with the specified request and template name.
     *
     * @param context      the specified request context
     * @param templateName the specified template name
     * @return template, returns {@code null} if not found
     */
    public static Template getSkinTemplate(final RequestContext context, final String templateName) {
        String templateDirName = (String) context.attr(Keys.TEMAPLTE_DIR_NAME);
        if (StringUtils.isBlank(templateDirName)) {
            templateDirName = Option.DefaultPreference.DEFAULT_SKIN_DIR_NAME;
        }

        try {
            return Skins.TEMPLATE_CFG.getTemplate("skins/" + templateDirName + "/" + templateName);
        } catch (final IOException e) {
            return null;
        }
    }

    /**
     * Fills the specified data model with the current skin's (WebRoot/skins/${skinDirName}/lang/lang_xx_XX.properties)
     * and core language (WebRoot/WEB-INF/classes/lang_xx_XX.properties) configurations.
     *
     * @param localeString       the specified locale string
     * @param currentSkinDirName the specified current skin directory name
     * @param dataModel          the specified data model
     * @throws ServiceException service exception
     */
    public static void fillLangs(final String localeString, String currentSkinDirName, final Map<String, Object> dataModel)
            throws ServiceException {
        Stopwatchs.start("Fill Skin Langs");

        try {
            // Fills the core language configurations
            final BeanManager beanManager = BeanManager.getInstance();
            final LangPropsService langPropsService = beanManager.getReference(LangPropsService.class);
            dataModel.putAll(langPropsService.getAll(Latkes.getLocale()));

            if (StringUtils.isBlank(currentSkinDirName)) {
                currentSkinDirName = Option.DefaultPreference.DEFAULT_SKIN_DIR_NAME;
            }
            final String langName = currentSkinDirName + "." + localeString;
            Map<String, String> langs = LANG_MAP.get(langName);
            if (null == langs) {
                LANG_MAP.clear(); // Collect unused skin languages

                langs = new HashMap<>();
                final String language = Locales.getLanguage(localeString);
                final String country = Locales.getCountry(localeString);
                final ServletContext servletContext = SoloServletListener.getServletContext();
                final InputStream inputStream = servletContext.getResourceAsStream(
                        "/skins/" + currentSkinDirName + "/lang/lang_" + language + '_' + country + ".properties");
                if (null != inputStream) {
                    LOGGER.log(Level.DEBUG, "Loading skin [dirName={0}, locale={1}]", currentSkinDirName, localeString);
                    final Properties props = new Properties();
                    props.load(inputStream);
                    inputStream.close();
                    final Set<Object> keys = props.keySet();
                    for (final Object key : keys) {
                        String val = props.getProperty((String) key);
                        val = replaceVars(val);
                        langs.put((String) key, val);
                    }

                    LANG_MAP.put(langName, langs);
                    LOGGER.log(Level.DEBUG, "Loaded skin [dirName={0}, locale={1}, keyCount={2}]", currentSkinDirName, localeString, langs.size());
                }
            }

            dataModel.putAll(langs); // Fills the current skin's language configurations
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Fills skin langs failed", e);

            throw new ServiceException(e);
        } finally {
            Stopwatchs.end();
        }
    }

    /**
     * Gets all skin directory names. Scans the /skins/ directory, using the subdirectory of it as the skin directory
     * name, for example,
     * <pre>
     * ${Web root}/skins/
     *     <b>default</b>/
     *     <b>mobile</b>/
     *     <b>classic</b>/
     * </pre>.
     *
     * @return a set of skin name, returns an empty set if not found
     */
    public static Set<String> getSkinDirNames() {
        final Set<String> ret = new HashSet<>();

        final ServletContext servletContext = SoloServletListener.getServletContext();
        final Set<String> resourcePaths = servletContext.getResourcePaths("/skins");
        for (final String path : resourcePaths) {
            final Path p = Paths.get(path);
            final Path file = p.getFileName();
            final String fileName = file.toString();
            if (fileName.startsWith(".") || fileName.endsWith(".md")) {
                continue;
            }

            ret.add(fileName);
        }

        return ret;
    }

    /**
     * Gets skin directory name from the specified request.
     * Refers to <a href="https://github.com/b3log/solo/issues/12060">前台皮肤切换</a> for more details.
     *
     * @param context the specified request context
     * @return directory name, or {@code null} if not found
     */
    public static String getSkinDirName(final RequestContext context) {
        // 1. Get skin from query
        final String specifiedSkin = context.param(Option.CATEGORY_C_SKIN);
        if (StringUtils.isNotBlank(specifiedSkin)) {
            final Set<String> skinDirNames = Skins.getSkinDirNames();
            if (skinDirNames.contains(specifiedSkin)) {
                return specifiedSkin;
            } else {
                return null;
            }
        }

        // 2. Get skin from cookie
        return getSkinDirNameFromCookie(context.getRequest());
    }

    /**
     * Gets skin directory name from the specified request's cookie.
     *
     * @param request the specified request
     * @return directory name, or {@code null} if not found
     */
    public static String getSkinDirNameFromCookie(final HttpServletRequest request) {
        final Set<String> skinDirNames = Skins.getSkinDirNames();
        boolean isMobile = Solos.isMobile(request);
        String skin = null, mobileSkin = null;
        final Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (final Cookie cookie : cookies) {
                if (Common.COOKIE_NAME_SKIN.equals(cookie.getName()) && !isMobile) {
                    final String s = cookie.getValue();
                    if (skinDirNames.contains(s)) {
                        skin = s;
                        break;
                    }
                }
                if (Common.COOKIE_NAME_MOBILE_SKIN.equals(cookie.getName()) && isMobile) {
                    final String s = cookie.getValue();
                    if (skinDirNames.contains(s)) {
                        mobileSkin = s;
                        break;
                    }
                }
            }
        }

        if (StringUtils.isNotBlank(skin)) {
            return skin;
        }
        if (StringUtils.isNotBlank(mobileSkin)) {
            return mobileSkin;
        }

        return null;
    }

    /**
     * Replaces all variables of the specified language value.
     *
     * <p>
     * Variables:
     * <ul>
     * <li>${servePath}</li>
     * <li>${staticServePath}</li>
     * </ul>
     * </p>
     *
     * @param langValue the specified language value
     * @return replaced value
     */
    private static String replaceVars(final String langValue) {
        String ret = StringUtils.replace(langValue, "${servePath}", Latkes.getServePath());
        ret = StringUtils.replace(ret, "${staticServePath}", Latkes.getStaticServePath());

        return ret;
    }
}
