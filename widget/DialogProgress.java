package com.audionote.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.audionote.R;

public class DialogProgress extends BaseDialog {

	TextView progressText;

	public DialogProgress(Context context) {
		super(context);

		title.setVisibility(View.GONE);
		commitLayout.setVisibility(View.GONE);
		mBaseWindow.setBackgroundResource(android.R.color.transparent);
		
		addProgressView();
	}
	
	public View addProgressView() {
		View v = addMyCustomView(R.layout.widget_progress);

		progressText = (TextView) v.findViewById(R.id.progress_text);
		ImageView i = (ImageView) v.findViewById(R.id.progress_image);
		AnimationDrawable a = (AnimationDrawable) i.getBackground();
		a.start();
		return v;
	}

	public void setMessage(String str) {
		progressText.setText(str);
	}

}
