package com.devsiele.roomdatabase.repo

import com.devsiele.roomdatabase.database.NoteDao

class MainRepository (database: NoteDao){

    /*
    *Retrieve data from database
    * */
    val notes = database.getAllNotes()

}