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
package org.b3log.solo.upgrade;

import org.b3log.latke.Keys;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.model.Role;
import org.b3log.latke.model.User;
import org.b3log.latke.repository.*;
import org.b3log.solo.bolo.tool.MD5Utils;
import org.b3log.solo.model.Option;
import org.b3log.solo.model.UserExt;
import org.b3log.solo.repository.OptionRepository;
import org.b3log.solo.repository.UserRepository;
import org.json.JSONObject;

import java.util.List;

/**
 * Upgrade script from v4.3.1 to v4.3.2.
 *
 * @author <a href="https://github.com/adlered">adlered</a>
 * @since 4.3.2
 */
public final class V431_432 {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(V431_432.class);

    /**
     * Performs upgrade from v4.3.1 to v4.3.2.
     *
     * @throws Exception upgrade fails
     */
    public static void perform() throws Exception {
        final String fromVer = "4.3.1";
        final String toVer = "4.3.2";

        LOGGER.log(Level.INFO, "Upgrading from version [" + fromVer + "] to version [" + toVer + "]....");

        final BeanManager beanManager = BeanManager.getInstance();
        final OptionRepository optionRepository = beanManager.getReference(OptionRepository.class);
        final UserRepository userRepository = beanManager.getReference(UserRepository.class);
        try {
            final Transaction transaction = optionRepository.beginTransaction();

            // 将明文密码和一次MD5加密的密码强制转为二次加密的MD5密码，确保安全性
            Query query = new Query().setFilter(CompositeFilterOperator.or(
                    new PropertyFilter(User.USER_ROLE, FilterOperator.EQUAL, Role.ADMIN_ROLE),
                    new PropertyFilter(User.USER_ROLE, FilterOperator.EQUAL, Role.DEFAULT_ROLE)
            ));
            List<JSONObject> users = userRepository.getList(query);
            for (JSONObject user : users) {
                String username = user.optString(User.USER_NAME);
                String password = user.optString(UserExt.USER_B3_KEY);
                String userRole = user.optString(User.USER_ROLE);
                String id = user.optString(Keys.OBJECT_ID);

                // 密码判断
                if (password.length() == 32) {
                    // 一次加密的MD5，进行一次MD5加密
                    password = MD5Utils.stringToMD5(password);
                } else {
                    // 明文密码，进行两次MD5加密
                    password = MD5Utils.stringToMD5Twice(password);
                }
                user.put(UserExt.USER_B3_KEY, password);

                userRepository.update(id, user);
                System.out.println("[Upgrade] " + username + " / " + password + " / " + userRole);
            }

            final JSONObject versionOpt = optionRepository.get(Option.ID_C_VERSION);
            versionOpt.put(Option.OPTION_VALUE, toVer);
            optionRepository.update(Option.ID_C_VERSION, versionOpt);

            transaction.commit();

            LOGGER.log(Level.INFO, "Upgraded from version [" + fromVer + "] to version [" + toVer + "] successfully");
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Upgrade failed!", e);

            throw new Exception("Upgrade failed from version [" + fromVer + "] to version [" + toVer + "]");
        }
    }
}
