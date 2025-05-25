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
package org.b3log.solo.upgrade;

import java.util.ArrayList;
import java.util.List;

import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.solo.model.Follow;
import org.b3log.solo.model.Option;
import org.b3log.solo.service.InitService;
import org.b3log.solo.service.OptionMgmtService;
import org.b3log.solo.service.OptionQueryService;
import org.json.JSONObject;

/**
 * Upgrade script from v4.3.3 to v4.3.4.
 *
 * @author <a href="https://github.com/gakkiyomi">gakkiyomi</a>
 * @since 4.3.3
 */
public final class V433_434 {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(V433_434.class);

    /**
     * Performs upgrade from v4.3.3 to v4.3.4.
     *
     * @throws Exception upgrade fails
     */
    public static void perform() throws Exception {
        final String fromVer = "4.3.3";
        final String toVer = "4.3.4";

        LOGGER.log(Level.INFO, "Upgrading from version [" + fromVer + "] to version [" + toVer + "]....");

        final BeanManager beanManager = BeanManager.getInstance();
        final OptionMgmtService optionMgmtService = beanManager.getReference(OptionMgmtService.class);
        final OptionQueryService optionQueryService = beanManager.getReference(OptionQueryService.class);
        final InitService initService = beanManager.getReference(InitService.class);
        try {
            final List<String> models = new ArrayList<>();
            models.add(Follow.FOLLOW);
            initService.initSpecificTables(models);
            final JSONObject versionOpt = optionQueryService.getOptionById(Option.ID_C_VERSION);
            versionOpt.put(Option.OPTION_VALUE, toVer);
            optionMgmtService.addOrUpdateOption(versionOpt);
            LOGGER.log(Level.INFO, "Upgraded from version [" + fromVer + "] to version [" + toVer + "] successfully");
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Upgrade failed!", e);

            throw new Exception("Upgrade failed from version [" + fromVer + "] to version [" + toVer + "]");
        }
    }
}
