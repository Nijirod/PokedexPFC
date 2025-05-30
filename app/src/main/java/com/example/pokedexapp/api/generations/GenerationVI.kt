package com.example.pokedexapp.api.generations

import com.google.gson.annotations.SerializedName
import com.example.pokedexapp.data.mapper.interfaces.IGame
import com.example.pokedexapp.data.mapper.interfaces.IGeneration

class GenerationVI(
    @SerializedName("omegaruby-alphasapphire")val omega_ruby_alpha_sapphire: OmegaRubyAlphaSapphire?,
    @SerializedName("x-y") val x_y: XY?
) : IGeneration {
    data class OmegaRubyAlphaSapphire(
        val front_default: String?,
        val front_female: String?,
        val front_shiny: String?,
        val front_shiny_female: String?
    ): IGame
    data class XY(
        val front_default: String?,
        val front_female: String?,
        val front_shiny: String?,
        val front_shiny_female: String?
    ): IGame
}
