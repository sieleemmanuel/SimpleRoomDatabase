package com.devsiele.roomdatabase.main.listitems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devsiele.roomdatabase.database.NoteDao

class ListViewModelFactory(private val dao: NoteDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}