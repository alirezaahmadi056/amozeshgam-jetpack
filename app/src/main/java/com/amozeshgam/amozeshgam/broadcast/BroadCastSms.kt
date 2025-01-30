package com.amozeshgam.amozeshgam.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class BroadCastSms @Inject constructor() : BroadcastReceiver() {
    private val _codes = MutableStateFlow<ArrayList<String>>(arrayListOf())
    val codes: StateFlow<ArrayList<String>> = _codes
    var codeChars = arrayListOf<String>()
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val sms = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            val message = sms[0].messageBody
            if (message.contains("آموزشگام")) {
                val code = message.filter {
                    it.isDigit()
                }
                for (i in code) {
                    codeChars.add(i.toString())
                }
                _codes.value = codeChars
            }
        }
    }
}