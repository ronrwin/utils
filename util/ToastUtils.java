package com.audionote.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.audionote.R;

/**
 * ToastUtils.java
 * 自定义Toast.
 *
 * @版本号 1.0
 * @date 2012-9-10
 * @author S.Kei.Cheung
 */
public class ToastUtils extends Toast {

	private static Handler handler = new Handler(Looper.getMainLooper());
	
	private static Toast toast = null;
	private static ToastUtils localSPToast=null;
	private static View localView=null;
	private static TextView mTextView=null;
	private static Object synObj = new Object();
	private int mDuration=0;
	
	public ToastUtils(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 自定义Toast图标和文本内容
	 * @param paramContext
	 * @param parIconResource 图标资源
	 * @param parCharSequence 文本资源
	 * @param paramInt	Toast.LENGTH_SHORT or Toast.LENGTH_SHORT
	 * @return
	 */
	public static void CustomToast(Context paramContext, int parIconResource, int parCharSequence, int paramInt){
		CharSequence localCharSequence = paramContext.getResources().getText(parCharSequence);
		CustomToast(paramContext,parIconResource, localCharSequence, paramInt);
	}
	
	/**
	 * 自定义Toast图标和文本内容
	 * @param paramContext
	 * @param parIconResource 图标资源
	 * @param parCharSequence 文本字符
	 * @param paramInt	Toast.LENGTH_SHORT or Toast.LENGTH_SHORT
	 * @return
	 */
	public static void CustomToast(final Context paramContext,final int parIconResource,final CharSequence parCharSequence,final int paramInt)
	{
		new Thread(new Runnable() {
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						synchronized (synObj) {
							if (localSPToast != null) {
								localSPToast.cancel();
							}
							localSPToast = new ToastUtils(paramContext);
							localView = ((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(R.layout.toast_transient_notification, null);
							mTextView=(TextView)localView.findViewById(R.id.message);
							mTextView.setText(parCharSequence);
							mTextView.setCompoundDrawablesWithIntrinsicBounds(null, paramContext.getResources().getDrawable(parIconResource), null, null);
							localSPToast.setView(localView);
							localSPToast.setDuration(paramInt);
							localSPToast.setGravity(Gravity.CENTER, 0, 0);
							
							localSPToast.show();
						}
					}
				});
			}
		}).start();
	}

	/**
	 * 确认Toast,显示确认图标
	 * @param paramContext
	 * @param paramInt1 说明文字Resource
	 * @param paramInt2	Toast.LENGTH_SHORT or Toast.LENGTH_SHORT
	 * @return
	 */
	public static void ConfToast(Context paramContext, int paramInt1, int paramInt2)
	{
		CharSequence localCharSequence = paramContext.getResources().getText(paramInt1);
		ConfToast(paramContext, localCharSequence, paramInt2);
	}

	public static void ConfToast(final Context paramContext,final CharSequence paramCharSequence,final int paramInt)
	{
		CustomToast(paramContext,R.drawable.toast_success,paramCharSequence,paramInt);
	}
	
	/**
	 * 确认Toast,显示确认图标
	 * @param paramContext
	 * @param paramInt1 说明文字Resource
	 * @param paramInt2	Toast.LENGTH_SHORT or Toast.LENGTH_SHORT
	 * @return
	 */
	public static void SmileToast(Context paramContext, int paramInt1, int paramInt2)
	{
		CharSequence localCharSequence = paramContext.getResources().getText(paramInt1);
		SmileToast(paramContext, localCharSequence, paramInt2);
	}

	public static void SmileToast(final Context paramContext,final CharSequence paramCharSequence,final int paramInt)
	{
		CustomToast(paramContext,R.drawable.toast_success_icon,paramCharSequence,paramInt);
	}
	
	
	/**
	 * 错误Toast,显示错误图标
	 * @param paramContext
	 * @param paramInt1	说明文字Resource
	 * @param paramInt2 Toast.LENGTH_SHORT or Toast.LENGTH_SHORT
	 * @return
	 */
	public static void ErrorToast(Context paramContext, int paramInt1, int paramInt2)
	{
		CharSequence localCharSequence ="";
		try
		{
			localCharSequence=paramContext.getResources().getText(paramInt1);
		}
		catch(NullPointerException ex)
		{
		  
		}
		ErrorToast(paramContext, localCharSequence, paramInt2);
	}

	public static void ErrorToast(final Context paramContext,final CharSequence paramCharSequence,final int paramInt)
	{
		CustomToast(paramContext,R.drawable.toast_err_icon,paramCharSequence,paramInt);
	}
	
	/**
	 * 提示Toast,不带图标
	 * @param paramContext
	 * @param paramInt1
	 * @param paramInt2
	 * @return
	 */
	public static void TipToast(Context paramContext, int paramInt1, int paramInt2)
	{
		CharSequence localCharSequence = paramContext.getResources().getText(paramInt1);
		TipToast(paramContext, localCharSequence, paramInt2);
	}

	public static void TipToast(final Context paramContext,final CharSequence paramCharSequence,final int paramInt)
	{
		new Thread(new Runnable() {
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						synchronized (synObj) {
							if (localSPToast != null) {
								localSPToast.cancel();
							}
							localSPToast = new ToastUtils(paramContext);
							localView = ((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(R.layout.toast_transient_notification_noicon, null);
							mTextView=(TextView)localView.findViewById(R.id.message);
							mTextView.setText(paramCharSequence);
							localSPToast.setView(localView);
							localSPToast.setDuration(paramInt);
							localSPToast.setGravity(Gravity.CENTER, 0, 0);
							
							localSPToast.show();
						}
					}
				});
			}
		}).start();
	}

	@Override
	public int getDuration()
	{
		return this.mDuration;
	}

	@Override
	public void setDuration(int duration) {
		this.mDuration = duration;
	}
	
	public static void showMessage(final Context context, final String msg) {
		showMessage(context, msg, Toast.LENGTH_SHORT);
	}

	public static void showMessage(final Context context, final String msg,
			final int len) {
		new Thread(new Runnable() {
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						synchronized (synObj) {
							if (toast != null) {
								toast.cancel();
								toast.setText(msg);
								toast.setDuration(len);
								toast.setGravity(Gravity.CENTER, 0, 0);
							} else {
								toast = Toast.makeText(context, msg, len);
								toast.setGravity(Gravity.CENTER, 0, 0);
							}
							toast.show();
						}
					}
				});
			}
		}).start();
	}

	public static void showMessage(final Context context, final int msg) {
		showMessage(context, msg, Toast.LENGTH_SHORT);
	}

	public static void showMessage(final Context context, final int msg,
			final int len) {
		new Thread(new Runnable() {
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						synchronized (synObj) {
							if (toast != null) {
								toast.cancel();
								toast.setText(msg);
								toast.setDuration(len);
								toast.setGravity(Gravity.CENTER, 0, 0);
							} else {
								toast = Toast.makeText(context, msg, len);
								toast.setGravity(Gravity.CENTER, 0, 0);
							}
							toast.show();
						}
					}
				});
			}
		}).start();
	}
	
	
	public static void cancelToast() {
		if (toast != null) 
			toast.cancel();
		if (localSPToast != null) {
			localSPToast.cancel();
		}
	}
	
}
