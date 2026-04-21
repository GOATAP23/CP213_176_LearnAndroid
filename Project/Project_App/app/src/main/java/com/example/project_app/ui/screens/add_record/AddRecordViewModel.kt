package com.example.project_app.ui.screens.add_record

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_app.data.local.CarDao
import com.example.project_app.data.local.ExpenseDao
import com.example.project_app.data.local.MaintenanceDao
import com.example.project_app.data.local.entity.ExpenseEntity
import com.example.project_app.data.local.entity.MaintenanceEntity
import com.example.project_app.data.local.entity.MaintenanceTypes
import com.example.project_app.data.local.entity.ExpenseTypes
import kotlinx.coroutines.launch

enum class RecordType { MAINTENANCE, EXPENSE }

class AddRecordViewModel(
    private val carId: Int,
    private val carDao: CarDao,
    private val maintenanceDao: MaintenanceDao,
    private val expenseDao: ExpenseDao
) : ViewModel() {

    var selectedTab by mutableStateOf(RecordType.MAINTENANCE)

    var selectedDateMillis by mutableStateOf(System.currentTimeMillis())
    var notes by mutableStateOf("")

    // --- Maintenance fields ---
    val maintenanceOptions = listOf(
        MaintenanceTypes.OIL_CHANGE,
        MaintenanceTypes.BRAKE_PADS,
        MaintenanceTypes.TIRES,
        MaintenanceTypes.BATTERY,
        MaintenanceTypes.OTHER
    )
    var maintType by mutableStateOf(maintenanceOptions[0])
    var maintMileage by mutableStateOf("")
    var maintCost by mutableStateOf("")

    // --- Expense fields ---
    val expenseOptions = listOf(
        ExpenseTypes.FUEL,
        ExpenseTypes.TOLL,
        ExpenseTypes.PARKING,
        ExpenseTypes.OTHER
    )
    var expType by mutableStateOf(expenseOptions[0])
    var expAmount by mutableStateOf("")

    // --- Fuel Economy fields ---
    var fuelLiters by mutableStateOf("")
    var fuelMileage by mutableStateOf("")

    // --- Photo attachment ---
    var imageUri by mutableStateOf<Uri?>(null)

    // --- Edit mode ---
    var isEditMode by mutableStateOf(false)
    private var editRecordId: Int = 0

    // Undo
    private var lastInsertedId: Long? = null
    private var lastInsertedType: RecordType? = null

    /**
     * โหลดข้อมูลที่มีอยู่เพื่อ Edit
     */
    fun loadExistingRecord(recordId: Int, recordType: RecordType) {
        isEditMode = true
        editRecordId = recordId
        selectedTab = recordType

        viewModelScope.launch {
            if (recordType == RecordType.MAINTENANCE) {
                val entity = maintenanceDao.getMaintenanceById(recordId.toLong())
                entity?.let {
                    maintType = it.type
                    selectedDateMillis = it.date
                    maintMileage = it.mileage.toString()
                    maintCost = it.cost.toString()
                    notes = it.notes ?: ""
                    if (!it.imagePath.isNullOrBlank()) imageUri = Uri.parse(it.imagePath)
                }
            } else {
                val entity = expenseDao.getExpenseById(recordId.toLong())
                entity?.let {
                    expType = it.type
                    selectedDateMillis = it.date
                    expAmount = it.amount.toString()
                    notes = it.notes ?: ""
                    fuelLiters = it.liters?.toString() ?: ""
                    fuelMileage = it.mileageAtFill?.toString() ?: ""
                    if (!it.imagePath.isNullOrBlank()) imageUri = Uri.parse(it.imagePath)
                }
            }
        }
    }

    fun saveRecord(onSuccess: () -> Unit) {
        viewModelScope.launch {
            if (selectedTab == RecordType.MAINTENANCE) {
                val newMileage = maintMileage.toIntOrNull() ?: 0
                val entity = MaintenanceEntity(
                    id = if (isEditMode) editRecordId else 0,
                    carId = carId,
                    type = maintType,
                    date = selectedDateMillis,
                    mileage = newMileage,
                    cost = maintCost.toDoubleOrNull() ?: 0.0,
                    notes = notes.ifBlank { null },
                    imagePath = imageUri?.toString()
                )

                if (isEditMode) {
                    maintenanceDao.updateMaintenance(entity)
                } else {
                    lastInsertedId = maintenanceDao.insertMaintenance(entity)
                    lastInsertedType = RecordType.MAINTENANCE
                }

                // อัปเดตเลขไมล์ของรถถ้าไมล์ใหม่มากกว่าเดิม
                val car = carDao.getCarByIdSync(carId)
                if (car != null && newMileage > car.currentMileage) {
                    carDao.updateCar(car.copy(currentMileage = newMileage))
                }
            } else {
                val entity = ExpenseEntity(
                    id = if (isEditMode) editRecordId else 0,
                    carId = carId,
                    type = expType,
                    date = selectedDateMillis,
                    amount = expAmount.toDoubleOrNull() ?: 0.0,
                    notes = notes.ifBlank { null },
                    liters = fuelLiters.toDoubleOrNull(),
                    mileageAtFill = fuelMileage.toIntOrNull(),
                    imagePath = imageUri?.toString()
                )

                if (isEditMode) {
                    expenseDao.updateExpense(entity)
                } else {
                    lastInsertedId = expenseDao.insertExpense(entity)
                    lastInsertedType = RecordType.EXPENSE
                }

                // อัปเดตไมล์จากค่าน้ำมัน
                val fillMileage = fuelMileage.toIntOrNull()
                if (fillMileage != null) {
                    val car = carDao.getCarByIdSync(carId)
                    if (car != null && fillMileage > car.currentMileage) {
                        carDao.updateCar(car.copy(currentMileage = fillMileage))
                    }
                }
            }
            onSuccess()
        }
    }

    /**
     * Undo — ลบรายการล่าสุดที่เพิ่งบันทึกไป
     */
    fun undoLastRecord(onComplete: () -> Unit) {
        val id = lastInsertedId ?: return
        val type = lastInsertedType ?: return

        viewModelScope.launch {
            when (type) {
                RecordType.MAINTENANCE -> {
                    val entity = maintenanceDao.getMaintenanceById(id)
                    entity?.let { maintenanceDao.deleteMaintenance(it) }
                }
                RecordType.EXPENSE -> {
                    val entity = expenseDao.getExpenseById(id)
                    entity?.let { expenseDao.deleteExpense(it) }
                }
            }
            lastInsertedId = null
            lastInsertedType = null
            onComplete()
        }
    }
}
