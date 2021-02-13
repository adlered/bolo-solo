package org.b3log.solo.bolo.tool;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

public class EditIMG {

    public static void createThumbnail(File srcFile, File dstFile, int width, int height, String suffix) throws Exception {
        BufferedImage buffer = ImageIO.read(srcFile);

        // 计算图片的压缩比
        int w = buffer.getWidth();
        int h = buffer.getHeight();
        double ratiox = 1.0d;
        double ratioy = 1.0d;

        ratiox = w * ratiox / width;
        ratioy = h * ratioy / height;

        if (ratiox >= 1) {
            if (ratioy < 1) {
                ratiox = height * 1.0 / h;
            } else {
                if (ratiox > ratioy) {
                    ratiox = height * 1.0 / h;
                } else {
                    ratiox = width * 1.0 / w;
                }
            }
        } else {
            if (ratioy < 1) {
                if (ratiox > ratioy) {
                    ratiox = height * 1.0 / h;
                } else {
                    ratiox = width * 1.0 / w;
                }
            } else {
                ratiox = width * 1.0 / w;
            }
        }
        // 对于图片的放大或缩小倍数计算完成，ratiox大于1，则表示放大，否则表示缩小
        AffineTransformOp op = new AffineTransformOp(AffineTransform
                .getScaleInstance(ratiox, ratiox), null);
        buffer = op.filter(buffer, null);
        // 从放大的图像中心截图
        buffer = buffer.getSubimage((buffer.getWidth() - width) / 2, (buffer.getHeight() - height) / 2, width, height);
        try {
            ImageIO.write(buffer, suffix, dstFile);
        } catch (Exception ex) {
            throw new Exception(" ImageIo.write error in CreatThum.: "
                    + ex.getMessage());
        }
    }
}
