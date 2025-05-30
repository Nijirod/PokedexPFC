package com.example.pokedexapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.pokedexapp.PokemonList

@Composable
fun rememberPokemonListState(initialValue: PokemonList?): MutableState<PokemonList?> {
    val pokemonListSaver = Saver<PokemonList?, Map<String, Any>>(
        save = { pokemon ->
            pokemon?.let {
                mapOf(
                    "name" to it.name,
                    "url" to it.url,
                    "isFavorite" to it.isFavorite
                ) as Map<String, Any>
            } ?: emptyMap<String, Any>()
        },
        restore = { map ->
            if (map.isNotEmpty()) {
                PokemonList(
                    name = map["name"] as String,
                    url = map["url"] as String,
                    isFavorite = map["isFavorite"] as Boolean
                )
            } else {
                null
            }
        }
    )

    return rememberSaveable(stateSaver = pokemonListSaver) { mutableStateOf(initialValue) }
}