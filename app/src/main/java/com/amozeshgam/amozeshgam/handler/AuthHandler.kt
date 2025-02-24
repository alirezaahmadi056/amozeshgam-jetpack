package com.amozeshgam.amozeshgam.handler

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class AuthHandler @Inject constructor(@ActivityContext private val context: Context) {
    private val biometricManager = BiometricManager.from(context)
    private val checkBiometricAvailable =
        biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
    private val _biometricAuthState = MutableLiveData<BiometricState>()
    val biometricAuthState: LiveData<BiometricState> = _biometricAuthState
    private val _biometricState = MutableLiveData<BiometricState>()
    val biometricState: LiveData<BiometricState> = _biometricState

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput
    fun checkBiometricAvailable() {
        when (checkBiometricAvailable) {
            BiometricManager.BIOMETRIC_SUCCESS -> _biometricState.value = BiometricState.SUCCESS
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> _biometricState.value =
                BiometricState.NO_BIOMETRIC_AVAILABLE

            else -> _biometricState.value = BiometricState.ERROR
        }
    }

    fun authWithFingerPrint() {
        val executor = ContextCompat.getMainExecutor(context)
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("تایید هویت")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .setSubtitle("تایید هویت پرداخت از کیف پول")
            .setNegativeButtonText("لغو")
            .build()

        val biometricPrompt = BiometricPrompt(
            context as FragmentActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    _biometricAuthState.value = BiometricState.ERROR
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    _biometricAuthState.value = BiometricState.AUTHENTICATED
                }

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    _biometricAuthState.value = BiometricState.ERROR
                }
            })
        biometricPrompt.authenticate(promptInfo)
    }

    suspend fun sendAuthenticationCode(worker: (phone: String) -> Unit) {
        worker(dataBaseInputOutput.getData(DataStoreKey.phoneDataKey).toString())
    }
}