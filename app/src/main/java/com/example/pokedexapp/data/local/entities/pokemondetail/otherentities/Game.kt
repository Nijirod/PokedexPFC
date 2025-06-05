package com.example.pokedexapp.data.local.entities.pokemondetail.otherentities

import com.example.pokedexapp.data.mapper.interfaces.IGame

enum class Game(val jsonKey: String) : IGame{
    RED_BLUE("red_blue"),
    YELLOW("yellow"),
    CRYSTAL("crystal"),
    GOLD("gold"),
    SILVER("silver"),
    EMERALD("emerald"),
    FIRERED_LEAFGREEN("firered_leafgreen"),
    RUBY_SAPPHIRE("ruby_sapphire"),
    DIAMOND_PEARL("diamond_pearl"),
    HEARTGOLD_SOULSILVER("heartgold_soulsilver"),
    PLATINUM("platinum"),
    BLACK_WHITE("black_white"),
    OMEGARUBY_ALPHASAPPHIRE("omega_ruby_alpha_sapphire"),
    X_Y("x_y"),
    ICONS("icons"),
    ULTRA_SUN_ULTRA_MOON("ultra_sun_ultra_moon"),
    NONE("none");
}
