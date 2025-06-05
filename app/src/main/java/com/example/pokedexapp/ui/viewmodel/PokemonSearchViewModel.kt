package com.example.pokedexapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.PokemonDetail
import com.example.pokedexapp.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonSearchViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    private val _pokemonDetail = MutableStateFlow<PokemonDetail?>(null)
    val pokemonDetail: StateFlow<PokemonDetail?> get() = _pokemonDetail

    fun fetchPokemonDetail(pokemonId: Int) {
        viewModelScope.launch {
            try {
                repository.fetchAndStorePokemonDetailFromApi(pokemonId)
                val detail = repository.getPokemonDetailFromDao(pokemonId)
                _pokemonDetail.value = detail
            } catch (e: Exception) {
                _pokemonDetail.value = null
            }
        }
    }
}