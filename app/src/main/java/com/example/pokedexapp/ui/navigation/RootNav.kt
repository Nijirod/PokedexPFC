package com.example.pokedexapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pokedexapp.ui.screens.PokemonListScreen
import com.example.pokedexapp.ui.screens.PokemonDetailScreen
import com.example.pokedexapp.ui.screens.SearchScreen
import com.example.pokedexapp.ui.screens.SettingsScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedexapp.ui.viewmodel.SettingsViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun RootNav(navController: NavHostController, modifier: Modifier = Modifier) {

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.PokemonListNav.route
    ) {
        addPokemonListNavGraph(navController)
        addSearchNavGraph(navController)
        addSettingsNavGraph(navController)
    }
}

fun NavGraphBuilder.addPokemonListNavGraph(navController: NavHostController) {
    navigation(
        startDestination = ScreenRoutes.PokemonListScreen.route,
        route = ScreenRoutes.PokemonListNav.route
    ) {
        composable(ScreenRoutes.PokemonListScreen.route) {
            PokemonListScreen(
                pokemonNavigation = PokemonNavigation(navController)
            )
        }
        composable(
            route = "${ScreenRoutes.PokemonDetailScreen.route}/{pokemonId}",
            arguments = listOf(navArgument("pokemonId") { type = NavType.IntType })
        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getString("pokemonId")?.toInt()
            if (pokemonId != null) {
                PokemonDetailScreen(pokemonId = pokemonId)
            } else {
                navController.popBackStack(ScreenRoutes.PokemonListScreen.route, inclusive = false)
            }
        }
    }
}
fun NavGraphBuilder.addSearchNavGraph(navController: NavHostController) {
    navigation(
        startDestination = ScreenRoutes.SearchScreen.route,
        route = ScreenRoutes.SearchNav.route
    ) {
        composable(ScreenRoutes.SearchScreen.route) {
            SearchScreen(
                pokemonNavigation = PokemonNavigation(navController)
            )
        }
    }
}

fun NavGraphBuilder.addSettingsNavGraph(navController: NavHostController) {
    navigation(
        startDestination = ScreenRoutes.SettingsScreen.route,
        route = ScreenRoutes.SettingsNav.route
    ) {
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