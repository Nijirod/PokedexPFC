package com.example.pokedexapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.res.stringResource
import com.example.pokedexapp.R

@Composable
fun ThemeSelector(
    currentTheme: String,
    onThemeChange: (String) -> Unit
) {
    val themeOptions = listOf(
        "light" to stringResource(R.string.theme_light),
        "dark" to stringResource(R.string.theme_dark),
        "system" to stringResource(R.string.theme_system)
    )
    val selectedOption = themeOptions.find { it.first == currentTheme }?.second ?: themeOptions[0].second
    var selectedTheme by remember { mutableStateOf(selectedOption) }

    Column {
        Text(text = stringResource(R.string.settings_theme), style = MaterialTheme.typography.bodyLarge)
        DropdownMenu(
            options = themeOptions.map { it.second },
            selectedOption = selectedTheme,
            onOptionSelected = { label ->
                selectedTheme = label
                val value = themeOptions.find { it.second == label }?.first ?: "light"
                onThemeChange(value)
            }
        )
    }
}