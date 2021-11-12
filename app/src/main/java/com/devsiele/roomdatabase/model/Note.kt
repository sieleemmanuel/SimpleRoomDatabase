package com.devsiele.roomdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_database_table")
data class Note(
    @ColumnInfo(name = "note_category") var noteCategory: String="",
    @ColumnInfo(name = "note_title") var noteTitle: String="",
    @ColumnInfo(name = "note_text") var noteText: String=""
) {
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0L
}