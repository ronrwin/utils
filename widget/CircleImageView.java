package com.audionote.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.audionote.R;

/**
 * 圆形ImageView
 * 
 * @author Win.FR
 * @date 2014-02-28
 */
public class CircleImageView extends ImageView {

	private int viewWidth;
	private int viewHeight;
	private Bitmap bitmap;
	private Bitmap roundBitmap;
	// 描边
	private int borderWidth;
	private Paint paintBorder;
	private Paint bitmapPaint;
	private boolean mIsborder = false;

	public CircleImageView(Context context) {
		super(context);

	}

	public CircleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);

		bitmapPaint = new Paint();
		bitmapPaint.setAntiAlias(true);

		paintBorder = new Paint();
		paintBorder.setAntiAlias(true);

		if (getDrawable() != null) {
			viewWidth = getDrawable().getIntrinsicWidth();
			viewHeight = getDrawable().getIntrinsicHeight();
		}

		// load the styled attributes and set their properties

		TypedArray attributes = context.obtainStyledAttributes(attrs,
				R.styleable.CircleImageView);

		mIsborder = attributes.getBoolean(
				R.styleable.CircleImageView_circle_border, false);
		if (mIsborder) {
			setBorderWidth(attributes.getInt(
					R.styleable.CircleImageView_circle_border_width, 4));
			setBorderColor(attributes.getColor(
					R.styleable.CircleImageView_circle_border_color,
					Color.WHITE));
		}

		attributes.recycle();
	}

	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
		this.invalidate();
	}

	public void setBorderColor(int borderColor) {
		if (paintBorder != null)
			paintBorder.setColor(borderColor);
		this.invalidate();
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		Drawable drawable;
		drawable = getDrawable();
		if (drawable == null
				|| (bitmap != null && bitmap.equals(((BitmapDrawable) drawable)
						.getBitmap()))) {
			if (bitmap != null && roundBitmap != null) {
				if (mIsborder) {
					int circleCenter = viewWidth / 2;
					canvas.drawCircle(circleCenter + borderWidth, circleCenter
							+ borderWidth, circleCenter + borderWidth - 4.0f,
							paintBorder);
					canvas.drawCircle(circleCenter + borderWidth, circleCenter
							+ borderWidth, circleCenter - 4.0f, bitmapPaint);
				} else {
					canvas.drawBitmap(roundBitmap, 0, 0, bitmapPaint);
				}
			}
			return;
		}

		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}

		bitmap = ((BitmapDrawable) drawable).getBitmap();
		roundBitmap = getCroppedBitmap(bitmap, getWidth());

		// circleCenter is the x or y of the view's center

		// radius is the radius in pixels of the cirle to be drawn

		// paint contains the shader that will texture the shape
		if (mIsborder) {
			BitmapShader shader = new BitmapShader(roundBitmap,
					Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
			bitmapPaint.setShader(shader);

			int circleCenter = viewWidth / 2;
			canvas.drawCircle(circleCenter + borderWidth, circleCenter
					+ borderWidth, circleCenter + borderWidth - 4.0f,
					paintBorder);
			canvas.drawCircle(circleCenter + borderWidth, circleCenter
					+ borderWidth, circleCenter - 4.0f, bitmapPaint);
		} else {
			canvas.drawBitmap(roundBitmap, 0, 0, bitmapPaint);
		}
	}

	public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
		Bitmap sbmp;
		if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
			sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
		} else
			sbmp = bmp;

		Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(),
				Bitmap.Config.ARGB_4444);
		final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		paint.setColor(Color.parseColor("#BAB399"));

		Canvas c = new Canvas(output);
		c.drawARGB(0, 0, 0, 0);
		c.drawCircle(sbmp.getWidth() / 2, sbmp.getHeight() / 2,
				sbmp.getWidth() / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		c.drawBitmap(sbmp, rect, rect, paint);
		return output;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = measureWidth(widthMeasureSpec);
		int height = measureHeight(heightMeasureSpec, widthMeasureSpec);

		viewWidth = width - (borderWidth * 2);
		viewHeight = height - (borderWidth * 2);

		setMeasuredDimension(width, height);
	}

	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be

			result = specSize;
		} else {
			// Measure the text

			result = viewWidth;
		}

		return result;
	}

	private int measureHeight(int measureSpecHeight, int measureSpecWidth) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpecHeight);
		int specSize = MeasureSpec.getSize(measureSpecHeight);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be

			result = specSize;
		} else {
			// Measure the text (beware: ascent is a negative number)

			result = viewHeight;
		}

		return (result + 2);
	}
}