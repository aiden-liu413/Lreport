package com.kxingyi.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
     * 时间格式 yyyy-MM-dd
     */
    public static final SimpleDateFormat DATE_FORMAT_SIGN_YMD = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 时间格式 yyyyMMdd
     */
    public static final SimpleDateFormat DATE_FORMAT_YMD = new SimpleDateFormat("yyyyMMdd");
    /**
     * 时间格式 yyyyMMddHHmmss
     */
    public static final SimpleDateFormat DATE_FORMAT_YMDHMS = new SimpleDateFormat("yyyyMMddHHmmss");
    /**
     * 时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static final SimpleDateFormat DATE_FORMAT_SIGN_YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final SimpleDateFormat DATE_FORMAT_SIGN_YMDH = new SimpleDateFormat("yyyy-MM-dd HH");

    /**
     * 时间格式 yyyyMMddHHmmssSSS
     */
    public static final SimpleDateFormat DATE_FORMAT_YMD_MILLISECOND = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    /**
     * yyyy年MM月dd日
     */
    public static final SimpleDateFormat DATE_FORMAT_SIGN_YMD_CHINESE = new SimpleDateFormat("yyyy年MM月dd日");


	public static final SimpleDateFormat DATE_FORMAT_HOUR_MINI_SEC = new SimpleDateFormat("HH:mm:ss");

	public static final SimpleDateFormat DATE_FORMAT_MD_HOUR_MINI = new SimpleDateFormat("MM/dd HH:mm");
    
	public static String parseDate(Date date, SimpleDateFormat simpleDateFormat) {
		try {
			String daStr = simpleDateFormat.format(date);
			return daStr;
		} catch (Exception e) {
			return "";
		}
	}
	
    public static String getyyyyMMddHHmmssSSS() {
    	try {
			Date date = new Date();
			String daStr = DATE_FORMAT_YMD_MILLISECOND.format(date);
			return daStr;
		} catch (Exception e) {
			return "";
		}
	}
    
    /**
     * 方法名：getCurrentTime
     * 描述  ：获取当前时间 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentDate() {
		try {
			Date date = new Date();
			String daStr = DATE_FORMAT_SIGN_YMDHMS.format(date);
			return daStr;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 方法名：getCurrentTime
	 * 描述  ：获取当前时间 HH:mm:ss
	 *
	 * @return
	 */
	public static String getCurrentTime() {
		try {
			Date date = new Date();
			String daStr = DATE_FORMAT_HOUR_MINI_SEC.format(date);
			return daStr;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 *
	 * 获取当前时间在一月的第几天
	 * @return
	 */
	public static Integer getCurrentDayofMonth(){
		Calendar calendar=Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取当前时间在一周的第几天
	 * @return
	 */
	public static Integer getCurrentDayofWeek(){
		Calendar calendar=Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_WEEK)-1;
	}



    public static String formatDefault(Date date) {
    	return DATE_FORMAT_SIGN_YMDHMS.format(date);
    }

}
