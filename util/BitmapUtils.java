package com.audionote.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class BitmapUtils {

	/**
	 * secure decode file to bitmap scale the file if necessary
	 */
	public static Bitmap secureDecodeFile(String imagePath) {
		BitmapFactory.Options option = new BitmapFactory.Options();
		int IMAGE_MAX_WIDTH = PhotoFetchUtils.MAX_WIDTH;
		int IMAGE_MAX_HEIGHT = PhotoFetchUtils.MAX_HEIGHT;
		option.inSampleSize = getImageScale(imagePath, IMAGE_MAX_WIDTH,
				IMAGE_MAX_HEIGHT);
		Bitmap bm = null;
		try {
			bm = BitmapFactory.decodeFile(imagePath, option);
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}

		return bm;
	}

	/**
	 * secure decode file to bitmap scale the file to the maximum width and
	 * height
	 */
	public static Bitmap secureDecodeFile(String imagePath, int maxW, int maxH) {
		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inSampleSize = getImageScale(imagePath, maxW, maxH);

		return BitmapFactory.decodeFile(imagePath, option);
	}

	/**
	 * scale image to fixed height and weight
	 *
	 */
	private static int getImageScale(String imagePath, int maxW, int maxH) {
		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inJustDecodeBounds = true;
		Bitmap b = BitmapFactory.decodeFile(imagePath, option);

		int scale = 1;
		while (option.outWidth / scale >= maxW
				|| option.outHeight / scale >= maxH) {
			scale *= 2;
		}
		if (b != null) {
			b.recycle();
		}
		return scale;
	}

	public static Bitmap getColorBitmap(int color) {
		Bitmap bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(color);
		canvas.drawBitmap(bitmap, 0, 0, null);
		return bitmap;
	}

	public static Bitmap getBitmapFromDrawable(Drawable drawable){
		if(drawable == null){
			return null;
		}

		if(drawable instanceof DrawableContainer) {
			return getBitmapFromDrawable(drawable.getCurrent());
		}else if(drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}else{
			return null;
		}
	}

	/**
	 * convert Bitmap to byte array
	 *
	 * @param b
	 * @return
	 */
	public static byte[] bitmapToByte(Bitmap b) {
		if (b == null) {
			return null;
		}

		ByteArrayOutputStream o = new ByteArrayOutputStream();
		b.compress(Bitmap.CompressFormat.PNG, 100, o);
		return o.toByteArray();
	}

	/**
	 * convert byte array to Bitmap
	 *
	 * @param b
	 * @return
	 */
	public static Bitmap byteToBitmap(byte[] b, BitmapFactory.Options opts) {
		return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length, opts);
	}

	/**
	 * convert Drawable to Bitmap
	 *
	 * @param d
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable d) {
		return d == null ? null : ((BitmapDrawable)d).getBitmap();
	}

	/**
	 * convert Bitmap to Drawable
	 *
	 * @param b
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap b) {
		return b == null ? null : new BitmapDrawable(b);
	}

	/**
	 * convert Drawable to byte array
	 *
	 * @param d
	 * @return
	 */
	public static byte[] drawableToByte(Drawable d) {
		return bitmapToByte(drawableToBitmap(d));
	}

	/**
	 * scale image
	 *
	 * @param org
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
		return scaleImage(org, (float)newWidth / org.getWidth(), (float)newHeight / org.getHeight());
	}

	/**
	 * scale image
	 *
	 * @param org
	 * @param scaleWidth sacle of width
	 * @param scaleHeight scale of height
	 * @return
	 */
	public static Bitmap scaleImage(Bitmap org, float scaleWidth, float scaleHeight) {
		if (org == null) {
			return null;
		}

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
	}

	/**
	 * scale image
	 */
	public static Bitmap rotateImage(Bitmap b, int degrees) {
		if (b == null) {
			return null;
		}
		Matrix m = new Matrix();
		m.setRotate(degrees, (float) b.getWidth() / 2, (float) b.getHeight() / 2);

		return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
	}

	/**
	 * close inputStream
	 *
	 * @param s
	 */
	private static void closeInputStream(InputStream s) {
		if (s == null) {
			return;
		}

		try {
			s.close();
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		}
	}
}
