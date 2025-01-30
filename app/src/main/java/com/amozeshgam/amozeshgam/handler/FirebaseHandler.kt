package com.amozeshgam.amozeshgam.handler

import android.content.Context
import android.os.Bundle
import androidx.annotation.Size
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseHandler @Inject constructor() {
    @Inject
    lateinit var firebaseAnalytics: FirebaseAnalytics
    fun logEvent(@Size(min = 1L, max = 40L) eventName: String, bundle: Bundle?) {
        firebaseAnalytics.logEvent(eventName, bundle)
    }
}

fun Modifier.logEventOnClick(context: Context, eventName: String, bundle: Bundle?) {
    this.clickable {
        FirebaseAnalytics.getInstance(context).logEvent(eventName, bundle)
    }
}
