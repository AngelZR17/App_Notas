package com.brian_david_angel.notas.others_codes

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.brian_david_angel.notas.MainActivity
import com.brian_david_angel.notas.R

class AlarmNotification(): BroadcastReceiver() {

    companion object{
        const val NOTIFICATION_ID = 1
    }

    override fun onReceive(context: Context, p1: Intent) {
        if(p1.hasExtra("textoNotificacion")){
            val textoNotificacion: String? = p1.getStringExtra("textoNotificacion")
            createSimpleNotification(context, textoNotificacion)
        }


    }

    private fun createSimpleNotification(context: Context, textoNotificacion: String?) {
        var text=textoNotificacion?.split("|")
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, flag)

        val notification = NotificationCompat.Builder(context, Notificaciones.MY_CHANNEL_ID)
            .setSmallIcon(R.drawable.notification)
            .setContentTitle(text?.get(0))
            .setContentText(text?.get(1))
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(text?.get(2))
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)
    }

}