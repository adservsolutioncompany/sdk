package com.adservsolution.sdk.utils

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.provider.Settings
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

class DeviceUtils(
    private val context: Context
) {

    // os
    fun os(): String = Build.BRAND

    // osv
    fun api(): String = Build.VERSION.SDK_INT.toString()

    // make
    fun manufacturer(): String = Build.MANUFACTURER

    // model
    fun model(): String = Build.MODEL

    // hwv
    fun device(): String = Build.DEVICE

    // sr
    fun screen(): String =
        "${context.resources.displayMetrics.widthPixels}x${context.resources.displayMetrics.heightPixels}"

    // hc
    fun cpu(): Int = Runtime.getRuntime().availableProcessors()

    // mem
    fun memory(): Int {
        val actManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        actManager.getMemoryInfo(memInfo)
        return kotlin.math.ceil(memInfo.totalMem / 1_073_741_824.0).toInt()
    }

    // ln
    fun language(): String = Locale.getDefault().country

    // tz
    fun timeZone(): Int {
        val defaultTimeZone = TimeZone.getDefault()
        return TimeUnit.MILLISECONDS.toMinutes(defaultTimeZone.rawOffset.toLong()).toInt()
    }

    // did
    fun id(): String =
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}
