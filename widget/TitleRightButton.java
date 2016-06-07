package com.audionote.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.audionote.R;

/**
 * title右端按钮
 * 
 */
public class TitleRightButton extends RelativeLayout {

	View window;
	TextView txt;
	ImageView img;

	public TitleRightButton(Context context) {
		super(context);
		initView(context);
	}

	public TitleRightButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public TitleRightButton(Context context, int id, int txtId, int ImgId) {
		super(context);
		initView(context);
		this.setId(id);
		setText(txtId, ImgId);
	}

	private void initView(Context context) {
		window = LayoutInflater.from(context).inflate(
				R.layout.title_right_button, null);
		txt = (TextView) window.findViewById(R.id.button);
		img = (ImageView) window.findViewById(R.id.image);
		addView(window);
	}

	public void setText(int txtId, int ImgId) {
		if (txtId != 0) {
			txt.setText(txtId);
			txt.setVisibility(View.VISIBLE);
		}

		if (ImgId != 0) {
			img.setImageResource(ImgId);
			img.setVisibility(View.VISIBLE);
		}

	}

}