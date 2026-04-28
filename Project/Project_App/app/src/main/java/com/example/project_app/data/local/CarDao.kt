package com.example.project_app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.project_app.data.local.entity.CarEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCar(car: CarEntity): Long

    @Update
    suspend fun updateCar(car: CarEntity)

    @Delete
    suspend fun deleteCar(car: CarEntity)

    @Query("SELECT * FROM cars")
    fun getAllCars(): Flow<List<CarEntity>>

    @Query("SELECT * FROM cars WHERE id = :id LIMIT 1")
    fun getCarById(id: Int): Flow<CarEntity?>

    @Query("SELECT * FROM cars WHERE id = :id LIMIT 1")
    suspend fun getCarByIdSync(id: Int): CarEntity?

    @Query("SELECT * FROM cars ORDER BY id DESC LIMIT 1")
    fun getLatestCar(): Flow<CarEntity?>

    @Query("SELECT * FROM cars WHERE bluetoothMacAddress = :macAddress LIMIT 1")
    suspend fun getCarByBluetoothMac(macAddress: String): CarEntity?
}
