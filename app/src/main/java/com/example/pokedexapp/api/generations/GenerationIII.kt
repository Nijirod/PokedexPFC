package com.example.pokedexapp.api.generations

import com.google.gson.annotations.SerializedName
import com.example.pokedexapp.data.mapper.interfaces.IGame
import com.example.pokedexapp.data.mapper.interfaces.IGeneration

class GenerationIII(
    val emerald: EmeraldSprites?,
    @SerializedName("firered-leafgreen") val firered_leafgreen: IIIGenerationSprites?,
    @SerializedName("ruby-sapphire") val ruby_sapphire: IIIGenerationSprites?
): IGeneration  {
    data class EmeraldSprites(
        val front_default: String?,
        val front_shiny: String?
    ): IGame

    data class IIIGenerationSprites(
        val back_default: String?,
        val back_shiny: String?,
        val front_default: String?,
        val front_shiny: String?
    ): IGame

}
