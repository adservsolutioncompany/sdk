package one.more.ads.учфьзду;

import android.os.Handler;
import android.os.Looper;

import java.util.Date;

public class ListenersWrapper implements InterstitialListener {

    private InterstitialListener mInterstitialListener;
    private ListenersWrapper.CallbackHandlerThread mCallbackHandlerThread = new ListenersWrapper.CallbackHandlerThread();
    private String mRvPlacementName = null;
    private long mLastChangedAvailabilityTime;

    public ListenersWrapper() {
        this.mCallbackHandlerThread.start();
        this.mLastChangedAvailabilityTime = (new Date()).getTime();
    }


    private void sendCallback(Runnable callbackRunnable) {
        if (this.mCallbackHandlerThread != null) {
            Handler callbackHandler = this.mCallbackHandlerThread.getCallbackHandler();
            if (callbackHandler != null) {
                callbackHandler.post(callbackRunnable);
            }

        }
    }

    public void setInterstitialListener(InterstitialListener interstitialListener) {
        this.mInterstitialListener = interstitialListener;
    }


    public void onInterstitialAdReady() {
        if (this.canSendCallback(this.mInterstitialListener)) {
            this.sendCallback(new Runnable() {
                public void run() {
                    ListenersWrapper.this.mInterstitialListener.onInterstitialAdReady();
                }
            });
        }

    }

    public void onInterstitialAdLoadFailed(final Exception error) {
        if (this.canSendCallback(this.mInterstitialListener)) {
            this.sendCallback(new Runnable() {
                public void run() {
                    ListenersWrapper.this.mInterstitialListener.onInterstitialAdLoadFailed(error);
                }
            });
        }

    }

    public void onInterstitialAdOpened() {
        if (this.canSendCallback(this.mInterstitialListener)) {
            this.sendCallback(new Runnable() {
                public void run() {
                    ListenersWrapper.this.mInterstitialListener.onInterstitialAdOpened();
                }
            });
        }

    }

    public void onInterstitialAdShowSucceeded() {
        if (this.canSendCallback(this.mInterstitialListener)) {
            this.sendCallback(new Runnable() {
                public void run() {
                    ListenersWrapper.this.mInterstitialListener.onInterstitialAdShowSucceeded();
                }
            });
        }

    }

    public void onInterstitialAdShowFailed(final Exception error) {
        if (this.canSendCallback(this.mInterstitialListener)) {
            this.sendCallback(new Runnable() {
                public void run() {
                    ListenersWrapper.this.mInterstitialListener.onInterstitialAdShowFailed(error);
                }
            });
        }
    }

    public void onInterstitialAdClicked() {
        if (this.canSendCallback(this.mInterstitialListener)) {
            this.sendCallback(new Runnable() {
                public void run() {
                    ListenersWrapper.this.mInterstitialListener.onInterstitialAdClicked();
                }
            });
        }

    }

    public void onInterstitialAdClosed() {
        if (this.canSendCallback(this.mInterstitialListener)) {
            this.sendCallback(new Runnable() {
                public void run() {
                    ListenersWrapper.this.mInterstitialListener.onInterstitialAdClosed();
                }
            });
        }

    }


    private boolean canSendCallback(Object productListener) {
        return productListener != null && this.mCallbackHandlerThread != null;
    }




    private class CallbackHandlerThread extends Thread {
        private Handler mCallbackHandler;

        private CallbackHandlerThread() {
        }

        public void run() {
            Looper.prepare();
            this.mCallbackHandler = new Handler();
            Looper.loop();
        }

        public Handler getCallbackHandler() {
            return this.mCallbackHandler;
        }
    }
}
