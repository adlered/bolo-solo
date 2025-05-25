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
package org.b3log.solo.event;

import org.b3log.latke.event.AbstractEventListener;
import org.b3log.latke.event.Event;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.ioc.Singleton;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.plugin.AbstractPlugin;
import org.b3log.latke.plugin.PluginManager;
import org.b3log.latke.repository.Transaction;
import org.b3log.solo.repository.PluginRepository;
import org.b3log.solo.service.PluginMgmtService;

import java.util.List;

/**
 * This listener is responsible for refreshing plugin after every loaded.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 0.3.1
 */
@Singleton
public class PluginRefresher extends AbstractEventListener<List<AbstractPlugin>> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(PluginRefresher.class);

    @Override
    public void action(final Event<List<AbstractPlugin>> event) {
        final List<AbstractPlugin> plugins = event.getData();

        LOGGER.log(Level.DEBUG, "Processing an event [type={0}, data={1}] in listener [className={2}]",
                event.getType(), plugins, PluginRefresher.class.getName());

        final BeanManager beanManager = BeanManager.getInstance();
        final PluginRepository pluginRepository = beanManager.getReference(PluginRepository.class);

        final Transaction transaction = pluginRepository.beginTransaction();
        try {
            final PluginMgmtService pluginMgmtService = beanManager.getReference(PluginMgmtService.class);
            pluginMgmtService.refresh(plugins);
            transaction.commit();
        } catch (final Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            LOGGER.log(Level.ERROR, "Process plugin loaded event error", e);
        }
    }

    /**
     * Gets the event type {@linkplain PluginManager#PLUGIN_LOADED_EVENT}.
     *
     * @return event type
     */
    @Override
    public String getEventType() {
        return PluginManager.PLUGIN_LOADED_EVENT;
    }
}
