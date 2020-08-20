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
package org.b3log.solo.service;

import org.b3log.latke.ioc.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.service.annotation.Service;
import org.b3log.solo.SoloServletListener;
import org.b3log.solo.model.Option;
import org.b3log.solo.upgrade.*;
import org.json.JSONObject;

/**
 * Upgrade service.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 1.2.0
 */
@Service
public class UpgradeService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(UpgradeService.class);

    /**
     * Option Query Service.
     */
    @Inject
    private OptionQueryService optionQueryService;

    /**
     * IF IT IS TRUE, FAST MIGRATION PROGRAM WILL BE BOOT UP.
     */
    public static boolean boloFastMigration = false;

    /**
     * Upgrades if need.
     */
    public void upgrade() {
        try {
            final JSONObject preference = optionQueryService.getPreference();
            if (null == preference) {
                return;
            }

            // 快速迁移检测程序
            final JSONObject skin = optionQueryService.getSkin();
            if (null == skin) {
                boloFastMigration = true;
                LOGGER.info("No skin dir name has set, enabling Bolo Fast Migration.");
            } else {
                final String currentSkinDirName = skin.optString(Option.ID_C_SKIN_DIR_NAME);
                final String currentMobileSkinDirName = skin.optString(Option.ID_C_MOBILE_SKIN_DIR_NAME);
                if (!currentSkinDirName.contains("bolo") && !currentMobileSkinDirName.contains("bolo")) {
                    boloFastMigration = true;
                    LOGGER.info("Not a bolo skin has set, enabling Bolo Fast Migration.");
                }
            }


            final String currentVer = preference.getString(Option.ID_C_VERSION); // 数据库中的版本
            if (SoloServletListener.VERSION.equals(currentVer)) {
                // 如果数据库中的版本和运行时版本一致则说明已经是最新版
                return;
            }

            // 如果版本较老，则调用对应的升级程序进行升级，并贯穿升级下去直到最新版
            if (!boloFastMigration) {
                switch (currentVer) {
                    case "2.9.9":
                        V299_300.perform();
                    case "3.0.0":
                        V300_310.perform();
                    case "3.1.0":
                        V310_320.perform();
                    case "3.2.0":
                        V320_330.perform();
                    case "3.3.0":
                        V330_340.perform();
                    case "3.4.0":
                        V340_350.perform();
                    case "3.5.0":
                        V350_360.perform();
                    case "3.6.0":
                        V360_361.perform();
                    case "3.6.1":
                        V361_362.perform();
                    case "3.6.2":
                        V362_363.perform();
                    case "3.6.3":
                        V363_364.perform();
                    case "3.6.4":
                        V364_365.perform();
                    case "3.6.5":
                        V365_366.perform();
                    case "3.6.6":
                        V366_367.perform();
                    case "3.6.7":
                        V367_368.perform();
                    case "3.6.8":
                        V368_370.perform();
                    case "3.7.0":
                        V370_380.perform();
                    case "3.8.0":
                        V380_390.perform();
                    case "3.9.0":
                        V390_400.perform();
                    case "4.0.0":
                        V400_410.perform();
                    case "4.1.0":
                        V410_420.perform();
                    case "4.2.0":
                        V420_430.perform();
                        break;
                    default:
                        LOGGER.log(Level.INFO, "Version " + currentVer + " loaded in compatibility mode.");
                        SoloServletListener.VERSION = currentVer;
                }
            }
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Upgrade failed, please contact the Bolo developers or reports this "
                    + "issue: https://github.com/adlered/bolo-solo/issues/new", e);
            System.exit(-1);
        }
    }
}
