package com.example.project_app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.project_app.data.local.entity.CarEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCar(car: CarEntity)

    @Query("SELECT * FROM cars")
    fun getAllCars(): Flow<List<CarEntity>>
    
    @Query("SELECT * FROM cars WHERE id = :id LIMIT 1")
    fun getCarById(id: Int): Flow<CarEntity?>
}
