package com.example.pokedexapp.utils

object StatUtils {
    fun getAbbreviatedStatName(statName: String): String {
        return when (statName.lowercase()) {
            "hp" -> "HP"
            "attack" -> "Atk"
            "defense" -> "Def"
            "special-attack" -> "Sp.Atk"
            "special-defense" -> "Sp.Def"
            "speed" -> "Speed"
            else -> statName
        }
    }
}