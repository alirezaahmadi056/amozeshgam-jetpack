package com.amozeshgam.amozeshgam.data.api

import com.amozeshgam.amozeshgam.BuildConfig
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestAddUserDataToBlackHole
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface BlackHoleApiInterface {
    @POST("/api/add")
    fun apiAddUserDataToBlackHole(
        @Header("x-api-key") apiKey: String = BuildConfig.API_KEY,
        @Body body: ApiRequestAddUserDataToBlackHole,
    ): Call<Any>
}