package org.b3log.solo.improve;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.b3log.latke.servlet.RequestContext;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * 感谢您参与「Bolo 用户体验改进计划」
 * 这里是用户体验改进计划的信息收集类
 */
public class ImproveHelper {

    // 提交反馈服务器地址
    private static String helperHost = "http://localhost:4399/Log";

    /**
     * 上传站点详情相关代码
     * @param context 请求数据
     */
    public static void uploadSiteStatistics(final RequestContext context) {
        JSONObject statisticsObject = new JSONObject();
        statisticsObject.put("category", "statistics");

        JSONObject statisticsDataObject = new JSONObject();
        HttpServletRequest request = context.getRequest();

        /**
         * 隐私信息说明
         * requestURL：访问者访问的URL地址
         * ClientIP：访问者的IP地址，后两位做去敏处理
         */
        statisticsDataObject.put("requestURL", request.getRequestURI());
        statisticsDataObject.put("clientIP", request.getRemoteHost());

        statisticsObject.put("data", statisticsDataObject);

        CloseableHttpClient uploadSiteStatisticsHttpClient = createSSLClientDefault();
        HttpPost httpPost = new HttpPost(helperHost);
        String params = statisticsObject.toString();
        StringEntity httpEntity = new StringEntity(params, "utf-8");
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(httpEntity);

        try {
            CloseableHttpResponse response = uploadSiteStatisticsHttpClient.execute(httpPost);
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置可访问HTTPS
     *
     * @return null
     */
    public static CloseableHttpClient createSSLClientDefault() {
        try {
            //信任所有
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }
}
