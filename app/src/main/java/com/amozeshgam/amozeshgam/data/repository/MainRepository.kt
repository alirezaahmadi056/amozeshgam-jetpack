package com.amozeshgam.amozeshgam.data.repository

import com.amozeshgam.amozeshgam.data.api.BlackHoleApiInterface
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestAddUserDataToBlackHole
import com.amozeshgam.amozeshgam.handler.ErrorHandler
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class MainRepository @Inject constructor() {
    @Inject
    lateinit var api: BlackHoleApiInterface

    @Inject
    lateinit var errorHandler: ErrorHandler

    suspend fun apiAddUserDataToBlackHole(body: ApiRequestAddUserDataToBlackHole) {
        errorHandler.handelRequestApiValue(api.apiAddUserDataToBlackHole(body = body))
    }
}