package com.amozeshgam.amozeshgam.viewmodel.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseFaq
import com.amozeshgam.amozeshgam.data.repository.HomeActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class FaqViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var repository: HomeActivityRepository
    fun getFaqData(): Deferred<ApiResponseFaq?> {
        return viewModelScope.async {
            repository.getFaqData()
        }
    }
}