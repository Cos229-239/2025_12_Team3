package com.example.xpjourney.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xpjourney.JournalDao
import com.example.xpjourney.JournalEntry
import kotlinx.coroutines.launch

class JournalViewModel(
    private val dao: JournalDao
) : ViewModel() {

    // Load all entries
    suspend fun loadEntries(): List<JournalEntry> {
        return dao.getAllEntries()
    }

    // Load a single entry by ID
    suspend fun getEntryById(id: Int): JournalEntry? {
        return dao.getEntryById(id)
    }

    // Update an entry
    fun updateEntry(id: Int, newTitle: String, newBody: String) {
        viewModelScope.launch {
            val existing = dao.getEntryById(id) ?: return@launch

            val updated = existing.copy(
                title = newTitle,
                body = newBody,
                timestamp = System.currentTimeMillis()
            )
            dao.updateEntry(updated)
        }
    }
}