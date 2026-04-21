package com.example.project_app.ui.screens.settings

import android.app.LocaleManager
import android.content.Context
import android.os.LocaleList
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    val oilChangeKmInterval = settingsDataStore.oilChangeKmInterval
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 10000)

    val oilChangeDayInterval = settingsDataStore.oilChangeDayInterval
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 365)

    val isAutoTrackEnabled = settingsDataStore.isAutoTrackEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun toggleDarkMode() {
        viewModelScope.launch {
            settingsDataStore.setDarkMode(!isDarkMode.value)
        }
    }

    fun setLanguage(context: Context, languageTag: String) {
        viewModelScope.launch {
            settingsDataStore.setLanguage(languageTag)
            val tags = if (languageTag.startsWith("th")) "th,en" else "en,th"
            val localeManager = context.getSystemService(LocaleManager::class.java)
            localeManager.applicationLocales = LocaleList.forLanguageTags(tags)
        }
    }

    fun setOilChangeKmInterval(km: Int) {
        viewModelScope.launch { settingsDataStore.setOilChangeKmInterval(km) }
    }

    fun setOilChangeDayInterval(days: Int) {
        viewModelScope.launch { settingsDataStore.setOilChangeDayInterval(days) }
    }

    fun toggleAutoTrack() {
        viewModelScope.launch { settingsDataStore.setAutoTrackEnabled(!isAutoTrackEnabled.value) }
    }
}
