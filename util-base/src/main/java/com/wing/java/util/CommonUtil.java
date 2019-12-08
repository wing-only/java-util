package com.wing.java.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具类
 */
public class CommonUtil {
	
	/**
	 * 判断对象是否为空(null或元素为0)
	 */
	public static boolean isEmpty(Object pObj) {
		if (pObj == null)
			return true;
		if (pObj == "")
			return true;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return true;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return true;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否不为空（null,''）
	 */
	public static boolean isNotEmpty(Object pObj) {
		if (pObj == null)
			return false;
		if (pObj == "")
			return false;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return false;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return false;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return false;
			}
		}
		return true;
	}
	

	/**
	 * 获取指定长度的随机数字
	 */
	public static String getRandomNumber(int count){
		String S = "0123456789";
		Random r = new Random();
		String tmp ="";
		for (int i=0;i<count;i++){
			int index = r.nextInt(S.length());
			tmp += S.charAt(index);
		}
		return tmp;
	}

    /** 
    * 获得随机颜色
    */  
	public static String getRandomColor(int count){
		String S = "0123456789ABCDEF";
		Random r = new Random();
		String tmp ="";
		for (int i=0;i<count;i++){
			int index = r.nextInt(S.length());
			tmp += S.charAt(index);
		}
		return tmp;
	}
	
	
	/**
	 * 获取系统名称
	 */
	public static String getOSName(){
		return System.getProperties().getProperty("os.name");
	}
	
	/**
	 * 是否是window系统
	 */
	public static Boolean isWindows(){
		if (getOSName().toLowerCase().indexOf("window")>-1){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 是否linux系统
	 */
	public static Boolean isLinux(){
		if (getOSName().toLowerCase().indexOf("linux")>-1){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 根据正则表达式查找符合规则的子串
	 * @param regexp 正则表达式
	 * @param content 源字符串
	 * @return 返回子串
	 */
	public static List<String> findString(String regexp,String content){
		Pattern pattern = Pattern.compile(regexp);  
	    Matcher match = pattern.matcher(content); 
	    List<String> list = new ArrayList<String>();
	    while(match.find()){
	    	list.add(match.group());
	    }
		return list;
	}

	
}