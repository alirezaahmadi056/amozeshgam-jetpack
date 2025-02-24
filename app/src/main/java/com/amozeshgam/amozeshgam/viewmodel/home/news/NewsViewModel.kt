package com.amozeshgam.amozeshgam.viewmodel.home.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseExplorer
import com.amozeshgam.amozeshgam.data.repository.HomeClusterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var repository: HomeClusterRepository
    fun getNewsData(): Deferred<ApiResponseExplorer?> {
        return viewModelScope.async {
            repository.getNewData()
        }
    }
}