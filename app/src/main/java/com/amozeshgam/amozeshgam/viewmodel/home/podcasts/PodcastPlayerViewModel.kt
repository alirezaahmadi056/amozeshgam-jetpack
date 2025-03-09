package com.amozeshgam.amozeshgam.viewmodel.home.podcasts

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.local.GlobalUiModel
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestAddPodcastToFavorite
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestId
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetPodcast
import com.amozeshgam.amozeshgam.data.repository.HomeClusterRepository
import com.amozeshgam.amozeshgam.service.bound.PodcastPlayerService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodcastPlayerViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    private var binder: PodcastPlayerService.Binder? = null
    private val _enabledRepeatMode = MutableStateFlow<Boolean>(false)
    val enabledRepeatMode: StateFlow<Boolean> = _enabledRepeatMode
    private val _isPlaying = MutableStateFlow<Boolean>(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying
    private val _readyForPlay = MutableStateFlow(false)
    val readyForPlay: StateFlow<Boolean> = _readyForPlay

    @Inject
    lateinit var repository: HomeClusterRepository

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            binder = (p1 as PodcastPlayerService.Binder)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            GlobalUiModel.errorExceptionDialog.value = true
            GlobalUiModel.errorExceptionMessage.value = "terminate podcast service"
        }
    }

    fun getPodcastData(id: Int): Deferred<ApiResponseGetPodcast?> {
        return viewModelScope.async {
            repository.getSinglePodcastData(body = ApiRequestId(id = id))
        }
    }

    fun resetPosition() {
        binder.let {
            it?.resetPosition()
        }
    }

    fun getCurrentPosition(): Long {
        return binder?.getCurrentPosition() ?: 0L
    }

    fun seekTo(position: Long) {
        binder.let {
            it?.seekTo(position)
        }
    }

    fun startPodcastService() {
        context.bindService(
            Intent(context, PodcastPlayerService::class.java),
            serviceConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    fun pausePodcast() {
        _isPlaying.value = false
        binder.let {
            it?.pausePodcast()
        }
    }


    fun loadData(uri: String, title: String,owner:String) {
        viewModelScope.launch {
            _readyForPlay.value = binder?.loadUri(uri, title,owner) ?: false
        }
    }

    fun playPodcast(): Long {
        _isPlaying.value = true
        return binder?.playPodcast() ?: 0L
    }

    fun release() {
        _readyForPlay.value = false
    }

    fun repeatPodcast() {
        _enabledRepeatMode.value = !(_enabledRepeatMode.value as Boolean)
        this.binder.let {
            it?.repeatPodcast(state = if (_enabledRepeatMode.value as Boolean) Player.REPEAT_MODE_ALL else Player.REPEAT_MODE_OFF)
        }
    }

    fun likePodcast(podcastId: Int) {
        viewModelScope.launch {
            repository.addPodcastToFavorite(
                body = ApiRequestAddPodcastToFavorite(
                    userId = dataBaseInputOutput.getData(
                        DataStoreKey.userIdDataKey
                    ).toString().toIntOrNull() ?: 0, podcastId = podcastId
                )
            )
        }
    }


    fun clear() {
        viewModelScope.launch {
            binder?.releasePlayer()
            context.unbindService(serviceConnection)
        }
    }
}