package com.wing.java.util;

import sun.misc.BASE64Decoder;

import java.io.IOException;

/**
 * @author wing
 * @create 2016-12-09 15:07
 */
public class Base64Util {

    private static final int CHUNK_SIZE = 76;
    private static final byte CHUNK_SEPARATOR[] = "\r\n".getBytes();
    private static final int BASELENGTH = 255;
    private static final int LOOKUPLENGTH = 64;
    private static final int EIGHTBIT = 8;
    private static final int SIXTEENBIT = 16;
    private static final int TWENTYFOURBITGROUP = 24;
    private static final int FOURBYTE = 4;
    private static final int SIGN = -128;
    private static final byte PAD = 61;
    private static byte base64Alphabet[];
    private static byte lookUpBase64Alphabet[];

    static {
        base64Alphabet = new byte[BASELENGTH];
        lookUpBase64Alphabet = new byte[LOOKUPLENGTH];
        int i;
        for (i = 0; i < BASELENGTH; i++)
            base64Alphabet[i] = -1;

        for (i = 90; i >= 65; i--)
            base64Alphabet[i] = (byte) (i - 65);

        for (i = 122; i >= 97; i--)
            base64Alphabet[i] = (byte) ((i - 97) + 26);

        for (i = 57; i >= 48; i--)
            base64Alphabet[i] = (byte) ((i - 48) + 52);

        base64Alphabet[43] = 62;
        base64Alphabet[47] = 63;
        for (i = 0; i <= 25; i++)
            lookUpBase64Alphabet[i] = (byte) (65 + i);

        i = 26;
        for (int j = 0; i <= 51; j++) {
            lookUpBase64Alphabet[i] = (byte) (97 + j);
            i++;
        }

        i = 52;
        for (int j = 0; i <= 61; j++) {
            lookUpBase64Alphabet[i] = (byte) (48 + j);
            i++;
        }

        lookUpBase64Alphabet[62] = 43;
        lookUpBase64Alphabet[63] = 47;
    }

    public Base64Util() {
    }

    private static boolean isBase64Char(byte octect) {
        if (octect < 0 || octect >= BASELENGTH)
            return false;
        if (octect == PAD)
            return true;
        return base64Alphabet[octect] != -1;
    }

    public static boolean isBase64(byte arrayOctect[]) {
        byte buf[] = discardWhitespace(arrayOctect);
        int length = buf.length;
        if (length == 0)
            return true;
        for (int i = 0; i < length; i++)
            if (!isBase64Char(buf[i]))
                return false;
        return true;
    }

    public static byte[] encode(byte binaryData[]) {
        return encode(binaryData, false);
    }

    public static String encode(String string) {
        return new String(encode(string.getBytes(), false));
    }

    public static byte[] encodeChunked(byte binaryData[]) {
        return encode(binaryData, true);
    }

    private static byte[] encode(byte binaryData[], boolean isChunked) {
        int lengthDataBits = binaryData.length * EIGHTBIT;
        int fewerThan24bits = lengthDataBits % TWENTYFOURBITGROUP;
        int numberTriplets = lengthDataBits / TWENTYFOURBITGROUP;
        byte encodedData[] = (byte[]) null;
        int encodedDataLength = 0;
        int nbrChunks = 0;
        if (fewerThan24bits != 0)
            encodedDataLength = (numberTriplets + 1) * FOURBYTE;
        else
            encodedDataLength = numberTriplets * FOURBYTE;
        if (isChunked) {
            nbrChunks = CHUNK_SEPARATOR.length != 0 ? (int) Math
                    .ceil((float) encodedDataLength / 76F) : 0;
            encodedDataLength += nbrChunks * CHUNK_SEPARATOR.length;
        }
        encodedData = new byte[encodedDataLength];
        byte k = 0;
        byte l = 0;
        byte b1 = 0;
        byte b2 = 0;
        byte b3 = 0;
        int encodedIndex = 0;
        int dataIndex = 0;
        int i = 0;
        int nextSeparatorIndex = CHUNK_SIZE;
        int chunksSoFar = 0;
        for (i = 0; i < numberTriplets; i++) {
            dataIndex = i * 3;
            b1 = binaryData[dataIndex];
            b2 = binaryData[dataIndex + 1];
            b3 = binaryData[dataIndex + 2];
            l = (byte) (b2 & 15);
            k = (byte) (b1 & 3);
            byte val1 = (b1 & SIGN) != 0 ? (byte) (b1 >> 2 ^ 192)
                    : (byte) (b1 >> 2);
            byte val2 = (b2 & SIGN) != 0 ? (byte) (b2 >> 4 ^ 240)
                    : (byte) (b2 >> 4);
            byte val3 = (b3 & SIGN) != 0 ? (byte) (b3 >> 6 ^ 252)
                    : (byte) (b3 >> 6);
            encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex + 1] = lookUpBase64Alphabet[val2 | k << 4];
            encodedData[encodedIndex + 2] = lookUpBase64Alphabet[l << 2 | val3];
            encodedData[encodedIndex + 3] = lookUpBase64Alphabet[b3 & 63];
            encodedIndex += 4;
            if (isChunked && encodedIndex == nextSeparatorIndex) {
                System.arraycopy(CHUNK_SEPARATOR, 0, encodedData, encodedIndex,
                        CHUNK_SEPARATOR.length);
                chunksSoFar++;
                nextSeparatorIndex = CHUNK_SIZE * (chunksSoFar + 1) + chunksSoFar
                        * CHUNK_SEPARATOR.length;
                encodedIndex += CHUNK_SEPARATOR.length;
            }
        }

        dataIndex = i * 3;
        if (fewerThan24bits == EIGHTBIT) {
            b1 = binaryData[dataIndex];
            k = (byte) (b1 & 3);
            byte val1 = (b1 & SIGN) != 0 ? (byte) (b1 >> 2 ^ 192)
                    : (byte) (b1 >> 2);
            encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex + 1] = lookUpBase64Alphabet[k << 4];
            encodedData[encodedIndex + 2] = PAD;
            encodedData[encodedIndex + 3] = PAD;
        } else if (fewerThan24bits == SIXTEENBIT) {
            b1 = binaryData[dataIndex];
            b2 = binaryData[dataIndex + 1];
            l = (byte) (b2 & 15);
            k = (byte) (b1 & 3);
            byte val1 = (b1 & SIGN) != 0 ? (byte) (b1 >> 2 ^ 192)
                    : (byte) (b1 >> 2);
            byte val2 = (b2 & SIGN) != 0 ? (byte) (b2 >> 4 ^ 240)
                    : (byte) (b2 >> 4);
            encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex + 1] = lookUpBase64Alphabet[val2 | k << 4];
            encodedData[encodedIndex + 2] = lookUpBase64Alphabet[l << 2];
            encodedData[encodedIndex + 3] = PAD;
        }
        if (isChunked && chunksSoFar < nbrChunks)
            System.arraycopy(CHUNK_SEPARATOR, 0, encodedData, encodedDataLength
                    - CHUNK_SEPARATOR.length, CHUNK_SEPARATOR.length);
        return encodedData;
    }

    public static byte[] decode(String data) {
        return decode(data.getBytes());
    }

    /**
     * 把编码后的数据解码
     *
     * @param data 编码后的数据
     * @return 字符数组  编码前的数据
     */
    public static byte[] decode(byte data[]) {
        byte base64Data[] = discardNonBase64(data);
        if (base64Data.length == 0)
            return new byte[0];
        int numberQuadruple = base64Data.length / FOURBYTE;
        byte decodedData[] = (byte[]) null;
        byte b1 = 0;
        byte b2 = 0;
        byte b3 = 0;
        byte b4 = 0;
        byte marker0 = 0;
        byte marker1 = 0;
        int encodedIndex = 0;
        int dataIndex = 0;
        int lastData;
        for (lastData = base64Data.length; base64Data[lastData - 1] == PAD; )
            if (--lastData == 0)
                return new byte[0];

        decodedData = new byte[lastData - numberQuadruple];
        for (int i = 0; i < numberQuadruple; i++) {
            dataIndex = i * FOURBYTE;
            marker0 = base64Data[dataIndex + 2];
            marker1 = base64Data[dataIndex + 3];
            b1 = base64Alphabet[base64Data[dataIndex]];
            b2 = base64Alphabet[base64Data[dataIndex + 1]];
            if (marker0 != PAD && marker1 != PAD) {
                b3 = base64Alphabet[marker0];
                b4 = base64Alphabet[marker1];
                decodedData[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
                decodedData[encodedIndex + 1] = (byte) ((b2 & 15) << 4 | b3 >> 2 & 15);
                decodedData[encodedIndex + 2] = (byte) (b3 << 6 | b4);
            } else if (marker0 == PAD)
                decodedData[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
            else if (marker1 == PAD) {
                b3 = base64Alphabet[marker0];
                decodedData[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
                decodedData[encodedIndex + 1] = (byte) ((b2 & 15) << 4 | b3 >> 2 & 15);
            }
            encodedIndex += 3;
        }

        return decodedData;
    }

    private static byte[] discardWhitespace(byte data[]) {
        byte groomedData[] = new byte[data.length];
        int bytesCopied = 0;
        for (int i = 0; i < data.length; )
            switch (data[i]) {
                default:
                    groomedData[bytesCopied++] = data[i];
                    // fall through

                case 9: // '\t'
                case 10: // '\n'
                case 13: // '\r'
                case 32: // ' '
                    i++;
                    break;
            }

        byte packedData[] = new byte[bytesCopied];
        System.arraycopy(groomedData, 0, packedData, 0, bytesCopied);
        return packedData;
    }

    private static byte[] discardNonBase64(byte data[]) {
        byte groomedData[] = new byte[data.length];
        int bytesCopied = 0;
        for (int i = 0; i < data.length; i++)
            if (isBase64Char(data[i]))
                groomedData[bytesCopied++] = data[i];

        byte packedData[] = new byte[bytesCopied];
        System.arraycopy(groomedData, 0, packedData, 0, bytesCopied);
        return packedData;
    }


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

    public static void main(String[] params) {
        System.out.println(new String(Base64Util.decode("MTkyLjE2OC4wLjE2")));
        System.out.println(Base64Util.encode("testpwd"));
    }

}
