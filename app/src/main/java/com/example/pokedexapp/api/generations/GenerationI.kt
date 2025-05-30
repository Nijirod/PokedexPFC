package com.example.pokedexapp.api.generations

import com.google.gson.annotations.SerializedName
import com.example.pokedexapp.data.mapper.interfaces.IGame
import com.example.pokedexapp.data.mapper.interfaces.IGeneration

data class GenerationI (
    @SerializedName("red-blue") val red_blue: BasicSprites?,
    val yellow: BasicSprites?
): IGeneration {
    data class BasicSprites(
        val back_default: String?,
        val back_gray: String?,
        val back_transparent: String?,
        val front_default: String?,
        val front_gray: String?,
        val front_transparent: String?
    ): IGame
}
