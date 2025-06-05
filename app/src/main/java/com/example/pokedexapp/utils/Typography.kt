package com.example.pokedexapp.utils

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.pokedexapp.R

val kvnPokemonFont = FontFamily(
    Font(R.font.kvn_pokemon_gen_5)
)

val PokemonTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = kvnPokemonFont,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = kvnPokemonFont,
        fontSize = 22.sp
    ),
)
