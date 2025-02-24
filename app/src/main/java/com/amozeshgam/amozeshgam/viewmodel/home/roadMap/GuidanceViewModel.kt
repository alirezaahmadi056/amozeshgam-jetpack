package com.amozeshgam.amozeshgam.viewmodel.home.roadMap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.local.HomeActivityModel
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestId
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetFieldPackage
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetFields
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetSingleGuidField
import com.amozeshgam.amozeshgam.data.repository.HomeClusterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class GuidanceViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var model: HomeActivityModel

    @Inject
    lateinit var repository: HomeClusterRepository

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput

    fun getFieldItems(): ArrayList<String> {
        return model.fieldTabItem
    }

    fun getFieldData(): Deferred<ApiResponseGetFields?> {
        return viewModelScope.async {
            repository.getFields().first
        }
    }

    fun getFieldPackageData(): Deferred<ApiResponseGetFieldPackage?> {
        return viewModelScope.async {
            repository.getFieldPackage(
                body = ApiRequestId(
                    id = dataBaseInputOutput.getData(DataStoreKey.userIdDataKey).toString()
                        .toIntOrNull() ?: 0,
                )
            ).first
        }
    }

    fun getGuidFieldData(id: Int): Deferred<ApiResponseGetSingleGuidField?> {
        return viewModelScope.async {
            repository.getGuidFiledData(body = ApiRequestId(id = id)).first
        }
    }
}