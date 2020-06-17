package one.more.ads.учфьзду;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import java.lang.ref.WeakReference;

import one.more.ads.учфьзду.environment.DeviceStatus;
import one.more.ads.учфьзду.integration.IntegrationHelper;

public class OneMoreSdkObject {

    private static volatile OneMoreSdkObject sInstance;

    private ListenersWrapper mListenersWrapper;

    private WeakReference<Activity> mActivityWeekRef;
    private String mAppKey;
    private String mDomen;
    private String mGoogleAdvId;

    private OneMoreSdkObject() {

    }

    public static synchronized OneMoreSdkObject getInstance() {
        if (sInstance == null) {
            sInstance = new OneMoreSdkObject();
        }
        return sInstance;
    }

    public synchronized void init(Activity activity, String appKey,String domen) {
        IntegrationHelper.validateIntegration(activity);
        mActivityWeekRef = new WeakReference<>(activity);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mGoogleAdvId = getAdvertiserId(mActivityWeekRef.get());
            }
        }).start();
        mAppKey = appKey;
        mDomen = domen;
    }

    public String getAppKey() {
        return mAppKey;
    }

    public String getDomen() {
        return mDomen;
    }

    public String getGoogleAdvKey(){
        return mGoogleAdvId;
    }

    @WorkerThread
    public String getAdvertiserId(Context context) {
        try {
            String[] deviceInfo = DeviceStatus.getAdvertisingIdInfo(context);
            return deviceInfo.length > 0 && deviceInfo[0] != null ? deviceInfo[0] : "";
        } catch (Exception var3) {
             return "";
        }
    }

    public void setInterstitialListener(@NonNull InterstitialListener listener) {
        this.mListenersWrapper.setInterstitialListener(listener);
       // ISListenerWrapper.getInstance().setListener(listener);
    //    CallbackThrottler.getInstance().setInterstitialListener(listener);
    }


}
