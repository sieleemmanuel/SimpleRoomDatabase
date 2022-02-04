package com.devsiele.roomdatabase.main.listitems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsiele.roomdatabase.database.NoteDao
import com.devsiele.roomdatabase.model.Note
import com.devsiele.roomdatabase.repo.MainRepository
import kotlinx.coroutines.launch

class ListViewModel(val database: NoteDao) :
    ViewModel() {
    private val notesRepo = MainRepository(database)
    val notelist = notesRepo.notes


   fun deleteSelectedNotes(note: Note){
       viewModelScope.launch {
           database.delete(note)
       }

   }

}