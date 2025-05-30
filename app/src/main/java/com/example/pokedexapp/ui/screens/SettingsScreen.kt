package com.example.pokedexapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.ui.components.ThemeSelector
import com.example.pokedexapp.ui.components.LanguageSelector

@Composable
fun SettingsScreen(
    currentTheme: String,
    onThemeChange: (String) -> Unit,
    currentLanguage: String,
    onLanguageChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Configuración", style = MaterialTheme.typography.headlineMedium)

        ThemeSelector(
            currentTheme = currentTheme,
            onThemeChange = onThemeChange
        )

        LanguageSelector(
            currentLanguage = currentLanguage,
            onLanguageChange = onLanguageChange
        )
    }
}