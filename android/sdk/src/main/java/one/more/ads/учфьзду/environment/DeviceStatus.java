//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package one.more.ads.учфьзду.environment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.Build.VERSION;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

import org.json.JSONObject;

public class DeviceStatus {
    private static final String DEVICE_OS = "android";
    private static final String GOOGLE_PLAY_SERVICES_CLASS_NAME = "com.google.android.gms.ads.identifier.AdvertisingIdClient";
    private static final String GOOGLE_PLAY_SERVICES_GET_AID_INFO_METHOD_NAME = "getAdvertisingIdInfo";
    private static final String GOOGLE_PLAY_SERVICES_GET_AID_METHOD_NAME = "getId";
    private static final String GOOGLE_PLAY_SERVICES_IS_LIMITED_AD_TRACKING_METHOD_NAME = "isLimitAdTrackingEnabled";
    private static String uniqueId = null;
    private static final String MEDIATION_SHARED_PREFS = "Mediation_Shared_Preferences";
    public static final String UUID_ENABLED = "uuidEnabled";
    private static final String CACHED_UUID_KEY = "cachedUUID";

    public DeviceStatus() {
    }

    public static long getDeviceLocalTime() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        Date currentLocalTime = calendar.getTime();
        return currentLocalTime.getTime();
    }

    public static int getDeviceTimeZoneOffsetInMinutes() {
        return -(TimeZone.getDefault().getOffset(getDeviceLocalTime()) / '\uea60');
    }

    public static String[] getAdvertisingIdInfo(Context c) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> mAdvertisingIdClientClass = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient");
        Method getAdvertisingIdInfoMethod = mAdvertisingIdClientClass.getMethod("getAdvertisingIdInfo", Context.class);
        Object mInfoClass = getAdvertisingIdInfoMethod.invoke(mAdvertisingIdClientClass, c);
        Method getIdMethod = mInfoClass.getClass().getMethod("getId");
        Method isLimitAdTrackingEnabledMethod = mInfoClass.getClass().getMethod("isLimitAdTrackingEnabled");
        String advertisingId = getIdMethod.invoke(mInfoClass).toString();
        boolean isLimitedTrackingEnabled = (Boolean)isLimitAdTrackingEnabledMethod.invoke(mInfoClass);
        return new String[]{advertisingId, "" + isLimitedTrackingEnabled};
    }

    public static String getDeviceLanguage(Context c) throws Exception {
        return c.getResources().getConfiguration().locale.getLanguage();
    }

    public static String getNetworkClass(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null || !info.isConnected())
            return "-"; // not connected
        if (info.getType() == ConnectivityManager.TYPE_WIFI)
            return "WIFI";
        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int networkType = info.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:     // api< 8: replace by 11
                case TelephonyManager.NETWORK_TYPE_GSM:      // api<25: replace by 16
                    return "2G";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:   // api< 9: replace by 12
                case TelephonyManager.NETWORK_TYPE_EHRPD:    // api<11: replace by 14
                case TelephonyManager.NETWORK_TYPE_HSPAP:    // api<13: replace by 15
                case TelephonyManager.NETWORK_TYPE_TD_SCDMA: // api<25: replace by 17
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE:      // api<11: replace by 13
                case TelephonyManager.NETWORK_TYPE_IWLAN:    // api<25: replace by 18
                case 19: // LTE_CA
                    return "4G";
                case TelephonyManager.NETWORK_TYPE_NR:       // api<29: replace by 20
                    return "5G";
                default:
                    return "?";
            }
        }
        return "?";
    }

    private static long getFreeStorageInBytes(File f) {
        long SIZE_KB = 1024L;
        long SIZE_MB = 1048576L;
        StatFs stat = new StatFs(f.getPath());
        long res;
        if (VERSION.SDK_INT < 19) {
            res = (long)stat.getAvailableBlocks() * (long)stat.getBlockSize();
        } else {
            res = stat.getAvailableBlocksLong() * stat.getBlockSizeLong();
        }

        return res / 1048576L;
    }

    public static boolean isExternalMemoryAvailableWritable() {
        return "mounted".equals(Environment.getExternalStorageState()) && Environment.isExternalStorageRemovable();
    }

    public static String getMobileCarrier(Context c) {
        TelephonyManager tm = (TelephonyManager)c.getSystemService("phone");
        return tm.getNetworkOperatorName();
    }

    public static String getAndroidOsVersion() {
        return VERSION.RELEASE;
    }

    public static int getAndroidAPIVersion() {
        return VERSION.SDK_INT;
    }

    public static String getDeviceModel() {
        return Build.MODEL;
    }

    public static String getDeviceOEM() {
        return Build.MANUFACTURER;
    }

    public static String getDeviceOs() {
        return "android";
    }

    public static boolean isRootedDevice() {
        return findBinary("su");
    }

    private static boolean findBinary(String binaryName) {
        boolean found = false;

        try {
            String[] paths = new String[]{"/sbin/", "/system/bin/", "/system/xbin/", "/data/local/xbin/", "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/"};
            String[] var3 = paths;
            int var4 = paths.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String path = var3[var5];
                File file = new File(path + binaryName);
                if (file.exists()) {
                    found = true;
                    break;
                }
            }
        } catch (Exception var8) {
        }

        return found;
    }

    public static boolean isRTL(Context context) {
        Configuration config = context.getResources().getConfiguration();
        return VERSION.SDK_INT >= 17 && config.getLayoutDirection() == 1;
    }

    public static int getApplicationRotation(Context context) {
        Display defaultDisplay = ((WindowManager)context.getSystemService("window")).getDefaultDisplay();
        return defaultDisplay.getRotation();
    }

    public static float getSystemVolumePercent(Context context) {
        AudioManager audio = (AudioManager)context.getSystemService("audio");
        return (float)audio.getStreamVolume(3) / (float)audio.getStreamMaxVolume(3);
    }

    public static int getDisplayWidth() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getDisplayHeight() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    public static int getDeviceWidth() {
        return getDisplayWidth();
    }

    public static int getDeviceHeight() {
        return getDisplayHeight();
    }

    public static int getActivityRequestedOrientation(Context activity) {
        return activity instanceof Activity ? ((Activity)activity).getRequestedOrientation() : -1;
    }

    public static int getDeviceDefaultOrientation(Context context) {
        int rotation = getApplicationRotation(context);
        int orientation = getDeviceOrientation(context);
        return (rotation != 0 && rotation != 2 || orientation != 2) && (rotation != 1 && rotation != 3 || orientation != 1) ? 1 : 2;
    }

    public static int getDeviceOrientation(Context context) {
        return context.getResources().getConfiguration().orientation;
    }

    public static float getDeviceDensity() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return displayMetrics.density;
    }

    public static List<ApplicationInfo> getInstalledApplications(Context context) {
        PackageManager pm = context.getPackageManager();
        return pm.getInstalledApplications(0);
    }

    public static boolean isDeviceOrientationLocked(Context context) {
        return System.getInt(context.getContentResolver(), "accelerometer_rotation", 0) != 1;
    }

    public static File getExternalCacheDir(Context context) {
        return context.getExternalCacheDir();
    }

    public static String getInternalCacheDirPath(Context context) {
        String path = null;
        File internalFile = context.getCacheDir();
        if (internalFile != null) {
            path = internalFile.getPath();
        }

        return path;
    }

    public static int getNumberOfCores() {
        if(Build.VERSION.SDK_INT >= 17) {
            return Runtime.getRuntime().availableProcessors();
        }
        else {
            // Use saurabh64's answer
            return getNumCoresOldPhones();
        }
    }

    public static Long getRAM(Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        long totalRam = memoryInfo.totalMem / (1024 * 1024);
        return totalRam;
    }

    private static int getNumCoresOldPhones() {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if(Pattern.matches("cpu[0-9]+", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch(Exception e) {
            //Default to return 1 core
            return 1;
        }
    }

    public static long getAvailableInternalMemorySizeInMegaBytes() {
        File path = Environment.getDataDirectory();
        long res = getFreeStorageInBytes(path);
        return res;
    }

    public static long getAvailableMemorySizeInMegaBytes(String path) {
        return getFreeStorageInBytes(new File(path));
    }

    public static long getAvailableExternalMemorySizeInMegaBytes() {
        long res = 0L;
        if (isExternalMemoryAvailableWritable()) {
            File path = Environment.getExternalStorageDirectory();
            res = getFreeStorageInBytes(path);
        }

        return res;
    }

    @TargetApi(19)
    public static boolean isImmersiveSupported(Activity activity) {
        int uiOptions = activity.getWindow().getDecorView().getSystemUiVisibility();
        return (uiOptions | 4096) == uiOptions || (uiOptions | 2048) == uiOptions;
    }

    public static int getBatteryLevel(Context context) {
        int batteryLevel = -1;

        try {
            Intent batteryIntent = context.registerReceiver((BroadcastReceiver)null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            int level = batteryIntent != null ? batteryIntent.getIntExtra("level", -1) : 0;
            int scale = batteryIntent != null ? batteryIntent.getIntExtra("scale", -1) : 0;
            if (level != -1 && scale != -1) {
                batteryLevel = (int)((float)level / (float)scale * 100.0F);
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return batteryLevel;
    }

    public static synchronized String getOrGenerateOnceUniqueIdentifier(Context context) {
        if (!TextUtils.isEmpty(uniqueId)) {
            return uniqueId;
        } else {
            try {
                SharedPreferences preferences = context.getSharedPreferences("Mediation_Shared_Preferences", 0);
                boolean isEnabled = preferences.getBoolean("uuidEnabled", true);
                if (isEnabled) {
                    String id = preferences.getString("cachedUUID", "");
                    if (TextUtils.isEmpty(id)) {
                        uniqueId = UUID.randomUUID().toString();
                        Editor editor = preferences.edit();
                        editor.putString("cachedUUID", uniqueId);
                        editor.apply();
                    } else {
                        uniqueId = id;
                    }
                }
            } catch (Exception var5) {
                var5.printStackTrace();
            }

            return uniqueId;
        }
    }

    private static boolean isSystemPackage(ResolveInfo resolveInfo) {
        return (resolveInfo.activityInfo.applicationInfo.flags & 1) != 0;
    }

    public static JSONObject getAppsInstallTime(Context ctx, boolean includeSystemPackages) {
        Intent intent = new Intent("android.intent.action.MAIN", (Uri)null);
        intent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> pkgAppsList = ctx.getPackageManager().queryIntentActivities(intent, 0);
        JSONObject packagesInstalledPerDate = new JSONObject();
        PackageManager manager = ctx.getPackageManager();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        for(int i = 0; i < pkgAppsList.size(); ++i) {
            ResolveInfo resolveInfo = (ResolveInfo)pkgAppsList.get(i);

            try {
                if (includeSystemPackages || !isSystemPackage(resolveInfo)) {
                    PackageInfo packageInfo = manager.getPackageInfo(resolveInfo.activityInfo.packageName, 4096);
                    String installTime = sdf.format(new Date(packageInfo.firstInstallTime));
                    int numberOfInstalledApps = packagesInstalledPerDate.optInt(installTime, 0);
                    ++numberOfInstalledApps;
                    packagesInstalledPerDate.put(installTime, numberOfInstalledApps);
                }
            } catch (Exception var12) {
                var12.printStackTrace();
            }
        }

        return packagesInstalledPerDate;
    }
}
