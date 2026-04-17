package com.example.project_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_app.data.local.CarDatabase
import com.example.project_app.data.local.SettingsDataStore
import com.example.project_app.ui.screens.add_car.AddCarScreen
import com.example.project_app.ui.screens.add_car.CarViewModel
import com.example.project_app.ui.screens.add_record.AddRecordScreen
import com.example.project_app.ui.screens.add_record.AddRecordViewModel
import com.example.project_app.ui.screens.home.DashboardScreen
import com.example.project_app.ui.screens.home.DashboardViewModel
import com.example.project_app.ui.screens.settings.SettingsScreen
import com.example.project_app.ui.screens.settings.SettingsViewModel
import com.example.project_app.ui.screens.home.MainHomeScreen
import com.example.project_app.ui.screens.tutorial.TutorialScreen

@Composable
fun AppNavigation(
    database: CarDatabase,
    settingsDataStore: SettingsDataStore
) {
    val navController = rememberNavController()

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

        composable("settings") {
            SettingsScreen(
                viewModel = settingsViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
