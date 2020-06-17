package one.more.ads.учфьзду.ui.view;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import one.more.ads.учфьзду.environment.DeviceStatus;

public final class OneMoreWebView extends WebView {

    private static final String TAG = "OneMoreWebView";

    public interface Listener{

        public void makeAction(String action);

    }

    private static final String SCRIPT_TEMPLATE = "<script src=\"%s\"></script>";

    private StringBuilder mStringBuilder = new StringBuilder();

    private String getUrl(String key,String domen,String advId,String type) throws Exception {
        if(mStringBuilder.length()>0) {
            mStringBuilder.delete(0, mStringBuilder.length());
        }
        return mStringBuilder.append("https://").append(domen)
                .append("/app?s=").append(key).append("&t=").append(type).append("&os=")
                .append(DeviceStatus.getDeviceOs()).append("&osv=").append(DeviceStatus.getAndroidAPIVersion())
                .append("&make=").append(DeviceStatus.getDeviceOEM()).append("&model=").append(DeviceStatus.getDeviceModel())
                .append("&hwv=").append(DeviceStatus.getMobileCarrier(getContext()))
                .append("&sr=").append(DeviceStatus.getDisplayWidth()).append("x").append(DeviceStatus.getDisplayHeight())
                .append("&app=").append(getContext().getPackageName()).append("&hc=").append(DeviceStatus.getNumberOfCores())
                .append("&mem=").append(DeviceStatus.getRAM(getContext())).append("&ln=").append(DeviceStatus.getDeviceLanguage(getContext()))
                .append("&tz=").append(DeviceStatus.getDeviceTimeZoneOffsetInMinutes()).append("&did=")
                .append(advId).append("&ct=").append(DeviceStatus.getNetworkClass(getContext()))
                .append("&cw=").append(getMeasuredWidth()).append("&ch=").append(getMeasuredHeight())
                .append("&or=").append(getResources().
                        getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT?"PORTRAIT":"LANDSCAPE").toString();
    }

    private OneMoreWebViewJsInterface mInterface;

    public OneMoreWebView(Context context) {
        this(context,null);
    }

    public OneMoreWebView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public OneMoreWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        mInterface = new OneMoreWebViewJsInterface();
        addJavascriptInterface(mInterface, "OMWVJSI");
        setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadUrl("javascript:window.mediaComplete = function(){OMWVJSI.mediaComplete()};");
            }
        });

    }

    public void setListener(Listener listener){
        mInterface.setListener(listener);
    }

    public void loadAds(@NonNull final String key,@NonNull final String domen,@NonNull final String advId,@NonNull final String type){
        post(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = String.format(SCRIPT_TEMPLATE,getUrl(key,domen,advId,type));
                 //   url = "<script src=\"https://z.cdn.adtarget.me/app?s=1466054648&t=interstitial&cw=300&ch=250&sr=1920x1080\"></script>";
                    Log.d(TAG,"URL: "+url);
                    loadData(url, "text/html", "UTF-8");
                } catch (Exception e) {
                    Log.e(TAG,null,e);
                }
            }
        });

    }

    @Keep
    private static class OneMoreWebViewJsInterface {

        private Listener mListener;

        public OneMoreWebViewJsInterface(){
            this(null);
        }

        public OneMoreWebViewJsInterface(Listener listener){
            mListener = listener;
        }

        public void setListener(Listener mListener) {
            this.mListener = mListener;
        }

        @JavascriptInterface
        public void mediaComplete() {
           if(mListener!=null){
               mListener.makeAction("mediaComplete");
           }
        }

    }
}
