package com.example.project_app.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.project_app.data.local.CarDatabase
import com.example.project_app.data.local.SettingsDataStore
import com.example.project_app.ui.screens.add_car.CarViewModel
import com.example.project_app.ui.screens.add_record.AddRecordViewModel
import com.example.project_app.ui.screens.calendar.CalendarViewModel
import com.example.project_app.ui.screens.history.HistoryViewModel
import com.example.project_app.ui.screens.home.DashboardViewModel
import com.example.project_app.ui.screens.settings.SettingsViewModel
import com.example.project_app.ui.screens.trips.TripViewModel

class AppViewModelFactory(
    private val database: CarDatabase,
    private val settingsDataStore: SettingsDataStore? = null,
    private val carId: Int = 0
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                DashboardViewModel(
                    database.carDao(), database.maintenanceDao(),
                    database.expenseDao(), settingsDataStore!!
                ) as T
            }
            modelClass.isAssignableFrom(AddRecordViewModel::class.java) -> {
                AddRecordViewModel(carId, database.carDao(), database.maintenanceDao(), database.expenseDao()) as T
            }
            modelClass.isAssignableFrom(CarViewModel::class.java) -> {
                CarViewModel(database.carDao()) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(settingsDataStore!!) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(carId, database.maintenanceDao(), database.expenseDao()) as T
            }
            modelClass.isAssignableFrom(TripViewModel::class.java) -> {
                TripViewModel(carId, database.tripDao()) as T
            }
            modelClass.isAssignableFrom(CalendarViewModel::class.java) -> {
                CalendarViewModel(carId, database.maintenanceDao(), database.expenseDao()) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
