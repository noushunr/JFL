package com.newagesmb.androidmvvmarchitecture.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class NetworkUtils(context: Context) : DefaultLifecycleObserver {

    companion object {
        var isNetworkConnected = false
    }

    private val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
    private val validNetworks = hashSetOf<Network>()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            connectivityManager.getNetworkCapabilities(network).also {
                if (it?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) == true) {
                    validNetworks.add(network)
                }
            }
            checkValidNetworks()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            validNetworks.remove(network)
            checkValidNetworks()
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        startNetworkMonitoring()
    }

    private fun startNetworkMonitoring() {
        try {
            val networkRequest = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                .build()
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
            checkValidNetworks()
        } catch (e: Exception) {
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        stopNetworkMonitoring()
    }

    private fun stopNetworkMonitoring() {
        try {
            validNetworks.clear()
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } catch (e: Exception) {
        }
    }

    private fun checkValidNetworks() {
        isNetworkConnected = validNetworks.size > 0
    }
}
