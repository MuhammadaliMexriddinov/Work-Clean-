package uz.gita.waterappgoogleplaygb.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import uz.gita.waterappgoogleplaygb.MainActivity
import uz.gita.waterappgoogleplaygb.R


class Work(val context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    override fun doWork(): Result {


        createChannel()
        val notification = NotificationCompat.Builder(context, Constants.CHANNEL_ID).apply {
            setSmallIcon(R.drawable.clock)
            setContentTitle("Water Reminder")
            setContentText("It's time to drink water!!!")
            setContentIntent(
                PendingIntent.getActivity(
                    context,
                    0,
                    Intent(context, MainActivity::class.java),
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
            setSound(
                Uri.parse("android.resource://" + context.packageName + "/" + R.raw.water_flow)
            )
            setAutoCancel(true)
        }.build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(0, notification)
        return Result.success()
    }


    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                Constants.CHANNEL_ID,
                "Main",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()

            notificationChannel.setSound(
                Uri.parse("android.resource://" + context.packageName + "/" + R.raw.water_flow),
                audioAttributes
            )

            val notificationManager: NotificationManager =
                context.getSystemService(NotificationManager::class.java)

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}