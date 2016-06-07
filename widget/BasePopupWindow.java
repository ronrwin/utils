package com.audionote.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.audionote.R;

@SuppressWarnings("deprecation")
public abstract class BasePopupWindow extends PopupWindow {

	protected Context mContext;
	protected View mBaseWindow;

	protected ViewGroup container;

	public BasePopupWindow(Context context) {
		super(context);
		mContext = context;
		mBaseWindow = LayoutInflater.from(context).inflate(R.layout.popup_layout,
				null);

		BitmapDrawable bm = new BitmapDrawable();
		this.setBackgroundDrawable(bm);
		setFocusable(true);
		setContentView(mBaseWindow);
		setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
		setHeight(LinearLayout.LayoutParams.MATCH_PARENT);

		showAtLocation(mBaseWindow, Gravity.CENTER, 0, 0);

		this.setOutsideTouchable(true);
		mBaseWindow.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				dismiss();
				return false;
			}
		});

		container = (ViewGroup) mBaseWindow.findViewById(R.id.popup_layout);
	}
	
	protected View addContentView(int resId) {
		View v = LayoutInflater.from(mContext).inflate(resId,
				null);
		container.addView(v);
		return v;
	}

}
