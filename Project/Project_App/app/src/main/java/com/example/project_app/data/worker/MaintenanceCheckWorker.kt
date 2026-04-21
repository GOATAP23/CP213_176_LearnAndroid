package com.example.project_app.data.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.project_app.MainActivity
import com.example.project_app.R
import com.example.project_app.data.local.CarDatabase
import com.example.project_app.data.local.SettingsDataStore
import com.example.project_app.data.local.entity.MaintenanceTypes
import kotlinx.coroutines.flow.first

/**
 * MaintenanceCheckWorker — ตรวจสอบสถานะรถทุก 24 ชม.
 * ถ้ามีรถที่เกินกำหนดเปลี่ยนน้ำมัน → ส่ง Notification
 */
class MaintenanceCheckWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val CHANNEL_ID = "maintenance_alerts"
        const val NOTIFICATION_ID = 1001
        const val WORK_NAME = "maintenance_check"
    }

    override suspend fun doWork(): Result {
        val database = CarDatabase.getDatabase(context)
        val settingsDataStore = SettingsDataStore(context)

        val kmInterval = settingsDataStore.oilChangeKmInterval.first()
        val dayInterval = settingsDataStore.oilChangeDayInterval.first()

        val cars = database.carDao().getAllCars().first()

        for (car in cars) {
            val latestOil = database.maintenanceDao()
                .getLatestMaintenanceByType(car.id, MaintenanceTypes.OIL_CHANGE)
                .first()

            if (latestOil != null) {
                val mileageSince = (car.currentMileage - latestOil.mileage).coerceAtLeast(0)
                val daysSince = ((System.currentTimeMillis() - latestOil.date) / (1000L * 60 * 60 * 24)).toInt()

                if (mileageSince > kmInterval || daysSince > dayInterval) {
                    sendNotification(
                        "${car.year} ${car.brand} ${car.model}",
                        mileageSince,
                        daysSince
                    )
                }
            } else {
                // ไม่เคยบันทึกเปลี่ยนน้ำมัน → แจ้งเตือน
                sendNotification("${car.year} ${car.brand} ${car.model}", 0, 0)
            }
        }

        return Result.success()
    }

    private fun sendNotification(carName: String, mileage: Int, days: Int) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)

        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = context.getString(R.string.notification_channel_desc)
        }
        notificationManager.createNotificationChannel(channel)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val title = context.getString(R.string.notification_title, carName)
        val body = if (mileage > 0) {
            context.getString(R.string.notification_body_detail, mileage, days)
        } else {
            context.getString(R.string.notification_body_no_record)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID + carName.hashCode(), notification)
    }
}
