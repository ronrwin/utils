package com.audionote.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.audionote.R;
import com.audionote.app.AppApplication;

/**
 * 参数辅助类.
 * 
 */
public class PreferencesHelper {

	/** 版本信息SP */
	private static final String VERSION = "version";
	/** 强制升级版本号 小于等于需要强制升级 */
	private static final String NEEDUPDATEVERSION = "needUpdateVersion";
	/** 强制升级版本内容 */
	private static final String NEEDUPDATEVERSION_TIP = "needUpdateVersionTip";
	/** 强制升级地址 */
	private static final String NEEDUPDATEVERSION_URL = "needUpdateVersionUrl";
	/** 数据字典版本名称 */
	private static final String VERSION_DIC_NAME = "version_dic_name";
	/** 最后一次版本更新时间名称 */
	private static final String VERSION_LATEST_UPDATE_TIME = "version_latest_update_time";

	/** 用户信息 */
	private static final String MEMBERINFO = "member_info";
	/** cookie name */
	private static final String COOKIENAME = "cookie_name";
	/** cookie value */
	private static final String COOKIEVALUE = "cookie_value";
	/** 提交设备信息SP */
	private static final String COMMIT_DEVICE_INFO = "commit_device_info";
	/** 是否提交设备信息 */
	private static final String COMMIT_DEVICE_INFO_VALUE = "commit_device_info_value";

	/**
	 * 获取Context实例.
	 * 
	 * @return
	 */
	public static Context getContext() {
		return AppApplication.getContext();
	}

	/**
	 * 获取SharedPreferences实例.
	 * 
	 * @param name
	 *            SP名
	 * @return
	 */
	public static SharedPreferences getSharedPreferences(String name) {
		return getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
	}

	/**
	 * 获取SharedPreferences中设置的数据字典版本信息, 如果获取不到, 默认返回本地配置文件 'config.xml'
	 * 中的数据字典版本信息.
	 * 
	 * @return
	 */
	public static int getDicVersion() {
		SharedPreferences sp = getSharedPreferences(VERSION);
		return sp.getInt(VERSION_DIC_NAME, getContext().getResources()
				.getInteger(R.integer.data_dictionary_version));
	}

	/**
	 * 设置SharedPreferences中数据字典版本号.
	 * 
	 * @param version
	 *            新版本号
	 */
	public static void setDicVersion(int version) {
		SharedPreferences.Editor editor = getSharedPreferences(VERSION).edit();
		editor.putInt(VERSION_DIC_NAME, version);
		editor.apply();
	}

	/**
	 * 获取需要强制更新版本号.
	 * 
	 * @return
	 */
	public static String getNeedUpdateVersion() {
		SharedPreferences sp = getSharedPreferences(VERSION);
		return sp.getString(NEEDUPDATEVERSION, "1.0.0");
	}

	/**
	 * 设置SharedPreferences中强制更新版本号.
	 * 
	 * @param version
	 *            强制更新版本号
	 */
	public static void setNeedUpdateVersion(String version) {
		SharedPreferences.Editor editor = getSharedPreferences(VERSION).edit();
		editor.putString(NEEDUPDATEVERSION, version);
		editor.apply();
	}
	
	/**
	 * 获取需要强制更新版本的更新内容.
	 * 
	 * @return
	 */
	public static String getNeedUpdateVersionTips() {
		SharedPreferences sp = getSharedPreferences(VERSION);
		return sp.getString(NEEDUPDATEVERSION_TIP, "");
	}

	/**
	 * 设置SharedPreferences中强制更新版本的地址.
	 * 
	 * @param version
	 *            强制更新版本号
	 */
	public static void setNeedUpdateVersionUrl(String appUrl) {
		SharedPreferences.Editor editor = getSharedPreferences(VERSION).edit();
		editor.putString(NEEDUPDATEVERSION_URL, appUrl);
		editor.apply();
	}
	
	/**
	 * 获取需要强制更新版本的更新地址.
	 * 
	 * @return
	 */
	public static String getNeedUpdateVersionUrl() {
		SharedPreferences sp = getSharedPreferences(VERSION);
		return sp.getString(NEEDUPDATEVERSION_URL, "");
	}

	/**
	 * 设置SharedPreferences中强制更新版本的内容.
	 * 
	 * @param version
	 *            强制更新版本号
	 */
	public static void setNeedUpdateVersionTips(String updateTips) {
		SharedPreferences.Editor editor = getSharedPreferences(VERSION).edit();
		editor.putString(NEEDUPDATEVERSION_TIP, updateTips);
		editor.apply();
	}

	/**
	 * 设置最后的版本更新时间.
	 * 
	 * @param latest
	 *            时间毫秒数
	 */
	public static void setVersionLatestUpdateTime(long latest) {
		SharedPreferences.Editor editor = getSharedPreferences(VERSION).edit();
		editor.putLong(VERSION_LATEST_UPDATE_TIME, latest);
		editor.apply();
	}

	/**
	 * 获取最后的版本更新时间, 默认返回0.
	 * 
	 * @return
	 */
	public static long getVersionLatestUpdateTime() {
		SharedPreferences sp = getSharedPreferences(VERSION);
		return sp.getLong(VERSION_LATEST_UPDATE_TIME, 0);
	}

	/**
	 * 设置Cookie.
	 * 
	 * @param isAutoLogin
	 *            是否自动登录
	 */
	public static void setCookie(String cookieName, String cookieValue) {
		SharedPreferences.Editor editor = getSharedPreferences(MEMBERINFO)
				.edit();
		editor.putString(COOKIENAME, cookieName);
		editor.putString(COOKIEVALUE, cookieValue);
		editor.apply();
	}

	/**
	 * 重置Cookie
	 */
	public static void getCookie() {
		SharedPreferences sp = getSharedPreferences(MEMBERINFO);
		AppApplication.getHttpClient().addCookie(sp.getString(COOKIENAME, ""),
				sp.getString(COOKIEVALUE, ""));
	}

	/**
	 * 设置是否提交设备信息.
	 * 
	 * @param isCommit
	 *            是否提交设备信息
	 */
	public static void setCommitDeviceInfo(boolean isCommit) {
		SharedPreferences.Editor editor = getSharedPreferences(
				COMMIT_DEVICE_INFO).edit();
		editor.putBoolean(COMMIT_DEVICE_INFO_VALUE, isCommit);
		editor.apply();
	}

	/**
	 * 获取是否提交设备信息.
	 * 
	 * @return
	 */
	public static boolean isCommitDeviceInfo() {
		SharedPreferences sp = getSharedPreferences(COMMIT_DEVICE_INFO);
		return sp.getBoolean(COMMIT_DEVICE_INFO_VALUE, true);
	}
}
