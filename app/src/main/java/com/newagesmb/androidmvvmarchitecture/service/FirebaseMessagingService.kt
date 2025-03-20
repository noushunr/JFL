package com.newagesmb.androidmvvmarchitecture.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import com.newagesmb.androidmvvmarchitecture.BuildConfig
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.local.preferences.AppPreferences
import com.newagesmb.androidmvvmarchitecture.data.repository.AppRepository
import com.newagesmb.androidmvvmarchitecture.di.ApplicationScope
import com.newagesmb.androidmvvmarchitecture.di.MainDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject


@AndroidEntryPoint
class FirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var preferences: AppPreferences

    @Inject
    @ApplicationScope
    lateinit var coroutineScope: CoroutineScope

    @Inject
    @MainDispatcher
    lateinit var mainDispatcher: CoroutineDispatcher

    @Inject
    @ApplicationContext
    lateinit var context: Context
    companion object {
        const val CHANNEL_NAME = "app_name"
        const val CHANNEL_ID = "${BuildConfig.APPLICATION_ID}.notification"
        const val CHANNEL_DESC = "Channel for all notifications"
        const val NOTIFICATION_ID = 101
        const val TAG = "FCMService"

    }
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val payload = remoteMessage.data
        Log.e(TAG, "Message data payload: $payload")
        createNotification(remoteMessage)

    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        preferences.fcmToken = token
    }

    private fun createNotificationChannel() {
        val importance = NotificationManagerCompat.IMPORTANCE_DEFAULT
        val channel =
            NotificationChannelCompat.Builder(CHANNEL_ID, importance).run {
                setDescription(CHANNEL_DESC)
                setName(CHANNEL_NAME)
                build()
            }
        NotificationManagerCompat.from(this).createNotificationChannel(channel)
    }
    private fun createNotification(remoteMessage: RemoteMessage) {
        val title = remoteMessage.data["title"]
        val body = remoteMessage.data["body"]


        val notification =
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(
                    BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher_round)
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
        // setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(this)) { if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
            notify(NOTIFICATION_ID, notification) }
    }

}
