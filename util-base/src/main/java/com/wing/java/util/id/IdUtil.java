package com.wing.java.util.id;

import java.util.UUID;

/**
 * @author wing
 * @create 2019-09-03 14:03
 */
public class IdUtil {
    /**
     * 随机获取UUID字符串(无中划线)
     */
    public static String getUUID32() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
    }

    /**
     * 随机获取UUID字符串(有中划线)
     */
    public static String getUUID36() {
        return UUID.randomUUID().toString();
    }


    /**
     * 主机和进程的机器码
     */
    private static final IdSequence worker = new IdSequence();

    public static long getId() {
        return worker.nextId();
    }

    public static String getIdStr() {
        return String.valueOf(worker.nextId());
    }


}
