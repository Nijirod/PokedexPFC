package com.example.pokedexapp.ui.screens

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.pokedexapp.ui.components.ThemeSelector
import com.example.pokedexapp.ui.components.LanguageSelector
import java.util.Locale
import androidx.compose.ui.res.stringResource
import com.example.pokedexapp.R
import com.example.pokedexapp.utils.saveLanguagePreference

@Composable
fun SettingsScreen(
    currentTheme: String,
    onThemeChange: (String) -> Unit,
    currentLanguage: String,
    onLanguageChange: (String) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.settings_title),
            style = MaterialTheme.typography.bodyLarge
        )

        ThemeSelector(
            currentTheme = currentTheme,
            onThemeChange = onThemeChange
        )

        LanguageSelector(
            currentLanguage = currentLanguage,
            onLanguageChange = { languageCode ->
                onLanguageChange(languageCode)
                saveLanguagePreference(context, languageCode)
                setAppLocale(context, languageCode)
                (context as? Activity)?.recreate()
            }
        )
    }
}

fun setAppLocale(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}