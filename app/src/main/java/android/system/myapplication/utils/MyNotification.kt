package android.system.myapplication.utils

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.system.myapplication.R
import androidx.core.app.NotificationCompat

const val channelID = "TODOLIST_NOTIFICATION"

class MyNotification : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("textExtra").toString()
        val title = intent.getStringExtra("titleExtra").toString()
        val notification =
            NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(message).setContentTitle(title).build()
        val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1212, notification)
    }
}