package com.devsiele.roomdatabase.main.newitem

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsiele.roomdatabase.R
import com.devsiele.roomdatabase.data.model.Note
import com.devsiele.roomdatabase.database.NoteDao
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewViewModel @Inject constructor(
    val noteDao: NoteDao,
    @ApplicationContext context: Context
) :
    ViewModel() {
    val categories: Array<String> = context.resources.getStringArray(R.array.categories)

    private suspend fun insert(note: Note) {
        noteDao.insert(note)

    }
    fun insertNewNote(newNote: Note) {
        viewModelScope.launch {
            insert(newNote)
        }

    }
    private suspend fun update(id:Long,category:String,title:String,noteText:String) {
        noteDao.update(id,category,title,noteText)

    }

    fun updateNote(id:Long,category:String,title:String,noteText:String) {
        viewModelScope.launch {
            update(id,category,title,noteText)
        }
    }

}
/*
class AddNewViewModelFactory(val dataSource: NoteDao, val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddNewViewModel::class.java))
            return AddNewViewModel(dataSource, application) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}*/
