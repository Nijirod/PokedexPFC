package com.example.pokedexapp.api.generations

import com.google.gson.annotations.SerializedName
import com.example.pokedexapp.data.mapper.interfaces.IGeneration

class GenerationV(
    @SerializedName("black-white") val black_white: BlackWhiteSprites?

): IGeneration  {
    data class BlackWhiteSprites(
        val animated: AnimatedSprites,
        val back_default: String?,
        val back_female: String?,
        val back_shiny: String?,
        val back_shiny_female: String?,
        val front_default: String?,
        val front_female: String?,
        val front_shiny: String?,
        val front_shiny_female: String?
    )
    data class AnimatedSprites(
        val back_default: String?,
        val back_female: String?,
        val back_shiny: String?,
        val back_shiny_female: String?,
        val front_default: String?,
        val front_female: String?,
        val front_shiny: String?,
        val front_shiny_female: String?
    )
}
