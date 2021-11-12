package com.devsiele.roomdatabase.main.newitem

import android.app.Application
import androidx.lifecycle.*
import com.devsiele.roomdatabase.R
import com.devsiele.roomdatabase.database.NoteDao
import com.devsiele.roomdatabase.model.Note
import kotlinx.coroutines.launch

class AddNewViewModel(val database: NoteDao, application: Application) :
    AndroidViewModel(application) {
    val categories: Array<String> = application.resources.getStringArray(R.array.categories)

    private val _navigateToList=MutableLiveData<Boolean>()
    val navigateToList:LiveData<Boolean>
    get() = _navigateToList


    private suspend fun insert(note: Note) {
        database.insert(note)

    }

    fun insertNewNote(newNote: Note) {
        viewModelScope.launch {
            insert(newNote)
        }

    }
    private suspend fun update(id:Long,category:String,title:String,noteText:String) {
        database.update(id,category,title,noteText)

    }

    fun updateNote(id:Long,category:String,title:String,noteText:String) {
        viewModelScope.launch {
            update(id,category,title,noteText)
        }

    }

    fun navigateToListFragment(){
        _navigateToList.value=true
    }

    fun navigateToListDone() {
        _navigateToList.value = false
    }

}
class AddNewViewModelFactory(val dataSource: NoteDao, val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddNewViewModel::class.java))
            return AddNewViewModel(dataSource, application) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}