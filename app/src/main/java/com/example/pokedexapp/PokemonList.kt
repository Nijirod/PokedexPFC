package com.example.pokedexapp

data class PokemonList(
    val name: String,
    val url: String?,
    val isFavorite: Boolean = false
) {
    val id: String
        get() = url
            ?.substringAfterLast("pokemon/")
            ?.substringBefore(".png")
            ?.removeSuffix("/")
            ?: "Unknown"
    val urlImage: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    val urlIcon: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-viii/icons/$id.png"
}
