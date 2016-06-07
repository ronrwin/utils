package com.audionote.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * ScrollView 嵌套 ListView 
 * ListView 嵌套 ListView
 * 根据模式计算每个child的高度和宽度
 * 计算出listview的高度
 */
public class MeasuerListView extends ListView {

	public MeasuerListView(Context context, AttributeSet attrs) {
		
		super(context, attrs);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //根据模式计算每个child的高度和宽度
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
	
}
