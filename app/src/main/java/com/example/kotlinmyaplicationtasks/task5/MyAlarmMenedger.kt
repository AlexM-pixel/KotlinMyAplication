package com.example.kotlinmyaplicationtasks.task5


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.kotlinmyaplicationtasks.R

class MyAlarmMenedger(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        createNotification()
        Log.e("1111", "doWork()")
        return Result.success()
    }

    fun createNotification() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                "CHANNEL_1",
                "channel_alarm",
                NotificationManager.IMPORTANCE_HIGH
            )
            mChannel.description = "This is MyChannel"
            val notificationManager: NotificationManager = applicationContext.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
        val notification = NotificationCompat.Builder(applicationContext, "CHANNEL_1")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("посмотреть инстаграмчик")
            .build()
        val managerCompat: NotificationManagerCompat =
            NotificationManagerCompat.from(applicationContext)
        managerCompat.notify(1, notification)

    }
}