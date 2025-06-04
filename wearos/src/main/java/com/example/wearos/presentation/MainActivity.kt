// MainActivity.kt
package com.example.wearos.presentation

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.Wearable
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import com.google.android.gms.wearable.MessageEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class MainActivity : ComponentActivity(), MessageClient.OnMessageReceivedListener {

    private var setUrls: ((String, String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("WearDebug", "MainActivity creada en el reloj")

        Wearable.getMessageClient(this).addListener(this)
        Log.d("WearDebug", "Listener registrado")
        setContent {
            var imageUrl by remember { mutableStateOf<String?>(null) }
            var audioUrl by remember { mutableStateOf<String?>(null) }

            setUrls = { img, audio ->
                imageUrl = img
                audioUrl = audio
            }
            LaunchedEffect(Unit) {
                Wearable.getMessageClient(this@MainActivity).addListener(this@MainActivity)
                Log.d("WearDebug", "Listener registrado en el reloj")
            }

            PokemonReceiverScreen(
                imageUrl = imageUrl,
                audioUrl = audioUrl
            )
            SendTestMessageButton()

        }
    }


    override fun onMessageReceived(event: MessageEvent) {
        val path = event.path
        val message = String(event.data)
        Log.d("WearDebug", "Mensaje recibido: path=$path, contenido=$message")

        if (path == "/pokemon_data") {
            val parts = message.split(",")
            if (parts.size >= 2) {
                val img = parts[0]
                val audio = parts[1]
                Log.d("WearDebug", "Parsed image: $img | audio: $audio")
                runOnUiThread {
                    setUrls?.invoke(img, audio)
                }
            } else {
                Log.e("WearDebug", "Mensaje mal formado: $message")
            }
        }
    }


    fun sendMessageToMobile(context: Context, message: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val nodes = Wearable.getNodeClient(context).connectedNodes.await()
                if (nodes.isEmpty()) {
                    Log.d("WearDebug", "No hay nodos conectados para enviar mensaje")
                    return@launch
                }
                for (node in nodes) {
                    Log.d("WearDebug", "Enviando mensaje al móvil: nodo=${node.displayName} id=${node.id}")
                    Wearable.getMessageClient(context)
                        .sendMessage(node.id, "/message_from_watch", message.toByteArray())
                        .await()
                    Log.d("WearDebug", "Mensaje enviado correctamente")
                }
            } catch (e: Exception) {
                Log.e("WearDebug", "Error enviando mensaje al móvil: ${e.message}", e)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Wearable.getMessageClient(this).removeListener(this)
    }
    @Composable
    fun SendTestMessageButton() {
        val context = LocalContext.current
        Button(onClick = {
            sendMessageToMobile(context, "Hola móvil desde el reloj!")
        }) {
            Text(text = "Enviar mensaje al móvil")
        }
    }
}