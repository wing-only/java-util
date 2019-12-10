package com.wing.java.util;

/**
 * @author wing
 * @create 2017-08-10 17:46
 */
public class StringUtil {

    /**
     * 把输入字符串的首字母改成大写
     *
     * @param str
     * @return
     */
    public static String transCapital(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    public static String transLower(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'A' && ch[0] <= 'Z') {
            ch[0] = (char) (ch[0] + 32);
        }
        return new String(ch);
    }

    public static String transCamel(String tablename) {
        tablename = tablename.toLowerCase();
        String[] s = tablename.split("_");
        StringBuffer sb = new StringBuffer();
        for (String str : s) {
            sb.append(transCapital(str));
        }
        return sb.toString();
    }




}
