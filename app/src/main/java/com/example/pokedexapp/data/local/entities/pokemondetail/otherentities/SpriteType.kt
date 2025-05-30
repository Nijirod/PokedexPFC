package com.example.pokedexapp.data.local.entities.pokemondetail.otherentities

import com.example.pokedexapp.data.mapper.interfaces.IType

enum class SpriteType(val jsonKey: String) : IType{
    BACK_DEFAULT("back_default"),
    BACK_FEMALE("back_female"),
    BACK_SHINY("back_shiny"),
    BACK_SHINY_FEMALE("back_shiny_female"),
    FRONT_DEFAULT("front_default"),
    FRONT_FEMALE("front_female"),
    FRONT_SHINY("front_shiny"),
    FRONT_SHINY_FEMALE("front_shiny_female"),
    BACK_GRAY("back_gray"),
    BACK_TRANSPARENT("back_transparent"),
    FRONT_GRAY("front_gray"),
    FRONT_TRANSPARENT("front_transparent"),
    BACK_SHINY_TRANSPARENT("back_shiny_transparent"),
    FRONT_SHINY_TRANSPARENT("front_shiny_transparent"),
    ANIMATED("animated"),
    NONE("none")
}
