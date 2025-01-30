package com.amozeshgam.amozeshgam.viewmodel.home.profile

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.BuildConfig
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestUpdateUser
import com.amozeshgam.amozeshgam.data.repository.HomeActivityRepository
import com.amozeshgam.amozeshgam.handler.ErrorHandler
import com.amozeshgam.amozeshgam.handler.RemoteStateHandler
import com.amozeshgam.amozeshgam.handler.SecurityHandler
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.File
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor() : ViewModel() {
    @ApplicationContext
    @Inject
    lateinit var context: Context

    @Inject
    lateinit var repository: HomeActivityRepository

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput


    @Inject
    lateinit var securityHandler: SecurityHandler

    @Inject
    lateinit var errorHandler: ErrorHandler
    private val _emailCredentialResult = MutableLiveData(RemoteStateHandler.WAITING)
    val emailCredentialResult: LiveData<RemoteStateHandler> = _emailCredentialResult
    private val _avatarUploaded = MutableLiveData(RemoteStateHandler.WAITING)
    val avatarUploaded: LiveData<RemoteStateHandler> = _avatarUploaded
    private val _updatedUser = MutableLiveData<RemoteStateHandler>(RemoteStateHandler.WAITING)
    val updateUser: LiveData<RemoteStateHandler> = _updatedUser

    fun requestForLoginWithEmail() {
        val md = MessageDigest.getInstance("SHA-256")
        val hashNonce = md.digest(UUID.randomUUID().toString().toByteArray())
            .fold("") { str, it -> str + "%02x".format(it) }
        val credentialManager = CredentialManager.create(context)
        val googleIdOptions = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.GoogleCredentials)
            .setNonce(hashNonce)
            .build()
        val requestCredential = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOptions)
            .build()
        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = requestCredential,
                    context = context
                )
                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(result.credential.data)
                _emailCredentialResult.value = RemoteStateHandler.OK
                Log.i("jjj", "requestForLoginWithEmail: ${googleIdTokenCredential.id}")
                Log.i("jjj", "requestForLoginWithEmail: ${googleIdTokenCredential.displayName}")
                Log.i("jjj", "requestForLoginWithEmail: ${googleIdTokenCredential.idToken}")
            } catch (e: Exception) {
                Log.i("jjj", "requestForLoginWithEmail: ${e.message}")
                Log.i("jjj", "requestForLoginWithEmail: ${e.cause}")
                _emailCredentialResult.value = RemoteStateHandler.ERROR
            }
        }
    }

    fun updateProfile(name: String, date: String) {
        viewModelScope.launch {
            val response = repository.updateUser(
                ApiRequestUpdateUser(
                    phone = dataBaseInputOutput.getData(DataStoreKey.phoneDataKey).toString(),
                    hash = securityHandler.decryptData(
                        dataBaseInputOutput.getData(DataStoreKey.hashDataKey) ?: byteArrayOf()
                    ),
                    name = name,
                    date = date
                )
            )
            _updatedUser.value = when (response) {
                true -> RemoteStateHandler.OK
                else -> RemoteStateHandler.ERROR
            }
        }
    }

    fun uploadAvatar(avatar: Uri) {
        val file = getFileFromUri(avatar)
        _avatarUploaded.value = RemoteStateHandler.LOADING
        viewModelScope.launch {
            val response = repository.uploadAvatar(
                id = dataBaseInputOutput.getData(DataStoreKey.userIdDataKey).toString()
                    .toIntOrNull() ?: 0,
                hash = securityHandler.decryptData(
                    dataBaseInputOutput.getData(DataStoreKey.hashDataKey) ?: byteArrayOf()
                ),
                avatar = file
            )
            _avatarUploaded.value = when (response) {
                true -> RemoteStateHandler.OK
                else -> RemoteStateHandler.ERROR
            }
        }
    }

    private fun getFileFromUri(uri: Uri): File? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, filePathColumn, null, null, null)
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
        val filePath = cursor?.getString(columnIndex!!)
        cursor?.close()
        return filePath?.let { File(it) }
    }
}