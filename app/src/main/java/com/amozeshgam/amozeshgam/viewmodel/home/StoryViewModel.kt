package com.amozeshgam.amozeshgam.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.model.local.HomeActivityModel
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestId
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetStory
import com.amozeshgam.amozeshgam.data.repository.HomeClusterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var model: HomeActivityModel
    @Inject
    lateinit var repository: HomeClusterRepository

    fun getStoryTime(): Int = model.storyTimer

    fun getStoryData(id:Int):Deferred<ApiResponseGetStory?>{
        return viewModelScope.async {
            repository.getStoryData(ApiRequestId(id))
        }
    }
}