package com.amozeshgam.amozeshgam.data.model.local

import androidx.compose.ui.focus.FocusRequester
import javax.inject.Inject

class LoginClusterModel @Inject constructor() {
    val focusArray = arrayOf(
        FocusRequester(),
        FocusRequester(),
        FocusRequester(),
        FocusRequester(),
        FocusRequester()
    )
}