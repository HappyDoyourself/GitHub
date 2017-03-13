package cn.com.jiuyao.pay.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 日期工具类
 */
public class DateUtilTool{

	public DateUtilTool() {
		super();
	}

	protected final static Log _log = LogFactory.getLog(DateUtilTool.class);

	/**
	 * lfj
	 * @return
	 */
	public static String[] getStartEnd_Day(String dateStr) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar rightNow = Calendar.getInstance();
		
//		Date tempDate = new Date();
		Date tempDate=null;
		try {
			tempDate = sdf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
//		System.out.println(str[0]+"-----"+str[1]);
		return str;
	}

	/**
	 * 返回当前时间和当前时间的前2个小时
	 * @return
	 */
	public static String[] getStartEnd_TwoHour(Date tempDate) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar rightNow = Calendar.getInstance();
		
		rightNow.setTime(tempDate);
		rightNow.add(Calendar.HOUR_OF_DAY,-2);//小时减去2
		Date dt1=rightNow.getTime();
		
		String start_create = sdf.format(dt1);
		String end_create = sdf.format(tempDate);
		
		String[] str=new String[2];
		str[0]=start_create;
		str[1]=end_create;
//		System.out.println(str[0]+"-----"+str[1]);
		return str;
	}
	/**
	 * lfj
	 * @return
	 */
	public static String getTimeByAdd(String dateStr,int num) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar rightNow = Calendar.getInstance();
		
//		Date tempDate = new Date();
		Date tempDate=null;
		try {
			tempDate = sdf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		rightNow.setTime(tempDate);
//		rightNow.add(Calendar.DAY_OF_YEAR,-1);//日期加10天
		
		rightNow.add(Calendar.HOUR_OF_DAY,num);//小时减去1
		Date dt1=rightNow.getTime();
		String start_create = sdf.format(dt1);
		
//		String end_create = sdf.format(tempDate);
//		String[] str=new String[2];
//		str[0]=start_create;
//		str[1]=end_create;
		System.out.println(start_create);
		return start_create;
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
	
	/**
	 * @param dateStr
	 * @param i:执行前一个小时，还是两个小时的
	 * @return
	 */
	public static String[] getStartEnd_TwoHour(String dateStr,int i) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar rightNow = Calendar.getInstance();
		Date tempDate=null;
		try {
			tempDate = sdf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		rightNow.setTime(tempDate);
//		rightNow.add(Calendar.YEAR,-1);//日期减1年
//		rightNow.add(Calendar.MONTH,3);//日期加3个月
//		rightNow.add(Calendar.DAY_OF_YEAR,-1);//日期加10天
		
		rightNow.add(Calendar.HOUR_OF_DAY,-i);//小时减去1
		Date dt1=rightNow.getTime();
		String start_create = sdf.format(dt1);
		
		String end_create = sdf.format(tempDate);
		String[] str=new String[2];
		str[0]=start_create;
		str[1]=end_create;
//		System.out.println(str[0]+"-----"+str[1]);
		return str;
	}
	
	
	/**
	 * @param dateStr
	 * @param i:执行前多少分钟的
	 * @return
	 */
	public static String[] getStartEnd_Minus_Minute(String dateStr,int i) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar rightNow = Calendar.getInstance();
		Date tempDate=null;
		try {
			tempDate = sdf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		rightNow.setTime(tempDate);
//		rightNow.add(Calendar.YEAR,-1);//日期减1年
//		rightNow.add(Calendar.MONTH,3);//日期加3个月
//		rightNow.add(Calendar.DAY_OF_YEAR,-1);//日期加10天
		
//		rightNow.add(Calendar.HOUR_OF_DAY,-i);//小时减去1
		rightNow.add(Calendar.MINUTE, -i);
		Date dt1=rightNow.getTime();
		String start_create = sdf.format(dt1);
		
		String end_create = sdf.format(tempDate);
		String[] str=new String[2];
		str[0]=start_create;
		str[1]=end_create;
//		System.out.println(str[0]+"-----"+str[1]);
		return str;
	}	
	
	/**
	 * @param startTime
	 * @param endTime
	 * @param minute:把结束、开始时间按照30分钟、或者60分钟切割
	 * @param hour:把切割后的时间，执行前一个小时，还是两个小时的
	 * @return
	 */
	public static String[] dateDiff(String startTime, String endTime, int minute) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		long nd = 1000*24*60*60;//一天的毫秒数
//		long nh = 1000*60*60;//一小时的毫秒数
		long nm = 1000*60;//一分钟的毫秒数
		//获得两个时间的毫秒时间差异
		long diff=0;
		Date tempDate=null;
		try {
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			tempDate = sd.parse(startTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long min = diff/nm;//计算差多少分钟
		long loop_num=min/minute;
		String[] strArray = new String[(int) loop_num];
		Calendar rightNow = Calendar.getInstance();
		for (int i = 0; i < loop_num; i++) {
			rightNow.setTime(tempDate);
			rightNow.add(Calendar.MINUTE, minute*(i+1));
			Date dt1=rightNow.getTime();
			String str = sd.format(dt1);
			strArray[i]=str;
			System.out.println(str);
		}
		return strArray;
	}
	
	
	public static String[] getStartEnd_Add(int hour) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar rightNow = Calendar.getInstance();
		
		Date tempDate = new Date();
		
		rightNow.setTime(tempDate);
//		rightNow.add(Calendar.YEAR,-1);//日期减1年
//		rightNow.add(Calendar.MONTH,3);//日期加3个月
//		rightNow.add(Calendar.DAY_OF_YEAR,-1);//日期加10天
		
		rightNow.add(Calendar.HOUR_OF_DAY,-hour);//小时减去1
		Date dt1=rightNow.getTime();
		String start_create = sdf.format(dt1);
		
		String end_create = sdf.format(tempDate);
		
		String[] str=new String[2];
		str[0]=start_create;
		str[1]=end_create;
		System.out.println(str[0]+"-----"+str[1]);
		return str;
	}	
}
