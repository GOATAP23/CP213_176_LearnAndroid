package com.example.project_app.ui.screens.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_app.data.local.ExpenseDao
import com.example.project_app.data.local.MaintenanceDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar

/**
 * ข้อมูลของแต่ละวัน — มีกี่ event
 */
data class CalendarDayInfo(
    val day: Int,
    val maintenanceCount: Int = 0,
    val expenseCount: Int = 0
)

class CalendarViewModel(
    private val carId: Int,
    private val maintenanceDao: MaintenanceDao,
    private val expenseDao: ExpenseDao
) : ViewModel() {

    val events: StateFlow<Map<String, CalendarDayInfo>> = combine(
        maintenanceDao.getMaintenancesForCar(carId),
        expenseDao.getExpensesForCar(carId)
    ) { maintenances, expenses ->
        val map = mutableMapOf<String, CalendarDayInfo>()
        val cal = Calendar.getInstance()

        maintenances.forEach { m ->
            cal.timeInMillis = m.date
            val key = "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH)}-${cal.get(Calendar.DAY_OF_MONTH)}"
            val existing = map[key] ?: CalendarDayInfo(cal.get(Calendar.DAY_OF_MONTH))
            map[key] = existing.copy(maintenanceCount = existing.maintenanceCount + 1)
        }

        expenses.forEach { e ->
            cal.timeInMillis = e.date
            val key = "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH)}-${cal.get(Calendar.DAY_OF_MONTH)}"
            val existing = map[key] ?: CalendarDayInfo(cal.get(Calendar.DAY_OF_MONTH))
            map[key] = existing.copy(expenseCount = existing.expenseCount + 1)
        }

        map
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())
}
