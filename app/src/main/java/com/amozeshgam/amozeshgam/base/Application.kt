package com.amozeshgam.amozeshgam.base

import android.app.Application
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.aheaditec.talsec_security.security.api.Talsec
import com.aheaditec.talsec_security.security.api.TalsecConfig
import com.aheaditec.talsec_security.security.api.ThreatListener
import com.amozeshgam.amozeshgam.broadcast.BroadCastBattery
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class Application : Application(), ThreatListener.ThreatDetected {
    @Inject
    lateinit var batteryBroadCast: BroadCastBattery
    override fun onCreate() {
        super.onCreate()
        registerReceiver(batteryBroadCast, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val config = TalsecConfig(
            "com.amozeshgam.amozeshgam",
            arrayOf("EE:F6:A2:81:41:3E:D6:CF:13:02:A4:9A:33:22:39:9A:50:AC:D7:BC:1A:C5:02:AB:29:0D:DD:B3:E4:6C:E3:4F"),
            "amozeshgam@gmail.com",
            null,
            false
        )
        ThreatListener(this).registerListener(this)
        Talsec.start(this, config)
        listenBatteryState()
    }

    private fun listenBatteryState() {
        batteryBroadCast.batteryState.observeForever {
            Log.i("jjj", "listenBatteryState: ${it}")
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterReceiver(batteryBroadCast)
    }

    override fun onRootDetected() {
        TODO("Not yet implemented")
    }

    override fun onDebuggerDetected() {
        Log.i("jjj", "onDebuggerDetected: debugger")
    }

    override fun onEmulatorDetected() {
        Log.i("jjj", "onEmulatorDetected: emulator")
    }

    override fun onTamperDetected() {
        TODO("Not yet implemented")
    }

    override fun onUntrustedInstallationSourceDetected() {
        TODO("Not yet implemented")
    }

    override fun onHookDetected() {
        TODO("Not yet implemented")
    }

    override fun onDeviceBindingDetected() {
        TODO("Not yet implemented")
    }

    override fun onObfuscationIssuesDetected() {
        TODO("Not yet implemented")
    }
}