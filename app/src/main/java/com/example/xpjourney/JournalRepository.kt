package com.example.xpjourney

class JournalRepository(private val dao: JournalDao) {
    suspend fun addEntry(entry: JournalEntry) {
        dao.insertEntry(entry)
    }

    suspend fun getAllEntries(): List<JournalEntry> {
        return dao.getAllEntries()
    }

    suspend fun deleteEntry(entry: JournalEntry) {
        dao.deleteEntry(entry)
    }

    suspend fun getEntryById(id: Int): JournalEntry? {
        return dao.getEntryById(id)
    }
}