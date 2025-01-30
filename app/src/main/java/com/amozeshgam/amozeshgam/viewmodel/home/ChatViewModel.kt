package com.amozeshgam.amozeshgam.viewmodel.home

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestId
import com.amozeshgam.amozeshgam.data.model.remote.ApiRequestSendSupportMessage
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetMessages
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetMessagesData
import com.amozeshgam.amozeshgam.data.repository.HomeActivityRepository
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
    lateinit var repository: HomeActivityRepository

    @Inject
    lateinit var suggestionHandler: SuggestionHandler

    @Inject
    lateinit var securityHandler: SecurityHandler

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput
    private val _mqttConnected = MutableStateFlow<Boolean>(false)
    val mqttConnected: StateFlow<Boolean> = _mqttConnected
    private val _messageSenderState = MutableStateFlow(RemoteStateHandler.WAITING)
    val messageSenderState: StateFlow<RemoteStateHandler> = _messageSenderState
    fun onStart() {
        errorHandler.handelAnyError {
            val connection = mqtt.connect()
            connection.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    _mqttConnected.value = true
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    _mqttConnected.value = false
                }
            }
        }
    }

    fun getAllMessages(): Deferred<ApiResponseGetMessages?> {
        return viewModelScope.async {
            repository.getMessages(
                ApiRequestId(
                    id = dataBaseInputOutput.getData(DataStoreKey.userIdDataKey).toString()
                        .toIntOrNull() ?: 0
                )
            )
        }
    }

    fun subscribeMqtt(messages: ArrayList<ApiResponseGetMessagesData>, topic: String) {
        mqtt.subscribe(
            "message/${topic}",
            2,
            null,
            object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    receiveMessage(messages)
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    _mqttConnected.value = false
                }
            })
    }


    private fun receiveMessage(messages: ArrayList<ApiResponseGetMessagesData>) {
        mqtt.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                errorHandler.handelAnyError {
                    viewModelScope.launch {
                        subscribeMqtt(
                            messages, topic = securityHandler.decryptData(
                                dataBaseInputOutput.getData(DataStoreKey.hashDataKey)
                                    ?: byteArrayOf()
                            )
                        )
                    }
                }
                _mqttConnected.value = false
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
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()
    }
}