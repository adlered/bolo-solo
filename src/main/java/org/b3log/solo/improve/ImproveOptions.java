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
package org.b3log.solo.improve;

import org.b3log.latke.ioc.BeanManager;
import org.b3log.solo.model.Option;
import org.b3log.solo.service.OptionQueryService;
import org.json.JSONObject;

public class ImproveOptions {

    private static String doJoin = "";

    /**
     * 查询用户是否加入了用户体验改进计划
     * 
     * @return String
     *         空白：还没设置过
     *         false/true：用户已设置
     */
    public static String doJoinHelpImprovePlan() {
        try {
            if (doJoin.isEmpty()) {
                final BeanManager beanManager = BeanManager.getInstance();
                final OptionQueryService optionQueryService = beanManager.getReference(OptionQueryService.class);
                final JSONObject preference = optionQueryService.getPreference();
                doJoin = preference.getString(Option.ID_C_HELP_IMPROVE_PLAN);
            }
        } catch (Exception e) {
            return "";
        }
        return doJoin;
    }
}
