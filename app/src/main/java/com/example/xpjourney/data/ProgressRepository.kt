package com.example.xpjourney.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class ProgressRepository(private val dataStore: DataStore<Preferences>) {
    data class Progress(
        val lastLoginDate: LocalDate?,
        val currentStreak: Int,
        val xp: Int
    )

    private val LAST_LOGIN_DATE = stringPreferencesKey("last_login_date")
    private val CURRENT_STREAK = intPreferencesKey("current_streak")
    private val XP = intPreferencesKey("xp")

    val progressFlow: Flow<Progress> = dataStore.data.map { prefs ->
        val dateStr = prefs[LAST_LOGIN_DATE]
        val date = dateStr?.let { LocalDate.parse(it) }
        Progress(
            lastLoginDate = date,
            currentStreak = prefs[CURRENT_STREAK] ?: 0,
            xp = prefs[XP] ?: 0
        )
    }

    suspend fun saveProgress(lastLoginDate: LocalDate?, currentStreak: Int, xp: Int) {
        dataStore.edit { prefs ->
            if (lastLoginDate != null) {
                prefs[LAST_LOGIN_DATE] = lastLoginDate.toString()
            } else {
                prefs.remove(LAST_LOGIN_DATE)
            }
            prefs[CURRENT_STREAK] = currentStreak
            prefs[XP] = xp
        }
    }

}