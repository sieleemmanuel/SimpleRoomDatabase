package com.devsiele.roomdatabase.adapters

import androidx.recyclerview.selection.ItemKeyProvider
import com.devsiele.roomdatabase.main.listitems.RecyclerAdapter
import com.devsiele.roomdatabase.model.Note

class KeyProvider(private val adapter: RecyclerAdapter):ItemKeyProvider<Note>(SCOPE_CACHED) {
    override fun getKey(position: Int): Note? {
       return adapter.getItemAtPosition(position)
    }

    override fun getPosition(key: Note): Int {
        return adapter.getPosition(key.noteTitle)
    }
}
