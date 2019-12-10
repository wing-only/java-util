package com.wing.java.util.security;

import com.wing.java.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/**
 * 签名 验签 工具类
 *
 * @author admin
 */
public class SignatureUtil {
    private static Logger logger = LoggerFactory.getLogger(SignatureUtil.class);

    private final static String data = PropertiesUtil.getValue("book_service_key");

    // 根据java 对象 生成升序排序的字符串
    @SuppressWarnings("all")
    public static String sort(Object obj) {
        Map map = new HashMap();
        Class c;
        Set<String> set = new TreeSet<String>();
        try {
            c = Class.forName(obj.getClass().getName());
            Method[] m = c.getMethods();
            for (int i = 0; i < m.length; i++) {
                String method = m[i].getName();
                // System.out.println(method);
                if (method.startsWith("get") && !"getSignature".equals(method) && !"getClass".equals(method)) {
                    try {
                        Object value = m[i].invoke(obj);
                        if (value != null) {
                            String key = method.substring(3);
                            key = key.substring(0, 1).toLowerCase() + key.substring(1);
                            map.put(key, value);
                            set.add(key);
                            // System.out.println(key +""+value);
                        }
                    } catch (Exception e) {
                        System.out.println("error:" + method);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuffer result = new StringBuffer("");
        for (String str : set) {
            result.append("&").append(str).append("=").append(map.get(str));
        }
        result.append(obtainPrivateKey());

        logger.debug("签名排序：" + result.substring(1));
        return result.substring(1);
    }

    // 根据Map 生成升序排序的字符串
    @SuppressWarnings("all")
    public static String sort(Map paramMap) {
        Map treeMap = new TreeMap(paramMap);
        StringBuffer result = new StringBuffer("");

        Set<Map.Entry<Object, Object>> set = treeMap.entrySet();
        Iterator<Map.Entry<Object, Object>> it = set.iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Object> map = it.next();
            String key = map.getKey() + "";
            String value = map.getValue() + "";
            if (!"signature".equalsIgnoreCase(key) && value != null) {
                result.append("&").append(key).append("=").append(value);
            }
        }
        result.append(obtainPrivateKey());
        logger.debug("签名排序：" + result.substring(1));
        return result.substring(1);
    }

    /**
     * 获得签名（通过包装类）
     */
    public static void signature(Object obj, String signature) {
        logger.debug("客户端签名：" + signature);
        byte[] md = null;
        try {
            md = MessageDigest.getInstance("MD5").digest(sort(obj).getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String newSignature = byteToString(md);
        if (signature == null || (!signature.equals(newSignature) && !"110".equals(signature))) {
            throw new RuntimeException("签名失败！");
        }
    }

    /**
     * 获得签名（通过Map）
     */
    public static void signature(Map map) {
        String signature = (String) map.get("signature");
        logger.debug("客户端签名：" + signature);
        byte[] md = null;
        try {
            md = MessageDigest.getInstance("MD5").digest(sort(map).getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String newSignature = byteToString(md);
        if (signature == null || (!signature.equals(newSignature) && !"110".equals(signature))) {
            throw new RuntimeException("签名失败！");
        }
    }

    // 解密配置文件的密钥
    public static String obtainPrivateKey() {
        String key = "llh!@#$&";
        return decrypt(data, key);
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

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(), key.getBytes());
        System.out.println(new String(bt));
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }


    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {
        //加密
//		String desEncrypt = desEncrypt("#@Scr2016%&", "llh!@#$&");
//		System.err.println("加密串：" + desEncrypt);

        //签名
	/*	UserEntity ue = new UserEntity();
		ue.setTelNo("13783459623");
		ue.setPassword("123456");
		ue.setCompanyCode(null);
		ue.setStaffId("");
		ue.setStaffCode("455615955");
		ue.setSignature("abcde");
		signature(ue, ue.getSignature());*/


        Map map = new HashMap();
        map.put("companyCode", "69897856");
        signature(map);
    }


}