package com.example.project_app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project_app.data.local.entity.CarEntity
import com.example.project_app.data.local.entity.ExpenseEntity
import com.example.project_app.data.local.entity.MaintenanceEntity

@Database(
    entities = [
        CarEntity::class, 
        MaintenanceEntity::class, 
        ExpenseEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CarDatabase : RoomDatabase() {

    abstract fun carDao(): CarDao
    abstract fun maintenanceDao(): MaintenanceDao
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var INSTANCE: CarDatabase? = null

        fun getDatabase(context: Context): CarDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarDatabase::class.java,
                    "car_tracker_database"
                )
                .build()
                
                INSTANCE = instance
                instance
            }
        }
    }
}
