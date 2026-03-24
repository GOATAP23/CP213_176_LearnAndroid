package com.example.a176lablearnandroid

import android.app.Application
import android.location.Location
import androidx.annotation.RequiresPermission
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SensorViewModel(application: Application) : AndroidViewModel(application) {
    
    private val sensorTracker = SensorTracker(application)

    // ตัวแปรท่อน้ำกักเก็บค่า Sensor เอียง (Accelerometer)
    private val _sensorData = MutableStateFlow(floatArrayOf(0f, 0f, 0f))
    val sensorData: StateFlow<FloatArray> = _sensorData.asStateFlow()

    // ตัวแปรท่อน้ำกักเก็บค่าที่อยู่ GPS
    private val _locationData = MutableStateFlow<Location?>(null)
    val locationData: StateFlow<Location?> = _locationData.asStateFlow()

    init {
        sensorTracker.startListeningSensors { newValue ->
            _sensorData.value = newValue
        }
    }

    fun startLocation() {
        sensorTracker.startLocationTracking { newLocation ->
            _locationData.value = newLocation
        }
    }

    fun stopLocation() {
        sensorTracker.stopLocationTracking()
        _locationData.value = null // รีเซ็ตค่าบน UI ถ้าหยุด (ถ้าต้องการ)
    }

    override fun onCleared() {
        super.onCleared()
        sensorTracker.stopListeningSensors()
        sensorTracker.stopLocationTracking()
    }
}
