package com.example.pokedexapp.ui.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pokedexapp.R

@Composable
fun BottomBar(navController: NavHostController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val items = listOf(
        BottomBarItem(ScreenRoutes.PokemonListScreen.route, R.drawable.ic_pokeball),
        BottomBarItem(ScreenRoutes.SearchScreen.route, R.drawable.ic_search),
        BottomBarItem(ScreenRoutes.SettingsScreen.route, R.drawable.ic_settings)
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.route == item.route,
                onClick = { navController.navigate(item.route) },
                icon = { androidx.compose.material3.Icon(painter = painterResource(id = item.icon), contentDescription = null) }
            )
        }
    }
}

data class BottomBarItem(
    val route: String,
    val icon: Int
)