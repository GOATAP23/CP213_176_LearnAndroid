package com.example.project_app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.project_app.data.local.entity.MaintenanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MaintenanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaintenance(maintenance: MaintenanceEntity)

    @Query("SELECT * FROM maintenances WHERE carId = :carId ORDER BY date DESC")
    fun getMaintenancesForCar(carId: Int): Flow<List<MaintenanceEntity>>
}
