package com.devsiele.roomdatabase.main.listitems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsiele.roomdatabase.database.NoteDao
import com.devsiele.roomdatabase.data.model.Note
import com.devsiele.roomdatabase.data.repo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    mainRepository: MainRepository,
    private val noteDao: NoteDao
) : ViewModel() {

    val noteList = mainRepository.notes

   fun deleteSelectedNotes(note: Note){

       viewModelScope.launch {
           noteDao.delete(note)
       }

   }

}