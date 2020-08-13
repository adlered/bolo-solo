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
package org.b3log.solo.processor;

import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.Transaction;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.renderer.AbstractFreeMarkerRenderer;
import org.b3log.solo.model.Option;
import org.b3log.solo.repository.OptionRepository;
import org.b3log.solo.service.ArticleMgmtService;
import org.b3log.solo.util.Skins;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.StringWriter;
import java.util.Map;

/**
 * Skin renderer.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 2.9.1
 */
public final class SkinRenderer extends AbstractFreeMarkerRenderer {

    /**
     * HTTP servlet request context.
     */
    private final RequestContext context;

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ArticleMgmtService.class);

    /**
     * Constructs a skin renderer with the specified request context and template name.
     *
     * @param context      the specified request context
     * @param templateName the specified template name
     */
    public SkinRenderer(final RequestContext context, final String templateName) {
        this.context = context;
        this.context.setRenderer(this);
        setTemplateName(templateName);
    }

    @Override
    protected Template getTemplate() {
        final String templateName = getTemplateName();
        Template ret = Skins.getSkinTemplate(context, templateName);
        if (null == ret) {
            // 优先使用皮肤内的登录、报错等模板 https://github.com/b3log/solo/issues/12566
            ret = Skins.getTemplate(templateName);
        }
        if (null == ret) {
            // Bolo 默认皮肤名称，如果迁移过来的可能还是 solo-nexmoe，更新一下，防止错误
            try {
                LOGGER.log(Level.INFO, "It seems like we can't found the skin for mobile. Restoring this setting default, please refresh your page, it should be OK.");
                final BeanManager beanManager = BeanManager.getInstance();
                final OptionRepository optionRepository = beanManager.getReference(OptionRepository.class);
                final Transaction transaction = optionRepository.beginTransaction();

                final JSONObject mobileSkinDirNameOpt = optionRepository.get(Option.ID_C_MOBILE_SKIN_DIR_NAME);
                mobileSkinDirNameOpt.put(Option.OPTION_VALUE, Option.DefaultPreference.DEFAULT_MOBILE_SKIN_DIR_NAME);
                optionRepository.update(Option.ID_C_MOBILE_SKIN_DIR_NAME, mobileSkinDirNameOpt);

                transaction.commit();
            } catch (RepositoryException RE) {
                RE.printStackTrace();
            }
        }

        return ret;
    }

    /**
     * Processes the specified FreeMarker template with the specified request, data model, pjax hacking.
     *
     * @param request   the specified request
     * @param dataModel the specified data model
     * @param template  the specified FreeMarker template
     * @return generated HTML
     * @throws Exception exception
     */
    @Override
    protected String genHTML(final HttpServletRequest request, final Map<String, Object> dataModel, final Template template)
            throws Exception {
        final boolean isPJAX = isPJAX(context);
        dataModel.put("pjax", isPJAX);

        if (!isPJAX) {
            return super.genHTML(request, dataModel, template);
        }

        final StringWriter stringWriter = new StringWriter();
        template.setOutputEncoding("UTF-8");
        template.process(dataModel, stringWriter);
        final long endTimeMillis = System.currentTimeMillis();
        final String dateString = DateFormatUtils.format(endTimeMillis, "yyyy/MM/dd HH:mm:ss");
        final long startTimeMillis = (Long) context.attr(Keys.HttpRequest.START_TIME_MILLIS);
        final String latke = String.format("\n<!-- Generated by Latke (https://github.com/b3log/latke) in %1$dms, %2$s -->", endTimeMillis - startTimeMillis, dateString);
        final String pjaxContainer = context.header("X-PJAX-Container");

        final String html = stringWriter.toString();
        final String[] containers = StringUtils.substringsBetween(html,
                "<!---- pjax {" + pjaxContainer + "} start ---->",
                "<!---- pjax {" + pjaxContainer + "} end ---->");
        if (null == containers) {
            return html + latke;
        }

        return String.join("", containers) + latke;
    }

    @Override
    protected void beforeRender(final RequestContext context) {
    }

    @Override
    protected void afterRender(final RequestContext context) {
    }

    /**
     * Determines whether the specified request is sending with pjax.
     *
     * @param context the specified request context
     * @return {@code true} if it is sending with pjax, otherwise returns {@code false}
     */
    private static boolean isPJAX(final RequestContext context) {
        final boolean pjax = Boolean.valueOf(context.header("X-PJAX"));
        final String pjaxContainer = context.header("X-PJAX-Container");

        return pjax && StringUtils.isNotBlank(pjaxContainer);
    }
}
