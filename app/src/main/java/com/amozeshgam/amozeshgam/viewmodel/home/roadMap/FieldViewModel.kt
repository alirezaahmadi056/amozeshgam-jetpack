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
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseSingleField
import com.amozeshgam.amozeshgam.data.repository.HomeClusterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FieldViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var model: HomeActivityModel

    @Inject
    lateinit var repository: HomeClusterRepository
    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput

    private val _addedRequirementToMyCart = MutableLiveData<Boolean>()
    val addedRequirementToMyCart: LiveData<Boolean> = _addedRequirementToMyCart
    fun getFieldItems(): ArrayList<String> {
        return model.fieldTabItem
    }

    fun getFieldData(id: Int): Deferred<ApiResponseSingleField?> {
        return viewModelScope.async {
            repository.getFieldData(body = ApiRequestId(id))
        }
    }
    fun addRequirementToMyCart(id: Int) {
        viewModelScope.launch {
            _addedRequirementToMyCart.value = repository.addCourseToMyCart(
                ApiRequestAddToCart(
                    id = id,
                    userId = dataBaseInputOutput.getData(DataStoreKey.userIdDataKey).toString()
                        .toIntOrNull() ?: 0
                )
            )
        }
    }
}