package com.amozeshgam.amozeshgam.viewmodel.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.broadcast.BroadCastSms
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.local.GlobalUiModel
import com.amozeshgam.amozeshgam.data.model.local.SplashClusterModel
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetTour
import com.amozeshgam.amozeshgam.data.repository.SplashClusterRepository
import com.amozeshgam.amozeshgam.handler.DeviceHandler
import com.amozeshgam.amozeshgam.handler.ScreenName
import com.amozeshgam.amozeshgam.handler.SuggestionHandler
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    private val _isAllPermissionGranted = MutableLiveData<Boolean>()
    val isAllPermissionGranted: LiveData<Boolean> = _isAllPermissionGranted
    private val _showErrorDialog = MutableStateFlow<Boolean>(false)
    val showErrorDialog: StateFlow<Boolean> = _showErrorDialog
    private val _whichActivityToGo = MutableLiveData<ScreenName>()
    val whichActivityToGo: LiveData<ScreenName> = _whichActivityToGo
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    @Inject
    lateinit var repository: SplashClusterRepository

    @Inject
    lateinit var suggestionHandler: SuggestionHandler

    @Inject
    lateinit var splashModel: SplashClusterModel



    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput

    @Inject
    lateinit var deviceHandler: DeviceHandler

    @Inject
    lateinit var receiver: BroadCastSms
    fun onStartUp() {
        loadThemeFromDataBase()
        subscribeTopic()
    }

    private suspend fun userIsLoggedIn(): Boolean {
        return dataBaseInputOutput.getData(DataStoreKey.loginDataKey) ?: false
    }

    private suspend fun userSeeTour(): Boolean {
        return dataBaseInputOutput.getData(DataStoreKey.tourDataKey) ?: false
    }


    private suspend fun checkAppVersion(): Pair<Boolean, Int?> {
        val (response, code) = repository.apiGetAppVersion()
        return Pair(response?.version == 1, code)
    }

    fun checkPermission() {
        _isAllPermissionGranted.value = checkNotificationPermission() && checkSmsPermission()
    }

    private fun checkNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun checkSmsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.READ_SMS,
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.RECEIVE_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun checkVersionAndWitchActivityToGo() {
        _isLoading.value = true
        viewModelScope.launch {
            val versionChecked = checkAppVersion()
            _isLoading.value = false
            if (versionChecked.first) {
                val userIsLoggedIn = userIsLoggedIn()
                val userSeeTour = userSeeTour()
                _whichActivityToGo.value = when {
                    userIsLoggedIn -> ScreenName.HOME
                    userSeeTour -> ScreenName.LOGIN
                    else -> ScreenName.TOUR
                }
            } else {
                _showErrorDialog.value = true
            }
        }
    }

    private fun loadThemeFromDataBase() {
        viewModelScope.launch {
            val themeCode = dataBaseInputOutput.getData(DataStoreKey.themeDataKey) ?: 1
            GlobalUiModel.uiTheme.intValue = themeCode
        }
    }

    fun loadDialogData() {
        viewModelScope.launch {
            GlobalUiModel.showNotificationDialog.value =
                dataBaseInputOutput.getData(DataStoreKey.showNotificationDialog) ?: false
            GlobalUiModel.dialogTitle.value =
                dataBaseInputOutput.getData(DataStoreKey.dialogTitle) ?: ""
            GlobalUiModel.dialogBody.value =
                dataBaseInputOutput.getData(DataStoreKey.dialogBody) ?: ""
        }
    }


    fun apiGetTourData(): Deferred<ApiResponseGetTour?> {
        return viewModelScope.async {
            repository.apiGetTourData()
        }
    }


    fun saveTourData() {
        CoroutineScope(Dispatchers.IO).launch {
            dataBaseInputOutput.saveData {
                it[DataStoreKey.tourDataKey] = true
            }
        }
    }
    @SuppressLint("HardwareIds")
    private fun subscribeTopic() {
        viewModelScope.launch {
            val enabledNotification =
                dataBaseInputOutput.getData(DataStoreKey.enabledNotification) ?: true
            if (enabledNotification) {
                Firebase.messaging.subscribeToTopic("security")
                Firebase.messaging.subscribeToTopic("notification")
                Firebase.messaging.subscribeToTopic(
                    "notification-${
                        deviceHandler.getAndroidId()
                    }"
                )
                Firebase.messaging.isAutoInitEnabled = true
            }
        }
    }
}