package com.audionote.widget;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.audionote.R;
import com.audionote.util.VersionUtils;

@SuppressWarnings("deprecation")
public class PopupSetting extends PopupWindow implements OnClickListener {

	private Activity mActivity;
	private View mWindow;

	public PopupSetting(Activity context, View anchor) {
		super(context);
		this.mActivity = context;
		mWindow = LayoutInflater.from(context).inflate(R.layout.popup_setting,
				null);

		BitmapDrawable bm = new BitmapDrawable();
		this.setBackgroundDrawable(bm);
		setFocusable(true);
		setContentView(mWindow);
		setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
		setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

		showAsDropDown(anchor);

		mWindow.setOnClickListener(this);
		this.setOutsideTouchable(true);
		mWindow.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				dismiss();
				return false;
			}
		});
		initView();
	}

	private void initView() {
		mWindow.findViewById(R.id.feedback).setOnClickListener(this);
		mWindow.findViewById(R.id.exit).setOnClickListener(this);
		mWindow.findViewById(R.id.check_update).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		dismiss();
		int id = v.getId();
		if (id == R.id.feedback) {
			new DialogFeedback(mActivity).show();
		} else if (id == R.id.exit) {
			mActivity.finish();
		} else if (id == R.id.check_update) {
			VersionUtils.checkUpdate(mActivity);
		}
	}

}
