package com.audionote.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 避免展示cycled bitmap
 * @author ronrwin
 *
 */
public class BitmapImageView extends ImageView {

	public BitmapImageView(Context context) {
		super(context);
	}

	public BitmapImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		try {
			super.onDraw(canvas);
		} catch (Exception e) {
			System.out
					.println("BitmapImageView -> onDraw() Canvas: trying to use a recycled bitmap");
		}
	}
}
