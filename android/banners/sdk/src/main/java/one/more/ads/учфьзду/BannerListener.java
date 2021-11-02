package one.more.ads.учфьзду;

public interface BannerListener {
    void onBannerAdLoaded();

    void onBannerAdLoadFailed(Exception exp);

    void onBannerAdClicked();

    void onBannerAdScreenPresented();

    void onBannerAdScreenDismissed();

    void onBannerAdLeftApplication();
}

