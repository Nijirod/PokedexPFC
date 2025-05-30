package com.example.pokedexapp.utils

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.tasks.await

object WearNodeHelper {

    suspend fun sendMessageToWatch(context: Context, message: String) {
        val nodes = Wearable.getNodeClient(context).connectedNodes.await()
        if (nodes.isEmpty()) {
            Log.e("WearDebug", "No hay nodos conectados")
            return
        }
        for (node in nodes) {
            Log.d("WearDebug", "Enviando mensaje a nodo: ${node.displayName}")
            try {
                Wearable.getMessageClient(context)
                    .sendMessage(node.id, "/pokemon_data", message.toByteArray())
                    .await()
                Log.d("WearDebug", "Mensaje enviado correctamente")
            } catch (e: Exception) {
                Log.e("WearDebug", "Error al enviar mensaje: ${e.message}", e)
            }
        }
    }
    suspend fun logConnectedNodes(context: Context) {
        val nodes = Wearable.getNodeClient(context).connectedNodes.await()
        if (nodes.isEmpty()) {
            Log.e("WearDebug", "No hay nodos conectados")
        } else {
            for (node in nodes) {
                Log.d("WearDebug", "Nodo conectado: ${node.displayName}")
            }
        }
    }

}
