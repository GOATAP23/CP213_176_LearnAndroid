package com.example.project_app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_app.data.local.CarDao
import com.example.project_app.data.local.ExpenseDao
import com.example.project_app.data.local.MaintenanceDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar

data class TransactionUI(
    val title: String,
    val dateMillis: Long,
    val amount: Double,
    val isMaintenance: Boolean
)

data class DashboardState(
    val monthlyTotal: Double = 0.0,
    val yearlyTotal: Double = 0.0,
    val recentTransactions: List<TransactionUI> = emptyList()
)

class DashboardViewModel(
    private val carId: Int, 
    carDao: CarDao,
    maintenanceDao: MaintenanceDao,
    expenseDao: ExpenseDao
) : ViewModel() {

    val currentCar = carDao.getCarById(carId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val dashboardState = combine(
        maintenanceDao.getMaintenancesForCar(carId),
        expenseDao.getExpensesForCar(carId)
    ) { maintenances, expenses ->
        
        val allTrx = maintenances.map { 
            TransactionUI(it.type, it.date, it.cost, true) 
        } + expenses.map { 
            TransactionUI(it.type, it.date, it.amount, false) 
        }

        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)

        var monthSum = 0.0
        var yearSum = 0.0

        allTrx.forEach { trx ->
            calendar.timeInMillis = trx.dateMillis
            val trxYear = calendar.get(Calendar.YEAR)
            val trxMonth = calendar.get(Calendar.MONTH)

            if (trxYear == currentYear) {
                yearSum += trx.amount
                if (trxMonth == currentMonth) {
                    monthSum += trx.amount
                }
            }
        }

        val recentFive = allTrx.sortedByDescending { it.dateMillis }.take(5)

        DashboardState(monthSum, yearSum, recentFive)

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DashboardState())
}
