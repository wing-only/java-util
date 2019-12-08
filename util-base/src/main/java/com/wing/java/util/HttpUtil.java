package com.wing.java.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Http请求工具类
 */
public class HttpUtil {

    /**
     * GET请求
     *
     * @param urlStr
     * @return
     */
    public static String get(String urlStr) {
        HttpURLConnection connection = null;
        BufferedReader br = null;
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();

            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * POST请求
     *
     * @param urlStr
     * @param jsonStr
     * @return
     */
    public static String post(String urlStr, String jsonStr) {
        HttpURLConnection connection = null;
        OutputStream outputStream = null;
        BufferedReader br = null;
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.connect();

            byte[] btyeAry = jsonStr.getBytes("utf-8");
            outputStream = connection.getOutputStream();
            outputStream.write(btyeAry);
            outputStream.flush();

            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                connection.disconnect();
            } catch (Exception ex) {
            }
        }
        return sb.toString();
    }


}
