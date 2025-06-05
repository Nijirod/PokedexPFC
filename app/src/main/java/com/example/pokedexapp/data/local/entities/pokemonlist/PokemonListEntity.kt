package com.example.pokedexapp.data.local.entities.pokemonlist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_list")
data class PokemonListEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val url: String?
)