package com.amozeshgam.amozeshgam.viewmodel.home.news

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestIdAndUserId
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetPost
import com.amozeshgam.amozeshgam.data.repository.HomeClusterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleNewsViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    @Inject
    lateinit var repository: HomeClusterRepository

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput


    private val _addPostToFavorite = MutableLiveData<Boolean>()
    val addPostToFavorite: LiveData<Boolean> = _addPostToFavorite
    fun getPostData(id: Int): Deferred<ApiResponseGetPost?> {
        return viewModelScope.async {
            repository.getPostDate(
                body = ApiRequestIdAndUserId(
                    id = id,
                    userId = dataBaseInputOutput.getData(DataStoreKey.userIdDataKey).toString()
                        .toIntOrNull() ?: 0
                )
            )
        }
    }

    fun createLink(route: String) {
        val link = ""
        val clipBoardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipBoardManager.setPrimaryClip(ClipData.newPlainText("amozeshgam", link))
        Toast.makeText(context, "کپی شد", Toast.LENGTH_SHORT).show()
    }

    fun addPostToFavorite(id: Int) {
        viewModelScope.launch {
            _addPostToFavorite.value = repository.addPostToFavorite(
                body = ApiRequestIdAndUserId(
                    id = id,
                    userId = dataBaseInputOutput.getData(DataStoreKey.userIdDataKey).toString()
                        .toIntOrNull() ?: 0
                )
            )
        }
    }
}