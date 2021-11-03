package one.more.ads.example

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import one.more.ads.sdk.*
import one.more.ads.sdk.ui.OneMoreSdkAdvActivity
import one.more.ads.sdk.ui.view.OneMoreWebView

class MainActivity : AppCompatActivity() {




    val clickListener = View.OnClickListener {view ->

        when (view.getId()) {
            R.id.btn_banner -> {
                BannerHelper.loadBanner(
                    OneMoreSdkObject.getInstance(),
                    banner_view,
                    OneMoreSdkAdsBannerType.BANNER_RECTANGLE,
                    object : OneMoreWebView.Listener {
                        override fun makeAction(action: String?) {
                            Toast.makeText(this@MainActivity, "Кнопка нажата", Toast.LENGTH_LONG)
                                .show()
                        }


                    }
                )
            }
            R.id.btn_interstitial -> {
                OneMoreSdk.forceInterstitial(
                    this@MainActivity,
                    1,
                    OneMoreSdkAdvActivity.Orientation.PORT
                )
            }
            R.id.btn_video -> {
                OneMoreSdk.forceVideo(
                    this@MainActivity,
                    2,
                    OneMoreSdkAdvActivity.Orientation.LAND
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        btn_banner.setOnClickListener(clickListener)
        btn_interstitial.setOnClickListener(clickListener)
        btn_video.setOnClickListener(clickListener)
        OneMoreSdk.init(
            this,
            "1466054648",
            "z.cdn.adtarget.me"
        );
    }


}
