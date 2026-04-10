package com.example.project_app.ui.screens.add_car

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_app.data.local.CarDao
import com.example.project_app.data.local.entity.CarEntity
import kotlinx.coroutines.launch

class CarViewModel(private val carDao: CarDao) : ViewModel() {

    var brand by mutableStateOf("")
    var model by mutableStateOf("")
    var year by mutableStateOf("")
    var mileage by mutableStateOf("")
    var imageUri by mutableStateOf<Uri?>(null)

    fun saveCar(onSuccess: () -> Unit) {
        val yearInt = year.toIntOrNull() ?: 0
        val mileageInt = mileage.toIntOrNull() ?: 0

        val newCar = CarEntity(
            brand = brand,
            model = model,
            year = yearInt,
            currentMileage = mileageInt,
            imagePath = imageUri?.toString()
        )

        viewModelScope.launch {
            carDao.insertCar(newCar)
            onSuccess()
        }
    }
}
