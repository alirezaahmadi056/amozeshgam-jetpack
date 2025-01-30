package com.amozeshgam.amozeshgam.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseInformation
import com.amozeshgam.amozeshgam.data.repository.HomeActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var repository: HomeActivityRepository
    fun getInformationData(): Deferred<ApiResponseInformation?> {
        return viewModelScope.async {
            repository.getInformationData()
        }
    }
}