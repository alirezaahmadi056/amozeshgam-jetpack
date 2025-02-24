package com.amozeshgam.amozeshgam.base

import android.app.Application
import android.util.Log
import com.securevale.rasp.android.api.SecureAppChecker
import com.securevale.rasp.android.api.result.Result
import com.securevale.rasp.android.native.SecureApp
import dagger.hilt.android.HiltAndroidApp
import kotlin.math.truncate

@HiltAndroidApp
class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        SecureApp.init()
        val result = SecureAppChecker.Builder(
            this,
            checkEmulator = true,
            checkDebugger = true,
            checkRoot = true
        ).build().check()
        when(result){
            Result.DebuggerEnabled -> {
                Log.i("jjj", "onCreate: debug")
            }
            Result.EmulatorFound -> {
                Log.i("jjj", "onCreate: emulator")
            }
            Result.Rooted -> {

            }
            Result.Secure -> {

            }
        }
    }
}