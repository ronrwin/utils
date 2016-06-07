package com.audionote.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.audionote.R;
import com.audionote.util.BitmapUtils;
import com.audionote.util.TimeUtils;

public class TimeStampContent extends LinearLayout {

	View window;
	private TextView mTime, mContent, mImageTime;
	private RelativeLayout mImageLayout;
	ImageView mImage;
	Bitmap bitmap;

	public TimeStampContent(Context context) {
		super(context);
		initView(context);
		addView(window);
	}

	public TimeStampContent(Context context, int seconds, String photoPath,
			String content) {
		super(context);
		initView(context);
		mTime.setText(TimeUtils.secondTime(seconds));
		mImageTime.setText(TimeUtils.secondTime(seconds));
		mContent.setText(content);
		
		if (TextUtils.isEmpty(photoPath)) {
			mImageLayout.setVisibility(View.GONE);
			mTime.setVisibility(View.VISIBLE);
		} else {
			mImageLayout.setVisibility(View.VISIBLE);
			mTime.setVisibility(View.GONE);
			
			bitmap = BitmapUtils.secureDecodeFile(photoPath);
			if (bitmap != null) {
				mImage.setImageBitmap(bitmap);
			}
		}
		
		addView(window);
	}

	private void initView(Context context) {
		window = LayoutInflater.from(context).inflate(R.layout.adapter_timestamp_content,
				null);
		mTime = (TextView) window.findViewById(R.id.timestamp);
		mContent = (TextView) window.findViewById(R.id.content);
		mImageLayout = (RelativeLayout) window.findViewById(R.id.image_layout);
		mImageTime = (TextView) window.findViewById(R.id.timestamp_image);
		mImage = (ImageView) window.findViewById(R.id.image);
	}

}
