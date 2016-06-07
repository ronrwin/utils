package com.audionote.widget;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.audionote.R;
import com.audionote.entity.NoteGroup;
import com.audionote.interfaces.ICommitGroupEditListener;
import com.audionote.util.ScreenUtils;
import com.audionote.util.SoftInputUtils;
import com.audionote.util.StringUtils;

public class DialogEditGroup extends BaseDialog implements OnClickListener {

	private View mWindow;

	EditText et;
	ImageView folderImage;
	GridView gridview;

	boolean isAdd;
	int mPosition;
	String mName;
	ICommitGroupEditListener iCommitGroupEditListener;

	NoteGroup group;

	public DialogEditGroup(Context context, String name, int index,
			ICommitGroupEditListener iCommitGroupEditListener, boolean isAdd,
			NoteGroup group) {
		super(context);
		mWindow = addMyCustomView(R.layout.dialog_edit_group);

		this.isAdd = isAdd;
		this.group = group;
		mPosition = index;
		mName = name;
		this.iCommitGroupEditListener = iCommitGroupEditListener;

		initView();
		et.setText(mName);
		et.setSelection(mName.length());
		folderImage.setImageResource(R.drawable.folder1 + mPosition);

		SoftInputUtils.showSoftInput(context, mWindow);
	}

	private void initView() {
		et = (EditText) mWindow.findViewById(R.id.rename);
		folderImage = (ImageView) mWindow.findViewById(R.id.folder_group);
		gridview = (GridView) mWindow.findViewById(R.id.gridview);
		gridview.setAdapter(new MyAdapter());
		folderImage.setOnClickListener(this);
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 9;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ImageView im = new ImageView(mContext);

			int width = ScreenUtils.getDipValue(mContext, 40);
			im.setLayoutParams(new AbsListView.LayoutParams(
					AbsListView.LayoutParams.MATCH_PARENT, width));
			im.setScaleType(ScaleType.FIT_CENTER);
			im.setImageResource(R.drawable.folder1 + position);

			im.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mPosition = position;
					folderImage
							.setImageResource(R.drawable.folder1 + mPosition);

					gridview.setVisibility(View.GONE);
				}
			});
			return im;
		}

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.commit) {
			String name = et.getText().toString();
			if (name.length() > 0) {
				// 这里需要对路径做转义
				iCommitGroupEditListener.commit(
						StringUtils.convertSpecialChar(name), mPosition, isAdd,
						group);
				dismiss();
				SoftInputUtils.hideSoftInputFromWindow(mContext, v);
			}
		} else if (v.getId() == R.id.folder_group) {
			gridview.setVisibility(View.VISIBLE);
		}
		super.onClick(v);
	}

}
