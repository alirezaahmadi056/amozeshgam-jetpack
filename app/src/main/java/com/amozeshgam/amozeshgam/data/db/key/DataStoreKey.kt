package com.amozeshgam.amozeshgam.data.db.key

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKey {
    val loginDataKey = booleanPreferencesKey("login")
    val nameDataKey = stringPreferencesKey("name")
    val hashDataKey = byteArrayPreferencesKey("hash")
    val phoneDataKey = stringPreferencesKey("phone")
    val themeDataKey = intPreferencesKey("theme")
    val numberOfCartDataKey = intPreferencesKey("number-of-cart")
    val numberOfMessageDataKey = intPreferencesKey("number-of-message")
    val publicKeyDataKey = stringPreferencesKey("public-key")
    val userIdDataKey = stringPreferencesKey("user-id")
    val showNotificationDialog = booleanPreferencesKey("notification-dialog")
    val dialogTitle = stringPreferencesKey("dialog-title")
    val dialogBody = stringPreferencesKey("dialog-body")
    val tourDataKey = booleanPreferencesKey("tour")
    val enabledNotification = booleanPreferencesKey("enabled-notification")
    val enabledAmozeshgamKeyboard = booleanPreferencesKey("enabled-amozeshgam-keyboard")
    val showGetNotificationListenerPermissionDialog =
        booleanPreferencesKey("show-get-notification-listener-permission-dialog")
}