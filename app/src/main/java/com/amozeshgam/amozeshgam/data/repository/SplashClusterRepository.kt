package com.amozeshgam.amozeshgam.data.repository

import com.amozeshgam.amozeshgam.data.api.AmozeshgamApiInterface
import com.amozeshgam.amozeshgam.data.api.ApiResponseTypeFace
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseCheckVersion
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetTour
import com.amozeshgam.amozeshgam.handler.ErrorHandler
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SplashClusterRepository @Inject constructor() {
    @Inject
    lateinit var errorHandler: ErrorHandler

    @Inject
    lateinit var api: AmozeshgamApiInterface
    suspend fun apiGetAppVersion(): ApiResponseTypeFace<ApiResponseCheckVersion> {
        val (response, code) = errorHandler.handelRequestApiValue(api.apiGetAppVersion())
        return Pair(response, code)
    }
    suspend fun apiGetTourData(): ApiResponseGetTour? {
        val (response, _) = errorHandler.handelRequestApiValue(api.apiGetTour())
        return response
    }
}