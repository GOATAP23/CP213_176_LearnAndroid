package com.example.project_app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_app.data.local.CarDao
import com.example.project_app.data.local.ExpenseDao
import com.example.project_app.data.local.MaintenanceDao
import com.example.project_app.data.local.entity.CarEntity
import com.example.project_app.data.local.entity.MaintenanceEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

data class TransactionUI(
    val title: String,
    val dateMillis: Long,
    val amount: Double,
    val isMaintenance: Boolean
)

/**
 * AlertLevel — ระดับการแจ้งเตือนเชิงพยากรณ์
 * GOOD = สีเขียว, WARNING = สีเหลือง, DANGER = สีแดง
 */
enum class AlertLevel { GOOD, WARNING, DANGER }

data class PredictiveAlert(
    val level: AlertLevel = AlertLevel.GOOD,
    val mileageSinceLastOilChange: Int = 0
)

data class DashboardState(
    val monthlyTotal: Double = 0.0,
    val yearlyTotal: Double = 0.0,
    val recentTransactions: List<TransactionUI> = emptyList(),
    val predictiveAlert: PredictiveAlert = PredictiveAlert()
)

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModel(
    private val carDao: CarDao,
    private val maintenanceDao: MaintenanceDao,
    private val expenseDao: ExpenseDao
) : ViewModel() {

    val allCars = carDao.getAllCars()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedCarId = MutableStateFlow<Int?>(null)

    private val _isGarageView = MutableStateFlow(true)
    val isGarageView: StateFlow<Boolean> = _isGarageView

    val currentCar = combine(allCars, _selectedCarId) { cars, selectedId ->
        if (selectedId != null) {
            cars.find { it.id == selectedId } ?: cars.firstOrNull()
        } else {
            cars.firstOrNull()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Reactive: เมื่อรถเปลี่ยน ข้อมูลทุกอย่างจะอัพเดตตาม
    val dashboardState: StateFlow<DashboardState> = currentCar.flatMapLatest { car ->
        if (car == null) {
            flowOf(DashboardState())
        } else {
            combine(
                maintenanceDao.getMaintenancesForCar(car.id),
                expenseDao.getExpensesForCar(car.id),
                maintenanceDao.getLatestMaintenanceByType(car.id, "น้ำมันเครื่อง")
            ) { maintenances, expenses, latestOilChange ->

                // รวม transactions จากทั้ง Maintenance + Expense
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

                // Predictive Alert: วิเคราะห์ระยะที่วิ่งตั้งแต่เปลี่ยนน้ำมันเครื่องล่าสุด
                val alert = calculatePredictiveAlert(car.currentMileage, latestOilChange)

                DashboardState(monthSum, yearSum, recentFive, alert)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DashboardState())

    fun selectCar(carId: Int) {
        _selectedCarId.value = carId
        _isGarageView.value = false
    }

    fun openGarage() {
        _isGarageView.value = true
    }

    /**
     * คำนวณ Predictive Alert จากเลขไมล์ปัจจุบัน vs ไมล์ตอนเปลี่ยนน้ำมันเครื่องล่าสุด
     */
    private fun calculatePredictiveAlert(
        currentMileage: Int,
        latestOilChange: MaintenanceEntity?
    ): PredictiveAlert {
        val mileageSinceChange = if (latestOilChange != null) {
            currentMileage - latestOilChange.mileage
        } else {
            // ถ้าไม่เคยบันทึกเปลี่ยนน้ำมัน ใช้ modulus ของเลขไมล์เป็น fallback
            currentMileage % 10000
        }

        val level = when {
            mileageSinceChange > 10000 -> AlertLevel.DANGER
            mileageSinceChange > 8000 -> AlertLevel.WARNING
            else -> AlertLevel.GOOD
        }

        return PredictiveAlert(level, mileageSinceChange)
    }

    /**
     * ลบรถปัจจุบันออกจากฐานข้อมูล
     */
    fun deleteCurrentCar(carId: Int) {
        viewModelScope.launch {
            val car = currentCar.value
            if (car != null && car.id == carId) {
                carDao.deleteCar(car)
                _selectedCarId.value = null // reset selection after delete
                _isGarageView.value = true
            }
        }
    }
}
