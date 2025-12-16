package com.example.xpjourney

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class JournalDao_Impl(
  __db: RoomDatabase,
) : JournalDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfJournalEntry: EntityInsertAdapter<JournalEntry>

  private val __deleteAdapterOfJournalEntry: EntityDeleteOrUpdateAdapter<JournalEntry>
  init {
    this.__db = __db
    this.__insertAdapterOfJournalEntry = object : EntityInsertAdapter<JournalEntry>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `journal_entries` (`id`,`title`,`body`,`timestamp`) VALUES (nullif(?, 0),?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: JournalEntry) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.body)
        statement.bindLong(4, entity.timestamp)
      }
    }
    this.__deleteAdapterOfJournalEntry = object : EntityDeleteOrUpdateAdapter<JournalEntry>() {
      protected override fun createQuery(): String = "DELETE FROM `journal_entries` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: JournalEntry) {
        statement.bindLong(1, entity.id.toLong())
      }
    }
  }

  public override suspend fun insertEntry(entry: JournalEntry): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfJournalEntry.insert(_connection, entry)
  }

  public override suspend fun deleteEntry(entry: JournalEntry): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfJournalEntry.handle(_connection, entry)
  }

  public override suspend fun getAllEntries(): List<JournalEntry> {
    val _sql: String = "SELECT * FROM journal_entries ORDER BY timestamp DESC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfBody: Int = getColumnIndexOrThrow(_stmt, "body")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: MutableList<JournalEntry> = mutableListOf()
        while (_stmt.step()) {
          val _item: JournalEntry
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpBody: String
          _tmpBody = _stmt.getText(_columnIndexOfBody)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          _item = JournalEntry(_tmpId,_tmpTitle,_tmpBody,_tmpTimestamp)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getEntryById(id: Int): JournalEntry? {
    val _sql: String = "SELECT * FROM journal_entries WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfBody: Int = getColumnIndexOrThrow(_stmt, "body")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: JournalEntry?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpBody: String
          _tmpBody = _stmt.getText(_columnIndexOfBody)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          _result = JournalEntry(_tmpId,_tmpTitle,_tmpBody,_tmpTimestamp)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
