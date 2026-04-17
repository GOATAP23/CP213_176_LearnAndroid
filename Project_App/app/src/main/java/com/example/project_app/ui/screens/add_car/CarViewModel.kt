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

    var currentCarId: Int? = null

    var brand by mutableStateOf("")
    var model by mutableStateOf("")
    var year by mutableStateOf("")
    var mileage by mutableStateOf("")
    var imageUri by mutableStateOf<Uri?>(null)
    var imageUrl by mutableStateOf("")

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

    fun loadCar(id: Int) {
        if (id == -1 || currentCarId == id) return
        viewModelScope.launch {
            val car = carDao.getCarByIdSync(id)
            if (car != null) {
                currentCarId = car.id
                brand = car.brand
                model = car.model
                year = car.year.toString()
                mileage = car.currentMileage.toString()
                imageUrl = car.imagePath ?: ""
            }
        }
    }

    fun saveCar(onSuccess: () -> Unit) {
        if (!validate()) return

        val yearInt = year.toIntOrNull() ?: 0
        val mileageInt = mileage.toIntOrNull() ?: 0

        val imagePathToSave = if (imageUrl.isNotBlank()) imageUrl else imageUri?.toString()

        val newCar = CarEntity(
            id = currentCarId ?: 0,
            brand = brand,
            model = model,
            year = yearInt,
            currentMileage = mileageInt,
            imagePath = imagePathToSave
        )

        viewModelScope.launch {
            if (currentCarId == null) {
                carDao.insertCar(newCar)
            } else {
                carDao.updateCar(newCar)
            }
            onSuccess()
        }
    }
}
