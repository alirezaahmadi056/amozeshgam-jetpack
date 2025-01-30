package com.amozeshgam.amozeshgam.data.repository

import com.amozeshgam.amozeshgam.data.api.AmozeshgamApiInterface
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetTour
import com.amozeshgam.amozeshgam.handler.ErrorHandler
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class TourActivityRepository @Inject constructor() {
    @Inject
    lateinit var api: AmozeshgamApiInterface

    @Inject
    lateinit var errorHandler: ErrorHandler
    suspend fun apiGetTourData(): ApiResponseGetTour? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetTour())
        return response
    }
}