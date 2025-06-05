package com.example.pokedexapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokedexapp.PokemonList

@Composable
fun PokemonListItem(
    pokemon: PokemonList,
    isFavorite: Boolean,
    onItemClick: (PokemonList) -> Unit,
    onFavoriteClick: (Boolean) -> Unit,
    isSelected: Boolean = false
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick(pokemon) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (isSelected) Color.LightGray else Color.Transparent)
                .padding(8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "${pokemon.id}   ${pokemon.name}",
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp)
            )
            IconButton(onClick = { onFavoriteClick(!isFavorite) }) {
                val icon = if (isFavorite) Icons.Filled.Star else Icons.Outlined.Star
                val iconColor = if (isFavorite) Color.Yellow else Color.Gray
                Icon(imageVector = icon, contentDescription = "Favorite", tint = iconColor)
            }
        }
    }
}

@Composable
fun PokemonImageItem(
    pokemon: PokemonList,
    onItemClick: (PokemonList) -> Unit
){
    AsyncImage(
        model = pokemon.urlImage,
        contentDescription = "Image of ${pokemon.name}",
        modifier = Modifier
            .padding(8.dp)
            .size(128.dp)
            .clickable { onItemClick(pokemon) },
        contentScale = ContentScale.Crop
    )
}