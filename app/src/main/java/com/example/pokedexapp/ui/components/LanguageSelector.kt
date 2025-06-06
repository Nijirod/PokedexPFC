package com.example.pokedexapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.res.stringResource
import com.example.pokedexapp.R


@Composable
fun LanguageSelector(
    currentLanguage: String,
    onLanguageChange: (String) -> Unit
) {
    val languageOptions = listOf(
        "es" to stringResource(R.string.lang_spanish),
        "en" to stringResource(R.string.lang_english),
        "fr" to stringResource(R.string.lang_french)
    )
    val selectedOption = languageOptions.find { it.first == currentLanguage }?.second ?: languageOptions[0].second
    var selectedLanguage by remember { mutableStateOf(selectedOption) }

    Column {
        Text(text = stringResource(R.string.settings_language), style = MaterialTheme.typography.bodyLarge)
        DropdownMenu(
            options = languageOptions.map { it.second },
            selectedOption = selectedLanguage,
            onOptionSelected = { label ->
                selectedLanguage = label
                val code = languageOptions.find { it.second == label }?.first ?: "en"
                onLanguageChange(code)
            }
        )
    }
}