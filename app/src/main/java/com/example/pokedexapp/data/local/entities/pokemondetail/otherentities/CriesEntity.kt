package com.example.pokedexapp.data.local.entities.pokemondetail.otherentities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "pokemon_cries")
class CriesEntity(
    @PrimaryKey val pokemonId: Int,
    val latest: String? = null,
    val legacy: String? = null
)
