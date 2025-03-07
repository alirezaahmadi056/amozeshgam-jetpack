package com.amozeshgam.amozeshgam.viewmodel.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.local.HomeActivityModel
import com.amozeshgam.amozeshgam.data.model.local.ProfileItem
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestId
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetProfile
import com.amozeshgam.amozeshgam.data.repository.HomeClusterRepository
import com.amozeshgam.amozeshgam.handler.DeviceHandler
import com.amozeshgam.amozeshgam.handler.ErrorHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var model: HomeActivityModel

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput

    @Inject
    lateinit var errorHandler: ErrorHandler

    @Inject
    lateinit var deviceHandler: DeviceHandler

    @Inject
    lateinit var repository: HomeClusterRepository
    private val _enabledAmozeshgamKeyboard = MutableStateFlow<Boolean>(false)
    val enabledAmozeshgamKeyboard: StateFlow<Boolean> = _enabledAmozeshgamKeyboard
    private val _enabledAmozeshgamNotification = MutableStateFlow<Boolean>(true)
    val enabledAmozeshgamNotification: StateFlow<Boolean> = _enabledAmozeshgamNotification


    fun getProfileItems(): Array<ProfileItem> {
        return model.profileItem
    }

    fun changeTheme(themeCode: Int) {
        UiHandler.changeTheme(themeCode = themeCode, dataBaseInputOutput = dataBaseInputOutput)
    }

    fun getUserData(): Deferred<ApiResponseGetProfile?> {
        return viewModelScope.async {
            val id = dataBaseInputOutput.getData(DataStoreKey.userIdDataKey)
            repository.getUserData(body = ApiRequestId(id = id.toString().toInt()))
        }
    }

    fun getCurrentNotificationState() {
        viewModelScope.launch {
            _enabledAmozeshgamNotification.value =
                dataBaseInputOutput.getData(DataStoreKey.enabledNotification) ?: true
        }
    }

    fun changeNotificationState() {
        errorHandler.handelAnyError {
            viewModelScope.launch {
                dataBaseInputOutput.saveData {
                    it[DataStoreKey.enabledNotification] = !(_enabledAmozeshgamNotification.value)
                }
            }
        }
        if (!(_enabledAmozeshgamNotification.value)) {
            unSubscribedFireBase()
        } else {
            subscribeFireBase()
        }
        _enabledAmozeshgamNotification.value = !(_enabledAmozeshgamNotification.value)!!
    }

    private fun subscribeFireBase() {
        Firebase.messaging.subscribeToTopic("security")
        Firebase.messaging.subscribeToTopic("notification")
        Firebase.messaging.subscribeToTopic(
            "notification-${
                deviceHandler.getAndroidId()
            }"
        )
    }

    private fun unSubscribedFireBase() {
        Firebase.messaging.unsubscribeFromTopic("security")
        Firebase.messaging.unsubscribeFromTopic("notification")
        Firebase.messaging.unsubscribeFromTopic(
            "notification-${
                deviceHandler.getAndroidId()
            }"
        )
    }
}