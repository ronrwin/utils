package com.audionote.util;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 软键盘
 *
 */
public class SoftInputUtils {
	/** 隐藏某焦点控件弹出的软件键盘. */
	public static void hideSoftInputFromWindow(final Context context,
			final View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		IBinder binder = view.getWindowToken();
		inputMethodManager.hideSoftInputFromWindow(binder, 0);
	}

	public static void showSoftInput(final Context context, final View view) {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager inputMethodManager = (InputMethodManager) context
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.showSoftInput(view,
						InputMethodManager.SHOW_FORCED);
			}
		}, 1000);
	}
}
