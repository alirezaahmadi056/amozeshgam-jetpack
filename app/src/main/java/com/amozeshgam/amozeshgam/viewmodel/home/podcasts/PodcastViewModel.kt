package com.amozeshgam.amozeshgam.viewmodel.home.podcasts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAllPodcasts
import com.amozeshgam.amozeshgam.data.repository.HomeActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class PodcastViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var repository: HomeActivityRepository
    fun getPodcastsData(): Deferred<ApiResponseAllPodcasts?> {
        return viewModelScope.async {
            repository.getPodcastsData()
        }
    }
}