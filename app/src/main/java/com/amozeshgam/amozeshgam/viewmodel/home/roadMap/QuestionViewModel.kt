package com.amozeshgam.amozeshgam.viewmodel.home.roadMap

import androidx.lifecycle.ViewModel
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestId
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAiQuestion
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseRoadMapQuestion
import com.amozeshgam.amozeshgam.data.repository.HomeActivityRepository
import com.amozeshgam.amozeshgam.view.ui.theme.itemColorDark
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var repository: HomeActivityRepository
    suspend fun getAiQuestions(): ApiResponseAiQuestion? {
        return repository.getQuestionAiData()
    }

    suspend fun getRoadMapQuestion(id: Int): ApiResponseRoadMapQuestion? {
        return repository.getRoadMapQuestion(body = ApiRequestId(id = id)).first
    }
}