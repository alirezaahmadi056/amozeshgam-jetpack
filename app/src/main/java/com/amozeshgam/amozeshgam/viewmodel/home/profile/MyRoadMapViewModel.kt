package com.amozeshgam.amozeshgam.viewmodel.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestId
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseMyRoadMap
import com.amozeshgam.amozeshgam.data.repository.HomeClusterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class MyRoadMapViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var repository: HomeClusterRepository

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput
    fun getMyRoadMap(): Deferred<ApiResponseMyRoadMap?> {
        return viewModelScope.async {
            repository.getMyRoadMap(
                body = ApiRequestId(
                    dataBaseInputOutput.getData(
                        DataStoreKey.userIdDataKey
                    ).toString().toIntOrNull() ?: 0
                )
            )
        }
    }
}