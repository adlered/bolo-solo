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

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.solo.bolo.pic.util.UploadUtil;
import org.b3log.solo.bolo.prop.Options;
import org.b3log.solo.model.Option;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Image utilities.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @since 2.7.0
 */
public final class Images {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Images.class);

    /**
     * Community file service URL.
     */
    public static String COMMUNITY_FILE_URL = "https://b3logfile.com";

    /**
     * Private constructor.
     */
    private Images() {
    }

    /**
     * Qiniu image processing.
     *
     * @param html the specified content HTML
     * @return processed content
     */
    public static String qiniuImgProcessing(final String html) {
        String ret = html;
        final String[] imgSrcs = StringUtils.substringsBetween(html, "<img src=\"", "\"");
        if (null == imgSrcs) {
            return ret;
        }

        for (final String imgSrc : imgSrcs) {
            if (StringUtils.contains(imgSrc, ".gif") || StringUtils.containsIgnoreCase(imgSrc, "imageView")) {
                continue;
            }

            ret = StringUtils.replace(ret, imgSrc, imgSrc + "?imageView2/2/w/1280/format/jpg/interlace/1/q/100");
        }

        return ret;
    }

    /**
     * Returns image URL of Qiniu image processing style with the specified width and height.
     *
     * @param imageURL the specified image URL
     * @param width    the specified width
     * @param height   the specified height
     * @return image URL
     */
    public static String imageSize(final String imageURL, final int width, final int height) {
        if (StringUtils.containsIgnoreCase(imageURL, "imageView")) {
            return imageURL;
        }

        return imageURL + "?imageView2/1/w/" + width + "/h/" + height + "/interlace/1/q/100";
    }

    public static File compressImage(File inputFile, float quality) throws IOException {
        BufferedImage image = ImageIO.read(inputFile);
        File compressedFile = new File(inputFile.getParent(), "compressed_" + inputFile.getName());

        ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(compressedFile);
        jpgWriter.setOutput(ios);

        ImageWriteParam param = jpgWriter.getDefaultWriteParam();
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);
        }
        jpgWriter.write(null, new javax.imageio.IIOImage(image, null, null), param);
        ios.close();
        jpgWriter.dispose();

        LOGGER.log(Level.INFO, "Temp Image " + inputFile.getName() + " Delete [" + inputFile.delete() + "]");
        return compressedFile;
    }


    /**
     * Gets an image URL randomly. Sees https://github.com/b3log/bing for more details.
     *
     * @return an image URL
     */
    public static String randImage() {
        // 修改
        try {
            final long min = DateUtils.parseDate("20171104", new String[]{"yyyyMMdd"}).getTime();
            final long max = System.currentTimeMillis();
            final long delta = max - min;
            final long time = ThreadLocalRandom.current().nextLong(0, delta) + min;

            String imageName = DateFormatUtils.format(time, "yyyyMMdd") + ".jpg";
            String B3logImageURL = COMMUNITY_FILE_URL + "/bing/" + imageName;
            String config = Options.get(Option.ID_C_TUCHUANG_CONFIG);
            String value = Options.get(Option.ID_C_THUMB_COMPRESS);

            if (!config.equals("hacpai") && !config.isEmpty()) {
                File file = new File("temp/tmp_" + imageName);
                // FileNotFoundException
                FileUtils.copyURLToFile(new URL(B3logImageURL), file);
                return UploadUtil.upload(config, compressImage(file, Float.parseFloat(value)));
            }
            return B3logImageURL;

        } catch (final FileNotFoundException e) {
            LOGGER.log(Level.ERROR, "Remote image resource lost", e);
            return COMMUNITY_FILE_URL + "/bing/20171104.jpg";
        } catch (final Exception e) {
            LOGGER.log(Level.ERROR, "Generates random image URL failed", e);
            return COMMUNITY_FILE_URL + "/bing/20171104.jpg";
        }

    }

    /**
     * Gets image URLs randomly.
     *
     * @param n the specified size
     * @return image URLs
     */
    public static List<String> randomImages(final int n) {
        final List<String> ret = new ArrayList<>();

        int i = 0;
        while (i < n * 5) {
            final String url = randImage();
            if (!ret.contains(url)) {
                ret.add(url);
            }

            if (ret.size() >= n) {
                return ret;
            }

            i++;
        }

        return ret;
    }
}
