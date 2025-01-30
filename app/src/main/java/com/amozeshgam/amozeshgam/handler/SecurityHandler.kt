package com.amozeshgam.amozeshgam.handler

import android.annotation.SuppressLint
import android.provider.Settings.Global
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import com.amozeshgam.amozeshgam.BuildConfig
import com.amozeshgam.amozeshgam.data.model.local.GlobalUiModel
import java.security.KeyPair
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Inject

class SecurityHandler @Inject constructor() {
    @SuppressLint("GetInstance")
    private val cipher =
        Cipher.getInstance("${KeyProperties.KEY_ALGORITHM_RSA}/${KeyProperties.BLOCK_MODE_ECB}/${KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1}")
    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    private fun getPrivateKey(): PrivateKey {
        val privateKey = keyStore.getKey(BuildConfig.CIPHERKEY, null) as PrivateKey
        return privateKey
    }

    private fun getPublicKey(): PublicKey {
        return keyStore.getCertificate(BuildConfig.CIPHERKEY).publicKey
    }

    fun encryptData(data: ByteArray): String {
        return try {
            cipher.init(
                Cipher.ENCRYPT_MODE,
                getPublicKey()
            )
            val encryptedData = cipher.doFinal(data)
            Base64.encodeToString(encryptedData, Base64.DEFAULT)
        } catch (e: Exception) {
            GlobalUiModel.errorExceptionDialog.value = true
            GlobalUiModel.errorExceptionMessage.value = e.message.toString()
            "null"
        }
    }

    fun decryptData(data: ByteArray): String {
        return try {
            cipher.init(
                Cipher.DECRYPT_MODE,
                getPrivateKey()
            )
            val decodedData = cipher.doFinal(Base64.decode(data, Base64.DEFAULT))
            String(decodedData, Charsets.UTF_8)
        } catch (e: Exception) {
            GlobalUiModel.errorExceptionDialog.value = true
            GlobalUiModel.errorExceptionMessage.value = e.message.toString()
            "null"
        }
    }
}