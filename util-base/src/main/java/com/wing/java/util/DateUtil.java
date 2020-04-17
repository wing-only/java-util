package com.wing.java.util;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 */
public class DateUtil {
	public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String yyyy_MM = "yyyy-MM";
	public static final String MM_DD = "MM-dd";
	public static final String yyyy = "yyyy";
	public static final String MM = "MM";
	public static final String dd = "dd";
	public static final String HH_mm_ss = "HH:mm:ss";
	public static final String HH_mm = "HH:mm";
	public static final String mm_ss = "mm:ss";
	public static final String yyyyMMddHHmmssS = "yyyyMMddHHmmssS";
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String yyMMddHHmmss = "yyMMddHHmmss";
	public static final String yyyyMMdd = "yyyyMMdd";
	public static final String MMddHHmmssS = "MMddHHmmssS";
	public static final String MMddHHmmss = "MMddHHmmss";
	public static final String HHmmssS = "HHmmssS";
	public static final String HHmmss = "HHmmss";


//	======================================= 格式转换 =======================================

	/**
	 * 根据指定格式转换指定日期
	 */
	public static String date2String(Date date, String format) {
		if (date == null) {
			date = new Date();
		}
		String dateStr = null;
		if (date != null) {
			dateStr = new SimpleDateFormat(format).format(date);
		}
		return dateStr;
	}

	/**
	 * 日期字符串转换为日期
	 */
	public static Date string2Date(String dateStr, String format) throws ParseException {
		Date date = null;
		date = new SimpleDateFormat(format).parse(dateStr);
		return date;
	}

	/**
	 * 转换旧的日期字符串为新的日期字符串
	 */
	public static String convertDateString(String dateStr, String oldFormat, String newFormat) throws ParseException {
		Date date = null;
		date = new SimpleDateFormat(oldFormat).parse(dateStr);

		String outDateStr = new SimpleDateFormat(newFormat).format(date);
		return outDateStr;
	}
	
	
	/**
	 * 根据参数格式 返回当前日期
	 */
	public static String getNow(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	/**
	 * 日期加减
	 */
	public static Date operateDate(int addDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, addDay);
		return cal.getTime();
	}

	/**
	 * 日期加减 + 格式化
	 */
	public static String operateDate(int addDay, String format) {
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(new Date());
		 cal.add(Calendar.DAY_OF_MONTH, addDay);
		 return new SimpleDateFormat(format).format(cal.getTime());
	}

	/**
	 * 日期加减，当前时间，根据单位
	 */
	public static Date operateDate(int add, TimeUnit timeUnit) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(timeUnit.getUnit(), add);
		return cal.getTime();
	}

	/**
	 * 日期加减, 指定时间，根据单位
	 */
	public static Date operateDate(Date currentDate, int add, TimeUnit timeUnit) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(timeUnit.getUnit(), add);
		return cal.getTime();
	}

	/**
	 * 获取一个月前的日期
	 */
	public static String getLastMonth(String format){
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		cl.add(Calendar.MONTH, -1);
		return new SimpleDateFormat(format).format(cl.getTime());
	}
	
	/**
	 * 获取前半年日期
	 */
	public static String getLastHalfYear(String format){
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		cl.add(Calendar.MONTH, -6);
		return new SimpleDateFormat(format).format(cl.getTime());
	}
	
	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	
	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}

	
	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DATE);
	}

	/**
	 * 获取当前年份
	 */
	public static int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 获取当前月份
	 */
	public static int getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取当前月的某一天
	 * @return
	 */
	public static Date getDayOfCurrentMonth(int dayOfMonth) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		return c.getTime();
	}

	/**
	 * 获取当前日期
	 */
	public static int getCurrentDay() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DATE);
	}

	/**
	 * 获取当前小时
	 */
	public static int getCurrenHour() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获得当前分钟
	 */
	public static int getCurrenMinute() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MINUTE);
	}

	/**
	 * 获取当前秒
	 */
	public static int getCurrenSecond() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.SECOND);
	}

	/**
	 * 获得当前年的第几周
	 */
	public static int getCurrenWeekOfYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获得当前月的第几周
	 */
	public static int getCurrenWeekOfMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 获取当前周的某一天
	 * @return
	 */
	public static Date getDayOfCurrenWeek(int dayOfWeek) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		return c.getTime();
	}

	/**
	 * 获得当前年的第几天
	 */
	public static int getCurrenDayOfYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获得当前月总共多少天
	 */
	public static int getDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获得给定月总共多少天
	 */
	public static int getDayOfMonth(String str) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(string2Date(str, yyyy_MM));
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取最近30天 日期列表
	 */
	public static List<String> getLastMonthList(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Long currentTimeMillis = System.currentTimeMillis();
		String endDate = sdf.format(new Date(currentTimeMillis));
		String startDate = sdf.format(currentTimeMillis - 30 * 24 * 3600 * 1000L);

		List<String> al = new ArrayList<String>();

		SimpleDateFormat sdf2 = new SimpleDateFormat(yyyyMMdd);
		String endDate2 = sdf2.format(new Date(currentTimeMillis));
		String startDate2 = sdf2.format(currentTimeMillis - 30 * 24 * 3600 * 1000L);

		while (startDate2.compareTo(endDate2) < 1) {
			al.add(startDate);
			try {
				Long l = sdf.parse(startDate).getTime();
				startDate = sdf.format(l + 24 * 3600 * 1000L); // +1天

				Long l2 = sdf2.parse(startDate2).getTime();
				startDate2 = sdf2.format(l2 + 24 * 3600 * 1000L); // +1天
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return al;
	}

	/**
	 * 获取当前时间距离当天0点的秒数
	 * @param date
	 * @return
	 */
	public static int getSecondBetweenZero(Date date) {
		int hours = date.getHours();
		int minutes = date.getMinutes();
		int seconds = date.getSeconds();
		return hours * 60 * 60 + minutes * 60 + seconds;
	}

	/**
	 * 获取两个日期之间相差的秒数
	 * @param largeDate
	 * @param smallDate
	 * @return
	 */
	public static long getSecondBetweenTwoDate(Date largeDate, Date smallDate){
		return (largeDate.getTime() - smallDate.getTime()) / 1000;
	}

	/**
	 * 获取当天0点时间
	 * @return
	 */
	public static Date get0Point(){
		return get0Point(new Date());
	}

	/**
	 * 获取指定日期 0点时间
	 * @param currentDate
	 * @return
	 */
	public static Date get0Point(Date currentDate){
		return cn.hutool.core.date.DateUtil.beginOfDay(currentDate);
	}

	/**
	 * 获取当天 24点时间
	 * @return
	 */
	public static Date get24Point(){
		return get24Point(new Date());
	}

	/**
	 * 获取指定日期 24点时间
	 * @param currentDate
	 * @return
	 */
	public static Date get24Point(Date currentDate){
		return cn.hutool.core.date.DateUtil.endOfDay(currentDate);
	}

	/**
	 * 获取昨天0点
	 * @return
	 */
	public static Date getLastDay0Point(){
		return cn.hutool.core.date.DateUtil.beginOfDay(operateDate(-1));
	}

	/**
	 * 获取昨天23:59:59
	 * @return
	 */
	public static Date getLastDay24Point(){
		return cn.hutool.core.date.DateUtil.endOfDay(operateDate(-1));
	}

	public enum TimeUnit{
		DAY_OF_MONTH(Calendar.DAY_OF_MONTH, "天"),
		HOUR_OF_DAY(Calendar.HOUR_OF_DAY, "时"),
		MINUTE(Calendar.MINUTE, "分"),
		SECOND(Calendar.SECOND, "秒");

		TimeUnit(int unit, String name) {
			this.unit = unit;
			this.name = name;
		}

		int unit;
		String name;

		public int getUnit() {
			return unit;
		}

		public String getName() {
			return name;
		}
	}
}
