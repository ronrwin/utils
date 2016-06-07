package com.audionote.util;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.audionote.app.AppApplication;
import com.audionote.exception.OutOfMemeryException;

/**
 * 图片效果处理
 * 
 * @version 1.0.0
 * @author Win.FR
 */
public class PhotoProUtils {

	/**
	 * 马赛克效果
	 * 
	 * @param instance
	 *            :原图像像素点采样间隔
	 * @return
	 */
	public static Bitmap Mosaic(Bitmap bmp, int instance) {
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		int newWidth = width / instance;
		int newHeight = height / instance;
		Bitmap newBmp = Bitmap.createBitmap(newWidth, newHeight,
				Bitmap.Config.RGB_565);
		int[] newPixels = new int[newWidth * newHeight];

		int[] pixels = new int[width * height];
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		for (int i = 0; i < newWidth; i++) {
			for (int k = 0; k < newHeight; k++) {
				newPixels[i * newWidth + k] = pixels[(i * instance + 1) * width
						+ (k * instance + 1)];
			}
		}
		newBmp.setPixels(newPixels, 0, newWidth, 0, 0, newWidth, newHeight);
		return newBmp;
	}

	/** 将Drawable转化为Bitmap */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;

	}
	
	/** 将Bitmap转成Drawable, Drawable缩放 */
	public static Drawable BitmapToDrawable(Bitmap bitmap) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		return BitmapToDrawable(bitmap, w, h);
	}

	/** 将Bitmap转成Drawable, Drawable缩放 */
	public static Drawable BitmapToDrawable(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return new BitmapDrawable(AppApplication.getContext().getResources(),newbmp);
	}

	/** 获得圆角图片 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 倍数Bitmap缩放
	 * */
	public static Bitmap multipleBitmap(Bitmap bitmap, float multiple) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		matrix.postScale(multiple, multiple);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	/**
	 * Bitmap缩放
	 * */
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	/**
	 * 图片适应处理
	 * */
	public static Bitmap getScaleNormalBitmap(Bitmap bitmap, int imageW,
			int imageH) {
		if (bitmap == null) {
			return null;
		}
		Bitmap rBitmap;
		int bw = bitmap.getWidth();
		int bh = bitmap.getHeight();
		int vw = imageW;
		int vh = imageH;
		if (vw <= 0)
			vw = bw;
		if (vh <= 0)
			vh = bh;

		float vRation = (float) vw / vh;
		float bRation = (float) bw / bh;
		int x = 0, y = 0;
		int srcWidth;
		int srcHeight;
		if (vRation > bRation) {
			srcWidth = bw;
			srcHeight = (int) (vh * ((float) bw / vw));
			x = 0;
			y = (bh - srcHeight) / 2;
		} else {
			srcWidth = (int) (vw * ((float) bh / vh));
			srcHeight = bh;
			x = (bw - srcWidth) / 2;
			y = 0;
		}
		try {
			Matrix matrix = new Matrix();
			float scaleWidth = ((float) vw / srcWidth);
			float scaleHeight = ((float) vh / srcHeight);
			matrix.postScale(scaleWidth, scaleHeight);
			rBitmap = Bitmap.createBitmap(bitmap, x, y, srcWidth, srcHeight,
					matrix, true);
		} catch (OutOfMemoryError e) {
			rBitmap = bitmap;
		}
		return rBitmap;
	}

	/**
	 * 图片适应处理
	 * */
	public static Bitmap getScaleBitmap(Bitmap bitmap, int imageW, int imageH) {
		if (bitmap == null) {
			return null;
		}
		Bitmap rBitmap;
		int bw = bitmap.getWidth();
		int bh = bitmap.getHeight();
		int vw = imageW;
		int vh = imageH;
		if (vw <= 0)
			vw = bw;
		if (vh <= 0)
			vh = bh;

		float vRation = (float) vw / vh;
		float bRation = (float) bw / bh;
		int x = 0, y = 0;
		int srcWidth;
		int srcHeight;
		if (vRation > bRation) {
			srcWidth = bw;
			srcHeight = (int) (vh * ((float) bw / vw));
			x = 0;
			y = (bh - srcHeight) / 2;
		} else {
			srcWidth = (int) (vw * ((float) bh / vh));
			srcHeight = bh;
			x = (bw - srcWidth) / 2;
			y = 0;
		}
		try {
			Matrix matrix = new Matrix();
			float scaleWidth = ((float) vw / srcWidth);
			float scaleHeight = ((float) vh / srcHeight);
			matrix.postScale(scaleWidth, scaleHeight);

			rBitmap = Bitmap.createBitmap(bitmap, x, y, srcWidth, srcHeight,
					matrix, true);
			rBitmap = getBlurredBitmap(rBitmap, 10);
		} catch (OutOfMemoryError e) {
			rBitmap = bitmap;
		}
		return rBitmap;
	}

	/**
	 * Process incoming {@linkplain Bitmap} to make rounded corners according to
	 * target {@link ImageView}.<br />
	 * This method <b>doesn't display</b> result bitmap in {@link ImageView}
	 * 
	 * @param bitmap
	 *            Incoming Bitmap to process
	 * @param imageView
	 *            Target {@link ImageView} to display bitmap in
	 * @param roundPixels
	 * @return Result bitmap with rounded corners
	 */
	public static Bitmap roundCorners(Bitmap bitmap, ImageView imageView,
			int roundPixels) {
		Bitmap roundBitmap;

		int bw = bitmap.getWidth();
		int bh = bitmap.getHeight();
		int vw = imageView.getWidth();
		int vh = imageView.getHeight();
		if (vw <= 0)
			vw = bw;
		if (vh <= 0)
			vh = bh;

		int width, height;
		Rect srcRect;
		Rect destRect;
		switch (imageView.getScaleType()) {
		case CENTER_INSIDE:
			float vRation = (float) vw / vh;
			float bRation = (float) bw / bh;
			int destWidth;
			int destHeight;
			if (vRation > bRation) {
				destHeight = Math.min(vh, bh);
				destWidth = (int) (bw / ((float) bh / destHeight));
			} else {
				destWidth = Math.min(vw, bw);
				destHeight = (int) (bh / ((float) bw / destWidth));
			}
			int x = (vw - destWidth) / 2;
			int y = (vh - destHeight) / 2;
			srcRect = new Rect(0, 0, bw, bh);
			destRect = new Rect(x, y, x + destWidth, y + destHeight);
			width = vw;
			height = vh;
			break;
		case FIT_CENTER:
		case FIT_START:
		case FIT_END:
		default:
			vRation = (float) vw / vh;
			bRation = (float) bw / bh;
			if (vRation > bRation) {
				width = (int) (bw / ((float) bh / vh));
				height = vh;
			} else {
				width = vw;
				height = (int) (bh / ((float) bw / vw));
			}
			srcRect = new Rect(0, 0, bw, bh);
			destRect = new Rect(0, 0, width, height);
			break;
		case CENTER_CROP:
			vRation = (float) vw / vh;
			bRation = (float) bw / bh;
			int srcWidth;
			int srcHeight;
			if (vRation > bRation) {
				srcWidth = bw;
				srcHeight = (int) (vh * ((float) bw / vw));
				x = 0;
				y = (bh - srcHeight) / 2;
			} else {
				srcWidth = (int) (vw * ((float) bh / vh));
				srcHeight = bh;
				x = (bw - srcWidth) / 2;
				y = 0;
			}
			width = Math.min(vw, bw);
			height = Math.min(vh, bh);
			srcRect = new Rect(x, y, x + srcWidth, y + srcHeight);
			destRect = new Rect(0, 0, width, height);
			break;
		case FIT_XY:
			width = vw;
			height = vh;
			srcRect = new Rect(0, 0, bw, bh);
			destRect = new Rect(0, 0, width, height);
			break;
		case CENTER:
		case MATRIX:
			width = Math.min(vw, bw);
			height = Math.min(vh, bh);
			x = (bw - width) / 2;
			y = (bh - height) / 2;
			srcRect = new Rect(x, y, x + width, y + height);
			destRect = new Rect(0, 0, width, height);
			break;
		}

		try {
			roundBitmap = getRoundedCornerBitmap(bitmap, roundPixels, srcRect,
					destRect, width, height);
		} catch (OutOfMemoryError e) {
			roundBitmap = bitmap;
		}

		return roundBitmap;
	}

	public static Bitmap roundCorners(int dheight, int dwidth, Bitmap bitmap,
			ImageView imageView, int roundPixels) {
		Bitmap roundBitmap;

		int bw = bitmap.getWidth();
		int bh = bitmap.getHeight();
		int vw = imageView.getWidth() > 0 ? imageView.getWidth() : dwidth;
		int vh = imageView.getHeight() > 0 ? imageView.getHeight() : dheight;
		if (vw <= 0)
			vw = bw;
		if (vh <= 0)
			vh = bh;

		int width, height;
		Rect srcRect;
		Rect destRect;
		switch (imageView.getScaleType()) {
		case CENTER_INSIDE:
			float vRation = (float) vw / vh;
			float bRation = (float) bw / bh;
			int destWidth;
			int destHeight;
			if (vRation > bRation) {
				destHeight = Math.min(vh, bh);
				destWidth = (int) (bw / ((float) bh / destHeight));
			} else {
				destWidth = Math.min(vw, bw);
				destHeight = (int) (bh / ((float) bw / destWidth));
			}
			int x = (vw - destWidth) / 2;
			int y = (vh - destHeight) / 2;
			srcRect = new Rect(0, 0, bw, bh);
			destRect = new Rect(x, y, x + destWidth, y + destHeight);
			width = vw;
			height = vh;
			break;
		case FIT_CENTER:
		case FIT_START:
		case FIT_END:
		default:
			vRation = (float) vw / vh;
			bRation = (float) bw / bh;
			if (vRation > bRation) {
				width = (int) (bw / ((float) bh / vh));
				height = vh;
			} else {
				width = vw;
				height = (int) (bh / ((float) bw / vw));
			}
			srcRect = new Rect(0, 0, bw, bh);
			destRect = new Rect(0, 0, width, height);
			break;
		case CENTER_CROP:
			vRation = (float) vw / vh;
			bRation = (float) bw / bh;
			int srcWidth;
			int srcHeight;
			if (vRation > bRation) {
				srcWidth = bw;
				srcHeight = (int) (vh * ((float) bw / vw));
				x = 0;
				y = (bh - srcHeight) / 2;
			} else {
				srcWidth = (int) (vw * ((float) bh / vh));
				srcHeight = bh;
				x = (bw - srcWidth) / 2;
				y = 0;
			}
			width = Math.min(vw, bw);
			height = Math.min(vh, bh);
			srcRect = new Rect(x, y, x + srcWidth, y + srcHeight);
			destRect = new Rect(0, 0, dwidth, dheight);
			break;
		case FIT_XY:
			width = vw;
			height = vh;
			srcRect = new Rect(0, 0, bw, bh);
			destRect = new Rect(0, 0, width, height);
			break;
		case CENTER:
		case MATRIX:
			width = Math.min(vw, bw);
			height = Math.min(vh, bh);
			x = (bw - width) / 2;
			y = (bh - height) / 2;
			srcRect = new Rect(x, y, x + width, y + height);
			destRect = new Rect(0, 0, width, height);
			break;
		}

		try {
			roundBitmap = getRoundedCornerBitmap(bitmap, roundPixels, srcRect,
					destRect, dwidth, dheight);
		} catch (OutOfMemoryError e) {
			roundBitmap = bitmap;
		}

		return roundBitmap;
	}

	private static Bitmap getRoundedCornerBitmap(Bitmap bitmap,
			int roundPixels, Rect srcRect, Rect destRect, int width, int height) {
		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final RectF destRectF = new RectF(destRect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(0xFF000000);
		canvas.drawRoundRect(destRectF, roundPixels, roundPixels, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, srcRect, destRectF, paint);

		return output;
	}

	/**
	 * generate a blurred bitmap from given one
	 * 
	 * referenced: http://incubator.quasimondo.com/processing/superfastblur.pde
	 * 
	 * @param original
	 * @param radius
	 * @return
	 */
	@Deprecated
	public static Bitmap getBlurredBitmap(Bitmap original, int radius) {
		if (radius < 1)
			return null;

		int width = original.getWidth();
		int height = original.getHeight();
		int wm = width - 1;
		int hm = height - 1;
		int wh = width * height;
		int div = radius + radius + 1;
		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, p1, p2, yp, yi, yw;
		int vmin[] = new int[Math.max(width, height)];
		int vmax[] = new int[Math.max(width, height)];
		int dv[] = new int[256 * div];
		for (i = 0; i < 256 * div; i++)
			dv[i] = i / div;

		int[] blurredBitmap = new int[wh];
		original.getPixels(blurredBitmap, 0, width, 0, 0, width, height);

		yw = 0;
		yi = 0;

		for (y = 0; y < height; y++) {
			rsum = 0;
			gsum = 0;
			bsum = 0;
			for (i = -radius; i <= radius; i++) {
				p = blurredBitmap[yi + Math.min(wm, Math.max(i, 0))];
				rsum += (p & 0xff0000) >> 16;
				gsum += (p & 0x00ff00) >> 8;
				bsum += p & 0x0000ff;
			}
			for (x = 0; x < width; x++) {
				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];

				if (y == 0) {
					vmin[x] = Math.min(x + radius + 1, wm);
					vmax[x] = Math.max(x - radius, 0);
				}
				p1 = blurredBitmap[yw + vmin[x]];
				p2 = blurredBitmap[yw + vmax[x]];

				rsum += ((p1 & 0xff0000) - (p2 & 0xff0000)) >> 16;
				gsum += ((p1 & 0x00ff00) - (p2 & 0x00ff00)) >> 8;
				bsum += (p1 & 0x0000ff) - (p2 & 0x0000ff);
				yi++;
			}
			yw += width;
		}

		for (x = 0; x < width; x++) {
			rsum = gsum = bsum = 0;
			yp = -radius * width;
			for (i = -radius; i <= radius; i++) {
				yi = Math.max(0, yp) + x;
				rsum += r[yi];
				gsum += g[yi];
				bsum += b[yi];
				yp += width;
			}
			yi = x;
			for (y = 0; y < height; y++) {
				blurredBitmap[yi] = 0xff000000 | (dv[rsum] << 16)
						| (dv[gsum] << 8) | dv[bsum];
				if (x == 0) {
					vmin[y] = Math.min(y + radius + 1, hm) * width;
					vmax[y] = Math.max(y - radius, 0) * width;
				}
				p1 = x + vmin[y];
				p2 = x + vmax[y];

				rsum += r[p1] - r[p2];
				gsum += g[p1] - g[p2];
				bsum += b[p1] - b[p2];

				yi += width;
			}
		}

		return Bitmap.createBitmap(blurredBitmap, width, height,
				Bitmap.Config.RGB_565);
	}

	/**
	 * 图像等比拉伸模糊
	 * 
	 * @param bm
	 *            bitmap图像
	 * @param radius  模糊程度 1-50
	 * @param width 图片等比宽
	 * @param height 图片等比高
	 * @return
	 */
	public static Drawable getBlurredImage(Bitmap bm,int radius, int width, int height) {
		BlurUtils _blurUtils = new BlurUtils(PhotoProUtils.multipleBitmap(bm,0.5f));
		Bitmap bitmap = getScaleNormalBitmap(
				_blurUtils.processNatively(radius),
				width, height);
		BitmapDrawable drawable= new BitmapDrawable(
				AppApplication.getContext().getResources(),
				bitmap);
		_blurUtils = null;
		return drawable;
	}
	
	/**
	 * 图像模糊
	 * 
	 * @param bm
	 *            bitmap图像
	 * @param radius  模糊程度 1-50
	 * @return
	 */
	public static Drawable getBlurredImage(Bitmap bm,int radius) {
		BlurUtils _blurUtils = new BlurUtils(PhotoProUtils.multipleBitmap(bm,0.5f));
		BitmapDrawable drawable= new BitmapDrawable(
				AppApplication.getContext().getResources(),
				_blurUtils.processNatively(radius));
		_blurUtils = null;
		return drawable;
	}
	

	/** Get the size in bytes of a bitmap. */
	// @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	public static int getBitmapSize(Bitmap bitmap) {
		// if (AndroidVersionCheckUtils.hasVersion3_1()) {
		// return bitmap.getByteCount();
		// }
		return bitmap.getRowBytes() * bitmap.getHeight();
	}
	
	/**
	 * 裁切图片.
	 * 
	 * @param imaOrg 原图片
	 * @param startX 起始X坐标
	 * @param startY 起始Y坐标
	 * @param width 宽度
	 * @param height 高度
	 * @return
	 */
	public static Bitmap cuteImage(Bitmap imaOrg, int startX, int startY, int width, int height) {
		if (imaOrg == null || imaOrg.isRecycled()) {
			return null;
		}
		
		Bitmap tempMap = null;
		try {
			tempMap = Bitmap.createBitmap(imaOrg, startX, startY, width, height);
		} catch (OutOfMemoryError e) {
			DebugUtils.error("裁切图片出错", new OutOfMemeryException());
			tempMap = imaOrg;
		}
		
		// 释放资源
		if (tempMap != imaOrg && imaOrg.getWidth() != width && imaOrg.getHeight() != height) {
			imaOrg.recycle();
			imaOrg = null;
		}
		
		return tempMap;
	}
	
	/**
	 * 根据宽度比例压缩图片(不需要圆角处理).
	 * 
	 * @param bitmap 原图片
	 * @param w 压缩后宽度
	 * @param h 压缩后高度
	 * @return
	 */
	public static Bitmap resizeBitmap(Bitmap bitmap, int w, int h) {
		return resizeBitmap(bitmap, w, h, false);
	}
	
	/**
	 * 根据宽度比例压缩图片.
	 * 
	 * @param bitmap 原图片
	 * @param newWidth 压缩后宽度
	 * @param newHeight 压缩后高度
	 * @param rounded 是否显示圆角
	 * @return
	 */
	public static Bitmap resizeBitmap(Bitmap bitmap, int newWidth, int newHeight, boolean rounded) {
		if (bitmap != null && !bitmap.isRecycled()) {
			// 图片原始尺寸
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			
			float scaleWidth = ((float) newWidth) / width;
			float scaleHeight = ((float) newHeight) / height;
			
			// 标识宽度是否超出
			boolean flag = false;
			
			Matrix matrix = new Matrix();
			boolean isMatrix = true;
			
			// 如果高度超出
			if (height * scaleWidth > newHeight) {
				flag = false;
				matrix.postScale(scaleWidth, scaleWidth);
			}
			// 如果宽度超出
			else {
				flag = true;
				matrix.postScale(scaleHeight, scaleHeight);
			}
			
			Bitmap resizedBitmap = null;
			try {
				if (isMatrix) {
					resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
				} else {
					resizedBitmap = bitmap;
				}
			} catch (OutOfMemoryError e) {
				DebugUtils.error("裁切图片出错", new OutOfMemeryException());
				resizedBitmap = bitmap;
			}
			
			// 释放资源
			if (resizedBitmap != bitmap) {
				bitmap.recycle();
				bitmap = null;
			}
			
			// 裁切图片, 以图片中心为基点
			if (newWidth > 0 && newHeight > 0) {
				int startX = resizedBitmap.getWidth() - newWidth;
				int startY = resizedBitmap.getHeight() - newHeight;
				resizedBitmap = cuteImage(resizedBitmap,
						flag ? (startX >= 0 ? startX : 0) / 2 : 0, 
						flag ? 0 : (startY >= 0 ? startY : 0) / 2,
						newWidth/* < resizedBitmap.getWidth() ? newWidth : resizedBitmap.getWidth()*/,
						newHeight/* < resizedBitmap.getHeight() ? newHeight : resizedBitmap.getHeight()*/);
			}
			
			// 圆角处理
			if (rounded) {
				resizedBitmap = getRoundedCornerBitmap(resizedBitmap, 10);
			}
			
			return resizedBitmap;
		} else {
			return null;
		}
	}
	
	/**
     * Calculate an inSampleSize for use in a {@link BitmapFactory.Options} object when decoding
     * bitmaps using the decode* methods from {@link BitmapFactory}. This implementation calculates
     * the closest inSampleSize that will result in the final decoded bitmap having a width and
     * height equal to or larger than the requested width and height. This implementation does not
     * ensure a power of 2 is returned for inSampleSize which can be faster when decoding but
     * results in a larger bitmap which isn't as useful for caching purposes.
     *
     * @param options An options object with out* params already populated (run through a decode*
     *            method with inJustDecodeBounds==true
     * @param reqWidth The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return The value to be used for inSampleSize
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
			if (width / reqWidth > height / reqHeight) {
				inSampleSize = Math.round(width / reqWidth);
            } else {
                inSampleSize = Math.round(height / reqHeight);
            }

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger
            // inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down
            // further.
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        
        return inSampleSize;
    }
    
    /**
	 * 设置灰色图片
	 * @param bitmap	Bitmap源图片
	 * @return
	 */
	public static final Bitmap grey(Bitmap bitmap) {
		 int width = bitmap.getWidth();
		 int height = bitmap.getHeight();
		  
		 Bitmap faceIconGreyBitmap = Bitmap
		   .createBitmap(width, height, Bitmap.Config.ARGB_8888);
		  
		 Canvas canvas = new Canvas(faceIconGreyBitmap);
		 Paint paint = new Paint();
		 ColorMatrix colorMatrix = new ColorMatrix();
		 colorMatrix.setSaturation(0);	//设置色饱和度
		 ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
		 paint.setColorFilter(colorMatrixFilter);
		 canvas.drawBitmap(bitmap, 0, 0, paint);
		 return faceIconGreyBitmap;
	}
	
	/**
	 * 通过decodeStream读取图片资源，减小内存占用
	 * @param context	上下文
	 * @param id		图片资源ID
	 * @return
	 */
	public static Bitmap readResourceBitmap(Context context, int id){
	     BitmapFactory.Options opt = new BitmapFactory.Options();
	     opt.inPreferredConfig=Bitmap.Config.RGB_565;	//表示16位位图 565代表对应三原色占的位数
	     opt.inInputShareable=true;
	     opt.inPurgeable=true;							//设置图片可以被回收
	     InputStream is = context.getResources().openRawResource(id);
	     context=null;
	     Bitmap local =BitmapFactory.decodeStream(is, null, opt);
	     return local;
	}
}
