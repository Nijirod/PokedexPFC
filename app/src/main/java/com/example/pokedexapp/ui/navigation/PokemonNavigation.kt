package com.example.pokedexapp.ui.navigation

import androidx.navigation.NavController

class PokemonNavigation(private val navController: NavController) {
    fun navigateToDetail(id: Int) {
        navController.navigate("pokemon_detail_screen/$id")
    }

    fun navigateToList() {
        navController.navigate(ScreenRoutes.PokemonListScreen.route)
    }
}