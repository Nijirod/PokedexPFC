package com.example.pokedexapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import com.example.pokedexapp.ui.components.DropdownMenu
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.pokedexapp.utils.getCenteredPokemonIndex
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.pokedexapp.PokemonList
import com.example.pokedexapp.R
import com.example.pokedexapp.ui.components.PokemonImageItem
import com.example.pokedexapp.ui.components.PokemonListItem
import com.example.pokedexapp.ui.navigation.PokemonNavigation
import com.example.pokedexapp.ui.viewmodel.PokemonListViewModel
import com.example.pokedexapp.utils.FilterOption
import com.example.pokedexapp.utils.GenerationOption
import com.example.pokedexapp.utils.rememberPokemonListState
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

@Composable
fun PokemonListScreen(
    pokemonNavigation: PokemonNavigation,
    modifier: Modifier = Modifier
) {
    val viewModel: PokemonListViewModel = hiltViewModel()
    val filteredList by viewModel.filteredPokemonList.collectAsState()
    var selectedPokemon by rememberPokemonListState(filteredList.firstOrNull())


    val generationLabels = mapOf(
        GenerationOption.ALL to stringResource(R.string.all),
        GenerationOption.GEN1 to stringResource(R.string.gen1),
        GenerationOption.GEN2 to stringResource(R.string.gen2),
        GenerationOption.GEN3 to stringResource(R.string.gen3),
        GenerationOption.GEN4 to stringResource(R.string.gen4),
        GenerationOption.GEN5 to stringResource(R.string.gen5),
        GenerationOption.GEN6 to stringResource(R.string.gen6),
        GenerationOption.GEN7 to stringResource(R.string.gen7),
        GenerationOption.GEN8 to stringResource(R.string.gen8),
        GenerationOption.GEN9 to stringResource(R.string.gen9)
    )
    val generations = GenerationOption.values().toList()
    var selectedGen by remember { mutableStateOf(GenerationOption.ALL) }
    val showFavorites by viewModel.filteredPokemonList.collectAsState().let { derivedStateOf { viewModel.filteredPokemonList.value.any { it.isFavorite } && viewModel.filteredPokemonList.value.all { it.isFavorite } } }
    var showFav by remember { mutableStateOf(false) }
    val filterOptions = listOf(FilterOption.ALL, FilterOption.FAVORITES)
    val filterLabels = mapOf(
        FilterOption.ALL to stringResource(R.string.all),
        FilterOption.FAVORITES to stringResource(R.string.favorites)
    )
    var selectedFilter by remember { mutableStateOf(FilterOption.ALL) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Pokédex", style = MaterialTheme.typography.titleLarge ) },
                    modifier = Modifier.fillMaxWidth().background(Color.Red),
                    actions = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            DropdownMenu(
                                options = generations.map { generationLabels[it] ?: "" },
                                selectedOption = generationLabels[selectedGen] ?: "",
                                onOptionSelected = { label ->
                                    val gen = generationLabels.entries.first { it.value == label }.key
                                    selectedGen = gen
                                    viewModel.setGeneration(gen.range?.let { it.first to it.last })
                                    viewModel.resetPage()
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            DropdownMenu(
                                options = filterOptions.map { filterLabels[it] ?: "" },
                                selectedOption = filterLabels[selectedFilter] ?: "",
                                onOptionSelected = { label ->
                                    val filter = filterLabels.entries.first { it.value == label }.key
                                    selectedFilter = filter
                                    val showFav = filter == FilterOption.FAVORITES
                                    viewModel.setShowFavorites(showFav)
                                    viewModel.resetPage()
                                }
                            )
                        }
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
                    pokemonList = filteredList,
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