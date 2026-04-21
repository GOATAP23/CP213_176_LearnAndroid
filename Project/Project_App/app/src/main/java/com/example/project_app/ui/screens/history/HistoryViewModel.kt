package com.example.project_app.ui.screens.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_app.data.local.ExpenseDao
import com.example.project_app.data.local.MaintenanceDao
import com.example.project_app.data.local.entity.ExpenseEntity
import com.example.project_app.data.local.entity.MaintenanceEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

/**
 * Unified record สำหรับแสดงใน History (รวม Maintenance + Expense)
 */
data class HistoryRecord(
    val id: Int,
    val type: String,
    val date: Long,
    val amount: Double,
    val mileage: Int? = null,
    val notes: String?,
    val isMaintenance: Boolean,
    val imagePath: String? = null
)

/**
 * ข้อมูลสรุปค่าใช้จ่ายรายเดือน (สำหรับ Chart)
 */
data class MonthlyChartData(
    val monthLabel: String,
    val maintenanceTotal: Double,
    val expenseTotal: Double
)

class HistoryViewModel(
    private val carId: Int,
    private val maintenanceDao: MaintenanceDao,
    private val expenseDao: ExpenseDao
) : ViewModel() {

    var searchQuery by mutableStateOf("")
    var selectedFilter by mutableStateOf<String?>(null)

    private val _allRecords = combine(
        maintenanceDao.getMaintenancesForCar(carId),
        expenseDao.getExpensesForCar(carId)
    ) { maintenances, expenses ->
        val mRecords = maintenances.map {
            HistoryRecord(it.id, it.type, it.date, it.cost, it.mileage, it.notes, true, it.imagePath)
        }
        val eRecords = expenses.map {
            HistoryRecord(it.id, it.type, it.date, it.amount, null, it.notes, false, it.imagePath)
        }
        (mRecords + eRecords).sortedByDescending { it.date }
    }

    val records: StateFlow<List<HistoryRecord>> = _allRecords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /**
     * กรองรายการตาม search + filter
     */
    fun getFilteredRecords(allRecords: List<HistoryRecord>): List<HistoryRecord> {
        return allRecords.filter { record ->
            val matchesFilter = selectedFilter == null || record.type == selectedFilter
            val matchesSearch = searchQuery.isBlank() ||
                record.type.contains(searchQuery, ignoreCase = true) ||
                (record.notes?.contains(searchQuery, ignoreCase = true) == true)
            matchesFilter && matchesSearch
        }
    }

    /**
     * สร้างข้อมูล Chart — 6 เดือนย้อนหลัง
     */
    fun getMonthlyChartData(allRecords: List<HistoryRecord>): List<MonthlyChartData> {
        val calendar = Calendar.getInstance()
        val months = mutableListOf<MonthlyChartData>()
        val monthNames = arrayOf("ม.ค.", "ก.พ.", "มี.ค.", "เม.ย.", "พ.ค.", "มิ.ย.", "ก.ค.", "ส.ค.", "ก.ย.", "ต.ค.", "พ.ย.", "ธ.ค.")

        for (i in 5 downTo 0) {
            val cal = Calendar.getInstance()
            cal.add(Calendar.MONTH, -i)
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)

            val maintTotal = allRecords.filter { r ->
                r.isMaintenance && run {
                    calendar.timeInMillis = r.date
                    calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) == month
                }
            }.sumOf { it.amount }

            val expTotal = allRecords.filter { r ->
                !r.isMaintenance && run {
                    calendar.timeInMillis = r.date
                    calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) == month
                }
            }.sumOf { it.amount }

            months.add(MonthlyChartData(monthNames[month], maintTotal, expTotal))
        }
        return months
    }

    fun deleteRecord(record: HistoryRecord) {
        viewModelScope.launch {
            if (record.isMaintenance) {
                maintenanceDao.deleteMaintenance(
                    MaintenanceEntity(id = record.id, carId = carId, type = record.type,
                        date = record.date, mileage = record.mileage ?: 0,
                        cost = record.amount, notes = record.notes, imagePath = record.imagePath)
                )
            } else {
                expenseDao.deleteExpense(
                    ExpenseEntity(id = record.id, carId = carId, type = record.type,
                        date = record.date, amount = record.amount,
                        notes = record.notes, imagePath = record.imagePath)
                )
            }
        }
    }
}
