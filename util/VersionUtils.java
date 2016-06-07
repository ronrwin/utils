package com.audionote.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.audionote.R;
import com.audionote.app.AppApplication;
import com.audionote.constant.TaskKey;
import com.audionote.interfaces.IDialogCommitListener;
import com.audionote.manager.VersionManager;
import com.audionote.task.TaskResult;
import com.audionote.task.impl.VersionUpgradeTask;
import com.audionote.widget.DialogEdit;

import java.io.File;

public class VersionUtils {
	/*
	 * 安装apk
	 */
	public static void installApk(Activity activity, String installPath) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(installPath)),
				"application/vnd.android.package-archive");
		activity.startActivity(intent);
		activity.finish();
	}

	private static boolean checkable = true;

	public static void checkUpdate(final Activity mActivity) {
		if (checkable) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					checkable = false;
					Looper.prepare();
					int code = VersionManager.getInstance().checkNewVersion()
							.getCode();
					checkable = true;
					if (code == TaskResult.OK) {
						if (AppApplication.mVersion != null
								&& !TextUtils
										.isEmpty(AppApplication.mVersion.appUrl)) {
							showUpgradeDialog(mActivity);
						} else {
							mActivity.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									Toast.makeText(
											mActivity,
											mActivity
													.getString(R.string.already_latest),
											Toast.LENGTH_SHORT).show();
								}
							});
						}
					} else {
						mActivity.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(
										mActivity,
										mActivity
												.getString(R.string.already_latest),
										Toast.LENGTH_SHORT).show();
							}
						});
					}
				}
			});
			ExecutorUtils.getExecutorService().execute(thread);
		}
	}

	public static void showUpgradeDialog(final Activity mActivity) {
		final Context mContext = mActivity;
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (AppApplication.mVersion.compareValue == 1) {
					// 提示更新
					long lastUpdateTime = PreferencesHelper
							.getVersionLatestUpdateTime();
					// 超过3天则再提醒
					if (System.currentTimeMillis() - lastUpdateTime > 1000 * 60
							* 60 * 24 * 3) {
						DialogEdit dialog = new DialogEdit(mContext,
								new IDialogCommitListener() {
									@Override
									public void commit(String str) {
										new VersionUpgradeTask(mContext, null,
												TaskKey.VERSION_UPGRADE)
												.commit(mActivity,
														AppApplication.mVersion.appUrl);
									}
								});
						dialog.mEdit.setVisibility(View.GONE);
						dialog.setTitle(mContext
								.getString(R.string.version_upgrad)
								+ "\n"
								+ VersionManager.UPDATE_TIPS);
						dialog.show();
					}
				} else if (AppApplication.mVersion.compareValue == 2) {
					// 强制更新
					DialogEdit dialog = new DialogEdit(mContext,
							new IDialogCommitListener() {
								@Override
								public void commit(String str) {
									new VersionUpgradeTask(mContext, null,
											TaskKey.VERSION_UPGRADE).commit(
											mActivity,
											AppApplication.mVersion.appUrl);
								}
							});
					dialog.mEdit.setVisibility(View.GONE);
					dialog.setTitle(mContext.getString(R.string.version_upgrad)
							+ "\n" + VersionManager.UPDATE_TIPS);
					dialog.setCancelable(false);
					dialog.show();
				} else {
					Toast.makeText(mActivity,
							mActivity.getString(R.string.already_latest),
							Toast.LENGTH_SHORT).show();
				}

			}
		});
	}
}
