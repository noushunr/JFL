package com.newagesmb.androidmvvmarchitecture.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NetworkConnectivityObserver(context: Context) : ConnectvityIObserver {


    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @RequiresApi(Build.VERSION_CODES.N)
    override fun observe(): Flow<ConnectvityIObserver.Status> {
        val initialStatus = getConnectivityStatus()

        return callbackFlow {
            if (initialStatus != ConnectvityIObserver.Status.Avialable) {
                launch {
                    send(initialStatus)
                }
            }

            val callback = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                               Log.d("PPP","internet is available ")

                    launch {
                        send(ConnectvityIObserver.Status.Avialable)

                    }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    Log.d("PPP","internet is available ")

                    launch {
                        send(ConnectvityIObserver.Status.Losing)

                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    Log.d("PPP","internet is available ")


                    launch {
                        send(ConnectvityIObserver.Status.Lost)

                    }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    Log.d("PPP","internet is unavailable ")

                    launch {
                        send(ConnectvityIObserver.Status.Unavailable)

                    }
                }
            }


            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose{
                 connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()

    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun getConnectivityStatus(): ConnectvityIObserver.Status {
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return when {
            networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true -> {
                ConnectvityIObserver.Status.Avialable
            }
            else -> ConnectvityIObserver.Status.Unavailable
        }
    }

}
