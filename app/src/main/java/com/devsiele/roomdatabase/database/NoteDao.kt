package com.devsiele.roomdatabase.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.devsiele.roomdatabase.model.Note

@Dao
interface NoteDao {
    @Insert
    suspend fun insert( note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * FROM notes_database_table")
    fun getAllNotes(): LiveData<List<Note>?>

    @Query("DELETE FROM notes_database_table")
    suspend fun clearAllNotes()

    @Query("DELETE FROM notes_database_table WHERE noteId=:id")
    suspend fun deleteNotes(id: Long)

    @Query("SELECT * FROM notes_database_table WHERE noteId=:id")
    suspend fun getNote(id: Long):Note?

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