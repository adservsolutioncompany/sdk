package one.more.ads.учфьзду;

import android.app.Activity;
import android.content.Intent;

import one.more.ads.учфьзду.ui.OneMoreSdkAdvActivity;

public abstract class OneMoreSdk {

    public static void init(Activity activity, String appKey,String domen) {
        OneMoreSdkObject.getInstance().init(activity, appKey,domen);
    }

    public static void setInterstitialListener(InterstitialListener listener) {
        OneMoreSdkObject.getInstance().setInterstitialListener(listener);
    }

    public static OneMoreBannerLayout createBanner(Activity activity, ISBannerSize size) {
      //  return OneMoreSdkObject.getInstance().createBanner(activity, size);
        return null;
    }

    public static void forceInterstitial(Activity activity, int requestCode, OneMoreSdkAdvActivity.Orientation orientation){
       Intent intent =  OneMoreSdkAdvActivity.getIntent(activity,
               OneMoreSdkAdsFullScreenType.INTERSTITIAL,orientation);
        activity.startActivityForResult(intent,requestCode);
    }

    public static void forceVideo(Activity activity,int requestCode, OneMoreSdkAdvActivity.Orientation orientation){
        Intent intent =  OneMoreSdkAdvActivity.getIntent(activity,
                OneMoreSdkAdsFullScreenType.VIDEO,orientation);
        activity.startActivityForResult(intent,requestCode);
    }

}
