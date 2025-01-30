package com.amozeshgam.amozeshgam.handler

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings.Secure
import android.text.format.Formatter
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DeviceHandler @Inject constructor(@ApplicationContext private val context: Context) {
    @SuppressLint("HardwareIds")
    fun getAndroidId(): String {
        return Secure.getString(context.contentResolver, Secure.ANDROID_ID).toString()
    }

    fun getDeviceName(): String {
        return Build.MODEL.toString()
    }
}