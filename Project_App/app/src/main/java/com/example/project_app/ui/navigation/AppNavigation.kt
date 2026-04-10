package com.example.project_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project_app.data.local.CarDatabase
import com.example.project_app.ui.screens.add_car.AddCarScreen
import com.example.project_app.ui.screens.add_car.CarViewModel
import com.example.project_app.ui.screens.add_record.AddRecordScreen
import com.example.project_app.ui.screens.add_record.AddRecordViewModel
import com.example.project_app.ui.screens.home.DashboardScreen
import com.example.project_app.ui.screens.home.DashboardViewModel

@Composable
fun AppNavigation(database: CarDatabase) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "dashboard") {
        
        composable("dashboard") {
            val viewModel: DashboardViewModel = viewModel(
                factory = AppViewModelFactory(database, carId = 1) 
            )
            
            DashboardScreen(
                viewModel = viewModel,
                onNavigateToAddRecord = { carId ->
                    navController.navigate("add_record/$carId")
                },
                onNavigateToAddCar = {
                    navController.navigate("add_car")
                }
            )
        }

        composable("add_car") {
            val viewModel: CarViewModel = viewModel(
                factory = AppViewModelFactory(database)
            )
            AddCarScreen(
                viewModel = viewModel,
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
                factory = AppViewModelFactory(database, passedCarId)
            )
            AddRecordScreen(
                viewModel = viewModel,
                onNavigateBack = { 
                    navController.popBackStack() 
                }
            )
        }
    }
}
