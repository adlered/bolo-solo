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
package pers.adlered.blog_platform_export_tool.util;

import pers.adlered.blog_platform_export_tool.module.TranslateResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>BlogPlatformExportTool</h3>
 * <p>解析类</p>
 *
 * @author : https://github.com/adlered
 * @date : 2020-05-23
 **/
public class XML {

    public static List<String> getAvailableFiles() {
        List<String> availableFiles = new ArrayList<>();
        String xmlFolder = Decompress.path;
        File file = new File(xmlFolder);
        try {
            File[] files = file.listFiles();
            if (files != null) {
                for (File i : files) {
                    availableFiles.add(i.getAbsolutePath());
                }
            }
        } catch (NullPointerException ignored) {
        }
        return availableFiles;
    }

    // 用于调试，打印出结果
    public static void printResult(List<TranslateResult> result) {
        for (TranslateResult i : result) {
            System.out.println("=====================================================");
            System.out.println("标题：" +  i.getTitle());
            System.out.println("作者：" + i.getAuthor());
            System.out.println("日期：" + i.getDate());
            System.out.println("链接：" + i.getLink());
            // System.out.println("正文：" + i.getArticleContent());
            System.out.println("=====================================================");
        }
    }
}
