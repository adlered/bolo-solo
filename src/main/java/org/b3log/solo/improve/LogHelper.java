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
package org.b3log.solo.improve;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.b3log.latke.Latkes;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.solo.bolo.tool.FixSizeLinkedList;
import org.b3log.solo.bolo.tool.PassSSL;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * 感谢您参与「Bolo 用户体验改进计划」
 * 这里是用户体验改进计划的错误日志收集类
 */
public class LogHelper implements Runnable {

    // 提交反馈服务器地址
    private static final String helperHost = "http://report.stackoverflow.wiki:4399/Log";
    private final FixSizeLinkedList<Map<String, Object>> list;

    public LogHelper(FixSizeLinkedList<Map<String, Object>> list) {
        this.list = list;
    }

    @Override
    public void run() {
        if (ImproveOptions.doJoinHelpImprovePlan().equals("true")) {
            JSONObject statisticsObject = new JSONObject();
            statisticsObject.put("category", "logErrors");

            JSONObject statisticsDataObject = new JSONObject();

            /*
              隐私信息说明
              serverTime: 服务器当前时间戳
              serverHost：博客的服务端地址
              requestURL：访问者访问的URL地址
              clientIP：访问者的IP地址，去敏处理
              userAgent: 浏览器UA
              referer: 浏览器Referer
             */
            try {
                statisticsDataObject.put("serverTime", System.currentTimeMillis());
                statisticsDataObject.put("serverHost", Latkes.getStaticServePath());
            } catch (Exception ignored) {
                return;
            }

            statisticsObject.put("data", statisticsDataObject);

            CloseableHttpClient uploadSiteStatisticsHttpClient = PassSSL.createSSLClientDefault();
            HttpPost httpPost = new HttpPost(helperHost);
            String params = statisticsObject.toString();
            StringEntity httpEntity = new StringEntity(params, "utf-8");
            RequestConfig config = RequestConfig.custom().setConnectTimeout(2000).setConnectionRequestTimeout(1000).setSocketTimeout(2000).build();
            httpPost.setConfig(config);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(httpEntity);

            try {
                CloseableHttpResponse response = uploadSiteStatisticsHttpClient.execute(httpPost);
                response.close();
            } catch (IOException ignored) {
            }
        }
    }
}
