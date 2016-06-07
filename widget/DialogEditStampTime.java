package com.audionote.widget;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.audionote.R;
import com.audionote.entity.Stamp;
import com.audionote.interfaces.IStampChangeListener;
import com.audionote.util.ScreenUtils;
import com.audionote.util.SoftInputUtils;
import com.pickerview.NumericWheelAdapter;
import com.pickerview.WheelView;

public class DialogEditStampTime extends BaseDialog implements OnClickListener {

	private View mWindow;

	WheelView mWheelView, mWheelView2;
	int minute, second;
	int maxMinute, maxSecond;
	Stamp mStamp;
	IStampChangeListener iStampChangeListener;
	int mDuration;

	public DialogEditStampTime(Context context, Stamp stamp, int duration,
			IStampChangeListener iStampChangeListener) {
		super(context);
		mWindow = addMyCustomView(R.layout.dialog_edit_stamp_time);

		mStamp = stamp;
		this.iStampChangeListener = iStampChangeListener;
		mDuration = duration;

		mWheelView = (WheelView) mWindow.findViewById(R.id.number_picker);
		mWheelView2 = (WheelView) mWindow.findViewById(R.id.number_picker2);

		maxMinute = duration / 60;
		maxSecond = duration % 60;
		minute = mStamp.timeSeconds / 60;
		second = mStamp.timeSeconds % 60;

		mWheelView.setAdapter(new NumericWheelAdapter(0, maxMinute, "%02d"));
		mWheelView2.setAdapter(new NumericWheelAdapter(0, maxMinute > 0 ? 59
				: maxSecond, "%02d"));

		// mWheelView.setCyclic(true);
		// mWheelView2.setCyclic(true);
		mWheelView.setCurrentItem(minute);
		mWheelView2.setCurrentItem(second);
		int textSize = ScreenUtils.getSpValue(mContext, mContext.getResources()
				.getInteger(R.integer.font_size_large_num));

		mWheelView.TEXT_SIZE = textSize;
		mWheelView2.TEXT_SIZE = textSize;

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.commit) {
			dismiss();
			SoftInputUtils.hideSoftInputFromWindow(mContext, v);
			// iStampChangeListener.change(60 * mWheelView.getValue()
			// + mWheelView2.getValue());
			iStampChangeListener.change(60 * mWheelView.getCurrentItem()
					+ mWheelView2.getCurrentItem());
		}
		super.onClick(v);
	}

}
