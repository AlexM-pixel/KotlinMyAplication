package com.example.kotlinmyaplicationtasks.task5

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.kotlinmyaplicationtasks.R


class MyChargeManager(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
 companion object {
    const val CHANNEL_ID = "CHANNEL_charge"
 }
    var level:Int=0
    var usbCharge: Boolean = false
    var acDcCharge: Boolean = false
    override fun doWork(): Result {
        val myfilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus: Intent? = applicationContext.registerReceiver(null, myfilter)
         level = batteryStatus!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val chargePlug: Int = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
        usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB
        acDcCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC
        Log.e("1111", "doWork() from chargeManager   $level   $usbCharge $acDcCharge")
        createNotification()
        return Result.success()
    }

    fun createNotification() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                CHANNEL_ID,
                "myChannel_charge",
                NotificationManager.IMPORTANCE_HIGH
            )
            mChannel.description = "This is MyChannel"
            val notificationManager: NotificationManager = applicationContext.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("заряд: $level")
            .setContentText("usb=$usbCharge ac/dc=$acDcCharge")
            .build()
        val managerCompat: NotificationManagerCompat =
            NotificationManagerCompat.from(applicationContext)
        managerCompat.notify(1, notification)

    }
}