package com.amozeshgam.amozeshgam.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestId
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseMyNotification
import com.amozeshgam.amozeshgam.data.repository.HomeClusterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var repository: HomeClusterRepository

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput
    fun getMyNotification(): Deferred<ApiResponseMyNotification?> {
        return viewModelScope.async {
            repository.getMyNotification(
                body = ApiRequestId(
                    dataBaseInputOutput.getData(
                        DataStoreKey.userIdDataKey
                    ).toString().toIntOrNull() ?: 0
                )
            )
        }
    }
}