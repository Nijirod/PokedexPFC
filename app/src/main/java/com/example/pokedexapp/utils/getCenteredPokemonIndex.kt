package com.example.pokedexapp.utils

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyListState

@OptIn(ExperimentalFoundationApi::class)
internal fun getCenteredPokemonIndex(
    listState: LazyListState,
    itemCount: Int,
    spacerOffset: Int = 1
): Int? {
    val visibleItems = listState.layoutInfo.visibleItemsInfo
    if (visibleItems.isEmpty() || itemCount == 0) return null

    val center = listState.layoutInfo.viewportEndOffset / 2

    val isAtStart = listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
    val isAtEnd = visibleItems.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1

    val centerItem = when {
        isAtStart -> visibleItems.firstOrNull()
        isAtEnd -> visibleItems.lastOrNull()
        else -> visibleItems.minByOrNull {
            kotlin.math.abs((it.offset + it.size / 2) - center)
        }
    }

    return centerItem?.index?.minus(spacerOffset)?.takeIf { it in 0 until itemCount }
}
