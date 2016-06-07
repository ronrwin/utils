package com.audionote.util;

import android.util.Log;

import com.audionote.BuildConfig;
import com.audionote.R;
import com.audionote.app.AppApplication;

/**
 * 调试信息工具类.
 */
public class DebugUtils {

	private static final String DEBUG_TAG = AppApplication.getContext()
			.getString(R.string.app_name);

	private DebugUtils() {
	}

	/**
	 * 打印调试信息.
	 * 
	 * @param msg
	 */
	public static void debug(String msg) {
		if (BuildConfig.DEBUG) {
			Log.d(DEBUG_TAG, msg);
		}
	}

	/**
	 * 打印警告信息.
	 * 
	 * @param msg
	 */
	public static void warn(String msg) {
		if (BuildConfig.DEBUG) {
			Log.w(DEBUG_TAG, msg);
		}
	}

	/**
	 * 打印提示信息.
	 * 
	 * @param msg
	 */
	public static void info(String msg) {
		if (BuildConfig.DEBUG) {
			Log.i(DEBUG_TAG, msg);
		}
	}

	/**
	 * 打印错误信息.
	 * 
	 * @param msg
	 */
	public static void error(String msg, Exception e) {
		if (BuildConfig.DEBUG) {
			Log.w(DEBUG_TAG, msg, e);
		}
	}

}
