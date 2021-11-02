package one.more.ads.учфьзду;

import android.view.View;
import android.widget.FrameLayout;

public interface BannerSmashListener {
    void onBannerInitSuccess();

    void onBannerInitFailed(Exception error);

    void onBannerAdLoaded(View var1, FrameLayout.LayoutParams var2);

    void onBannerAdLoadFailed(Exception error);

    void onBannerAdClicked();

    void onBannerAdScreenPresented();

    void onBannerAdScreenDismissed();

    void onBannerAdLeftApplication();
}

