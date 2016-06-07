package com.audionote.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

import com.audionote.R;
import com.audionote.app.AppApplication;
import com.audionote.constant.Constants;
import com.audionote.interfaces.IDialogCommitListener;
import com.audionote.widget.DialogEdit;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片上传工具类.
 * 
 */
@SuppressLint("NewApi")
public class PhotoFetchUtils {
	public static final int PHOTO_REQUEST_TAKEPHOTO = 1;
	public static final int PHOTO_REQUEST_GALLERY = 2;
	public static final int PHOTO_REQUEST_CUT = 3;

	public static final int MAX_WIDTH = ScreenUtils
			.screenDisplayMetrics(AppApplication.getContext()).widthPixels;
	public static final int MAX_HEIGHT = ScreenUtils
			.screenDisplayMetrics(AppApplication.getContext()).heightPixels;

	/**
	 * 照片选择器
	 *
	 * @param folderStr
	 *            如果选择拍照，传参：照片存储文件夹
	 * @param photoName
	 *            如果选择拍照，传参：照片名称
	 */
	public static void showSelectDialog(final Activity activity, String title,
			final String folderStr, final String photoName) {
		DialogEdit dialog = new DialogEdit(activity,
				new IDialogCommitListener() {
					@Override
					public void commit(String str) {
						getFromCamera(activity,
								PhotoFetchUtils.PHOTO_REQUEST_TAKEPHOTO,
								folderStr, photoName);
					}
				});
		dialog.setTitle(title);
		dialog.mEdit.setVisibility(View.GONE);
		dialog.commit.setText(activity.getString(R.string.take_photo));
		dialog.cancel.setText(activity.getString(R.string.album));
		dialog.cancel.setTag(dialog);
		dialog.cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PhotoFetchUtils.getFromAlbum(activity,
						PhotoFetchUtils.PHOTO_REQUEST_GALLERY);
				((Dialog) v.getTag()).dismiss();
			}
		});
		dialog.show();

	}

	/**
	 * 照片选择器
	 */
	public static void showSelectDialog(final Fragment fragment, String title,
			final String folderStr, final String photoName) {
		DialogEdit dialog = new DialogEdit(fragment.getActivity(),
				new IDialogCommitListener() {
					@Override
					public void commit(String str) {
						getFromCamera(fragment,
								PhotoFetchUtils.PHOTO_REQUEST_TAKEPHOTO,
								folderStr, photoName);
					}
				});
		dialog.setTitle(title);
		dialog.mEdit.setVisibility(View.GONE);
		dialog.commit.setText(fragment.getString(R.string.take_photo));
		dialog.cancel.setText(fragment.getString(R.string.album));
		dialog.cancel.setTag(dialog);
		dialog.cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PhotoFetchUtils.getFromAlbum(fragment,
						PhotoFetchUtils.PHOTO_REQUEST_GALLERY);
				((Dialog) v.getTag()).dismiss();
			}
		});
		dialog.show();
	}

	/**
	 * 从相册获取照片.
	 */
	public static void getFromAlbum(Activity activity, int requestCode) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);

		// activity.startActivityForResult(intent, requestCode);
		activity.startActivityForResult(
				Intent.createChooser(intent, "Select Picture"), requestCode);
	}

	/**
	 * 从相册获取照片.
	 */
	public static void getFromAlbum(Fragment fragment, int requestCode) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);

		fragment.startActivityForResult(intent, requestCode);
	}

	/**
	 * 从相机获取照片
	 */
	public static void getFromCamera(Activity activity, int requestCode,
			String folderStr, String photoName) {
		File folder = new File(folderStr);
		if (folder != null && !folder.exists()) {
			folder.mkdirs();
		}

		File photo = new File(folder, photoName);

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * 从相机获取照片
	 */
	public static void getFromCamera(Fragment fragment, int requestCode,
			String folderStr, String photoName) {
		File folder = new File(folderStr);
		if (folder != null && !folder.exists()) {
			folder.mkdirs();
		}

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(folder, photoName)));
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

		fragment.startActivityForResult(intent, requestCode);
	}

	public static void startPhotoZoom(Fragment fragmrnt, Uri uri) {
		startPhotoZoom(fragmrnt, uri, Constants.IMAGE_MIN_SIZE);
	}

	public static void startPhotoZoom(Activity activity, Uri uri) {
		startPhotoZoom(activity, uri, Constants.IMAGE_MIN_SIZE);
	}

	/** 截图 */
	public static void startPhotoZoom(Activity activity, Uri uri, int size) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");

		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);

		activity.startActivityForResult(intent,
				PhotoFetchUtils.PHOTO_REQUEST_CUT);
	}

	/** 截图 */
	public static void startPhotoZoom(Fragment fragment, Uri uri, int size) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");

		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);

		fragment.startActivityForResult(intent,
				PhotoFetchUtils.PHOTO_REQUEST_CUT);
	}

	/**
	 * 如果正常方式获取相册图片失败，采取转存的方式，先存到新文件之后再读取新文件
	 * 
	 * @param context
	 * @param uri
	 * @param newTempFile
	 *            转存文件
	 * @return
	 */
	public static String getPathIfFail(final Context context, final Uri uri,
			File newTempFile) {
		String path = getPath(context, uri);
		if (path == null) {
			try {
				if (StreamUtils.saveStreamToFile(context.getContentResolver()
						.openInputStream(uri), newTempFile)) {
					Uri newUri = Uri.fromFile(newTempFile);
					path = getPath(context, newUri);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		return path;
	}

	/**
	 * 获取文件Uri中的路径.
	 */
	public static String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/"
							+ split[1];
				}

				// TODO handle non-primary volumes
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection,
						selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 * 
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @param selection
	 *            (Optional) Filter used in the query.
	 * @param selectionArgs
	 *            (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri,
			String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection,
					selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGoogleDrivePhotosUri(Uri uri) {
		return "com.google.android.apps.docs.storage"
				.equals(uri.getAuthority());
	}

	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 对上传的照片进行压缩处理.
	 * 
	 * @param srcPhoto
	 *            原始照片文件
	 * @param uploadPhotoName
	 *            压缩后待上传的文件名
	 * @return
	 * @throws OutOfMemoryError
	 */
	public static File compressUploadPhoto(File srcPhoto, String folderStr,
			String uploadPhotoName) throws OutOfMemoryError {
		File folder = new File(folderStr);
		if (folder != null && !folder.exists()) {
			folder.mkdirs();
		}

		final String imageFileName = srcPhoto.getAbsolutePath();
		File uploadFile = null;

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options opts = new BitmapFactory.Options();

		// Calculate inSampleSize
		final int inSampleSize = PhotoProUtils.calculateInSampleSize(opts,
				MAX_WIDTH, MAX_HEIGHT);
		opts.inSampleSize = inSampleSize;

		// Decode bitmap with inSampleSize set
		opts.inPreferredConfig = Config.RGB_565;
		opts.inDither = true;
		opts.inJustDecodeBounds = false;
		Bitmap bm = BitmapFactory.decodeFile(imageFileName, opts);

		BufferedOutputStream bufOutput = null;
		try {
			uploadFile = new File(folderStr, uploadPhotoName);
			if (uploadFile.exists()) {
				uploadFile.delete();
			}
			bufOutput = new BufferedOutputStream(new FileOutputStream(
					uploadFile));
			// output bitmap to file
			bm.compress(Bitmap.CompressFormat.JPEG, 70, bufOutput);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bm != null) {
				bm.recycle();
			}
			if (bufOutput != null) {
				try {
					bufOutput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return uploadFile;
	}

	/**
	 * 对上传的照片进行压缩处理.
	 */
	public static File compressUploadPhoto(Bitmap bitmap, String folderStr,
			String uploadPhotoName) throws OutOfMemoryError {

		File folder = new File(folderStr);
		if (folder != null && !folder.exists()) {
			folder.mkdirs();
		}
		File uploadFile = null;

		final BitmapFactory.Options opts = new BitmapFactory.Options();
		// Calculate inSampleSize
		final int inSampleSize = PhotoProUtils.calculateInSampleSize(opts,
				MAX_WIDTH, MAX_HEIGHT);
		opts.inSampleSize = inSampleSize;

		// Decode bitmap with inSampleSize set
		opts.inPreferredConfig = Config.RGB_565;
		opts.inDither = true;
		opts.inJustDecodeBounds = false;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		InputStream isBm = new ByteArrayInputStream(baos.toByteArray());

		Bitmap bm = BitmapFactory.decodeStream(isBm, null, opts);

		BufferedOutputStream bufOutput = null;
		try {
			uploadFile = new File(folderStr, uploadPhotoName);
			if (uploadFile.exists()) {
				uploadFile.delete();
			}
			bufOutput = new BufferedOutputStream(new FileOutputStream(
					uploadFile));
			// output bitmap to file
			bm.compress(Bitmap.CompressFormat.JPEG, 70, bufOutput);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bitmap != null) {
				bitmap.recycle();
			}

			if (bm != null) {
				bm.recycle();
			}
			if (bufOutput != null) {
				try {
					bufOutput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return uploadFile;
	}

}
