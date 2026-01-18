package com.example.xpjourney.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.xpjourney.data.LoginDataStore


class LoginViewModel(private val dataStore: LoginDataStore) : ViewModel() {

    val isLoggedIn = dataStore.isLoggedIn
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
        )

    fun login() {
        viewModelScope.launch {
            dataStore.setLoggedIn(true)
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStore.setLoggedIn(false)
        }
    }
}