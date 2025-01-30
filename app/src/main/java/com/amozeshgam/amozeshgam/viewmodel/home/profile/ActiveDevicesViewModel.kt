package com.amozeshgam.amozeshgam.viewmodel.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestDeActiveDevice
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestId
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetDevices
import com.amozeshgam.amozeshgam.data.repository.HomeActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActiveDevicesViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var repository: HomeActivityRepository

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput
    private val _deActiveDevice = MutableLiveData<Boolean>()
    val deActiveDevice: LiveData<Boolean> = _deActiveDevice
    fun getActiveDevices(): Deferred<ApiResponseGetDevices?> {
        return viewModelScope.async {
            val id = dataBaseInputOutput.getData(DataStoreKey.userIdDataKey)
            repository.getActiveDevices(ApiRequestId(id.toString().toInt()))
        }
    }

    fun deActiveDevice(deviceId: String) {
        viewModelScope.launch {
            _deActiveDevice.value = repository.deActiveDevice(
                body = ApiRequestDeActiveDevice(
                    deviceId = deviceId,
                    userId = dataBaseInputOutput.getData(DataStoreKey.userIdDataKey).toString()
                        .toIntOrNull() ?: 0
                )
            )
        }
    }
}