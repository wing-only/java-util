package com.wing.java.util.generatejava;

import com.wing.java.util.StringUtil;
import java.io.File;

/**
 * @author wing
 * @create 2019-08-08 14:12
 */
public class GenerateJavaConstant {
    //动态生成类 配置
    public static final String DEFAULT_PACKAGE = "com.wing.model";
    public static String entityPath = "/home/wing/entity";

    public static String getEntityJavaDir(String tableName) {
        return entityPath + File.separator + "java" + File.separator + tableName.toLowerCase();
    }

    public static String getEntityClassDir(String tableName) {
        return entityPath + File.separator + "class" + File.separator + tableName.toLowerCase();
    }

    public static String getEntityJarDir(String tableName) {
        return entityPath + File.separator + "jar" + File.separator + tableName.toLowerCase();
    }

    public static String getEntityJarFile(String tableName) {
        return entityPath + File.separator + "jar" + File.separator + tableName.toLowerCase() + File.separator + StringUtil.transCamel(tableName) + ".jar";
    }


}
