package com.example.pokedexapp.ui.navigation

sealed class ScreenRoutes(val route: String) {

    data object StartScreen : ScreenRoutes("start_screen")
    data object PokemonListScreen : ScreenRoutes("pokemon_list_screen")
    data object PokemonDetailScreen : ScreenRoutes("pokemon_detail_screen")
    data object SearchScreen : ScreenRoutes("search_screen")
    data object SettingsScreen : ScreenRoutes("settings_screen")

    data object RootNav : ScreenRoutes("root_nav_graph")
    data object PokemonListNav : ScreenRoutes("pokemon_list_nav_graph")
    data object SearchNav : ScreenRoutes("search_nav_graph")
    data object SettingsNav : ScreenRoutes("settings_nav_graph")
}