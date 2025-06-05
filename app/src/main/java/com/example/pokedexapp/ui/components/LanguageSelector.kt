package com.example.pokedexapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.material3.*

@Composable
fun LanguageSelector(
    currentLanguage: String,
    onLanguageChange: (String) -> Unit
) {
    var selectedLanguage by remember { mutableStateOf(currentLanguage) }

    Column {
        Text(text = "Idioma")
        DropdownMenu(
            options = listOf("Español", "Inglés", "Francés"),
            selectedOption = selectedLanguage,
            onOptionSelected = {
                selectedLanguage = it
                onLanguageChange(it)
            }
        )
    }
}