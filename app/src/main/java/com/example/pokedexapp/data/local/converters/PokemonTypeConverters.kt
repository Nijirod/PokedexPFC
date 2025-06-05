package com.example.pokedexapp.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.AbilityEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.CriesEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.FormEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.SpeciesEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.SpritesEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.StatEntity
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.TypeEntity

class PokemonTypeConverters {
    private val gson = Gson()
    @TypeConverter
    fun fromStatList(value: List<StatEntity>): String = gson.toJson(value)

    @TypeConverter
    fun toStatList(value: String): List<StatEntity> =
        gson.fromJson(value, object : TypeToken<List<StatEntity>>() {}.type)

    @TypeConverter
    fun fromSprites(value: SpritesEntity): String = gson.toJson(value)

    @TypeConverter
    fun toSprites(value: String): SpritesEntity =
        gson.fromJson(value, SpritesEntity::class.java)

    @TypeConverter
    fun fromSpeciesEntity(species: SpeciesEntity?): String? {
        return gson.toJson(species)
    }

    @TypeConverter
    fun toSpeciesEntity(speciesJson: String?): SpeciesEntity? {
        return speciesJson?.let { gson.fromJson(it, SpeciesEntity::class.java) }
    }
    @TypeConverter
    fun fromTypeEntityList(types: List<TypeEntity>?): String? {
        return gson.toJson(types)
    }

    @TypeConverter
    fun toTypeEntityList(typesJson: String?): List<TypeEntity>? {
        val type = object : TypeToken<List<TypeEntity>>() {}.type
        return gson.fromJson(typesJson, type)
    }

    @TypeConverter
    fun fromFormEntity(form: FormEntity?): String? {
        return gson.toJson(form)
    }

    @TypeConverter
    fun toFormEntity(formJson: String?): FormEntity? {
        return formJson?.let { gson.fromJson(it, FormEntity::class.java) }
    }
    @TypeConverter
    fun fromCriesEntity(cries: CriesEntity?): String? {
        return cries?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toCriesEntity(criesJson: String?): CriesEntity? {
        return criesJson?.let { gson.fromJson(it, CriesEntity::class.java) }
    }
    @TypeConverter
    fun fromAbilityList(abilities: List<AbilityEntity>?): String? {
        return abilities?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toAbilityList(abilitiesJson: String?): List<AbilityEntity>? {
        return abilitiesJson?.let {
            gson.fromJson(it, object : TypeToken<List<AbilityEntity>>() {}.type)
        }
    }

}