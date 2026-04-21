package com.example.project_app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// สร้าง DataStore instance แบบ Singleton ผ่าน Context Extension
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")

/**
 * SettingsDataStore — จัดการค่าตั้งค่าของแอป
 * - isDarkMode: สลับธีมมืด/สว่าง
 * - currentLanguage: ภาษาปัจจุบัน ("th" หรือ "en")
 * - oilChangeKmInterval: รอบเปลี่ยนน้ำมัน (กม.)
 * - oilChangeDayInterval: รอบเปลี่ยนน้ำมัน (วัน)
 * - isAutoTrackEnabled: เปิด/ปิด Auto-track ระยะทาง
 */
class SettingsDataStore(private val context: Context) {

    companion object {
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        val CURRENT_LANGUAGE = stringPreferencesKey("current_language")
        val OIL_CHANGE_KM_INTERVAL = intPreferencesKey("oil_change_km_interval")
        val OIL_CHANGE_DAY_INTERVAL = intPreferencesKey("oil_change_day_interval")
        val IS_AUTO_TRACK_ENABLED = booleanPreferencesKey("is_auto_track_enabled")
    }

    // ดึงค่า Dark Mode (ค่าเริ่มต้น: false = Light Mode)
    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_DARK_MODE] ?: false
    }

    // ดึงค่าภาษา (ค่าเริ่มต้น: "th" = ภาษาไทย)
    val currentLanguage: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[CURRENT_LANGUAGE] ?: "th"
    }

    // รอบเปลี่ยนน้ำมันเครื่อง (กม.) — ค่าเริ่มต้น 10,000 km
    val oilChangeKmInterval: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[OIL_CHANGE_KM_INTERVAL] ?: 10000
    }

    // รอบเปลี่ยนน้ำมันเครื่อง (วัน) — ค่าเริ่มต้น 365 วัน
    val oilChangeDayInterval: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[OIL_CHANGE_DAY_INTERVAL] ?: 365
    }

    // Auto-track ระยะทาง
    val isAutoTrackEnabled: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_AUTO_TRACK_ENABLED] ?: false
    }

    // บันทึกค่าโหมดมืด
    suspend fun setDarkMode(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = isDark
        }
    }

    // บันทึกค่าภาษา
    suspend fun setLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[CURRENT_LANGUAGE] = language
        }
    }

    suspend fun setOilChangeKmInterval(km: Int) {
        context.dataStore.edit { it[OIL_CHANGE_KM_INTERVAL] = km }
    }

    suspend fun setOilChangeDayInterval(days: Int) {
        context.dataStore.edit { it[OIL_CHANGE_DAY_INTERVAL] = days }
    }

    suspend fun setAutoTrackEnabled(enabled: Boolean) {
        context.dataStore.edit { it[IS_AUTO_TRACK_ENABLED] = enabled }
    }
}
