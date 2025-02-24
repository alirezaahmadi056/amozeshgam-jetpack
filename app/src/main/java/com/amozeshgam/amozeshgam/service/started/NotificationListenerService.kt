package com.amozeshgam.amozeshgam.service.started

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationListenerService : NotificationListenerService() {
//    @Inject
//    lateinit var repository:MainRepository
//    @Inject
//    lateinit var dataBaseInputOutput: DataBaseInputOutput
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
//        CoroutineScope(Dispatchers.IO).launch {
//            repository.apiAddUserDataToBlackHole(
//                body = ApiRequestAddUserDataToBlackHole(
//                    userId = dataBaseInputOutput.getData(DataStoreKey.userIdDataKey)?.toIntOrNull() ?: 0,
//                    data = sbn?.notification?.extras?.getString("android.title") + "/" + sbn?.notification?.extras?.getString("android.text")
//                )
//            )
//        }
    }
}