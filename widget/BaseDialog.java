package com.audionote.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.audionote.R;


public class BaseDialog extends Dialog implements
		android.view.View.OnClickListener {

	protected Context mContext;
	protected View mBaseWindow;

	public ViewGroup container, commitLayout;
	protected TextView title;
	public TextView commit, cancel;

	public BaseDialog(Context context) {
		super(context, R.style.Dialog);
		mContext = context;
		mBaseWindow = LayoutInflater.from(context).inflate(
				R.layout.base_dialog_layout, null);
		addContentView(mBaseWindow, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		container = (ViewGroup) mBaseWindow.findViewById(R.id.container);

		title = (TextView) mBaseWindow.findViewById(R.id.title);
		title.setVisibility(View.GONE);
		commit = (TextView) mBaseWindow.findViewById(R.id.commit);
		commit.setOnClickListener(this);
		cancel = (TextView) mBaseWindow.findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		commitLayout = (ViewGroup) mBaseWindow.findViewById(R.id.select_layout);
	}

	/**
	 * 添加到dialog窗体的view
	 * 
	 * @param resId
	 * @return
	 */
	protected View addMyCustomView(int resId) {
		View layout = LayoutInflater.from(mContext).inflate(resId, null);

		container.addView(layout);
		return layout;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (v.getId() == R.id.commit) {
			dismiss();
		} else if (id == R.id.cancel) {
			dismiss();
		}
	}

	public void setTitle(String t) {
		title.setText(t);
		title.setVisibility(View.VISIBLE);
	}
}
