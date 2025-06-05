package com.example.pokedexapp.utils

import com.example.pokedexapp.R

object PokemonTypeDrawableMapper {
    private val typeToDrawable = mapOf(
        "normal" to R.drawable.normal,
        "fire" to R.drawable.fire,
        "water" to R.drawable.water,
        "electric" to R.drawable.electric,
        "grass" to R.drawable.grass,
        "ice" to R.drawable.ice,
        "fighting" to R.drawable.fighting,
        "poison" to R.drawable.poison,
        "ground" to R.drawable.ground,
        "flying" to R.drawable.flying,
        "psychic" to R.drawable.psychic,
        "bug" to R.drawable.bug,
        "rock" to R.drawable.rock,
        "ghost" to R.drawable.ghost,
        "dragon" to R.drawable.dark,
        "steel" to R.drawable.steel,
        "fairy" to R.drawable.fairy
    )

    fun getDrawableForType(type: String): Int? {
        return typeToDrawable[type.lowercase()]
    }
}