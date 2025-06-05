package com.example.pokedexapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.SpriteType
import com.example.pokedexapp.ui.viewmodel.PokemonSearchViewModel
import com.example.pokedexapp.ui.navigation.PokemonNavigation

@Composable
fun SearchScreen(
    pokemonNavigation: PokemonNavigation,
    viewModel: PokemonSearchViewModel = hiltViewModel()
) {
    var inputId by remember { mutableStateOf("") }
    val pokemonDetail by viewModel.pokemonDetail.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = inputId,
            onValueChange = { inputId = it },
            label = { Text("Introduce el ID del Pokémon") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val id = inputId.toIntOrNull()
                if (id != null) {
                    viewModel.fetchPokemonDetail(id)
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Buscar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        pokemonDetail?.let { detail ->
            val spriteUrl = detail.sprites
                ?.firstOrNull { it.type == SpriteType.FRONT_DEFAULT }
                ?.let { "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${it.pokemonId}.png" }

            spriteUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = "Imagen de ${detail.name}",
                    modifier = Modifier
                        .size(128.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 8.dp)
                        .clickable { detail.id?.let { pokemonNavigation.navigateToDetail(it) } }
                )
            }

            Text(
                "${detail.id}",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                detail.name?.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } ?: "",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { detail.id?.let { pokemonNavigation.navigateToDetail(it) } },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Ver detalle completo")
            }
        }
    }
}