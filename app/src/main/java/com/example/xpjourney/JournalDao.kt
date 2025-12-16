package com.example.xpjourney

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface JournalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: JournalEntry)

    @Query("SELECT * FROM journal_entries ORDER BY timestamp DESC")
    suspend fun getAllEntries(): List<JournalEntry>

    @Delete
    suspend fun deleteEntry(entry: JournalEntry)

    @Query("SELECT * FROM journal_entries WHERE id = :id")
    suspend fun getEntryById(id: Int): JournalEntry?
}