package com.example.pokedexapp.data.local.entities.pokemondetail.otherentities

import com.example.pokedexapp.data.local.entities.pokemondetail.PokemonDetailEntity

data class PokemonDetailEntities(
    val detailEntity: PokemonDetailEntity,
    val speciesEntity: SpeciesEntity?,
    val abilities: List<AbilityEntity>,
    val types: List<TypeEntity>,
    val criesEntity: CriesEntity?,
    val formEntity: FormEntity?,
    val sprites: List<SpritesEntity>,
    val stats: List<StatEntity?>,
)