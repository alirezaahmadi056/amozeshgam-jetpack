package com.amozeshgam.amozeshgam.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amozeshgam.amozeshgam.handler.BatteryStateHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class BroadCastBattery @Inject constructor() : BroadcastReceiver() {
    private val _batteryState = MutableLiveData<BatteryStateHandler>()
    val batteryState: LiveData<BatteryStateHandler> = _batteryState
    override fun onReceive(context: Context?, intent: Intent?)  {
        val batteryLevel = (intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)!! * 100)
        when (batteryLevel) {
            in 5..20 -> _batteryState.value = BatteryStateHandler.HIGHLEVEL
            in 21..70 -> _batteryState.value = BatteryStateHandler.MIDLEVEL
            in 71..100 -> _batteryState.value = BatteryStateHandler.LOWLEVEL
        }
    }
}