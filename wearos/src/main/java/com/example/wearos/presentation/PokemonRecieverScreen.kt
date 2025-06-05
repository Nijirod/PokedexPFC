package com.example.wearos.presentation

import android.media.MediaPlayer
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment


@Composable
fun PokemonReceiverScreen(
    imageUrl: String?,
    audioUrl: String?
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        imageUrl?.let { img ->
            AsyncImage(
                model = img,
                contentDescription = "Imagen recibida",
                modifier = Modifier
                    .size(128.dp)
                    .clickable {
                        audioUrl?.let { url ->
                                try {
                                val mediaPlayer = MediaPlayer()
                                mediaPlayer.setDataSource(url)
                                mediaPlayer.prepare()
                                mediaPlayer.start()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
            )
        }
    }
}
