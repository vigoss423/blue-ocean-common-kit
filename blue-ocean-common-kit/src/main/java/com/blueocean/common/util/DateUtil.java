package com.blueocean.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;

/**
 * 时间处理函数
 * 
 * @20080509 15：50
 */
public class DateUtil {

	public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD_PATTERN = "yyyy-MM-dd";
	public static final String YYYYMMDDHHMMSS_PATTERN = "yyyyMMddHHmmss";
	public static final String yyyyMMddhhmmssSSS="yyyyMMddhhmmssSSS";
	public static final String yyyyMMdd="yyyyMMdd";

	public static String getDate(String interval, Date starttime, String pattern) {
		Calendar temp = Calendar.getInstance(TimeZone.getDefault());
		temp.setTime(starttime);
		temp.add(Calendar.MONTH, Integer.parseInt(interval));
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(temp.getTime());
	}
	/**
	 * 将当前日期date - days 进行计算，返回计算后日期
	 * @return
	 */
	public static String getBeforeDate(Date date,int days,String dateFormate){
		SimpleDateFormat df = new SimpleDateFormat(dateFormate);
		Calendar calendar = Calendar.getInstance();   
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) - days);
		return df.format(calendar.getTime());
	}
	/**
	 * 将当前日期date + days 进行计算，返回计算后日期
	 * @return
	 */
	public static String getAfterDate(Date date,int days,String dateFormate){
		SimpleDateFormat df = new SimpleDateFormat(dateFormate);
		Calendar calendar = Calendar.getInstance();   
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) + days);
		return df.format(calendar.getTime());
	}
	
	/**
	 * 将传入的时间time - hour 进行计算，返回计算后时间
	 * @return
	 */
	public static String getBeforeTime(Date date,int hour){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendar = Calendar.getInstance();   
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY) - hour);
		return df.format(calendar.getTime());
	}
	
	/**
	 * 将字符串类型转换为时间类型
	 * 
	 * @return
	 */
	public static Date str2Date(String str) {
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);
		try {			
			d = sdf.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
			d = null;
			return d;
		}
		return d;
	}

	public static Date str2Date(String str, String pattern) {
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			d = sdf.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 将时间格式化
	 * 
	 * @return
	 */
	public static Date DatePattern(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);
		try {
			String dd = sdf.format(date);
			date = str2Date(dd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期格式化：yyyy-MM-dd HH:mm 转换失败返回1900-01-01 0:00
	 * @return
	 */
	public static String dateToStr(Date date){
		String dateStr="1900-01-01 00:00";
		DateFormat dateFormat=null;
		try {
			dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return dateFormat.format(date);
		} catch (Exception e) {}
		return dateStr;
	}
	/**
	 * 将时间格式化
	 */
	public static Date DatePattern(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			String dd = sdf.format(date);
			date = str2Date(dd, pattern);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String date2Str(Date date) {
		if(date==null)return "";
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);
		return sdf.format(date);
	}

	public static String date2Str(Date date, String format) {
		if(date==null)return "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 获取昨天
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date getLastDate(Date date) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(date);

		calendar.add(Calendar.DATE, -1);

		return str2Date(date2Str(calendar.getTime()));
	}

	/**
	 * 获取上周第一天（周一）
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date getLastWeekStart(Date date) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(date);
		int i = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		int startnum = 0;
		if (i == 0) {
			startnum = 7 + 6;
		} else {
			startnum = 7 + i - 1;
		}
		calendar.add(Calendar.DATE, -startnum);

		return str2Date(date2Str(calendar.getTime()));
	}

	/**
	 * 获取上周最后一天（周末）
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date getLastWeekEnd(Date date) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(date);
		int i = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		int endnum = 0;
		if (i == 0) {
			endnum = 7;
		} else {
			endnum = i;
		}
		calendar.add(Calendar.DATE, -(endnum - 1));

		return str2Date(date2Str(calendar.getTime()));
	}

	/**
	 * 改更现在时间
	 */
	public static Date changeDate(String type, int value) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		if (type.equals("month")) {
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + value);
		} else if (type.equals("date")) {
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + value);
		}
		return calendar.getTime();
	}

	/**
	 * 更改时间
	 */
	public static Date changeDate(Date date, String type, int value) {
		if (date != null) {
			// Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			// Calendar calendar = Calendar.
			if (type.equals("month")) {
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + value);
			} else if (type.equals("date")) {
				calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + value);
			} else if (type.endsWith("year")) {
				calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + value);
			}
			return calendar.getTime();
		}
		return null;
	}

	/**
	 * haoxw 比较时间是否在这两个时间点之间
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean checkTime(String time1, String time2) {
		Calendar calendar = Calendar.getInstance();
		Date date1 = calendar.getTime();
		Date date11 = DateUtil.str2Date(DateUtil.date2Str(date1, "yyyy-MM-dd") + " " + time1);// 起始时间

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		Date date2 = c.getTime();
		Date date22 = DateUtil.str2Date(DateUtil.date2Str(date2, "yyyy-MM-dd") + " " + time2);// 终止时间

		Calendar scalendar = Calendar.getInstance();
		scalendar.setTime(date11);// 起始时间

		Calendar ecalendar = Calendar.getInstance();
		ecalendar.setTime(date22);// 终止时间

		Calendar calendarnow = Calendar.getInstance();
		if (calendarnow.after(scalendar) && calendarnow.before(ecalendar)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public static boolean checkDate(String date1, String date2) {

		Date date11 = DateUtil.str2Date(date1, "yyyy-MM-dd HH:mm:ss");// 起始时间

		Date date22 = DateUtil.str2Date(date2, "yyyy-MM-dd HH:mm:ss");// 终止时间

		Calendar scalendar = Calendar.getInstance();
		scalendar.setTime(date11);// 起始时间

		Calendar ecalendar = Calendar.getInstance();
		ecalendar.setTime(date22);// 终止时间

		Calendar calendarnow = Calendar.getInstance();

		

		if (calendarnow.after(scalendar) && calendarnow.before(ecalendar)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取interval天之前的日期
	 * 
	 * @param interval
	 * @param starttime
	 * @param pattern
	 * @return
	 */
	public static Date getIntervalDate(String interval, Date starttime, String pattern) {
		Calendar temp = Calendar.getInstance();
		temp.setTime(starttime);
		temp.add(Calendar.DATE, Integer.parseInt(interval));
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String shijian = sdf.format(temp.getTime());
		return str2Date(shijian);
	}
	
	/**
	 * 当前时间（字符串格式：yyyy-MM-dd HH:mm:ss）
	 * 
	 * @return
	 */
	public static String currentTime() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(new Date());
	}
	
	/**
	 * 当前时间（字符串格式：yyyy-MM-dd HH:mm:ss）
	 * @param format<String>
	 * @return
	 */
	public static String currentTime(String format) {
		SimpleDateFormat sf = new SimpleDateFormat("".equals(format)?DEFAULT_PATTERN:format);
		return sf.format(new Date());
	}
	public static String currentTimeRadom() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        int result = 0;
        Random rand = new Random();
        result = rand.nextInt(999999);
		return sf.format(new Date())+"000000".substring((result+"").length())+result;
	}
	
	/**
	 * 获得当前时间（Date）
	 * 
	 * @return
	 */
	public static Date currentDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}
	
	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}
	
	/**
	 * long类型转换为指定格式的String时间
	 * @param dateFormat
	 * @param millSec
	 * @return
	 */
	public static String  transferLongToDate(String dateFormat,Long millSec){
		  SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	      Date date= new Date(millSec);
	      return sdf.format(date);
	}
	
	
	
	
	public static void main(String arf[]) {
		String time1 ="23:30:00";
		String time2 ="23:59:59";
		String time3 = "00:00:00";
		String time4 = "02:30:00";
		Date date=new Date();
		Long a=date.getTime();
//		String time = getBeforeTime(currentDate(),24);
//      System.out.print(time);
		System.out.println(DateUtil.currentTime());
	}
}
