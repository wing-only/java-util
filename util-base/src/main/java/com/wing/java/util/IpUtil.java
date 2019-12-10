package com.wing.java.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class IpUtil {

    /*
     * 随机生成国内IP地址
     */
    public static String getRandomIp() {

        //ip范围
        int[][] range = {{607649792, 608174079},//36.56.0.0-36.63.255.255
                {1038614528, 1039007743},//61.232.0.0-61.237.255.255
                {1783627776, 1784676351},//106.80.0.0-106.95.255.255
                {2035023872, 2035154943},//121.76.0.0-121.77.255.255
                {2078801920, 2079064063},//123.232.0.0-123.235.255.255
                {-1950089216, -1948778497},//139.196.0.0-139.215.255.255
                {-1425539072, -1425014785},//171.8.0.0-171.15.255.255
                {-1236271104, -1235419137},//182.80.0.0-182.92.255.255
                {-770113536, -768606209},//210.25.0.0-210.47.255.255
                {-569376768, -564133889}, //222.16.0.0-222.95.255.255
        };

        Random rdint = new Random();
        int index = rdint.nextInt(10);
        String ip = num2ip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
        return ip;
    }

    /*
     * 将十进制转换成ip地址
     */
    public static String num2ip(int ip) {
        int[] b = new int[4];
        String x = "";

        b[0] = (int) ((ip >> 24) & 0xff);
        b[1] = (int) ((ip >> 16) & 0xff);
        b[2] = (int) ((ip >> 8) & 0xff);
        b[3] = (int) (ip & 0xff);
        x = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + Integer.toString(b[2]) + "." + Integer.toString(b[3]);

        return x;
    }

    //投票方法
    public static String vote(int count) {
        String reqUrl = "http://vote6.haobrand.cn/zan.php";
        String reqDate = "id=630";

        HttpURLConnection connection = null;
        OutputStream outputStream = null;
        StringBuilder sb = new StringBuilder();
        String line = null;
        BufferedReader br = null;

        for (int i = 0; i < count; i++) {
            String ip = getRandomIp();

//			System.getProperties().setProperty("proxySet", "true"); 
//			System.getProperties().setProperty("http.proxyHost", "221.10.102.203");
//			System.getProperties().setProperty("http.proxyPort", "8888");

            try {
                URL url = new URL(reqUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);

                connection.setRequestMethod("POST");

                connection.setRequestProperty("Connection", "keep-alive");
                connection.setRequestProperty("Origin", "http://vote6.haobrand.cn");//添加一个请求链接
                connection.setRequestProperty("Referer", "http://vote6.haobrand.cn/plus/jilu.php?action=view&diyid=1&id=630");//添加一个请求链接
                connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");//添加一个请求链接
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("Accept", "*/*");
                connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
                connection.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0");

                //关键代码 php 防止ip重复提交
                connection.setRequestProperty("CLIENT-IP", ip);
                connection.setRequestProperty("X-Forwarded-For", ip);

                connection.connect();

                byte[] btyeAry = reqDate.getBytes("utf-8");
                outputStream = connection.getOutputStream();
                outputStream.write(btyeAry);
                outputStream.flush();

                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = br.readLine()) != null) {
                    System.err.println(line);
                    sb.append(line + "\r\n success \r\n");
//	                fr.write(line);
                }
            } catch (Exception e) {
                System.err.println("exception...");
                sb.append("exception... \r\n");
                e.printStackTrace();
            } finally {
                try {
//					fr.close();
                    br.close();
                    connection.disconnect();
                } catch (Exception ex) {
                }
            }

            if (count > 1 && i != (count - 1)) {
                //线程睡一会
                int max = 1800000;        //30分钟
                int min = 300000;            //5分钟
                Random random = new Random();
                int ms = random.nextInt(max) % (max - min + 1) + min;

                try {
                    Thread.sleep(ms);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        return sb.toString();
    }


    public static void main(String[] args) throws Exception {
//		String reqUrl = "http://www.codoon.com/activity/node/customer/cityrunleader/user_info";
//		String reqDate = "open_id=3778750";

        String reqUrl = "http://www.codoon.com/activity/node/customer/cityrunleader?open_id=";

//		String reqUrl = "http://www.codoon.com/activity/node/customer/cityrunleader/vote";
//		String reqDate = "open_id=3910306&index=10&id=10";


        HttpURLConnection connection = null;
        OutputStream outputStream = null;
        StringBuilder sb = new StringBuilder();
        String line = null;
        BufferedReader br = null;

        for (int i = 0; i < 1; i++) {

            try {
                URL url = new URL(reqUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);

                connection.setRequestMethod("GET");

                connection.setRequestProperty("Connection", "keep-alive");
                connection.setRequestProperty("Origin", "http://www.codoon.com");//添加一个请求链接
                connection.setRequestProperty("Referer", "http://www.codoon.com/activity/node/customer/cityrunleader");//添加一个请求链接
                connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");//添加一个请求链接
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("Accept", "*/*");
                connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
                connection.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0");

                connection.connect();

//				byte[] btyeAry = reqDate.getBytes("utf-8");
//				outputStream = connection.getOutputStream();
//				outputStream.write(btyeAry);
//				outputStream.flush();


                br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                while ((line = br.readLine()) != null) {
                    System.err.println(line);
                }


            } catch (Exception e) {
                System.err.println("异常了");
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                    connection.disconnect();
                } catch (Exception ex) {
                }
            }
        }
    }

}
