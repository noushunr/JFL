package com.newagesmb.androidmvvmarchitecture.data.local.preferences

import android.content.Context
import android.content.Intent
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.newagesmb.androidmvvmarchitecture.data.model.response.LoginModel
import com.newagesmb.androidmvvmarchitecture.di.ApplicationScope
import com.newagesmb.androidmvvmarchitecture.ui.MainActivity
import com.newagesmb.androidmvvmarchitecture.utils.Constents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Singleton

/** Created by Afsal on 30-08-2023.
 * Copyright (c) 2023 NewAgeSys. All rights reserved.
 */
private val Context.dataStore by preferencesDataStore("user_preferences")

@Singleton
class DataStoreManager(context: Context ) {

    private val dataStore = context.dataStore

    suspend fun saveAuthToken(authToken: String) {
        dataStore.edit { preferences ->
            preferences[Constents.PRE_AUTH_TOKEN] = authToken
        }
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        dataStore.edit { preferences ->
            preferences[Constents.PRE_REFRESH_TOKEN] = refreshToken
        }
    }
    suspend fun setToken(token:String) {
        dataStore.edit { preference ->
            preference[Constents.BEARER_TOKEN] = token
        }
    }

    fun getToken() = dataStore.data.map { preferences ->
        preferences[Constents.BEARER_TOKEN]
    }

    suspend fun setUserName(name:String) {
        dataStore.edit { preference ->
            preference[Constents.USER_NAME] = name
        }
    }

    fun getName() = dataStore.data.map { preferences ->
        preferences[Constents.USER_NAME]
    }

    suspend fun setUserLoggedIn(logged: Boolean) {
        dataStore.edit { preference ->
            preference[Constents.PRE_USER_LOGGED_IN] = logged.toString()
        }
    }

    fun isUserLoggedIn() = dataStore.data.map { preferences ->
        preferences[Constents.PRE_USER_LOGGED_IN].toBoolean()
    }


    suspend fun saveSession(session: LoginModel?) {
        if (session == null) return
        val gson = Gson()
        val json = gson.toJson(session)
        dataStore.edit { prefrence ->
            prefrence[Constents.PRE_SESSION] = json
        }
    }

    fun getSession() =
        dataStore.data.map { preferences ->

            val gson = Gson()
            val json = preferences[Constents.PRE_SESSION]
            gson.fromJson(json, LoginModel::class.java)


        }


    fun getAuthTokens() = dataStore.data.map { preferences ->
        preferences[Constents.PRE_AUTH_TOKEN]
    }

    fun getRefreshToken() = dataStore.data.map { preferences ->
        preferences[Constents.PRE_REFRESH_TOKEN]
    }


    suspend fun clearSession() {

        dataStore.edit { preferences ->
            preferences.clear()
        }

    }



//    private fun clearUserData() {
//        externalScope.launch(Dispatchers.Main) {
//            try {
////                context.showToast("Unauthorised login detected, signing out!!")
//
//                Intent(context.applicationContext, MainActivity::class.java).also {
//                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    context.startActivity(it)
//                }
//            } catch (e: Exception) {
//            }
//        }
//    }
}
