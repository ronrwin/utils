package com.uc.ronrwin.uctopic.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import com.uc.ronrwin.uctopic.R;

public class ColorPoint extends View {

    private Paint mPaint;

    private int mRadius;
    private int mCirclePoint;
    private int mColor;
    private int mWidth;
    private int mHeight;

    public ColorPoint(Context context) {
        this(context, null);
    }

    public ColorPoint(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorPoint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.ColorPoint, 0, 0);
        if (arr != null) {
            mRadius = arr.getDimensionPixelSize(R.styleable.ColorPoint_radius, 0);
            mColor = arr.getColor(R.styleable.ColorPoint_color, Color.RED);
            arr.recycle();
        }

        mPaint = new Paint();
        mPaint.setStyle(Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mWidth == 0) {
            mWidth = getMeasuredWidth();
            mHeight = getMeasuredHeight();
            if (mWidth != 0) {
                if (mWidth < mHeight) {
                    mCirclePoint = mWidth / 2;
                    if (mRadius == 0) {
                        mRadius = mCirclePoint;
                    }
                } else {
                    mCirclePoint = mHeight / 2;
                    if (mRadius == 0) {
                        mRadius = mCirclePoint;
                    }
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(mColor);
        canvas.drawCircle(mCirclePoint, mCirclePoint, mRadius, mPaint);
        super.onDraw(canvas);
    }

    public void setColor(int color) {
        mColor = color;
        invalidate();
    }
}
