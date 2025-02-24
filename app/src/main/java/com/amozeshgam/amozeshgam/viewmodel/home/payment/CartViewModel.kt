package com.amozeshgam.amozeshgam.viewmodel.home.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestId
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestIdAndUserId
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseCart
import com.amozeshgam.amozeshgam.data.repository.HomeClusterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var repository: HomeClusterRepository

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput
    private val _removeCourseFromMyCartIsSuccess = MutableLiveData<Boolean>()
    val removeCourseFromMyCartIsSuccess: LiveData<Boolean> = _removeCourseFromMyCartIsSuccess
    private val _removeRoadMapFromMyCartIsSuccess = MutableLiveData<Boolean>()
    val removeRoadMapFromMyCartIsSuccess: LiveData<Boolean> = _removeRoadMapFromMyCartIsSuccess
    fun getMyCart(): Deferred<ApiResponseCart?> {
        return viewModelScope.async {
            repository.getMyCart(
                body = ApiRequestId(
                    dataBaseInputOutput.getData(DataStoreKey.userIdDataKey).toString().toIntOrNull()
                        ?: 0
                )
            )
        }
    }

    fun removeCourseFromMyCart(id: Int) {
        viewModelScope.launch {
            _removeCourseFromMyCartIsSuccess.value = repository.removeCourseFromCart(
                body = ApiRequestIdAndUserId(
                    id = id,
                    userId = dataBaseInputOutput.getData(DataStoreKey.userIdDataKey).toString()
                        .toIntOrNull() ?: 0
                )
            )
        }
    }

    fun removeRoadMapFromMyCart(id: Int) {
        viewModelScope.launch {
            _removeRoadMapFromMyCartIsSuccess.value = repository.removeRoadMapFromCart(
                body = ApiRequestIdAndUserId(
                    id = id,
                    userId = dataBaseInputOutput.getData(DataStoreKey.userIdDataKey).toString()
                        .toIntOrNull() ?: 0
                )
            )
        }
    }
}