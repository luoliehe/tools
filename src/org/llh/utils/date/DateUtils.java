package org.llh.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期辅助工具类
 * 
 * @author victor.luo
 */
public abstract class DateUtils {
	
	/**yyyy-MM-dd*/
	private static final String TODAY_PATTERN = "yyyy-MM-dd";
	
	/**yyyy-MM-dd hh:mm:ss*/
	private static final String SIMPLE_PATTERN = "yyyy-MM-dd hh:mm:ss";
	
	public static Date getNowTime() {
		return new Date();
	}

	public static long getTimeMillis() {
		return System.currentTimeMillis();
	}

	public static String format(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * 简单 format成 {@value #SIMPLE_PATTERN}
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return new SimpleDateFormat(SIMPLE_PATTERN).format(date);
	}
	
	public static Date parse(String date, String pattern) 
			throws ParseException {
		return new SimpleDateFormat(pattern).parse(date);
	}

	public static boolean isToday(Date date) {
		return date != null && 
				format(getNowTime(), TODAY_PATTERN)
				.equals(format(date, TODAY_PATTERN));
	}
	
	public static Date addDay(int day) {
		return addDay(getNowTime(), day);
	}
	
	public static Date addDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}
	
	public static Date addHour(int hour) {
		return addHour(getNowTime(), hour);
	}
	
	public static Date addHour(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);
        return cal.getTime();
    }

	/**
	 * 获得今天凌晨0点时间
	 * @return 返回 2016/06/17 00:00:00
	 */
	public static Date getToday() {
		final Calendar cld = Calendar.getInstance();
		cld.set(Calendar.HOUR_OF_DAY, 0);
		cld.set(Calendar.MINUTE, 0);
		cld.set(Calendar.SECOND, 0);
		cld.set(Calendar.MILLISECOND, 0);
		return cld.getTime();
	}
	
	/**
	 * 获得昨天凌晨0点时间
	 * @return 返回 2016/06/17 00:00:00
	 */
	public static Date getYesterday(){
		Calendar c = Calendar.getInstance();
		c.setTime(getToday());
		c.add(Calendar.DAY_OF_MONTH, -1);
		return c.getTime();
	}
	
	/**
	 * 获得昨天凌晨0点时间
	 * @return 返回 2016/06/17 00:00:00
	 */
	public static Date getTomorrow(){
		Calendar c = Calendar.getInstance();
		c.setTime(getToday());
		c.add(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}
	
	public static int getSecond(){
		return getHours(getNowTime());
	}
	
	public static int getSecond(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.SECOND);
	}
	
	public static int getMinute(){
		return getHours(getNowTime());
	}
	
	public static int getMinute(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}
	
	public static int getHours(){
		return getHours(getNowTime());
	}
	
	public static int getHours(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * @param date
	 * @return 范围 [1~12]
	 */
	public static int getMonth(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH)+1;
	}
	
	/**
	 * @param date
	 * @return 范围 [1~12]
	 */
	public static int getMonth(){
		return getMonth(getNowTime());
	}
	
	public static int getYear(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}
	
	public static int getYear(){
		return getYear(getNowTime());
	}
	
	/**
	 * 返回当前向前，或向后天列表，顺序都是从小到大顺序 
	 * @param from
	 * @param days
	 * @return
	 */
	public static List<Date> getDateArray(Date from, int days) {
		int b = days < 0 ? days + 1 : 0;
		int e = days < 0 ? 1 : days;
		List<Date> list = new ArrayList<>();
		for (int i = b; i < e; i++) {
			list.add(addDay(from, i));
		}
		return list;
	}
	
	public static void main(String[] args) throws ParseException {
		System.out.println(getDateArray(new Date(), 3));
		System.out.println(getDateArray(new Date(), -3));
		
		System.out.println(getNowTime());
		System.out.println(getToday());
		System.out.println(getYesterday());
	}
}
