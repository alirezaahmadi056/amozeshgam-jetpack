package com.amozeshgam.amozeshgam.viewmodel.home

import android.icu.util.Calendar
import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestId
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestSendSupportMessage
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetMessages
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetMessagesData
import com.amozeshgam.amozeshgam.data.repository.HomeClusterRepository
import com.amozeshgam.amozeshgam.handler.DeviceHandler
import com.amozeshgam.amozeshgam.handler.ErrorHandler
import com.amozeshgam.amozeshgam.handler.RemoteStateHandler
import com.amozeshgam.amozeshgam.handler.SecurityHandler
import com.amozeshgam.amozeshgam.handler.SuggestionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import info.mqtt.android.service.MqttAndroidClient
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var mqtt: MqttAndroidClient

    @Inject
    lateinit var errorHandler: ErrorHandler

    @Inject
    lateinit var deviceHandler: DeviceHandler

    @Inject
    lateinit var repository: HomeClusterRepository

    @Inject
    lateinit var suggestionHandler: SuggestionHandler

    @Inject
    lateinit var securityHandler: SecurityHandler

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput

    private val _messageSenderState = MutableStateFlow(RemoteStateHandler.WAITING)
    val messageSenderState: StateFlow<RemoteStateHandler> = _messageSenderState
    fun onStart() {
        Log.i("jjj", "onStart: hello")
        errorHandler.handelAnyError {
            mqtt.connect()
        }
    }



    fun getAllMessages(): Deferred<ApiResponseGetMessages?> {
        return viewModelScope.async {
            repository.getAllMessages(
                ApiRequestId(
                    id = dataBaseInputOutput.getData(DataStoreKey.userIdDataKey).toString()
                        .toIntOrNull() ?: 0
                )
            )
        }
    }

    suspend fun subscribeMqtt(messages: SnapshotStateList<ApiResponseGetMessagesData>) {
        mqtt.subscribe(
            "message/${
                securityHandler.decryptData(
                    dataBaseInputOutput.getData(DataStoreKey.hashDataKey)
                        ?: byteArrayOf()
                )
            }",
            2,
            null,
            object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    receiveMessage(messages)
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                }
            })
    }


    private fun receiveMessage(messages: SnapshotStateList<ApiResponseGetMessagesData>) {
        mqtt.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                errorHandler.handelAnyError {
                    viewModelScope.launch {
                        subscribeMqtt(
                            messages
                        )
                    }
                }
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                messages.add(
                    ApiResponseGetMessagesData(
                        time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString(),
                        senderType = "admin",
                        message = message?.payload?.decodeToString().toString()
                    )
                )
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                TODO("Not yet implemented")
            }
        })
    }

    fun sendSupportMessage(message: String) {
        viewModelScope.launch {
            _messageSenderState.value = RemoteStateHandler.LOADING
            val result = repository.sendMessageWithMqtt(
                ApiRequestSendSupportMessage(
                    id = dataBaseInputOutput.getData(DataStoreKey.userIdDataKey).toString()
                        .toIntOrNull() ?: 0,
                    message = message
                )
            )
            _messageSenderState.value =
                if (result) RemoteStateHandler.OK else RemoteStateHandler.ERROR
        }
    }

    fun getTime(): String {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            .toString() + ":" + Calendar.getInstance().get(Calendar.MINUTE)
    }
}