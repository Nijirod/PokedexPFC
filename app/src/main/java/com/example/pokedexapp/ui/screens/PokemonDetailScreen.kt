 package com.example.pokedexapp.ui.screens

import ChoosePokemonSpriteScreen
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
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
import com.example.pokedexapp.utils.PokemonTypeDrawableMapper
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
             .verticalScroll(scrollState)
             .fillMaxWidth(),
         verticalArrangement = Arrangement.spacedBy(8.dp),
         horizontalAlignment = Alignment.CenterHorizontally
     ) {
         Text(
             text = detail.name?.replaceFirstChar { it.uppercase() } ?: "",
             style = MaterialTheme.typography.titleLarge
         )
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
         Row {
             detail.types?.forEach { type ->
                 val drawableId = type.name?.let { PokemonTypeDrawableMapper.getDrawableForType(it) }
                 drawableId?.let {
                     Image(
                         painter = painterResource(id = it),
                         contentDescription = type.name,
                         modifier = Modifier.size(50.dp)
                     )
                 }
             }
         }
         Row {
             Text(
                 text = stringResource(R.string.weight, detail.weight ?: ""),
                 style = MaterialTheme.typography.bodyLarge
             )
             Spacer(modifier = Modifier.width(16.dp))
             Text(
                 text = stringResource(R.string.height, detail.height ?: ""),
                 style = MaterialTheme.typography.bodyLarge
             )
         }
         Text(
             text = stringResource(
                 R.string.abilities,
                 detail.abilities?.joinToString(", ") { it.name.toString() } ?: ""
             ),
             style = MaterialTheme.typography.bodyLarge
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



     }
 }

 @Preview(showBackground = true)
 @Composable
 fun PokemonDetailScreenPreview() {
     PokemonDetailScreen(
         pokemonId = 1
     )
 }