package com.example.pokedexapp.utils

import android.content.Context

fun saveLanguagePreference(context: Context, languageCode: String) {
    val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    prefs.edit().putString("language", languageCode).apply()
}

fun getSavedLanguage(context: Context): String {
    val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    return prefs.getString("language", "en") ?: "en"
}

