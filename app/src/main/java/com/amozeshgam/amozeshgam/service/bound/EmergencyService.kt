package com.amozeshgam.amozeshgam.service.bound

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.amozeshgam.amozeshgam.data.model.local.GlobalUiModel
import com.amozeshgam.amozeshgam.handler.ErrorHandler
import dagger.hilt.android.AndroidEntryPoint
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage
import javax.inject.Inject

@AndroidEntryPoint
class EmergencyService : Service() {
    @Inject
    lateinit var mqttClient: MqttAndroidClient

    @Inject
    lateinit var errorHandler: ErrorHandler

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        connectToMqttServer()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun connectToMqttServer() {
        errorHandler.handelAnyError {
            val connection = mqttClient.connect()
            connection.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    publishForGetState()
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    connectToMqttServer()
                }
            }
        }
    }

    private fun publishForGetState() {
        errorHandler.handelAnyError {
            mqttClient.publish("emergency", "getData".encodeToByteArray(), 2, false)
            subscribeState()
        }
    }

    private fun subscribeState() {
        errorHandler.handelAnyError {
            mqttClient.subscribe("emergency", 2, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    receiveMessage()
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    connectToMqttServer()
                }
            })
        }
    }

    private fun receiveMessage() {
        errorHandler.handelAnyError {
            mqttClient.addCallback(object : MqttCallback {
                override fun connectionLost(cause: Throwable?) {
                    connectToMqttServer()
                }

                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    GlobalUiModel.emergencyState.value = message?.payload?.decodeToString() == "1"
                }

                override fun deliveryComplete(token: IMqttDeliveryToken?) {
                }
            })
        }
    }
}