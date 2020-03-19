/*
 * Solo - A small and beautiful blogging system written in Java.
 * Copyright (c) 2010-present, b3log.org
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
package org.b3log.solo.processor;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.b3log.latke.ioc.BeanManager;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.servlet.HttpMethod;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.JsonRenderer;
import org.b3log.solo.SoloServletListener;
import org.b3log.solo.bolo.SslUtils;
import org.b3log.solo.repository.PluginRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.zeroturnaround.zip.ZipUtil;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

/**
 * KanBanNiang processor. https://github.com/b3log/solo/issues/12472
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.2, Mar 4, 2019
 * @since 2.9.2
 */
@RequestProcessor
public class KanBanNiangProcessor {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(KanBanNiangProcessor.class);

    /**
     * Online KanBanNiang resources download.
     */
    public static void downloadKBNResource() {
        boolean enabled = false;
        try {
            final BeanManager beanManager = BeanManager.getInstance();
            final PluginRepository pluginRepository = beanManager.getReference(PluginRepository.class);
            enabled = pluginRepository.get("看板娘 ＋_0.0.2").optString("status").equals("ENABLED");
        } catch (RepositoryException RE) {
        }
        if (enabled) {
            String path = "";
            File file = null;
            try {
                LOGGER.log(Level.INFO, "Downloading KanBanNiang resources online...");
                final ServletContext servletContext = SoloServletListener.getServletContext();
                final String assets = "/plugins/kanbanniang/assets/";
                path = servletContext.getResource(assets).getPath();
                path = URLDecoder.decode(path);
                System.out.println("KanBanNiang resources dir: " + path);
                String downloadURL = "https://ftp.stackoverflow.wiki/bolo/kanbanniang/KBNModel.zip";
                file = new File(path + "KBNModel.zip");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                URL url = new URL(downloadURL);
                SslUtils.ignoreSsl();
                URLConnection connection = url.openConnection();
                float size = (connection.getContentLength() / 1024 / 1024);
                System.out.println("KanBanNiang resources total size: " + size + " MB ");
                InputStream inputStream = connection.getInputStream();
                long sizeKB = 0;
                int length = 0;
                byte[] bytes = new byte[1024];
                while ((length = inputStream.read(bytes)) != -1) {
                    sizeKB++;
                    fileOutputStream.write(bytes, 0, length);
                    if (sizeKB % 1024 == 0) {
                        int percent = (int) (((sizeKB / 1024) / size) * 100);
                        System.out.print(percent + "% ");
                    }
                }
                System.out.println();
                LOGGER.log(Level.INFO, "Unpacking KanBanNiang resources...");
                fileOutputStream.close();
                inputStream.close();
                ZipUtil.unpack(file, new File(path));
                file.delete();
                LOGGER.log(Level.INFO, "KanBanNiang is ready.");
            } catch (Exception e) {
                file.delete();
                LOGGER.log(Level.ERROR, "KanBanNiang resources download failed. Reason: " + e.toString());
            }
        }
    }

    /**
     * Returns a random model.
     *
     * @param context the specified request context
     */
    @RequestProcessing(value = "/plugins/kanbanniang/assets/model", method = HttpMethod.GET)
    public void randomModel(final RequestContext context) {
        final JsonRenderer renderer = new JsonRenderer();
        context.setRenderer(renderer);
        try {
            final String assets = "/plugins/kanbanniang/assets";
            String model;
            final ServletContext servletContext = SoloServletListener.getServletContext();
            try (final InputStream inputStream = servletContext.getResourceAsStream(assets + "/model-list.json")) {
                final JSONArray models = new JSONArray(IOUtils.toString(inputStream, "UTF-8"));
                final int i = RandomUtils.nextInt(models.length());
                model = models.getString(i);
            }

            try (final InputStream modelResource = servletContext.getResourceAsStream(assets + "/model/" + model + "/index.json")) {
                final JSONObject index = new JSONObject(IOUtils.toString(modelResource, "UTF-8"));
                final JSONArray textures = index.optJSONArray("textures");
                if (textures.length() == 0) {
                    try (final InputStream texturesRes = servletContext.getResourceAsStream(assets + "/model/" + model + "/textures.json")) {
                        final JSONArray texturesArray = new JSONArray(IOUtils.toString(texturesRes, "UTF-8"));
                        final Object element = texturesArray.opt(RandomUtils.nextInt(texturesArray.length()));
                        if (element instanceof JSONArray) {
                            index.put("textures", element);
                        } else {
                            index.put("textures", new JSONArray().put(element));
                        }
                    }
                }
                renderer.setJSONObject(index);
            }
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Returns a random KanBanNiang model failed.");
        }
    }
}
