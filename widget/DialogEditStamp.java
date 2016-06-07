package com.audionote.widget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.audionote.R;
import com.audionote.activity.PhotoActivity;
import com.audionote.entity.Stamp;
import com.audionote.interfaces.IStampChangeListener;
import com.audionote.interfaces.IStampEditListener;
import com.audionote.util.BitmapUtils;
import com.audionote.util.FileUtils;
import com.audionote.util.PhotoFetchUtils;
import com.audionote.util.SoftInputUtils;
import com.audionote.util.TimeUtils;
import com.audionote.util.UiUtils;

import java.io.File;

public class DialogEditStamp extends BaseDialog implements OnClickListener {

	private View mWindow;

	private TextView time;
	private EditText content;
	IStampEditListener mContentCommitListener;
	ImageView imageView;
	LayoutParams mLayoutParam;
	ImageView imageDelete;
	Bitmap bitmap;
	ViewGroup timestampEditLayout;

	Stamp mStamp;
	int mDuration;
	Activity activity;
	String mTempCameraPhotoName = "camera_temp.jpg";
	String mNotePath;
	String mPhotoName;
	// 监听dismiss()事件，判断是否提交
	boolean isCommitDismiss;
	// 有照片时不可再点击编辑照片，须删除后再操作
	boolean hasImage = false;

	public DialogEditStamp(Activity context, Stamp stamp, int duration,
			IStampEditListener iContentCommitListener, String notePath,
			String tempPhotoName) {
		super(context);
		mWindow = addMyCustomView(R.layout.dialog_edit_stamp);

		activity = context;
		mNotePath = notePath;
		mStamp = stamp;
		mDuration = duration;
		mContentCommitListener = iContentCommitListener;
		initView();

		time.setText(TimeUtils.secondTime(mStamp.timeSeconds));
		content.setText(mStamp.content);
		content.setSelection(mStamp.content.length());

		if (!tempPhotoName.equals("")) {
			mPhotoName = tempPhotoName;
		} else {
			mPhotoName = stamp.photoName;
		}

		if (!TextUtils.isEmpty(mPhotoName)) {
			bitmap = BitmapUtils.secureDecodeFile(mNotePath + File.separator
					+ mPhotoName);
			if (bitmap != null) {
				mLayoutParam = imageView.getLayoutParams();
				if (bitmap.getWidth() > bitmap.getHeight()) {
					imageView.setLayoutParams(UiUtils.relativeImageWidthLayout);
				} else {
					imageView
							.setLayoutParams(UiUtils.relativeImageHeightLayout);
				}

				imageView.setScaleType(ScaleType.CENTER_CROP);
				imageView.setImageBitmap(bitmap);
				imageDelete.setVisibility(View.VISIBLE);
				hasImage = true;
			} else {
				imageDelete.setVisibility(View.GONE);
				hasImage = false;
			}
		}
		setCancelable(false);
		show();
	}

	private void initView() {
		time = (TextView) mWindow.findViewById(R.id.timestamp);
		content = (EditText) mWindow.findViewById(R.id.content);
		imageView = (ImageView) mWindow.findViewById(R.id.img);
		imageDelete = (ImageView) mWindow.findViewById(R.id.img_delete);
		content.requestFocus();
		timestampEditLayout = (ViewGroup) mWindow
				.findViewById(R.id.timestamp_edit_lay);

		imageView.setOnClickListener(this);
		timestampEditLayout.setOnClickListener(this);
		imageDelete.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.commit) {
			isCommitDismiss = true;
			dismiss();

			SoftInputUtils.hideSoftInputFromWindow(mContext, v);
			mStamp.content = content.getText().toString();
			if (hasImage) {
				if (!mPhotoName.equals(mStamp.photoName)) {
					if (!mStamp.photoName.equals("")) {
						FileUtils.deleteFile(new File(mNotePath + File.separator
								+ mStamp.photoName));
					}
					mStamp.photoName = mPhotoName;
				}
			} else {
				mStamp.photoName = "";
			}
			mContentCommitListener.refresh();
		} else if (v.getId() == R.id.img) {
			if (hasImage) {
				Intent pIntent = new Intent(mContext, PhotoActivity.class);
				pIntent.putExtra("photo", mNotePath + File.separator
						+ mPhotoName);
				mContext.startActivity(pIntent);
			} else {
				PhotoFetchUtils.showSelectDialog(activity,
						activity.getString(R.string.select_photo), mNotePath,
						mTempCameraPhotoName);
			}
		} else if (v.getId() == R.id.timestamp_edit_lay) {
			// dismiss();
			new DialogEditStampTime(mContext, mStamp, mDuration,
					iStampChangeListener).show();
		} else if (v.getId() == R.id.img_delete) {
			if (bitmap != null) {
				bitmap.recycle();
				bitmap = null;
			}
			imageView.setImageResource(R.drawable.plus);
			if (mLayoutParam != null) {
				imageView.setLayoutParams(mLayoutParam);
			}
//			mPhotoName = "";
			imageDelete.setVisibility(View.GONE);
			hasImage = false;
		}
		super.onClick(v);
	}

	IStampChangeListener iStampChangeListener = new IStampChangeListener() {
		@Override
		public void change(int seconds) {
			mStamp.timeSeconds = seconds;
			time.setText(TimeUtils.secondTime(mStamp.timeSeconds));
		}
	};

	@Override
	public void dismiss() {
		// 非提交的退出事件&&图片路径已经改变，则删除改变后的照片，保留原来的
		if (!isCommitDismiss && !mPhotoName.equals(mStamp.photoName)) {
			FileUtils.deleteFile(new File(mNotePath + File.separator
					+ mPhotoName));
		}

		if (bitmap != null) {
			bitmap.recycle();
			bitmap = null;
		}
		super.dismiss();
	}
}
