package com.amozeshgam.amozeshgam.viewmodel.home.course

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestAddToCart
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestCreateComment
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestGetCourse
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestIdAndUserId
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetCourse
import com.amozeshgam.amozeshgam.data.repository.HomeClusterRepository
import com.amozeshgam.amozeshgam.handler.DeviceHandler
import com.amozeshgam.amozeshgam.handler.RemoteStateHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleCourseViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    @Inject
    lateinit var repository: HomeClusterRepository

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput

    @Inject
    lateinit var deviceHandler: DeviceHandler


    private val _addToFavorites = MutableLiveData<Boolean>()
    val addToFavorites: LiveData<Boolean> = _addToFavorites
    private val _addCourseToMyCart = MutableLiveData<RemoteStateHandler>()
    val addCourseToMyCart: LiveData<RemoteStateHandler> = _addCourseToMyCart
    private val _addCommentToCourse = MutableLiveData<RemoteStateHandler>()
    val addCommentToCourse: LiveData<RemoteStateHandler> = _addCommentToCourse
    fun resetCommentState() {
        _addCommentToCourse.value = RemoteStateHandler.WAITING
    }

    fun getCourseData(courseId: Int): Deferred<ApiResponseGetCourse?> {
        return viewModelScope.async {
            repository.getSingleCourseData(
                body = ApiRequestGetCourse(
                    courseId = courseId,
                    userId = dataBaseInputOutput.getData(DataStoreKey.userIdDataKey).toString()
                        .toIntOrNull() ?: 1
                )
            )
        }
    }

    fun addCourseToFavorites(courseId: Int) {
        viewModelScope.launch {
            val response = repository.addCourseToFavorites(
                body = ApiRequestIdAndUserId(
                    userId = dataBaseInputOutput.getData(DataStoreKey.userIdDataKey)?.toIntOrNull()
                        ?: 0,
                    id = courseId
                )
            )
            _addToFavorites.value = response != null
        }
    }

    fun addCourseToMyCart(courseId: Int) {
        viewModelScope.launch {
            val response = repository.addCourseToMyCart(
                body = ApiRequestAddToCart(
                    id = courseId,
                    userId = dataBaseInputOutput.getData(DataStoreKey.userIdDataKey).toString()
                        .toIntOrNull() ?: 1
                )
            )
            when (response) {
                true -> {
                    _addCourseToMyCart.value = RemoteStateHandler.OK
                }

                false -> _addCourseToMyCart.value = RemoteStateHandler.ERROR
            }
        }
    }

    fun addCommentToCourse(comment: String, courseId: Int) {
        viewModelScope.launch {
            val response = repository.createCourseComment(
                ApiRequestCreateComment(
                    comment = comment,
                    courseId = courseId,
                    userId = dataBaseInputOutput.getData(DataStoreKey.userIdDataKey).toString()
                        .toIntOrNull()
                        ?: 1,
                    deviceId = deviceHandler.getAndroidId()
                )
            )
            _addCommentToCourse.value = if (response) {
                RemoteStateHandler.OK
            } else {
                RemoteStateHandler.ERROR
            }
        }
    }

    fun saveLinkToClipBoard(route: String) {
        val deepLink = ""
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(ClipData.newPlainText("amozeshgam", deepLink))
    }
}