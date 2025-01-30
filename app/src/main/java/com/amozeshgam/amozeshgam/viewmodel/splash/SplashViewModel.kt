package com.amozeshgam.amozeshgam.viewmodel.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.local.GlobalUiModel
import com.amozeshgam.amozeshgam.data.model.local.SplashActivityModel
import com.amozeshgam.amozeshgam.data.repository.SplashActivityRepository
import com.amozeshgam.amozeshgam.handler.ActivityName
import com.amozeshgam.amozeshgam.handler.DeepLinkHandler
import com.amozeshgam.amozeshgam.handler.DeviceHandler
import com.amozeshgam.amozeshgam.handler.SuggestionHandler
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
    private val _whitchActivityToGo = MutableLiveData<ActivityName>()
    val witchActivityToGo: LiveData<ActivityName> = _whitchActivityToGo
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    @Inject
    lateinit var repository: SplashActivityRepository

    @Inject
    lateinit var suggestionHandler: SuggestionHandler

    @Inject
    lateinit var splashModel: SplashActivityModel

    @Inject
    lateinit var deepLinkHandler: DeepLinkHandler

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput

    @Inject
    lateinit var deviceHandler: DeviceHandler
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

    fun hideStatusBar(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsetsCompat.Type.systemBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
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
        CoroutineScope(Dispatchers.IO).launch {
            val versionChecked = checkAppVersion()
            _isLoading.value = false
            if (versionChecked.first) {
                val userIsLoggedIn = userIsLoggedIn()
                val userSeeTour = userSeeTour()
                CoroutineScope(Dispatchers.Main).launch {
                    _whitchActivityToGo.value = when {
                        userIsLoggedIn -> ActivityName.HomeActivity
                        userSeeTour -> ActivityName.LoginActivity
                        else -> ActivityName.TourActivity
                    }
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

    fun handleTextProvider(text: String) {
        suggestionHandler.setTextToTextProvider(text)
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