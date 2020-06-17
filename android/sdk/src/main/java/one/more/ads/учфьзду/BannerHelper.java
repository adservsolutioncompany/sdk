package one.more.ads.учфьзду;

import androidx.annotation.NonNull;

import one.more.ads.учфьзду.ui.view.OneMoreWebView;

public class BannerHelper {

    public static void loadBanner(@NonNull OneMoreSdkObject sdkObj, @NonNull OneMoreWebView bannerView,
                                  @NonNull OneMoreSdkAdsBannerType banner, @NonNull OneMoreWebView.Listener listener){
        bannerView.loadAds(sdkObj.getAppKey(),
                sdkObj.getDomen(),
                sdkObj.getGoogleAdvKey(),
                banner.getValue());
        bannerView.setListener(listener);
    }


}
