package com.example.project_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.project_app.data.local.CarDatabase
import com.example.project_app.ui.navigation.AppNavigation
import com.example.project_app.ui.theme.Project_AppTheme

class MainActivity : ComponentActivity() {

    private val database by lazy {
        CarDatabase.getDatabase(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Project_AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(database = database)
                }
            }
        }
    }
}