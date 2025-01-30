package com.amozeshgam.amozeshgam.viewmodel.tour

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetTour
import com.amozeshgam.amozeshgam.data.repository.TourActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TourViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var repository: TourActivityRepository

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput
    private val _finishActivity = MutableLiveData<Boolean>()
    val finishActivity: LiveData<Boolean> = _finishActivity

    suspend fun apiGetTourData(): Deferred<ApiResponseGetTour?> {
        return viewModelScope.async {
            repository.apiGetTourData()
        }
    }

    suspend fun userIsLoggedIn() {
        dataBaseInputOutput.getData(DataStoreKey.loginDataKey)
    }
    fun finishActivity(){
        _finishActivity.value = true
    }
    fun saveTourData() {
        CoroutineScope(Dispatchers.IO).launch {
            dataBaseInputOutput.saveData {
                it[DataStoreKey.tourDataKey] = true
            }
        }
    }
}