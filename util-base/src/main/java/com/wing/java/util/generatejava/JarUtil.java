package com.wing.java.util.generatejava;

import com.wing.java.util.file.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成jar包
 */
public class JarUtil {
	/**
	 * 使用sun.tools.jar生成jar包
	 */
	public static boolean doPack(String tableName) {
		String classDir = GenerateJavaConstant.getEntityClassDir(tableName);

		String jarDir = GenerateJavaConstant.getEntityJarDir(tableName);
		FileUtil.mkDir(jarDir);

		List<String> argList = new ArrayList<String>();
		argList.add("cvfn");
		argList.add(GenerateJavaConstant.getEntityJarFile(tableName));
		argList.add("-C");
		argList.add(classDir);
		argList.add(".");

		String[] jarArgs = new String[argList.size()];
		argList.toArray(jarArgs);

		sun.tools.jar.Main jarTool = new sun.tools.jar.Main(System.out, System.err, "JarCreator");
		return jarTool.run(jarArgs);
	}

	public static void main(String[] args) {
		boolean b = JarUtil.doPack("sys_user");
		System.out.println(b);
	}
}
