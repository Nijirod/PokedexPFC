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

    private val _selectedGeneration = MutableStateFlow<Pair<Int, Int>?>(null)
    private val _showFavorites = MutableStateFlow(false)

    val filteredPokemonList: StateFlow<List<PokemonList>> = combine(
        pokemonList, _selectedGeneration, _showFavorites
    ) { list, gen, fav ->
        list.filter { pokemon ->
            val id = pokemon.id.toIntOrNull() ?: return@filter false
            val inGen = gen?.let { id in it.first..it.second } ?: true
            val isFav = if (fav) pokemon.isFavorite else true
            inGen && isFav
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val limit = 10
    private var isFetching = false
    private val generationOffsets = mutableMapOf<Pair<Int, Int>?, Int>()

    private fun getCurrentOffset(): Int {
        val offset = generationOffsets[_selectedGeneration.value] ?: 0
        println("getCurrentOffset: generación=${_selectedGeneration.value}, offset=$offset")
        return offset
    }
    private fun setCurrentOffset(offset: Int) {
        println("setCurrentOffset: generación=${_selectedGeneration.value}, nuevo offset=$offset")
        generationOffsets[_selectedGeneration.value] = offset
    }
    private fun getGlobalOffset(): Int {
        val gen = _selectedGeneration.value
        val localOffset = generationOffsets[gen] ?: 0
        return if (gen != null) (gen.first - 1) + localOffset else localOffset
    }

    private fun fetchPokemonList(reset: Boolean = false) {
        viewModelScope.launch {
            if (isFetching) return@launch
            isFetching = true
            try {
                val gen = _selectedGeneration.value
                var offset = if (reset) 0 else getCurrentOffset()
                val globalOffset = getGlobalOffset()
                println("fetchPokemonList: generación=$gen, offset_local=$offset, offset_global=$globalOffset, reset=$reset")
                val pokemonFromDb = if (gen != null) {
                    repository.getPokemonByGeneration(gen.first, gen.second, limit, offset)
                } else {
                    repository.getAllPokemonList(limit, offset)
                }
                if (pokemonFromDb.size < limit) {
                    if (gen != null) {
                        repository.fetchAndStorePokemonListByGeneration(gen.first, gen.second, limit, globalOffset)
                    } else {
                        repository.fetchAndStorePokemonList(limit, offset)
                    }
                }
                offset += limit
                setCurrentOffset(offset)
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
            fetchPokemonList(reset = false)
        }
    }

    fun resetPage() {
        setCurrentOffset(getCurrentOffset())
        fetchPokemonList(reset = true)
    }

    fun selectPokemon(pokemon: PokemonList) {
        _selectedPokemon.value = pokemon
    }

    fun selectPokemonByIndex(index: Int) {
        if (index in pokemonList.value.indices) {
            _selectedPokemon.value = pokemonList.value[index]
        }
    }

    fun setGeneration(gen: Pair<Int, Int>?) { _selectedGeneration.value = gen }
    fun setShowFavorites(show: Boolean) { _showFavorites.value = show }
}