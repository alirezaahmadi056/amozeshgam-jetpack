package com.amozeshgam.amozeshgam.viewmodel.home.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestRegister
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetTicketSubjects
import com.amozeshgam.amozeshgam.data.repository.HomeClusterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var repository: HomeClusterRepository

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput

    private val _registerRequest = MutableLiveData<Boolean>()
    val registerRequest: LiveData<Boolean> = _registerRequest

    fun getTicketSubjects(): Deferred<ApiResponseGetTicketSubjects?> {
        return viewModelScope.async {
            repository.getTicketSubjects()
        }
    }

    fun registerRequest(text: String, priority: String, subject: String) {
        viewModelScope.launch {
            val response = repository.requestRegister(
                body = ApiRequestRegister(
                    id = dataBaseInputOutput.getData(
                        DataStoreKey.userIdDataKey
                    ).toString().toIntOrNull() ?: 0,
                    priority = priority,
                    subject = subject,
                    text = text
                )
            )
            Log.i("jjj", "registerRequest: $response")
            _registerRequest.value = response != null
        }
    }
}