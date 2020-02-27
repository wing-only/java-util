package com.wing.java.util.generatejava;

import com.wing.java.util.file.FileUtil;

import javax.tools.*;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * java源文件编译成class
 */
public class ClassUtil {

    public static Boolean complier(String tableName) throws Exception {
        String javaFilePath = GenerateJavaConstant.getEntityJavaDir(tableName);

        String classFilePath = GenerateJavaConstant.getEntityClassDir(tableName);
        FileUtil.mkDir(classFilePath);

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        // 获取编译器实例
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        // 获取标准文件管理器实例
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        try {
            //得到filePath目录下的所有java源文件
            File sourceFile = new File(javaFilePath);
            List<File> sourceFileList = new ArrayList<File>();
            getSourceFiles(sourceFile, sourceFileList);

            // 没有java文件，直接返回
            if (sourceFileList.size() == 0) {
                throw new Exception(javaFilePath + "目录下查找不到任何java文件");
            }

            // 获取要编译的编译单元
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(sourceFileList);

            /**
             * 编译选项，在编译java文件时，编译程序会自动的去寻找java文件引用的其他的java源文件或者class。
             * -sourcepath选项就是定义java源文件的查找目录， -classpath选项就是定义class文件的查找目录。
             */
            Iterable<String> options = Arrays.asList("-d", classFilePath, "-sourcepath", javaFilePath, "-source", "1.7", "-target", "1.7");
            JavaCompiler.CompilationTask compilationTask = compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits);

            // 运行编译任务
            Boolean call = compilationTask.call();
            if (!call) {
                List<Diagnostic<? extends JavaFileObject>> list = diagnostics.getDiagnostics();
                for (Diagnostic<? extends JavaFileObject> diagnostic : list) {
                    long lineNumber = diagnostic.getLineNumber();
                    String code = diagnostic.getCode();
                }
            }
            return call;
        } catch (Exception e) {
            throw new Exception("编译异常：" + classFilePath + javaFilePath, e);
        } finally {
            fileManager.close();
        }
    }

    private static void getSourceFiles(File sourceFile, List<File> sourceFileList) throws Exception {
        if (sourceFile.exists() && sourceFileList != null) {// 文件或者目录必须存在
            if (sourceFile.isDirectory()) {// 若file对象为目录
                // 得到该目录下以.java结尾的文件或者目录
                File[] childrenFiles = sourceFile.listFiles(new FileFilter() {
                    public boolean accept(File pathname) {
                        if (pathname.isDirectory()) {
                            return true;
                        } else {
                            String name = pathname.getName();
                            return name.endsWith(".java") ? true : false;
                        }
                    }
                });
                // 递归调用
                for (File childFile : childrenFiles) {
                    getSourceFiles(childFile, sourceFileList);
                }
            } else {// 若file对象为文件
                sourceFileList.add(sourceFile);
            }
        }
    }


    public static void main(String[] args) {
        String tableName = "sys_user";
        try {
            ClassUtil.complier(tableName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
