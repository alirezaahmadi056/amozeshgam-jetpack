package com.amozeshgam.amozeshgam.viewmodel.home.roadMap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestArrayString
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestId
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAiQuestion
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseRoadMapQuestion
import com.amozeshgam.amozeshgam.data.repository.HomeClusterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var repository: HomeClusterRepository
    suspend fun getAiQuestions(): ApiResponseAiQuestion? {
        return repository.getQuestionAiData()
    }

    fun sendAiAnswers(answers: ApiRequestArrayString) {
        viewModelScope.launch {
            repository.sendAiAnswer(answers)
        }
    }

    suspend fun getRoadMapQuestion(id: Int): ApiResponseRoadMapQuestion? {
        return repository.getRoadMapQuestion(body = ApiRequestId(id = id)).first
    }
}