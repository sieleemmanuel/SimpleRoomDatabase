package com.devsiele.roomdatabase.database

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.devsiele.roomdatabase.getOrAwaitValue
import com.devsiele.roomdatabase.data.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class NoteDaoTest {
    private lateinit var database: NoteDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun  setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            NoteDatabase::class.java,
        ).build()
        noteDao = database.noteDao
    }
    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insert() = runBlocking {
        val noteItem = Note(noteCategory = "Shopping", noteTitle = "Food Stuffs", noteText =
            "I will have to go out and by some missing food stuffs like vegetables and groceries")
        val noteItem1 = Note(noteCategory = "Studying", noteTitle = "Coding Stuffs",
           noteText =  "I have to go code and code some missing code stuffs")
        noteDao.insert(noteItem)
        //noteDao.insert(noteItem1)
        val noteItems = noteDao.getNotesList()
        val note = noteDao.getNote(noteItem.noteId)
        Log.d("noteDaoTest", "insert: ${noteItems?.size}")
        assertThat(noteItems).contains(note)
    }

    @Test
    fun deleteNote()= runBlocking {
        val noteItem = Note(noteCategory = "Shopping", noteTitle = "Food Stuffs", noteText =
        "I will have to go out and by some missing food stuffs like vegetables and groceries")
        val noteItem1 = Note(noteCategory = "Studying", noteTitle = "Coding Stuffs",
            noteText =  "I have to go code and code some missing code stuffs")
        noteDao.insert(noteItem)
        noteDao.insert(noteItem1)
        val rows = noteDao.delete(noteItem1)
        Log.d("NoteDaoTest", "deleteNotes: $rows")
        assertThat(rows).isEqualTo(1)
    }



}