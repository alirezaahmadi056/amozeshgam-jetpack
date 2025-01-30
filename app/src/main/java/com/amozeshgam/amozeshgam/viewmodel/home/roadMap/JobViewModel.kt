package com.amozeshgam.amozeshgam.viewmodel.home.roadMap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.model.local.HomeActivityModel
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAADS
import com.amozeshgam.amozeshgam.data.repository.HomeActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class JobViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var model: HomeActivityModel

    @Inject
    lateinit var repository: HomeActivityRepository


}