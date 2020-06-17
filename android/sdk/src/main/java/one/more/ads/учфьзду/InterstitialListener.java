package one.more.ads.учфьзду;

public interface InterstitialListener {
    void onInterstitialAdReady();

    void onInterstitialAdLoadFailed(Exception exp);

    void onInterstitialAdOpened();

    void onInterstitialAdClosed();

    void onInterstitialAdShowSucceeded();

    void onInterstitialAdShowFailed(Exception exp);

    void onInterstitialAdClicked();
}
