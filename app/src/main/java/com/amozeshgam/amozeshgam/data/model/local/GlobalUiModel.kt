package com.amozeshgam.amozeshgam.data.model.local

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

object GlobalUiModel {
    const val DARK_CODE = 3
    const val LIGHT_CODE = 2
    const val SYSTEM_THEME_CODE = 1
    val uiTheme = mutableIntStateOf(SYSTEM_THEME_CODE)
    val emergencyState = mutableStateOf(false)
    val requestForEnabledDarkMode = mutableStateOf(false)
    val errorExceptionDialog = mutableStateOf(false)
    val errorExceptionMessage = mutableStateOf("")
    val numberOfMessage = mutableIntStateOf(0)
    val numberOfCart = mutableIntStateOf(0)
    val showNotificationDialog = mutableStateOf(false)
    val dialogTitle = mutableStateOf("")
    val dialogBody = mutableStateOf("")
    val textProviderData = mutableStateOf<String?>(null)
}