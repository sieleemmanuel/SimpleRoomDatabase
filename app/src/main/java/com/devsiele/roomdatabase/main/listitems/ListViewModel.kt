package com.devsiele.roomdatabase.main.listitems

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devsiele.roomdatabase.database.NoteDao
import com.devsiele.roomdatabase.repo.MainRepository

class ListViewModel(val database: NoteDao) :
    ViewModel() {
    private val notesRepo = MainRepository(database)
    val notelist = notesRepo.notes


    private val _navigateToDetail = MutableLiveData<Boolean>()
    val navigateToDetail:LiveData<Boolean>
        get() = _navigateToDetail

    fun navigateToNoteDetail() {
        _navigateToDetail.value = true
    }

    fun navigateToDetailDone() {
        _navigateToDetail.value = false
    }

    private val _navigateToAddFragment = MutableLiveData<Boolean>()
    val navigateToAddNote: LiveData<Boolean>
        get() = _navigateToAddFragment

    fun navigateToAddNote() {
        _navigateToDetail.value = true
    }

    fun navigateToAddNoteDone() {
        _navigateToDetail.value = false
    }


    /*
     private suspend fun insert(note: Note) {
         database.insert(note)
     }

     private suspend fun update(note: Note) {

         database.update(note)

     }

     private suspend fun delete(noteID: Long) {
         database.deleteNotes(noteID)
     }

     private suspend fun getNote(id: Long): Note? {
         return database.getNote(id)
     }

     fun insertNewNote(newNote: Note) {
         viewModelScope.launch {
             insert(newNote)
         }

     }
     private val note = MutableLiveData<Note?>()
     fun updateNote(id: Long, title: String, category: String, note_text: String) {
         viewModelScope.launch {
             note.value = getNote(id)
             val noteTUpdate = note.value
             noteTUpdate?.apply {
                 noteTitle = title
                 noteCategory = category
                 noteText = note_text
             }
             noteTUpdate?.let { update(it) }
         }
     }

     fun deleteNote(id: Long) {
         viewModelScope.launch {
             delete(id)
         }
     }*/

}