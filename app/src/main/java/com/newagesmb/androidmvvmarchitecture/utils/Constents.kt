package com.newagesmb.androidmvvmarchitecture.utils

import android.provider.ContactsContract
import androidx.datastore.preferences.core.stringPreferencesKey

object Constents {


    const val DATABASE = "base_app.db"

    val PRE_SESSION = stringPreferencesKey("login_session")
    val PRE_AUTH_TOKEN = stringPreferencesKey("auth")
    val PRE_REFRESH_TOKEN = stringPreferencesKey("refresh")
     val PRE_USER_LOGGED_IN = stringPreferencesKey("key_user_logged_in")
    const val PREFERENCE_NAME = ContactsContract.Directory.PACKAGE_NAME + "_pref"
    const val  TEMP_PHOTO_FILE = "image.jpeg"
    val BEARER_TOKEN = stringPreferencesKey("token")
    val USER_NAME = stringPreferencesKey("user_name")

    const val INTENT_BROADCAST = "local_broadcast"
    const val LOGIN = 1
    const val LOGOUT = 2
}