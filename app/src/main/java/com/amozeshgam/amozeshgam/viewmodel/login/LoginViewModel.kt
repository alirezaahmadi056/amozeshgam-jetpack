package com.amozeshgam.amozeshgam.viewmodel.login

import android.annotation.SuppressLint
import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.BuildConfig
import com.amozeshgam.amozeshgam.broadcast.BroadCastSms
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.local.LoginClusterModel
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestCheckCode
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestPhone
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestSetName
import com.amozeshgam.amozeshgam.data.repository.LoginClusterRepository
import com.amozeshgam.amozeshgam.handler.DeviceHandler
import com.amozeshgam.amozeshgam.handler.RemoteStateHandler
import com.amozeshgam.amozeshgam.handler.SecurityHandler
import com.amozeshgam.amozeshgam.handler.ValidatingStateHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.security.KeyPairGenerator
import java.security.KeyStore
import javax.inject.Inject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) :
    ViewModel() {

    @Inject
    lateinit var repository: LoginClusterRepository

    @Inject
    lateinit var model: LoginClusterModel

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput

    @Inject
    lateinit var securityHandler: SecurityHandler

    @Inject
    lateinit var deviceHandler: DeviceHandler
    @Inject
    lateinit var receiver: BroadCastSms

    private val _codeSending = MutableLiveData(RemoteStateHandler.WAITING)
    val codeSending: LiveData<RemoteStateHandler> = _codeSending
    private val _codeIsValid = MutableLiveData(Pair(ValidatingStateHandler.WAITING, false))
    val codeIsValid: LiveData<Pair<ValidatingStateHandler, Boolean>> = _codeIsValid
    private val _saveUserName = MutableLiveData(RemoteStateHandler.WAITING)
    val saveUserName: LiveData<RemoteStateHandler> = _saveUserName

    fun getTextCodeFocused(): Array<FocusRequester> {
        return model.focusArray

    }

    suspend fun getLoginCodeFromBroadCast(codes: SnapshotStateList<String>) {
        receiver.codes.collect {
            if (it.size == 5){
                codes.addAll(it)
            }
        }
    }

    override fun onCleared() {
        context.unregisterReceiver(receiver)
    }

    fun setUserName(name: String) {
        viewModelScope.launch {
            val phone = dataBaseInputOutput.getData(DataStoreKey.phoneDataKey)
            val hash = dataBaseInputOutput.getData(DataStoreKey.hashDataKey)
            val code = repository.sendUserNameApi(
                ApiRequestSetName(
                    phone.toString(), securityHandler.decryptData(hash!!), name
                )
            )
            when (code) {
                200 -> {
                    _saveUserName.value = RemoteStateHandler.OK
                    dataBaseInputOutput.saveData {
                        it[DataStoreKey.loginDataKey] = true
                    }
                }

                500 -> _saveUserName.value = RemoteStateHandler.BAD_RESPONSE
                else -> _saveUserName.value = RemoteStateHandler.ERROR
            }
            _saveUserName.value = RemoteStateHandler.WAITING
        }
    }

    fun sendCode(phoneNumber: String) {
        viewModelScope.launch {
            val result = repository.sendCodeLoginApi(ApiRequestPhone(phoneNumber))
            if (result) {
                _codeSending.value = RemoteStateHandler.OK
            } else {
                _codeSending.value = RemoteStateHandler.ERROR
            }
            _codeSending.value = RemoteStateHandler.WAITING
        }
    }

    fun phoneNumberValidating(phoneNumber: String): Boolean {
        return phoneNumber.length == 11 && phoneNumber.startsWith("09")
    }

    @SuppressLint("HardwareIds")
    fun checkCode(
        phoneNumber: String, code: String, deviceName: String, deviceId: String
    ) {
        viewModelScope.launch {
            val (response, responseCode) = repository.sendCheckCodeApi(
                ApiRequestCheckCode(
                    phoneNumber, code, deviceName, deviceId, generateKeyPair()
                )
            )
            when (responseCode) {
                200 -> {
                    dataBaseInputOutput.saveData {
                        it[DataStoreKey.phoneDataKey] = phoneNumber
                        it[DataStoreKey.hashDataKey] =
                            securityHandler.encryptData(response!!.hash.toByteArray()).toByteArray()
                        it[DataStoreKey.nameDataKey] = response.usernameId.toString()
                        it[DataStoreKey.loginDataKey] = response.account
                        it[DataStoreKey.publicKeyDataKey] = response.publicKey
                        it[DataStoreKey.userIdDataKey] = response.usernameId.toString()
                    }
                    _codeIsValid.value = Pair(
                        ValidatingStateHandler.VALID, response?.account ?: false
                    )
                }

                500 -> {
                    _codeIsValid.value = Pair(ValidatingStateHandler.INVALID, false)
                }

                else -> {
                    _codeIsValid.value = Pair(ValidatingStateHandler.ERROR, false)
                }
            }
            _codeIsValid.value = Pair(ValidatingStateHandler.WAITING, false)
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun generateKeyPair(keyAlias: String = BuildConfig.CIPHER_KEY): String {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        if (!keyStore.containsAlias(keyAlias)) {
            val keyPairGenerator =
                KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, keyStore.provider)
            val spec = KeyGenParameterSpec.Builder(
                keyAlias,
                KeyProperties.PURPOSE_ENCRYPT
                        or KeyProperties.PURPOSE_DECRYPT
            )
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                .setDigests(KeyProperties.DIGEST_SHA256)
                .setKeySize(2048)
                .build()
            keyPairGenerator.initialize(spec)
            return Base64.encode(keyPairGenerator.generateKeyPair().public.encoded)
        } else {
            val publicKey = keyStore.getCertificate(keyAlias).publicKey
            return Base64.encode(publicKey.encoded)
        }
    }
}
