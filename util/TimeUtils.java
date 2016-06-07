package com.audionote.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.annotation.SuppressLint;

/**
 * 日期通用方法类
 * 
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtils {

	/**
	 * 返回当前日期
	 */
	public static String dateofnow() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new java.util.Date());
	}

	/**
	 * 返回当前日期格式为20070921
	 */
	public static String dateofnowonlynumber() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new java.util.Date());
	}

	/**
	 * 返回当前时间
	 */
	public static String timeofnow() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar curcal = Calendar.getInstance();
		return sdf.format(curcal.getTime());
	}

	/**
	 * 返回指定格式时间
	 */
	public static String timeofnow(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (date == null)
			return "";
		return sdf.format(date);
	}

	/**
	 * 返回指定格式时间
	 */
	public static String dateofnow(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (date == null)
			return "";
		return sdf.format(date);
	}

	public static String dateOfMillis(long millis) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar curcal = Calendar.getInstance();
		curcal.setTimeInMillis(millis);
		return sdf.format(curcal.getTime());
	}

	public static String timeOfMillis(long millis) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar curcal = Calendar.getInstance();
		curcal.setTimeInMillis(millis);
		return sdf.format(curcal.getTime());
	}

	/**
	 * 传入当前时间
	 * 
	 * @param strDate
	 *            字符串日期时间
	 * @return 返回今天 ,昨天 ,前天 ,月-日 hh:mm
	 */
	public static String shortTime(long millis) {
		return shortTime(timeOfMillis(millis));
	}

	/**
	 * 传入当前时间
	 * 
	 * @param strDate
	 *            字符串日期时间
	 * @return 返回今天 ,昨天 ,前天 ,月-日
	 */
	public static String shortTime(String strDate) {
		String todySDF = "今天 HH:mm";
		String yesterDaySDF = "昨天 HH:mm";
		String beforeYesterDaySDF = "前天 HH:mm";
		String otherSDF = "MM-dd HH:mm";
		SimpleDateFormat sfd = null;
		String time = "";
		Calendar dateCalendar = Calendar.getInstance();
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		Date date;
		try {
			date = simpleDateFormat.parse(strDate);
			dateCalendar.setTime(date);
			Date now = new Date();
			Calendar targetCalendar = Calendar.getInstance();
			targetCalendar.setTime(now);
			targetCalendar.set(Calendar.HOUR_OF_DAY, 0);
			targetCalendar.set(Calendar.MINUTE, 0);
			if (dateCalendar.after(targetCalendar)) {
				sfd = new SimpleDateFormat(todySDF);
				time = sfd.format(date);
				return time;
			} else {
				targetCalendar.add(Calendar.DATE, -1);
				if (dateCalendar.after(targetCalendar)) {
					sfd = new SimpleDateFormat(yesterDaySDF);
					time = sfd.format(date);
					return time;
				} else {
					targetCalendar.add(Calendar.DATE, -2);
					if (dateCalendar.after(targetCalendar)) {
						sfd = new SimpleDateFormat(beforeYesterDaySDF);
						time = sfd.format(date);
						return time;
					}
				}
			}
			sfd = new SimpleDateFormat(otherSDF);
			time = sfd.format(date);
			return time;
		} catch (ParseException e) {
			// 当天时间
			if (strDate.length() == 5)
				strDate = "今天 " + strDate;
		}
		return strDate;
	}

	/**
	 * 传入当前时间
	 * 
	 * @param strDate
	 *            字符串日期时间
	 * @return 返回今天 ,昨天 ,前天 ,年-月-日 hh:mm
	 */
	public static String chatTimeStamp(long millis) {
		return chatTimeStamp(timeOfMillis(millis));
	}

	/**
	 * 传入当前时间
	 * 
	 * @param strDate
	 *            字符串日期时间
	 * @return 返回今天 ,昨天 ,前天 ,年-月-日 hh:mm
	 */
	public static String chatTimeStamp(String strDate) {
		String todySDF = "今天 HH:mm";
		String yesterDaySDF = "昨天 HH:mm";
		String beforeYesterDaySDF = "前天 HH:mm";
		String otherSDF = "yyyy年MM月dd日 HH:mm";
		SimpleDateFormat sfd = null;
		String time = "";
		Calendar dateCalendar = Calendar.getInstance();
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		Date date;
		try {
			date = simpleDateFormat.parse(strDate);
			dateCalendar.setTime(date);
			Date now = new Date();
			Calendar targetCalendar = Calendar.getInstance();
			targetCalendar.setTime(now);
			targetCalendar.set(Calendar.HOUR_OF_DAY, 0);
			targetCalendar.set(Calendar.MINUTE, 0);
			if (dateCalendar.after(targetCalendar)) {
				sfd = new SimpleDateFormat(todySDF);
				time = sfd.format(date);
				return time;
			} else {
				targetCalendar.add(Calendar.DATE, -1);
				if (dateCalendar.after(targetCalendar)) {
					sfd = new SimpleDateFormat(yesterDaySDF);
					time = sfd.format(date);
					return time;
				} else {
					targetCalendar.add(Calendar.DATE, -2);
					if (dateCalendar.after(targetCalendar)) {
						sfd = new SimpleDateFormat(beforeYesterDaySDF);
						time = sfd.format(date);
						return time;
					}
				}
			}
			sfd = new SimpleDateFormat(otherSDF);
			time = sfd.format(date);
			return time;
		} catch (ParseException e) {
			// 当天时间
			if (strDate.length() == 5)
				strDate = "今天 " + strDate;
		}
		return strDate;
	}

	/**
	 * 传入当前时间
	 * 
	 * @param strDate
	 *            字符串日期时间
	 * @return 返回几分钟前，今天 ,昨天 ,前天 ,年-月-日
	 */
	public static String simplyTime(long millis) {
		return simplyTime(timeOfMillis(millis));
	}

	/**
	 * 传入当前字符串时间 yyyy-mm-dd HH:MM
	 * 
	 * @param strDate
	 *            字符串日期时间
	 * @return 返回几分钟前，今天 ,昨天 ,前天 ,年-月-日
	 */
	public static String simplyTime(String strDate) {
		String todySDF = "今天 HH:mm";
		String yesterDaySDF = "昨天 HH:mm";
		String beforeYesterDaySDF = "前天 HH:mm";
		String otherSDF = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sfd = null;
		String time = "";
		Calendar dateCalendar = Calendar.getInstance();
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		Date date;
		try {
			date = simpleDateFormat.parse(strDate);
			dateCalendar.setTime(date);
			Date now = new Date();
			Calendar targetCalendar = Calendar.getInstance();
			targetCalendar.setTime(now);
			targetCalendar.set(Calendar.HOUR_OF_DAY, 0);
			targetCalendar.set(Calendar.MINUTE, 0);
			long dis = System.currentTimeMillis()
					- dateCalendar.getTimeInMillis();
			if (dateCalendar.after(targetCalendar)) {
				if (dis / (1000 * 60 * 60) > 0) {
					return (int) (dis / (1000 * 60 * 60)) + "小时前";
				} else if (dis / (1000 * 60) > 0) {
					return (int) (dis / (1000 * 60)) + "分钟前";
				} else {
					return "刚刚";
				}
			} else {
				targetCalendar.add(Calendar.DATE, -1);
				if (dateCalendar.after(targetCalendar)) {
					sfd = new SimpleDateFormat(yesterDaySDF);
					time = sfd.format(date);
					return time;
				} else {
					targetCalendar.add(Calendar.DATE, -2);
					if (dateCalendar.after(targetCalendar)) {
						sfd = new SimpleDateFormat(beforeYesterDaySDF);
						time = sfd.format(date);
						return time;
					}
				}
			}

			if (dis / (1000 * 60 * 60 * 24) > 2) {
				return (int) (dis / (1000 * 60 * 60 * 24)) + "天前";
			} else {
				sfd = new SimpleDateFormat(otherSDF);
				time = sfd.format(date);
				return time;
			}
		} catch (ParseException e) {
			// 当天时间
			if (strDate.length() == 5)
				strDate = "今天 " + strDate;
		}
		return strDate;
	}

	/**
	 * 返回指定格式时间
	 */
	public static String timeofnowFormat(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (date == null)
			return "";
		return sdf.format(date);
	}

	public static String timeofnowFromStr(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (dateStr == null) {
			return "";
		}
		try {
			return timeofnow(sdf.parse(dateStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 给指定时间加上一个数值
	 * 
	 * @param time1
	 *            要加上一数值的时间，为null即为当前时间，格式为yyyy-MM-dd HH:mm:ss
	 * @param addpart
	 *            要加的部分：年月日时分秒分别为：YMDHFS
	 * @param num
	 *            要加的数值
	 * @return 新时间，格式为yyyy-MM-dd HH:mm:ss
	 */
	public static String addTime(String time1, String addpart, int num) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(new Date());
			time1 = (time1 == null) ? now : time1;
			if (time1.length() < 19) {
				time1 += " 00:00:00";
			}
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(sdf.parse(time1));
			if (addpart.equalsIgnoreCase("Y")) {
				cal.add(Calendar.YEAR, num);
			} else if (addpart.equalsIgnoreCase("M")) {
				cal.add(Calendar.MONTH, num);
			} else if (addpart.equalsIgnoreCase("D")) {
				cal.add(Calendar.DATE, num);
			} else if (addpart.equalsIgnoreCase("H")) {
				cal.add(Calendar.HOUR, num);
			} else if (addpart.equalsIgnoreCase("F")) {
				cal.add(Calendar.MINUTE, num);
			} else if (addpart.equalsIgnoreCase("S")) {
				cal.add(Calendar.SECOND, num);
			}
			return sdf.format(cal.getTime());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 给指定时间加上一个数值
	 * 
	 * @param time1
	 *            要加上一数值的时间，为null即为当前时间，格式为格式可自定义传入
	 * @param addpart
	 *            要加的部分：年月日时分秒分别为：YMDHFS
	 * @param num
	 *            要加的数值
	 * @return 新时间，格式可自定义传入
	 */
	public static String addTimeFormat(String time1, String addpart, int num,
			String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			String now = sdf.format(new Date());
			time1 = (time1 == null) ? now : time1;
			if (time1.length() < 19) {
				time1 += " 00:00:00";
			}
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(sdf.parse(time1));
			if (addpart.equalsIgnoreCase("Y")) {
				cal.add(Calendar.YEAR, num);
			} else if (addpart.equalsIgnoreCase("M")) {
				cal.add(Calendar.MONTH, num);
			} else if (addpart.equalsIgnoreCase("D")) {
				cal.add(Calendar.DATE, num);
			} else if (addpart.equalsIgnoreCase("H")) {
				cal.add(Calendar.HOUR, num);
			} else if (addpart.equalsIgnoreCase("F")) {
				cal.add(Calendar.MINUTE, num);
			} else if (addpart.equalsIgnoreCase("S")) {
				cal.add(Calendar.SECOND, num);
			}
			return sdf.format(cal.getTime());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 给指定日期加上一个数值
	 * 
	 * @param date1
	 *            要加上一数值的日期，为null即为当前日期，格式为yyyy-MM-dd
	 * @param addpart
	 *            要加的部分：年月日分别为：YMD
	 * @param num
	 *            要加的数值
	 * @return 新日期，格式为yyyy-MM-dd
	 */
	public static String addDate(String date1, String addpart, int num) {
		return addTime(date1, addpart, num).substring(0, 10);
	}

	/**
	 * 传入当前字符串时间 yyyy-mm-dd HH:MM:SS 返回今天 HH:MM:SS,昨天 HH:MM:SS,前天 HH:MM:SS,年-月-日
	 * HH:MM:SS
	 * 
	 * @param strDate
	 *            字符串日期时间
	 * @return 返回今天 HH:MM:SS,昨天 HH:MM:SS,前天 HH:MM:SS,年-月-日 HH:MM:SS
	 */
	public static String getTime_yyyymmdd_hhmmss(String strDate) {
		return getTime(strDate, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 传入当前字符串时间 yyyy-mm-dd HH:MM 返回今天 HH:MM,昨天 HH:MM,前天 HH:MM,年-月-日 HH:MM
	 * 
	 * @param strDate
	 *            字符串日期时间
	 * @return 返回今天 HH:MM,昨天 HH:MM,前天 HH:MM,年-月-日 HH:MM
	 */
	public static String getTime_yyyymmdd_hhmm(String strDate) {
		return getTime(strDate, "yyyy-MM-dd HH:mm");
	}

	/**
	 * 传入当前字符串时间 yyyy-mm-dd HH:MM:SS 返回今天 HH:MM:SS,昨天 HH:MM:SS,前天 HH:MM:SS,年-月-日
	 * HH:MM:SS
	 * 
	 * @param strDate
	 *            字符串日期时间
	 * @return 返回今天 HH:MM:SS,昨天 HH:MM:SS,前天 HH:MM:SS,年-月-日 HH:MM:SS
	 */
	public static String getTime(String strDate, String format) {
		String todySDF = "今天 HH:mm";
		String yesterDaySDF = "昨天 HH:mm";
		String beforeYesterDaySDF = "前天 HH:mm";
		String otherSDF = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sfd = null;
		String time = "";
		Calendar dateCalendar = Calendar.getInstance();
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		Date date;
		try {
			date = simpleDateFormat.parse(strDate);
			dateCalendar.setTime(date);
			Date now = new Date();
			Calendar targetCalendar = Calendar.getInstance();
			targetCalendar.setTime(now);
			targetCalendar.set(Calendar.HOUR_OF_DAY, 0);
			targetCalendar.set(Calendar.MINUTE, 0);
			if (dateCalendar.after(targetCalendar)) {
				sfd = new SimpleDateFormat(todySDF);
				time = sfd.format(date);
				return time;
			} else {
				targetCalendar.add(Calendar.DATE, -1);
				if (dateCalendar.after(targetCalendar)) {
					sfd = new SimpleDateFormat(yesterDaySDF);
					time = sfd.format(date);
					return time;
				} else {
					targetCalendar.add(Calendar.DATE, -2);
					if (dateCalendar.after(targetCalendar)) {
						sfd = new SimpleDateFormat(beforeYesterDaySDF);
						time = sfd.format(date);
						return time;
					}
				}
			}
			sfd = new SimpleDateFormat(otherSDF);
			time = sfd.format(date);
			return time;
		} catch (ParseException e) {
			// 当天时间
			if (strDate.length() == 5)
				strDate = "今天 " + strDate;
		}
		return strDate;
	}

	/**
	 * 获取两个日期之间的间隔天数
	 * 
	 * @return 两个日期之间的间隔天数
	 */
	public static int getGapCount(Date startDate, Date endDate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(startDate);
		fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);

		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(endDate);
		toCalendar.set(Calendar.HOUR_OF_DAY, 0);
		toCalendar.set(Calendar.MINUTE, 0);
		toCalendar.set(Calendar.SECOND, 0);
		toCalendar.set(Calendar.MILLISECOND, 0);

		return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime()
				.getTime()) / (1000 * 60 * 60 * 24));
	}

	/** 格式化显示 mm:ss */
	public static String secondTime(int seconds) {
		int second = 0, minute = 0;
		if (seconds >= 60) {
			minute = seconds / 60;
			second = seconds % 60;
		} else {
			second = seconds;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(String.format("%02d", minute));
		sb.append(":");
		sb.append(String.format("%02d", second));

		return sb.toString();
	}

}
