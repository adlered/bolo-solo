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
package org.b3log.solo.bolo.pic.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.upyun.RestManager;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * <h3>bolo-solo</h3>
 * <p>图片上传</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2020-03-06 14:35
 **/
public class UploadUtil {
    public static String upload(String config, File file) throws Exception {
        String result = "";
        String type = config.split("<<>>")[0];
        switch (type) {
            case "picuang":
                String site = config.split("<<>>")[1];
                CloseableHttpClient httpClient = createSSLClientDefault();
                try {
                    HttpPost httpPost = new HttpPost(site + "/upload");
                    FileBody bin = new FileBody(file);
                    StringBody comment = new StringBody("file", ContentType.TEXT_PLAIN);
                    HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", bin).addPart("comment", comment).build();
                    httpPost.setEntity(reqEntity);
                    CloseableHttpResponse response = httpClient.execute(httpPost);
                    try {
                        if (response.getStatusLine().getStatusCode() == 200) {
                            HttpEntity resEntity = response.getEntity();
                            String str = EntityUtils.toString(resEntity);
                            EntityUtils.consume(resEntity);
                            JSONObject jsonObject = new JSONObject(str);
                            result = site + (String) jsonObject.get("msg");
                        } else {
                            throw new NullPointerException();
                        }
                    } finally {
                        response.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        httpClient.close();
                    } catch (IOException IOE) {
                        IOE.printStackTrace();
                    }
                }
                break;
            case "qiniu":
                Configuration cfg = new Configuration(Region.autoRegion());
                UploadManager uploadManager = new UploadManager(cfg);
                String accessKey = config.split("<<>>")[1];
                String secretKey = config.split("<<>>")[2];
                String bucket = config.split("<<>>")[3];
                String domain = config.split("<<>>")[4];
                String treaty = config.split("<<>>")[5];
                String localFilePath = file.getAbsolutePath();
                String key = null;

                Auth auth = Auth.create(accessKey, secretKey);
                String upToken = auth.uploadToken(bucket);
                try {
                    Response response = uploadManager.put(localFilePath, key, upToken);
                    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                    result = treaty + "://" + domain + "/" + putRet.key;
                } catch (QiniuException e) {
                    throw new NullPointerException();
                }
                break;
            case "aliyun":
                String accessKeyID = config.split("<<>>")[1];
                String accessKeySecret = config.split("<<>>")[2];
                String endPoint = config.split("<<>>")[3];
                String bucketName = config.split("<<>>")[4];
                String bucketDomain = config.split("<<>>")[5];
                String aliTreaty = config.split("<<>>")[6];
                String filename = RandomStringUtils.randomAlphanumeric(10);

                OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyID, accessKeySecret);
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filename, file);
                try {
                    ossClient.putObject(putObjectRequest);
                    ossClient.shutdown();
                    result = aliTreaty + "://" + bucketDomain + "/" + filename;
                } catch (OSSException| ClientException e) {
                    throw new NullPointerException();
                }
                break;
            case "upyun":
                String zoneName = config.split("<<>>")[1];
                String name = config.split("<<>>")[2];
                String pwd = config.split("<<>>")[3];
                String upDomain = config.split("<<>>")[4];
                String upTreaty = config.split("<<>>")[5];
                String filenm = RandomStringUtils.randomAlphanumeric(10);

                RestManager manager = new RestManager(zoneName, name, pwd);
                manager.setApiDomain(RestManager.ED_AUTO);
                Map<String, String> params = new HashMap<String, String>();
                manager.writeFile("/" + filenm, file, params);
                result = upTreaty + "://" + upDomain + "/" + filenm;
                break;
        }
        file.delete();
        return result;
    }

    /**
     * 设置可访问https
     * @return
     */
    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }
}
