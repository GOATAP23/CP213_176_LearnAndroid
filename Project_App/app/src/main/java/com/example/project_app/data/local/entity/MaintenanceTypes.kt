package com.example.project_app.data.local.entity

/**
 * Constants สำหรับ Maintenance Type
 * ใช้เป็น key ที่เก็บในฐานข้อมูล — ต้องไม่เปลี่ยนค่า
 * (ค่าเหล่านี้จะถูกแปลเป็นภาษาต่างๆ ด้วย stringResource ที่ UI เท่านั้น)
 */
object MaintenanceTypes {
    const val OIL_CHANGE = "น้ำมันเครื่อง"
    const val BRAKE_PADS = "ผ้าเบรก"
    const val TIRES = "ยาง"
    const val BATTERY = "แบตเตอรี่"
    const val OTHER = "อื่นๆ"
}

/**
 * Constants สำหรับ Expense Type
 */
object ExpenseTypes {
    const val FUEL = "ค่าน้ำมัน"
    const val TOLL = "ค่าทางด่วน"
    const val PARKING = "ค่าที่จอดรถ"
    const val OTHER = "อื่นๆ"
}
