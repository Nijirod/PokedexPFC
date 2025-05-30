package com.example.pokedexapp.api.generations

import com.example.pokedexapp.data.mapper.interfaces.IGame
import com.example.pokedexapp.data.mapper.interfaces.IGeneration

class GenerationVIII (
    val icons : Icons?
): IGeneration {
    data class Icons(
        val front_default: String?,
        val front_female: String?
    ): IGame


}
