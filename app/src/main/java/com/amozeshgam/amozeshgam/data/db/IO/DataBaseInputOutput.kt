package com.amozeshgam.amozeshgam.data.db.IO

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataBaseInputOutput @Inject constructor() {
    @Inject
    lateinit var dataStore:DataStore<Preferences>
    fun saveData(worker: (data: MutablePreferences) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit(worker)
        }
    }
    suspend fun <T> getData(key:Preferences.Key<T>):T?{
        return dataStore.data.first()[key]
    }
}