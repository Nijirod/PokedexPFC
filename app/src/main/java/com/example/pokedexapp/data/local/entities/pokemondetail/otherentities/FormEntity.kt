package com.example.pokedexapp.data.local.entities.pokemondetail.otherentities

import androidx.room.Entity


@Entity(tableName = "form", primaryKeys = ["pokemonId"])
class FormEntity(
    val pokemonId: Int,
    val name: String? = null,
    val url: String? = null
)
