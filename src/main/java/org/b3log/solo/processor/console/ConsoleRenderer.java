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
package org.b3log.solo.processor.console;

import freemarker.template.Template;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.renderer.AbstractFreeMarkerRenderer;
import org.b3log.solo.util.Skins;

/**
 * <a href="http://freemarker.org">FreeMarker</a> HTTP response renderer for administrator console.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 0.4.1
 */
public final class ConsoleRenderer extends AbstractFreeMarkerRenderer {

    /**
     * HTTP servlet request context.
     */
    private final RequestContext context;

    /**
     * Constructs a skin renderer with the specified request context and template name.
     *
     * @param context      the specified request context
     * @param templateName the specified template name
     */
    public ConsoleRenderer(final RequestContext context, final String templateName) {
        this.context = context;
        this.context.setRenderer(this);
        setTemplateName("admin/" + templateName);
    }

    @Override
    protected Template getTemplate() {
        return Skins.getTemplate(getTemplateName());
    }

    @Override
    protected void beforeRender(final RequestContext context) {
    }

    @Override
    protected void afterRender(final RequestContext context) {
    }
}
