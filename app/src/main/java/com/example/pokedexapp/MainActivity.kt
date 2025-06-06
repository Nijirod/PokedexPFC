package com.example.pokedexapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.pokedexapp.ui.navigation.BottomBar
import com.example.pokedexapp.ui.navigation.BottomNavGraph
import com.example.pokedexapp.ui.screens.setAppLocale
import com.example.pokedexapp.ui.theme.PokemonAppTheme
import com.example.pokedexapp.utils.MyWearableCapabilityListener
import com.example.pokedexapp.utils.getSavedLanguage
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), MessageClient.OnMessageReceivedListener {

    private lateinit var capabilityListener: MyWearableCapabilityListener

    override fun onCreate(savedInstanceState: Bundle?) {
        val language = getSavedLanguage(this)
        setAppLocale(this, language)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        capabilityListener = MyWearableCapabilityListener(this)
        capabilityListener.startListening()
        Wearable.getMessageClient(this).addListener(this)
        setContent {
            PokemonAppTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomBar(navController) }
                ) { paddingValues ->
                    BottomNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
    override fun onDestroy() {
        capabilityListener.stopListening()
        super.onDestroy()
        Wearable.getMessageClient(this).removeListener(this)
    }

    override fun onMessageReceived(event: MessageEvent) {
        if (event.path == "/message_from_watch") {
            val msg = String(event.data)
            runOnUiThread {
                Toast.makeText(this, "Mensaje recibido del reloj: $msg", Toast.LENGTH_LONG).show()
                Log.d("WearDebug", "Mensaje recibido del reloj: $msg")
            }
        }
    }

}