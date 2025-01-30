package com.amozeshgam.amozeshgam.keyboard

import android.inputmethodservice.InputMethodService
import android.view.View
import androidx.compose.ui.platform.ComposeView

class AmozeshgamKeyboard : InputMethodService() {
    override fun onCreateInputView(): View {
        val composeView = ComposeView(this@AmozeshgamKeyboard)
        composeView.apply {
            setContent {

            }
        }
        return composeView
    }
}