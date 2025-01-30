package com.amozeshgam.amozeshgam.viewmodel.home.profile

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestId
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetMyTransactions
import com.amozeshgam.amozeshgam.data.repository.HomeActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class MyTransactionViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var repository: HomeActivityRepository

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput
    fun getTransactionsData(): Deferred<ApiResponseGetMyTransactions?> {
        return viewModelScope.async {
            repository.getMyTransactions(
                body = ApiRequestId(
                    id = dataBaseInputOutput.getData(
                        DataStoreKey.userIdDataKey
                    ).toString().toIntOrNull() ?: 0
                )
            )
        }
    }
}