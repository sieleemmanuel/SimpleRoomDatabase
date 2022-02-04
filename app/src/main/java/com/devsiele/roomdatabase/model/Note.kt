package com.devsiele.roomdatabase.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "notes_database_table")
@Parcelize
data class Note(
    @ColumnInfo(name = "note_category") var noteCategory: String="",
    @ColumnInfo(name = "note_title") var noteTitle: String="",
    @ColumnInfo(name = "note_text") var noteText: String=""
):Parcelable{
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0
}