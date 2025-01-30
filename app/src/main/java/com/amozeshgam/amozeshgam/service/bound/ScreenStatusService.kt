package com.amozeshgam.amozeshgam.service.bound

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.local.GlobalUiModel
import com.amozeshgam.amozeshgam.handler.ErrorHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import dagger.hilt.android.AndroidEntryPoint
import info.mqtt.android.service.MqttAndroidClient
import info.mqtt.android.service.QoS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttMessageListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage
import javax.inject.Inject

@AndroidEntryPoint
class ScreenStatusService : Service() {
    @Inject
    lateinit var mqttAndroidClient: MqttAndroidClient

    @Inject
    lateinit var errorHandler: ErrorHandler

    @Inject
    lateinit var dataBaseInputOutput: DataBaseInputOutput

    private lateinit var phone: String

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        connectToMqtt()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun connectToMqtt() {
        CoroutineScope(Dispatchers.IO).launch {
            phone = dataBaseInputOutput.getData(DataStoreKey.phoneDataKey).toString()
        }
        errorHandler.handelAnyError {
            val connection = mqttAndroidClient.connect()
            connection.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    subscribeMqtt("status/$phone")
                    mqttAndroidClient.publish("status/$phone", "getData".toByteArray(), 2, false)
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    connectToMqtt()
                }
            }
        }
    }

    private fun subscribeMqtt(topic: String) {
        mqttAndroidClient.subscribe(
            topic, 2, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    receiveMessage()
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    connectToMqtt()
                }
            }
        )
    }

    private fun receiveMessage() {
        mqttAndroidClient.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                connectToMqtt()
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                val payloadMessage = message?.payload?.decodeToString().toString()
                if (payloadMessage.startsWith("cart/")) {
                    val data = payloadMessage.removePrefix("cart/")
                    errorHandler.handelAnyError {
                        GlobalUiModel.numberOfCart.intValue = data.toInt()
                    }
                } else if (payloadMessage.startsWith("message/")) {
                    val data = payloadMessage.removePrefix("message/")
                    errorHandler.handelAnyError {
                        GlobalUiModel.numberOfMessage.intValue = data.toInt()
                    }
                }
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {

            }
        })
    }
}