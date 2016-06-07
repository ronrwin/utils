package com.audionote.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.EditText;

import com.audionote.util.BitmapUtils;

public class BitmapEditText extends EditText {

	public BitmapEditText(Context context) {
		super(context);
	}

	public BitmapEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void addDrawable(Context context, String photoPath) {
		Bitmap bm = BitmapUtils.secureDecodeFile(photoPath);
		if (bm != null) {
			append("\n");
			final SpannableString ss = new SpannableString(photoPath);
			ImageSpan span = new ImageSpan(context, bm);
			ss.setSpan(span, 0, photoPath.length(),
					Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
			append(ss);
			append("\n");
		}
	}

	public void addBitmap(Context context, Bitmap bitmap) {
		if (bitmap != null) {
			append("\n");
			String tempStr = bitmap.toString();
			final SpannableString ss = new SpannableString(tempStr);
			ImageSpan span = new ImageSpan(context, bitmap);
			ss.setSpan(span, 0, tempStr.length(),
					Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
			append(ss);
			append("\n");
		}
	}

}
