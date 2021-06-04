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

        HttpServletRequest request = context.getRequest();
        JSONObject statisticsDataObject = new JSONObject();
        statisticsDataObject.put("test", "yes");
        statisticsDataObject.put("alsoTest", 100);
        statisticsObject.put("data", statisticsDataObject);

        CloseableHttpClient uploadSiteStatisticsHttpClient = createSSLClientDefault();
        HttpPost httpPost = new HttpPost(helperHost);
        String params = statisticsObject.toString();
        StringEntity httpEntity = new StringEntity(params, "utf-8");
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(httpEntity);

        try {
            CloseableHttpResponse response = uploadSiteStatisticsHttpClient.execute(httpPost);
            System.out.println(response.getStatusLine().getStatusCode());
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
