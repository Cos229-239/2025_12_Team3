package com.example.xpjourney.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.xpjourney.JournalDao

class JournalViewModelFactory(
    private val dao: JournalDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JournalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JournalViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}