package com.example.pokedexapp.data.local.entities.views

import androidx.room.Relation
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.AbilityEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.CriesEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.FormEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.SpeciesEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.SpritesEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.StatEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.TypeEntity

data class PokemonDetailView(
    val id: Int,
    val name: String,
    val order: Int?,
    val isDefault: Boolean?,
    val height: String?,
    val weight: Int?,
    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId")
    val species: SpeciesEntity?,

    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId")
    val types: List<TypeEntity>?,
    
    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId")
    val abilities: List<AbilityEntity>?,

    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId")
    val cries: CriesEntity?,

    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId")
    val sprites: List<SpritesEntity>?,

    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId")
    val form: FormEntity?,

    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId")
    val stats: List<StatEntity>?
)