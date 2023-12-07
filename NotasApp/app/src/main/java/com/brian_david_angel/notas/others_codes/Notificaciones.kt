package com.brian_david_angel.notas.others_codes

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class Notificaciones {
    companion object {
        const val MY_CHANNEL_ID = "myChannel"
    }

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleNotification(ctx: Context, titulo: String, millis: Long) {
        val intervalos = millis / 3
        val totalNotificaciones = 3
        val tiempoActual = System.currentTimeMillis()

        for (i in 1..totalNotificaciones) {
            val intent = Intent(ctx, AlarmNotification::class.java)
            intent.putExtra("textoNotificacion", titulo)
            val pendingIntent = PendingIntent.getBroadcast(
                ctx,
                1+i,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, tiempoActual + i * intervalos, pendingIntent)
        }

    }
    fun createChannel(ctx: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(MY_CHANNEL_ID, "MySuperChannel", NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "hol"
            }

            val notificationManager: NotificationManager =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }
}