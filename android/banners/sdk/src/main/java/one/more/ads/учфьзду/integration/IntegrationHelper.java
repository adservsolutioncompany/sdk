//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package one.more.ads.учфьзду.integration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import one.more.ads.учфьзду.OneMoreSdkObject;
import one.more.ads.учфьзду.environment.DeviceStatus;

public class IntegrationHelper {
    private static final String TAG = "IntegrationHelper";
    private static final String SDK_COMPATIBILITY_VERSION = "4.1";
    private static final String BANNER_COMPATIBILITY_VERSION = "4.3";

    public IntegrationHelper() {
    }

    public static void validateIntegration(Activity activity) {
        Log.i("IntegrationHelper", "Verifying Integration:");
        validatePermissions(activity);
        validateGooglePlayServices(activity);
    }

    private static boolean isServicesValid(Activity activity, String[] services) {
        if (services == null) {
            return true;
        } else {
            PackageManager packageManager = activity.getPackageManager();
            Log.i("IntegrationHelper", "*** Services ***");
            boolean ret = true;
            String[] var4 = services;
            int var5 = services.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String service = var4[var6];

                try {
                    Class localClass = Class.forName(service);
                    Intent intent = new Intent(activity, localClass);
                    List<ResolveInfo> list = packageManager.queryIntentServices(intent, 65536);
                    if (list.size() > 0) {
                        Log.i("IntegrationHelper", service + " - VERIFIED");
                    } else {
                        ret = false;
                        Log.e("IntegrationHelper", service + " - MISSING");
                    }
                } catch (ClassNotFoundException var11) {
                    ret = false;
                    Log.e("IntegrationHelper", service + " - MISSING");
                }
            }

            return ret;
        }
    }

    private static boolean isExternalLibsValid(ArrayList<Pair<String, String>> externalLibs) {
        if (externalLibs == null) {
            return true;
        } else {
            Log.i("IntegrationHelper", "*** External Libraries ***");
            boolean ret = true;
            Iterator var2 = externalLibs.iterator();

            while(var2.hasNext()) {
                Pair externalLibrary = (Pair)var2.next();

                try {
                    Class c = Class.forName((String)externalLibrary.first);
                    Log.i("IntegrationHelper", (String)externalLibrary.second + " - VERIFIED");
                } catch (ClassNotFoundException var5) {
                    ret = false;
                    Log.e("IntegrationHelper", (String)externalLibrary.second + " - MISSING");
                }
            }

            return ret;
        }
    }

    private static boolean isActivitiesValid(Activity activity, String[] activities) {
        if (activities == null) {
            return true;
        } else {
            Log.i("IntegrationHelper", "*** Activities ***");
            boolean ret = true;
            String[] var3 = activities;
            int var4 = activities.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String act = var3[var5];

                try {
                    Class localClass = Class.forName(act);
                    Intent intent = new Intent(activity, localClass);
                    List<ResolveInfo> list = activity.getPackageManager().queryIntentActivities(intent, 65536);
                    if (list.size() > 0) {
                        Log.i("IntegrationHelper", act + " - VERIFIED");
                    } else {
                        ret = false;
                        Log.e("IntegrationHelper", act + " - MISSING");
                    }
                } catch (ClassNotFoundException var10) {
                    ret = false;
                    Log.e("IntegrationHelper", act + " - MISSING");
                }
            }

            return ret;
        }
    }

    private static void validatePermissions(Activity activity) {
        Log.i("IntegrationHelper", "*** Permissions ***");
        PackageManager pm = activity.getPackageManager();
        if (pm.checkPermission("android.permission.INTERNET", activity.getPackageName()) == 0) {
            Log.i("IntegrationHelper", "android.permission.INTERNET - VERIFIED");
        } else {
            Log.e("IntegrationHelper", "android.permission.INTERNET - MISSING");
        }

        if (pm.checkPermission("android.permission.ACCESS_NETWORK_STATE", activity.getPackageName()) == 0) {
            Log.i("IntegrationHelper", "android.permission.ACCESS_NETWORK_STATE - VERIFIED");
        } else {
            Log.e("IntegrationHelper", "android.permission.ACCESS_NETWORK_STATE - MISSING");
        }

    }

    private static void validateGooglePlayServices(final Activity activity) {
        String mGooglePlayServicesMetaData = "com.google.android.gms.version";
        String mGooglePlayServices = "Google Play Services";
        Thread thread = new Thread() {
            public void run() {
                try {
                    Log.w("IntegrationHelper", "--------------- Google Play Services --------------");
                    PackageManager packageManager = activity.getPackageManager();
                    ApplicationInfo ai = packageManager.getApplicationInfo(activity.getPackageName(), 128);
                    Bundle bundle = ai.metaData;
                    boolean exists = bundle.containsKey("com.google.android.gms.version");
                    if (exists) {
                        Log.i("IntegrationHelper", "Google Play Services - VERIFIED");
                        String gaid = OneMoreSdkObject.getInstance().getAdvertiserId(activity);
                        if (!TextUtils.isEmpty(gaid)) {
                            Log.i("IntegrationHelper", "GAID is: " + gaid + " (use this for test devices)");
                        }
                    } else {
                        Log.e("IntegrationHelper", "Google Play Services - MISSING");
                    }
                } catch (Exception var6) {
                    Log.e("IntegrationHelper", "Google Play Services - MISSING");
                }

            }
        };
        thread.start();
    }

    public String getAdvertiserId(Context context) {
        try {
            String[] deviceInfo = DeviceStatus.getAdvertisingIdInfo(context);
            return deviceInfo.length > 0 && deviceInfo[0] != null ? deviceInfo[0] : "";
        } catch (Exception var3) {
            return "";
        }
    }


}
