package com.amozeshgam.amozeshgam.data.api

import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseInformation
import retrofit2.Call
import retrofit2.http.GET

interface DaneshjooyarApiInterface {
    @GET("/wp-json/wp/v2/teacher/alireza-ahmadi")
    fun apiGetInformation(): Call<ApiResponseInformation>
}