package com.example.pokedexapp.data.local.entities.pokemondetail.otherentities

import com.example.pokedexapp.data.mapper.interfaces.IGeneration

enum class Generation(val jsonKey: String) : IGeneration {
    GENERATION_I("generation_i"),
    GENERATION_II("generation_ii"),
    GENERATION_III("generation_iii"),
    GENERATION_IV("generation_iv"),
    GENERATION_V("generation_v"),
    GENERATION_VI("generation_vi"),
    GENERATION_VII("generation_vii"),
    GENERATION_VIII("generation_viii"),
    NONE("none");
}