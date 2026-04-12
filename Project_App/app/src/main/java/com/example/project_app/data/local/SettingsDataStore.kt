package com.example.project_app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
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
 */
class SettingsDataStore(private val context: Context) {

    companion object {
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        val CURRENT_LANGUAGE = stringPreferencesKey("current_language")
    }

    // ดึงค่า Dark Mode (ค่าเริ่มต้น: false = Light Mode)
    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_DARK_MODE] ?: false
    }

    // ดึงค่าภาษา (ค่าเริ่มต้น: "th" = ภาษาไทย)
    val currentLanguage: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[CURRENT_LANGUAGE] ?: "th"
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
}
