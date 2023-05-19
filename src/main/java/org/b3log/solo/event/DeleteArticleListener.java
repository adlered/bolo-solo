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

import org.apache.commons.lang.StringUtils;
import org.b3log.latke.event.AbstractEventListener;
import org.b3log.latke.event.Event;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.ioc.Singleton;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.service.ServiceException;
import org.b3log.solo.service.OptionMgmtService;

/**
 * @author fangcong
 * @version 0.0.1
 * @since Created by work on 2023-05-19 14:16
 **/
@Singleton
public class DeleteArticleListener extends AbstractEventListener<String> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(DeleteArticleListener.class);

    @Override
    public String getEventType() {
        return EventTypes.DELETE_ARTICLE;
    }

    @Override
    public void action(Event<String> event) {
        final String articleId = event.getData();
        if (StringUtils.isBlank(articleId)) {
            return;
        }
        final BeanManager instance = BeanManager.getInstance();
        final OptionMgmtService optionMgmtService = instance.getReference(OptionMgmtService.class);
        try {
            optionMgmtService.removeOption("article_" + articleId);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "及联删除帖子[{0}]关联的option失败" + e.getMessage(), articleId);
        }
    }
}
