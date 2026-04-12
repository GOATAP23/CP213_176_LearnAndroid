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

    // Validation error states
    var brandError by mutableStateOf(false)
    var modelError by mutableStateOf(false)
    var yearError by mutableStateOf(false)
    var mileageError by mutableStateOf(false)

    /**
     * Validate ทุกฟิลด์ก่อน Save
     * @return true ถ้าข้อมูลครบถ้วน
     */
    private fun validate(): Boolean {
        brandError = brand.isBlank()
        modelError = model.isBlank()
        yearError = year.isBlank() || year.toIntOrNull() == null
        mileageError = mileage.isBlank() || mileage.toIntOrNull() == null

        return !brandError && !modelError && !yearError && !mileageError
    }

    fun saveCar(onSuccess: () -> Unit) {
        if (!validate()) return

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
