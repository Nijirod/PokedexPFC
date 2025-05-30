package com.example.pokedexapp.data.repository

import com.example.pokedexapp.PokemonDetail
import com.example.pokedexapp.PokemonList
import com.example.pokedexapp.api.PokemonApiServiceInterface
import com.example.pokedexapp.data.local.dao.PokemonDao
import com.example.pokedexapp.data.local.entities.pokemonlist.PokemonListEntity
import com.example.pokedexapp.data.local.entities.views.PokemonListWithFavoriteView
import com.example.pokedexapp.data.local.entities.pokemonfavorite.PokemonFavoriteEntity
import com.example.pokedexapp.data.mapper.PokemonMapper
import com.example.pokedexapp.data.mapper.PokemonMapper.toDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val apiService: PokemonApiServiceInterface,
    private val pokemonDao: PokemonDao
) {
    fun getPokemonFromDatabase(): Flow<List<PokemonList>> = pokemonDao.allPokemonList()

    suspend fun fetchAndStorePokemonList(limit: Int, offset: Int): List<PokemonListEntity> {
        val apiResponse = apiService.getPokemonList(limit, offset)
        val pokemonList = apiResponse.results.map { pokemon ->
            PokemonListEntity(
                id = pokemon.id.toInt(),
                name = pokemon.name,
                url = pokemon.url,
            )
        }
        pokemonDao.insertPokemonList(pokemonList)
        return pokemonList
    }

    suspend fun fetchAndStorePokemonDetailFromApi(id: Int) {
        val apiResponse = apiService.getPokemonDetail(id)
        val pokemonDetailEntities = PokemonMapper.fromResponseToEntities(apiResponse)

        pokemonDao.insertPokemonDetailEntity(pokemonDetailEntities.detailEntity)
        pokemonDetailEntities.speciesEntity?.let { pokemonDao.insertSpeciesEntity(it) }
        val normalizedSprites = PokemonMapper.normalizeSprites(pokemonDetailEntities.sprites)
        normalizedSprites.forEach { pokemonDao.insertSpritesEntity(it) }
        pokemonDetailEntities.abilities.forEach { pokemonDao.insertAbilityEntity(it) }
        pokemonDetailEntities.types.forEach { pokemonDao.insertTypeEntity(it) }
        pokemonDetailEntities.criesEntity?.let { pokemonDao.insertCriesEntity(it) }
        pokemonDetailEntities.formEntity?.let { pokemonDao.insertFormEntity(it) }
        pokemonDetailEntities.stats.filterNotNull().forEach { pokemonDao.insertStatEntity(it) }
    }


    suspend fun updatePokemonFavoriteStatus(name: String, isFavorite: Boolean) {
        val pokemon = pokemonDao.getPokemonByName(name)
        if (pokemon != null) {
            val favoriteEntity = PokemonFavoriteEntity(
                id = pokemon.id,
                isFavorite = isFavorite
            )
            if (isFavorite) {
                pokemonDao.insertFavorite(favoriteEntity)
            } else {
                pokemonDao.deleteFavorite(pokemon.id)
            }
        }
    }


    suspend fun getAllPokemonList(limit: Int, offset: Int): List<PokemonListWithFavoriteView> {
        return pokemonDao.getAllPokemonList(limit, offset)
    }

    suspend fun getPokemonDetailFromDao(id: Int): PokemonDetail? {
        val detailView = pokemonDao.getPokemonDetailViewById(id)
        return detailView?.toDomain()
    }

    suspend fun getPokemonByName(name: String): PokemonListWithFavoriteView? {
        return pokemonDao.getPokemonByName(name)
    }
}