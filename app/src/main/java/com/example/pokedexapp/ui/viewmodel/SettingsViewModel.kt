package com.example.pokedexapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class SettingsViewModel : ViewModel() {
    private val _currentTheme = MutableStateFlow("light")
    val currentTheme: StateFlow<String> = _currentTheme

    private val _currentLanguage = MutableStateFlow("en")
    val currentLanguage: StateFlow<String> = _currentLanguage

    fun changeTheme(theme: String) {
        _currentTheme.value = theme
    }

    fun changeLanguage(language: String) {
        _currentLanguage.value = language
    }
}