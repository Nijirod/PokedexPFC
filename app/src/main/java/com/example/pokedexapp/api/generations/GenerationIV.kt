package com.example.pokedexapp.api.generations

import com.google.gson.annotations.SerializedName
import com.example.pokedexapp.data.mapper.interfaces.IGeneration

class GenerationIV(
    @SerializedName("diamond-pearl") val diamond_pearl: SharedSprites?,
    @SerializedName("heartgold-soulsilver") val heartgold_soulsilver: SharedSprites?,
    val platinum: SharedSprites?
) : IGeneration {
    data class SharedSprites(
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
