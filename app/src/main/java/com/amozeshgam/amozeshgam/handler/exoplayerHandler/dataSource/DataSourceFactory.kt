package com.amozeshgam.amozeshgam.handler.exoplayerHandler.dataSource

import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource

class DataSourceFactory : DataSource.Factory {
    @OptIn(UnstableApi::class)
    override fun createDataSource(): DataSource {
        return DataSource()
    }
}