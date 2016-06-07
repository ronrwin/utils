package com.audionote.util;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressUtils {

	private static ProgressDialog pd;

	public static void showDialog(Context context, String msg) {
		dismissDialog();
		pd = new ProgressDialog(context);
		pd.setCancelable(false);
		pd.setMessage(msg);
		pd.setCanceledOnTouchOutside(false);
		pd.show();
	}

	public static void dismissDialog() {
		if (pd != null && pd.isShowing()) {
			pd.dismiss();
			pd = null;
		}
	}
}
