package com.example.project_app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = RacingRed,
    onPrimary = PearlWhite,
    secondary = MetallicSilver,
    onSecondary = CarbonNavy,
    tertiary = EngineAmber,
    background = MatteCarbon,
    surface = SurfaceDark,
    error = AlertRed
)

private val LightColorScheme = lightColorScheme(
    primary = RacingRed,
    onPrimary = PearlWhite,
    secondary = CarbonNavy,
    onSecondary = PearlWhite,
    tertiary = EngineAmber,
    background = PearlWhite,
    surface = SurfaceLight,
    error = AlertRed
)

@Composable
fun Project_AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+ (ปรับสีตามเครื่องของผู้ใช้) เราปิดไว้เพื่อให้ใช้ธีมสีรถของเราเอง
    dynamicColor: Boolean = false,
    // Override จาก DataStore — ถ้า null จะ fallback ใช้ค่า darkTheme (จากระบบ)
    overrideDarkMode: Boolean? = null,
    content: @Composable () -> Unit
) {
    val useDarkTheme = overrideDarkMode ?: darkTheme

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (useDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        useDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    // ตั้งค่าพฤติกรรมของแถบแจ้งเตือนด้านบนเครื่อง (Status Bar)
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !useDarkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}