package com.amozeshgam.amozeshgam.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbManager
import com.amozeshgam.amozeshgam.data.model.local.GlobalUiModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BroadCastUSB @Inject constructor() : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.action == UsbManager.ACTION_USB_DEVICE_ATTACHED) {
            GlobalUiModel.showEmergencyUSBDialog.value = true
        } else if (p1?.action == UsbManager.ACTION_USB_DEVICE_DETACHED) {
            GlobalUiModel.showEmergencyUSBDialog.value = false
        }
    }
}