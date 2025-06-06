package com.example.pokedexapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.example.pokedexapp.ui.screens.setAppLocale
import com.example.pokedexapp.ui.viewmodel.SettingsViewModel
import com.example.pokedexapp.utils.getSavedLanguage
import com.example.pokedexapp.utils.saveLanguagePreference

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
            val context = LocalContext.current
            var currentLanguage by remember { mutableStateOf(getSavedLanguage(context)) }

            SettingsScreen(
                currentTheme = settingsViewModel.currentTheme.collectAsState().value,
                onThemeChange = { newTheme -> settingsViewModel.changeTheme(newTheme) },
                currentLanguage = currentLanguage,
                onLanguageChange = { newLang ->
                    currentLanguage = newLang
                    saveLanguagePreference(context, newLang)
                    setAppLocale(context, newLang)
                    (context as? android.app.Activity)?.recreate()
                }
            )
        }
    }
}