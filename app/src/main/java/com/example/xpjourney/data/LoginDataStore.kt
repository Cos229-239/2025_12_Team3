package com.example.xpjourney.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.loginDataStore by preferencesDataStore("login_prefs")

class LoginDataStore(private val context: Context) {

    companion object {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    val isLoggedIn: Flow<Boolean> = context.loginDataStore.data
        .map { prefs -> prefs[IS_LOGGED_IN] ?: false }

    suspend fun setLoggedIn(value: Boolean) {
        context.loginDataStore.edit { prefs -> prefs [IS_LOGGED_IN] = value
        }
    }
}