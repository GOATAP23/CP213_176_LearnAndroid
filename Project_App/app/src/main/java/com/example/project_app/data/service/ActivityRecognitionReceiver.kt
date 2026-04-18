package com.example.project_app.data.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.ActivityRecognitionResult
import com.google.android.gms.location.DetectedActivity

/**
 * BroadcastReceiver รับ Activity Recognition updates จาก Google Play Services
 * ตรวจจับว่าผู้ใช้กำลัง IN_VEHICLE หรือ STILL
 */
class ActivityRecognitionReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_ACTIVITY_UPDATE =
            "com.example.project_app.ACTION_ACTIVITY_UPDATE"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (!ActivityRecognitionResult.hasResult(intent)) return

        val result = ActivityRecognitionResult.extractResult(intent) ?: return
        val activity = result.mostProbableActivity

        // ต้องมั่นใจ >= 70% ถึงจะตอบสนอง
        if (activity.confidence < 70) return

        when (activity.type) {
            DetectedActivity.IN_VEHICLE -> {
                // เริ่มจับ Trip (ถ้ายังไม่มี active trip)
                val serviceIntent = Intent(context, TripTrackingService::class.java).apply {
                    action = TripTrackingService.ACTION_START_TRIP
                }
                context.startForegroundService(serviceIntent)
            }

            DetectedActivity.STILL -> {
                // หยุด Trip
                val serviceIntent = Intent(context, TripTrackingService::class.java).apply {
                    action = TripTrackingService.ACTION_STOP_TRIP
                }
                context.startForegroundService(serviceIntent)
            }
        }
    }
}
