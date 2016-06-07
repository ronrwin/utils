package com.uc.ronrwin.uctopic.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * Copyright (C) 2004 - 2015 UCWeb Inc. All Rights Reserved.
 * Description : This is my code. Do not even change a single
 * line, otherwise ... ^_*
 * <p>
 * Creation    : 2016/6/1
 * Author      : Ronrwin
 */

public class ScreenUtils {

    /**
     * 返回状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }
    /**
     * 返回屏幕DisplayMetrics
     *
     * @return 当前屏幕DisplayMetrics
     * */
    public static DisplayMetrics screenDisplayMetrics(Context context) {
        WindowManager mWm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        mWm.getDefaultDisplay().getMetrics(metrics);
        // 有时候密度出错，所以通过Dpi来判断
        return metrics;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param context
     * @param pxValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getDipValue(Context context, int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                value, context.getResources().getDisplayMetrics());
    }

    public static int getSpValue(Context context, int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                value, context.getResources().getDisplayMetrics());
    }

    /**
     * 应用当前是否在前台跑
     *
     * @param paramContext
     * @return
     */
    public static boolean isAppRunningForeground(Context paramContext) {
        ActivityManager localActivityManager = (ActivityManager) paramContext
                .getSystemService("activity");
        List<RunningTaskInfo> localList = localActivityManager
                .getRunningTasks(1);
        return paramContext
                .getPackageName()
                .equalsIgnoreCase(
                        ((ActivityManager.RunningTaskInfo) localList.get(0)).baseActivity
                                .getPackageName());
    }

    public static String getTopActivityName(Context paramContext) {
        ActivityManager localActivityManager = (ActivityManager) paramContext
                .getSystemService("activity");
        // List localList = localActivityManager.getRunningTasks(2147483647);
        List localList = localActivityManager.getRunningTasks(1);
        return ((ActivityManager.RunningTaskInfo) localList.get(0)).topActivity
                .getClassName();
    }

    public static boolean writeToZipFile(byte[] paramArrayOfByte,
                                         String paramString) {
        FileOutputStream localFileOutputStream = null;
        GZIPOutputStream localGZIPOutputStream = null;
        try {
            localFileOutputStream = new FileOutputStream(paramString);
            localGZIPOutputStream = new GZIPOutputStream(
                    new BufferedOutputStream(localFileOutputStream));
            localGZIPOutputStream.write(paramArrayOfByte);
        } catch (Exception localException) {
            localException.printStackTrace();
            return false;
        } finally {
            if (localGZIPOutputStream != null)
                try {
                    localGZIPOutputStream.close();
                } catch (IOException localIOException5) {
                    localIOException5.printStackTrace();
                }
            if (localFileOutputStream != null)
                try {
                    localFileOutputStream.close();
                } catch (IOException localIOException6) {
                    localIOException6.printStackTrace();
                }
        }
        return true;
    }

    /**
     *
     * @param activity
     * @return > 0 success; <= 0 fail
     */
    public static int getStatusHeight(Activity activity){
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight){
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }
}
