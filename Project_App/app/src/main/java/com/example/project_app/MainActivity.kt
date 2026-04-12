package com.example.project_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.project_app.data.local.CarDatabase
import com.example.project_app.data.local.SettingsDataStore
import com.example.project_app.ui.navigation.AppNavigation
import com.example.project_app.ui.theme.Project_AppTheme

class MainActivity : ComponentActivity() {

    private val database by lazy {
        CarDatabase.getDatabase(applicationContext)
    }

    private val settingsDataStore by lazy {
        SettingsDataStore(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // อ่านค่า Dark Mode จาก DataStore เพื่อ Override ธีม
            val isDarkMode by settingsDataStore.isDarkMode.collectAsState(initial = false)

            Project_AppTheme(overrideDarkMode = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        database = database,
                        settingsDataStore = settingsDataStore
                    )
                }
            }
        }
    }
}