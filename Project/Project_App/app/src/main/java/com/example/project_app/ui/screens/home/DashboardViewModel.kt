package com.example.project_app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_app.data.local.CarDao
import com.example.project_app.data.local.ExpenseDao
import com.example.project_app.data.local.MaintenanceDao
import com.example.project_app.data.local.SettingsDataStore
import com.example.project_app.data.local.entity.CarEntity
import com.example.project_app.data.local.entity.ExpenseTypes
import com.example.project_app.data.local.entity.MaintenanceEntity
import com.example.project_app.data.local.entity.MaintenanceTypes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
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
    val mileageSinceLastOilChange: Int = 0,
    val daysSinceLastOilChange: Int = 0
)

data class FuelEconomyInfo(
    val averageKmPerLiter: Double? = null,
    val lastKmPerLiter: Double? = null,
    val totalFuelCost: Double = 0.0
)

data class DashboardState(
    val monthlyTotal: Double = 0.0,
    val yearlyTotal: Double = 0.0,
    val recentTransactions: List<TransactionUI> = emptyList(),
    val predictiveAlert: PredictiveAlert = PredictiveAlert(),
    val fuelEconomy: FuelEconomyInfo = FuelEconomyInfo()
)

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModel(
    private val carDao: CarDao,
    private val maintenanceDao: MaintenanceDao,
    private val expenseDao: ExpenseDao,
    private val settingsDataStore: SettingsDataStore
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
                maintenanceDao.getLatestMaintenanceByType(car.id, MaintenanceTypes.OIL_CHANGE),
                settingsDataStore.oilChangeKmInterval,
                settingsDataStore.oilChangeDayInterval
            ) { maintenances, expenses, latestOilChange, kmInterval, dayInterval ->

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
                val alert = calculatePredictiveAlert(car.currentMileage, latestOilChange, kmInterval, dayInterval)

                // Fuel Economy: คำนวณอัตราสิ้นเปลือง
                val fuelEconomy = calculateFuelEconomy(expenses)

                DashboardState(monthSum, yearSum, recentFive, alert, fuelEconomy)
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
     * ใช้ค่า interval จาก Settings
     */
    private fun calculatePredictiveAlert(
        currentMileage: Int,
        latestOilChange: MaintenanceEntity?,
        kmInterval: Int,
        dayInterval: Int
    ): PredictiveAlert {
        if (latestOilChange == null) {
            return PredictiveAlert(AlertLevel.WARNING, currentMileage % kmInterval, 0)
        }

        val mileageSinceChange = (currentMileage - latestOilChange.mileage).coerceAtLeast(0)
        val daysSinceChange = ((System.currentTimeMillis() - latestOilChange.date)
            / (1000L * 60 * 60 * 24)).toInt().coerceAtLeast(0)

        val warningKm = (kmInterval * 0.8).toInt()
        val warningDay = (dayInterval * 0.5).toInt()

        val level = when {
            mileageSinceChange > kmInterval || daysSinceChange > dayInterval -> AlertLevel.DANGER
            mileageSinceChange > warningKm || daysSinceChange > warningDay -> AlertLevel.WARNING
            else -> AlertLevel.GOOD
        }

        return PredictiveAlert(level, mileageSinceChange, daysSinceChange)
    }

    /**
     * คำนวณอัตราสิ้นเปลืองจากรายการเติมน้ำมัน
     */
    private fun calculateFuelEconomy(
        expenses: List<com.example.project_app.data.local.entity.ExpenseEntity>
    ): FuelEconomyInfo {
        val fuelRecords = expenses
            .filter { it.type == ExpenseTypes.FUEL && it.liters != null && it.liters > 0 && it.mileageAtFill != null }
            .sortedBy { it.mileageAtFill }

        if (fuelRecords.size < 2) {
            return FuelEconomyInfo(totalFuelCost = expenses.filter { it.type == ExpenseTypes.FUEL }.sumOf { it.amount })
        }

        val kmpls = mutableListOf<Double>()
        for (i in 1 until fuelRecords.size) {
            val prev = fuelRecords[i - 1]
            val curr = fuelRecords[i]
            val distKm = (curr.mileageAtFill!! - prev.mileageAtFill!!).toDouble()
            val liters = curr.liters!!
            if (distKm > 0 && liters > 0) {
                kmpls.add(distKm / liters)
            }
        }

        val avgKmpl = if (kmpls.isNotEmpty()) kmpls.average() else null
        val lastKmpl = kmpls.lastOrNull()
        val totalCost = expenses.filter { it.type == ExpenseTypes.FUEL }.sumOf { it.amount }

        return FuelEconomyInfo(avgKmpl, lastKmpl, totalCost)
    }

    /**
     * ลบรถปัจจุบันออกจากฐานข้อมูล
     */
    fun deleteCurrentCar(carId: Int) {
        viewModelScope.launch {
            val car = currentCar.value
            if (car != null && car.id == carId) {
                carDao.deleteCar(car)
                _selectedCarId.value = null
                _isGarageView.value = true
            }
        }
    }
}
