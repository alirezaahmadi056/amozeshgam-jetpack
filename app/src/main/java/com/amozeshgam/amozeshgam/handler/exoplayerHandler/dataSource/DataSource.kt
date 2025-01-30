package com.amozeshgam.amozeshgam.handler.exoplayerHandler.dataSource


import android.annotation.SuppressLint
import android.net.Uri
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.TransferListener
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.amozeshgam.amozeshgam.di.qualifier.DefaultDataSource
import javax.inject.Inject

@UnstableApi
class DataSource @Inject constructor() : DataSource {
    @DefaultDataSource
    @Inject
    lateinit var dataSource: DataSource
    external fun decryptVideo() : ByteArray

    @SuppressLint("UnsafeDynamicallyLoadedCode")
    companion object {
        init {
            System.load("rust")
        }
    }

    override fun addTransferListener(p0: TransferListener) {
        dataSource.addTransferListener(p0)
    }

    override fun open(p0: DataSpec): Long {
        return dataSource.open(p0)
    }

    override fun getUri(): Uri? =
        dataSource.uri

    override fun close() {
        dataSource.close()
    }

    override fun read(buffer: ByteArray, offset: Int, readLength: Int): Int {
        val bytesRead = dataSource.read(buffer, offset, readLength)
        if (bytesRead != -1) {
            val encryptedData = buffer.copyOfRange(offset, offset + bytesRead)
            val decryptedData = byteArrayOf()
            System.arraycopy(decryptedData, 0, buffer, offset, decryptedData.size)
        }
        return bytesRead
    }
}