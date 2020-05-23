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
package pers.adlered.blog_platform_export_tool.module.cnblogs;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import pers.adlered.blog_platform_export_tool.module.ModuleService;
import pers.adlered.blog_platform_export_tool.module.TranslateResult;
import pers.adlered.blog_platform_export_tool.util.XML;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>BlogPlatformExportTool</h3>
 * <p>博客园导入模块</p>
 *
 * @author : https://github.com/adlered
 * @date : 2020-05-23
 **/
public class CNBLOGSModuleServiceImpl implements ModuleService {

    @Override
    public List<TranslateResult> analyze() {
        List<TranslateResult> list = new ArrayList<>();
        List<String> fileList = XML.getAvailableFiles();
        for (String i : fileList) {
            File file = new File(i);
            try {
                XmlReader xmlReader = new XmlReader(file);
                SyndFeedInput syndFeedInput = new SyndFeedInput();
                SyndFeed syndFeed = syndFeedInput.build(xmlReader);
                List<SyndEntryImpl> entries = syndFeed.getEntries();
                for (SyndEntryImpl j : entries) {
                    TranslateResult translateResult = new TranslateResult();
                    translateResult.setAuthor(j.getAuthor());
                    translateResult.setArticleContent(j.getDescription().getValue());
                    translateResult.setLink(j.getLink());
                    translateResult.setTitle(j.getTitle().trim());
                    translateResult.setDate(j.getPublishedDate());
                    list.add(translateResult);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
