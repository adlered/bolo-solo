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
package org.b3log.solo.util;

import org.b3log.latke.ioc.BeanManager;
import org.b3log.solo.repository.PluginRepository;

/**
 * Get Bolo Plugin status.
 */
public class PluginUtil {

    final static BeanManager beanManager = BeanManager.getInstance();
    final static PluginRepository pluginRepository = beanManager.getReference(PluginRepository.class);

    public static boolean b3logPluginEnabled() {
        try {
            return pluginRepository.get("B3log支持插件_0.0.1").optString("status").equals("ENABLED");
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean fishpiPluginEnabled() {
        try {
            return pluginRepository.get("摸鱼派支持插件_0.0.1").optString("status").equals("ENABLED");
        } catch (Exception e) {
            return false;
        }
    }
}
