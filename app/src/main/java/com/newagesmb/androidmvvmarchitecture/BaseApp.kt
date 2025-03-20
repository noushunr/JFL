package com.newagesmb.androidmvvmarchitecture

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.newagesmb.androidmvvmarchitecture.utils.NetworkUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setupFirebase()
        setupNetworkMonitoring()

    }

    private fun setupNetworkMonitoring() {
        val networkUtils = NetworkUtils(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(networkUtils)
    }

    private fun setupFirebase() {
        if (BuildConfig.DEBUG) {
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)
        }
    }
}