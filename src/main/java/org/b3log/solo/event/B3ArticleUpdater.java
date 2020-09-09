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
package org.b3log.solo.event;

import org.b3log.latke.event.AbstractEventListener;
import org.b3log.latke.event.Event;
import org.b3log.latke.ioc.Singleton;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.json.JSONObject;

/**
 * This listener is responsible for updating article to B3log Rhythm. Sees <a href="https://ld246.com/b3log">B3log 构思</a> for more details.
 * <p>
 * API spec: https://ld246.com/article/1457158841475
 * </p>
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 0.6.0
 */
@Singleton
public class B3ArticleUpdater extends AbstractEventListener<JSONObject> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(B3ArticleUpdater.class);

    public void action(final Event<JSONObject> event) {
        final JSONObject data = event.getData();
        LOGGER.log(Level.DEBUG, "Processing an event [type={0}, data={1}] in listener [className={2}]",
                event.getType(), data, B3ArticleUpdater.class.getName());

        B3ArticleSender.pushArticleToRhy(data);
    }

    /**
     * Gets the event type {@linkplain EventTypes#UPDATE_ARTICLE}.
     *
     * @return event type
     */
    @Override
    public String getEventType() {
        return EventTypes.UPDATE_ARTICLE;
    }
}
