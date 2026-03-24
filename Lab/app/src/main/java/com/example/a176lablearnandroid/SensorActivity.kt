package com.example.a176lablearnandroid

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat

class SensorActivity : ComponentActivity() {
    
    private val sensorViewModel: SensorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SensorScreen(sensorViewModel)
                }
            }
        }
    }
}

@Composable
fun SensorScreen(viewModel: SensorViewModel) {
    val context = LocalContext.current
    val sensorValues by viewModel.sensorData.collectAsState()
    val locationValue by viewModel.locationData.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.any { it.value } // ให้ผ่านถ้าได้อย่างใดอย่างหนึ่ง
        if (granted) {
            viewModel.startLocation()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sensor and Location", 
            fontSize = 24.sp, 
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        Text(text = "Accelerometer:", fontSize = 20.sp)
        Text(text = "X: ${"%.2f".format(sensorValues[0])}")
        Text(text = "Y: ${"%.2f".format(sensorValues[1])}")
        Text(text = "Z: ${"%.2f".format(sensorValues[2])}")
        Text(text = "(Updates automatically)", fontSize = 12.sp, color = androidx.compose.ui.graphics.Color.Gray)

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "GPS Location:", fontSize = 20.sp)
        if (locationValue != null) {
            Text(text = "Lat: ${locationValue?.latitude}")
            Text(text = "Lng: ${locationValue?.longitude}")
        } else {
            Text(text = "Not tracking")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val hasFineLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            val hasCoarseLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            if (hasFineLocation || hasCoarseLocation) {
                viewModel.startLocation()
            } else {
                permissionLauncher.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ))
            }
        }) {
            Text("Start Tracking Location")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { viewModel.stopLocation() }) {
            Text("Stop Tracking Location")
        }
    }
}
