package com.audionote.util;

import android.os.Build;

/**
 * 多版本兼容检测
 * @author Win.FR
 *
 */
public class AndroidVersionCheckUtils {
	private AndroidVersionCheckUtils() {}
	
	/**
	 * 当前Android系统版本是否在（ Donut） Android 1.6或以上
	 * API 4
	 */
	public static boolean hasVersion1_6()
	{
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT;
	}

	/**
	 * 当前Android系统版本是否在（ Eclair） Android 2.0或 以上
	 * API 5
	 */
	public static boolean hasVersion2_0()
	{
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR;
	}

	/**
	 * 当前Android系统版本是否在（ Froyo） Android 2.2或 Android 2.2以上
	 * API 8
	 */
	public static boolean hasVersion2_2()
	{
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	/**
	 * 当前Android系统版本是否在（ Gingerbread） Android 2.3x或 Android 2.3x 以上
	 * API 9
	 */
	public static boolean hasVersion2_3()
	{
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}

	/**
	 * 当前Android系统版本是否在（ Honeycomb） Android3.1或 Android3.1以上
	 * API 11
	 */
	public static boolean hasVersion3_0()
	{
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	/**
	 * 当前Android系统版本是否在（ HoneycombMR1） Android3.1.1或 Android3.1.1以上
	 * API 12
	 */
	public static boolean hasVersion3_1()
	{
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
	}

	/**
	 * 当前Android系统版本是否在（ IceCreamSandwich） Android4.0或 Android4.0以上
	 * API 14
	 */
	public static boolean hasVersion4_0()
	{
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}
	
	/**
	 * 当前Android系统版本是否在（ Jelly Bean） Android4.1以上/ API 16
	 *
	 */
	public static boolean hasVersion4_1()
	{
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}
	
	/**
	 * 当前Android系统版本是否在（ Jelly Bean MR1） Android4.2以上 / API 17
	 *
	 */
	public static boolean hasVersion4_2()
	{
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
	}
	
	/**
	 * 当前Android系统版本是否在（ Jelly Bean MR2） Android4.3以上 / API 18
	 *
	 */
	public static boolean hasVersion4_3() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
	}
	
	/**
	 * 当前Android系统版本是否在（ KITKAT ） Android4.4以上 / API 19
	 *
	 */
	public static boolean hasVersion4_4() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
	}
	
	/**
	 * 当前Android系统版本是否在（ LOLLIPOP ） Android5.0以上 / API 21
	 *
	 */
	public static boolean hasVersion5_0() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
	}
	
	/**
	 * 当前Android系统版本是否在（ LOLLIPOP ） Android5.1以上 / API 22
	 *
	 */
	public static boolean hasVersion5_1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
	}
}
