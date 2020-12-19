package org.b3log.solo.bolo.tool;

import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.solo.bolo.pic.PicUploadProcessor;

public class PBThread implements Runnable {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(PBThread.class);

    final private String STATUS_SPARE = "<span style='color: green; font-weight: bold'>空闲</span>";
    final private String STATUS_RUNNING = "<span style='color: red; font-weight: bold'>运行中</span>";
    final private String STATUS_ERROR = "<span style='color: yellow; font-weight: bold'>有错误，但仍在运行中，请在日志中查看详情并联系维护者</span>";

    private String status = STATUS_SPARE;
    private boolean lock = false;

    public PBThread() {

    }

    public String getStatus() {
        return status;
    }

    @Override
    public void run() {
        if (!lock) {
            // 锁定线程
            lock = true;
            status = STATUS_RUNNING;

            // 开始处理图片
            LOGGER.log(Level.INFO, "图片开始处理");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ignored) {
            }

            // 关闭线程
            lock = false;
            status = STATUS_SPARE;
            LOGGER.log(Level.INFO, "图片处理完毕");
        }
    }
}
