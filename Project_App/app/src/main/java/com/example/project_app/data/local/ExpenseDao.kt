package com.example.project_app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.project_app.data.local.entity.ExpenseEntity
import com.example.project_app.data.local.entity.MonthlySummary
import com.example.project_app.data.local.entity.YearlySummary
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses WHERE carId = :carId ORDER BY date DESC")
    fun getExpensesForCar(carId: Int): Flow<List<ExpenseEntity>>

    @Query("""
        SELECT strftime('%Y-%m', date / 1000, 'unixepoch', 'localtime') AS period, 
               SUM(amount) AS totalAmount 
        FROM expenses 
        WHERE carId = :carId 
        GROUP BY period 
        ORDER BY period DESC
    """)
    fun getMonthlyExpenseSummary(carId: Int): Flow<List<MonthlySummary>>

    @Query("""
        SELECT strftime('%Y', date / 1000, 'unixepoch', 'localtime') AS period, 
               SUM(amount) AS totalAmount 
        FROM expenses 
        WHERE carId = :carId 
        GROUP BY period 
        ORDER BY period DESC
    """)
    fun getYearlyExpenseSummary(carId: Int): Flow<List<YearlySummary>>
}
