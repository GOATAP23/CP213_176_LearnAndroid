package com.example.project_app.ui.navigation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_app.data.export.CsvExporter
import com.example.project_app.data.export.ShareHelper
import com.example.project_app.data.local.CarDatabase
import com.example.project_app.data.local.SettingsDataStore
import com.example.project_app.ui.screens.add_car.AddCarScreen
import com.example.project_app.ui.screens.add_car.CarViewModel
import com.example.project_app.ui.screens.add_record.AddRecordScreen
import com.example.project_app.ui.screens.add_record.AddRecordViewModel
import com.example.project_app.ui.screens.add_record.RecordType
import com.example.project_app.ui.screens.calendar.CalendarScreen
import com.example.project_app.ui.screens.calendar.CalendarViewModel
import com.example.project_app.ui.screens.history.HistoryScreen
import com.example.project_app.ui.screens.history.HistoryViewModel
import com.example.project_app.ui.screens.home.DashboardScreen
import com.example.project_app.ui.screens.home.DashboardViewModel
import com.example.project_app.ui.screens.settings.SettingsScreen
import com.example.project_app.ui.screens.settings.SettingsViewModel
import com.example.project_app.ui.screens.home.MainHomeScreen
import com.example.project_app.ui.screens.trips.TripScreen
import com.example.project_app.ui.screens.trips.TripViewModel
import com.example.project_app.ui.screens.tutorial.TutorialScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(
    database: CarDatabase,
    settingsDataStore: SettingsDataStore
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // SettingsViewModel ต้องแชร์ข้ามหน้าจอ (เพื่อ Toggle Dark Mode จาก Dashboard)
    val settingsViewModel: SettingsViewModel = viewModel(
        factory = AppViewModelFactory(database, settingsDataStore)
    )

    NavHost(navController = navController, startDestination = "home") {
        
        composable("home") {
            MainHomeScreen(
                onNavigateToDashboard = { navController.navigate("dashboard") },
                onNavigateToTutorial = { navController.navigate("tutorial") },
                onNavigateToSettings = { navController.navigate("settings") }
            )
        }

        composable("tutorial") {
            TutorialScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("dashboard") {
            val viewModel: DashboardViewModel = viewModel(
                factory = AppViewModelFactory(database, settingsDataStore)
            )
            val car by viewModel.currentCar.collectAsState()
            
            DashboardScreen(
                viewModel = viewModel,
                settingsViewModel = settingsViewModel,
                onNavigateToAddRecord = { carId ->
                    navController.navigate("add_record/$carId")
                },
                onNavigateToAddCar = {
                    navController.navigate("add_car")
                },
                onNavigateToEditCar = { carId ->
                    navController.navigate("add_car?carId=$carId")
                },
                onNavigateToSettings = {
                    navController.navigate("settings")
                },
                onNavigateToHistory = { carId ->
                    navController.navigate("history/$carId")
                },
                onNavigateToTrips = { carId ->
                    navController.navigate("trips/$carId")
                },
                onNavigateToCalendar = { carId ->
                    navController.navigate("calendar/$carId")
                },
                onShareCar = {
                    car?.let { c ->
                        coroutineScope.launch {
                            val text = ShareHelper.generateShareText(context, database, c)
                            ShareHelper.shareText(context, text)
                        }
                    }
                }
            )
        }

        composable(
            route = "add_car?carId={carId}",
            arguments = listOf(navArgument("carId") { type = NavType.IntType; defaultValue = -1 })
        ) { backStackEntry ->
            val passedCarId = backStackEntry.arguments?.getInt("carId") ?: -1
            val viewModel: CarViewModel = viewModel(
                factory = AppViewModelFactory(database, settingsDataStore)
            )
            AddCarScreen(
                viewModel = viewModel,
                carId = passedCarId,
                onNavigateBack = {
                    navController.popBackStack() 
                }
            )
        }

        composable(
            route = "add_record/{carId}", 
            arguments = listOf(navArgument("carId") { type = NavType.IntType }) 
        ) { backStackEntry ->
            val passedCarId = backStackEntry.arguments?.getInt("carId") ?: 1
            
            val viewModel: AddRecordViewModel = viewModel(
                factory = AppViewModelFactory(database, settingsDataStore, passedCarId)
            )
            AddRecordScreen(
                viewModel = viewModel,
                onNavigateBack = { 
                    navController.popBackStack() 
                }
            )
        }

        // Edit Record Route
        composable(
            route = "edit_record/{carId}/{recordType}/{recordId}",
            arguments = listOf(
                navArgument("carId") { type = NavType.IntType },
                navArgument("recordType") { type = NavType.StringType },
                navArgument("recordId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val passedCarId = backStackEntry.arguments?.getInt("carId") ?: 1
            val recordTypeStr = backStackEntry.arguments?.getString("recordType") ?: "maintenance"
            val recordId = backStackEntry.arguments?.getInt("recordId") ?: 0

            val viewModel: AddRecordViewModel = viewModel(
                factory = AppViewModelFactory(database, settingsDataStore, passedCarId)
            )

            LaunchedEffect(Unit) {
                val type = if (recordTypeStr == "maintenance") RecordType.MAINTENANCE else RecordType.EXPENSE
                viewModel.loadExistingRecord(recordId, type)
            }

            AddRecordScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // History Screen
        composable(
            route = "history/{carId}",
            arguments = listOf(navArgument("carId") { type = NavType.IntType })
        ) { backStackEntry ->
            val passedCarId = backStackEntry.arguments?.getInt("carId") ?: 1
            val viewModel: HistoryViewModel = viewModel(
                factory = AppViewModelFactory(database, settingsDataStore, passedCarId)
            )
            HistoryScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onEditRecord = { recordId, isMaintenance ->
                    val type = if (isMaintenance) "maintenance" else "expense"
                    navController.navigate("edit_record/$passedCarId/$type/$recordId")
                }
            )
        }

        // Trips Screen
        composable(
            route = "trips/{carId}",
            arguments = listOf(navArgument("carId") { type = NavType.IntType })
        ) { backStackEntry ->
            val passedCarId = backStackEntry.arguments?.getInt("carId") ?: 1
            val viewModel: TripViewModel = viewModel(
                factory = AppViewModelFactory(database, settingsDataStore, passedCarId)
            )
            TripScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Calendar Screen
        composable(
            route = "calendar/{carId}",
            arguments = listOf(navArgument("carId") { type = NavType.IntType })
        ) { backStackEntry ->
            val passedCarId = backStackEntry.arguments?.getInt("carId") ?: 1
            val viewModel: CalendarViewModel = viewModel(
                factory = AppViewModelFactory(database, settingsDataStore, passedCarId)
            )
            CalendarScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("settings") {
            val car by database.carDao().getAllCars()
                .collectAsState(initial = emptyList())

            // CSV export launcher
            val csvLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.CreateDocument("text/csv")
            ) { uri: Uri? ->
                uri?.let { outputUri ->
                    coroutineScope.launch {
                        val firstCar = car.firstOrNull() ?: return@launch
                        context.contentResolver.openOutputStream(outputUri)?.let { stream ->
                            CsvExporter.exportCarData(context, database, firstCar, stream)
                        }
                    }
                }
            }

            SettingsScreen(
                viewModel = settingsViewModel,
                onNavigateBack = { navController.popBackStack() },
                onExportData = {
                    val firstCar = car.firstOrNull()
                    if (firstCar != null) {
                        csvLauncher.launch(CsvExporter.generateFileName(firstCar))
                    }
                }
            )
        }
    }
}
