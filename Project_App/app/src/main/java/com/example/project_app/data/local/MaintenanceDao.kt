package com.example.project_app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.project_app.data.local.entity.MaintenanceEntity
import com.example.project_app.data.local.entity.MonthlySummary
import com.example.project_app.data.local.entity.YearlySummary
import kotlinx.coroutines.flow.Flow

@Dao
interface MaintenanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaintenance(maintenance: MaintenanceEntity): Long

    @Update
    suspend fun updateMaintenance(maintenance: MaintenanceEntity)

    @Delete
    suspend fun deleteMaintenance(maintenance: MaintenanceEntity)

    @Query("SELECT * FROM maintenances WHERE carId = :carId ORDER BY date DESC")
    fun getMaintenancesForCar(carId: Int): Flow<List<MaintenanceEntity>>

    @Query("SELECT * FROM maintenances WHERE carId = :carId ORDER BY date DESC LIMIT 1")
    fun getLatestMaintenanceForCar(carId: Int): Flow<MaintenanceEntity?>

    @Query("SELECT * FROM maintenances WHERE carId = :carId AND type = :type ORDER BY date DESC LIMIT 1")
    fun getLatestMaintenanceByType(carId: Int, type: String): Flow<MaintenanceEntity?>

    @Query("SELECT * FROM maintenances WHERE id = :id LIMIT 1")
    suspend fun getMaintenanceById(id: Long): MaintenanceEntity?

    @Query("""
        SELECT strftime('%Y-%m', date / 1000, 'unixepoch', 'localtime') AS period, 
               SUM(cost) AS totalAmount 
        FROM maintenances 
        WHERE carId = :carId 
        GROUP BY period 
        ORDER BY period DESC
    """)
    fun getMonthlyMaintenanceSummary(carId: Int): Flow<List<MonthlySummary>>

    @Query("""
        SELECT strftime('%Y', date / 1000, 'unixepoch', 'localtime') AS period, 
               SUM(cost) AS totalAmount 
        FROM maintenances 
        WHERE carId = :carId 
        GROUP BY period 
        ORDER BY period DESC
    """)
    fun getYearlyMaintenanceSummary(carId: Int): Flow<List<YearlySummary>>
}
