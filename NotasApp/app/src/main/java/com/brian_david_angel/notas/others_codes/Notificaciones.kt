package com.brian_david_angel.notas.others_codes

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.Calendar

class Notificaciones {
    companion object {
        const val MY_CHANNEL_ID = "myChannel"
    }

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleNotification(ctx: Context) {
        val intent = Intent(ctx, AlarmNotification::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            ctx,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().timeInMillis + 15000, pendingIntent)
    }

    fun createChannel(ctx: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(MY_CHANNEL_ID, "MySuperChannel", NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "SUSCRIBETE"
            }

            val notificationManager: NotificationManager =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }
}