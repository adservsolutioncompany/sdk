package com.adservsolution.sample

import android.app.Application
import com.adservsolution.sdk.AdservsolutionSdk

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AdservsolutionSdk.Builder(this)
            .domain("https://z.cdn.advzone.net")
            .advToken("79IbvxRySnw-MHctQsxuaXQy0_jYqcZuLVgLf7aqEM0Ck9ZkJGQrUoFf0lnQS-9l")
            .appName("com.adservsolution.sample")
            .pixelId("160")
            .build()
    }
}
