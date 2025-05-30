package com.example.pokedexapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.material3.*

@Composable
fun ThemeSelector(
    currentTheme: String,
    onThemeChange: (String) -> Unit
) {
    var selectedTheme by remember { mutableStateOf(currentTheme) }

    Column {
        Text(text = "Tema")
        DropdownMenu(
            options = listOf("Claro", "Oscuro", "Tema del Dispositivo"),
            selectedOption = selectedTheme,
            onOptionSelected = {
                selectedTheme = it
                onThemeChange(it)
            }
        )
    }
}