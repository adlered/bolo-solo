package org.b3log.solo.bolo.pic;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.b3log.latke.servlet.HttpMethod;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.solo.util.Solos;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h3>bolo-solo</h3>
 * <p>图床上传</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2020-03-04 20:50
 **/
@RequestProcessor
public class PicUploadProcessor {
    @RequestProcessing(value = "/pic/upload", method = {HttpMethod.POST})
    public void uploadPicture(final RequestContext context) {
        if (!Solos.isAdminLoggedIn(context)) {
            context.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(new File("temp/"));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        Map okPic = new HashMap();
        try {
            List<FileItem> itemList = upload.parseRequest(context.getRequest());
            for (FileItem item : itemList) {
                String name = item.getName();
                String value = item.getString("UTF-8");
                System.out.println("name="+name+"  value="+value);
                String url = "https://pic.stackoverflow.wiki/uploadImages/114/244/228/172/2020/03/04/21/53/7d0fb26a-cc23-454b-b4b3-5bd58f5ed292.jpg";
                okPic.put(name, url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map map = new HashMap();
        map.put("succMap", okPic);
        map.put("errFiles", new ArrayList<>());
        context.renderJSON().renderData(map);
        context.renderCode(0);
        context.renderMsg("");
    }
}
