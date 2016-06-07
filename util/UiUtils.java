package com.audionote.util;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.audionote.app.AppApplication;

/**
 * 显示相关
 *
 */
public class UiUtils {

	public static RelativeLayout.LayoutParams relativeImageWidthLayout = new RelativeLayout.LayoutParams(
			ScreenUtils.getDipValue(AppApplication.getContext(), 250),
			ScreenUtils.getDipValue(AppApplication.getContext(), 200));

	public static RelativeLayout.LayoutParams relativeImageHeightLayout = new RelativeLayout.LayoutParams(
			ScreenUtils.getDipValue(AppApplication.getContext(), 200),
			ScreenUtils.getDipValue(AppApplication.getContext(), 250));
	
	public static LinearLayout.LayoutParams linearImageWidthLayout = new LinearLayout.LayoutParams(
			ScreenUtils.getDipValue(AppApplication.getContext(), 250),
			ScreenUtils.getDipValue(AppApplication.getContext(), 200));

	public static LinearLayout.LayoutParams linearImageHeightLayout = new LinearLayout.LayoutParams(
			ScreenUtils.getDipValue(AppApplication.getContext(), 200),
			ScreenUtils.getDipValue(AppApplication.getContext(), 250));

	/**
	 * ScrollView内嵌套listview，需要重新计算listview的高度
	 *
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = listView.getPaddingTop()
				+ listView.getPaddingBottom();
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			if (listItem instanceof ViewGroup) {
				listItem.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

}
