package com.amozeshgam.amozeshgam.data.repository

import com.amozeshgam.amozeshgam.data.api.AmozeshgamApiInterface
import com.amozeshgam.amozeshgam.data.api.ApiResponseTypeFace
import com.amozeshgam.amozeshgam.handler.ErrorHandler
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestCheckCode
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestPhone
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestSetName
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseCheckCode
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class LoginActivityRepository @Inject constructor() {
    @Inject
    lateinit var api: AmozeshgamApiInterface

    @Inject
    lateinit var errorHandler: ErrorHandler

    suspend fun sendCodeLoginApi(phoneBody: ApiRequestPhone): Boolean {
        val (response) = errorHandler.handelRequestApiValue(api.apiLogin(body = phoneBody))
        return response != null
    }

    suspend fun sendCheckCodeApi(codeBody: ApiRequestCheckCode): ApiResponseTypeFace<ApiResponseCheckCode> {
        val (response, code) = errorHandler.handelRequestApiValue(api.apiCheckCode(body = codeBody))
        return Pair(response, code)
    }

    suspend fun sendUserNameApi(nameBody: ApiRequestSetName): Int? {
        val (_, code) = errorHandler.handelRequestApiValue(api.apiSetUserName(body = nameBody))
        return code
    }
}