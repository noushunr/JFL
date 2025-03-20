package com.newagesmb.androidmvvmarchitecture.data.local.preferences
// Created by Noushad on 15-08-2023.
// Copyright (c) 2023 NewAgeSys. All rights reserved.
//
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(private val sharedPreference: SharedPreferences) {

    companion object {
        const val KEY_BEARER_TOKEN = "KEY_BEARER_TOKEN"
        const val KEY_REFRESH_TOKEN = "KEY_REFRESH_TOKEN"
        const val KEY_USER_LOGGED_IN = "KEY_USER_LOGGED_IN"
        const val KEY_FCM_TOKEN = "KEY_FCM_TOKEN"
    }

    fun clearPreferences() {
        sharedPreference.edit { clear() }
    }

    var bearerToken: String?
        get() = sharedPreference.getString(KEY_BEARER_TOKEN, null)
        set(value) = sharedPreference.edit { putString(KEY_BEARER_TOKEN, value) }

    var refreshToken: String?
        get() = sharedPreference.getString(KEY_REFRESH_TOKEN, null)
        set(value) = sharedPreference.edit { putString(KEY_REFRESH_TOKEN, value) }

    var isUserLoggedIn: Boolean
        get() = sharedPreference.getBoolean(KEY_USER_LOGGED_IN, false)
        set(value) = sharedPreference.edit { putBoolean(KEY_USER_LOGGED_IN, value) }

    var fcmToken: String?
        get() = sharedPreference.getString(KEY_FCM_TOKEN, null)
        set(value) = sharedPreference.edit { putString(KEY_FCM_TOKEN, value) }

}
