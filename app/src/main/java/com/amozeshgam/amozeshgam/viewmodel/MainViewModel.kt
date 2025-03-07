package com.amozeshgam.amozeshgam.viewmodel

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbManager
import android.provider.Telephony
import androidx.lifecycle.ViewModel
import com.amozeshgam.amozeshgam.broadcast.BroadCastBattery
import com.amozeshgam.amozeshgam.broadcast.BroadCastSms
import com.amozeshgam.amozeshgam.broadcast.BroadCastUSB
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.handler.NavigationClusterHandler
import com.amozeshgam.amozeshgam.handler.NavigationScreenHandler
import com.amozeshgam.amozeshgam.handler.SuggestionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    @Inject
    lateinit var batteryBroadCast: BroadCastBattery

    @Inject
    lateinit var smsBroadCast: BroadCastSms

    @Inject
    lateinit var usbBroadCast: BroadCastUSB

    @Inject
    lateinit var suggestionHandler: SuggestionHandler

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput
    fun startBroadCast() {
        val usbIntentFilter = IntentFilter()
        usbIntentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
        usbIntentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
        context.registerReceiver(batteryBroadCast, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        context.registerReceiver(
            smsBroadCast,
            IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        )
        context.registerReceiver(usbBroadCast, usbIntentFilter)
    }
    suspend fun userIsLoggedIn():Boolean{
        return dataBaseInputOutput.getData(DataStoreKey.userIdDataKey) != null && dataBaseInputOutput.getData(DataStoreKey.hashDataKey) != null
    }
    fun handleTextProvider(text: String) {
        suggestionHandler.setTextToTextProvider(text)
    }
    fun handleDeepLink(link:String):String{
        val detectedDestination=link.substringAfter("https://app.amozeshgam.com/").split("/")
        return when(detectedDestination.getOrNull(0)){
            "home"->NavigationClusterHandler.Home.route
            else->NavigationScreenHandler.SplashScreen.route
        }
    }
    override fun onCleared() {
        super.onCleared()
        context.unregisterReceiver(batteryBroadCast)
        context.unregisterReceiver(smsBroadCast)
        context.unregisterReceiver(usbBroadCast)
    }
}