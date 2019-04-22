package br.com.uvets.uvetsandroid.business

import br.com.uvets.uvetsandroid.business.interfaces.Configuration
import br.com.uvets.uvetsandroid.utils.AppLogger
import br.com.uvets.uvetsandroid.utils.NotificationUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import org.koin.android.ext.android.inject

class AppMessagingService : FirebaseMessagingService() {

    private val TAG = AppMessagingService::class.java.simpleName

    private val mConfiguration: Configuration by inject()

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        AppLogger.d("$TAG > From: ${remoteMessage?.from}")

        remoteMessage?.let {
            AppLogger.d("$TAG > RemoteMessage: ${Gson().toJson(it)}")

            if (it.data.isNotEmpty()) {
                AppLogger.d("$TAG > Message data payload: ${it.data}")
            }

            it.notification?.let { notification ->
                AppLogger.d("$TAG > Message Notification Body: ${notification.body}")
                NotificationUtils.notificationSimple(applicationContext, notification.title!!, notification.body!!)
            }
        }
    }

    override fun onNewToken(token: String?) {
        AppLogger.d("$TAG > Refreshed token: $token")
        token?.let {
            mConfiguration.getStorage().saveDeviceId(it)
        }
    }

}