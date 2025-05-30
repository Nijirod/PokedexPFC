package com.example.pokedexapp.api

import com.example.pokedexapp.PokemonList

data class PokemonApiResponse(
    val results: List<PokemonList>
)