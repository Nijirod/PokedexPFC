package com.example.pokedexapp.data.local.entities.pokemondetail.otherentities
import androidx.room.Entity

@Entity(tableName = "pokemon_species", primaryKeys = ["pokemonId", "name"])
class SpeciesEntity (
    val pokemonId: Int,
    val name: String,
    val url: String? = null
    )