package com.example.project_app.data.export

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.project_app.data.local.CarDatabase
import com.example.project_app.data.local.entity.CarEntity
import kotlinx.coroutines.flow.first
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * CsvExporter — สร้างไฟล์ CSV จากข้อมูลรถ Maintenance + Expense
 */
object CsvExporter {

    suspend fun exportCarData(
        context: Context,
        database: CarDatabase,
        car: CarEntity,
        outputStream: OutputStream
    ) {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val maintenances = database.maintenanceDao().getMaintenancesForCar(car.id).first()
        val expenses = database.expenseDao().getExpensesForCar(car.id).first()

        outputStream.bufferedWriter().use { writer ->
            // Header
            writer.appendLine("=== iDrive Export ===")
            writer.appendLine("Car: ${car.year} ${car.brand} ${car.model}")
            writer.appendLine("Mileage: ${car.currentMileage} km")
            writer.appendLine("Export Date: ${dateFormatter.format(Date())}")
            writer.appendLine()

            // Maintenance Section
            writer.appendLine("--- MAINTENANCE RECORDS ---")
            writer.appendLine("Date,Type,Mileage (km),Cost (Baht),Notes")
            maintenances.forEach { m ->
                writer.appendLine(
                    "${dateFormatter.format(Date(m.date))},${m.type},${m.mileage},${m.cost},${m.notes ?: ""}"
                )
            }
            writer.appendLine()

            // Expense Section
            writer.appendLine("--- EXPENSE RECORDS ---")
            writer.appendLine("Date,Type,Amount (Baht),Liters,Mileage at Fill,Notes")
            expenses.forEach { e ->
                writer.appendLine(
                    "${dateFormatter.format(Date(e.date))},${e.type},${e.amount},${e.liters ?: ""},${e.mileageAtFill ?: ""},${e.notes ?: ""}"
                )
            }
        }
    }

    /**
     * สร้าง filename สำหรับ CSV export
     */
    fun generateFileName(car: CarEntity): String {
        val date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        return "iDrive_${car.brand}_${car.model}_$date.csv"
    }
}
