package com.example.pokedexapp.data.local.entities.pokemonfavorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_favorite")
data class PokemonFavoriteEntity(
    @PrimaryKey val id: Int,
    val isFavorite: Boolean
)