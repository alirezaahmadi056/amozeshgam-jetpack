package com.amozeshgam.amozeshgam.data.model.local

import androidx.compose.ui.focus.FocusRequester
import javax.inject.Inject

class LoginActivityModel @Inject constructor() {
    val focusArray = arrayOf(
        FocusRequester(),
        FocusRequester(),
        FocusRequester(),
        FocusRequester(),
        FocusRequester()
    )
}