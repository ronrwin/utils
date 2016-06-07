package com.audionote.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import org.json.JSONArray;
import org.json.JSONException;

import com.audionote.entity.Stamp;

/**
 * 文件信息类
 * 
 * @author Win.FR
 *
 */
public class FileUtils {

	/**
	 * 返回自定文件或文件夹的大小
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(File f) throws Exception {
		long s = 0;
		if (f.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(f);
			s = fis.available();
			fis.close();
		} else {
			f.createNewFile();
			System.out.println("File not Exist...");
		}
		return s;
	}

	/**
	 * 使用递归方法取得文件夹大小
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSizes(File f) throws Exception {
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory())
				size = size + getFileSizes(flist[i]);
			else
				size = size + flist[i].length();
		}
		return size;
	}

	/**
	 * 转换文件大小
	 * 
	 * @param fileS
	 * @return
	 */
	public static String formatFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#0.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 递归求取目录文件个数
	 * 
	 * @param f
	 * @return
	 */
	public static long getList(File f) {
		long size = 0;
		File flist[] = f.listFiles();
		size = flist.length;
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getList(flist[i]);
				size--;
			}
		}
		return size;
	}

	/** 获取存储文件夹地址 */
	public static String getFolderPath(String folder) {
		return getFolderFile(folder).getAbsolutePath();
	}

	/** 获取存储文件夹 */
	public static File getFolderFile(String folder) {
		File dir = new File(DeviceUtils.getDiskCacheDir() + File.separator
				+ folder);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	public static void deleteFile(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
				return;
			}

			if (file.isDirectory()) {
				File[] childFiles = file.listFiles();
				if (childFiles == null || childFiles.length == 0) {
					file.delete();
					return;
				}

				for (int i = 0; i < childFiles.length; i++) {
					deleteFile(childFiles[i]);
				}
				file.delete();
			}
		}
	}

	public static void writeStringToFile(String str, String filePath) {
		try {
			FileOutputStream out = new FileOutputStream(filePath);
			StringBuffer sb = new StringBuffer(str);
			out.write(sb.toString().getBytes("UTF-8"));

			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static String readStringFromFile(String filePath) {
		StringBuffer sb = new StringBuffer("");
		try {
			FileInputStream fis = new FileInputStream(filePath);
			byte[] buf = new byte[1024];
			while ((fis.read(buf)) != -1) {
				sb.append(new String(buf));
				buf = new byte[1024];// 重新生成，避免和上次读取的数据重复
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sb.toString();
	}
}
