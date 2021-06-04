package org.b3log.solo.improve;

import org.b3log.latke.servlet.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * 感谢您参与「Bolo 用户体验改进计划」
 * 这里是用户体验改进计划的信息收集类
 */
public class ImproveHelper {

    // 提交反馈服务器地址
    private static String helperHost = "http://localhost:4399";

    /**
     * 上传站点详情相关代码
     * @param context 请求数据
     */
    public static void uploadSiteStatistics(final RequestContext context) {
        HttpServletRequest request = context.getRequest();

    }

}
