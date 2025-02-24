package com.amozeshgam.amozeshgam.viewmodel.home

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.exoplayer.ExoPlayer
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.local.HomeActivityModel
import com.amozeshgam.amozeshgam.data.model.local.NavItem
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestCheckHash
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestReportBug
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseHomeData
import com.amozeshgam.amozeshgam.data.repository.HomeClusterRepository
import com.amozeshgam.amozeshgam.handler.DeviceHandler
import com.amozeshgam.amozeshgam.handler.RemoteStateHandler
import com.amozeshgam.amozeshgam.handler.SecurityHandler
import com.amozeshgam.amozeshgam.service.bound.ScreenStatusService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    @Inject
    lateinit var model: HomeActivityModel

    @Inject
    lateinit var repository: HomeClusterRepository

    @Inject
    lateinit var exoPlayer: ExoPlayer

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput

    @Inject
    lateinit var securityHandler: SecurityHandler

    @Inject
    lateinit var deviceHandler: DeviceHandler
    private val _hashIsValid = MutableLiveData<RemoteStateHandler>(RemoteStateHandler.WAITING)
    val hashIsValid: LiveData<RemoteStateHandler> = _hashIsValid

    fun getNavItems(): Array<NavItem> {
        return model.navItems
    }

    fun doNotShowGetPermissionDialog(value: Boolean) {
        viewModelScope.launch {
            dataBaseInputOutput.saveData {
                it[DataStoreKey.showGetNotificationListenerPermissionDialog] = value
            }
        }
    }

    suspend fun showRecipientNotificationPermissionDialog(): Boolean {
        val accessShowDialog =
            dataBaseInputOutput.getData(DataStoreKey.showGetNotificationListenerPermissionDialog)
                ?: false
        val packageNames = Settings.Secure.getString(
            context.contentResolver,
            "enabled_notification_listeners"
        )
        val myPackageName = context.packageName
        return if (accessShowDialog) {
            false
        } else {
            !(!TextUtils.isEmpty(packageNames) && packageNames.contains(myPackageName))
        }
    }

    fun getHomeData(): Deferred<ApiResponseHomeData?> {
        return viewModelScope.async {
            repository.getHomeData()
        }
    }


    fun clearAllExoplayer() {
        exoPlayer.pause()
        exoPlayer.seekToDefaultPosition()
    }

    fun reportBug(exception: String) {
        viewModelScope.launch {
            repository.reportBug(
                body = ApiRequestReportBug(
                    id = dataBaseInputOutput.getData(DataStoreKey.userIdDataKey).toString()
                        .toIntOrNull() ?: 0,
                    deviceId = deviceHandler.getAndroidId(),
                    text = exception
                )
            )
        }
    }

    suspend fun checkHash(): RemoteStateHandler {
        val response = repository.checkHash(
            body = ApiRequestCheckHash(
                deviceId = deviceHandler.getAndroidId(),
                hash = securityHandler.decryptData(
                    dataBaseInputOutput.getData(DataStoreKey.hashDataKey) ?: byteArrayOf()
                )
            )
        )
        return when (response) {
            200 -> RemoteStateHandler.OK
            500 -> RemoteStateHandler.BAD_RESPONSE
            else -> RemoteStateHandler.ERROR
        }
    }


    fun logOut() {
        dataBaseInputOutput.saveData {
            it[DataStoreKey.loginDataKey] = false
        }
    }

    override fun onCleared() {
        context.stopService(Intent(context, ScreenStatusService::class.java))
        super.onCleared()
    }
}