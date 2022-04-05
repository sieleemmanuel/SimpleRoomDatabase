package com.devsiele.roomdatabase.data.repo

import com.devsiele.roomdatabase.database.NoteDao
import javax.inject.Inject

class MainRepository @Inject constructor(noteDao: NoteDao){
    val notes = noteDao.getAllNotes()

}