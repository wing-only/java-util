package com.wing.java.util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	private static final String filePath = "config.properties";
	private final static Properties objProperties = new Properties(); // 属性对象

	static {
		try {
			load();
		} catch (Exception e) {
//			log.error("加载.properties文件出错", e);
		}
	}

	/**
	 * @name PropertiesUtil
	 * @title 构造函数
	 * @desc 加载属性资源文件
	 * @return
	 * @throws Exception
	 */
	public static void load() throws Exception {
		InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties");

		try {
			objProperties.load(inputStream);
		} catch (FileNotFoundException e) {
//			log.error("未找到属性资源文件!");
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
//			log.error("读取属性资源文件发生未知错误!");
			e.printStackTrace();
			throw e;
		} finally {
			inputStream.close();
		}
	}

	/**
	 * @name savefile
	 * @title 持久化属性文件
	 * @desc 使用setValue()方法后，必须调用此方法才能将属性持久化到存储文件中
	 * @param String
	 *            , String
	 * @return
	 * @throws Exception
	 */
	public static void savefile(String desc) throws Exception {
		FileOutputStream outStream = null;
		String realPath = PropertiesUtil.class.getClassLoader().getResource(
				filePath).getPath().toString();
		try {
			File file = new File(realPath);
			outStream = new FileOutputStream(file);
			objProperties.store(outStream, desc);// 保存属性文件
		} catch (Exception e) {
//			log.error("保存属性文件出错.");
			e.printStackTrace();
			throw e;
		} finally {
			outStream.close();
		}
	}

	/**
	 * @name getVlue
	 * @title 获取属性值
	 * @desc 指定Key值，获取value
	 * @param String
	 * @return String
	 */
	public static String getValue(String key) {
		PropertiesUtil.printAllVlue();
		return objProperties.getProperty(key);
	}

	/**
	 * @name getVlue
	 * @title 获取属性值,支持缺省设置
	 * @desc 重载getValue()方法；指定Key值，获取value并支持缺省值
	 * @param String
	 * @return String
	 */
	public static String getValue(String key, String defaultValue) {
		return objProperties.getProperty(key, defaultValue);
	}

	/**
	 * @name removeVlue
	 * @title 删除属性
	 * @desc 根据Key,删除属性
	 * @param String
	 * @return
	 */
	public static void removeValue(String key) {
		objProperties.remove(key);
	}

	/**
	 * @name setValue
	 * @title 设置属性
	 * @desc
	 * @param String
	 *            ,String
	 * @return
	 */
	public static void setValue(String key, String value) {
		objProperties.setProperty(key, value);
	}

	/**
	 * @name printAllVlue
	 * @title 打印所有属性值
	 * @desc
	 * @param
	 * @return
	 */
	public static void printAllVlue() {
		objProperties.list(System.out);
	}
	
	public static boolean getFlag(){
		String environment = getValue("push_environment");
//		log.debug("push_environment = " + environment);
		if("product".equals(environment)){
			return true;
		}else if("test".equals(environment)){
			return false;
		}else{
			return false;
		}
	}
	
	
	public static void main(String[] args) throws Exception {
        System.out.println("start");
		String userName = PropertiesUtil.getValue("USER_NAME");
		System.out.println(" end start");
		System.out.println(userName);
		System.out.println(" second start");
		PropertiesUtil.setValue("USER_NAME", "thinker");
		System.out.println(" second start");
		
		PropertiesUtil.savefile("");
		System.out.println(" second start");

		userName = PropertiesUtil.getValue("USER_NAME");
		System.out.println(userName);
		PropertiesUtil.printAllVlue();
	}
	
	
}