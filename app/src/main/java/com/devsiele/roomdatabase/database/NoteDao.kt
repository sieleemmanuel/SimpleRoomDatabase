package com.devsiele.roomdatabase.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.devsiele.roomdatabase.data.model.Note

@Dao
interface NoteDao {
    @Insert
    suspend fun insert( note: Note)

    @Delete
    suspend fun delete(note: Note):Int

    @Query("SELECT * FROM notes_database_table")
    fun getAllNotes(): LiveData<List<Note>?>

    @Query("SELECT * FROM notes_database_table")
    fun getNotesList(): List<Note>?

    @Query("DELETE FROM notes_database_table")
    suspend fun clearAllNotes()

    @Query("SELECT * FROM notes_database_table WHERE noteId=:id")
    suspend fun getNote(id: Long): Note?

    @Query(
        "UPDATE notes_database_table SET note_category=:category,note_title=:noteTitle,note_text=:noteText WHERE noteId=:id"
    )
    suspend fun update(
        id: Long,
        category: String,
        noteTitle: String,
        noteText: String
    )

}