package com.wing.java.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

/**
 * 文件上传工具类
 */
public class UploadUtil {

    public static String postFile(String url, Map<String, Object> param, File file) throws IOException {
        String res = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(getMutipartEntry(param, file));
        CloseableHttpResponse response = httpClient.execute(httppost);
        HttpEntity entity = response.getEntity();
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            res = EntityUtils.toString(entity, "UTF-8");
            response.close();
        } else {
            res = EntityUtils.toString(entity, "UTF-8");
            response.close();
            throw new IllegalArgumentException(res);
        }
        return res;
    }

    private static MultipartEntity getMutipartEntry(Map<String, Object> param, File file) throws UnsupportedEncodingException {
        if (file == null) {
            throw new IllegalArgumentException("file is not exists");
        }
        FileBody fileBody = new FileBody(file);
        FormBodyPart filePart = new FormBodyPart("file", fileBody);
        MultipartEntity multipartEntity = new MultipartEntity();
        multipartEntity.addPart(filePart);

        Iterator<String> iterator = param.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            FormBodyPart field = new FormBodyPart(key, new StringBody((String) param.get(key)));
            multipartEntity.addPart(field);

        }
        return multipartEntity;
    }
}