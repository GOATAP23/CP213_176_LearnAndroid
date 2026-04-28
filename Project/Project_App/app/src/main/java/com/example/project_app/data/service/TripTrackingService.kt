package com.example.project_app.data.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.location.Location
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.example.project_app.MainActivity
import com.example.project_app.R
import com.example.project_app.data.local.CarDatabase
import com.example.project_app.data.local.entity.TripEntity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Foreground Service สำหรับจับ GPS location ระหว่างขับรถ
 * คำนวณระยะทางจาก location updates
 */
class TripTrackingService : Service() {

    companion object {
        const val ACTION_START_TRIP = "ACTION_START_TRIP"
        const val ACTION_STOP_TRIP = "ACTION_STOP_TRIP"
        const val EXTRA_CAR_ID = "EXTRA_CAR_ID"
        const val CHANNEL_ID = "trip_tracking"
        const val NOTIFICATION_ID = 2001
    }

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lastLocation: Location? = null
    private var totalDistance: Double = 0.0
    private var currentTripId: Long = -1
    private var isTracking = false
    private var trackingCarName: String? = null

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            val newLocation = result.lastLocation ?: return
            lastLocation?.let { prev ->
                val distMeters = prev.distanceTo(newLocation)
                // ไม่นับถ้าระยะทางน้อยกว่า 10 เมตร (ลด noise)
                if (distMeters > 10) {
                    totalDistance += distMeters / 1000.0 // แปลงเป็น km
                }
            }
            lastLocation = newLocation
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START_TRIP -> {
                val carId = intent.getIntExtra(EXTRA_CAR_ID, -1)
                startTrip(carId)
            }
            ACTION_STOP_TRIP -> stopTrip()
        }
        return START_STICKY
    }

    private fun startTrip(carId: Int = -1) {
        if (isTracking) return
        isTracking = true
        totalDistance = 0.0
        lastLocation = null

        startForeground(
            NOTIFICATION_ID,
            createNotification(),
            ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
        )

        // บันทึก Trip เริ่มต้นใน DB
        serviceScope.launch {
            val database = CarDatabase.getDatabase(applicationContext)
            // ถ้ามี carId จาก Bluetooth ให้ใช้ ถ้าไม่มีให้ใช้รถคันแรก
            val targetCar = if (carId > 0) {
                database.carDao().getCarByIdSync(carId)
            } else {
                database.carDao().getAllCars().first().firstOrNull()
            }
            if (targetCar != null) {
                trackingCarName = "${targetCar.brand} ${targetCar.model}"
                // อัปเดต Notification ให้แสดงชื่อรถ
                val nm = getSystemService(NotificationManager::class.java)
                nm.notify(NOTIFICATION_ID, createNotification())

                val trip = TripEntity(
                    carId = targetCar.id,
                    startTime = System.currentTimeMillis(),
                    isActive = true
                )
                currentTripId = database.tripDao().insertTrip(trip)
            }
        }

        // เริ่มจับ Location
        try {
            val request = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                5000L // ทุก 5 วินาที
            ).setMinUpdateDistanceMeters(10f).build()

            fusedLocationClient.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            stopSelf()
        }
    }

    private fun stopTrip() {
        if (!isTracking) {
            stopSelf()
            return
        }
        isTracking = false
        fusedLocationClient.removeLocationUpdates(locationCallback)

        // อัปเดต Trip ใน DB
        serviceScope.launch {
            if (currentTripId > 0) {
                val database = CarDatabase.getDatabase(applicationContext)
                val trip = database.tripDao().getTripById(currentTripId.toInt())
                trip?.let {
                    database.tripDao().updateTrip(
                        it.copy(
                            endTime = System.currentTimeMillis(),
                            distanceKm = totalDistance,
                            isActive = false
                        )
                    )

                    // อัปเดตเลขไมล์รถ
                    val car = database.carDao().getCarByIdSync(it.carId)
                    if (car != null && totalDistance > 0) {
                        database.carDao().updateCar(
                            car.copy(currentMileage = car.currentMileage + totalDistance.toInt())
                        )
                    }
                }
            }
        }

        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun createNotification(): Notification {
        val channel = NotificationChannel(
            CHANNEL_ID,
            getString(R.string.trip_channel_name),
            NotificationManager.IMPORTANCE_LOW
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        // แสดงชื่อรถใน Notification ถ้ามี
        val bodyText = if (trackingCarName != null) {
            getString(R.string.trip_tracking_body_car, trackingCarName!!)
        } else {
            getString(R.string.trip_tracking_body)
        }

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(getString(R.string.trip_tracking_title))
            .setContentText(bodyText)
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isTracking) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }
}
