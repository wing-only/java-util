package com.wing.java.util.file;

import java.io.File;

/**
 * @author wing
 * @create 2019-08-10 18:22
 */
public class FileUtil {

    /**
     * 创建目录
     * @param file
     * @return
     */
    public static boolean mkDir(String file) {
        File f = new File(file);
        boolean flag = true;
        if (!f.exists()) {
            flag = f.mkdirs();
        }
        return flag;
    }

}
