package com.example.pokedexapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import com.example.pokedexapp.ui.components.DropdownMenu
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import kotlinx.coroutines.flow.filter
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.pokedexapp.utils.getCenteredPokemonIndex
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.pokedexapp.PokemonList
import com.example.pokedexapp.ui.components.PokemonImageItem
import com.example.pokedexapp.ui.components.PokemonListItem
import com.example.pokedexapp.ui.navigation.PokemonNavigation
import com.example.pokedexapp.ui.viewmodel.PokemonListViewModel
import com.example.pokedexapp.utils.rememberPokemonListState
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

@Composable
fun PokemonListScreen(
    pokemonNavigation: PokemonNavigation,
    modifier: Modifier = Modifier
) {
    val viewModel: PokemonListViewModel = hiltViewModel()
    val pokemonList by viewModel.pokemonList.collectAsState()
    var selectedPokemon by rememberPokemonListState(pokemonList.firstOrNull())
    var filterText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pokédex") },
                modifier = Modifier.fillMaxWidth()
                    .background(Color.Red),
                actions = {
                    DropdownMenu(
                        options = listOf("All", "Favorites"),
                        selectedOption = if (filterText.isEmpty()) "All" else "Favorites",
                        onOptionSelected = { option ->
                            filterText = if (option == "All") "" else pokemonList.filter { it.isFavorite }
                                .joinToString(", ") { it.name }
                        }
                    )
                }
            )
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                selectedPokemon?.let { pokemon ->
                    PokemonImageItem(
                        pokemon = pokemon,
                        onItemClick = { pokemonNavigation.navigateToDetail(pokemon.id.toInt()) }
                    )
                }
            }
            PokemonListContent(
                pokemonList = pokemonList.filter { it.name.contains(filterText, ignoreCase = true) },
                modifier = Modifier.weight(0.6f),
                onItemClick = { pokemon ->
                    selectedPokemon = pokemon
                    pokemonNavigation.navigateToDetail(pokemon.id.toInt())
                },
                onLoadMore = { viewModel.loadNextPage() },
                viewModel = viewModel,
                onPokemonSelected = { selectedPokemon = it },
                selectedPokemon = selectedPokemon
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalSnapperApi::class)
@Composable
private fun PokemonListContent(
    pokemonList: List<PokemonList>,
    modifier: Modifier,
    onItemClick: (PokemonList) -> Unit,
    onLoadMore: () -> Unit = {},
    viewModel: PokemonListViewModel,
    onPokemonSelected: (PokemonList) -> Unit,
    selectedPokemon: PokemonList?,
) {
    val listState = rememberLazyListState()
    val snapperFlingBehavior = rememberSnapperFlingBehavior(listState)

    LaunchedEffect(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) {
        getCenteredPokemonIndex(
            listState = listState,
            itemCount = pokemonList.size,
            spacerOffset = 1
        )?.let { index ->
            val pokemon = pokemonList[index]
            if (pokemon != selectedPokemon) {
                onPokemonSelected(pokemon)
            }
        }
    }


    LaunchedEffect(pokemonList) {
        if (selectedPokemon !in pokemonList) {
            pokemonList.firstOrNull()?.let { onPokemonSelected(it) }
        }
    }

    LazyColumn(
        state = listState,
        flingBehavior = snapperFlingBehavior,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 210.dp)
    ) {
        itemsIndexed(pokemonList) { index, pokemon ->
            val isSelected = pokemon == selectedPokemon
            PokemonListItem(
                pokemon = pokemon,
                isFavorite = pokemon.isFavorite,
                onFavoriteClick = { isFavorite ->
                    viewModel.updateFavoriteStatus(pokemon, isFavorite)
                },
                onItemClick = {
                    onPokemonSelected(pokemon)
                    onItemClick(pokemon)
                },
                isSelected = isSelected
            )
        }
        item {
            LaunchedEffect(Unit) {
                onLoadMore()
            }
        }
    }

}

@Composable
@Preview
fun PokemonListScreenPreview() {
    PokemonListScreen(
        pokemonNavigation = PokemonNavigation(rememberNavController()),
        modifier = Modifier.fillMaxSize()
    )
}