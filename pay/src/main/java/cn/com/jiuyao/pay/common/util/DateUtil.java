package cn.com.jiuyao.pay.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 日期工具类
 */
public class DateUtil {

	public DateUtil() {
		super();
	}

	protected final static Log _log = LogFactory.getLog(DateUtil.class);

	 /**
     * 时间转换成字符串
     *
     * @param date       时间
     * @param formatType 格式化类型
     * @return String
     */
    public static String date2String(Date date, String formatType) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(formatType);
        return sdf.format(date);
    }
	
	/**
	 * @param date
	 * @param format
	 *            日期格式
	 * @return String
	 */
	public static String DateToStr(Date date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Date 转 String 类型失败: " + e);
			}
			return null;
		}
	}

	/**
	 * @param date
	 * @return String yyyy-MM-dd
	 */
	public static String DateToStr(Date date) {
		return DateToStr(date, "yyyy-MM-dd");
	}

	/**
	 * @param Date
	 *            date
	 * @return String yyyyMMdd
	 */
	public static String dateToStr(Date date) {
		return DateToStr(date, "yyyyMMdd");
	}

	/**
	 * @param date
	 * @return String yyyy-MM-dd HH:mm:ss
	 */
	public static String DateTimeToStr(Date date) {
		return DateToStr(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @param date
	 * @return String yyyyMMddHHmmss
	 */
	public static String dateTimeToStr(Date date) {
		return DateToStr(date, "yyyyMMddHHmmss");
	}

	/**
	 * @param date
	 * @return String HH:mm:ss
	 */
	public static String TimeToStr(Date date) {
		return DateToStr(date, "HH:mm:ss");
	}

	/**
	 * @param date
	 * @return String HHmmss
	 */
	public static String timeToStr(Date date) {
		return DateToStr(date, "HHmmss");
	}

	/**
	 * @param date
	 * @return String yyyy
	 */
	public static String YearToStr(Date date) {
		return DateToStr(date, "yyyy");
	}

	/**
	 * @param date
	 * @return int year
	 */
	public static int YearToInt(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * @param date
	 * @return String MM
	 */
	public static String MonthToStr(Date date) {
		return DateToStr(date, "MM");
	}

	/**
	 * @param date
	 * @return int month
	 */
	public static int MonthToInt(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * @param date
	 * @return int month
	 */
	public static int HourToInt(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR);
	}
	
	public static int MiniteToInt(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}
	
	public static int SecondToInt(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.SECOND);
	}

	/**
	 * @param date
	 * @return String dd
	 */
	public static String DayToStr(Date date) {
		return DateToStr(date, "dd");
	}

	/**
	 * @param date
	 * @return int day
	 */
	public static int DayToInt(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DATE);
	}

	/**
	 * @param date
	 * @return String yyyy-MM
	 */
	public static String YearMonthToStr(Date date) {
		return DateToStr(date, "yyyy-MM");
	}

	/**
	 * @param date
	 * @return String yyyyMM
	 */
	public static String yearMonthToStr(Date date) {
		return DateToStr(date, "yyyyMM");
	}

	/**
	 * @param date
	 * @return String MM-dd
	 */
	public static String MonthDayToStr(Date date) {
		return DateToStr(date, "MM-dd");
	}

	/**
	 * @param date
	 * @return String MMdd
	 */
	public static String monthDayToStr(Date date) {
		return DateToStr(date, "MMdd");
	}

	/**
	 * @param date
	 * @return dd/MM/yyyy
	 */
	public static String DayMonthYearToStr(Date date) {
		return DateToStr(date, "dd/MM/yyyy");
	}
	
	/**
	 * @param date
	 * @return yyyy/MM/dd
	 */
	public static String YearMonthDayToStr(Date date) {
		return DateToStr(date, "yyyy/MM/dd");
	}

	// ***************************************************************************

	/**
	 * @param sDate
	 * @param format
	 *            日期格式
	 * @return String
	 */
	public static Date StrToDate(String sDate, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(sDate);
		} catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("String 转 Date 类型失败: " + e);
			}
			return null;
		}
	}

	/**
	 * @param sDate
	 *            yyyy-MM-dd
	 * @return Date
	 */
	public static Date StrToDate(String sDate) {
		return StrToDate(sDate, "yyyy-MM-dd");
	}

	/**
	 * @param sDate
	 *            yyyyMMdd
	 * @return Date
	 */
	public static Date strToDate(String sDate) {
		return StrToDate(sDate, "yyyyMMdd");
	}

	/**
	 * @param sDateTime
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date SDateTimeToDate(String sDateTime) {
		return StrToDate(sDateTime, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @param sDateTime
	 *            yyyyMMddHHmmss
	 * @return
	 */
	public static Date sDateTimeToDate(String sDateTime) {
		return StrToDate(sDateTime, "yyyyMMddHHmmss");
	}

	// ***************************************************************************

	/**
	 * 日期相加
	 * 
	 * @param date
	 *            日期
	 * @param day
	 *            天数
	 * @return 返回相加后的日期
	 */
	public static Date addDate(Date date, int val) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.DATE, val);
		return gc.getTime();
	}

	/**
	 * 日期相减
	 * 
	 * @param date
	 *            日期
	 * @param val
	 *            天数
	 * @return 返回相减后的日期
	 */
	public static Date subDate(Date date, int val) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.DATE, -val);
		return gc.getTime();
	}

	public static Date addMonth(Date date, int val) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.MONTH, val);
		return gc.getTime();
	}
	
	public static Date addHour(Date date, int val) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.HOUR, val);
		return gc.getTime();
	}
	
	public static Date subHour(Date date, int val) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.HOUR, -val);
		return gc.getTime();
	}

	public static Date subMinute(Date date, int val) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.MINUTE, -val);
		return gc.getTime();
	}
	
	public static Date addMinute(Date date, int val) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.MINUTE, val);
		return gc.getTime();
	}
	
	public static Date addSencond(Date date, int val) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.SECOND, val);
		return gc.getTime();
	}
	
	public static Date addYear(Date date, int val) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.YEAR, val);
		return gc.getTime();
	}

	/**
	 * 根据 date 判断这一天是这一年的第几天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_YEAR);
	}

	// ***************************************************************************

	/**
	 * 计算某一月份的最大天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int sumDayByYearMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1); // 注意,Calendar对象默认一月为0
		return c.getActualMaximum(Calendar.DAY_OF_MONTH); // month 月份的天数
	}

	/**
	 * 得到某月的最后一天
	 * 
	 * @param sDate1
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(c.DATE, c.getActualMaximum(c.DAY_OF_MONTH));
		return c.getTime();
	}

	/**
	 * 得到某月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(c.YEAR, year);
		c.set(c.MONTH, month - 1);
		c.set(c.DATE, c.getActualMaximum(c.DAY_OF_MONTH));
		return c.getTime();
	}

	/**
	 * 得到某月的第一天
	 * 
	 * @param sDate1
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(c.DATE, c.getActualMinimum(c.DAY_OF_MONTH));
		return c.getTime();
	}

	/**
	 * 得到某月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(c.YEAR, year);
		c.set(c.MONTH, month - 1);
		c.set(c.DATE, c.getActualMinimum(c.DAY_OF_MONTH));
		return c.getTime();
	}
	
	public static void main(String[] args) {
		for (String sDate : getDateStrListOfMinutes("201106151702", "201106151802")) {
			System.out.println("==>> " + sDate);
		}
	}
	
	/**
	 * 
	 * @param startDate	: yyyyMMddHHmm
	 * @param endDate : yyyyMMddHHmm	
	 * @return List<yyyyMMddHHmm>
	 */
	public static List<String> getDateStrListOfMinutes(String startDate, String endDate) {
		Date sDate = StrToDate(startDate, "yyyyMMddHHmm");
		Date eDate = StrToDate(endDate, "yyyyMMddHHmm");
		
		List<String> sDateList = new ArrayList<String>();
		
		while (sDate.before(eDate)) {
			sDateList.add(DateToStr(sDate, "yyyyMMddHHmm"));
			sDate = DateUtils.addMinutes(sDate, 1);
			
			sDateList.add(DateToStr(sDate, "yyyyMMddHHmm"));
		}
		
		return sDateList;
	}
	
	public static List<Date> getDateListOfMinutes(Date startDate, Date endDate) {
		List<Date> dateList = new ArrayList<Date>();
		Date sdate = startDate;
		while (sdate.before(endDate)) {		
			sdate = DateUtils.addMinutes(sdate, 1);
			dateList.add(sdate);
		}
		return dateList;
	}

	/**
	 * 根据 开始日期 与 结束日期 得到它们之间的所有日期 包括 开始日期 与 结束日期
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<Date> getDateList(Date startDate, Date endDate) {
		Date tempDate = startDate;
		List<Date> dateList = new ArrayList();
		if (isSameDay(startDate, endDate)) {
			dateList.add(tempDate);
		} else {
			while (tempDate.before(endDate)) {
				dateList.add(tempDate);
				tempDate = DateUtils.addDays(tempDate, 1);
			}
			dateList.add(tempDate);
		}
		return dateList;
	}

	/**
	 * 根据 开始日期 与 结束日期 得到它们之间的所有日期 包括 开始日期 与 结束日期
	 * 
	 * @param startDate -
	 *            开始日期 yyyy-MM-dd
	 * @param endDate -
	 *            结束日期 yyyy-MM-dd
	 * @return List<yyyy-MM-dd>
	 */
	public static List<String> getDateToStrList(String startDate, String endDate) {
		return getDateList(startDate, endDate, "yyyy-MM-dd");
	}

	/**
	 * 根据 开始日期 与 结束日期 得到它们之间的所有日期 包括 开始日期 与 结束日期
	 * 
	 * @param startDate -
	 *            开始日期
	 * @param endDate -
	 *            结束日期
	 * @param format -
	 *            定义 startDate, endDate 及 返回数据 的格式
	 * @return
	 */
	public static List<String> getDateList(String startDate, String endDate, String format) {
		List<String> sDateList = new ArrayList<String>();
		Date periodDate = StrToDate(startDate, format);
		if (startDate.equals(endDate)) {
			sDateList.add(DateToStr(periodDate, format));
		} else {
			while (periodDate.before(StrToDate(endDate, format))) {
				sDateList.add(DateToStr(periodDate, format));
				periodDate = DateUtils.addDays(periodDate, 1);
			}
			sDateList.add(DateToStr(periodDate, format));
		}
		return sDateList;
	}

	/**
	 * 根据年月得到 month 月所有的日期
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static List<Date> getYearMonthAllDate(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		List<Date> dateList = new ArrayList<Date>();
		int size = c.getActualMaximum(Calendar.DATE);
		for (int i = 0; i < size; i++) {
			c.set(Calendar.DATE, i + 1);
			dateList.add(c.getTime());
		}
		return dateList;
	}

	/**
	 * 判断某年是否为闰年
	 * 
	 * @param year
	 * @return
	 */
	public boolean isLeapYear(int year) {
		GregorianCalendar gc = new GregorianCalendar();
		return gc.isLeapYear(year);
	}

	/**
	 * 判断某年是否为闰年
	 * 
	 * @param date
	 * @return
	 */
	public boolean isLeapYear(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		return gc.isLeapYear(YearToInt(date));
	}

	/**
	 * 相隔有多少天
	 * 
	 * @param sd
	 * @param ed
	 * @return
	 */
	public static int getDays(Date sd, Date ed) {
		return (int) ((ed.getTime() - sd.getTime()) / (24 * 3600 * 1000));
	}
	
	/**
	 * 合并日期和时间
	 * @param date
	 * @param time
	 * @return
	 */
	public static Calendar mergeDateTime(Date date, java.sql.Time time){
		Calendar cal = Calendar.getInstance();
		if(date!=null)
			cal.setTime(date);
		if(time!=null){
			Calendar temp = Calendar.getInstance();
			temp.setTime(time);
			cal.set(Calendar.HOUR_OF_DAY, temp.get(Calendar.HOUR_OF_DAY));
			cal.set(Calendar.MINUTE, temp.get(Calendar.MINUTE));
			cal.set(Calendar.SECOND, temp.get(Calendar.SECOND));
			cal.set(Calendar.MILLISECOND, temp.get(Calendar.MILLISECOND));
		}
		return cal;
	}
	
	/**
	 * 返回两个日期相差的天数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int diff_in_date(Date d1, Date d2){
		return (int)(d1.getTime() - d2.getTime())/86400000;
	}

	/**
	 * 获取某天开始的那一刻
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static Calendar getDateBegin(int year, int month, int date){
		Calendar begin_time = Calendar.getInstance();
		begin_time.set(Calendar.YEAR, year);
		begin_time.set(Calendar.MONTH, month-1);
		begin_time.set(Calendar.DATE, date);
		begin_time.set(Calendar.HOUR_OF_DAY, 0);
		begin_time.set(Calendar.MINUTE, 0);
		begin_time.set(Calendar.SECOND, 0);
		begin_time.set(Calendar.MILLISECOND, 0);
		return begin_time;
	}
	
	/**
	 * 清除日历的时间字段
	 * @param cal
	 */
	public static void resetTime(Calendar cal){
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
	}

	// ***************************************************************************

	/**
	 * Checks if two date objects are on the same day ignoring time
	 * 
	 * @param date1
	 *            the first date, not altered, not null
	 * @param date2
	 *            the second date, not altered, not null
	 * @return true if they represent the same day
	 * @throws IllegalArgumentException
	 *             if either date is <code>null</code>
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameDay(cal1, cal2);
	}

	/**
	 * Checks if two calendar objects are on the same day ignoring time
	 * 
	 * @param cal1
	 *            the first calendar, not altered, not null
	 * @param cal2
	 *            the second calendar, not altered, not null
	 * @return true if they represent the same day
	 * @throws IllegalArgumentException
	 *             if either calendar is <code>null</code>
	 */
	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1
				.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
	}

	/**
	 * Checks if two date objects represent the same instant in time. This method compares the long millisecond time of the two objects.
	 * 
	 * @param date1
	 *            the first date, not altered, not null
	 * @param date2
	 *            the second date, not altered, not null
	 * @return true if they represent the same millisecond instant
	 * @throws IllegalArgumentException
	 *             if either date is <code>null</code>
	 */
	public static boolean isSameInstant(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		return date1.getTime() == date2.getTime();
	}

	/**
	 * Checks if two calendar objects represent the same instant in time. This method compares the long millisecond time of the two objects.
	 * 
	 * @param cal1
	 *            the first calendar, not altered, not null
	 * @param cal2
	 *            the second calendar, not altered, not null
	 * @return true if they represent the same millisecond instant
	 * @throws IllegalArgumentException
	 *             if either date is <code>null</code>
	 */
	public static boolean isSameInstant(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		return cal1.getTime().getTime() == cal2.getTime().getTime();
	}

	/**
	 * Checks if two calendar objects represent the same local time. This method compares the values of the fields of the two objects. In addition,
	 * both calendars must be the same of the same type.
	 * 
	 * @param cal1
	 *            the first calendar, not altered, not null
	 * @param cal2
	 *            the second calendar, not altered, not null
	 * @return true if they represent the same millisecond instant
	 * @throws IllegalArgumentException
	 *             if either date is <code>null</code>
	 */
	public static boolean isSameLocalTime(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		return (cal1.get(Calendar.MILLISECOND) == cal2.get(Calendar.MILLISECOND) && cal1.get(Calendar.SECOND) == cal2.get(Calendar.SECOND)
				&& cal1.get(Calendar.MINUTE) == cal2.get(Calendar.MINUTE) && cal1.get(Calendar.HOUR) == cal2.get(Calendar.HOUR)
				&& cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.getClass() == cal2.getClass());
	}

	// ***************************************************************************

	/**
	 * 开始时间 start 在 结束时间 end 之前返回 true，否则 false
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isDateBefore(Date start, Date end) {
		GregorianCalendar sgc = new GregorianCalendar();
		GregorianCalendar egc = new GregorianCalendar();
		sgc.setTime(start);
		egc.setTime(end);

		return sgc.before(egc);
	}

	/**
	 * 判断 date 在 开始时间 start 与 结束时间 end 之间返回 true, 否则 false
	 * 
	 * @param start -
	 *            开始时间
	 * @param end -
	 *            结束时间
	 * @param date -
	 *            要判断的时间
	 * @return
	 */
	public static boolean isDateBetweenStartAndEnd(Date start, Date end, Date date) {
		if ((isDateBefore(start, end)) && (isDateBefore(start, date)) && (isDateBefore(date, end))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param date
	 * @return 若指定时间在当前时间之后则返回true,否则返回false
	 */
	public static boolean afterCurDate(Date date) {
		GregorianCalendar now = new GregorianCalendar();
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		return c.after(now);
	}

	// ----------------------------------------------------------------------- Other

	public static String getMonthStr(String str) {
		String monthOfYear[] = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
		if ("01".equals(str)) {
			str = monthOfYear[0];
		} else if ("02".equals(str)) {
			str = monthOfYear[1];
		} else if ("03".equals(str)) {
			str = monthOfYear[2];
		} else if ("04".equals(str)) {
			str = monthOfYear[3];
		} else if ("05".equals(str)) {
			str = monthOfYear[4];
		} else if ("06".equals(str)) {
			str = monthOfYear[5];
		} else if ("07".equals(str)) {
			str = monthOfYear[6];
		} else if ("08".equals(str)) {
			str = monthOfYear[7];
		} else if ("09".equals(str)) {
			str = monthOfYear[8];
		} else if ("10".equals(str)) {
			str = monthOfYear[9];
		} else if ("11".equals(str)) {
			str = monthOfYear[10];
		} else if ("12".equals(str)) {
			str = monthOfYear[11];
		} else {
			str = "ERROR";
		}
		return str;
	}

	/**
	 * 根据 英文月份全称 得到 数字 如: January => 1 </br>若返回为 0 即表明 strMonth 非月份字符串
	 * 
	 * @param strMonth
	 * @return
	 */
	public static int getNumByStrMonth(String strMonth) {
		if (strMonth.equals("January")) {
			return 1;
		} else if (strMonth.equals("February")) {
			return 2;
		} else if (strMonth.equals("March")) {
			return 3;
		} else if (strMonth.equals("April")) {
			return 4;
		} else if (strMonth.equals("May")) {
			return 5;
		} else if (strMonth.equals("June")) {
			return 6;
		} else if (strMonth.equals("July")) {
			return 7;
		} else if (strMonth.equals("August")) {
			return 8;
		} else if (strMonth.equals("September")) {
			return 9;
		} else if (strMonth.equals("October")) {
			return 10;
		} else if (strMonth.equals("November")) {
			return 11;
		} else if (strMonth.equals("December")) {
			return 12;
		} else {
			return 0; // strMonth 非 月份
		}
	}
	
	
	/**
	 * lfj
	 * @return
	 */
	public static String[] getStartEnd() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar rightNow = Calendar.getInstance();
		
		Date tempDate = new Date();
		
		rightNow.setTime(tempDate);
//		rightNow.add(Calendar.YEAR,-1);//日期减1年
//		rightNow.add(Calendar.MONTH,3);//日期加3个月
//		rightNow.add(Calendar.DAY_OF_YEAR,-1);//日期加10天
		
		rightNow.add(Calendar.HOUR_OF_DAY,-3);//小时减去1
		Date dt1=rightNow.getTime();
		String start_create = sdf.format(dt1);
		
		String end_create = sdf.format(tempDate);
		
		String[] str=new String[2];
		str[0]=start_create;
		str[1]=end_create;
		System.out.println(str[0]+"-----"+str[1]);
		return str;
	}
	
	/**
	 * 将毫秒的UTC时间转换格式为Date
	 * @param utc
	 * @return
	 */
	public static Date getUTCToDate(String utc) {
		return new Date(Long.parseLong(utc));
//		System.out.println(new Date(Long.parseLong("1465271706000")));
	}
	
	/**
	 * 转utc时间
	 * @param date
	 * @return
	 */
	public static String getDate2UTC(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String time=new Long(calendar.getTimeInMillis()/1000).toString();
		return time;
	}
	
}
