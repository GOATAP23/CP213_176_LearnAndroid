package com.example.project_app.ui.screens.settings

import android.app.LocaleManager
import android.content.Context
import android.os.LocaleList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_app.data.local.SettingsDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    val isDarkMode = settingsDataStore.isDarkMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val currentLanguage = settingsDataStore.currentLanguage
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "th")

    fun toggleDarkMode() {
        viewModelScope.launch {
            settingsDataStore.setDarkMode(!isDarkMode.value)
        }
    }

    fun setLanguage(context: Context, languageTag: String) {
        viewModelScope.launch {
            settingsDataStore.setLanguage(languageTag)
            // แก้บัคคีย์บอร์ด (เช่น Samsung Keyboard) ที่จำกัดภาษาพิมพ์ตาม Locale ของแอป
            // โดยการส่งภาษาที่ 2 พ่วงไปด้วย เพื่อให้คีย์บอร์ดอนุญาตให้สลับภาษาได้ 
            val tags = if (languageTag.startsWith("th")) "th,en" else "en,th"
            
            val localeManager = context.getSystemService(LocaleManager::class.java)
            localeManager.applicationLocales = LocaleList.forLanguageTags(tags)
        }
    }
}
