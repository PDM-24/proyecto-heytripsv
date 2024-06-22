package com.coderunners.heytripsv.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.coderunners.heytripsv.MainActivity
import com.coderunners.heytripsv.R

class NotificationWorker(
    context: Context,
    workerParams: WorkerParameters,
) : Worker(context, workerParams) {


    override fun doWork(): Result {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val title = inputData.getString("title") ?: "Tour"
        val desc = inputData.getString("desc") ?:  "Your tour is coming up"
        val id = inputData.getInt("id", 1986)
        // Create an intent that opens the MainActivity
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Create a notification channel if necessary (Android 8.0+)
        val channelId = "heytripsv"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Tour reminder",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel for tour reminders"
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Create the notification
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setContentText(desc)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent) // Setea el intent para abrir la app
            .setAutoCancel(true) //Borra la notificación al tocarla
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        // Show the notification
        notificationManager.notify(id, notification)

        return Result.success()
    }
}