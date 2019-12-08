package com.wing.java.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileSystemClassLoader extends ClassLoader {
    private String classpath;

    public FileSystemClassLoader(String classpath) {
        this.classpath = classpath;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] datas = getClassData(name);
            if (datas == null) {
                throw new ClassNotFoundException("类没有找到：" + name);
            }
            //解密！
            return this.defineClass(name, datas, 0, datas.length);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ClassNotFoundException("类找不到：" + name);
        }
    }

    private byte[] getClassData(String name) throws IOException {
        name = name.replace(".", File.separator) + ".class";
        File classFile = new File(classpath, name);

        try {
            FileInputStream is = new FileInputStream(classFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int b = 0;
            while ((b = is.read()) != -1) {
                baos.write(b);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
