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
package pers.adlered.blog_platform_export_tool;

import pers.adlered.blog_platform_export_tool.module.ModuleService;
import pers.adlered.blog_platform_export_tool.module.TranslateResult;
import pers.adlered.blog_platform_export_tool.util.Decompress;
import pers.adlered.blog_platform_export_tool.util.XML;

import java.util.List;

/**
 * <h3>BlogPlatformExportTool</h3>
 * <p>博客平台导出器主方法.</p>
 *
 * @author : https://github.com/adlered
 * @date : 2020-05-14
 **/
public class Launcher {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        List<TranslateResult> list = run("CNBlogs");
        XML.printResult(list);
    }

    public static List<TranslateResult> run(String blogType) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Decompress decompress = new Decompress("temp/file");
        decompress.run();
        Class<?> serviceClass = Class.forName("pers.adlered.blog_platform_export_tool.module." + blogType.toLowerCase() + "." + blogType.toUpperCase() + "ModuleServiceImpl");
        ModuleService moduleService = (ModuleService) serviceClass.newInstance();
        return moduleService.analyze();
    }
}
