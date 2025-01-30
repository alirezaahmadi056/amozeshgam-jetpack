package com.amozeshgam.amozeshgam.handler

import android.util.Log
import com.amozeshgam.amozeshgam.data.api.ApiResponseTypeFace
import com.amozeshgam.amozeshgam.data.model.local.GlobalUiModel
import retrofit2.Call
import retrofit2.awaitResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorHandler @Inject constructor() {
    suspend fun <T> handelRequestApiValue(value: Call<T>): ApiResponseTypeFace<T> {
        return try {
            val response = value.awaitResponse()

            return if (response.code() == 200) {
                Pair(response.body(), response.code())
            } else {
                Log.i("jjj", "handelRequestApiValue: ${response.errorBody()}")
                Log.i("jjj", "handelRequestApiValue: ${response.code()}")
                Pair(null, response.code())
            }
        } catch (e: Exception) {
            GlobalUiModel.errorExceptionDialog.value = true
            GlobalUiModel.errorExceptionMessage.value = e.message.toString()
            Log.i("jjj", "handelRequestApiValue: $e")
            Pair(null, null)
        }
    }

    inline fun handelAnyError(crossinline worker: () -> Unit): Boolean {
        return try {
            worker()
            true
        } catch (e: Exception) {
            Log.i("jjj", "handelAnyError: $e")
            GlobalUiModel.errorExceptionDialog.value = true
            GlobalUiModel.errorExceptionMessage.value = e.message.toString()
            false
        }
    }
}