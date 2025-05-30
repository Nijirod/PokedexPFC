package com.example.pokedexapp.utils

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.launch

fun sendUrlsToWear(context: Context) {
    CoroutineScope(Dispatchers.IO).launch {
        val nodes = Wearable.getNodeClient(context).connectedNodes.await()
        for (node in nodes) {
            val message = "test"
            Log.d("WearDebug", "Enviando mensaje de prueba a: ${node.displayName}")
            Wearable.getMessageClient(context)
                .sendMessage(node.id, "/pokemon_data", message.toByteArray())
                .await()
        }
    }
}