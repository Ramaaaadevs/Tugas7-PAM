package com.diwan.myprofileapp.shared.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.diwan.myprofileapp.db.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class NoteRepository(driverFactory: DatabaseDriverFactory) {
    private val db = NotesDatabase(driverFactory.createDriver())
    private val queries = db.noteQueries

    fun getAllNotes(): Flow<List<NoteItem>> =
        queries.selectAll().asFlow().mapToList(Dispatchers.Default).map { list ->
            list.map { it.toNoteItem() }
        }

    fun getFavoriteNotes(): Flow<List<NoteItem>> =
        queries.selectFavorites().asFlow().mapToList(Dispatchers.Default).map { list ->
            list.map { it.toNoteItem() }
        }

    fun searchNotes(query: String): Flow<List<NoteItem>> =
        queries.search(query).asFlow().mapToList(Dispatchers.Default).map { list ->
            list.map { it.toNoteItem() }
        }

    suspend fun getNoteById(id: Long): NoteItem? = withContext(Dispatchers.Default) {
        queries.selectById(id).executeAsOneOrNull()?.toNoteItem()
    }

    suspend fun insertNote(title: String, content: String) = withContext(Dispatchers.Default) {
        val now = System.currentTimeMillis()
        queries.insert(title, content, now, now)
    }

    suspend fun updateNote(id: Long, title: String, content: String) = withContext(Dispatchers.Default) {
        queries.update(title, content, System.currentTimeMillis(), id)
    }

    suspend fun toggleFavorite(id: Long) = withContext(Dispatchers.Default) {
        queries.toggleFavorite(id)
    }

    suspend fun deleteNote(id: Long) = withContext(Dispatchers.Default) {
        queries.delete(id)
    }

    private fun com.diwan.myprofileapp.db.Note.toNoteItem() = NoteItem(
        id = id,
        title = title,
        content = content,
        isFavorite = isFavorite,
        createdAt = created_at
    )
}