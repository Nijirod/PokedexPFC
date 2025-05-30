 package com.example.pokedexapp.ui.screens

import ChoosePokemonSpriteScreen
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pokedexapp.PokemonDetail
import com.example.pokedexapp.R
import com.example.pokedexapp.data.local.entities.pokemondetail.otherentities.SpriteType
import com.example.pokedexapp.ui.components.StatBar
import com.example.pokedexapp.ui.viewmodel.PokemonDetailViewModel
import com.example.pokedexapp.utils.ImageResizer
import com.example.pokedexapp.utils.WearNodeHelper
import com.example.pokedexapp.utils.sendUrlsToWear
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

 @Composable
fun PokemonDetailScreen(
    pokemonId: Int,
    modifier: Modifier = Modifier,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val pokemonDetail by viewModel.pokemonDetail.collectAsState()
    var showSpriteChooser by remember { mutableStateOf(false) }
    var selectedSpriteUrl by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current



    LaunchedEffect(Unit) {
        viewModel.fetchPokemonDetail(pokemonId)
    }


    pokemonDetail?.let { detail ->
        PokemonDetailContent(
            detail = detail,
            modifier = modifier,
            scrollState = scrollState,
            selectedSpriteUrl = selectedSpriteUrl,
            onImageClick = { showSpriteChooser = true }
        )
        if (showSpriteChooser) {
            ChoosePokemonSpriteScreen(
                sprites = detail.sprites?.mapNotNull { it.url } ?: emptyList(),
                onSpriteSelected = { url ->
                    selectedSpriteUrl = url
                    showSpriteChooser = false
                },
                onDismiss = { showSpriteChooser = false }
            )
        }
        Button(
            onClick = {
                val imageUrl = selectedSpriteUrl
                    ?: detail.sprites?.firstOrNull { it.type == SpriteType.FRONT_DEFAULT }
                        ?.let { "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${it.pokemonId}.png" }
                    ?: ""
                val cryUrl = detail.cries?.latest

                if (cryUrl != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        WearNodeHelper.logConnectedNodes(context)
                        WearNodeHelper.sendMessageToWatch(context, "$imageUrl,$cryUrl")
                    }
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Enviar a reloj")
        }
    }
}
 @Composable
 private fun PokemonDetailContent(
     detail: PokemonDetail,
     modifier: Modifier,
     scrollState: ScrollState,
     selectedSpriteUrl: String?,
     onImageClick: () -> Unit
 ) {
     Column(
         modifier = modifier
             .padding(16.dp)
             .verticalScroll(scrollState)
             .fillMaxWidth(),
         verticalArrangement = Arrangement.spacedBy(8.dp),
         horizontalAlignment = Alignment.CenterHorizontally
     ) {
         Text(
             text = stringResource(R.string.id, detail.id ?: ""),
             style = MaterialTheme.typography.titleLarge
         )
         Text(
             text = stringResource(R.string.name, detail.name ?: ""),
             style = MaterialTheme.typography.titleMedium
         )
         Text(
             text = stringResource(R.string.order, detail.order ?: ""),
             style = MaterialTheme.typography.bodyMedium
         )

         Spacer(modifier = Modifier.height(16.dp))

         detail.sprites
             ?.firstOrNull { it.type == SpriteType.FRONT_DEFAULT }
             ?.let { sprite ->
                 val spriteUrl = selectedSpriteUrl
                     ?: detail.sprites?.firstOrNull { it.type == SpriteType.FRONT_DEFAULT }
                         ?.let { "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${it.pokemonId}.png" }

                 AsyncImage(
                     model = spriteUrl,
                     contentDescription = stringResource(R.string.image_of, detail.name ?: ""),
                     modifier = Modifier
                         .padding(8.dp)
                         .size(128.dp)
                         .align(Alignment.CenterHorizontally)
                         .clickable { onImageClick() }
                 )
             }
         Text(
             text = stringResource(R.string.species, detail.species.name ?: ""),
             style = MaterialTheme.typography.bodyMedium
         )
         Text(
             text = stringResource(R.string.types, detail.types?.joinToString(", ") { it.name.toString() } ?: ""),
             style = MaterialTheme.typography.bodyMedium
         )
         Text(
             text = stringResource(
                 R.string.abilities,
                 detail.abilities?.joinToString(", ") { it.name.toString() } ?: ""
             ),
             style = MaterialTheme.typography.bodyMedium
         )
         detail.stats?.let { stats ->
             Column(
                 modifier = Modifier
                     .padding(16.dp),
             ) {
                 stats.forEach { stat ->
                     StatBar(
                         statName = stat.name.toString(),
                         value = stat.value?.toFloat() ?: 0f,
                         maxValue = 255f,
                     )
                 }
             }
         }

         Spacer(modifier = Modifier.height(16.dp))

         Row {
             Text(
                 text = stringResource(R.string.weight, detail.weight ?: ""),
                 style = MaterialTheme.typography.bodyMedium
             )
             Spacer(modifier = Modifier.width(16.dp))
             Text(
                 text = stringResource(R.string.height, detail.height ?: ""),
                 style = MaterialTheme.typography.bodyMedium
             )
         }
     }
 }

 @Preview(showBackground = true)
 @Composable
 fun PokemonDetailScreenPreview() {
     PokemonDetailScreen(
         pokemonId = 1
     )
 }