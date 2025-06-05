package com.example.pokedexapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pokedexapp.ui.screens.PokemonDetailScreen
import com.example.pokedexapp.ui.screens.PokemonListScreen
import com.example.pokedexapp.ui.screens.SearchScreen
import com.example.pokedexapp.ui.screens.SettingsScreen
import com.example.pokedexapp.ui.viewmodel.SettingsViewModel

@Composable
fun BottomNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.PokemonListScreen.route,
        modifier = modifier
    ) {
        composable(ScreenRoutes.PokemonListScreen.route) {
            PokemonListScreen(
                pokemonNavigation = PokemonNavigation(navController)
            )
        }
        composable(
            route = "pokemon_detail_screen/{pokemonId}",
            arguments = listOf(navArgument("pokemonId") { type = NavType.IntType })
        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getInt("pokemonId")
            if (pokemonId != null) {
                PokemonDetailScreen(pokemonId = pokemonId)
            } else {
                navController.popBackStack()
            }
        }
        composable(ScreenRoutes.SearchScreen.route) {
            SearchScreen(
                pokemonNavigation = PokemonNavigation(navController)
            )
        }
        composable(ScreenRoutes.SettingsScreen.route) {
            val settingsViewModel: SettingsViewModel = hiltViewModel()

            SettingsScreen(
                currentTheme = settingsViewModel.currentTheme.collectAsState().value,
                onThemeChange = { newTheme -> settingsViewModel.changeTheme(newTheme) },
                currentLanguage = "Español",
                onLanguageChange = {}
            )
        }
    }
}