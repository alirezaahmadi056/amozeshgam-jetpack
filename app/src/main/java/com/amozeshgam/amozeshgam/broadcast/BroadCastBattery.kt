package com.amozeshgam.amozeshgam.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import com.amozeshgam.amozeshgam.data.model.local.GlobalUiModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BroadCastBattery @Inject constructor() : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val batteryLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0) ?: 0
        GlobalUiModel.requestForEnabledDarkMode.value = batteryLevel in 0..20
    }
}