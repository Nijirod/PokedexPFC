package com.example.pokedexapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.PokemonList
import com.example.pokedexapp.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val repository: PokemonRepository) : ViewModel() {

    val pokemonList: StateFlow<List<PokemonList>> = repository.getPokemonFromDatabase()
        .map { list -> list.map { PokemonList(it.name, it.url, it.isFavorite) } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _selectedPokemon = MutableStateFlow<PokemonList?>(null)
    val selectedPokemon: StateFlow<PokemonList?> get() = _selectedPokemon

    private var offset = 0
    private val limit = 10
    private var isFetching = false

    private fun fetchPokemonList() {
        viewModelScope.launch {
            if (isFetching) return@launch
            isFetching = true
            try {
                val pokemonFromDb = repository.getAllPokemonList(limit, offset)
                if (pokemonFromDb.size < limit) {
                    repository.fetchAndStorePokemonList(limit, offset)
                }
                offset += limit

                if (_selectedPokemon.value == null && pokemonList.value.isNotEmpty()) {
                    _selectedPokemon.value = pokemonList.value.first()
                }
            } catch (e: Exception) {
                println("Error obteniendo Pokémon: ${e.message}")
            }
            isFetching = false
        }
    }

    fun updateFavoriteStatus(pokemonList: PokemonList, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updatePokemonFavoriteStatus(pokemonList.name, isFavorite)
        }
    }

    fun loadNextPage() {
        if (!isFetching) {
            fetchPokemonList()
        }
    }

    fun resetPage() {
        offset = 0
        fetchPokemonList()
    }

    fun selectPokemon(pokemon: PokemonList) {
        println("Selected Pokemon: ${pokemon.name}")
        _selectedPokemon.value = pokemon
    }

    fun selectPokemonByIndex(index: Int) {
        if (index in pokemonList.value.indices) {
            _selectedPokemon.value = pokemonList.value[index]
        }
    }
}