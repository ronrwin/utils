package com.uc.base.util.drawable;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;

public class DrawableUtils {

    public static int DEFAULT_FILTER_COLOR = Color.parseColor("#33000000");

    public static Drawable getDefaultColorFilterDrawable(Drawable drawable) {
        return getColorFilterDrawable(drawable, DEFAULT_FILTER_COLOR);
    }

    public static Drawable getColorFilterDrawable(Drawable drawable, int color, PorterDuff.Mode mode) {
        if (drawable != null) {
            Drawable filterDrawable = drawable.getConstantState().newDrawable().mutate();
            filterDrawable.setColorFilter(color, mode);
            return filterDrawable;
        }
        return null;
    }

    /**
     * 叠加颜色之后的效果
     * 使用SRC_ATOP也能直接达到效果，但是对于带透明度黑色的图片，叠加黑色透明度颜色就不起作用。
     * 因此改用LayerDrawable实现。
     */
    public static Drawable getColorFilterLayerDrawable(Drawable drawable, int color) {
        // 保留图片内容，设为目标颜色，透明度为图片透明度与颜色透明度相乘
        Drawable drawable1 = getColorFilterDrawable(drawable, color, PorterDuff.Mode.SRC_IN);
        if (drawable1 != null) {
            Drawable[] array = new Drawable[2];
            array[0] = drawable;
            array[1] = drawable1;
            LayerDrawable layerDrawable = new LayerDrawable(array);
            // 返回叠加的图片
            return layerDrawable;
        }
        return null;
    }

    public static Drawable getColorFilterDrawable(Drawable drawable, int color) {
        return getColorFilterDrawable(drawable, color, PorterDuff.Mode.SRC_ATOP);
    }

    public static Drawable getDefaultColorFilterStatusDrawable(Drawable dst) {
        return getColorFilterStatusDrawable(dst, DEFAULT_FILTER_COLOR);
    }

    public static Drawable getColorFilterStatusDrawable(Drawable dst, int color) {
        return getStatusDrawable(dst, getColorFilterDrawable(dst, color));
    }



    /**
     *  带点击态的drawable
     */
    public static Drawable getStatusDrawable(Drawable aEmptyDrawable, Drawable aPressingDrawable) {
        StateListDrawable ret = new StateListDrawable();

        ret.addState(new int[] {
                android.R.attr.state_pressed,
        }, aPressingDrawable);
        ret.addState(new int[] {}, aEmptyDrawable);

        return ret;
    }

}
