package com.example.pokedexapp.api.generations

import com.google.gson.annotations.SerializedName
import com.example.pokedexapp.data.mapper.interfaces.IGame
import com.example.pokedexapp.data.mapper.interfaces.IGeneration

class GenerationVII(
    @SerializedName("ultra-sun-ultra-moon") val ultra_sun_ultra_moon: UltraSunUltraMoon?,
    val icons: Icons?
): IGeneration  {
    data class Icons(
        val front_default: String?,
        val front_female: String?
    ): IGame
    data class UltraSunUltraMoon(
        val front_default: String?,
        val front_female: String?,
        val front_shiny: String?,
        val front_shiny_female: String?
    ): IGame

}
