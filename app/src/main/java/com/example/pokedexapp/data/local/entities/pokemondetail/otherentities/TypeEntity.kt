package com.example.pokedexapp.data.local.entities.pokemondetail.otherentities

import androidx.room.Entity

@Entity(tableName = "pokemon_type", primaryKeys = ["pokemonId", "name"])
data class TypeEntity(
    val pokemonId: Int,
    val name: String,
    val url: String?,
)
