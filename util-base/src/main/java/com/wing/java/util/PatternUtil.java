package com.wing.java.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {

	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return [0-9]{5,9}
	 */
	public static boolean isMobileNOs(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern
					.compile("^((13[0-9])|(15[^4,\\D])|(14[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return [0-9]{5,9}
	 */
	public static boolean isMobileNO(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern
					.compile("^1\\d{10}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	public static boolean isMobileNO1(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern
					.compile("^\\d{11}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
}
