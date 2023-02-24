package android.system.myapplication.utils

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

object ScheduleNotification {
    fun scheduleNotification(calendar: Calendar, context: Context) {
        val intent = Intent(
            context,
            MyNotification::class.java
        )

        intent.putExtra("titleExtra", "Dynamic Title")
        intent.putExtra("textExtra", "Dynamic Text Body")

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        alarmManager!!.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    fun createNotificationChannel(context: Context) {
        val name = "Daily Alerts"
        val des = "Channel Description A Brief"
        val importance = NotificationManager.IMPORTANCE_DEFAULT // TODO: change priority
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = des
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        manager!!.createNotificationChannel(channel)
    }
}