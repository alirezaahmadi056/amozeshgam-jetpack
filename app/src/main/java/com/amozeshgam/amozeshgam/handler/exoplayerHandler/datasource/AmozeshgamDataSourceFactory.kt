package com.amozeshgam.amozeshgam.handler.exoplayerHandler.datasource

import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.okhttp.OkHttpDataSource
import okhttp3.OkHttpClient
import javax.inject.Inject

@UnstableApi
class AmozeshgamDataSourceFactory(private val key: ByteArray, private val iv: ByteArray) :
    DataSource.Factory {
    @Inject
    lateinit var dataSource: AmozeshgamDataSource
    override fun createDataSource(): DataSource {
        dataSource.setKeyAndIv(key, iv)
        return OkHttpDataSource.Factory(OkHttpClient()).createDataSource().let { dataSource }
    }
}