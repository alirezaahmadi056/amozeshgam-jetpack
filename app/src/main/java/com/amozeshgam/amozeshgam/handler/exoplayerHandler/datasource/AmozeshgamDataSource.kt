package com.amozeshgam.amozeshgam.handler.exoplayerHandler.datasource

import android.content.Context
import android.security.keystore.KeyProperties
import androidx.compose.ui.input.key.Key
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.TransferListener
import com.amozeshgam.amozeshgam.data.model.local.GlobalUiModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@UnstableApi
class AmozeshgamDataSource @Inject constructor(
    @ApplicationContext context: Context,
) : DataSource {
    private val upstream: DataSource = DefaultDataSource.Factory(context).createDataSource()
    private val cipher: Cipher =
        Cipher.getInstance("${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_CTR}/${KeyProperties.ENCRYPTION_PADDING_NONE}")
    private var key: ByteArray = byteArrayOf()
    private var iv: ByteArray = byteArrayOf()

    fun setKeyAndIv(key: ByteArray, iv: ByteArray) {
        this.key = key
        this.iv = iv
        require(key.size in arrayOf(16, 24, 32)) { "Invalid key size: ${key.size}" }
        require(iv.size == 16) { "Invalid iv size: ${iv.size}" }
    }

    override fun open(dataSpec: DataSpec): Long {
        return try {
            val params = IvParameterSpec(iv)
            cipher.init(
                Cipher.DECRYPT_MODE,
                SecretKeySpec(key, KeyProperties.KEY_ALGORITHM_AES),
                params
            )
            val result = upstream.open(dataSpec)
            result
        } catch (e: Exception) {
            GlobalUiModel.errorExceptionDialog.value = true
            GlobalUiModel.errorExceptionMessage.value = e.message.toString()
            return -1
        }
    }

    override fun read(target: ByteArray, offset: Int, length: Int): Int {
        return try {
            val encrypted = ByteArray(length)
            val bytesRead = upstream.read(encrypted, 0, length)
            if (bytesRead == -1) {
                return -1
            }
            val decrypted = cipher.update(encrypted, 0, bytesRead) ?: ByteArray(0)
            System.arraycopy(decrypted, 0, target, offset, decrypted.size)
            decrypted.size
        } catch (e: Exception) {
            GlobalUiModel.errorExceptionDialog.value = true
            GlobalUiModel.errorExceptionMessage.value = e.message.toString()
            return -1
        }
    }

    override fun addTransferListener(listener: TransferListener) {
        upstream.addTransferListener(listener)
    }

    override fun close() {
        upstream.close()
    }

    override fun getUri() = upstream.uri

}