package com.newagesmb.androidmvvmarchitecture.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo

import android.net.ConnectivityManager




class ConnectivityReceiver : BroadcastReceiver() {
    companion object{
        var connectivityReceiverListener: ConnectivityReceiverListener? = null
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = (activeNetwork != null
                && activeNetwork.isConnectedOrConnecting)

        if (connectivityReceiverListener != null) {
            connectivityReceiverListener?.onNetworkConnectionChanged(isConnected)
        }
    }
    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }
}