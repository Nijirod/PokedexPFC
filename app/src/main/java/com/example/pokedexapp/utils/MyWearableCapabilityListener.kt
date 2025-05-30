package com.example.pokedexapp.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.Wearable

class MyWearableCapabilityListener(private val context: Context) :
    CapabilityClient.OnCapabilityChangedListener {

    private val capabilityClient = Wearable.getCapabilityClient(context)
    private val WEAR_CAPABILITY_NAME = "verify_remote_example_wear_app"
    private val CAPABILITY_WEAR_APP = "verify_remote_example_wear_app"



    fun startListening() {
        capabilityClient.addListener(this, WEAR_CAPABILITY_NAME)

        capabilityClient.getCapability(WEAR_CAPABILITY_NAME, CapabilityClient.FILTER_REACHABLE)
            .addOnSuccessListener { capabilityInfo ->
                onCapabilityChanged(capabilityInfo)
            }
            .addOnFailureListener { e ->
                Log.e("WearDebug", "Error al verificar capacidad: ${e.message}")
            }
    }


    fun stopListening() {
        capabilityClient.removeListener(this)
    }

    override fun onCapabilityChanged(capabilityInfo: CapabilityInfo) {
        val nodes = capabilityInfo.nodes
        if (nodes.isNotEmpty()) {
            Log.d("WearDebug", "Reloj conectado: ${nodes.map { it.displayName }}")
        } else {
            Log.d("WearDebug", "Reloj no conectado")
        }
    }
}
