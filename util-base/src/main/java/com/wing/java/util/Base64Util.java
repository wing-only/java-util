package com.wing.java.util;

import sun.misc.BASE64Decoder;

import java.io.IOException;

/**
 * @author wing
 * @create 2019-12-09 15:07
 */
public class Base64Util {

    public static byte[] transBase64ToByte(String base64Img) {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imgByte = new byte[0];
        try {
            imgByte = decoder.decodeBuffer(base64Img);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgByte;
    }

}
