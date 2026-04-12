package com.example.project_app.ui.screens.add_record

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_app.data.local.ExpenseDao
import com.example.project_app.data.local.MaintenanceDao
import com.example.project_app.data.local.entity.ExpenseEntity
import com.example.project_app.data.local.entity.MaintenanceEntity
import kotlinx.coroutines.launch

enum class RecordType { MAINTENANCE, EXPENSE }

class AddRecordViewModel(
    private val carId: Int,
    private val maintenanceDao: MaintenanceDao,
    private val expenseDao: ExpenseDao
) : ViewModel() {

    var selectedTab by mutableStateOf(RecordType.MAINTENANCE)

    var selectedDateMillis by mutableStateOf(System.currentTimeMillis())
    var notes by mutableStateOf("")

    val maintenanceOptions = listOf("น้ำมันเครื่อง", "ผ้าเบรก", "ยาง", "แบตเตอรี่", "อื่นๆ")
    var maintType by mutableStateOf(maintenanceOptions[0])
    var maintMileage by mutableStateOf("")
    var maintCost by mutableStateOf("")

    val expenseOptions = listOf("ค่าน้ำมัน", "ค่าทางด่วน", "ค่าที่จอดรถ", "อื่นๆ")
    var expType by mutableStateOf(expenseOptions[0])
    var expAmount by mutableStateOf("")

    // เก็บ ID ของรายการล่าสุดที่ถูก Insert เพื่อใช้ใน Undo
    private var lastInsertedId: Long? = null
    private var lastInsertedType: RecordType? = null

    fun saveRecord(onSuccess: () -> Unit) {
        viewModelScope.launch {
            if (selectedTab == RecordType.MAINTENANCE) {
                val entity = MaintenanceEntity(
                    carId = carId,
                    type = maintType,
                    date = selectedDateMillis,
                    mileage = maintMileage.toIntOrNull() ?: 0,
                    cost = maintCost.toDoubleOrNull() ?: 0.0,
                    notes = notes.ifBlank { null }
                )
                lastInsertedId = maintenanceDao.insertMaintenance(entity)
                lastInsertedType = RecordType.MAINTENANCE
            } else {
                val entity = ExpenseEntity(
                    carId = carId,
                    type = expType,
                    date = selectedDateMillis,
                    amount = expAmount.toDoubleOrNull() ?: 0.0,
                    notes = notes.ifBlank { null }
                )
                lastInsertedId = expenseDao.insertExpense(entity)
                lastInsertedType = RecordType.EXPENSE
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
