package one.more.ads.учфьзду;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.FrameLayout;

public class OneMoreBannerLayout extends FrameLayout {
    private View mBannerView;
    private ISBannerSize mSize;
    private String mPlacementName;
    private Activity mActivity;
    private boolean isDestroyed = false;
    private boolean mIsBannerDisplayed = false;
    private BannerListener mBannerListener;

    public OneMoreBannerLayout(Activity activity, ISBannerSize size) {
        super(activity);
        this.mActivity = activity;
        if (size == null) {
            size = ISBannerSize.BANNER;
        }

        this.mSize = size;
    }

    protected void destroyBanner() {
        this.isDestroyed = true;
        this.mBannerListener = null;
        this.mActivity = null;
        this.mSize = null;
        this.mPlacementName = null;
        this.mBannerView = null;
    }

    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    public View getBannerView() {
        return this.mBannerView;
    }

    public Activity getActivity() {
        return this.mActivity;
    }

    public ISBannerSize getSize() {
        return this.mSize;
    }

    public String getPlacementName() {
        return this.mPlacementName;
    }

    public void setPlacementName(String placementName) {
        this.mPlacementName = placementName;
    }

    public void setBannerListener(BannerListener listener) {
        String logMessage = "setBannerListener()";
        this.mBannerListener = listener;
    }

    public void removeBannerListener() {
        String logMessage = "removeBannerListener()";
        this.mBannerListener = null;
    }

    public BannerListener getBannerListener() {
        return this.mBannerListener;
    }

    void sendBannerAdLoaded() {
        if (this.mBannerListener != null && !this.mIsBannerDisplayed) {
            this.mBannerListener.onBannerAdLoaded();
        }

        this.mIsBannerDisplayed = true;
    }

    void sendBannerAdLoadFailed(final Exception error) {
        (new Handler(Looper.getMainLooper())).post(new Runnable() {
            public void run() {
                if (OneMoreBannerLayout.this.mIsBannerDisplayed) {
                    OneMoreBannerLayout.this.mBannerListener.onBannerAdLoadFailed(error);
                } else {
                    try {
                        if (OneMoreBannerLayout.this.mBannerView != null) {
                            OneMoreBannerLayout.this.removeView(OneMoreBannerLayout.this.mBannerView);
                            OneMoreBannerLayout.this.mBannerView = null;
                        }
                    } catch (Exception var2) {
                        var2.printStackTrace();
                    }

                    if (OneMoreBannerLayout.this.mBannerListener != null) {
                        OneMoreBannerLayout.this.mBannerListener.onBannerAdLoadFailed(error);
                    }

                }
            }
        });
    }

    void sendBannerAdClicked() {
        if (this.mBannerListener != null) {
            this.mBannerListener.onBannerAdClicked();
        }

    }

    void sendBannerAdScreenPresented() {
        if (this.mBannerListener != null) {
            this.mBannerListener.onBannerAdScreenPresented();
        }

    }

    void sendBannerAdScreenDismissed() {
        if (this.mBannerListener != null) {
            this.mBannerListener.onBannerAdScreenDismissed();
        }

    }

    void sendBannerAdLeftApplication() {
        if (this.mBannerListener != null) {
            this.mBannerListener.onBannerAdLeftApplication();
        }

    }

    void addViewWithFrameLayoutParams(final View adView, final LayoutParams layoutParams) {
        (new Handler(Looper.getMainLooper())).post(new Runnable() {
            public void run() {
                OneMoreBannerLayout.this.removeAllViews();
                OneMoreBannerLayout.this.mBannerView = adView;
                OneMoreBannerLayout.this.addView(adView, 0, layoutParams);
            }
        });
    }
}
