package com.amozeshgam.amozeshgam.viewmodel.home.roadMap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.local.HomeActivityModel
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestAddToCart
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestId
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseSingleSubField
import com.amozeshgam.amozeshgam.data.repository.HomeClusterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubFieldViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var repository: HomeClusterRepository

    @Inject
    lateinit var model: HomeActivityModel

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput

    private val _addToMyCart = MutableLiveData<Boolean>()
    val addToMyCart: LiveData<Boolean> = _addToMyCart

    fun getSubFieldData(id: Int): Deferred<ApiResponseSingleSubField?> {
        return viewModelScope.async {
            repository.getSubFieldData(body = ApiRequestId(id = id))
        }
    }


    fun addToMyCart(id: Int) {
        viewModelScope.launch {
            _addToMyCart.value = repository.addRoadMapToMyCart(
                body = ApiRequestAddToCart(
                    id = id,
                    userId = dataBaseInputOutput.getData(DataStoreKey.userIdDataKey).toString()
                        .toIntOrNull() ?: 0
                )
            )
        }
    }

}