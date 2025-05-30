package com.example.pokedexapp.data.local.entities.pokemondetail

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pokedexapp.data.local.converters.PokemonTypeConverters

@Entity(tableName = "pokemon_detail")
@TypeConverters(PokemonTypeConverters::class)
data class PokemonDetailEntity(
    @PrimaryKey val id: Int?,
    val name: String?,
    val pokemonOrder: Int?,
    val isDefault: Boolean?,
    val height: String?,
    val weight: Int?,
)