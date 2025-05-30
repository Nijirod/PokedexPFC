package com.example.pokedexapp

import com.example.pokedexapp.api.AbilityDetail
import com.example.pokedexapp.api.Cries
import com.example.pokedexapp.api.Form
import com.example.pokedexapp.api.Species
import com.example.pokedexapp.api.TypeName
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.SpritesEntity

data class PokemonDetail(
    val id: Int?,
    val order: Int?,
    val name: String?,
    val species: Species,
    val types: List<TypeName>?,
    val form: Form?,
    val isDefault: Boolean?,
    val cries: Cries?,
    val sprites: List<SpritesEntity>?,
    val abilities: List<AbilityDetail>?,
    val stats: List<Stat>?,
    val weight: String?,
    val height: String?
)

data class Stat(
    val name: String?,
    val value: Int?
)