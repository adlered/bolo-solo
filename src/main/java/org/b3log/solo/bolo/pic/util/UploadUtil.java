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
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
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
                CloseableHttpClient httpClient = HttpClients.createDefault();
                try {
                    HttpPost httpPost = new HttpPost(site + "/upload");
                    FileBody bin = new FileBody(file);
                    StringBody comment = new StringBody("file", ContentType.TEXT_PLAIN);
                    HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", bin).addPart("comment", comment).build();
                    httpPost.setEntity(reqEntity);
                    System.out.println("executing request " + httpPost.getRequestLine());

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
}
