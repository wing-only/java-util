package com.wing.java.util.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/**
 * 签名 验签 工具类
 */
public class APISignatureUtil {

    private static Logger logger = LoggerFactory.getLogger(APISignatureUtil.class);

    /**
     * 获得签名（通过Map）
     */
    public static String signature(Map map, String privateKey) {
        byte[] md = null;
        try {
            md = MessageDigest.getInstance("MD5").digest((privateKey + getStr2Sign(map)).getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return byteToString(md);
    }

    public static String decrypt(String data, String key) {
        if (data == null) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf;
        byte[] bt = null;
        try {
            buf = decoder.decodeBuffer(data);
            bt = decrypt(buf, key.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String(bt);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象
        // 创建一个 DESKeySpec 对象，使用 key 中的前 8 个字节作为 DES 密钥的密钥内容
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); // 返回转换指定算法的秘密密钥的
        // SecretKeyFactory对象
        SecretKey securekey = keyFactory.generateSecret(dks);// 根据提供的密钥规范（密钥材料）生成
        // SecretKey 对象。
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密钥初始化Cipher对象 用于将 Cipher 初始化为解密模式的常量
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        // cipher.init(Cipher.ENCRYPT_MODE,securekey,sr);
        return cipher.doFinal(data);
    }


    public static String getStr2Sign(Map map) {
        Map treeMap = new TreeMap(map);
        StringBuffer result = new StringBuffer("");
        Set<Map.Entry<Object, Object>> set = treeMap.entrySet();
        Iterator<Map.Entry<Object, Object>> it = set.iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Object> obj = it.next();
            String key = obj.getKey() + "";
            String value = obj.getValue() + "";
            if (!"signature".equalsIgnoreCase(key) && value != null) {
                result.append(key).append("=").append(value).append("&");
            }
        }
        String resultstr = result.toString();
        if (resultstr.endsWith("&")) {
            resultstr = resultstr.substring(0, resultstr.length() - 1);
        }
        return resultstr;
    }

    //签名字符串
    public static String byteToString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            String tmp = Integer.toHexString(b[i] & 0xFF);
            if (tmp.length() == 1) {
                result += "0" + tmp;
            } else {
                result += tmp;
            }
        }
        logger.debug("服务端签名：" + result);
        return result;
    }


    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("billid", "20170717174538890713727483704836");
        map.put("orderid", "201706081230");
        map.put("title", "青岛啤酒");
        map.put("amount", "5");
        map.put("merchantid", "1001");
        map.put("period", "30"); // @2017#%
        System.out.println(signature(map, "@2017#%"));
    }
}
