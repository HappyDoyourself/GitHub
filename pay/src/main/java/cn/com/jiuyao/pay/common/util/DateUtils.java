/**
 * DateUtils.java
 * 
 * Copyright(C)2008 Founder Corporation.
 * written by Founder Corp.
 */
package cn.com.jiuyao.pay.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * [类名]<br>
 * DateUtils<br>
 * [功能概要]<br>
 * <br>
 * <br>
 * [変更履歴]<br>
 * 2009-4-27 ver1.00 新建 zhaoxinsheng<br>
 * 
 * @author FOUNDER CORPORATION
 * @version 1.00
 */
public class DateUtils {

	static Integer[] weekDays = { 7, 1, 2, 3, 4, 5, 6 };

	private static SimpleDateFormat getDateParser(String pattern) {
		return new SimpleDateFormat(pattern);
	}

	public static String longToDate(Long time, String pattern) {
		if (time != null)
			return getDateParser(pattern).format(new Date(time));
		else
			return "";
	}

	public static String longToDate(Long time) {
		return getDateParser("yyyy-MM-dd").format(new Date(time));
	}

	public static String longToDate2(Long time) {
		return getDateParser("MM-dd HH:mm").format(new Date(time));
	}

	public static String longToDateAll(Long time) {
		return getDateParser("yyyy-MM-dd HH:mm:ss").format(new Date(time));
	}

	public static String longToDateAllNew(Long time) {
		return getDateParser("yyyyMMddHHmmss").format(new Date(time));
	}

	public static String getCurrentDateString() {
		return getDateParser("yyyy-MM-dd HH:mm:ss").format(
				new Date(System.currentTimeMillis()));
	}

	/***
	 * String型日期转为long型
	 * 
	 * @param date
	 *            String型日期
	 * @return long 日期
	 * @throws ParseException
	 */
	public static long dateToLong(String source) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		try {
			return getDateParser("yyyy/MM/dd").parse(source).getTime();
		} catch (ParseException e) {
			try {
				return getDateParser("yyyy-MM-dd").parse(source).getTime();
			} catch (ParseException e1) {
				return -1;
			}
		}

	}

	public static long dateAddOneDayAndToLong(String source) {
		try {
			Date date = getDateParser("yyyy/MM/dd").parse(source);
			Calendar cd = Calendar.getInstance();
			cd.setTime(date);
			cd.add(Calendar.DAY_OF_MONTH, 1);
			return cd.getTime().getTime();
		} catch (ParseException e) {
			try {
				Date date = getDateParser("yyyy-MM-dd").parse(source);
				Calendar cd = Calendar.getInstance();
				cd.setTime(date);
				cd.add(Calendar.DAY_OF_MONTH, 1);
				return cd.getTime().getTime();
			} catch (ParseException e1) {
				return -1;
			}
		}
	}

	public static long nextDate(String source) {
		try {
			return getDateParser("yyyy/MM/dd").parse(source).getTime() + 24
					* 60 * 60 * 1000;
		} catch (ParseException e) {
			try {
				return getDateParser("yyyy-MM-dd").parse(source).getTime() + 24
						* 60 * 60 * 1000;
			} catch (ParseException e1) {
				return -1;
			}
		}

	}

	public static String longToFrontDate(Long time) {
		return getDateParser("yyyy-MM-dd").format(
				new Date(time - 24 * 60 * 60 * 1000));
	}

	/***
	 * String型日期转为long型
	 * 
	 * @param date
	 *            String型日期
	 * @return long 日期
	 * @throws ParseException
	 */
	public static long dateAllToLong(String source) {
		try {
			return getDateParser("yyyy/MM/dd HH:mm:ss").parse(source).getTime();
		} catch (ParseException e) {
			try {
				return getDateParser("yyyy-MM-dd HH:mm:ss").parse(source)
						.getTime();
			} catch (ParseException e1) {
				return -1;
			}
		}

	}

	/**
	 * 生成流水号
	 * 
	 * @return 类似"20090507095515693"的字符串(15位)
	 */
	public static long genSerialNumber() {
		return Long
				.valueOf(getDateParser("yyyyMMddHHmmsss").format(new Date()));
	}

	/**
	 * 生成流水号
	 * 
	 * @return 类似"2009050709551569"的字符串(14位)
	 */
	public static long gen14SerialNumber() {
		return Long.valueOf(getDateParser("yyyyMMddHHmmss").format(new Date()));
	}

	public static long genYMD() {
		return Long.valueOf(getDateParser("yyyyMMdd").format(new Date()));
	}

	/**
	 * 获得日期
	 */
	public static Date getDate(String birthday) {
		return new Date(dateToLong(birthday));
	}

	/**
	 * 获取日期字符
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式 如:yyy-MM-dd
	 * @return
	 */
	public static String getDate(Date date, String format) {
		return getDateParser(format).format(date);
	}

	public static Date toDate(String dataString, String format)
			throws ParseException {
		return getDateParser(format).parse(dataString);
	}

	/**
	 * 获得当前日期
	 * 
	 * @return
	 */
	public static long getNowDate() {
		return DateUtils.dateToLong(getDateParser("yyyy-MM-dd").format(
				new Date()));
	}

	/**
	 * 获得当前日期
	 * 
	 * @return
	 */
	public static long getNowDate(Date curDate) {
		return DateUtils
				.dateToLong(getDateParser("yyyy-MM-dd").format(curDate));
	}

	/**
	 * 获取当前时间加上任意天数后的日期
	 * 
	 * @param dayNum
	 *            天数
	 * @return
	 */
	public static String getNewDateByAdd(int dayNum) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, dayNum);
		return (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime());
	}

	public static String getNewDateByAdd(int dayNum, String format) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, dayNum);
		return (new SimpleDateFormat(format)).format(cal.getTime());
	}

	public static Date getDateByAdd(Date date, int num, int timeType) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(timeType, num);
		return cal.getTime();
	}

	/**
	 * 获取指定时间加上任意小时后的日期
	 * 
	 * @param dayNum
	 *            天数
	 * @return
	 */
	public static String getNewDateByAddHour(int hour) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hour);
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(cal
				.getTime());
	}

	/**
	 * @获取当前月份最大天数
	 * @author lidong
	 * @return 当前月份最大天数
	 * @2011-12-26
	 */
	public static int lastDayOfMonth() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return value;
	}

	/**
	 * 得到有效期天数
	 * 
	 * @param days
	 * @return
	 */
	public static Date addDays(long days) {
		Date date = new Date();
		return new Date(date.getTime() + days * 24 * 60 * 60 * 1000);
	}

	public static Date addDays(Date date, long days) {
		return new Date(date.getTime() + days * 24 * 60 * 60 * 1000);
	}

	/**
	 * 获取某年某月的最后一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 最后一天
	 */
	public static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**
	 * 是否闰年
	 * 
	 * @param year
	 *            年
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	/**
	 * 获得下个月最后一天的日期
	 * 
	 * @return
	 */
	public String getNextMonthEnd(int da) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, da);// 加一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 月份相加
	 * 
	 * @return
	 */
	public String getNextMonth(int da) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, da);// 加一个月
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * @获取当前年份及往前5年的年份集合
	 * @author lidong
	 * @return 当前年份及往前5年的年份集合
	 * @2012-01-11
	 */
	public static List<Object> getYearList() {
		List<Object> yearList = new ArrayList<Object>();
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		for (int i = 0; i < 5; i++) {
			Map<String, Object> yearMap = new HashMap<String, Object>();
			yearMap.put("id", currentYear);
			yearMap.put("text", currentYear + "年");
			if (i == 0) {
				yearMap.put("selected", true);
			}
			yearList.add(yearMap);
			currentYear = currentYear - 1;
		}
		return yearList;
	}

	/**
	 * 获取当前星期
	 * 
	 * @return 星期几
	 */
	public static Integer getCurrentWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 当月第一天
	 * 
	 * @return
	 */
	public static String getFirstDayOfMonth(String formart) {
		Calendar cal = Calendar.getInstance();

		SimpleDateFormat datef = new SimpleDateFormat(formart);
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		Date beginTime = cal.getTime();

		return datef.format(beginTime);
	}

	public static long nextDate(String formart, String source)
			throws ParseException {
		return getDateParser(formart).parse(source).getTime() + 24 * 60 * 60
				* 1000;
	}

	public static String genHMS() {
		return getDateParser("HH:mm:ss").format(new Date());
	}

	public static String longToDate3(long time) {
		return getDateParser("MM月dd日").format(new Date(time));
	}

	/**
	 * 获取当前星期
	 * 
	 * @return 周几
	 */
	static String[] weekDayStr = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };

	public static String getCurrentWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekDayStr[w];
	}

	/**
	 * 将特定格式的字符串转成date
	 * 
	 * @param date
	 *            20131021 转成2013-19-21
	 * @throws ParseException
	 */
	public static String stringToDate(String date) throws ParseException {
		if (date.length() == 8) {
			date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
					+ date.substring(6, 8);
		}
		return date;
	}
	/**
	 * 获取前一天时间
	 * @param date 
	 * @param formart
	 * @return
	 */
	public static String getYesterdayStr(Date date,String formart){
		String dateStr = "";
		if (null!=date) {
			Long dateLong = date.getTime();
			Long yesterdayLong = dateLong - 24*60*60*1000;
			SimpleDateFormat sdf= new SimpleDateFormat(formart);
			dateStr = sdf.format(new Date(yesterdayLong));
		}
		System.out.println(dateStr);
		return dateStr;
		
	}
	public static void main(String[] args) {
		// String str = "";
		// try {
		// str =stringToDate("20130817");
		// System.out.println(str);
		//
		// System.out.println(DateUtils.longToDate(DateUtils.nextDate("yyyy-MM-dd",str)));
		// System.out.println(DateUtils.toDate("2013/10/21 21:00:00", "hh:mm"));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// String str = "20131117";
		// if(str.length()==8){
		// str =
		// str.substring(0,4)+"-"+str.substring(4,6)+"-"+str.substring(6,8);
		// System.out.println("2013/10/21 21:00:00".split(" ")[1]);
		// System.out.println(DateUtils.longToDate(DateUtils.dateToLong("2013/10/21 21:00:00"),"yyyy-MM-dd kk:mm"));
		// }
		//
		DateUtils.getYesterdayStr(new Date(),"yyyy-MM-dd");
		//System.out.println(DateUtils.getYesterdayStr(new Date());
		//24*60*60*1000

	}

}

