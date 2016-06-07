package com.audionote.util;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.audionote.R;
import com.audionote.app.AppApplication;
import com.audionote.constant.NetworkType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/**
 * 手机设备相关工具类.
 */
public class DeviceUtils {
    public static final int NETWORK_NONE = -1;
    public static final int NETWORK_WIFI = 0;
    public static final int NETWORK_UNINET = 1;
    public static final int NETWORK_UNIWAP = 2;
    public static final int NETWORK_WAP_3G = 3;
    public static final int NETWORK_NET_3G = 4;
    public static final int NETWORK_CMWAP = 5;
    public static final int NETWORK_CMNET = 6;
    public static final int NETWORK_CTWAP = 7;
    public static final int NETWORK_CTNET = 8;
    public static final int NETWORK_MOBILE = 9;

    public DeviceUtils() {

    }

    /**
     * 判断是否已连接到网络
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查网络接连类型.
     *
     * @return NetworkType.NONE: 无网络连接<br>
     * NetworkType.WIFI: 通过WIFI连接网络<br>
     * NetworkType.CMWAP: 通过移动, 联通GPRS连接网络<br>
     * NetworkType.CTWAP: 通过电信GPRS连接网络<br>
     */
    public static int checkNetWorkType(Context context) {
        if (isAirplaneModeOn(context)) {
            return NetworkType.NONE;
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED) {
            return NetworkType.WIFI;
        }
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState() == NetworkInfo.State.CONNECTED) {
            String type = connectivityManager.getActiveNetworkInfo()
                    .getExtraInfo();
            if (!StringUtils.isEmpty(type)
                    && type.toLowerCase().contains("wap")) {
                if (type.toLowerCase().contains("ctwap")) {
                    return NetworkType.CTWAP;
                } else {
                    return NetworkType.CMWAP;
                }
            } else {
                return NetworkType.WIFI;
            }
        }
        return NetworkType.NONE;
    }

    public static int checkNetWorkTypeAll(Context context) {
        // 飞行模式
        if (isAirplaneModeOn(context)) {
            return NETWORK_NONE;
        }
        ConnectivityManager mag = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = mag.getActiveNetworkInfo();
        // 没有网络
        if (info == null) {
            return NETWORK_NONE;
        }
        if (info.getTypeName().equals("WIFI")) {
            return NETWORK_WIFI;
        } else {
            if (info.getExtraInfo().equals("uninet"))
                return NETWORK_UNINET;
            if (info.getExtraInfo().equals("uniwap"))
                return NETWORK_UNIWAP;
            if (info.getExtraInfo().equals("3gwap"))
                return NETWORK_WAP_3G;
            if (info.getExtraInfo().equals("3gnet"))
                return NETWORK_NET_3G;
            if (info.getExtraInfo().equals("cmwap"))
                return NETWORK_CMWAP;
            if (info.getExtraInfo().equals("cmnet"))
                return NETWORK_CMNET;
            if (info.getExtraInfo().equals("ctwap"))
                return NETWORK_CTWAP;
            if (info.getExtraInfo().equals("ctnet"))
                return NETWORK_CTNET;

            return NETWORK_MOBILE;
        }
    }

    /**
     * 判断手机是否处于飞行模式.
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isAirplaneModeOn(Context context) {
        if (AndroidVersionCheckUtils.hasVersion4_2()) {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        }

    }

    /**
     * 获取设备序列号.
     */
    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    /**
     * 判断手机SDCard是否已安装并可读写.
     */
    public static boolean isSDCardUsable() {
        return Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment
                .getExternalStorageState());
    }

    /**
     * 隐藏某焦点控件弹出的软件键盘.
     */
    public static void hideSoftInputFromWindlw(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        IBinder binder = view.getWindowToken();
        inputMethodManager.hideSoftInputFromInputMethod(binder, 0);
    }

    /**
     * Check how much usable space is available at a given path.
     *
     * @param path The path to check
     * @return The space available in bytes
     */
    @SuppressWarnings("deprecation")
    public static long getUsableSpace(File path) {
        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }

    /**
     * all size
     *
     */
    public static long getAllSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getBlockCount();
        return availableBlocks * blockSize;
    }

    /**
     * Get the memory class of this device (approx. per-app memory limit)
     *
     */
    public static int getMemoryClass(Context context) {
        return ((ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
    }

    /**
     * 获取文件路径
     */
    public static File getDiskCacheDir(Context context) {
        File cacheDir;
        if (isSDCardUsable()) {
            cacheDir = new File(Environment.getExternalStorageDirectory(),
                    context.getResources().getString(
                            R.string.default_app_folder_name));
        } else {
            cacheDir = context.getFilesDir();
        }

        if (cacheDir != null && !cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir;
    }

    /**
     * 获取文件路径
     */
    public static File getDiskCacheDir() {
        return getDiskCacheDir(AppApplication.getContext());
    }

    /**
     * 获取CPU核心个数.
     */
    public static int getCpuCoreCount() {
        int coreCount = 1;
        try {
            String cpuDirPath = "/sys/devices/system/cpu";
            File dir = new File(cpuDirPath);
            String[] cpuFiles = dir.list(new FilenameFilter() {

                @Override
                public boolean accept(File dir, String filename) {
                    return Pattern.matches("cpu\\d{1}", filename);
                }
            });
            if (cpuFiles != null && cpuFiles.length > 0) {
                coreCount = cpuFiles.length;
            }
        } catch (Exception e) {
            DebugUtils.error(e.getMessage(), e);
        }
        return coreCount;
    }

    /**
     * 获取CPU信息.
     *
     * @return "CPU核心个数 x CPU频率"
     */
    public static String getCpuInfo() {
        return getCpuCoreCount() + " x " + getCpuFrequency();
    }

    /**
     * 获取CPU频率.
     */
    public static String getCpuFrequency() {
        String cpuFreq = "";
        BufferedReader bufferedReader = null;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            ProcessBuilder cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            bufferedReader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            cpuFreq = bufferedReader.readLine();

            float tempFreq = Float.valueOf(cpuFreq.trim());
            cpuFreq = tempFreq / (1000 * 1000) + "Gb";
            return cpuFreq;
        } catch (Exception e) {
            return StringUtils.isEmpty(cpuFreq) ? "N/A" : cpuFreq + "Kb";
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // ignore.
                }
            }
        }
    }

    /**
     * 获取系统当前可用内存.
     */
    public static String getSystemAvailMemory(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo memoryInfo = new MemoryInfo();
        // 内存大小规格化, Byte转换为KB或者MB
        return Formatter.formatFileSize(context, memoryInfo.availMem);
    }

    /**
     * 获得系统总内存大小.
     */
    public static String getSystemTotalMemory(Context context) {
        String memInfoFilePath = "/proc/meminfo";
        String firstLine;
        String[] arrayOfString;
        long initialMemory = 0;
        BufferedReader localBufferedReader = null;
        try {
            FileReader localFileReader = new FileReader(memInfoFilePath);
            localBufferedReader = new BufferedReader(localFileReader, 10240);
            // 读取meminfo第一行, 系统总内存大小
            firstLine = localBufferedReader.readLine();
            arrayOfString = firstLine.split("\\s+");
            // 获得系统总内存, 单位是KB, 乘以1024转换为Byte
            initialMemory = Long.valueOf(arrayOfString[1].trim()) * 1024;
        } catch (Exception e) {
            DebugUtils.error(e.getMessage(), e);
        } finally {
            if (localBufferedReader != null) {
                try {
                    localBufferedReader.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

        // 内存大小规格化, Byte转换为KB或者MB
        return Formatter.formatFileSize(context, initialMemory);
    }

}
