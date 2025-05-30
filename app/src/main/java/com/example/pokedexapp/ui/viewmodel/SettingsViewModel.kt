package com.example.pokedexapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel : ViewModel() {

    private val _currentTheme = MutableStateFlow("Claro")
    val currentTheme: StateFlow<String> = _currentTheme

    fun changeTheme(newTheme: String) {
        _currentTheme.value = newTheme
    }
}