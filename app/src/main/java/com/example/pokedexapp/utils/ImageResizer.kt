package com.example.pokedexapp.utils

import android.graphics.Bitmap
import androidx.compose.ui.unit.Dp
import coil.size.Size
import coil.transform.Transformation
import androidx.core.graphics.scale

class ImageResizer(private val width: Dp, private val height: Dp) : Transformation {
    override val cacheKey: String
        get() = "ImageResizer($width, $height)"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        return input.scale(width.value.toInt(), height.value.toInt())
    }
}