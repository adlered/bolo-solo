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

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>BlogPlatformExportTool</h3>
 * <p>智能解压 RAR 及 ZIP</p>
 *
 * @author : https://github.com/adlered
 * @date : 2020-05-14
 **/
public class Decompress {
    public static String path;

    public Decompress(String path) {
        Decompress.path = path;
    }

    public void run() {
        List<String> unzipFiles = analyze();
        for (String i : unzipFiles) {
            String filename = path + "/" + i;
            String[] splitFilename = i.split("\\.");
            i = splitFilename[splitFilename.length - 1];
            System.out.println("Unpacking " + filename);
            if ("zip".equals(i)) {
                ZipUtil.unpack(new File(filename), new File(path));
            }
        }
        delete(unzipFiles);
    }

    private List<String> analyze() {
        File file = new File(path);
        File[] files = file.listFiles();
        List<String> supported = new ArrayList<>();
        supported.add("zip");
        List<String> result = new ArrayList<>();
        for (File i : files != null ? files : new File[0]) {
            String filename = i.getName();
            String[] splitFilename = filename.split("\\.");
            String prefix = splitFilename[splitFilename.length - 1];
            if (supported.contains(prefix)) {
                result.add(filename);
            }
        }
        return result;
    }

    private void delete(List<String> unzipFiles) {
        File dsStore = new File(path + "/.DS_Store");
        if (dsStore.exists()) {
            unzipFiles.add(dsStore.getName());
        }
        for (String i : unzipFiles) {
            String filename = path + "/" + i;
            File file = new File(filename);
            boolean result = file.delete();
            System.out.println("Remove " + i + "  " + result);
        }
    }
}
