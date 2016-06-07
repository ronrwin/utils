package com.audionote.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.audionote.R;

public class DialogListView extends Dialog {

	private Context mContext;
	private View mWindow;

	protected ListView mListView;

	private String[] strs;
	DialogInterface.OnClickListener mListener;
	DialogListView dialog;

	public DialogListView(Context context, String[] strs,
			DialogInterface.OnClickListener listener) {
		super(context, R.style.Dialog);
		mContext = context;
		initView();

		this.strs = strs;
		mListener = listener;
		DialogAdapter adapter = new DialogAdapter();
		setAdapter(adapter);
	}

	private void initView() {
		dialog = this;
		mWindow = LayoutInflater.from(mContext).inflate(
				R.layout.dialog_listview, null);
		addContentView(mWindow, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		mListView = (ListView) mWindow.findViewById(R.id.listview);
	}

	public void setAdapter(BaseAdapter adapter) {
		mListView.setAdapter(adapter);
	}

	private class DialogAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return strs != null ? strs.length : 0;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.widget_textview, null);
				holder.tv = (TextView) convertView.findViewById(R.id.textviews);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.tv.setText(strs[position]);

			holder.tv.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					mListener.onClick(dialog, position);
					dismiss();
				}
			});

			return convertView;
		}

		class ViewHolder {
			public TextView tv;
		}

	}
}
