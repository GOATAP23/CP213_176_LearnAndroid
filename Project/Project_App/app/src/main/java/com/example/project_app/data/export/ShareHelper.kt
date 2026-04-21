package com.example.project_app.data.export

import android.content.Context
import android.content.Intent
import com.example.project_app.R
import com.example.project_app.data.local.CarDatabase
import com.example.project_app.data.local.entity.CarEntity
import kotlinx.coroutines.flow.first
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * ShareHelper — สร้าง Text Summary เพื่อแชร์ข้อมูลรถผ่าน LINE, Messenger, Email ฯลฯ
 */
object ShareHelper {

    suspend fun generateShareText(
        context: Context,
        database: CarDatabase,
        car: CarEntity
    ): String {
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale("th", "TH"))
        val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("th", "TH"))

        val maintenances = database.maintenanceDao().getMaintenancesForCar(car.id).first()
        val expenses = database.expenseDao().getExpensesForCar(car.id).first()

        val totalMaintCost = maintenances.sumOf { it.cost }
        val totalExpenseCost = expenses.sumOf { it.amount }

        return buildString {
            appendLine("🚗 iDrive — ข้อมูลรถยนต์")
            appendLine("═══════════════════")
            appendLine("📋 ${car.year} ${car.brand} ${car.model}")
            appendLine("📏 เลขไมล์: ${NumberFormat.getNumberInstance().format(car.currentMileage)} กม.")
            appendLine()

            if (maintenances.isNotEmpty()) {
                appendLine("🔧 ประวัติการดูแลรักษา (${maintenances.size} รายการ)")
                appendLine("─────────────────")
                maintenances.take(5).forEach { m ->
                    appendLine("• ${m.type} — ${dateFormatter.format(Date(m.date))} — ${currencyFormatter.format(m.cost)}")
                }
                if (maintenances.size > 5) appendLine("  ... และอีก ${maintenances.size - 5} รายการ")
                appendLine("💰 รวม: ${currencyFormatter.format(totalMaintCost)}")
                appendLine()
            }

            if (expenses.isNotEmpty()) {
                appendLine("💸 ค่าใช้จ่าย (${expenses.size} รายการ)")
                appendLine("─────────────────")
                expenses.take(5).forEach { e ->
                    appendLine("• ${e.type} — ${dateFormatter.format(Date(e.date))} — ${currencyFormatter.format(e.amount)}")
                }
                if (expenses.size > 5) appendLine("  ... และอีก ${expenses.size - 5} รายการ")
                appendLine("💰 รวม: ${currencyFormatter.format(totalExpenseCost)}")
                appendLine()
            }

            appendLine("═══════════════════")
            appendLine("📱 ส่งจากแอป iDrive")
        }
    }

    fun shareText(context: Context, text: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, context.getString(R.string.share_car_data))
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(shareIntent)
    }
}
