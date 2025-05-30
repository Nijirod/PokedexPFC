package com.example.pokedexapp.data.local.entities.pokemondetail.otherentities

import androidx.room.Entity

@Entity(tableName = "pokemon_sprites", primaryKeys = ["pokemonId", "generation", "game", "type"])
data class SpritesEntity(
    val pokemonId: Int,
    val generation: Generation,
    val game: Game,
    val type: SpriteType,
    val url: String?)