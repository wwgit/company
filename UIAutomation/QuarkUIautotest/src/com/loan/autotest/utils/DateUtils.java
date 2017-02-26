package com.loan.autotest.utils;

/**
 * @author yu yang
 * @Decription 
 * @data 2017年2月15日 上午10:34:36
 *
 * */

import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtils {

	// 定义日期格式 ， 如: yyyy-MM-dd HH:mm:ss
	public static String DATE_STYLE = "yyyy/MM/dd";

	/**
	 * 计算二个字符串日期的相差日
	 * 
	 * @param str,str
	 * @return int
	 */
	public static int daysDifference(String dateStr1, String dateStr2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
		try {
			dateStr1 = sdf.format(sdf2.parse(dateStr1));
			dateStr2 = sdf.format(sdf2.parse(dateStr2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int year1 = Integer.parseInt(dateStr1.substring(0, 4));
		int month1 = Integer.parseInt(dateStr1.substring(4, 6));
		int day1 = Integer.parseInt(dateStr1.substring(6, 8));
		int year2 = Integer.parseInt(dateStr2.substring(0, 4));
		int month2 = Integer.parseInt(dateStr2.substring(4, 6));
		int day2 = Integer.parseInt(dateStr2.substring(6, 8));
		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.YEAR, year1);
		c1.set(Calendar.MONTH, month1 - 1);
		c1.set(Calendar.DAY_OF_MONTH, day1);
		Calendar c2 = Calendar.getInstance();
		c2.set(Calendar.YEAR, year2);
		c2.set(Calendar.MONTH, month2 - 1);
		c2.set(Calendar.DAY_OF_MONTH, day2);
		long mills = c1.getTimeInMillis() > c2.getTimeInMillis() ? c1.getTimeInMillis() - c2.getTimeInMillis()
				: c2.getTimeInMillis() - c1.getTimeInMillis();
		return (int) (mills / 1000 / 3600 / 24);
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_STYLE);
		String str = format.format(date);
		return str;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_STYLE);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期自加一天：如需要可扩展计算种类
	 * 
	 * @param str
	 * @return str
	 */
	public static String daysAdd(String str) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_STYLE);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c = Calendar.getInstance();
		c.setTime(date);
		// c.add(Calendar.MINUTE, 1);
		// //日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
		c.add(Calendar.DAY_OF_MONTH, 1);
		return format.format(c.getTime());
	}

	// 调试用
	public static void main(String[] args) {

		Date date = new Date();
		System.out.println("日期转字符串：" + DateUtils.DateToStr(date));
		System.out.println("字符串转日期1：" + DateUtils.StrToDate(DateUtils.DateToStr(date)));
		// date = DateUtils.StrToDate("2045/07/15 22:37:09");
		// Calendar c = Calendar.getInstance();
		// c.setTime(date);
		// //c.add(Calendar.MINUTE, 1);
		// //日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
		// c.add(Calendar.DAY_OF_MONTH, 1);
		//
		// Date tomorrow = c.getTime();
		// System.out.println("运算+1后用字符形式展示：" + DateUtils.DateToStr(tomorrow));
		System.out.println("封装后的天数+1是：" + DateUtils.daysAdd("2045/07/10"));
	}

}
