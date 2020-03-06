package org.b3log.solo.bolo.pic.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

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
                CloseableHttpClient httpClient = HttpClients.createDefault();
                try {
                    HttpPost httpPost = new HttpPost("http://localhost:1212/upload");
                    FileBody bin = new FileBody(file);
                    StringBody comment = new StringBody("file", ContentType.TEXT_PLAIN);
                    HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", bin).addPart("comment", comment).build();
                    httpPost.setEntity(reqEntity);
                    System.out.println("executing request " + httpPost.getRequestLine());

                    CloseableHttpResponse response = httpClient.execute(httpPost);
                    try {
                        System.out.println(response.getStatusLine());
                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            System.out.println("Response content length: " + resEntity.getContentLength());
                        }
                        EntityUtils.consume(resEntity);
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
            default:
                break;
        }
        return result;
    }
}
