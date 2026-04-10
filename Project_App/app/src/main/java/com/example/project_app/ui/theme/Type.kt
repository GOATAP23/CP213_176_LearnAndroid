package com.example.project_app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ==========================================
// 🖋 Typography: การจัดฟอนต์ให้อ่านง่าย สไตล์ Dashboard
// ==========================================

val Typography = Typography(
    // ใช้งานกับ Headline หลักหน้าจอ (เช่น "ภาพรวมรถของคุณ")
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    // ใช้งานกับ Card Title หรือหัวข้อรอง (เช่น ยี่ห้อรถและรุ่น)
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    // ข้อความเนื้อหาทั่วไป (เช่น ยอดเงิน, เลขไมล์)
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    // ข้อความเล็กๆ (เช่น วันที่, คำอธิบายรอง)
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    // ใช้กับข้อความบนปุ่มกด (เน้นหนานิดนึงให้กดง่าย)
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
)