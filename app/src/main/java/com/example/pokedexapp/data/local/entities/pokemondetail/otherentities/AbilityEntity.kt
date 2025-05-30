package com.example.pokedexapp.data.local.entities.pokemondetail.otherentities

import androidx.room.Entity

@Entity(tableName = "pokemon_ability", primaryKeys = ["pokemonId", "name"])
data class AbilityEntity(
    val pokemonId: Int,
    val name: String,
    val url: String,
)