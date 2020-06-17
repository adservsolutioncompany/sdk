package one.more.ads.учфьзду.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

import one.more.ads.учфьзду.OneMoreSdkAdsFullScreenType;
import one.more.ads.учфьзду.OneMoreSdkObject;
import one.more.ads.sdk.R;
import one.more.ads.учфьзду.ui.view.OneMoreWebView;

public class OneMoreSdkAdvActivity extends Activity {

    private static final String TAG = "OneMoreSdkAdvActivity";

    private static final String TYPE_PARAM = "type";

    private static final String TYPE_ORIENTATION = "orientation";

    private static final String DOMEN_PARAM = "domen";

    public enum Orientation{
        PORT,
        LAND
    }



    public static Intent getIntent(@NonNull Context context,@NonNull OneMoreSdkAdsFullScreenType type,@NonNull Orientation orientation){
        Intent intent = new Intent(context,OneMoreSdkAdvActivity.class);
        intent.putExtra(TYPE_PARAM,(Serializable) type);
        intent.putExtra(TYPE_ORIENTATION,(Serializable) orientation);
        return intent;
    }

    private OneMoreWebView mWebView;
    private OneMoreSdkAdsFullScreenType mFullScreenType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        setRequestedOrientation(((Orientation)getIntent().getSerializableExtra(TYPE_ORIENTATION))
                == Orientation.PORT?ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mFullScreenType = (OneMoreSdkAdsFullScreenType) getIntent().getSerializableExtra(TYPE_PARAM);
        mWebView = findViewById(R.id.web_view);
        if(mFullScreenType == OneMoreSdkAdsFullScreenType.INTERSTITIAL){
            mWebView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT));
        }
        mWebView.setListener(new OneMoreWebView.Listener() {
            @Override
            public void makeAction(String action) {
                finish();
            }
        });
        mWebView.loadAds(OneMoreSdkObject.getInstance().getAppKey(),
                OneMoreSdkObject.getInstance().getDomen(),OneMoreSdkObject.getInstance().getGoogleAdvKey()
               ,mFullScreenType.getValue());
    }
}
