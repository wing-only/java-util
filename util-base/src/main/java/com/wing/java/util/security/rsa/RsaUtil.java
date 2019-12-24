package com.wing.java.util.security.rsa;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 非对称加密算法RSA算法组件
 */
public class RsaUtil {

    /**
     * 密钥长度，DH算法的默认密钥长度是1024
     * 密钥长度必须是64的倍数，在512到65536位之间
     */
    private static final int KEY_SIZE = 1024;
    public static final String KEY_ALGORITHM = "RSA";               //非对称密钥算法
    private static final String PUBLIC_KEY = "RSAPublicKey";         //公钥
    private static final String PRIVATE_KEY = "RSAPrivateKey";       //私钥
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 初始化密钥对
     */
    public static Map<String, Object> initKey() throws Exception {
        //实例化密钥生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //初始化密钥生成器
        keyPairGenerator.initialize(KEY_SIZE);
        //生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //甲方公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //甲方私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //将密钥存储在map中
        Map<String, Object> keyMap = new HashMap<String, Object>();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }


    /**
     * 私钥加密
     * @param data 待加密数据
     * @param privateKeyStr  密钥
     * @return byte[] 加密数据
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKeyStr) throws Exception {
        //取得私钥
        byte[] key = Base64.decodeBase64(privateKeyStr);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 公钥加密
     * @param data 待加密数据
     * @param publicKeyStr  密钥
     * @return byte[] 加密数据
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKeyStr) throws Exception {
        byte[] key = Base64.decodeBase64(publicKeyStr);

        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

        //数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     * @param data 待解密数据
     * @param privateKeyStr  密钥
     * @return byte[] 解密数据
     */
    public static byte[] decryptByPrivateKey(byte[] data, String privateKeyStr) throws Exception {
        byte[] key = Base64.decodeBase64(privateKeyStr);
        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 公钥解密
     * @param data 待解密数据
     * @param publicKeyStr  密钥
     * @return byte[] 解密数据
     */
    public static byte[] decryptByPublicKey(byte[] data, String publicKeyStr) throws Exception {
        byte[] key = Base64.decodeBase64(publicKeyStr);
        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        //数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥签名
     * @param data
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static byte[] sign(byte[] data, String privateKeyStr) throws Exception {
        byte[] key = Base64.decodeBase64(privateKeyStr);
//        byte[] key = (new BASE64Decoder()).decodeBuffer(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey priK = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priK);
        signature.update(data);
        return signature.sign();
    }

    /**
     * 公钥验签
     */
    public static boolean verify(byte[] data, byte[] sign, String publicKeyStr) throws Exception {
        byte[] key = Base64.decodeBase64(publicKeyStr);
//        byte[] key = (new BASE64Decoder()).decodeBuffer(publicKeyStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey pubK = keyFactory.generatePublic(keySpec);
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initVerify(pubK);
        sig.update(data);
        return sig.verify(sign);
    }

    /**
     * 取得私钥
     * @param keyMap 密钥map
     * @return byte[] 私钥
     */
    public static byte[] getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return key.getEncoded();
    }

    /**
     * 取得公钥
     * @param keyMap 密钥map
     * @return byte[] 公钥
     */
    public static byte[] getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }



    public static void main(String[] args) throws Exception {
        //初始化密钥,生成密钥对
        Map<String, Object> keyMap = RsaUtil.initKey();

        byte[] publicKey = RsaUtil.getPublicKey(keyMap);       //公钥
        byte[] privateKey = RsaUtil.getPrivateKey(keyMap);      //私钥
        String publicKeyStr = Base64.encodeBase64String(publicKey);
        String privateKeyStr = Base64.encodeBase64String(privateKey);

        System.out.println("公钥：" + publicKeyStr);
        System.out.println("私钥：" + privateKeyStr);


        System.out.println("======================== 私钥加密 公钥解密 =============================");

        String str = "这是张三，请确认";
        System.out.println("私钥加密前的明文:" + str);

        //私钥加密
        byte[] code1 = RsaUtil.encryptByPrivateKey(str.getBytes(), privateKeyStr);
        System.out.println("私钥加密后密文：" + Base64.encodeBase64String(code1));

        //公钥解密
        byte[] decode1 = RsaUtil.decryptByPublicKey(code1, publicKeyStr);
        System.out.println("公钥解密后的明文：" + new String(decode1));

        System.out.println("======================== 公钥加密 私钥解密 =============================");


        //公钥加密
        str = "我是李四，已收到";
        System.out.println("公钥加密前的明文:" + str);
        byte[] code2 = RsaUtil.encryptByPublicKey(str.getBytes(), publicKeyStr);
        System.out.println("公钥加密后的数据：" + Base64.encodeBase64String(code2));

        //私钥解密
        byte[] decode2 = RsaUtil.decryptByPrivateKey(code2, privateKeyStr);
        System.out.println("私钥解密后的明文：" + new String(decode2));


        System.out.println("======================== 私钥签名 公钥验签 =============================");

        //私钥签名
        String text = "数字签名，防篡改";
        System.out.println("被签名的内容:" + text);
        byte[] signature = sign(text.getBytes(), privateKeyStr);

        //公钥验签
        boolean status = verify(text.getBytes(), signature, publicKeyStr);
        System.out.println("公钥验证情况：" + status);
    }
}