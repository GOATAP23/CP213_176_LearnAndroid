package com.example.project_app.data.service

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.project_app.data.local.CarDatabase
import com.example.project_app.data.local.SettingsDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * BroadcastReceiver ดักจับเหตุการณ์ Bluetooth ACL_CONNECTED / ACL_DISCONNECTED
 * เมื่อ Bluetooth ของรถที่ผูกไว้เชื่อมต่อ → เริ่ม TripTrackingService ให้คันนั้น
 * เมื่อ Bluetooth ของรถที่ผูกไว้ตัดการเชื่อมต่อ → หยุด TripTrackingService
 */
class BluetoothConnectionReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "BtConnectionReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val device: BluetoothDevice? =
            intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE, BluetoothDevice::class.java)

        if (device == null) {
            Log.w(TAG, "No Bluetooth device in intent")
            return
        }

        val macAddress: String = try {
            device.address
        } catch (e: SecurityException) {
            Log.w(TAG, "Missing BLUETOOTH_CONNECT permission", e)
            return
        }

        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // ตรวจสอบว่า auto-track เปิดอยู่หรือไม่
                val settings = SettingsDataStore(context)
                val isAutoTrackEnabled = settings.isAutoTrackEnabled.first()
                if (!isAutoTrackEnabled) {
                    Log.d(TAG, "Auto-track is disabled, ignoring Bluetooth event")
                    return@launch
                }

                // ค้นหารถที่ผูกกับ MAC address นี้
                val database = CarDatabase.getDatabase(context)
                val car = database.carDao().getCarByBluetoothMac(macAddress)

                if (car == null) {
                    Log.d(TAG, "No car linked to Bluetooth MAC: $macAddress")
                    return@launch
                }

                Log.d(TAG, "Matched car: ${car.brand} ${car.model} (id=${car.id})")

                when (intent.action) {
                    BluetoothDevice.ACTION_ACL_CONNECTED -> {
                        Log.i(TAG, "Bluetooth connected → Starting trip for car id=${car.id}")
                        val serviceIntent = Intent(context, TripTrackingService::class.java).apply {
                            action = TripTrackingService.ACTION_START_TRIP
                            putExtra(TripTrackingService.EXTRA_CAR_ID, car.id)
                        }
                        context.startForegroundService(serviceIntent)
                    }

                    BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                        Log.i(TAG, "Bluetooth disconnected → Stopping trip for car id=${car.id}")
                        val serviceIntent = Intent(context, TripTrackingService::class.java).apply {
                            action = TripTrackingService.ACTION_STOP_TRIP
                        }
                        context.startForegroundService(serviceIntent)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error handling Bluetooth event", e)
            } finally {
                pendingResult.finish()
            }
        }
    }
}
