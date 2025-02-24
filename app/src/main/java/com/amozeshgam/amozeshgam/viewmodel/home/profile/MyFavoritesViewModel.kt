package com.amozeshgam.amozeshgam.viewmodel.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestDeleteFavorite
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestId
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetMyFavorites
import com.amozeshgam.amozeshgam.data.repository.HomeClusterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyFavoritesViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var repository: HomeClusterRepository

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput
    private val _deletedFavorite = MutableLiveData<Boolean>()
    val deletedFavorite: LiveData<Boolean> = _deletedFavorite
    fun getMyFavorites(): Deferred<ApiResponseGetMyFavorites?> {
        return viewModelScope.async {
            repository.getMyFavorites(
                body = ApiRequestId(
                    dataBaseInputOutput.getData(DataStoreKey.userIdDataKey).toString().toIntOrNull()
                        ?: 0
                )
            )
        }
    }

    fun removeFromMyFavorite(id: Int, type: String) {
        viewModelScope.launch {
            _deletedFavorite.value =
                repository.deleteFromFavorite(body = ApiRequestDeleteFavorite(id = id, type = type))
        }
    }
}